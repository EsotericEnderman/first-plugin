package dev.esoteric_enderman.first_plugin.events.listeners.minecraft.server;

import dev.esoteric_enderman.first_plugin.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class ServerListPingEventListener implements Listener {
	private final File SERVER_ICON_PNG_FILE = new File("assets/server-icon.png");

	@EventHandler
	public void onServerListPing(@NotNull final ServerListPingEvent event) {
		event.setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);

		event.setMotd(
						ChatColor.BOLD + "| " + ChatColor.GREEN + ChatColor.BOLD + "Local host server " + ChatColor.RESET
										+ ChatColor.BOLD + "-"
										+ ChatColor.AQUA
										+ " for testing my plugins!\n" + ChatColor.RESET
										+ ChatColor.BOLD + "| " + ChatColor.YELLOW + "Enjoy your stay and remember to " + ChatColor.BOLD
										+ "work hard" + ChatColor.YELLOW
										+ "!");

		try {
			event.setServerIcon(Bukkit.loadServerIcon(SERVER_ICON_PNG_FILE));
		} catch (final Exception exception) {
			Utility.log(exception.getMessage());
			exception.printStackTrace();
			Utility.log(exception);
		}
	}
}
