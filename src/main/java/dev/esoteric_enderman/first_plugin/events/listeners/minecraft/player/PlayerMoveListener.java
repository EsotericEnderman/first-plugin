package dev.esoteric_enderman.first_plugin.events.listeners.minecraft.player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import dev.esoteric_enderman.first_plugin.FirstPlugin;
import dev.esoteric_enderman.first_plugin.utility.types.Cuboid;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public final class PlayerMoveListener implements Listener {
	private final FirstPlugin plugin;
	private final List<UUID> movementDisabled;

	public PlayerMoveListener(@NotNull final FirstPlugin plugin) {
		this.plugin = plugin;
		this.movementDisabled = plugin.getMovementDisabled();
	}

	@EventHandler
	public void onPlayerMove(@NotNull final PlayerMoveEvent event) {
		final Player player = event.getPlayer();

		if (movementDisabled.contains(player.getUniqueId())) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "Trolled! Stop moving! You are frozen.");
		}

		final Cuboid latestFill = plugin.getLatestFill();

		if (latestFill != null && latestFill.contains(player.getLocation())) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
							TextComponent
											.fromLegacyText(ChatColor.GRAY.toString() + ChatColor.BOLD + "- " + ChatColor.RED + ChatColor.BOLD
															+ "BEWARE" + ChatColor.RED + ": " + ChatColor.YELLOW + "You are in the latest fill region!"
															+ ChatColor.GRAY + ChatColor.BOLD + " -"));
		}
	}
}
