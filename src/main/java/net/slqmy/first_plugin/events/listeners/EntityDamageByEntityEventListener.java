package net.slqmy.first_plugin.events.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.FirstPlugin;

public final class EntityDamageByEntityEventListener implements Listener {
  private static final BlockData REDSTONE_BLOCK_DATA = Material.REDSTONE_BLOCK.createBlockData();

  private final NamespacedKey isShotgunBulletKey;
  private final NamespacedKey isPistolBulletKey;
  private final NamespacedKey isMiniGunBulletKey;
  private final NamespacedKey isGatlingGunBulletKey;

  public EntityDamageByEntityEventListener(@NotNull final FirstPlugin plugin) {
    this.isPistolBulletKey = plugin.getIsPistolBulletKey();
    this.isShotgunBulletKey = plugin.getIsShotgunBulletKey();
    this.isMiniGunBulletKey = plugin.getIsMiniGunBulletKey();
    this.isGatlingGunBulletKey = plugin.getIsGatlingGunBulletKey();
  }

  @EventHandler
  public void onEntityDamageByEntity(@NotNull final EntityDamageByEntityEvent event) {
    final Entity damagedEntity = event.getEntity();
    final Entity damagerEntity = event.getDamager();

    if (damagedEntity instanceof Damageable) {
      final Damageable damageable = (Damageable) damagedEntity;
      final double health = damageable.getHealth();

      final World world = damageable.getWorld();
      final Location location = damageable.getLocation();

      final PersistentDataContainer container = damagerEntity.getPersistentDataContainer();

      if (container.has(isPistolBulletKey, PersistentDataType.BOOLEAN)) {

        damageable.setHealth(Math.max(health - 10, 0));
        world.spawnParticle(Particle.BLOCK_CRACK, location, 2, REDSTONE_BLOCK_DATA);
      } else if (container.has(isShotgunBulletKey, PersistentDataType.BOOLEAN)) {

        damageable.setHealth(Math.max(health - 24, 0));
        world.spawnParticle(Particle.BLOCK_CRACK, location, 5, REDSTONE_BLOCK_DATA);
      } else if (container.has(isMiniGunBulletKey, PersistentDataType.BOOLEAN)) {

        damageable.setHealth(Math.max(health - 14, 0));
        world.spawnParticle(Particle.BLOCK_CRACK, location, 2, REDSTONE_BLOCK_DATA);
      } else if (container.has(isGatlingGunBulletKey, PersistentDataType.BOOLEAN)) {

        damageable.setHealth(Math.max(health - 14.5F, 0));
        world.spawnParticle(Particle.BLOCK_CRACK, location, 6, REDSTONE_BLOCK_DATA);
      }
    }
  }
}
