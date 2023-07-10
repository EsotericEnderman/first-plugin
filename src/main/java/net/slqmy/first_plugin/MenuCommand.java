package net.slqmy.first_plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;

public class MenuCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			Inventory inventory = Bukkit.createInventory(null, 45, ChatColor.RED.toString() + ChatColor.BOLD + "ADMIN MENU");

			// RANDOM TELEPORT.

			ItemStack randomTeleport = new ItemStack(Material.ENDER_PEARL);
			ItemMeta randomTeleportMeta = randomTeleport.getItemMeta();
			randomTeleportMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Random Teleport");
			randomTeleportMeta.setLore(Arrays.asList(
							ChatColor.GRAY + "Teleport to a random location in the world.",
							ChatColor.BLUE + "Very powerful!"
			));
			randomTeleport.setItemMeta(randomTeleportMeta);

			inventory.setItem(11, randomTeleport);

			// KILL YOURSELF.

			ItemStack killYourself = new ItemStack(Material.LEAD);
			ItemMeta killYourselfMeta = killYourself.getItemMeta();
			killYourselfMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Kill Yourself");
			killYourselfMeta.setLore(Collections.singletonList(ChatColor.WHITE + "You should kill yourself, " + ChatColor.BOLD + "NOW" + ChatColor.WHITE + "!"));
			killYourself.setItemMeta(killYourselfMeta);

			inventory.setItem(13, killYourself);

			// CLEAR INVENTORY.

			ItemStack clearInventory = new ItemStack(Material.BUCKET);
			ItemMeta clearInventoryMeta = clearInventory.getItemMeta();
			clearInventoryMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Clear Inventory");
			clearInventoryMeta.setLore(Collections.singletonList(ChatColor.GRAY + "You should clear your inventory, " + ChatColor.BOLD + "NOW" + ChatColor.GRAY + "!"));
			clearInventory.setItemMeta(clearInventoryMeta);

			inventory.setItem(15, clearInventory);

			// MAGIC

			ItemStack magic = new ItemStack(Material.END_CRYSTAL);
			ItemMeta magicMeta = magic.getItemMeta();
			magicMeta.setDisplayName(ChatColor.MAGIC.toString() + ChatColor.BOLD + "Magic Operator /\\*&*/\\");
			magicMeta.setLore(Arrays.asList(ChatColor.GRAY + "The magic operator is an operator in mathematics used only by the top mathematicians.", ChatColor.BOLD + "Only the most elite mathematicians are capable of understanding this operator", "This operator is also commonly used in code when the programmer is too lazy.", "", "This operator does whatever it was intended to do.", "In other words, it is magic. That is why it is called the " + ChatColor.ITALIC + "magic operator" + ChatColor.RESET + "!"));
			magic.setItemMeta(magicMeta);

			inventory.setItem(29, magic);

			// CREATE THE SLIMY SWAMP.

			ItemStack createTheSlimySwamp = new ItemStack(Material.SLIME_BALL);
			ItemMeta createTheSlimySwampMeta = createTheSlimySwamp.getItemMeta();
			createTheSlimySwampMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Create The Slimy Swamp");
			createTheSlimySwampMeta.setLore(Arrays.asList(ChatColor.GRAY + "Well, here it is. This is the item that will change the server forever.", "This item quite literally creates the entirety of The Slimy Swamp.", "", "This is done using an advanced technology called SlimeGPT.", "SlimeGPT is part of this plugin, it is an advanced AI.", "SlimeGPT will hack the system and quite literally modify the binary data in such a way that", "The Slimy Swamp is complete", "", ChatColor.RED.toString() + ChatColor.BOLD + "EXPERIMENTAL FEATURE: USE WITH CAUTION."));
			createTheSlimySwamp.setItemMeta(createTheSlimySwampMeta);

			inventory.setItem(31, createTheSlimySwamp);

			// SECRET BUTTON.

			ItemStack secretButton = new ItemStack(Material.STICK);
			ItemMeta secretItemMeta = secretButton.getItemMeta();
			secretItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Secret Button");
			secretItemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Pressing this button kills a random person in the world.", "Delay is around one second", "Think this is fake? You can't prove it"));
			secretButton.setItemMeta(secretItemMeta);

			inventory.setItem(33, secretButton);

			// CLOSE BUTTON.

			ItemStack closeButton = new ItemStack(Material.BARRIER);
			ItemMeta closeButtonMeta = clearInventory.getItemMeta();
			closeButtonMeta.setDisplayName(ChatColor.RED + "Close Menu");
			closeButtonMeta.setLore(Collections.singletonList(" "));
			closeButton.setItemMeta(closeButtonMeta);

			inventory.setItem(0, closeButton);

			// FRAME

			ItemStack frame = new ItemStack(Material.LIME_STAINED_GLASS);
			ItemMeta frameMeta = frame.getItemMeta();
			frameMeta.setDisplayName(" ");
			frame.setItemMeta(frameMeta);

			for (int i = 0; i < 45; i++) {
				if ((i % 9 != 0 || i == 0) && i % 9 != 8 && (i > 9 || i == 0) && i < 35) {
					continue;
				}

				inventory.setItem(i, frame);
			}

			player.openInventory(inventory);
		}

		return false;
	}
}
