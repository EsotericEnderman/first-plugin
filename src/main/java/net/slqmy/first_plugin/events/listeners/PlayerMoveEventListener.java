package net.slqmy.first_plugin.events.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.types.Cuboid;

public final class PlayerMoveEventListener implements Listener {
	private final List<UUID> movementDisabled;
	private final Cuboid latestFill;

	public PlayerMoveEventListener(@NotNull final FirstPlugin plugin) {
		this.movementDisabled = plugin.getMovementDisabled();
		this.latestFill = plugin.getLatestFill();
	}

	@EventHandler
	public void onPlayerMove(@NotNull final PlayerMoveEvent event) {
		final Player player = event.getPlayer();

		if (movementDisabled.contains(player.getUniqueId())) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "Trolled! Stop moving! You are frozen.");
		}

		if (latestFill != null && latestFill.contains(player.getLocation())) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
					TextComponent.fromLegacyText(ChatColor.GRAY + "- " + ChatColor.RED + ChatColor.BOLD
							+ "BEWARE" + ChatColor.RED + ": You are in the latest fill region!" + ChatColor.GRAY + " -"));
		}
	}
}
