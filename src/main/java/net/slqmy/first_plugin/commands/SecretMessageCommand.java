package net.slqmy.first_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SecretMessageCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			if (player.hasPermission("first_plugin.secret_message")) {
				player.sendMessage(ChatColor.GREEN + "The secret message is... 'troll'!");
			} else {
				player.sendMessage(ChatColor.RED + "You don't have permission to see the secret message... get 'troll'-ed!");
			};
		}

		return false;
	}
}
