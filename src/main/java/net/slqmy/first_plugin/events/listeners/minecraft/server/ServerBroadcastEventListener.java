package net.slqmy.first_plugin.events.listeners.minecraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class ServerBroadcastEventListener implements Listener {
	@EventHandler
	public void onServerBroadcast(@NotNull final ServerBroadcastEvent event) {
		final String message = event.getMessage();
		final Player player = event.getPlayer();

		if ("".equals(message.trim())) {
			event.setCancelled(true);

			player.sendMessage(ChatColor.RED + "Please enter a message to broadcast!");
		} else if ("troll".equalsIgnoreCase(message)) {
			event.setCancelled(true);

			player.sendMessage(ChatColor.RED + "Nope! Can't say that!");
		}
	}
}
