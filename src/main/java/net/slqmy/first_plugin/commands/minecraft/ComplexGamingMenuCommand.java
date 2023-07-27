package net.slqmy.first_plugin.commands;

import com.google.common.collect.Multimap;
import net.slqmy.first_plugin.types.AbstractCommand;
import net.slqmy.first_plugin.utility.InventoryUtility;
import net.slqmy.first_plugin.utility.types.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public final class ComplexGamingMenuCommand extends AbstractCommand {

	public ComplexGamingMenuCommand() {
		super(
						"complex-gaming-menu",
						"View a menu that I copied from this random server called complex gaming.",
						"/complex-gaming-menu",
						new Integer[]{0},
						new String[]{"cgm"},
						"first_plugin.complex_gaming_menu",
						true
		);
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

		final Player player = (Player) sender;

		final ItemStack duels = InventoryUtility.createItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Duels",
						(Multimap<Attribute, AttributeModifier>) null);

		final ItemStack frame = InventoryUtility.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
						ChatColor.GRAY + "Complex Gaming");

		final ItemStack duelStatus = InventoryUtility.createItem(Material.BARRIER,
						ChatColor.RED.toString() + ChatColor.BOLD + "✘" + ChatColor.RESET + ChatColor.RED + " No Active Duels "
										+ ChatColor.BOLD + "✘",
						ChatColor.WHITE.toString() + ChatColor.ITALIC + "You can start one yourself by typing:\n" + ChatColor.RESET
										+ ChatColor.GREEN + "/duel create");

		final ItemStack createDuel = InventoryUtility.createItem(Material.EMERALD_BLOCK,
						ChatColor.GREEN.toString() + ChatColor.BOLD + "✔" + ChatColor.RESET + ChatColor.GREEN + " Click to "
										+ ChatColor.UNDERLINE + "Create" + ChatColor.RESET + ChatColor.GREEN + " a Duel " + ChatColor.BOLD + "✔");

		final ItemStack duelNotifications = InventoryUtility.createItem(Material.REDSTONE,
						ChatColor.AQUA + "Duel Notifications: " + ChatColor.GREEN + ChatColor.BOLD + "✔",
						ChatColor.GREEN.toString() + ChatColor.BOLD + "✔" + ChatColor.RESET + ChatColor.GREEN + " Click to "
										+ ChatColor.UNDERLINE + "Toggle" + ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD + " ✔");

		// Maybe the inventory utility isn't as good as I thought, but I can learn from
		// this.
		final Inventory inventory = InventoryUtility.createInventory(
						ChatColor.UNDERLINE.toString() + ChatColor.DARK_GRAY + "Active Duels", 45,
						Arrays.asList(new Pair<>(frame, 4), new Pair<>(duels, 1),
										new Pair<>(frame, 5),
										new Pair<>(null, 7), new Pair<>(frame, 2),
										new Pair<>(null, 3), new Pair<>(duelStatus, 1),
										new Pair<>(null, 3), new Pair<>(frame, 2),
										new Pair<>(null, 7), new Pair<>(frame, 1),
										new Pair<>(duelNotifications, 1), new Pair<>(frame, 3),
										new Pair<>(createDuel, 1),
										new Pair<>(frame, 4)
						)
		);

		player.openInventory(inventory);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
