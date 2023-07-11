package net.slqmy.first_plugin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Utility {
	// I will create a better utility class in actual plugins, this is just a test to see
	// what I can do and just to experiment.

	public static ItemStack createItem(@NotNull Material material, @NotNull String name, Multimap<Attribute, AttributeModifier> modifiers) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;

		itemMeta.setDisplayName(name);

		Multimap<Attribute, AttributeModifier> itemModifiers = ArrayListMultimap.create();
		if (itemModifiers != null) {
			itemModifiers = modifiers;
		}
		itemMeta.setAttributeModifiers(itemModifiers);

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}


	public static ItemStack createItem(@NotNull Material material, @NotNull String name, @NotNull String lore) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;

		itemMeta.setDisplayName(name);

		String[] loreArray = lore.split("\n");
		itemMeta.setLore(Arrays.asList(loreArray));

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	public static ItemStack createItem(@NotNull Material material, @NotNull String name) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;

		itemMeta.setDisplayName(name);

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	public static Inventory createInventory(@NotNull String name, int size, @NotNull List<List> items) {
		Inventory inventory  = Bukkit.createInventory(null, size, name);

		int currentSlotIndex = 0;

		for (List item : items) {
			ItemStack itemStack = (ItemStack) item.get(0);
			Integer count = (Integer) item.get(1);

			if (itemStack != null) {
				for (int i = currentSlotIndex; i < currentSlotIndex + count && i < size; i++) {
					inventory.setItem(i, itemStack);
				}
			}

			currentSlotIndex += count;
		}

		return inventory;
	};
}
