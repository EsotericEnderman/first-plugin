package net.slqmy.first_plugin;

import com.google.common.collect.Multimap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
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

			ItemStack duels = Utility.createItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Duels", (Multimap<Attribute, AttributeModifier>) null);
			ItemStack frame = Utility.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ChatColor.GRAY + "Complex Gaming");
			ItemStack duelStatus = Utility.createItem(Material.BARRIER, ChatColor.RED.toString() + ChatColor.BOLD + "✘" + ChatColor.RESET + ChatColor.RED + " No Active Duels " + ChatColor.BOLD + "✘", ChatColor.WHITE.toString() + ChatColor.ITALIC + "You can start one yourself by typing:\n" + ChatColor.RESET + ChatColor.GREEN +  "/duel create");
			ItemStack createDuel = Utility.createItem(Material.EMERALD_BLOCK, ChatColor.GREEN.toString() + ChatColor.BOLD +  "✔" + ChatColor.RESET + ChatColor.GREEN + " Click to " + ChatColor.UNDERLINE + "Create" + ChatColor.RESET + ChatColor.GREEN + " a Duel " + ChatColor.BOLD + "✔");
			ItemStack duelNotifications = Utility.createItem(Material.REDSTONE, ChatColor.AQUA + "Duel Notifications: " + ChatColor.GREEN + ChatColor.BOLD + "✔", ChatColor.GREEN.toString() + ChatColor.BOLD + "✔" + ChatColor.RESET + ChatColor.GREEN + " Click to " + ChatColor.UNDERLINE + "Toggle" + ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD + " ✔");

			// Maybe the inventory utility isn't as good as I thought, but I can learn from this.
			Inventory inventory = Utility.createInventory(ChatColor.UNDERLINE.toString() + ChatColor.DARK_GRAY + "Active Duels", 45, Arrays.asList(Arrays.asList(frame, 4), Arrays.asList(duels, 1), Arrays.asList(frame, 5), Arrays.asList(null, 7), Arrays.asList(frame, 2), Arrays.asList(null, 3), Arrays.asList(duelStatus, 1), Arrays.asList(null, 3), Arrays.asList(frame, 2), Arrays.asList(null, 7), Arrays.asList(duelNotifications, 1), Arrays.asList(frame, 3), Arrays.asList(createDuel, 1), Arrays.asList(frame, 4)));

			player.openInventory(inventory);
		}

		return false;
	}
}
