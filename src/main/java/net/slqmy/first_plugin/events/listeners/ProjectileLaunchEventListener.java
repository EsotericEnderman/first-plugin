package net.slqmy.first_plugin.events.listeners;

import net.slqmy.first_plugin.FirstPlugin;
import org.bukkit.*;
import org.bukkit.Particle.DustTransition;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class ProjectileLaunchEventListener implements Listener {
	private static final DustTransition DUST_TRANSITION = new DustTransition(Color.RED, Color.RED, 1);

	private final FirstPlugin plugin;

	public ProjectileLaunchEventListener(@NotNull final FirstPlugin plugin) {
		this.plugin = plugin;
	}

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
			} else if (projectile instanceof Snowball) {
				final PlayerInventory playerInventory = player.getInventory();

				final ItemStack mainHand = playerInventory.getItemInMainHand();
				final ItemStack offHand = playerInventory.getItemInOffHand();

				final ItemMeta mainHandMeta = mainHand.getItemMeta();
				final ItemMeta offHandMeta = offHand.getItemMeta();

				if ((mainHandMeta != null && mainHandMeta.getDisplayName().equals("Non-Parabolic Snowball")) || (offHandMeta != null && offHandMeta.getDisplayName().equals("Non-Parabolic Snowball"))) {
					projectile.setVelocity(projectile.getVelocity().multiply(2F));

					if (!player.isSneaking()) {
						projectile.addPassenger(player);
					}

					new BukkitRunnable() {
						@Override
						public void run() {
							final Location location = projectile.getLocation();
							final World world = location.getWorld();
							assert world != null;

							new BukkitRunnable() {
								@Override
								public void run() {
									world.spawnParticle(Particle.DUST_COLOR_TRANSITION, location, 1, DUST_TRANSITION);
									if (projectile.isDead()) {
										cancel();
									}
								}
							}.runTaskTimer(plugin, 0, 1);

							if (projectile.isDead()) {
								cancel();
							}
						}
					}.runTaskTimer(plugin, 0, 1);
				}
			}
		}
	}
}
