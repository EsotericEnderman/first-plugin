package net.slqmy.first_plugin.commands;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.utility.Utility;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public final class PunishCommand implements CommandExecutor {
	private static final int ARGUMENT_LENGTH = 2;
	private static final int BAN_DURATION_HOURS = 12;

	@Override
	public boolean onCommand(
			@NotNull final CommandSender sender,
			@NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != ARGUMENT_LENGTH || "".equals(Arrays.toString(args).trim())) {
			return false;
		}

		if (sender instanceof Player) {
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
					calendar.add(Calendar.HOUR, BAN_DURATION_HOURS);

					Bukkit.getBanList(BanList.Type.NAME).addBan(punishedPlayer.getName(),
							ChatColor.RED + "You have been banned! Stop being a bad player!\nStop now! Please appeal!",
							calendar.getTime(), null);

					punishedPlayer.kickPlayer(
							ChatColor.RED + "You have been temp-banned! Come back in " + ChatColor.BOLD + BAN_DURATION_HOURS
									+ ChatColor.RED + " hours.");
					break;
				default:
					player.sendMessage(ChatColor.RED + "Invalid usage!\nUse /punish <player name> <kick | ban | temp-ban>");
					break;
			}
		} else {
			Utility.log("/punish is a player-only command!");

			return false;
		}

		return true;
	}
}
