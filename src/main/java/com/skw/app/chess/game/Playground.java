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
		for (int chessman_id = 0; chessman_id < ChessMan.TOTAL_CHESS_CNT; chessman_id++) {
			ChessMan chessman = new ChessMan(chessman_id);
			GlobalPosition globalPosition = chessman.getGlobalPosition();
			chessmen[chessman_id] = chessman;
			board[globalPosition.getX()][globalPosition.getY()] = chessman;
		}
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
	}
}



