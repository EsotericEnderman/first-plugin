package net.slqmy.first_plugin.events.listeners.minecraft.player;

import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
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
