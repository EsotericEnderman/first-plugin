package net.slqmy.first_plugin.events.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.events.custom_events.ServerBroadcastEvent;

public final class ServerBroadcastEventListener implements Listener {
	@EventHandler
	public void onServerBroadcast(@NotNull final ServerBroadcastEvent event) {
		if ("troll".equalsIgnoreCase(event.getMessage())) {
			event.setCancelled(true);

			event.getPlayer().sendMessage(ChatColor.RED + "Nope! Can't say that!");
		}
	}
}
