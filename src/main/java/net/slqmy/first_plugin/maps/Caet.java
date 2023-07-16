package net.slqmy.first_plugin.maps;

import org.bukkit.entity.Player;
import org.bukkit.map.*;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Caet extends MapRenderer {

	@Override
	public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
		try {
			// If the image is bigger than the map, it just goes over the map.

			// Can also use 64x64, but it won't fully fit the frame.
			// Transparency in images works.
			// JPEGs work too.
			BufferedImage image = ImageIO.read(new URL("https://cdn.discordapp.com/avatars/500690028960284672/5b556e4ef15daab8d2f8743cb443eb22.png?size=128"));

			canvas.drawImage(1, 1, image);
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		canvas.drawText(60, 90, MinecraftFont.Font, "Caet!");

		for (int x = 5; x <= 15; x++) {
			for (int y = 22; y <= 32; y++) {
				canvas.setPixel(x, y, MapPalette.LIGHT_GREEN);
			}
		}

		canvas.setPixel(1, 1, MapPalette.RED);
		canvas.setPixel(128, 128, MapPalette.BLUE);
	}
}
