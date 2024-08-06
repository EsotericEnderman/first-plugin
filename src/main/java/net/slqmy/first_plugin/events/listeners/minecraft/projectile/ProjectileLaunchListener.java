package net.slqmy.first_plugin.events.listeners.minecraft.projectile;

import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.NumberUtility;
import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.*;
import org.bukkit.Particle.DustTransition;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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

public final class ProjectileLaunchListener implements Listener {
	private final Pattern entityPattern = Pattern.compile("[A-Z_]+$");
	private final Pattern velocityPattern = Pattern.compile("[\\d.]+$");
	private final DustTransition DUST_TRANSITION_RED = new DustTransition(Color.RED, Color.RED, 0.85F);
	private final DustTransition DUST_TRANSITION_MAROON = new DustTransition(Color.MAROON, Color.MAROON, 0.85F);
	private final DustTransition DUST_TRANSITION_ORANGE = new DustTransition(Color.ORANGE, Color.ORANGE, 0.85F);
	private final DustTransition DUST_TRANSITION_LIME = new DustTransition(Color.LIME, Color.LIME, 0.85F);
	private final DustTransition DUST_TRANSITION_GREEN = new DustTransition(Color.GREEN, Color.GREEN, 0.85F);
	private final DustTransition DUST_TRANSITION_NAVY = new DustTransition(Color.NAVY, Color.NAVY, 0.85F);
	private final DustTransition DUST_TRANSITION_TEAL = new DustTransition(Color.TEAL, Color.TEAL, 0.85F);
	private final DustTransition DUST_TRANSITION_BLUE = new DustTransition(Color.BLUE, Color.BLUE, 0.85F);
	private final DustTransition DUST_TRANSITION_AQUA = new DustTransition(Color.AQUA, Color.AQUA, 0.85F);
	private final DustTransition DUST_TRANSITION_FUCHSIA = new DustTransition(Color.FUCHSIA, Color.FUCHSIA, 0.85F);
	private final DustTransition DUST_TRANSITION_PURPLE = new DustTransition(Color.PURPLE, Color.PURPLE, 0.85F);

	private final DustTransition DUST_TRANSITION_SILVER = new DustTransition(Color.SILVER, Color.SILVER, 1.25F);

	private final FirstPlugin plugin;

