package net.slqmy.first_plugin;

import org.bukkit.ChatColor;

public enum Rank {
	SLIME_GOD('a', ChatColor.BOLD.toString() + ChatColor.GREEN + "Slime God"),
	ROYAL_SLUDGE('b', ChatColor.DARK_GREEN + "Royal Sludge"),
	BOOSTER('c', ChatColor.LIGHT_PURPLE + "Booster");

	private final char orderLetter;
	public char getOrderLetter() { return orderLetter; }

	private final String displayName;
	public String getDisplayName() { return displayName; }

	Rank(char orderLetter, String displayName) {
		this.orderLetter = orderLetter;
		this.displayName = displayName + " ";
	}
}
