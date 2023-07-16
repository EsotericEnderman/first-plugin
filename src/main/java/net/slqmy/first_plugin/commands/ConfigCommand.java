package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.FirstPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfigCommand implements CommandExecutor {
	private final FirstPlugin firstPlugin;

	public ConfigCommand(FirstPlugin firstPlugin) {
		this.firstPlugin = firstPlugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player)commandSender;

			player.sendMessage(firstPlugin.getConfig().getString("Word"));
			player.sendMessage(String.valueOf(firstPlugin.getConfig().getInt("Number")));

			if (firstPlugin.getConfig().getBoolean("Boolean")) {
				player.sendMessage(ChatColor.GREEN + "true");
			} else {
				player.sendMessage(ChatColor.DARK_RED + "false");
			}

			List<String> stringList = firstPlugin.getConfig().getStringList("Fruits");

			for (String string : stringList) {
				player.sendMessage(string);
			}

			firstPlugin.getConfig();
		}

		return false;
	}
}
