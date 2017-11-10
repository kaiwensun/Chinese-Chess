package com.skw.app.chess.game;

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
	
	public void move(int chessmanId, GlobalPosition dst) {
		ChessMan chessMan = chessmen[chessmanId];
		if (!chessMan.canMoveTo(dst)) {
			String msg = String.format("Cannot move %s%s from %s to %s",
					chessMan.isAlive()? "" : "dead ",
					chessMan.toDebugString(),
					chessMan.getGlobalPosition().toString(),
					dst.toString());
			throw new IllegalStateException(msg);
		}
		GlobalPosition src = chessMan.getGlobalPosition();
		if (board[dst.getX()][dst.getY()] != null) {
			board[dst.getX()][dst.getY()].setKilled();
		}
		board[dst.getX()][dst.getY()] = chessMan;
		if (chessMan.isAlive()) {				// dead chessman might be moved to undo an earlier move.
			board[src.getX()][src.getY()] = null;
		}
		chessMan.moveTo(dst);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				if (board[x][y] != null) {
					sb.append(board[x][y].toString());
				} else {
					sb.append('十');
				}
			}
			sb.append(String.format("%n"));
		}
		return sb.toString();
	}

	public static void main(String[] argv) {
		test();
	}

	private static void test() {
		Playground playground = new Playground();
		System.out.println(playground);
		GlobalPosition dst = new GlobalPosition(4, 1);
		playground.move(0, dst);
		playground.move(31, new GlobalPosition(5, 7));
		System.out.println(playground);
	}
}



