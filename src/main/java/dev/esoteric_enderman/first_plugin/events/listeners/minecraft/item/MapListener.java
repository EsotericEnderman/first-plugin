package dev.esoteric_enderman.first_plugin.events.listeners.minecraft.item;

import dev.esoteric_enderman.first_plugin.maps.Caet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

public final class MapListener implements Listener {
	@EventHandler
	public void onMapInitialise(@NotNull final MapInitializeEvent event) {
		final MapView view = event.getMap();

		for (final MapRenderer renderer : view.getRenderers()) {
			view.removeRenderer(renderer);
		}

		// Idea: make a map image loading system / manager.

		view.addRenderer(new Caet());
	}
}
