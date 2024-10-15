package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.player;

import dev.enderman.minecraft.plugins.first.FirstPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public final class ChatListener implements Listener {
	private final FirstPlugin plugin;

	public ChatListener(@NotNull final FirstPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onChat(@NotNull final AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		if (!plugin.isChatEnabled()) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "Chat is disabled!");
		}
	}
}
