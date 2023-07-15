package net.slqmy.first_plugin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
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
	}

	public static void setSkin(Player player) {
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		GameProfile gameProfile = entityPlayer.getBukkitEntity().getProfile();
		PropertyMap propertyMap = gameProfile.getProperties();
		Property property = propertyMap.get("textures").iterator().next();

		propertyMap.remove("textures", property);

		propertyMap.put("textures", new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYyMTY2ODQyMDIyOCwKICAicHJvZmlsZUlkIiA6ICI0ZWQ4MjMzNzFhMmU0YmI3YTVlYWJmY2ZmZGE4NDk1NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaXJlYnlyZDg4IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk0ZWZmZDI5ODM2Nzk3MTE3NzMwZmVkYmViMzM1MzFhM2Y1ZDc2Y2VlM2M4OWFlYzkzMDRkMjJlNjkyMjJkZjciCiAgICB9CiAgfQp9", "dK07fLBFAPxYCm8dlpf3dGo9e7NN6pwTXiY96axB/YgK9QQFOt96MnvTgXVmoDyt1w5JB9+KC1Hyur3Z97OT6ZIzjPGCCN0mrfj72xQq9K65TZSJ4/+LRQcVaH6niDAhhEp7IJQpKj8GBJ0KGsG4rGYz2afKJ6rJrMvXyIvOBrqKOBDyZlRec/i0vIPWmz1XTBXexRGWX3d5j0yd0z6W+dhXnffWLbLYLDlYOLtGjGWbL4zR34vK+Agzzqwuu59QVwDDWx/sY4WmChbIA3kY77Gm0iA6UEPGw5t6+HE3da19E4XKXzGFaIQaNm20bqZ3h78TXGlLQd6R+/7kGtWj1d69IE1VUYvykkAlDs+0kA1u7my/f+6uHfC5d2gKJq5nENqPQBbr4GBnRFQsdWwtsxLQz+4XgNAvyWvdLeU2wDeIvjkmtbiRvwYzLdo+Fwg71y7hs6peNhhVGB9Nj+kNnqy/ssTo/5PDqqS1HcmRi35R5GYIuH4u98qc78EKlEtgSRGF6h2m6v1GJT4jqkLBPnZ0MLmSLOmPZhlJbqUpQHQsmosYk9c7fJ3cAo/p2kS186ujodubeg+2qwxxlg7uD2JNQaTjqlNEZacX96wSWevHebT5XlnvRnCBUG1+p/P2qj146fHHOu4x2mV2ufIgJwKWtPLM1PTeJ4Qs4H5fdh8="));
	}
}
