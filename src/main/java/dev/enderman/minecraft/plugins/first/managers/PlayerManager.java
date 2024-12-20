package dev.enderman.minecraft.plugins.first.managers;

import dev.enderman.minecraft.plugins.first.types.PlayerWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public final class PlayerManager {
	private final HashMap<UUID, PlayerWrapper> players = new HashMap<>();

	public PlayerWrapper getPlayer(@NotNull final UUID uuid) {
		return players.get(uuid);
	}

	public void addPlayer(@NotNull final PlayerWrapper player) {
		players.put(player.getUUID(), player);
	}

	public void removePlayer(@NotNull final UUID uuid) {
		players.remove(uuid);
	}
}
