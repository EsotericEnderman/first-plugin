package net.slqmy.first_plugin.events.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.type.Cake;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.FirstPlugin;

public final class PlayerInteractEventListener implements Listener {
	private final List<UUID> movementDisabled;
	private Boolean chatEnabled;

	private final NamespacedKey isPistolBullet;
	private final NamespacedKey isShotgunBullet;
	private final NamespacedKey isMiniGunBullet;
	private final NamespacedKey isGatlingGunBullet;
	private final NamespacedKey isPoisonLauncherBullet;

	public PlayerInteractEventListener(@NotNull final FirstPlugin plugin) {
		this.movementDisabled = plugin.getMovementDisabled();
		this.chatEnabled = plugin.getChatEnabled();

		this.isPistolBullet = plugin.getIsPistolBulletKey();
		this.isShotgunBullet = plugin.getIsShotgunBulletKey();
		this.isMiniGunBullet = plugin.getIsMiniGunBulletKey();
		this.isGatlingGunBullet = plugin.getIsGatlingGunBulletKey();
		this.isPoisonLauncherBullet = plugin.getIsPoisonLauncherBulletKey();
	}

	@EventHandler
	public void onPlayerInteract(@NotNull final PlayerInteractEvent event) {
		final Player player = event.getPlayer();

		final ItemStack mainHand = player.getInventory().getItemInMainHand();
		final ItemMeta mainHandMeta = mainHand.getItemMeta();

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			final BlockState blockState = event.getClickedBlock().getState();
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
		final Vector playerDirection = playerLocation.getDirection();

		if (mainHandMaterial == Material.NETHER_STAR && event.getHand() == EquipmentSlot.HAND) {
			chatEnabled = !chatEnabled;

			player.sendMessage(
					"Chat " + (Boolean.TRUE.equals(chatEnabled) ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
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
		} else if (mainHandMaterial == Material.IRON_HOE && itemName.equals(ChatColor.WHITE + "Mini-gun")) {
			final Snowball snowball = player.launchProjectile(Snowball.class, playerDirection.multiply(5));

			final PersistentDataContainer container = snowball.getPersistentDataContainer();
			container.set(isMiniGunBullet, PersistentDataType.BOOLEAN, true);

			snowball.setFireTicks(300);
			player.spawnParticle(Particle.WHITE_ASH, playerLocation, 10, 1, 1, 1);
		} else if (mainHandMaterial == Material.GOLDEN_HOE && itemName.equals(
				ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Gatling gun")) {
			final Trident trident = player.launchProjectile(Trident.class, playerDirection.multiply(8.5F));

			final PersistentDataContainer container = trident.getPersistentDataContainer();
			container.set(isGatlingGunBullet, PersistentDataType.BOOLEAN, true);

			trident.setFireTicks(300);
			player.spawnParticle(Particle.FLAME, playerLocation, 10, 1, 1, 1);
		} else if (mainHandMaterial == Material.DIAMOND_HOE && itemName.equals(
				ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Poison launcher")) {
			final ItemStack poisonPotion = new ItemStack(Material.LINGERING_POTION);
			final PotionMeta meta = (PotionMeta) poisonPotion.getItemMeta();

			meta.setColor(Color.GREEN);
			meta.setBasePotionData(new PotionData(PotionType.POISON, false, false));

			poisonPotion.setItemMeta(meta);

			final ThrownPotion potion = player.launchProjectile(ThrownPotion.class, playerDirection.multiply(5.15F));

			final PersistentDataContainer container = potion.getPersistentDataContainer();
			container.set(isPoisonLauncherBullet, PersistentDataType.BOOLEAN, true);

			potion.setItem(poisonPotion);

			potion.setFireTicks(300);

			potion.setItem(poisonPotion);
			player.spawnParticle(Particle.SMOKE_LARGE, playerLocation, 10, 1, 1, 1);
		} else if (mainHandMaterial == Material.NETHERITE_HOE && itemName.equals(
				ChatColor.RED.toString() + ChatColor.BOLD + "Rocket launcher")) {
			final Fireball fireball = player.launchProjectile(Fireball.class, playerDirection.multiply(4.2F));

			fireball.setYield(3.6F);
			fireball.setIsIncendiary(true);
			fireball.setFireTicks(300);
			player.spawnParticle(Particle.EXPLOSION_NORMAL, playerLocation, 10, 1, 1, 1);
		}
	}
}
