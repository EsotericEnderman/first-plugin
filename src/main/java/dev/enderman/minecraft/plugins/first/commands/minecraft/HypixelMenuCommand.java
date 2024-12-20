package dev.enderman.minecraft.plugins.first.commands.minecraft;

import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public final class HypixelMenuCommand extends AbstractCommand {

	public HypixelMenuCommand() {
		super(
						"hypixel-menu",
						"View a menu that I copied from Hypixel.",
						"/hypixel-menu",
						new Integer[]{0},
						new String[]{"hm", "hpm"},
						"first_plugin.hypixel_menu",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
		final Player player = (Player) sender;

		final Inventory inventory = Bukkit.createInventory(null, 36, "Event Guide");

		final ItemStack eventPage = new ItemStack(Material.WRITABLE_BOOK);
		final ItemMeta eventPageMeta = eventPage.getItemMeta();
		assert eventPageMeta != null;
		eventPageMeta.setDisplayName(ChatColor.GREEN + "Event Page");
		eventPageMeta.setLore(Arrays.asList(ChatColor.RESET.toString() + ChatColor.GRAY + "Check out our news post for a",
						ChatColor.RESET.toString() + ChatColor.GRAY + "full breakdown of what's",
						ChatColor.RESET.toString() + ChatColor.GRAY + "included in this event!", "",
						ChatColor.YELLOW + "Click to open!"));
		eventPage.addUnsafeEnchantment(Enchantment.INFINITY, 1);
		eventPageMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		eventPage.setItemMeta(eventPageMeta);

		inventory.setItem(11, eventPage);

		final ItemStack mainLobbyActivities = new ItemStack(Material.COMPASS);
		final ItemMeta mainLobbyActivitiesMeta = mainLobbyActivities.getItemMeta();
		assert mainLobbyActivitiesMeta != null;
		mainLobbyActivitiesMeta.setDisplayName(ChatColor.GREEN + "Main Lobby Activities");
		mainLobbyActivitiesMeta
						.setLore(Arrays.asList(ChatColor.RESET.toString() + ChatColor.GRAY + "View what activities are",
										ChatColor.RESET.toString() + ChatColor.GRAY + "available in the Main Lobby for",
										ChatColor.RESET.toString() + ChatColor.GRAY + "this event!", "", ChatColor.YELLOW + "Click to view!"));
		mainLobbyActivities.setItemMeta(mainLobbyActivitiesMeta);

		inventory.setItem(13, mainLobbyActivities);

		final ItemStack eventShop = new ItemStack(Material.EMERALD);
		final ItemMeta eventShopMeta = eventShop.getItemMeta();
		assert eventShopMeta != null;
		eventShopMeta.setDisplayName(ChatColor.GREEN + "Event Shop");
		eventShopMeta.setLore(Arrays.asList(
						ChatColor.RESET.toString() + ChatColor.GRAY + "Level up during events by",
						ChatColor.RESET.toString() + ChatColor.GRAY + "playing games and completing",
						ChatColor.RESET.toString() + ChatColor.GRAY + "quests.",
						"",
						ChatColor.RESET.toString() + ChatColor.GRAY + "Earn " + ChatColor.WHITE + "Event Silver"
										+ ChatColor.RESET + ChatColor.GRAY + "when you",
						ChatColor.RESET.toString() + ChatColor.GRAY + "gain an event level. " + ChatColor.WHITE + "Silver",
						ChatColor.RESET.toString() + ChatColor.GRAY + "can be used to purchase",
						ChatColor.RESET.toString() + ChatColor.GRAY + "event-themed cosmetics!",
						"",
						ChatColor.RESET.toString() + ChatColor.GRAY + "Currently Active: " + ChatColor.YELLOW + "Summer 2023",
						ChatColor.GREEN + "Ends in 58 days!",
						"",
						ChatColor.GOLD + "Event Level " + ChatColor.YELLOW + "1" + ChatColor.DARK_GRAY +
										" ||||||||||||||||||||||||||||||||||||||||||||||||||||||",
						ChatColor.RESET.toString() + ChatColor.GRAY + "Daily EXP: " + ChatColor.YELLOW + "0" + ChatColor.DARK_GRAY
										+ "/" + ChatColor.GOLD + "100,000",
						ChatColor.RESET.toString() + ChatColor.GRAY + "Event Silver: " + ChatColor.WHITE + "2,610",
						"",
						ChatColor.YELLOW + "Click to view the shop!"));
		eventShop.setItemMeta(eventShopMeta);

		inventory.setItem(15, eventShop);

		final ItemStack closeButton = new ItemStack(Material.BARRIER);
		final ItemMeta closeButtonMeta = closeButton.getItemMeta();
		assert closeButtonMeta != null;
		closeButtonMeta.setDisplayName(ChatColor.RED + "Close");
		closeButton.setItemMeta(closeButtonMeta);

		inventory.setItem(31, closeButton);

		player.openInventory(inventory);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
