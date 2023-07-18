package net.slqmy.first_plugin.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.utility.Utility;

public final class PlayerResourcePackStatusEventListener implements Listener {
	@EventHandler
	public void onPlayerResourcePackStatus(@NotNull final PlayerResourcePackStatusEvent event) {
		final Status status = event.getStatus();

		Utility.log("Resource pack status: " + status.name());
	}
}
