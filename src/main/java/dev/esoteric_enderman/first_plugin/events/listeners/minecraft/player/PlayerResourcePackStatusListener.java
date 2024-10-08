package dev.esoteric_enderman.first_plugin.events.listeners.minecraft.player;

import dev.esoteric_enderman.first_plugin.utility.Utility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerResourcePackStatusListener implements Listener {
	@EventHandler
	public void onPlayerResourcePackStatus(@NotNull final PlayerResourcePackStatusEvent event) {
		Utility.log("Resource pack status: " + event.getStatus().name());
	}
}
