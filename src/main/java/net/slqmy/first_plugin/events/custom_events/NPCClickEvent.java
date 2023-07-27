package net.slqmy.first_plugin.events.custom_events;

import net.slqmy.first_plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class NPCClickEvent extends Event implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();

	private final Entity npc;
	private final Player clicker;

	private boolean cancelled;

	public NPCClickEvent(@NotNull final Main plugin, @NotNull final Player clicker, final int npcEntityID) {
		this.clicker = clicker;
		// The map is needed because there is no way to get an entity from entity ID sadly.
		this.npc = Bukkit.getEntity(plugin.getNPCs().get(npcEntityID));
	}

	public Entity getNPC() {
		return npc;
	}

	public Player getClicker() {
		return clicker;
	}

	@NotNull
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
}
