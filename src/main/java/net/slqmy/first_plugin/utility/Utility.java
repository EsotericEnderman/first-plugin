package net.slqmy.first_plugin.utility;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.level.EntityPlayer;
import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.types.Pair;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utility {
	private static final Logger LOGGER = Logger.getLogger("Minecraft");
	private static final String LOG_PREFIX = "[First-Plugin]";

	private static final Pattern DIRECTORY_PATTERN = Pattern.compile(".+(?=/.+\\..+$)");
	private static final Pattern FORMAT_PATTERN = Pattern.compile(ChatColor.COLOR_CHAR + "[0-9a-fk-or]");
	private static final String TEXTURES_KEY = "textures";

	@Nullable
	public static Pair<@NotNull File, @NotNull YamlConfiguration> initiateYAMLFile(@NotNull String name,
			final @NotNull FirstPlugin plugin)
			throws IOException {
		final File pluginDataFolder = plugin.getDataFolder();
		name += ".yml";

		log("Attempting to create file '" + name + "'...");

		final Matcher matcher = DIRECTORY_PATTERN.matcher(name);

		if (matcher.find()) {
			final String directoryPath = matcher.group(0);

			log("Attempting to create directory '" + directoryPath + "'...");

			final File directory = new File(pluginDataFolder, directoryPath);

			if (!directory.exists()) {
				final boolean success = directory.mkdirs();

				if (success) {
					log("Successfully created directory.");
				} else {
					log("Failed to create directory!");
					return null;
				}
			} else {
				log("Directory already exists.");
			}
		}

		log("Creating file...");

		final File file = new File(pluginDataFolder, name);

		final boolean fileCreated = file.createNewFile();

		if (fileCreated) {
			log("File successfully created!");
		} else {
			log("File already exists.");
		}

		return new Pair<>(file, YamlConfiguration.loadConfiguration(file));
	}

	public static void log(@Nullable final Object message) {
		final String messageString = clearFormatting(LOG_PREFIX + " " + (message == null ? "NULL" : message.toString()));

		LOGGER.info(messageString);
	}

	@NotNull
	public static String replaceAll(@NotNull final String input, @NotNull final Pattern pattern,
			@NotNull final String replaceString) {
		final Matcher matcher = pattern.matcher(input);
		return matcher.replaceAll(replaceString);
	}

	@NotNull
	public static String clearFormatting(@NotNull final String input) {
		return replaceAll(input, FORMAT_PATTERN, "");
	}

	@NotNull
	public static ItemStack createItem(@NotNull final Material material, @NotNull final String name,
			final Multimap<Attribute, AttributeModifier> modifiers) {
		final ItemStack itemStack = new ItemStack(material);
		final ItemMeta itemMeta = itemStack.getItemMeta();
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

	@NotNull
	public static ItemStack createItem(@NotNull final Material material, @NotNull final String name,
			@NotNull final String lore) {
		final ItemStack itemStack = new ItemStack(material);
		final ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;

		itemMeta.setDisplayName(name);

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

		itemMeta.setDisplayName(name);

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

	public static void setSkin(@NotNull final Player player, @NotNull final String textureValue) {
		final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		final GameProfile gameProfile = entityPlayer.getBukkitEntity().getProfile();
		final PropertyMap propertyMap = gameProfile.getProperties();
		final Property property = propertyMap.get(TEXTURES_KEY).iterator().next();

		propertyMap.remove(TEXTURES_KEY, property);

		propertyMap.put(TEXTURES_KEY, new Property(TEXTURES_KEY, textureValue));
	}
}
