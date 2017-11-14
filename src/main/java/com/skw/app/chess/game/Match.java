package com.skw.app.chess.game;

import java.util.Random;
import java.util.UUID;

import com.skw.app.chess.game.ChessMan.ChessColor;

public class Match {
    private static Random rg = new Random();
    private Player[] players = new Player[2];
    private UUID roomId;
    private Playground playground;
    private int currPlayerIndex = -1;

    private Object joinGameLock = new Object();

    public Match(Player hostPlayer) {
        players[0] = hostPlayer;
        roomId = UUID.randomUUID();
    }

    // getters
    public String getRoomId() {
        return roomId.toString();
    }

    public int getCurrPlayerIndex() {
        return currPlayerIndex;
    }

    private Player getPlayerByPlayerId(String playerId) {
        for (int index = 0; index < 2; index++) {
            if (players[index] != null && players[index].getId().equals(playerId)) {
                return players[index];
            }
        }
        return null;
    }

    // actions
    private void startGame() {
        playground = new Playground();
        boolean hostFirst = rg.nextBoolean();
        players[0].setColor(hostFirst ? ChessColor.RED : ChessColor.BLACK);
        players[1].setColor(hostFirst ? ChessColor.BLACK : ChessColor.RED);
        currPlayerIndex = hostFirst ? 0 : 1;
    }

    public boolean joinGame(Player visitingPlayer) {
        if (!players[0].equals(visitingPlayer)) {
            return false;
        }
        if (players[1] != null) {
            return false;
        } else {
            synchronized (joinGameLock) {
                if (players[1] != null) {
                    return false;
                } else {
                    players[1] = visitingPlayer;
                }
            }
        }
        startGame();
        return true;
    }

    public void move(String playerId, int chessmanId, GlobalPosition position) {
        Player player = getPlayerByPlayerId(playerId);
        ChessMan chessMan = playground.getChessMan(chessmanId);
        if (!chessMan.getColor().equals(player.getColor())) {
            String msg = String.format("%s player %s is not allowed to move %s", player.getColor(), player.toString(),
                    chessMan.toDebugString());
            throw new IllegalChessMoveException(msg);
        }
    }
}
