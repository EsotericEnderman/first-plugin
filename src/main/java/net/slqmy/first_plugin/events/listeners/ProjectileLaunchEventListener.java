package net.slqmy.first_plugin.events.listeners;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;

public final class ProjectileLaunchEventListener implements Listener {
	@EventHandler
	public void onProjectileLaunch(@NotNull final ProjectileLaunchEvent event) {
		final Projectile projectile = event.getEntity();
		final ProjectileSource projectileSource = projectile.getShooter();

		if (projectileSource instanceof Player) {
			final Player player = (Player) projectileSource;

			if (projectile instanceof EnderPearl) {
				if (player.isInsideVehicle()) {
					final Entity vehicle = player.getVehicle();

					if (vehicle instanceof EnderPearl) {
						vehicle.remove();
					}
				}

				projectile.addPassenger(player);
			}
		}
	}
}
