package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.player;

import dev.enderman.minecraft.plugins.first.FirstPlugin;
import dev.enderman.minecraft.plugins.first.managers.PlayerManager;
import dev.enderman.minecraft.plugins.first.types.PlayerWrapper;
import dev.enderman.minecraft.plugins.first.utility.DebugUtility;
import dev.enderman.minecraft.plugins.first.utility.PacketUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public final class ConnectionListener implements Listener {
	private final FirstPlugin plugin;
	private final PlayerManager playerManager;
	private final Map<UUID, UUID> recentMessages;

	public ConnectionListener(@NotNull final FirstPlugin plugin) {
		this.plugin = plugin;

		this.playerManager = plugin.getPlayerManager();
		this.recentMessages = plugin.getRecentMessages();
	}

	@EventHandler
	public void onJoin(@NotNull final PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		PacketUtility.inject(plugin, player);

		final UUID uuid = player.getUniqueId();

		final PlayerWrapper playerData;

		try {
			playerData = new PlayerWrapper(plugin, event.getPlayer().getUniqueId());
		} catch (final SQLException exception) {
			DebugUtility.logError(exception, "There was an error saving player with UUID " + uuid + " to the database!");
			player.kickPlayer(ChatColor.RED + "Sorry! Your data could not be loaded!");

			return;
		}

		playerManager.addPlayer(playerData);
	}

	@EventHandler
	public void onQuit(@NotNull final PlayerQuitEvent event) {
		final Player player = event.getPlayer();

		playerManager.removePlayer(player.getUniqueId());
		recentMessages.remove(player.getUniqueId());

		PacketUtility.eject(player);
	}
}
