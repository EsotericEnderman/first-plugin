package net.slqmy.first_plugin.events.listeners;

import net.slqmy.first_plugin.Main;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.event.entity.EntityMoveEvent;
import net.slqmy.first_plugin.utility.HoglinRiderUtility;
import net.slqmy.first_plugin.utility.VectorUtility;

public final class HoglinMoveEventListener implements Listener {
	private static final PotionEffect HOGLIN_JUMP_EFFECT = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, 0, true,
			true);

	private final Main plugin;

	public HoglinMoveEventListener(@NotNull final Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onHoglinMove(@NotNull final EntityMoveEvent event) {
		final Entity entity = event.getEntity();

		if (!HoglinRiderUtility.isHoglin(entity)) {
			return;
		}

		final PiglinBrute piglin = (PiglinBrute) entity.getPassengers().get(0);
		final LivingEntity target = piglin.getTarget();

		if (target == null) {
			return;
		}

		final Hoglin hoglin = (Hoglin) entity;

		final Location hoglinLocation = hoglin.getLocation();
		final Location targetLocation = target.getLocation();

		targetLocation.add(0F, target.getHeight(), 0F);

		final double distance = hoglinLocation.distance(targetLocation);

		if (HoglinRiderUtility.canJump(hoglin) && !hoglin.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)
				&& distance >= 4) {
			hoglin.addPotionEffect(HOGLIN_JUMP_EFFECT);

			hoglin.setVelocity(VectorUtility.calculateLeapVelocityVector(hoglin, targetLocation, 1.8F));

			final AttributeInstance movementSpeed = hoglin.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);

			movementSpeed.setBaseValue(0);

			new BukkitRunnable() {
				@Override
				public void run() {
					if (hoglin.isOnGround()) {
						movementSpeed.setBaseValue(0.55F);

						cancel();
					}
				}
			}.runTaskTimer(plugin, 5, 1);
		}
	}
}
