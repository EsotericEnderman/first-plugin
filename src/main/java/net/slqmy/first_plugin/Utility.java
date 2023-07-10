package net.slqmy.first_plugin;

import jdk.internal.net.http.common.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

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

	public static Inventory createInventory(String name, int size, ArrayList<Pair<ItemStack, Integer>> items) {
		Inventory inventory  = Bukkit.createInventory(null, size, name);

		// TODO: Complete this method.
		// [ItemStack item, Integer count][]

		return inventory;
	};
}
