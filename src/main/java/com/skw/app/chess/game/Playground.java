package com.skw.app.chess.game;

import java.util.Map;

public class Playground {
	
}


class ChessMan {
	private static final int TOTAL_CHESS_CNT = 32;
	private static final ChessMan[] ALL_CHESS_MEN = null;
	
	enum ChessColor {
		RED("红"), BLACK("黑");
		private String color;
		
		private ChessColor(String color) {
			this.color = color;
		}
		
		@Override
		public String toString() {
			return this.color;
		}
		
		public static ChessColor id2Color(int id) {
			return id < TOTAL_CHESS_CNT/2 ? ChessColor.RED : ChessColor.BLACK; 
		}
	}

	enum ChessRole {
		SHI, XIANG, MA, JU, PAO, BING, SHUAI;
		
		/**
		 * Convert id to ChessRole. From 0 to 15, the roles are
		 * [SHI, SHI, XIANG, XIANG, MA, MA, JU, JU, PAO, PAO, BING, BING, BING, BING, BING, SHUAI].
		 * Same with 16 to 31.
		 * @param id 0 to 31
		 * @return ChessRole
		 */
		public static ChessRole id2Role(int id) { 
			id = id % (TOTAL_CHESS_CNT/2);
			if (id == 15) {
				return ChessRole.SHUAI;
			}
			int halfId = id / 2;
			switch (halfId) {
			case 0:
				return ChessRole.SHI;
			case 1:
				return ChessRole.XIANG;
			case 2:
				return ChessRole.MA;
			case 3:
				return ChessRole.JU;
			case 4:
				return ChessRole.PAO;
			default:
				return ChessRole.BING;
			}
		}
	}
	
	private ChessColor color;
	private ChessRole role;
	private int id;				// 0 to 31 inclusive

	public ChessMan(int id) {
		this.id = id;
		this.color = ChessColor.id2Color(id);
		this.role = ChessRole.id2Role(id);
	}
	
	@Override
	public String toString() {
		switch (role) {
		case SHI:
			return color == ChessColor.RED ? "仕" : "士";
		case XIANG:
			return color == ChessColor.RED ? "相" : "象";
		case MA:
			return color == ChessColor.RED ? "傌" : "馬";
		case JU:
			return color == ChessColor.RED ? "俥" : "車";
		case PAO:
			return color == ChessColor.RED ? "炮" : "砲";
		case BING:
			return color == ChessColor.RED ? "兵" : "卒";
		default:
			return "X";
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public ChessColor getColor() {
		return this.color;
	}
	
	public ChessRole getRole() {
		return this.role;
	}
}

