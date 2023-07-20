package net.slqmy.first_plugin.events.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.event.entity.EntityMoveEvent;
import net.slqmy.first_plugin.utility.HoglinRiderUtility;
import net.slqmy.first_plugin.utility.VectorUtility;

public final class HoglinMoveEventListener implements Listener {
	private static final PotionEffect HOGLIN_JUMP_EFFECT = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, 0, true,
			true);

	@EventHandler
	public void onHoglinMove(@NotNull final EntityMoveEvent event) {
		final Entity entity = event.getEntity();

		if (!HoglinRiderUtility.isHoglin(entity)) {
			return;
		}

		final Entity passenger = entity.getPassengers().get(0);

		if (!HoglinRiderUtility.isRider(passenger)) {
			return;
		}

		final PiglinBrute piglin = (PiglinBrute) passenger;
		final LivingEntity target = piglin.getTarget();

		if (target == null) {
			return;
		}

		final Hoglin hoglin = (Hoglin) entity;

		final Location hoglinLocation = hoglin.getLocation();
		final Location targetLocation = target.getLocation();
		final double distance = hoglinLocation.distance(targetLocation);

		hoglin.teleport(hoglinLocation.setDirection(piglin.getLocation().getDirection()));

		if (HoglinRiderUtility.canJump(hoglin) && !hoglin.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)
				&& distance >= 4) {
			hoglin.addPotionEffect(HOGLIN_JUMP_EFFECT);

			hoglin.setVelocity(VectorUtility.calculateLeapVelocityVector(hoglin, targetLocation, 5));
			// Some magic math to get a jump vector...
		}
	}
}
