package dev.esoteric_enderman.first_plugin.events.listeners.minecraft.projectile;

import dev.esoteric_enderman.first_plugin.FirstPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class ProjectileHitListener implements Listener {
	private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

	private final NamespacedKey isPistolBulletKey;
	private final NamespacedKey isGatlingGunBulletKey;

	public ProjectileHitListener(@NotNull final FirstPlugin plugin) {
		isPistolBulletKey = plugin.getIsPistolBulletKey();
		isGatlingGunBulletKey = plugin.getIsGatlingGunBulletKey();
	}

	@EventHandler
	public void onProjectileHit(@NotNull final ProjectileHitEvent event) {
		final Projectile projectile = event.getEntity();

		final ProjectileSource projectileSource = projectile.getShooter();
		final Location location = projectile.getLocation();

		if (projectileSource instanceof Player) {
			final Player player = (Player) projectileSource;

			final Location playerLocation = player.getLocation();

			final double x = location.getX();
			final double y = location.getY();
			final double z = location.getZ();

			DECIMAL_FORMAT.setRoundingMode(RoundingMode.CEILING);

			player.sendMessage(
							ChatColor.BOLD + "→" + ChatColor.RESET + " (" + ChatColor.RED + ChatColor.BOLD + DECIMAL_FORMAT.format(x)
											+ ChatColor.RESET
											+ ", " + ChatColor.GREEN + ChatColor.BOLD + DECIMAL_FORMAT.format(y) + ChatColor.RESET
											+ ", " + ChatColor.BLUE + ChatColor.BOLD + DECIMAL_FORMAT.format(z) + ChatColor.RESET
											+ ") "
											+ ChatColor.AQUA + DECIMAL_FORMAT.format(projectile.getVelocity().length()) + ChatColor.RESET + " m/s");

			final World world = location.getWorld();
			assert world != null;

			if (world.equals(player.getWorld())) {
				final double distance = location.distance(playerLocation);

				player.sendMessage(ChatColor.BOLD + "» " + ChatColor.RESET + DECIMAL_FORMAT.format(distance));
			}

			final PersistentDataContainer container = projectile.getPersistentDataContainer();

			if (container.has(isPistolBulletKey, PersistentDataType.BOOLEAN)
							|| container.has(isGatlingGunBulletKey, PersistentDataType.BOOLEAN)) {
				projectile.remove();
			}
		}
	}
}
