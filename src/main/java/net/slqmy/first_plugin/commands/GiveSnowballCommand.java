package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.utility.InventoryUtility;
import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GiveSnowballCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label, @NotNull final String[] args) {
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
}
