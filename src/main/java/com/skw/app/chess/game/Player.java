package com.skw.app.chess.game;

import java.util.UUID;

public class Player {
    private final String name;
    private final UUID id;

    public Player(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }
    public String getName() {
        return this.name;
    };
    public UUID getId() {
        return this.id;
    };
}
