package com.skw.app.chess.game;

public class ChessMan {
	
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

		private static ChessColor id2Color(int id) {
			return id < TOTAL_CHESS_CNT / 2 ? ChessColor.RED : ChessColor.BLACK; 
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
		private static ChessRole id2Role(int id) { 
			int mirrored_id = id % (TOTAL_CHESS_CNT / 2);
			if (mirrored_id == 15) {
				return ChessRole.SHUAI;
			}
			switch (mirrored_id / 2) {
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
			case 5:
			case 6:
			case 7:
				return ChessRole.BING;
			default:
				String msg = String.format("Illegal chessman id %d", id);
				throw new IllegalArgumentException(msg);
			}
		}
	}

	class GlobalPosition {
		private int x;
		private int y;

		public GlobalPosition(int x, int y) {
			update(x, y);
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public void update(int x, int y) {
			validate(x, y);
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return String.format("(%d, %d)", x, y);
		}
		private void validate(int x, int y) {
			if (x < 0 && y < 0) {
				//dead chessman
				return;
			}
			if (x < 0 || x > 8 || y < 0 || y > 9) {
				String msg = String.format("Illegal global position x = %d, y = %d", x, y);
				throw new IllegalArgumentException(msg);
			}
		}
		
	}
	private static final int TOTAL_CHESS_CNT = 32;
	private final ChessColor color;
	private final ChessRole role;
	private final int id;						// 0 to 31 inclusive
	private final GlobalPosition init_global_position; 
	private final GlobalPosition curr_global_position;
	private boolean alive = true;

	public ChessMan(int id) {
		this.id = id;
		this.color = ChessColor.id2Color(id);
		this.role = ChessRole.id2Role(id);
		this.init_global_position = new GlobalPosition(getInitGlobalPositionX(), getInitGlobalPositionY());
		this.curr_global_position = new GlobalPosition(
				init_global_position.getX(),
				init_global_position.getY());
		this.alive = true;
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

	// public getters

	public int getId() {
		return this.id;
	}

	public ChessColor getColor() {
		return this.color;
	}

	public ChessRole getRole() {
		return this.role;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public GlobalPosition getGlobalPosition() {
		return new GlobalPosition(curr_global_position.getX(), curr_global_position.getY());
	}
	
	// public setters
	public void moveTo(int x, int y) {
		if (alive) {
			curr_global_position.update(x, y);
		} else {
			String msg = String.format(
					"Cannot move dead %s to %s",
					toDebugString(),
					new GlobalPosition(x, y).toString());
			throw new IllegalStateException(msg);
		}
	}
	
	public void setKilled() {
		if (!alive) {
			throw new IllegalStateException("The " + toDebugString() + "already dead");
		}
		curr_global_position.update(-1, -1);
		alive = false;
	}
	
	// helpers
	private int getInitGlobalPositionX() {
		int[] MIRRORED_X = new int[] {3, 5, 2, 6, 1, 7, 0, 8, 1, 7, 0, 2, 4, 6, 8, 4};
		int mirrored_id = id % (TOTAL_CHESS_CNT / 2);
		return MIRRORED_X[mirrored_id];
	}

	private int getInitGlobalPositionY() {
		int mirrored_id = id % (TOTAL_CHESS_CNT / 2);
		int[] MIRRORED_Y = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 3, 3, 3, 3, 3, 0};
		if (id == mirrored_id) {
			return MIRRORED_Y[mirrored_id];
		} else {
			return MIRRORED_Y[9 - mirrored_id];
		}
	}
	
	protected String toDebugString() {
		return "" + color + toString() + "(" + id + ")";
	}
}
