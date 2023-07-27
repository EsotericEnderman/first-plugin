/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Hoglin
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 */
package net.slqmy.first_plugin.commands.minecraft;

import net.slqmy.first_plugin.events.custom_events.CustomEntitySpawnEvent;
import net.slqmy.first_plugin.types.AbstractCommand;
import net.slqmy.first_plugin.utility.HoglinRiderUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SpawnRiderCommand extends AbstractCommand {
	private final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();

	public SpawnRiderCommand() {
		super(
						"spawn-rider",
						"Spawn a hoglin rider! Good luck fighting it!",
						"/spawn-rider (x) (y) (z)",
						new Integer[]{0, 3},
						new String[]{"hog-rider", "rider", "hoglin-rider", "sr", "hr"},
						"first_plugin.spawn_hoglin_rider",
						true
		);
	}

	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final Player player = (Player) sender;

		final Location playerLocation = player.getLocation();
		final Location spawnLocation = playerLocation.clone();

		try {
			spawnLocation.setX("~".equals(args[0]) ? spawnLocation.getX() : Integer.parseInt(args[0]));
			spawnLocation.setY("~".equals(args[1]) ? spawnLocation.getY() : Integer.parseInt(args[1]));
			spawnLocation.setZ("~".equals(args[2]) ? spawnLocation.getZ() : Integer.parseInt(args[2]));
		} catch (final NumberFormatException exception) {
			player.sendMessage(ChatColor.RED + "Invalid coordinates! Must be integer numbers.");

			return false;
		} catch (final ArrayIndexOutOfBoundsException ignoredException) {
		}

		final World world = player.getWorld();

		final Hoglin hoglin = HoglinRiderUtility.spawnHoglinRider(world, spawnLocation);
		final CustomEntitySpawnEvent event = new CustomEntitySpawnEvent(hoglin, spawnLocation);

		PLUGIN_MANAGER.callEvent(event);

		return true;
	}

	public @NotNull List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final Player player = (Player) sender;
		final Location location = player.getLocation();

		switch (args.length) {
			case 1:
				return StringUtil.copyPartialMatches(args[0], Arrays.asList(String.valueOf(location.getBlockX()), "~"),
								new ArrayList<>());
			case 2:
				return StringUtil.copyPartialMatches(args[1], Arrays.asList(String.valueOf(location.getBlockY()), "~"),
								new ArrayList<>());
			case 3:
				return StringUtil.copyPartialMatches(args[2], Arrays.asList(String.valueOf(location.getBlockZ()), "~"),
								new ArrayList<>());
		}

		return new ArrayList<>();
	}
}
