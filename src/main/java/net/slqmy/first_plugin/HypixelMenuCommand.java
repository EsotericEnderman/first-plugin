package net.slqmy.first_plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class HypixelMenuCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			Inventory inventory = Bukkit.createInventory(null,36, "Event Guide");

			ItemStack eventPage = new ItemStack(Material.WRITABLE_BOOK);
			ItemMeta eventPageMeta = eventPage.getItemMeta();
			assert eventPageMeta != null;
			eventPageMeta.setDisplayName(ChatColor.GREEN + "Event Page");
			eventPageMeta.setLore(Arrays.asList("Check out our news post for a", "full breakdown of what's", "included in this event!", "", ChatColor.YELLOW + "Click to open!"));
			eventPage.setItemMeta(eventPageMeta);

			inventory.setItem(11, eventPage);

			ItemStack mainLobbyActivities = new ItemStack(Material.COMPASS);
			ItemMeta mainLobbyActivitiesMeta = mainLobbyActivities.getItemMeta();
			assert mainLobbyActivitiesMeta != null;
			mainLobbyActivitiesMeta.setDisplayName(ChatColor.GREEN + "Main Lobby Activities");
			mainLobbyActivitiesMeta.setLore(Arrays.asList("View what activities are", "available in the Main Lobby for", "this event!", "", ChatColor.YELLOW + "Click to view!"));
			mainLobbyActivities.setItemMeta(mainLobbyActivitiesMeta);

			inventory.setItem(13, mainLobbyActivities);

			ItemStack eventShop = new ItemStack(Material.EMERALD);
			ItemMeta eventShopMeta = eventShop.getItemMeta();
			assert eventShopMeta != null;
			eventShopMeta.setDisplayName(ChatColor.GREEN + "Event Shop");
			eventShopMeta.setLore(Arrays.asList("Level up during events by",
							"playing games and completing",
							"quests.",
							"",
							"Earn " + ChatColor.WHITE + "Event Silver" + ChatColor.RESET + "when you",
							"gain an event level. " + ChatColor.WHITE + "Silver",
							"can be used to purchase",
							"event-themed cosmetics!",
							"",
							"Currently Active: " + ChatColor.YELLOW + "Summer 2023",
							ChatColor.GREEN + "Ends in 58 days! (HURRY UP, SUMMER IS ENDING)",
							"",
							ChatColor.GOLD + "Event Level " + ChatColor.YELLOW + "1" + ChatColor.DARK_GRAY +
											" ||||||||||||||||||||||||||||||||||||||||||||||||||||||",
							"Daily EXP: " + ChatColor.YELLOW + "0" + ChatColor.DARK_GRAY + "/" + ChatColor.GOLD + "100,000",
							"Event Silver:" + ChatColor.WHITE + "2,610",
							"",
							ChatColor.YELLOW + "Click to view the shop!"
			));
			eventShop.setItemMeta(eventShopMeta);

			inventory.setItem(15, eventShop);

			ItemStack closeButton = new ItemStack(Material.BARRIER);
			ItemMeta closeButtonMeta = closeButton.getItemMeta();
			assert closeButtonMeta != null;
			closeButtonMeta.setDisplayName(ChatColor.RED + "Close");
			closeButton.setItemMeta(closeButtonMeta);

			inventory.setItem(31, closeButton);

			player.openInventory(inventory);
		}

		return false;
	}
}
