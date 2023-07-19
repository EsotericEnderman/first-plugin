package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FruitCommand implements CommandExecutor, TabCompleter {
	private static final int ARGUMENT_LENGTH = 2;

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != ARGUMENT_LENGTH || "".equals(Arrays.toString(args).trim())) {
			return false;
		}

		if (sender instanceof Player) {
			sender.sendMessage(ChatColor.RED + "Sorry, this command doesn't do anything lol.");
		} else {
			Utility.log("/fruit is a player-only command!");

			return false;
		}

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label, @NotNull final String[] args) {
		final List<String> results = new ArrayList<>();

		if (sender instanceof Player) {
			switch (args.length) {
				case 1:
					results.add("Pear");
					results.add("Apple");
					results.add("Grape");
					results.add("Orange");

					return StringUtil.copyPartialMatches(args[0], results, new ArrayList<>());
				case ARGUMENT_LENGTH:
					for (final Player player : Bukkit.getOnlinePlayers()) {
						results.add(player.getName());
					}

					return StringUtil.copyPartialMatches(args[1], results, new ArrayList<>());
				default:
					break;
			}
		}

		return results;
	}
}
