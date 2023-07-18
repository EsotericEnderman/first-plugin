package net.slqmy.first_plugin.types;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public enum Rank {
	SLIME_GOD('a', ChatColor.BOLD.toString() + ChatColor.GREEN + "Slime God"),
	ROYAL_SLUDGE('b', ChatColor.DARK_GREEN + "Royal Sludge"),
	BOOSTER('c', ChatColor.LIGHT_PURPLE + "Booster");

	private final char orderLetter;

	private final String displayName;

	Rank(@NotNull final char orderLetter, @NotNull final String displayName) {
		this.orderLetter = orderLetter;
		this.displayName = displayName + " ";
	}

	@NotNull
	public String getDisplayName() {
		return displayName;
	}

	@NotNull
	public char getOrderLetter() {
		return orderLetter;
	}
}
