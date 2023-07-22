package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.types.Command;
import net.slqmy.first_plugin.utility.InventoryUtility;
import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class GiveSnowballsCommand extends Command {

	public GiveSnowballsCommand() {
		super(
						"give-snowballs",
						"Receive a non-parabolic snowball from the Minecraft gods.",
						"/give-snowballs <starting velocity>",
						new Integer[] { 0 },
						new String[] { "gs", "snowball", "sb" },
						"first_plugin.give_snowball",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final ItemStack snowball = InventoryUtility.createItem(Material.SNOWBALL,
					ChatColor.RESET + "Non-Parabolic Snowball", ChatColor.GRAY + "Minecraft has air resistance...");

			snowball.setAmount(16);

			((Player) sender).getInventory().addItem(snowball);
		} else {
			Utility.log("/give-snowball is a player-only command!");
			return false;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return Arrays.asList("1", "2", "3", "4", "5");
	}
}
