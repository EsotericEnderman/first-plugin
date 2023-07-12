package net.slqmy.first_plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NametagManager {
	public static void setNametags(Player player, Scoreboard scoreboard) {
		player.setScoreboard(scoreboard);

		for (Rank rank : Rank.values()) {
			Team team = scoreboard.registerNewTeam(rank.getOrderLetter() + rank.getDisplayName());

			team.setPrefix(ChatColor.translateAlternateColorCodes(ChatColor.COLOR_CHAR, rank.getDisplayName()));
		}
	}

	public static void createNewTag(Player player) {
		Rank rank;

		switch (player.getName()) {
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

		for (Player target : Bukkit.getOnlinePlayers()) {
			target.getScoreboard().getTeam(rank.getOrderLetter() + rank.getDisplayName()).addEntry(player.getName());
		}
	}

	public static void removeTag(Player player) {
		for (Player target : Bukkit.getOnlinePlayers()) {
			target.getScoreboard().getEntryTeam(player.getName()).removeEntry(player.getName());
		}
	}
}
