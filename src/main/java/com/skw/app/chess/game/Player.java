package com.skw.app.chess.game;

import java.util.UUID;

public class Player {
	private final String name;
	private final UUID id;
	private ChessMan.ChessColor color = null;
	private boolean isHost;

	public Player(String name, boolean isHost) {
		this.name = name;
		this.isHost = isHost;
		this.id = UUID.randomUUID();
	}

	public String getName() {
		return this.name;
	};

	public String getId() {
		return this.id.toString();
	};

	public boolean isHost() {
		return isHost;
	}

	public ChessMan.ChessColor getColor() {
		return color;
	}

	private Object setColorLock = new Object();

	public void setColor(ChessMan.ChessColor color) {
		if (this.color != null) {
			return;
		}
		synchronized (setColorLock) {
			if (this.color == null) {
				this.color = color;
			}
		}
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof Player) && ((Player) other).getId().equals(this.id);
	}

	@Override
	public String toString() {
		return name;
	}
}
