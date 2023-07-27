package net.slqmy.first_plugin.events.listeners.minecraft.hoglin_rider;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.utility.HoglinRiderUtility;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class HoglinRiderDeathListener implements Listener {
	private final Main plugin;
	private final YamlConfiguration config;

	public HoglinRiderDeathListener(final @NotNull Main plugin) {
		this.plugin = plugin;
		this.config = (YamlConfiguration) plugin.getConfig();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(@NotNull final EntityDeathEvent event) {
		final LivingEntity entity = event.getEntity();

		if (HoglinRiderUtility.isHoglin(entity)) {
			final PiglinBrute piglin = (PiglinBrute) entity.getPassengers().get(0);

			assert piglin != null;

			piglin.setHealth(0.0);

			HoglinRiderUtility.playDeathSound(entity.getWorld(), entity.getLocation(), "PIGLIN");
			final List<String> hoglinRiders = config.getStringList("HoglinRiders");

			hoglinRiders.remove(entity.getUniqueId().toString());

			config.set("HoglinRiders", hoglinRiders);
			plugin.saveConfig();
		} else if (HoglinRiderUtility.isRider(entity)) {
			final Hoglin hoglin = (Hoglin) entity.getVehicle();

			assert hoglin != null;
			hoglin.setHealth(0.0);

			HoglinRiderUtility.playDeathSound(entity.getWorld(), entity.getLocation(), "HOGLIN");
		}
	}
}
