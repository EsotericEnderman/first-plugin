package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.hoglin;

import dev.enderman.minecraft.plugins.first.utility.HoglinRiderUtility;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public final class HoglinRiderDamageListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public synchronized void onEntityDamage(@NotNull final EntityDamageEvent event) {
		final Entity entity = event.getEntity();

		if (HoglinRiderUtility.isHoglin(entity)) {
			final PiglinBrute piglin = (PiglinBrute) entity.getPassengers().get(0);
			final double health = piglin.getHealth();
			final double damage = event.getDamage();

			piglin.setHealth(Math.max(health - damage, 0));
		} else if (HoglinRiderUtility.isRider(entity)) {
			final Hoglin hoglin = (Hoglin) entity.getVehicle();

			assert hoglin != null;

			final double health = hoglin.getHealth();
			final double damage = event.getDamage();

			hoglin.setHealth(Math.max(health - damage, 0));
		}
	}
}
