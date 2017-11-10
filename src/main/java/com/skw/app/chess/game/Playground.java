package com.skw.app.chess.game;

public class Playground {
	private ChessMan[][] board;
	private static final int width = 9;
	private static final int height = 10;
	
	public Playground() {
		board = new ChessMan[width][];
		for (int y = 0; y < height; y++) {
			board[y] = new ChessMan[width];
		}
	}
}



