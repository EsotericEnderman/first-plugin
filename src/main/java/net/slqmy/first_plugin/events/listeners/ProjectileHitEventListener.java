package net.slqmy.first_plugin.events.listeners;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.FirstPlugin;

public final class ProjectileHitEventListener implements Listener {
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

	private final NamespacedKey isPistolBulletKey;
	private final NamespacedKey isMiniGunBulletKey;
	private final NamespacedKey isGatlingGunBulletKey;
	private final NamespacedKey isPoisonLauncherBulletKey;

	public ProjectileHitEventListener(@NotNull final FirstPlugin plugin) {
		this.isPistolBulletKey = plugin.getIsPistolBulletKey();
		this.isMiniGunBulletKey = plugin.getIsMiniGunBulletKey();
		this.isGatlingGunBulletKey = plugin.getIsGatlingGunBulletKey();
		this.isPoisonLauncherBulletKey = plugin.getIsPoisonLauncherBulletKey();
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

			final double distance = location.distance(playerLocation);

			player.sendMessage(ChatColor.BOLD + "» " + ChatColor.RESET + DECIMAL_FORMAT.format(distance));

			final PersistentDataContainer container = projectile.getPersistentDataContainer();

			if (container.has(isPistolBulletKey, PersistentDataType.BOOLEAN)
					|| container.has(isGatlingGunBulletKey, PersistentDataType.BOOLEAN)) {
				projectile.remove();
			}
		}
	}
}
