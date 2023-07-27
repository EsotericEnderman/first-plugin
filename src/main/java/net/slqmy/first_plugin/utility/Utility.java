package net.slqmy.first_plugin.utility;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.level.ServerPlayer;
import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.utility.types.Pair;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
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
	public static Pair<@NotNull File, @NotNull YamlConfiguration> initiateYAMLFile(@NotNull String name, final @NotNull Main plugin)
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
	public static String replaceAll(@NotNull final String input, @NotNull final Pattern pattern, @NotNull final String replaceString) {
		final Matcher matcher = pattern.matcher(input);
		return matcher.replaceAll(replaceString);
	}

	@NotNull
	public static String clearFormatting(@NotNull final String input) {
		return replaceAll(input, FORMAT_PATTERN, "");
	}

	public static void setSkin(@NotNull final Player player, @NotNull final String textureValue) {
		final ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		final GameProfile gameProfile = entityPlayer.getBukkitEntity().getProfile();
		final PropertyMap propertyMap = gameProfile.getProperties();
		final Property property = propertyMap.get(TEXTURES_KEY).iterator().next();

		propertyMap.remove(TEXTURES_KEY, property);

		propertyMap.put(TEXTURES_KEY, new Property(TEXTURES_KEY, textureValue));
	}

	public static double minAbs(@NotNull final List<Double> numbers) {
		double min = numbers.get(0);

		for (int i = 1; i < numbers.size(); i++) {
			final double currentNumber = numbers.get(i);

			if (Math.abs(currentNumber) < Math.abs(min)) {
				min = currentNumber;
			}
		}

		return min;
	}
}
