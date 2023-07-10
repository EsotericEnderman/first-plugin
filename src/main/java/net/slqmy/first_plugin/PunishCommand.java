package net.slqmy.first_plugin;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class PunishCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			if (arguments.length != 2) {
				player.sendMessage(ChatColor.RED + "You must provide 2 arguments for this command to work!\nUse /punish <player name> <kick | ban | temp-ban>");
			} else {
				String punishedPlayerName = arguments[0];

				Player punishedPlayer = Bukkit.getPlayer(punishedPlayerName);

				if (punishedPlayer == null) {
					player.sendMessage("Couldn't find the player!");
				} else {
					String punishment = arguments[1];

					switch (punishment.toLowerCase()) {
						case "kick":
							punishedPlayer.kickPlayer(ChatColor.RED + "You have been kicked. Bad player!\nStop it, get some help!");

							break;
						case "ban":
							Bukkit.getBanList(BanList.Type.NAME).addBan(punishedPlayer.getName(), ChatColor.RED + "You have been banned! Stop being a bad player!\nStop now! Please appeal!", null, null);

							punishedPlayer.kickPlayer(ChatColor.RED + "You have been banned!");

							break;
						case "temp-ban":
							Calendar calendar = Calendar.getInstance();
							calendar.add(Calendar.HOUR, 12);

							Bukkit.getBanList(BanList.Type.NAME).addBan(punishedPlayer.getName(), ChatColor.RED + "You have been banned! Stop being a bad player!\nStop now! Please appeal!", calendar.getTime(), null);

							punishedPlayer.kickPlayer(ChatColor.RED + "You have been temp-banned! Come back in 12 hours.");

							break;
						default:
							player.sendMessage(ChatColor.RED + "Invalid usage!\nUse /punish <player name> <kick | ban | temp-ban>");
					}
				}
			}
		}

		return false;
	}
}
