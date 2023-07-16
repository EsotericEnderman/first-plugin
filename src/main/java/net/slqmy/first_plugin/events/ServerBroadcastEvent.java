package net.slqmy.first_plugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ServerBroadcastEvent extends Event implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();

	private final Player player;
	private final String message;

	private boolean cancelled;

	public ServerBroadcastEvent(Player player, String message) {
		this.player = player;
		this.message = message;

		cancelled = false;
	}

	public Player getPlayer() {
		return player;
	}

	public String getMessage() {
		return message;
	}

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
