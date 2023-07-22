package net.slqmy.first_plugin.events.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.Main;

public final class AsyncPlayerChatEventListener implements Listener {
	private final Main plugin;

	public AsyncPlayerChatEventListener(@NotNull final Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onAsyncPlayerChat(@NotNull final AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		if (!plugin.isChatEnabled()) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "Chat is disabled!");
		}
	}
}
