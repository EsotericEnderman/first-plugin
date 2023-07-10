package net.slqmy.first_plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayersCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {

		if (commandSender instanceof Player) {
			((Player)commandSender).sendMessage(ChatColor.RED + "This is a console-only command!");
		} else {
			// ChatColor does not work in console.
			System.out.println(/* ChatColor.LIGHT_PURPLE + */ "There are currently " + Bukkit.getOnlinePlayers().toArray().length + " players online!");
		}

		return false;
	}
}
