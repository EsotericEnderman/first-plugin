package net.slqmy.first_plugin.commands;

import com.google.common.collect.Multimap;

import net.slqmy.first_plugin.utility.Utility;
import net.slqmy.first_plugin.utility.types.Pair;

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

public final class ComplexGamingMenuCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			final ItemStack duels = Utility.createItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Duels",
					(Multimap<Attribute, AttributeModifier>) null);

			final ItemStack frame = Utility.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
					ChatColor.GRAY + "Complex Gaming");

			final ItemStack duelStatus = Utility.createItem(Material.BARRIER,
					ChatColor.RED.toString() + ChatColor.BOLD + "✘" + ChatColor.RESET + ChatColor.RED + " No Active Duels "
							+ ChatColor.BOLD + "✘",
					ChatColor.WHITE.toString() + ChatColor.ITALIC + "You can start one yourself by typing:\n" + ChatColor.RESET
							+ ChatColor.GREEN + "/duel create");

			final ItemStack createDuel = Utility.createItem(Material.EMERALD_BLOCK,
					ChatColor.GREEN.toString() + ChatColor.BOLD + "✔" + ChatColor.RESET + ChatColor.GREEN + " Click to "
							+ ChatColor.UNDERLINE + "Create" + ChatColor.RESET + ChatColor.GREEN + " a Duel " + ChatColor.BOLD + "✔");

			final ItemStack duelNotifications = Utility.createItem(Material.REDSTONE,
					ChatColor.AQUA + "Duel Notifications: " + ChatColor.GREEN + ChatColor.BOLD + "✔",
					ChatColor.GREEN.toString() + ChatColor.BOLD + "✔" + ChatColor.RESET + ChatColor.GREEN + " Click to "
							+ ChatColor.UNDERLINE + "Toggle" + ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD + " ✔");

			// Maybe the inventory utility isn't as good as I thought, but I can learn from
			// this.
			final Inventory inventory = Utility.createInventory(
					ChatColor.UNDERLINE.toString() + ChatColor.DARK_GRAY + "Active Duels", 45,

					Arrays.asList(new Pair<ItemStack, Integer>(frame, 4), new Pair<ItemStack, Integer>(duels, 1),
							new Pair<ItemStack, Integer>(frame, 5),
							new Pair<ItemStack, Integer>(null, 7), new Pair<ItemStack, Integer>(frame, 2),
							new Pair<ItemStack, Integer>(null, 3), new Pair<ItemStack, Integer>(duelStatus, 1),
							new Pair<ItemStack, Integer>(null, 3), new Pair<ItemStack, Integer>(frame, 2),
							new Pair<ItemStack, Integer>(null, 7), new Pair<ItemStack, Integer>(frame, 1),
							new Pair<ItemStack, Integer>(duelNotifications, 1), new Pair<ItemStack, Integer>(frame, 3),
							new Pair<ItemStack, Integer>(createDuel, 1),
							new Pair<ItemStack, Integer>(frame, 4)));

			player.openInventory(inventory);
		} else {
			Utility.log("/complex-gaming-menu is a player-only command!");

			return false;
		}

		return true;
	}
}
