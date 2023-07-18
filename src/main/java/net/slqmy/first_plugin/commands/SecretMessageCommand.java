package net.slqmy.first_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.utility.Utility;

public final class SecretMessageCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			if (player.hasPermission("first_plugin.secret_message")) {
				player.sendMessage(
						ChatColor.GREEN + "The secret message is... " + ChatColor.BOLD + "'troll'" + ChatColor.GREEN + "!");
			} else {
				player.sendMessage(ChatColor.RED + "You don't have permission to see the secret message... get "
						+ ChatColor.BOLD + "'troll'" + ChatColor.RED + "-ed!");
			}
		} else {
			Utility.log("/secret-message is a player-only command!");

			return false;
		}

		return true;
	}
}
