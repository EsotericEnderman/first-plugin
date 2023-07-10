package net.slqmy.first_plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BroadcastCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player)commandSender;

			if (arguments.length != 1) {
				player.sendMessage(ChatColor.RED + "You must provide a message to broadcast!");
			} else {
				Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);

				for (Player onlinePlayer : onlinePlayers) {
					onlinePlayer.sendMessage(arguments[0]);
				}

				if (arguments[0].equalsIgnoreCase("hello")) {
					player.sendMessage(ChatColor.AQUA + "Hello to you too!");
				}
			}
		}

		return false;
	}
}
