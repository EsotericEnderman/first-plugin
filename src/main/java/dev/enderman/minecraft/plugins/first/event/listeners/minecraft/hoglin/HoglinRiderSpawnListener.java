package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.hoglin;

import dev.enderman.minecraft.plugins.first.FirstPlugin;
import dev.enderman.minecraft.plugins.first.events.custom.HoglinRiderSpawnEvent;
import dev.enderman.minecraft.plugins.first.utility.HoglinRiderUtility;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class HoglinRiderSpawnListener implements Listener {
	private final YamlConfiguration config;
	private final FirstPlugin plugin;

	public HoglinRiderSpawnListener(@NotNull final FirstPlugin plugin) {
		this.plugin = plugin;
		this.config = (YamlConfiguration) plugin.getConfig();
	}

	@EventHandler
	public void onCustomEntitySpawn(@NotNull final HoglinRiderSpawnEvent event) {
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
