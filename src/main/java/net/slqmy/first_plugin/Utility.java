package net.slqmy.first_plugin;

import jdk.internal.net.http.common.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Utility {
	public static ItemStack createItem(Material material, String name, String lore) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;

		itemMeta.setDisplayName(name);

		String[] loreArray = lore.split("\n");
		itemMeta.setLore(Arrays.asList(loreArray));

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	public static ItemStack createItem(Material material, String name) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;

		itemMeta.setDisplayName(name);

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	public static Inventory createInventory(@NotNull String name, int size, @NotNull List<Pair<ItemStack, Integer>> items) {
		Inventory inventory  = Bukkit.createInventory(null, size, name);

		int currentSlotNumber = 0;

		for (Pair<ItemStack, Integer> item : items) {
			ItemStack itemStack = item.first;
			Integer count = item.second;

			if (itemStack != null) {
				for (int i = currentSlotNumber; i < currentSlotNumber + count; i++) {
				inventory.setItem(i, itemStack);
				}
			}

			currentSlotNumber+=count;
		}

		return inventory;
	};
}