	public ProjectileLaunchListener(@NotNull final FirstPlugin plugin) {
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
			} else if (projectile instanceof Snowball || projectile instanceof FishHook) {
				final PlayerInventory playerInventory = player.getInventory();

				final ItemStack mainHand = playerInventory.getItemInMainHand();
				final ItemStack offHand = playerInventory.getItemInOffHand();

				final ItemMeta mainHandMeta = mainHand.getItemMeta();
				final ItemMeta offHandMeta = offHand.getItemMeta();

				ItemMeta meta = null;

				if (mainHandMeta != null && mainHandMeta.getDisplayName().startsWith("Non-Parabolic ")) {
					meta = mainHandMeta;
				} else if (offHandMeta != null && offHandMeta.getDisplayName().startsWith("Non-Parabolic ")) {
					meta = offHandMeta;
				}

				if (meta != null) {
					if (projectile instanceof Snowball) {
						event.setCancelled(true);
					}

					final Location currentLocation = projectile.getLocation();
					final World currentWorld = projectile.getWorld();
					currentLocation.add(0, 5, 0);

					final Entity newProjectile;

					final List<String> lore = meta.getLore();

					if (projectile instanceof Snowball) {
						final Matcher entityMatcher = entityPattern.matcher(lore.get(1));
						entityMatcher.find();

						final EntityType entity = EntityType.valueOf(entityMatcher.group(0));

						newProjectile = currentWorld.spawnEntity(currentLocation, entity);
					} else {
						newProjectile = projectile;
					}

					player.setCollidable(false);

					final double DRAG_X;
					final double DRAG_Y;
					final double GRAVITY;

					if (newProjectile instanceof LivingEntity) {
						GRAVITY = 0.08D;
						DRAG_X = 0.02D;
						DRAG_Y = 0.02D;

						final AttributeInstance speed = ((LivingEntity) newProjectile).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
						assert speed != null;

						speed.setBaseValue(0);
					} else if (newProjectile instanceof FallingBlock || newProjectile instanceof TNTPrimed || newProjectile instanceof Item) {
						GRAVITY = 0.04D;
						DRAG_X = 0.02D;
						DRAG_Y = 0.02D;
					} else if (newProjectile instanceof Minecart) {
						GRAVITY = 0.04D;
						DRAG_X = 0.05D;
						DRAG_Y = 0.05D;
					} else if (newProjectile instanceof Boat) {
						GRAVITY = 0.04D;
						DRAG_X = 0.05D;
						DRAG_Y = 4.9E-324D;
					} else if (newProjectile instanceof Egg || newProjectile instanceof Snowball || newProjectile instanceof ThrownPotion || newProjectile instanceof EnderPearl) {
						GRAVITY = 0.03D;
						DRAG_X = 0.01D;
						DRAG_Y = 0.01D;
					} else if (newProjectile instanceof ExperienceOrb) {
						GRAVITY = 0.03D;
						DRAG_X = 0.02D;
						DRAG_Y = 0.02D;
					} else if (newProjectile instanceof FishHook) {
						GRAVITY = 0.03D;
						DRAG_X = 0.08D;
						DRAG_Y = 0.08D;
					} else if (newProjectile instanceof LlamaSpit) {
						GRAVITY = 0.06D;
						DRAG_X = 0.05D;
						DRAG_Y = 0.01D;
					} else if (newProjectile instanceof Arrow || newProjectile instanceof Trident) {
						GRAVITY = 0.05D;
						DRAG_X = 0.01D;
						DRAG_Y = 0.01D;
					} else if (newProjectile instanceof WitherSkull && ((WitherSkull) newProjectile).isCharged()) {
						GRAVITY = 0D;
						DRAG_X = 0.27D;
						DRAG_Y = 0.27D;
					} else if (newProjectile instanceof Fireball) {
						GRAVITY = 0D;
						DRAG_X = 0.05D;
						DRAG_Y = 0.05D;
					} else {
						GRAVITY = 0.04D;
						DRAG_X = 0.02D;
						DRAG_Y = 0.02D;
					}

					if (projectile instanceof Snowball) {
						final Matcher velocityMatcher = velocityPattern.matcher(lore.get(2));
						velocityMatcher.find();

						final float velocity = Float.parseFloat(velocityMatcher.group(0));

						newProjectile.setVelocity(projectile.getVelocity().normalize().multiply(velocity));
					}

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

					final int[] xPredictionAccuracies = {0, 0};
					final Double[] xPredictionErrorAverages = {0D, 0D, 0D};

					final int[] yPredictionAccuracies = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
					final Double[] yPredictionErrorAverages = {0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D};

					final Double[] zPredictionErrorAverages = {0D};

					final double[] estimatedYVelocity = {v_0_y};
					final double[] estimatedXVelocity = {x};
					final double[] estimatedZVelocity = {z};

					new BukkitRunnable() {
						@Override
						public void run() {
							if (newProjectile.isDead() || newProjectile.isOnGround()) {
								newProjectile.remove();

								player.sendMessage(ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + "X Prediction Accuracies: " + Arrays.toString(xPredictionAccuracies));

								for (int i = 0; i < xPredictionErrorAverages.length; i++) {
									xPredictionErrorAverages[i] /= ticksPassed[0];
								}

								player.sendMessage(ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + "X Prediction Error Averages: " + Arrays.toString(xPredictionErrorAverages));


								player.sendMessage(ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + "Y Prediction Accuracies: " + Arrays.toString(yPredictionAccuracies));

								for (int i = 0; i < yPredictionErrorAverages.length; i++) {
									yPredictionErrorAverages[i] /= ticksPassed[0];
								}

								player.sendMessage(ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + "Y Prediction Error Averages: " + Arrays.toString(yPredictionErrorAverages));

								player.sendMessage(ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + "X Drag Value Used: " + ChatColor.YELLOW + ChatColor.UNDERLINE + DRAG_X);
								player.sendMessage(ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + "Y Drag Value Used: " + ChatColor.YELLOW + ChatColor.UNDERLINE + DRAG_Y);

								player.sendMessage(ChatColor.DARK_GRAY + "• " + ChatColor.GRAY + "Gravity Value Used: " + ChatColor.YELLOW + ChatColor.UNDERLINE + GRAVITY);

								Utility.log("Absolute X Prediction Error Average: " + (xPredictionErrorAverages[2] / ticksPassed[0]));
								Utility.log("Absolute Y Prediction Error Average: " + (yPredictionErrorAverages[15] / ticksPassed[0]));
								Utility.log("Absolute Z Prediction Error Average: " + (zPredictionErrorAverages[0] / ticksPassed[0]));

								player.setCollidable(true);
								cancel();
								return;
							}

							final Location location = newProjectile.getLocation();
							final World world = location.getWorld();
							assert world != null;

							final double yDrag1 = Math.pow(1D - DRAG_Y, ticksPassed[0]);
							final double yDrag2 = Math.pow(1D - DRAG_Y, ticksPassed[0] + 1);

							final double xDrag1 = Math.pow(1D - DRAG_X, ticksPassed[0]);
							final double xDrag2 = Math.pow(1D - DRAG_X, ticksPassed[0] + 1);

							final double estimatedXVelocity1 = v_0_x * xDrag1;
							final double estimatedXVelocity2 = v_0_x * xDrag2;

							final Vector v_t = newProjectile.getVelocity();

							final double v_t_x = v_t.getX();
							final double v_t_z = v_t.getZ();

							final double actualXVelocity = Math.sqrt(v_t_x * v_t_x + v_t_z * v_t_z);

							final double errorX1 = estimatedXVelocity1 - actualXVelocity;
							final double errorX2 = estimatedXVelocity2 - actualXVelocity;

							xPredictionAccuracies[Math.abs(errorX2) > Math.abs(errorX1) ? 0 : 1]++;

							xPredictionErrorAverages[0] += Math.abs(errorX1);
							xPredictionErrorAverages[1] += Math.abs(errorX2);

							final double estimatedYVelocity1 = v_0_y * yDrag1 - GRAVITY * ((1 - yDrag1) / DRAG_Y) * (1 - DRAG_Y); // From Minecraft Wiki. Equation 1.
							final double estimatedYVelocity2 = v_0_y * yDrag2 - GRAVITY * ((1 - yDrag2) / DRAG_Y) * (1 - DRAG_Y); // From Minecraft Wiki. Equation 2.
							final double estimatedYVelocity3 = v_0_y * yDrag1 - GRAVITY * ((1 - yDrag2) / DRAG_Y) * (1 - DRAG_Y);
							final double estimatedYVelocity4 = v_0_y * yDrag2 - GRAVITY * ((1 - yDrag1) / DRAG_Y) * (1 - DRAG_Y);
							final double estimatedYVelocity5 = (v_0_y - GRAVITY) * yDrag1 - GRAVITY * ((1 - yDrag1) / DRAG_Y) * (1 - DRAG_Y); // From Minecraft Wiki. Equation 3.
							final double estimatedYVelocity6 = (v_0_y - GRAVITY) * yDrag2 - GRAVITY * ((1 - yDrag2) / DRAG_Y) * (1 - DRAG_Y);
							final double estimatedYVelocity7 = (v_0_y - GRAVITY) * yDrag1 - GRAVITY * ((1 - yDrag2) / DRAG_Y) * (1 - DRAG_Y);
							final double estimatedYVelocity8 = (v_0_y - GRAVITY) * yDrag2 - GRAVITY * ((1 - yDrag1) / DRAG_Y) * (1 - DRAG_Y);

							final double estimatedYVelocity9 = (v_0_y - GRAVITY) * yDrag1;
							final double estimatedYVelocity10 = (v_0_y - GRAVITY) * yDrag2;

							final double estimatedYVelocity11 = v_0_y - GRAVITY * ticksPassed[0];

							final double estimatedYVelocity12 = v_0_y * yDrag1 - GRAVITY * yDrag1;
							final double estimatedYVelocity13 = v_0_y * yDrag2 - GRAVITY * yDrag2;
							final double estimatedYVelocity14 = v_0_y * yDrag1 - GRAVITY * yDrag2;
							final double estimatedYVelocity15 = v_0_y * yDrag2 - GRAVITY * yDrag1;

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
							final double errorY11 = estimatedYVelocity11 - actualYVelocity;
							final double errorY12 = estimatedYVelocity12 - actualYVelocity;
							final double errorY13 = estimatedYVelocity13 - actualYVelocity;
							final double errorY14 = estimatedYVelocity14 - actualYVelocity;
							final double errorY15 = estimatedYVelocity15 - actualYVelocity;

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
											errorY10,
											errorY11,
											errorY12,
											errorY13,
											errorY14,
											errorY15
							);

							for (int i = 0; i < errors.size(); i++) {
								yPredictionErrorAverages[i] += Math.abs(errors.get(i));
							}

							final double smallestError = NumberUtility.absMin(errors);

							yPredictionAccuracies[errors.indexOf(smallestError)]++;

							final double x_t = location.getX();
							final double z_t = location.getZ();

							estimatedYVelocity[0] -= 0.08D;
							estimatedYVelocity[0] *= 0.9800000190734863;

							Utility.log("Estimated Y Velocity: " + estimatedYVelocity[0]);
							Utility.log("Actual Y Velocity: " + actualYVelocity);

							Utility.log("Error: " + Math.abs(estimatedYVelocity[0] - actualYVelocity));

							yPredictionErrorAverages[15] += Math.abs(estimatedYVelocity[0] - actualYVelocity);

							estimatedXVelocity[0] *= 0.91D;
							estimatedZVelocity[0] *= 0.91D;

							Utility.log("Estimated X Velocity: " + estimatedXVelocity[0]);
							Utility.log("Error: " + Math.abs(estimatedXVelocity[0] - v_t_x));

							xPredictionErrorAverages[2] += Math.abs(estimatedXVelocity[0] - v_t_x);

							Utility.log("Estimated Z Velocity: " + estimatedZVelocity[0]);
							Utility.log("Error: " + Math.abs(estimatedZVelocity[0] - v_t_z));

							zPredictionErrorAverages[0] += Math.abs(estimatedZVelocity[0] - v_t_z);

							ticksPassed[0]++;

							new BukkitRunnable() {
								@Override
								public void run() {
									if (newProjectile.isDead() || newProjectile.isOnGround()) {
										cancel();
										return;
									}

									world.spawnParticle(Particle.DUST_COLOR_TRANSITION, location, 1, DUST_TRANSITION_RED);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorX1 * 25, z_t),
													1,
													DUST_TRANSITION_BLUE
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorX2 * 25, z_t),
													1,
													DUST_TRANSITION_NAVY
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorY1 * 25, z_t),
													1,
													DUST_TRANSITION_LIME
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorY2 * 25, z_t),
													1,
													DUST_TRANSITION_GREEN
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorY3 * 25, z_t),
													1,
													DUST_TRANSITION_TEAL
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorY4 * 25, z_t),
													1,
													DUST_TRANSITION_MAROON
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorY5 * 25, z_t),
													1,
													DUST_TRANSITION_FUCHSIA
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorY6 * 25, z_t),
													1,
													DUST_TRANSITION_PURPLE
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorY7 * 25, z_t),
													1,
													DUST_TRANSITION_ORANGE
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0 + errorY8 * 25, z_t),
													1,
													DUST_TRANSITION_AQUA
									);

									world.spawnParticle(
													Particle.DUST_COLOR_TRANSITION,
													new Location(world, x_t, y_0, z_t),
													1,
													DUST_TRANSITION_SILVER
									);
								}
							}.runTaskTimer(plugin, 0, 1);
						}
					}.runTaskTimer(plugin, 0, 1);
				}
			}
		}
	}
}
