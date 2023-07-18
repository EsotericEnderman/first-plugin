package net.slqmy.first_plugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.types.Rank;

public final class NametagManager {
	public static void setNametags(@NotNull final Player player, @NotNull final Scoreboard scoreboard) {
		player.setScoreboard(scoreboard);

		for (final Rank rank : Rank.values()) {
			final String rankDisplayName = rank.getDisplayName();

			final Team team = scoreboard.registerNewTeam(rank.getOrderLetter() + rankDisplayName);

			team.setPrefix(ChatColor.translateAlternateColorCodes(ChatColor.COLOR_CHAR, rankDisplayName));
		}
	}

	public static void createNewTag(@NotNull final Player player) {
		final String playerName = player.getName();
		final Rank rank;

		switch (playerName) {
			case "Slqmy":
				rank = Rank.SLIME_GOD;

				break;
			case "rolyPolyVole":
				rank = Rank.ROYAL_SLUDGE;

				break;
			default:
				rank = Rank.BOOSTER;

				break;
		}

		final char rankOrderLetter = rank.getOrderLetter();
		final String rankDisplayName = rank.getDisplayName();

		for (final Player target : Bukkit.getOnlinePlayers()) {
			target.getScoreboard().getTeam(rankOrderLetter + rankDisplayName).addEntry(playerName);
		}
	}

	public static void removeTag(@NotNull final Player player) {
		final String playerName = player.getName();

		for (final Player target : Bukkit.getOnlinePlayers()) {
			final Team targetTeam = target.getScoreboard().getEntryTeam(playerName);

			if (targetTeam != null) {
				targetTeam.removeEntry(playerName);
			}
		}
	}
}
