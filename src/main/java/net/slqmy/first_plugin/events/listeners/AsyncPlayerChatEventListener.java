package net.slqmy.first_plugin.events.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.FirstPlugin;

public final class AsyncPlayerChatEventListener implements Listener {
	private final Boolean chatEnabled;

	public AsyncPlayerChatEventListener(FirstPlugin plugin) {
		this.chatEnabled = plugin.getChatEnabled();
	}

	@EventHandler
	public void onAsyncPlayerChat(@NotNull final AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		if (Boolean.FALSE.equals(chatEnabled)) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "Chat is disabled!");
		}
	}
}
