package com.skw.app.chess.game;

import java.util.Scanner;

public class Playground {
	private ChessMan[][] board;
	public static final int width = 9;
	public static final int height = 10;
	private ChessMan[] chessmen = new ChessMan[ChessMan.TOTAL_CHESS_CNT];

	public Playground() {
		board = new ChessMan[width][];
		for (int x = 0; x < width; x++) {
			board[x] = new ChessMan[height];
		}
		for (int chessmanId = 0; chessmanId < ChessMan.TOTAL_CHESS_CNT; chessmanId++) {
			ChessMan chessman = new ChessMan(chessmanId);
			GlobalPosition globalPosition = chessman.getGlobalPosition();
			chessmen[chessmanId] = chessman;
			board[globalPosition.getX()][globalPosition.getY()] = chessman;
		}
	}

	public ChessMan getChessMan(GlobalPosition position) {
		return board[position.getX()][position.getY()];
	}

	public ChessMan getChessMan(int chessmanId) {
		if (chessmanId < 0 || chessmanId >= ChessMan.TOTAL_CHESS_CNT) {
			return null;
		}
		return chessmen[chessmanId];
	}

	public void move(int chessmanId, GlobalPosition dst) {
		ChessMan chessMan = chessmen[chessmanId];
		if (!chessMan.canExistAt(dst)) {
			String msg = String.format("%s%s cannot exist at %s", chessMan.isAlive() ? "" : "dead ",
					chessMan.toDebugString(), dst.toString());
			throw new IllegalChessMoveException(msg);
		}
		if (!chessMan.canReach(dst, this)) {
			String msg = String.format("%s%s can not reach from %s to %s", chessMan.isAlive() ? "" : "dead ",
					chessMan.toDebugString(), chessMan.getGlobalPosition().toString(), dst.toString());
			throw new IllegalChessMoveException(msg);
		}
		GlobalPosition src = chessMan.getGlobalPosition();
		if (board[dst.getX()][dst.getY()] != null) {
			board[dst.getX()][dst.getY()].setKilled();
		}
		board[dst.getX()][dst.getY()] = chessMan;
		if (chessMan.isAlive()) { // dead chessman might be moved to undo an earlier move.
			board[src.getX()][src.getY()] = null;
		}
		chessMan.moveTo(dst);
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean coordinate) {
		StringBuilder sb = new StringBuilder();
		for (int y = height - 1; y >= 0; y--) {
			if (coordinate) {
				sb.append(y + " ");
			}
			for (int x = 0; x < width; x++) {
				if (board[x][y] != null) {
					sb.append(board[x][y].toString());
				} else {
					sb.append('十');
				}
			}
			sb.append(String.format("%n"));
		}
		if (coordinate) {
			sb.append("- 〇一二三四五六七八");
			sb.append(String.format("%n"));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		new ManualTester();
	}
}

class IllegalChessMoveException extends IllegalStateException {
	private static final long serialVersionUID = 4472797180478847001L;

	public IllegalChessMoveException() {
		super();
	}

	public IllegalChessMoveException(String msg) {
		super(msg);
	}
}

class ManualTester {
	private Playground playground;
	Scanner sc;

	public ManualTester() {
		playground = new Playground();
		sc = new Scanner(System.in);
		run();
		sc.close();
	}

	private void run() {
		System.out.println("Test begins");
		while (sc.hasNextLine()) {
			try {
				String cmd = sc.nextLine();
				String[] slices = cmd.trim().split("\\s+");
				if (slices.length == 4 && slices[0].equals("move")) {
					int chessmanId = Integer.parseInt(slices[1]);
					int dstX = Integer.parseInt(slices[2]);
					int dstY = Integer.parseInt(slices[3]);
					playground.move(chessmanId, new GlobalPosition(dstX, dstY));
					System.out.println(playground.toString(true));
				} else if (slices.length == 3 && slices[0].equals("whatis")) {
					int x = Integer.parseInt(slices[1]);
					int y = Integer.parseInt(slices[2]);
					ChessMan chessMan = playground.getChessMan(new GlobalPosition(x, y));
					if (chessMan != null) {
						System.out.println(chessMan.toDebugString());
					} else {
						System.out.println(chessMan);
					}
				} else if (slices.length == 1 && slices[0].equals("exit")) {
					break;
				} else if (slices.length == 1 && slices[0].equals("show")) {
					System.out.println(playground.toString(true));
				} else {
					System.out.println("Unknown cmd");
				}
			} catch (IllegalChessMoveException e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println("Test ends");
	}
}
