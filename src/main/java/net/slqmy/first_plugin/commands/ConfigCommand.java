package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.types.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ConfigCommand extends AbstractCommand {
	private final YamlConfiguration config;

	public ConfigCommand(@NotNull final Main plugin) {
		super(
						"config",
						"View the server config. How interesting...",
						"/config",
						new Integer[]{0},
						new String[]{"configuration", "settings"},
						"first_plugin.config",
						true
		);

		config = (YamlConfiguration) plugin.getConfig();
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
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

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
