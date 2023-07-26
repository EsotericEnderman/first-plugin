package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.types.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SetConfigCommand extends AbstractCommand {
	private final Main plugin;
	private final YamlConfiguration config;

	public SetConfigCommand(@NotNull final Main plugin) {
		super(
						"set-config",
						"Set data in the config file!",
						"/set-config <word> <fruit>",
						new Integer[]{2},
						new String[]{"set-settings"},
						"first_plugin.config",
						false
		);

		this.plugin = plugin;
		this.config = (YamlConfiguration) plugin.getConfig();
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		config.set("Word", args[0]);
		final List<String> stringList = config.getStringList("Fruits");
		stringList.add(args[1]);

		config.set("Fruits", stringList);

		plugin.saveConfig();

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
