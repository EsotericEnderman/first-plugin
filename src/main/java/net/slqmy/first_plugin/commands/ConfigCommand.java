package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.utility.Utility;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ConfigCommand implements CommandExecutor {
	private final YamlConfiguration config;

	public ConfigCommand(@NotNull final Main plugin) {
		config = (YamlConfiguration) plugin.getConfig();
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			player.sendMessage("Word: " + ChatColor.BLACK + config.getString("Word"));
			player.sendMessage("Number: " + ChatColor.DARK_BLUE + config.getInt("Number"));

			if (config.getBoolean("Boolean")) {
				player.sendMessage("Boolean: " + ChatColor.GREEN + "true");
			} else {
				player.sendMessage("Boolean: " + ChatColor.DARK_RED + "false");
			}

			final List<String> stringList = config.getStringList("Fruits");

			for (final String string : stringList) {
				player.sendMessage(ChatColor.DARK_GREEN + string);
			}
		} else {
			Utility.log("/config is a player-only command!");

			return false;
		}

		return true;
	}
}
