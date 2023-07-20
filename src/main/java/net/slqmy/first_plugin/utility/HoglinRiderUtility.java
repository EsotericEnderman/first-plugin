package net.slqmy.first_plugin.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.slqmy.first_plugin.FirstPlugin;

public final class HoglinRiderUtility {
	private static final String HOGLIN_NAME = ChatColor.DARK_RED + "The Beast";
	private static final String PIGLIN_NAME = ChatColor.DARK_RED + "Beast Rider";

	@NotNull
	public static Hoglin spawnHoglinRider(@NotNull final World world, @NotNull final Location location) {
		final Hoglin hoglin = (Hoglin) world.spawnEntity(location, EntityType.HOGLIN);
		final PiglinBrute piglin = (PiglinBrute) world.spawnEntity(location, EntityType.PIGLIN_BRUTE);

		Objects.requireNonNull(hoglin.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(800);
		Objects.requireNonNull(hoglin.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(9);
		Objects.requireNonNull(hoglin.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.55F);
		Objects.requireNonNull(hoglin.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK)).setBaseValue(1.5F);
		Objects.requireNonNull(hoglin.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)).setBaseValue(64);

		hoglin.setImmuneToZombification(true);
		hoglin.setIsAbleToBeHunted(false);
		hoglin.setAdult();
		hoglin.setHealth(800.0);
		hoglin.setCustomName(HOGLIN_NAME);
		hoglin.setPersistent(true);

		Objects.requireNonNull(piglin.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(800);
		Objects.requireNonNull(piglin.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.95F);
		Objects.requireNonNull(piglin.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(14);
		Objects.requireNonNull(piglin.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK)).setBaseValue(0.6F);
		Objects.requireNonNull(piglin.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)).setBaseValue(64);
		piglin.setHealth(800.0);

		piglin.setImmuneToZombification(true);
		piglin.setAdult();
		piglin.setCanPickupItems(false);
		piglin.setCustomName(PIGLIN_NAME);
		piglin.setCustomNameVisible(true);
		piglin.setPersistent(true);

		final ItemStack bannerItem = new ItemStack(Material.ORANGE_BANNER);
		final BannerMeta bannerMeta = (BannerMeta) bannerItem.getItemMeta();
		assert bannerMeta != null;

		bannerMeta.addPattern(new org.bukkit.block.banner.Pattern(DyeColor.BLACK, PatternType.SKULL));
		bannerMeta.addPattern(new org.bukkit.block.banner.Pattern(DyeColor.RED, PatternType.GRADIENT));
		bannerMeta.addPattern(new org.bukkit.block.banner.Pattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP));
		bannerMeta.addPattern(new org.bukkit.block.banner.Pattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM));
		bannerMeta.addPattern(new org.bukkit.block.banner.Pattern(DyeColor.BLACK, PatternType.SKULL));

		bannerItem.setItemMeta(bannerMeta);

		Objects.requireNonNull(piglin.getEquipment()).setHelmet(bannerItem);

		hoglin.addPassenger(piglin);
		return hoglin;
	}

	public static boolean canJump(@NotNull final Hoglin hoglin) {
		final BlockFace[] blockFaces = new BlockFace[] { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST,
				BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };

		final List<Block> blocks = new ArrayList<>();

		final Location hoglinLocation = hoglin.getLocation();
		final Vector facingVector = hoglinLocation.getDirection().setY(0).normalize().multiply(2);

		final Block upperCenter = hoglinLocation.add(facingVector).subtract(0.0, 1.0, 0.0).getBlock();
		final Block center = upperCenter.getRelative(BlockFace.DOWN);
		final Block lowerCenter = center.getRelative(BlockFace.DOWN);

		blocks.add(upperCenter);
		blocks.add(center);
		blocks.add(lowerCenter);

		for (final BlockFace face : blockFaces) {
			blocks.add(upperCenter.getRelative(face));
			blocks.add(center.getRelative(face));
			blocks.add(lowerCenter.getRelative(face));
		}

		int airBlocks = 0;

		for (final Block block : blocks) {
			if (!block.getType().isSolid()) {
				airBlocks++;
			}
		}

		return airBlocks >= 22 && hoglin.isOnGround();
	}

	public static void playDeathSound(@NotNull final World world, @NotNull final Location location,
			@NotNull final String mob) {
		if ("HOGLIN".equals(mob)) {
			for (int i = 0; i < 10; i++) {
				world.playSound(location, Sound.ENTITY_HOGLIN_HURT, 1, 1);
			}
		} else if ("PIGLIN".equals(mob)) {
			for (int i = 0; i < 10; i++) {
				world.playSound(location, Sound.ENTITY_PIGLIN_BRUTE_HURT, 1, 1);
			}
		}
	}

	public static void playParticles(@NotNull final FirstPlugin plugin, @Nullable final Entity hoglin) {
		new BukkitRunnable() {
			public void run() {
				if (hoglin != null) {
					final List<String> hoglinRiders = plugin.getConfig().getStringList("HoglinRiders");
					final String hoglinUUID = hoglin.getUniqueId().toString();

					if (hoglinRiders.contains(hoglinUUID)) {
						final Location location = hoglin.getLocation();
						location.add(0.0, hoglin.getHeight() + 1.5, 0.0);

						Objects.requireNonNull(location.getWorld()).spawnParticle(Particle.VILLAGER_ANGRY, location, 1);
					} else if (!hoglinRiders.contains(hoglinUUID)) {
						cancel();
					}
				}
			}
		}.runTaskTimer(plugin, 0L, 5L);
	}

	public static void manageHoglinRiders(@NotNull final FirstPlugin plugin) {
		final List<String> hoglinRiders = plugin.getConfig().getStringList("HoglinRiders");

		for (final String hoglinUUID : hoglinRiders) {
			playParticles(plugin, Bukkit.getEntity(UUID.fromString(hoglinUUID)));
		}
	}

	public static boolean isHoglin(@Nullable final Entity entity) {
		if (entity == null) {
			return false;
		}

		final List<Entity> passengers = entity.getPassengers();

		return entity instanceof Hoglin
				&& entity.getCustomName().equals(HOGLIN_NAME)
				&& !passengers.isEmpty()
				&& passengers.get(0) instanceof PiglinBrute
				&& passengers.get(0).getCustomName().equals(PIGLIN_NAME);
	}

	public static boolean isRider(@Nullable final Entity entity) {
		if (entity == null) {
			return false;
		}

		final Entity vehicle = entity.getVehicle();

		return entity instanceof PiglinBrute
				&& entity.getCustomName().equals(PIGLIN_NAME)
				&& vehicle != null && vehicle instanceof Hoglin
				&& vehicle.getCustomName().equals(HOGLIN_NAME);
	}
}
