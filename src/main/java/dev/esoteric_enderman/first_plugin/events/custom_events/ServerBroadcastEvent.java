package dev.esoteric_enderman.first_plugin.events.custom_events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class ServerBroadcastEvent extends Event implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();

	private final Player player;
	private final String message;

	private boolean cancelled;

	public ServerBroadcastEvent(@NotNull final Player player, @NotNull final String message) {
		this.player = player;
		this.message = message;

		cancelled = false;
	}

	@NotNull
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@NotNull
	public Player getPlayer() {
		return player;
	}

	@NotNull
	public String getMessage() {
		return message;
	}

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}
}
