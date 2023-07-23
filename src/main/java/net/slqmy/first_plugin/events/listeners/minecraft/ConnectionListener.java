package net.slqmy.first_plugin.events.listeners.minecraft;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.managers.PlayerManager;
import net.slqmy.first_plugin.types.PlayerWrapper;
import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.UUID;

public final class ConnectionListener implements Listener {
	private final Main plugin;
	private final PlayerManager playerManager;

	public ConnectionListener(@NotNull final Main plugin) {
		this.plugin = plugin;
		this.playerManager = plugin.getPlayerManager();
	}

	@EventHandler
	public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();

		final PlayerWrapper playerData;

		try {
			playerData = new PlayerWrapper(plugin, event.getPlayer().getUniqueId());
		} catch (final SQLException exception) {
			Utility.log("There was an error saving player with UUID " + uuid + " to the database!");
			player.kickPlayer(ChatColor.RED + "Sorry! Your data could not be loaded!");

			throw new RuntimeException(exception);
		}

		playerManager.addPlayer(playerData);
	}

	@EventHandler
	public void onPlayerQuit(@NotNull final PlayerQuitEvent event) {
		playerManager.removePlayer(event.getPlayer().getUniqueId());
	}
}
