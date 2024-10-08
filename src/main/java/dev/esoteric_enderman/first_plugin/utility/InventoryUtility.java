package dev.esoteric_enderman.first_plugin.utility;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.esoteric_enderman.first_plugin.utility.types.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public final class InventoryUtility {
	@NotNull
	public static ItemStack createItem(@NotNull final Material material, @NotNull final String name,
	                                   @Nullable final Multimap<Attribute, AttributeModifier> modifiers) {
		final ItemStack itemStack = new ItemStack(material);
		final ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;

		itemMeta.setDisplayName(ChatColor.RESET + name);

		Multimap<Attribute, AttributeModifier> itemModifiers = ArrayListMultimap.create();

		if (modifiers != null) {
			itemModifiers = modifiers;
		}

		itemMeta.setAttributeModifiers(itemModifiers);

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	@NotNull
	public static ItemStack createItem(@NotNull final Material material, @NotNull final String name,
	                                   @NotNull final String lore) {
		final ItemStack itemStack = new ItemStack(material);
		final ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;

		itemMeta.setDisplayName(ChatColor.RESET + name);

		final String[] loreArray = lore.split("\n");
		itemMeta.setLore(Arrays.asList(loreArray));

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	@NotNull
	public static ItemStack createItem(@NotNull final Material material, @NotNull final String name) {
		final ItemStack itemStack = new ItemStack(material);
		final ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;

		itemMeta.setDisplayName(ChatColor.RESET + name);

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	@NotNull
	public static Inventory createInventory(@NotNull final String name, final int size,
	                                        @NotNull final List<Pair<ItemStack, Integer>> items) {
		final Inventory inventory = Bukkit.createInventory(null, size, name);

		int currentSlotIndex = 0;

		for (final Pair<ItemStack, Integer> item : items) {
			final ItemStack itemStack = item.first;
			final Integer count = item.second;

			if (itemStack != null) {
				for (int i = currentSlotIndex; i < currentSlotIndex + count && i < size; i++) {
					inventory.setItem(i, itemStack);
				}
			}

			currentSlotIndex += count;
		}

		return inventory;
	}

	public static boolean isValidPage(final int pageNumber, @NotNull final List<ItemStack> items,
	                                  final int slotsPerPage) {
		return pageNumber > 0 && pageNumber <= Math.ceil((double) items.size() / slotsPerPage);
	}

	@NotNull
	public static List<ItemStack> getPageItems(@NotNull final List<ItemStack> items, final int pageNumber,
	                                           final int slotsPerPage) {

		return items.subList(slotsPerPage * (pageNumber - 1), Math.min(items.size(), slotsPerPage * pageNumber));
	}
}
