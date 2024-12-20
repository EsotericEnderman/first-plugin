package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerInteractEntityListener implements Listener {
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
