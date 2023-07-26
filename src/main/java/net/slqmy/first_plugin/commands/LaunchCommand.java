package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.types.AbstractCommand;
import net.slqmy.first_plugin.utility.VectorUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class LaunchCommand extends AbstractCommand {
	public LaunchCommand() {
		super(
				"launch",
				"Launch yourself to the specified location!",
				"/launch <x> <y> <z> <height gain>",
				new Integer[] { 4 },
				new String[] { "fling", "leap", "jump" },
				"first_plugin.launch",
				true);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
		final Player player = (Player) sender;

		try {
			player.setVelocity(
					VectorUtility.calculateLeapVelocityVector(
							player,
							new Location(
									player.getWorld(),
									Double.parseDouble(args[0]),
									Double.parseDouble(args[1]),
									Double.parseDouble(args[2])
							),
							Float.parseFloat(args[3])
					)
			);
		} catch (final NumberFormatException exception) {
			player.sendMessage(ChatColor.RED + "Invalid input!");
			return false;
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final String[] args) {
		final Player player = (Player) sender;
		final Location location = player.getLocation();

		switch (args.length) {
			case 1:
				return Arrays.asList(String.valueOf(location.getBlockX()), "~");
			case 2:
				return Arrays.asList(String.valueOf(location.getBlockY()), "~");
			case 3:
				return Arrays.asList(String.valueOf(location.getBlockZ()), "~");
		}

		return null;
	}
}
