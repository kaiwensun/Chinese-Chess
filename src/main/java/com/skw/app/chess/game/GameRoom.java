package com.skw.app.chess.game;

public class GameRoom {
    private Player[] players = new Player[2];

    public GameRoom(Player hostPlayer) {
        players[0] = hostPlayer;
    }

    public boolean joinGame(Player visitingPlayer) {
        if (players[1] != null) {
return false;
        } else {
            players[1] = visitingPlayer;
            return true;
        }
    }
}

