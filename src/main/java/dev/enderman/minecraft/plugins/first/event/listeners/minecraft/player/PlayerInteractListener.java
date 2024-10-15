package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.player;

import dev.enderman.minecraft.plugins.first.FirstPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Cake;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public final class PlayerInteractListener implements Listener {
	private static final Particle.DustTransition DUST_TRANSITION_GREEN = new Particle.DustTransition(Color.GREEN, Color.GREEN, 0.85F);
	private static final PotionEffect[] poisonLauncherEffects = {
					new PotionEffect(PotionEffectType.POISON, PotionEffect.INFINITE_DURATION, 1, true, true, true),
					new PotionEffect(PotionEffectType.INSTANT_DAMAGE, PotionEffect.INFINITE_DURATION, 1, true, true, true),
					new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 1, true, true),
					new PotionEffect(PotionEffectType.DARKNESS, PotionEffect.INFINITE_DURATION, 1, true, true),
					new PotionEffect(PotionEffectType.HUNGER, PotionEffect.INFINITE_DURATION, 99, true, true),
					new PotionEffect(PotionEffectType.SLOWNESS, PotionEffect.INFINITE_DURATION, 1, true, true),
					new PotionEffect(PotionEffectType.MINING_FATIGUE, PotionEffect.INFINITE_DURATION, 1, true, true),
					new PotionEffect(PotionEffectType.WEAKNESS, PotionEffect.INFINITE_DURATION, 1, true, true),
					new PotionEffect(PotionEffectType.WITHER, PotionEffect.INFINITE_DURATION, 1, true, true)
	};

	private final FirstPlugin plugin;

	private final List<UUID> movementDisabled;

	private final NamespacedKey isPistolBullet;
	private final NamespacedKey isShotgunBullet;
	private final NamespacedKey isMiniGunBullet;
	private final NamespacedKey isGatlingGunBullet;

	public PlayerInteractListener(@NotNull final FirstPlugin plugin) {
		this.plugin = plugin;

		this.movementDisabled = plugin.getMovementDisabled();

		this.isPistolBullet = plugin.getIsPistolBulletKey();
		this.isShotgunBullet = plugin.getIsShotgunBulletKey();
		this.isMiniGunBullet = plugin.getIsMiniGunBulletKey();
		this.isGatlingGunBullet = plugin.getIsGatlingGunBulletKey();
	}

	@EventHandler
	public void onPlayerInteract(@NotNull final PlayerInteractEvent event) {
		final Player player = event.getPlayer();

		final ItemStack mainHand = player.getInventory().getItemInMainHand();
		final ItemMeta mainHandMeta = mainHand.getItemMeta();

		final Block rightClickedBlocked = event.getClickedBlock();
		if (rightClickedBlocked != null) {
			final BlockState blockState = rightClickedBlocked.getState();
			final BlockData blockData = blockState.getBlockData();

			if (blockData instanceof Cake) {
				final Cake cake = (Cake) blockData;

				final int bites = cake.getBites();

				if (bites == cake.getMaximumBites() - 1) {
					cake.setBites(0);
				}
			}

			blockState.setBlockData(blockData);
			blockState.update();
		}

		if (mainHandMeta == null) {
			return;
		}

		final String itemName = mainHandMeta.getDisplayName();
		final Material mainHandMaterial = mainHand.getType();

		final Location playerLocation = player.getLocation();
		final Vector playerDirection = playerLocation.getDirection().normalize();

		if (mainHandMaterial == Material.NETHER_STAR && event.getHand() == EquipmentSlot.HAND) {
			boolean chatEnabled = plugin.isChatEnabled();
			chatEnabled = !chatEnabled;

			plugin.setChatEnabled(chatEnabled);

			player.sendMessage(
							"Chat " + (chatEnabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled")
											+ ChatColor.RESET + "!");
		} else if (mainHandMaterial == Material.LEVER) {
			final UUID playerUUID = player.getUniqueId();

			if (movementDisabled.contains(playerUUID)) {
				movementDisabled.remove(playerUUID);
			} else {
				movementDisabled.add(playerUUID);
			}
		} else if (mainHandMaterial == Material.WOODEN_HOE && itemName.equals(ChatColor.GOLD + "Pistol")) {
			final Arrow arrow = player.launchProjectile(Arrow.class, playerDirection.multiply(6.5F));

			final PersistentDataContainer container = arrow.getPersistentDataContainer();
			container.set(isPistolBullet, PersistentDataType.BOOLEAN, true);

			player.spawnParticle(Particle.CRIT, playerLocation, 10, 1, 1, 1);
		} else if (mainHandMaterial == Material.STONE_HOE && itemName.equals(ChatColor.BLUE + "Shotgun")) {
			final Egg egg = player.launchProjectile(Egg.class, playerDirection.multiply(4.75F));

			final PersistentDataContainer container = egg.getPersistentDataContainer();
			container.set(isShotgunBullet, PersistentDataType.BOOLEAN, true);

			egg.setFireTicks(300);
			player.spawnParticle(Particle.LAVA, playerLocation, 10, 1, 1, 1);
		} else if (mainHandMaterial == Material.IRON_HOE && itemName.equals(ChatColor.WHITE + "Mini-Gun")) {
			final Snowball snowball = player.launchProjectile(Snowball.class, playerDirection.multiply(5));

			final PersistentDataContainer container = snowball.getPersistentDataContainer();
			container.set(isMiniGunBullet, PersistentDataType.BOOLEAN, true);

			snowball.setFireTicks(300);
			player.spawnParticle(Particle.WHITE_ASH, playerLocation, 10, 1, 1, 1);
		} else if (mainHandMaterial == Material.GOLDEN_HOE && itemName.equals(
						ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Gatling Gun")) {
			final Trident trident = player.launchProjectile(Trident.class, playerDirection.multiply(8.5F));

			final PersistentDataContainer container = trident.getPersistentDataContainer();
			container.set(isGatlingGunBullet, PersistentDataType.BOOLEAN, true);

			trident.setFireTicks(300);
			player.spawnParticle(Particle.FLAME, playerLocation, 10, 1, 1, 1);
		} else if (mainHandMaterial == Material.DIAMOND_HOE && itemName.equals(
						ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Poison Launcher")) {
			final ItemStack poisonPotion = new ItemStack(Material.LINGERING_POTION);
			final PotionMeta meta = (PotionMeta) poisonPotion.getItemMeta();

			assert meta != null;

			meta.setColor(Color.fromRGB(0x4E9331));

			meta.setBasePotionData(new PotionData(PotionType.POISON, false, false));
			for (final PotionEffect effect : poisonLauncherEffects) {
				meta.addCustomEffect(effect, true);
			}

			poisonPotion.setItemMeta(meta);

			final ThrownPotion potion = player.launchProjectile(ThrownPotion.class, playerDirection.multiply(1.55F));

			potion.setItem(poisonPotion);

			potion.setFireTicks(300);

			potion.setItem(poisonPotion);

			new BukkitRunnable() {
				@Override
				public void run() {
					new BukkitRunnable() {
						final Location location = potion.getLocation();

						@Override
						public void run() {
							potion.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, location, 1, DUST_TRANSITION_GREEN);

							if (potion.isDead()) {
								cancel();
							}
						}
					}.runTaskTimer(plugin, 0, 0);

					if (potion.isDead()) {
						cancel();
					}
				}
			}.runTaskTimer(plugin, 0, 0);

			player.spawnParticle(Particle.LARGE_SMOKE, playerLocation, 10, 1, 1, 1);
		} else if (mainHandMaterial == Material.NETHERITE_HOE && itemName.equals(
						ChatColor.RED.toString() + ChatColor.BOLD + "Rocket Launcher")) {
			final Fireball fireball = player.launchProjectile(Fireball.class, playerDirection.multiply(4.2F));

			fireball.setYield(4.2F);
			fireball.setIsIncendiary(true);
			fireball.setFireTicks(300);
			player.spawnParticle(Particle.EXPLOSION, playerLocation, 10, 1, 1, 1);
		}
	}
}
