package dev.enderman.minecraft.plugins.first.commands.minecraft;

import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import dev.enderman.minecraft.plugins.first.utility.InventoryUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GiveSnowballsCommand extends AbstractCommand {

	public GiveSnowballsCommand() {
		super(
						"give-snowballs",
						"Receive a non-parabolic snowball from the Minecraft gods.",
						"/give-snowballs (starting velocity) (entity type)",
						new Integer[]{0, 1, 2},
						new String[]{"gs", "snowball", "sb"},
						"first_plugin.give_snowball",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final float velocity;

		if (args.length >= 1) {
			try {
				velocity = Float.parseFloat(args[0]);
			} catch (NumberFormatException exception) {
				sender.sendMessage(ChatColor.RED + "Invalid velocity!");
				return false;
			}
		} else {
			velocity = 1F;
		}

		final EntityType entity;

		if (args.length == 2) {
			try {
				entity = EntityType.valueOf(args[1]);

				if (entity == EntityType.PLAYER || entity == EntityType.FISHING_BOBBER) {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException exception) {
				sender.sendMessage(ChatColor.RED + "Invalid entity!");
				return false;
			}
		} else {
			entity = EntityType.SNOWBALL;
		}

		final ItemStack snowball = InventoryUtility.createItem(
						Material.SNOWBALL,
						"Non-Parabolic Snowball",
						ChatColor.DARK_GRAY + "| " + ChatColor.GRAY + "Minecraft has air resistance...\n"
										+ ChatColor.GRAY + "Entity: " + ChatColor.YELLOW + ChatColor.UNDERLINE + entity + "\n" + ChatColor.GRAY
										+ "Velocity: " + ChatColor.YELLOW + ChatColor.UNDERLINE + velocity);

		snowball.setAmount(16);

		((Player) sender).getInventory().addItem(snowball);

		return true;
	}

	@Override
	public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
		if (args.length == 1) {
			return Arrays.asList("1", "2", "3", "4", "5");
		}

		final List<String> entities = new ArrayList<>();

		for (final EntityType entity : EntityType.values()) {
			if (entity != EntityType.PLAYER && entity != EntityType.FISHING_BOBBER) {
				entities.add(entity.toString());
			}
		}

		return entities;
	}
}
