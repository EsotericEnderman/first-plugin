package dev.esoteric_enderman.first_plugin.data;

import java.util.Date;

public class Data {
	private final String playerName;
	private final String message;
	private final Date date;

	public Data(String playerName, String message, Date date) {
		this.playerName = playerName;
		this.message = message;
		this.date = date;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getMessage() {
		return message;
	}

	public boolean isBestPlugin() {
		return true;
	}

	public Date getDate() {
		return date;
	}
}
