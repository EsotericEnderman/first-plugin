package net.slqmy.first_plugin.events.listeners;

import net.minecraft.world.entity.projectile.EntityFishingHook;
import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.Utility;
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
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProjectileLaunchEventListener implements Listener {
	private static final Pattern entityPattern = Pattern.compile("[A-Z_]+$");
	private static final Pattern velocityPattern = Pattern.compile("[\\d.]+$");
	private static final DustTransition DUST_TRANSITION_RED = new DustTransition(Color.RED, Color.RED, 0.65F);
	private static final DustTransition DUST_TRANSITION_LIME = new DustTransition(Color.LIME, Color.LIME, 0.65F);
	private static final DustTransition DUST_TRANSITION_BLUE = new DustTransition(Color.BLUE, Color.BLUE, 0.65F);
	private static final DustTransition DUST_TRANSITION_BLACK = new DustTransition(Color.BLACK, Color.BLACK, 0.65F);

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
				event.setCancelled(true);

				final PlayerInventory playerInventory = player.getInventory();

				final ItemStack mainHand = playerInventory.getItemInMainHand();
				final ItemStack offHand = playerInventory.getItemInOffHand();

				final ItemMeta mainHandMeta = mainHand.getItemMeta();
				final ItemMeta offHandMeta = offHand.getItemMeta();

				ItemMeta meta = null;

				if (mainHandMeta != null && mainHandMeta.getDisplayName().equals("Non-Parabolic Snowball")) {
					meta = mainHandMeta;
				} else if (offHandMeta != null && offHandMeta.getDisplayName().equals("Non-Parabolic Snowball")) {
					meta = offHandMeta;
				}

				if (meta != null) {
					final List<String> lore = meta.getLore();
					assert lore != null;

					final Matcher entityMatcher = entityPattern.matcher(lore.get(1));
					entityMatcher.find();

					final EntityType entity = EntityType.valueOf(entityMatcher.group(0));

					final Location currentLocation = projectile.getLocation();
					final World currentWorld = projectile.getWorld();

					player.setCollidable(false);
					final Entity newProjectile = currentWorld.spawnEntity(currentLocation, entity);

					final double DRAG;
					final double GRAVITY;

					if (newProjectile instanceof LivingEntity) {
						GRAVITY = 0.08D;
						DRAG = 0.02D;
					} else if (newProjectile instanceof FallingBlock || newProjectile instanceof TNTPrimed || newProjectile instanceof Item) {
						GRAVITY = 0.04D;
						DRAG = 0.02D;
					} else if (newProjectile instanceof Minecart) {
						GRAVITY = 0.04D;
						DRAG = 0.05D;
					} else if (newProjectile instanceof Boat) {
						GRAVITY = 0.04D;
						DRAG = 0D;
					} else if (newProjectile instanceof Egg || newProjectile instanceof Snowball || newProjectile instanceof ThrownPotion || newProjectile instanceof EnderPearl) {
						GRAVITY = 0.03D;
						DRAG = 0.01D;
					} else if (newProjectile instanceof ExperienceOrb) {
						GRAVITY = 0.03D;
						DRAG = 0.02D;
					} else if (newProjectile instanceof EntityFishingHook) {
						GRAVITY = 0.03D;
						DRAG = 0.08D;
					} else if (newProjectile instanceof LlamaSpit) {
						GRAVITY = 0.06D;
						DRAG = 0.01D;
					} else if (newProjectile instanceof Arrow || newProjectile instanceof Trident) {
						GRAVITY = 0.05D;
						DRAG = 0.01D;
					} else if (newProjectile instanceof Fireball) {
						GRAVITY = 0.1D;
						DRAG = 0.05D;
					} else {
						GRAVITY = 0D;
						DRAG = 0D;
					}

					final Matcher velocityMatcher = velocityPattern.matcher(lore.get(2));
					velocityMatcher.find();

					final float velocity = Float.parseFloat(velocityMatcher.group(0));

					newProjectile.setVelocity(projectile.getVelocity().normalize().multiply(velocity));

					if (!player.isSneaking()) {
						newProjectile.addPassenger(player);
					}

					final double y_0 = currentLocation.getY();

					final Vector v_0 = newProjectile.getVelocity();

					final double v_0_y = v_0.getY();

					final double x = v_0.getX();
					final double z = v_0.getZ();

					final double v_0_x = Math.sqrt(x * x + z * z);
					final int[] ticksPassed = {0};
					final int[] yPredictionAccuracies = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
					final int[] xPredictionAccuracies = { 0, 0 };

					new BukkitRunnable() {
						@Override
						public void run() {
							final Location location = newProjectile.getLocation();
							final World world = location.getWorld();
							assert world != null;

							final double drag1 = Math.pow(1D - DRAG, ticksPassed[0]);
							final double drag2 = Math.pow(1D - DRAG, ticksPassed[0] + 1);

							final double estimatedXVelocity1 = v_0_x * drag1;
							final double estimatedXVelocity2 = v_0_x * drag2;

							final Vector v_t = newProjectile.getVelocity();

							final double v_t_x = v_t.getX();
							final double v_t_z = v_t.getZ();

							final double actualXVelocity = Math.sqrt(v_t_x * v_t_x + v_t_z * v_t_z);

							final double errorX1 = estimatedXVelocity1 - actualXVelocity;
							final double errorX2 = estimatedXVelocity2 - actualXVelocity;

							xPredictionAccuracies[errorX2 > errorX1 ? 0 : 1]++;

							final double estimatedYVelocity1 = v_0_y * drag1 - GRAVITY * ((1 - drag1) / DRAG) * (1 - DRAG); // From Minecraft Wiki.
							final double estimatedYVelocity2 = v_0_y * drag2 - GRAVITY * ((1 - drag2) / DRAG) * (1 - DRAG); // From Minecraft Wiki.
							final double estimatedYVelocity3 = v_0_y * drag1 - GRAVITY * ((1 - drag2) / DRAG) * (1 - DRAG);
							final double estimatedYVelocity4 = v_0_y * drag2 - GRAVITY * ((1 - drag1) / DRAG) * (1 - DRAG);
							final double estimatedYVelocity5 = (v_0_y - GRAVITY) * drag1 - GRAVITY * ((1 - drag1) / DRAG) * (1 - DRAG); // From Minecraft Wiki.
							final double estimatedYVelocity6 = (v_0_y - GRAVITY) * drag2 - GRAVITY * ((1 - drag2) / DRAG) * (1 - DRAG);
							final double estimatedYVelocity7 = (v_0_y - GRAVITY) * drag1 - GRAVITY * ((1 - drag2) / DRAG) * (1 - DRAG);
							final double estimatedYVelocity8 = (v_0_y - GRAVITY) * drag2 - GRAVITY * ((1 - drag1) / DRAG) * (1 - DRAG);
							final double estimatedYVelocity9 = (v_0_y - GRAVITY) * drag1;
							final double estimatedYVelocity10 = (v_0_y - GRAVITY) * drag2;

							final double actualYVelocity = v_t.getY();

							final double errorY1 = estimatedYVelocity1 - actualYVelocity;
							final double errorY2 = estimatedYVelocity2 - actualYVelocity;
							final double errorY3 = estimatedYVelocity3 - actualYVelocity;
							final double errorY4 = estimatedYVelocity4 - actualYVelocity;
							final double errorY5 = estimatedYVelocity5 - actualYVelocity;
							final double errorY6 = estimatedYVelocity6 - actualYVelocity;
							final double errorY7 = estimatedYVelocity7 - actualYVelocity;
							final double errorY8 = estimatedYVelocity8 - actualYVelocity;
							final double errorY9 = estimatedYVelocity9 - actualYVelocity;
							final double errorY10 = estimatedYVelocity10 - actualYVelocity;

							final List<Double> errors = Arrays.asList(
											errorY1,
											errorY2,
											errorY3,
											errorY4,
											errorY5,
											errorY6,
											errorY7,
											errorY8,
											errorY9,
											errorY10
							);

							final double smallestError = Utility.minAbs(errors);

							yPredictionAccuracies[errors.indexOf(smallestError)]++;

							ticksPassed[0]++;

							final double x_t = location.getX();
							final double z_t = location.getZ();

							new BukkitRunnable() {
								@Override
								public void run() {
									world.spawnParticle(Particle.DUST_COLOR_TRANSITION, location, 1, DUST_TRANSITION_RED);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorY3 * 25, z_t),
													1,
													DUST_TRANSITION_LIME
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorX1 * 25, z_t),
													1,
													DUST_TRANSITION_BLUE
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0, z_t),
													1,
													DUST_TRANSITION_BLACK
									);

									if (newProjectile.isDead() || newProjectile.isOnGround()) {
										cancel();
									}
								}
							}.runTaskTimer(plugin, 0, 0);

							if (newProjectile.isDead() ||newProjectile.isOnGround()) {
								cancel();

								player.sendMessage(ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + "X Prediction Accuracies: " + Arrays.toString(xPredictionAccuracies));
								player.sendMessage(ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + "Y Prediction Accuracies: " + Arrays.toString(yPredictionAccuracies));
							}
						}
					}.runTaskTimer(plugin, 0, 0);
				}
			}
		}
	}
}
