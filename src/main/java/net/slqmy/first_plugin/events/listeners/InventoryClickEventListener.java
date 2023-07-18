package net.slqmy.first_plugin.events.listeners;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public final class InventoryClickEventListener implements Listener {
	private static final Random random = new Random();

	@EventHandler
	public void onInventoryClick(@NotNull final InventoryClickEvent event) {
		final Player player = (Player) event.getWhoClicked();

		// A better way to check if the player is actually in the correct inventory is
		// comparing the inventory using Object#equals with some sort of class that
		// represents that specific inventory.

		// This is basically necessary for actual plugins because sometimes players can
		// rename chests / other containers, so simply checking the name isn't really
		// safe.

		if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle())
				.equals(ChatColor.RED.toString() + ChatColor.BOLD + "ADMIN MENU") && event.getCurrentItem() != null) {
			final int slot = event.getRawSlot();

			switch (slot) {
				// CLOSE.
				case 0:
					break;
				// RANDOM TELEPORT.
				case 11:
					final World world = player.getWorld();
					final WorldBorder worldBorder = world.getWorldBorder();
					final double worldSize = worldBorder.getSize();
					final Location center = worldBorder.getCenter();

					final double x = random.nextDouble() * worldSize + center.getX();
					final double y = random.nextDouble() * 448 - 64;
					final double z = random.nextDouble() * worldSize + center.getZ();

					player.teleport(new Location(world, x, y, z));
					player.sendMessage(ChatColor.AQUA + "You teleported to (" + x + ", " + y + ", " + z + ").");

					break;
				// KILL YOURSELF.
				case 13:
					player.setHealth(0);
					player.sendMessage(
							ChatColor.RED.toString() + ChatColor.BOLD + "RIP" + ChatColor.RED + ". You killed yourself.");

					break;
				// CLEAR INVENTORY.
				case 15:
					player.closeInventory();

					final Inventory playerInventory = player.getInventory();
					playerInventory.clear();

					player.sendMessage(ChatColor.GOLD + "» You have cleared your inventory.");

					return;
				case 29:
					break;
				// CREATE THE SLIMY SWAMP.
				case 31:
					player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[Error]" + ChatColor.RED
							+ " SlimePointerException: slime");

					break;
				// SECRET BUTTON.
				case 33:
					player.sendMessage(ChatColor.ITALIC + "Why?");

					break;
				default:
					event.setCancelled(true);
					return;
			}

			player.closeInventory();
		}
	}
}
