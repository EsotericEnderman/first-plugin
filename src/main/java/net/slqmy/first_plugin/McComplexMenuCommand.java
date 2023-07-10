package net.slqmy.first_plugin;

import jdk.internal.net.http.common.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class McComplexMenuCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			ItemStack duels = Utility.createItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Duels");
			ItemStack frame = Utility.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ChatColor.GRAY + "Complex Gaming");
			ItemStack duelStatus = Utility.createItem(Material.BARRIER, ChatColor.RED.toString() + ChatColor.BOLD + "✘" + ChatColor.RESET + ChatColor.RED + "No Active Duels " + ChatColor.BOLD + "✘", ChatColor.ITALIC + "You can start one yourself by typing:\n" + ChatColor.RESET + ChatColor.GREEN +  "/duel create");
			ItemStack createDuel = Utility.createItem(Material.EMERALD_BLOCK, ChatColor.GREEN.toString() + ChatColor.BOLD +  "✔" + ChatColor.RESET + ChatColor.GREEN + "Click to " + ChatColor.UNDERLINE + "Create" + ChatColor.RESET + ChatColor.GREEN + " a Duel " + ChatColor.BOLD + "✔");
			ItemStack duelNotifications = Utility.createItem(Material.REDSTONE, ChatColor.AQUA + "Duel Notifications: " + ChatColor.GREEN + ChatColor.BOLD + "✔", ChatColor.GREEN.toString() + ChatColor.BOLD + "✔" + ChatColor.RESET + ChatColor.GREEN + " Click to " + ChatColor.UNDERLINE + "Toggle" + ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD + " ✔");

			// Maybe the inventory utility isn't as good as I thought, but I can learn from this.
			Inventory inventory = Utility.createInventory(ChatColor.UNDERLINE.toString() + ChatColor.GRAY + "Active Duels", 45, Arrays.asList(new Pair<>(frame, 4), new Pair<>(duels, 1), new Pair<>(frame, 5), new Pair<>(null, 7), new Pair<>(frame, 2), new Pair<>(null, 4), new Pair<>(duelStatus, 1), new Pair<>(null, 4), new Pair<>(frame, 2), new Pair<>(null, 7), new Pair<>(duelNotifications, 1), new Pair<>(frame, 3), new Pair<>(createDuel, 1), new Pair<>(frame, 4)) );

			player.openInventory(inventory);
		}

		return false;
	}
}
