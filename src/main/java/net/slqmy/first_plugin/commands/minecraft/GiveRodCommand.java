package net.slqmy.first_plugin.commands.minecraft;

import net.slqmy.first_plugin.types.AbstractCommand;
import net.slqmy.first_plugin.utility.InventoryUtility;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GiveRodCommand extends AbstractCommand {
	public GiveRodCommand() {
		super(
						"give-rod",
						"Receive a really weird fishing rod.",
						"/give-rod",
						new Integer[]{0},
						new String[]{"rod", "give-fishing-rod", "gr", "gfr"},
						"first_plugin.give_fishing_rod",
						true);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
		final ItemStack rod = InventoryUtility.createItem(Material.FISHING_ROD, "Non-Parabolic Fishing Rod");

		((Player) sender).getInventory().addItem(rod);
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final String[] args) {
		return null;
	}
}
