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
			int mirroredId = id % (TOTAL_CHESS_CNT / 2);
			if (mirroredId == 15) {
				return ChessRole.SHUAI;
			}
			switch (mirroredId / 2) {
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

	public static final int TOTAL_CHESS_CNT = 32;
	private final ChessColor color;
	private final ChessRole role;
	private final int id;						// 0 to 31 inclusive
	private final GlobalPosition initGlobalPosition; 
	private final GlobalPosition currGlobalPosition;
	private boolean alive = true;

	public ChessMan(int id) {
		this.id = id;
		this.color = ChessColor.id2Color(id);
		this.role = ChessRole.id2Role(id);
		this.initGlobalPosition = new GlobalPosition(getInitGlobalPositionX(), getInitGlobalPositionY());
		this.currGlobalPosition = new GlobalPosition(
				initGlobalPosition.getX(),
				initGlobalPosition.getY());
		this.alive = true;
	}

	@Override
	public String toString() {
		if (!alive) {
			return "X";
		}
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
		case SHUAI:
			return color == ChessColor.RED ? "帥" : "将";
		default:
			return "?";
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
		return new GlobalPosition(currGlobalPosition.getX(), currGlobalPosition.getY());
	}
	
	// public setters
	public boolean canMoveTo(GlobalPosition dst) {
		return alive && isValidPosition(dst);
	}

	public void moveTo(GlobalPosition dst) {
		currGlobalPosition.update(dst.getX(), dst.getY());
	}

	public void setKilled() {
		if (!alive) {
			throw new IllegalStateException("The " + toDebugString() + "already dead");
		}
		alive = false;
	}
	
	// helpers

	public boolean isValidPosition(GlobalPosition position) {
		int px = position.getX();
		int py = position.getY();
		switch (this.role) {
		case SHI:
			if (color == ChessColor.BLACK) {
				py = Playground.height - 1 - py;
			}
			if (py == 0 || py == 2) {
				return px == 3 || px == 5;
			} else if (py == 1) {
				return px == 4;
			} else {
				return false;
			}
		case XIANG:
			if (color == ChessColor.BLACK) {
				py = Playground.height - 1 - py;
			}
			if (py == 0 || py == 4) {
				return px == 2 || px == 6;
			} else if (py == 2) {
				return px == 0 || px == 4 || px == 8;
			} else {
				return false;
			}
		case MA:
		case JU:
		case PAO:
			return true;
		case BING:
			if (color == ChessColor.BLACK) {
				py = Playground.height - 1 - py;
			}
			if (py < 3) {
				return false;
			} else if (py == 3 || py == 4) {
				return px == 0 || px == 2 || px == 4 || px == 6 || px == 8;
			} else {
				return true;
			}
		case SHUAI:
			if (color == ChessColor.BLACK) {
				py = Playground.height - 1 - py;
			}
			return px >= 3 && px <= 5 && py >= 0 && py <= 2;
		default:
			return false;
		}
	}

	private int getInitGlobalPositionX() {
		int[] MIRRORED_X = new int[] {3, 5, 2, 6, 1, 7, 0, 8, 1, 7, 0, 2, 4, 6, 8, 4};
		int mirroredId = id % (TOTAL_CHESS_CNT / 2);
		return MIRRORED_X[mirroredId];
	}

	private int getInitGlobalPositionY() {
		int mirroredId = id % (TOTAL_CHESS_CNT / 2);
		int[] MIRRORED_Y = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 3, 3, 3, 3, 3, 0};
		if (id == mirroredId) {
			return MIRRORED_Y[mirroredId];
		} else {
			return Playground.height - 1 - MIRRORED_Y[mirroredId];
		}
	}
	
	protected String toDebugString() {
		return "" + color + toString() + "(" + id + ")";
	}
}

/**
 * Global position of chessman. In red player's view, bottom-left is (0, 0), a red JU.
 * The chess board's indexing is also using this rule.
 * The position should never be out of the chess board.
 * @author ksun
 *
 */
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

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GlobalPosition)) {
			return false;
		}
		GlobalPosition otherPosi = (GlobalPosition) other;
		return this.x == otherPosi.getX() && this.y == otherPosi.getY();
	}

	private void validate(int x, int y) {
		if (x < 0 || x > Playground.width - 1 || y < 0 || y > Playground.height - 1) {
			String msg = String.format("Illegal global position x = %d, y = %d", x, y);
			throw new IllegalArgumentException(msg);
		}
	}
}
