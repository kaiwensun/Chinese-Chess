package com.skw.app.chess.game;

import java.util.HashMap;
import java.util.Map;

public class ChessRoom {
	private Map<String, Match> matches;
	private Map<String, Player> uuid2players;
	private Map<String, Player> name2players;

	public ChessRoom() {
		matches = new HashMap<>();
		uuid2players = new HashMap<>();
		name2players = new HashMap<>();
	}

	public Player createPlayer(String name, boolean isHost) {
		Player player = new Player(name, isHost);
		synchronized (name2players) {
			if (uuid2players.containsKey(player.getId()) || name2players.containsKey(player.getName())) {
				player = null;
			} else {
				uuid2players.put(player.getId(), player);
				name2players.put(player.getName(), player);
			}
		}
		System.out.println(name2players.keySet());
		System.out.println(uuid2players.keySet());
		return player;
	}

	public Match createMatch(Player host) {
		Match match = new Match(host);
		synchronized (matches) {
			matches.put(match.getMatchId(), match);
		}
		return match;
	}

	public Match joinMatch(Player visitingPlayer, String matchId) {
		if (!matches.containsKey(matchId)) {
			return null;
		}
		Match match = null;
		synchronized (matches) {
			match = matches.get(matchId);
			if (match != null) {
				match.joinGame(visitingPlayer);
			}
		}
		return match;
	}

	public void deleteMatch(String matchId) {
		if (!matches.containsKey(matchId)) {
			return;
		}
		synchronized (matches) {
			matches.remove(matchId);
		}
	}

	public void deletePlayer(String playerId) {
		System.out.println(name2players.keySet());
		System.out.println(uuid2players.keySet());
		if (!uuid2players.containsKey(playerId)) {
			System.out.println(playerId);
			return;
		}
		synchronized (name2players) {
			Player player = uuid2players.get(playerId);
			System.out.println("bbb");
			if (player != null) {
				uuid2players.remove(player.getId());
				name2players.remove(player.getName());
				System.out.println("ccc");
			}
		}
	}
	
	public Match getMatch(String matchId) {
		return matches.get(matchId);
	}
}
