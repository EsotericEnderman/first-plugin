package net.slqmy.first_plugin.events.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerInteractEntityEventListener implements Listener {
	@EventHandler
	public void onPlayerInteractEntity(@NotNull final PlayerInteractEntityEvent event) {
		final Player player = event.getPlayer();

		final Entity rightClicked = event.getRightClicked();

		if (player.isSneaking()) {
			player.addPassenger(rightClicked);
		} else {
			rightClicked.addPassenger(player);
		}
	}
}
