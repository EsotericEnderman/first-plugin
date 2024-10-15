package dev.enderman.minecraft.plugins.first.commands.minecraft;

import dev.enderman.minecraft.plugins.first.FirstPlugin;
import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import dev.enderman.minecraft.plugins.first.utility.types.Cuboid;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class FillCommand extends AbstractCommand {
	private final FirstPlugin plugin;

	public FillCommand(@NotNull final FirstPlugin plugin) {
		super(
						"fill",
						"Fill a region with a certain type of block!",
						"/fill <x1> <y1> <z1> <x2> <y2> <z2> <block>",
						new Integer[]{7},
						new String[]{"set"},
						"first_plugin.fill",
						true
		);

		this.plugin = plugin;
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final Player player = (Player) sender;

		final int x1, y1, z1, x2, y2, z2;

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

		final Cuboid latestFill = new Cuboid(
						new Location(world, x1, y1, z1),
						new Location(world, x2, y2, z2));

		plugin.setLatestFill(latestFill);

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

		player.sendMessage(
						ChatColor.GREEN + "Successfully filled cuboid with block type " + ChatColor.RESET + ChatColor.BOLD
										+ blockType + ChatColor.GREEN + "!"
		);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
