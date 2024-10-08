package dev.esoteric_enderman.first_plugin.commands.minecraft;

import dev.esoteric_enderman.first_plugin.types.AbstractCommand;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public final class PunishCommand extends AbstractCommand {
	public PunishCommand() {
		super(
						"punish",
						"Punish someone, get revenge... >:)",
						"/punish <player name> <kick | ban | temp-ban>",
						new Integer[]{2},
						new String[]{},
						"first_plugin.punish",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final Player player = (Player) sender;

		final String punishedPlayerName = args[0];
		final Player punishedPlayer = Bukkit.getPlayer(punishedPlayerName);

		if (punishedPlayer == null) {
			player.sendMessage("Couldn't find the player!");
			return true;
		}

		final String punishment = args[1];

		switch (punishment.toLowerCase(Locale.ENGLISH)) {
			case "kick":
				punishedPlayer.kickPlayer(ChatColor.RED + "You have been kicked. Bad player!\nStop it, get some help!");
				break;
			case "ban":
				Bukkit.getBanList(BanList.Type.NAME).addBan(punishedPlayer.getName(),
								ChatColor.RED + "You have been banned! Stop being a bad player!\nStop now! Please appeal!", null,
								null);

				punishedPlayer.kickPlayer(ChatColor.RED + "You have been banned!");
				break;
			case "temp-ban":
				final Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR, 12);

				Bukkit.getBanList(BanList.Type.NAME).addBan(punishedPlayer.getName(),
								ChatColor.RED + "You have been banned! Stop being a bad player!\nStop now! Please appeal!",
								calendar.getTime(), null);

				punishedPlayer.kickPlayer(
								ChatColor.RED + "You have been temp-banned! Come back in " + ChatColor.BOLD + 12
												+ ChatColor.RED + " hours.");
				break;
			default:
				player.sendMessage(ChatColor.RED + "Invalid usage!\nUse /punish <player name> <kick | ban | temp-ban>");
				break;
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
