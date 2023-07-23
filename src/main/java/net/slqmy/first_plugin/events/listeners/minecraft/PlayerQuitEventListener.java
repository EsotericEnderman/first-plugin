package net.slqmy.first_plugin.events.listeners.minecraft;

import net.slqmy.first_plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public final class PlayerQuitEventListener implements Listener {
	private final Map<UUID, UUID> recentMessages;

	public PlayerQuitEventListener(@NotNull final Main plugin) {
		recentMessages = plugin.getRecentMessages();
	}

	@EventHandler
	public void onPlayerQuit(@NotNull final PlayerQuitEvent event) {
		final Player player = event.getPlayer();

		recentMessages.remove(player.getUniqueId());
	}
}
