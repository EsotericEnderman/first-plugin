package dev.esoteric_enderman.first_plugin.utility;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class DebugUtility {
	private static final Logger LOGGER = Bukkit.getLogger();

	public static void logError(@NotNull final Exception exception, @NotNull final String message) {
		LOGGER.severe(message);
		LOGGER.severe(exception.getMessage());
		LOGGER.severe(exception.toString());
		exception.printStackTrace();
	}
}
