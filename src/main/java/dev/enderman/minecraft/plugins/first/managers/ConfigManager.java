package dev.enderman.minecraft.plugins.first.managers;

import dev.enderman.minecraft.plugins.first.FirstPlugin;
import dev.enderman.minecraft.plugins.first.utility.Utility;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

// Can also use something like https://crowdin.com/.

public final class ConfigManager {
	private YamlConfiguration configuration;

	public ConfigManager(@NotNull final FirstPlugin plugin) {
		final File file = new File(plugin.getDataFolder(), Objects.requireNonNull(plugin.getConfig().getString("Language")) + ".yml");

		if (file.exists()) {
			configuration = YamlConfiguration.loadConfiguration(file);
		} else {
			Utility.log("Language file does not exist!");
		}
	}

	// Can also use enum members as keys.
	// Better (much cleaner and easier to refactor) but takes more time.
	public String getMessage(@NotNull final String key) {
		// Could also make this method static, but that violates OOP ;-;.

		return configuration.getString(key); // Good idea to make a cache if using on a large server.
	}
}
