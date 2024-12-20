package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.player;

import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerDeathListener implements Listener {
	@EventHandler
	public void onPlayerDeath(@NotNull final PlayerDeathEvent event) {
		final ServerPlayer serverPlayer = ((CraftPlayer) event.getEntity()).getHandle();
		serverPlayer.connection.send(
						new ClientboundGameEventPacket(
										ClientboundGameEventPacket.IMMEDIATE_RESPAWN,
										1
						)
		);
	}
}
