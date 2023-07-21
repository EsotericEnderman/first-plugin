package net.slqmy.first_plugin.types;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public abstract class Command extends BukkitCommand {
	public Command(@NotNull final String name, final String description, final String usage, final String[] aliases, final String permission) {
		super(name);

		setDescription(description);
		setUsage(usage);
		setAliases(Arrays.asList(aliases));
		setPermission(permission);
		setPermissionMessage(
						"[" + ChatColor.AQUA + ChatColor.BOLD + "First" + ChatColor.DARK_GRAY + "-" + ChatColor.AQUA + ChatColor.BOLD + "Plugin" + ChatColor.RESET + "] " + ChatColor.RED + "You must have the " + ChatColor.UNDERLINE + permission + ChatColor.RED + " permission to execute this command!"
		);

		try {
			final Server server = Bukkit.getServer();

			final Field field = server.getClass().getDeclaredField("commandMap");
			field.setAccessible(true);

			final CommandMap map = (CommandMap) field.get(server);

			map.register(name, this);
		} catch (NoSuchFieldException | IllegalAccessException exception) {
			exception.printStackTrace();
		}
	}

	public abstract boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args);

	public abstract List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final String[] args);

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String label, @NotNull final String[] args) {
		return execute(sender, args);
	}

	@NotNull
	@Override
	public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
		return onTabComplete(sender, args);
	}
}
