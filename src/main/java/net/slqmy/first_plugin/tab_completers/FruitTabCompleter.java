package net.slqmy.first_plugin.tab_completers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FruitTabCompleter implements TabCompleter {
	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {
		if (commandSender instanceof Player) {
			List<String> results = new ArrayList();

			if (arguments.length == 1) {
				results.add("Pear");
				results.add("Apple");
				results.add("Grape");
				results.add("Orange");

				return StringUtil.copyPartialMatches(arguments[0], results, new ArrayList<>());
			} else if (arguments.length == 2) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					results.add(player.getName());
				}

				return StringUtil.copyPartialMatches(arguments[1], results, new ArrayList<>());
			}

			//      results.sort((a, b) -> {
			//				if ((arguments.length == 1 && a.startsWith(arguments[0].trim())) || (arguments.length == 2 && a.startsWith(arguments[1].trim()))) {
			//					return -1;
			//				}
			//
			//				if ((arguments.length == 1 && b.startsWith(arguments[0].trim())) || (arguments.length == 2 && b.startsWith(arguments[1].trim()))) {
			//					return 1;
			//				}
			//
			//				return 0;
			//			});

			//	return results;
		}

		return null;
	}
}
