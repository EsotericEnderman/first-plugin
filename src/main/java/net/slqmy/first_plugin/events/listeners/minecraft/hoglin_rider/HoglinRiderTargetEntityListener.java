package net.slqmy.first_plugin.events.listeners.minecraft.hoglin_rider;

import net.slqmy.first_plugin.utility.HoglinRiderUtility;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.jetbrains.annotations.NotNull;

public final class HoglinRiderTargetEntityListener implements Listener {
	@EventHandler
	public void onEntityTargetEntity(@NotNull final EntityTargetLivingEntityEvent event) {
		final Entity entity = event.getEntity();

		if (HoglinRiderUtility.isHoglin(entity)) {
			final PiglinBrute piglin = (PiglinBrute) entity.getPassengers().get(0);

			assert piglin != null;

			final LivingEntity target = event.getTarget();
			final LivingEntity piglinTarget = piglin.getTarget();

			if (piglinTarget == null || !piglinTarget.equals(target)) {
				piglin.setTarget(target);
			}
		} else if (HoglinRiderUtility.isRider(entity)) {
			final Hoglin hoglin = (Hoglin) entity.getVehicle();

			assert hoglin != null;

			final LivingEntity target = event.getTarget();
			final LivingEntity hoglinTarget = hoglin.getTarget();

			if (hoglinTarget == null || !hoglinTarget.equals(target)) {
				hoglin.setTarget(target);
			}
		}
	}
}
