package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.projectile;

import dev.enderman.minecraft.plugins.first.FirstPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class EggThrowListener implements Listener {
	private final NamespacedKey isShotgunBulletKey;

	public EggThrowListener(@NotNull final FirstPlugin plugin) {
		isShotgunBulletKey = plugin.getIsShotgunBulletKey();
	}

	@EventHandler
	public void onPlayerEggThrow(@NotNull final PlayerEggThrowEvent event) {
		final Egg egg = event.getEgg();

		final PersistentDataContainer container = egg.getPersistentDataContainer();

		if (container.has(isShotgunBulletKey, PersistentDataType.BOOLEAN)) {
			event.setHatching(false);
		}
	}
}
