package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.Utility;
import net.slqmy.first_plugin.utility.types.Cuboid;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class FillCommand implements CommandExecutor {
	private static final int ARGUMENT_LENGTH = 7;
	private Cuboid latestFill;

	public FillCommand(@NotNull final FirstPlugin plugin) {
		this.latestFill = plugin.getLatestFill();
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != ARGUMENT_LENGTH || "".equals(Arrays.toString(args).trim())) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			final int x1;
			final int y1;
			final int z1;
			final int x2;
			final int y2;
			final int z2;

			try {
				x1 = Integer.parseInt(args[0]);
				y1 = Integer.parseInt(args[1]);
				z1 = Integer.parseInt(args[2]);
				x2 = Integer.parseInt(args[3]);
				y2 = Integer.parseInt(args[4]);
				z2 = Integer.parseInt(args[5]);
			} catch (final NumberFormatException exception) {
				player.sendMessage(ChatColor.RED + "Invalid locations! Must be integer numbers.");
				return false;
			}

			final World world = player.getWorld();

			latestFill = new Cuboid(
					new Location(world, x1, y1, z1),
					new Location(world, x2, y2, z2));

			final Material blockType;

			try {
				blockType = Material.valueOf(args[6]);

				for (final Block block : latestFill) {
					block.setType(blockType);
				}
			} catch (final Exception exception) {
				player.sendMessage(ChatColor.RED + "Invalid block type!");
				return false;
			}

			player
					.sendMessage(
							ChatColor.GREEN + "Successfully filled a cuboid with block type " + ChatColor.RESET + ChatColor.BOLD
									+ blockType + ChatColor.GREEN + "!");
		} else {
			Utility.log("/fill is a player-only command!");
		}

		return true;
	}
}
