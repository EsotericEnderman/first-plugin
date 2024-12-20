package dev.enderman.minecraft.plugins.first.commands.minecraft;

import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import dev.enderman.minecraft.plugins.first.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class PlayersCommand extends AbstractCommand {
	public PlayersCommand() {
		super(
						"players",
						"(Console-only) get the number of online players.",
						"/players",
						new Integer[]{0},
						new String[]{"player-count", "pc"},
						"first_plugin.players",
						false
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		if (sender instanceof ConsoleCommandSender) {
			final int playerCount = Bukkit.getOnlinePlayers().size();

			// ChatColor does not work in console.
			Utility.log(/* ChatColor.LIGHT_PURPLE + */ "There " + (playerCount == 1 ? "is" : "are") + " currently "
							+ playerCount + " player" + (playerCount == 1 ? "" : "s") + " online!");
		} else if (sender instanceof Player) {
			sender.sendMessage(ChatColor.RED + "This is a console-only command!");

			return false;
		} else {
			Utility.log("/players is a console-only command!");

			return false;
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
