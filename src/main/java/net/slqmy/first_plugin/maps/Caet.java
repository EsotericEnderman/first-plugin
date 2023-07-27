package net.slqmy.first_plugin.maps;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.utility.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public final class Caet extends MapRenderer {
	@Override
	public void render(@NotNull final MapView map, @NotNull final MapCanvas canvas, @NotNull final Player player) {
		// Idea: make a map handler / manager.

		try {
			// If the image is bigger than the map, it just goes over the map, and you can't
			// see it.
			//
			// If it's smaller it won't fully fit the frame.
			// Transparency in images works, you'll just see the map background.
			// But in item frames, it fully works.
			// JPEGs work too.
			final BufferedImage image = ImageIO.read(new URL(
							"https://cdn.discordapp.com/avatars/500690028960284672/5b556e4ef15daab8d2f8743cb443eb22.png?size=128"));

			canvas.drawImage(0, 0, image);
		} catch (final IOException exception) {
			Utility.log(exception.getMessage());
			exception.printStackTrace();
			Utility.log(exception);
		}

		canvas.drawText(1, 1, MinecraftFont.Font, "Caet! ~ <3");
	}
}
