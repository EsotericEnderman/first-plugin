package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.events.ServerBroadcastEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BroadcastCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (args.length == 0) {
				player.sendMessage(ChatColor.RED + "You must provide a message to broadcast!");
			} else {
				StringBuilder builder = new StringBuilder();

				for (String arg : args) {
					builder.append(arg).append(" ");
				}

				String message = builder.toString();

				ServerBroadcastEvent event = new ServerBroadcastEvent(player, message);
				Bukkit.getPluginManager().callEvent(event);

				if (!event.isCancelled()) {
					Bukkit.broadcastMessage(message);

					if (args[0].equalsIgnoreCase("hello")) {
						player.sendMessage(ChatColor.AQUA + "Hello to you too!");
					}
				}
			}
		}

		return false;
	}
}
