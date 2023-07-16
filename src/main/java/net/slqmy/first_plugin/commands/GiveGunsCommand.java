package net.slqmy.first_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class GiveGunsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			PlayerInventory playerInventory = player.getInventory();

			ItemStack woodenHoe = new ItemStack(Material.WOODEN_HOE);
			ItemStack ironHoe = new ItemStack(Material.IRON_HOE);
			ItemStack netheriteHoe = new ItemStack(Material.NETHERITE_HOE);
			ItemStack goldenHoe = new ItemStack(Material.GOLDEN_HOE);

			ItemMeta woodenHoeMeta = woodenHoe.getItemMeta();
			ItemMeta ironHoeMeta = ironHoe.getItemMeta();
			ItemMeta netheriteHoeMeta = netheriteHoe.getItemMeta();
			ItemMeta goldenHoeMeta = goldenHoe.getItemMeta();

			woodenHoeMeta.setDisplayName(ChatColor.AQUA + "Revolver");
			ironHoeMeta.setDisplayName(ChatColor.DARK_BLUE + "Mini-gun");
			netheriteHoeMeta.setDisplayName(ChatColor.RED + "Shotgun");
			goldenHoeMeta.setDisplayName(ChatColor.DARK_GREEN + "Pistol");

			woodenHoe.setItemMeta(woodenHoeMeta);
			ironHoe.setItemMeta(ironHoeMeta);
			netheriteHoe.setItemMeta(netheriteHoeMeta);
			goldenHoe.setItemMeta(goldenHoeMeta);

			playerInventory.addItem(woodenHoe);
			playerInventory.addItem(ironHoe);
			playerInventory.addItem(netheriteHoe);
			playerInventory.addItem(goldenHoe);
		}

		return false;
	}
}
