package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.FirstPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public final class SetConfigCommand implements CommandExecutor {
	private static final int ARGUMENT_LENGTH = 2;

	private final FirstPlugin firstPlugin;

	public SetConfigCommand(@NotNull final FirstPlugin firstPlugin) {
		this.firstPlugin = firstPlugin;
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != ARGUMENT_LENGTH || "".equals(Arrays.toString(args).trim())) {
			return false;
		}

		firstPlugin.getConfig().set("Word", args[0]);
		final List<String> stringList = firstPlugin.getConfig().getStringList("Fruits");
		stringList.add(args[1]);

		firstPlugin.getConfig().set("Fruits", stringList);

		firstPlugin.saveConfig();

		return true;
	}
}
