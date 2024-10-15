package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.player;

import dev.enderman.minecraft.plugins.first.types.AuctionHouseGUI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public final class GUIListener implements Listener {
	private static final Random random = new Random();

	@EventHandler
	public void onInventoryClick(@NotNull final InventoryClickEvent event) {

		// A better way to check if the player is actually in the correct inventory is
		// comparing the inventory using Object#equals with some sort of class that
		// represents that specific inventory.

		// This is basically necessary for actual plugins because sometimes players can
		// rename chests / other containers, so simply checking the name isn't really
		// safe.

		if (event.getCurrentItem() != null) {
			final Player player = (Player) event.getWhoClicked();

			if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle())
							.equals(ChatColor.RED.toString() + ChatColor.BOLD + "ADMIN MENU")) {
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

						final double x = random.nextDouble() * worldSize - worldSize / 2 + center.getX();
						final double y = random.nextDouble() * 448D - 64D;
						final double z = random.nextDouble() * worldSize - worldSize / 2 + center.getZ();

						player.teleport(new Location(world, x, y, z));
						player.sendMessage(ChatColor.GRAY + "You have been teleported to (" + ChatColor.RED + x + ChatColor.RESET
										+ ", " + ChatColor.GREEN + y + ChatColor.RESET + ", " + ChatColor.BLUE + z + ChatColor.RESET + ").");

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
			} else if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle())
							.startsWith(ChatColor.GOLD.toString() + ChatColor.BOLD + "Auction House " + ChatColor.DARK_GRAY + "» Page "
											+ ChatColor.YELLOW)) {
				final int slot = event.getRawSlot();

				final Inventory inventory = event.getInventory();

				final ItemStack firstItem = inventory.getItem(0);
				assert firstItem != null;

				final ItemMeta firstItemMeta = firstItem.getItemMeta();
				assert firstItemMeta != null;

				final int page = Integer.parseInt(firstItemMeta.getLocalizedName());

				// If I'm making a dynamically changing inventory, such as an auction house
				// menu, check again here whether it is a valid page.
				if (slot == 45) {
					new AuctionHouseGUI(player, page - 1);
				} else if (slot == 53) {
					new AuctionHouseGUI(player, page + 1);
				}

				event.setCancelled(true);
			}
		}
	}
}
