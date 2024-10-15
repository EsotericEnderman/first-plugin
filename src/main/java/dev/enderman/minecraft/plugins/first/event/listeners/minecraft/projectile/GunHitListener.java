package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.projectile;

import dev.enderman.minecraft.plugins.first.FirstPlugin;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class GunHitListener implements Listener {
	private final BlockData REDSTONE_BLOCK_DATA = Material.REDSTONE_BLOCK.createBlockData();

	private final NamespacedKey isShotgunBulletKey;
	private final NamespacedKey isPistolBulletKey;
	private final NamespacedKey isMiniGunBulletKey;
	private final NamespacedKey isGatlingGunBulletKey;

	public GunHitListener(@NotNull final FirstPlugin plugin) {
		isPistolBulletKey = plugin.getIsPistolBulletKey();
		isShotgunBulletKey = plugin.getIsShotgunBulletKey();
		isMiniGunBulletKey = plugin.getIsMiniGunBulletKey();
		isGatlingGunBulletKey = plugin.getIsGatlingGunBulletKey();
	}

	@EventHandler
	public void onEntityDamageByEntity(@NotNull final EntityDamageByEntityEvent event) {
		final Entity damagedEntity = event.getEntity();
		final Entity damagerEntity = event.getDamager();

		if (damagedEntity instanceof LivingEntity) {
			final LivingEntity damaged = (LivingEntity) damagedEntity;
			final double health = damaged.getHealth();

			final World world = damaged.getWorld();
			final Location location = damaged.getLocation();

			final PersistentDataContainer container = damagerEntity.getPersistentDataContainer();

			if (container.has(isPistolBulletKey, PersistentDataType.BOOLEAN)) {

				damaged.setHealth(Math.max(health - 10, 0));
				world.spawnParticle(Particle.BLOCK, location, 2, REDSTONE_BLOCK_DATA);
			} else if (container.has(isShotgunBulletKey, PersistentDataType.BOOLEAN)) {

				damaged.setHealth(Math.max(health - 24, 0));
				world.spawnParticle(Particle.BLOCK, location, 5, REDSTONE_BLOCK_DATA);
			} else if (container.has(isMiniGunBulletKey, PersistentDataType.BOOLEAN)) {

				damaged.setHealth(Math.max(health - 14, 0));
				world.spawnParticle(Particle.BLOCK, location, 2, REDSTONE_BLOCK_DATA);
			} else if (container.has(isGatlingGunBulletKey, PersistentDataType.BOOLEAN)) {

				damaged.setHealth(Math.max(health - 14.5F, 0));
				world.spawnParticle(Particle.BLOCK, location, 6, REDSTONE_BLOCK_DATA);
			}
		}
	}
}
