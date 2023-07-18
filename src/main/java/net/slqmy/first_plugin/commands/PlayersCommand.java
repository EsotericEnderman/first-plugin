package net.slqmy.first_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.utility.Utility;

public final class PlayersCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof ConsoleCommandSender) {
			// ChatColor does not work in console.
			Utility.log(/* ChatColor.LIGHT_PURPLE + */ "There are currently "
					+ Bukkit.getOnlinePlayers().size() + " players online!");
		} else if (sender instanceof Player) {
			((Player) sender).sendMessage(ChatColor.RED + "This is a console-only command!");

			return false;
		} else {
			Utility.log("/players is a console-only command!");

			return false;
		}

		return true;
	}
}
