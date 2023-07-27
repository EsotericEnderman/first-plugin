/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package net.slqmy.first_plugin.events.custom_events;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class HoglinRiderSpawnEvent extends Event {
	private static final HandlerList HANDLERS = new HandlerList();
	private final Entity entity;
	private final Location location;

	public HoglinRiderSpawnEvent(@NotNull final Entity entity, @NotNull final Location location) {
		this.entity = entity;
		this.location = location;
	}

	@NotNull
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public Location getLocation() {
		return this.location;
	}

	@NotNull
	public HandlerList getHandlers() {
		return HANDLERS;
	}
}
