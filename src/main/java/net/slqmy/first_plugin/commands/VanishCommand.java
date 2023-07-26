package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.types.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class VanishCommand extends AbstractCommand {
	private final Main plugin;
	private final List<UUID> vanished = new ArrayList<>();

	public VanishCommand(@NotNull final Main plugin) {
		super(
						"vanish",
						"Disappear forever (or temporarily).",
						"/vanish",
						new Integer[]{0},
						new String[]{"hide", "disappear", "v"},
						"first_plugin.vanish",
						true
		);

		this.plugin = plugin;
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
		final Player player = (Player) sender;
		final UUID playerUUID = player.getUniqueId();

		final Collection<? extends Player> players = Bukkit.getOnlinePlayers();

		if (vanished.contains(playerUUID)) {
			vanished.remove(playerUUID);

			for (final Player target : players) {
				target.showPlayer(plugin, player);
			}

			player.sendMessage(ChatColor.AQUA + "Where did you come from!?");
		} else {
			vanished.add(playerUUID);

			for (final Player target : players) {
				target.hidePlayer(plugin, player);
			}

			player.sendMessage(ChatColor.AQUA + "Where did you go!?");
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
