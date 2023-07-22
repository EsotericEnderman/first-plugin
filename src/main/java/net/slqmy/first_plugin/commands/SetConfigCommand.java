package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public final class SetConfigCommand implements CommandExecutor {
	private static final int ARGUMENT_LENGTH = 2;

	private final Main plugin;
	private final YamlConfiguration config;

	public SetConfigCommand(@NotNull final Main plugin) {
		this.plugin = plugin;
		this.config = (YamlConfiguration) plugin.getConfig();
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != ARGUMENT_LENGTH || "".equals(Arrays.toString(args).trim())) {
			return false;
		}

		config.set("Word", args[0]);
		final List<String> stringList = config.getStringList("Fruits");
		stringList.add(args[1]);

		config.set("Fruits", stringList);

		plugin.saveConfig();

		return true;
	}
}
