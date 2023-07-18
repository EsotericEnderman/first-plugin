package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.Utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class VanishCommand implements CommandExecutor {
	private final List<UUID> vanished = new ArrayList<>();
	private final FirstPlugin firstPlugin;

	public VanishCommand(final FirstPlugin firstPlugin) {
		this.firstPlugin = firstPlugin;
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;
			final UUID playerUUID = player.getUniqueId();

			final Collection<? extends Player> players = Bukkit.getOnlinePlayers();

			if (vanished.contains(playerUUID)) {
				vanished.remove(playerUUID);

				for (final Player target : players) {
					target.showPlayer(firstPlugin, player);
				}

				player.sendMessage(ChatColor.AQUA + "Where did you come from!?");
			} else {
				vanished.add(playerUUID);

				for (final Player target : players) {
					target.hidePlayer(firstPlugin, player);
				}

				player.sendMessage(ChatColor.AQUA + "Where did you go!?");
			}
		} else {
			Utility.log("/vanish is a player-only command!");

			return false;
		}

		return true;
	}
}
