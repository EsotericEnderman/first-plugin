package dev.esoteric_enderman.first_plugin.commands.minecraft;

import dev.esoteric_enderman.first_plugin.types.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class FruitCommand extends AbstractCommand {

	public FruitCommand() {
		super(
						"fruit",
						"A command for testing autocomplete - it doesn't do anything.",
						"/fruit <fruit> <player name>",
						new Integer[]{2},
						new String[]{"autocomplete", "ac"},
						"first_plugin.fruit",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
		sender.sendMessage(ChatColor.RED + "Sorry, this command doesn't do anything lol.");

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final List<String> results = new ArrayList<>();

		switch (args.length) {
			case 1:
				results.add("Pear");
				results.add("Apple");
				results.add("Grape");
				results.add("Orange");

				return StringUtil.copyPartialMatches(args[0], results, new ArrayList<>());
			case 2:
				for (final Player player : Bukkit.getOnlinePlayers()) {
					results.add(player.getName());
				}

				return StringUtil.copyPartialMatches(args[1], results, new ArrayList<>());
			default:
				break;
		}

		return results;
	}
}
