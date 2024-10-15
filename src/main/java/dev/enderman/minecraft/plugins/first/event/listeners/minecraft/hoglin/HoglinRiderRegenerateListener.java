package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.hoglin;

import dev.enderman.minecraft.plugins.first.utility.HoglinRiderUtility;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

public final class HoglinRiderRegenerateListener implements Listener {
	@EventHandler
	public synchronized void onEntityRegenerate(@NotNull final EntityRegainHealthEvent event) {
		final Entity entity = event.getEntity();

		if (HoglinRiderUtility.isHoglin(entity)) {
			final PiglinBrute piglin = (PiglinBrute) entity.getPassengers().get(0);

			assert piglin != null;

			final double healedAmount = event.getAmount();
			final AttributeInstance piglinHealth = piglin.getAttribute(Attribute.GENERIC_MAX_HEALTH);

			assert piglinHealth != null;

			piglin.setHealth(Math.min(piglin.getHealth() + healedAmount, piglinHealth.getBaseValue()));
		} else if (HoglinRiderUtility.isRider(entity)) {
			final Hoglin hoglin = (Hoglin) entity.getVehicle();

			assert hoglin != null;

			final double healedAmount = event.getAmount();
			final AttributeInstance hoglinHealth = hoglin.getAttribute(Attribute.GENERIC_MAX_HEALTH);

			assert hoglinHealth != null;

			hoglin.setHealth(Math.min(hoglin.getHealth() + healedAmount, hoglinHealth.getBaseValue()));
		}
	}
}
