package net.slqmy.first_plugin.events.listeners.minecraft;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.events.custom_events.CustomEntitySpawnEvent;
import net.slqmy.first_plugin.utility.HoglinRiderUtility;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class CustomEntitySpawnEventListener implements Listener {
	private final YamlConfiguration config;
	private final Main plugin;

	public CustomEntitySpawnEventListener(@NotNull final Main plugin) {
		this.plugin = plugin;
		this.config = (YamlConfiguration) plugin.getConfig();
	}

	@EventHandler
	public void onCustomEntitySpawn(@NotNull final CustomEntitySpawnEvent event) {
		final Entity entity = event.getEntity();

		if (HoglinRiderUtility.isHoglin(entity)) {
			final List<String> hoglinRiders = config.getStringList("HoglinRiders");
			final String entityUUID = entity.getUniqueId().toString();

			hoglinRiders.add(entityUUID);

			config.set("HoglinRiders", hoglinRiders);

			plugin.saveConfig();

			HoglinRiderUtility.playParticles(plugin, entity);
		}
	}
}
