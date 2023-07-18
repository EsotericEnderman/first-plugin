package net.slqmy.first_plugin.data;

import java.util.Date;

public class Data {
	private String playerName;
	private String message;
	private boolean isBestPlugin = true;
	private Date date;

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
		return isBestPlugin;
	}

	public Date getDate() {
		return date;
	}
}
