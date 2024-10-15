package dev.enderman.minecraft.plugins.first.types;

import dev.enderman.minecraft.plugins.first.utility.NumberUtility;
import dev.enderman.minecraft.plugins.first.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand extends BukkitCommand {
	private final String name;
	private final String usage;
	private final List<Integer> argLengths;
	private final List<String> aliases;
	private final String permission;
	private final boolean playerOnly;

	protected AbstractCommand(@NotNull final String name, final String description, final String usage, final Integer[] argLengths, final String[] aliases, final String permission, final boolean playerOnly) {
		super(name);
		this.name = name;

		setDescription(description);

		setUsage(usage);
		this.usage = usage;
		this.argLengths = Arrays.asList(argLengths);

		setAliases(Arrays.asList(aliases));
		this.aliases = Arrays.asList(aliases);

		setPermission(permission);
		this.permission = permission;

		setPermissionMessage(
						"[" + ChatColor.AQUA + ChatColor.BOLD + "First" + ChatColor.DARK_GRAY + "-" + ChatColor.AQUA + ChatColor.BOLD
										+ "Plugin" + ChatColor.RESET + "] " + ChatColor.RED + "You must have the " + ChatColor.UNDERLINE
										+ permission + ChatColor.RED + " permission to execute this command!");

		this.playerOnly = playerOnly;

		try {
			final Server server = Bukkit.getServer();

			final Field field = server.getClass().getDeclaredField("commandMap");
			field.setAccessible(true);

			final CommandMap map = (CommandMap) field.get(server);

			map.register(name, this);
		} catch (final NoSuchFieldException | IllegalAccessException exception) {
			Utility.log("There was an error loading command " + name + "!");
			Utility.log(exception.getMessage());
			exception.printStackTrace();
			Utility.log(exception);
		}
	}

	public abstract boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args);

	@Nullable
	public abstract List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final String[] args);

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String label,
	                       @NotNull final String[] args) {
		if (playerOnly && !(sender instanceof Player)) {
			Utility.log("/" + name + " is a player-only command!");
			return true;
		}

		if (
						(!argLengths.contains(args.length) || (args.length != 0 && "".equals(Arrays.toString(args).trim())))
										&& (NumberUtility.min(argLengths) >= 0 || args.length >= -NumberUtility.min(argLengths))) {
			if (sender instanceof Player) {
				sender.sendMessage(
								" \n" + ChatColor.RED + "Invalid command usage!\nPlease use " + ChatColor.UNDERLINE + usage + ChatColor.RED
												+ "!\n \n"
												+ ChatColor.RESET + ChatColor.UNDERLINE + "/" + name + "\n"
												+ ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + description + "\n"
												+ ChatColor.DARK_GRAY + "• " + ChatColor.RESET + "Usage: " + ChatColor.GRAY + usage + "\n"
												+ ChatColor.DARK_GRAY + "• " + ChatColor.RESET + "Aliases: " + ChatColor.GRAY
												+ String.join(", ", aliases) + "\n"
												+ ChatColor.DARK_GRAY + "• " + ChatColor.RESET + "Permission: " + ChatColor.GRAY + permission + "\n ");

			} else {
				Utility.log("Invalid command usage! Please use " + usage + "!");
				Utility.log("/" + name);
				Utility.log("• " + description);
				Utility.log("• Usage: " + usage);
				Utility.log("• Aliases: " + String.join(", ", aliases));
				Utility.log("• Permission: " + permission);

			}

			return true;
		}

		return execute(sender, args);
	}

	@NotNull
	@Override
	public List<String> tabComplete(@NotNull final CommandSender sender, @NotNull final String alias,
	                                @NotNull final String @NotNull [] args) {
		if (args.length <= NumberUtility.absMax(argLengths) && (!playerOnly || sender instanceof Player)) {
			final List<String> results = onTabComplete(sender, args);

			return results == null ? new ArrayList<>()
							: StringUtil.copyPartialMatches(args[args.length - 1], results, new ArrayList<>());
		}

		return new ArrayList<>();
	}
}
