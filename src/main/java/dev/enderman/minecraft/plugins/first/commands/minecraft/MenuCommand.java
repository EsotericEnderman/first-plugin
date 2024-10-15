package dev.enderman.minecraft.plugins.first.commands.minecraft;

import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class MenuCommand extends AbstractCommand {
	private final String THE_SLIMY_SWAMP = ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "The Slimy Swamp";
	private final String SLIME_GPT = ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "SlimeGPT";

	public MenuCommand() {
		super(
						"menu",
						"View a cool menu.",
						"/menu",
						new Integer[]{0},
						new String[]{"menu", "inv"},
						"first_plugin.menu",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final Player player = (Player) sender;

		// IDEA: Make an inventory GUI manager. Maybe as an enum.
		final Inventory inventory = Bukkit.createInventory(null, 45,
						ChatColor.RED.toString() + ChatColor.BOLD + "ADMIN MENU");

		// RANDOM TELEPORT.

		final ItemStack randomTeleport = new ItemStack(Material.ENDER_PEARL);
		final ItemMeta randomTeleportMeta = randomTeleport.getItemMeta();

		assert randomTeleportMeta != null;

		randomTeleportMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Random Teleport");
		randomTeleportMeta.setLore(Arrays.asList(
						ChatColor.GRAY + "Teleport to a random location in the world.",
						ChatColor.BLUE + "Very powerful!"));
		randomTeleport.setItemMeta(randomTeleportMeta);

		inventory.setItem(11, randomTeleport);

		// KILL YOURSELF.

		final ItemStack killYourself = new ItemStack(Material.LEAD);
		final ItemMeta killYourselfMeta = killYourself.getItemMeta();

		assert killYourselfMeta != null;

		killYourselfMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Kill Yourself");
		killYourselfMeta.setLore(Collections.singletonList(
						ChatColor.WHITE + "You should kill yourself, " + ChatColor.BOLD + "NOW" + ChatColor.WHITE + "!"));
		killYourself.setItemMeta(killYourselfMeta);

		inventory.setItem(13, killYourself);

		// CLEAR INVENTORY.

		final ItemStack clearInventory = new ItemStack(Material.BUCKET);
		final ItemMeta clearInventoryMeta = clearInventory.getItemMeta();

		assert clearInventoryMeta != null;

		clearInventoryMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Clear Inventory");
		clearInventoryMeta.setLore(Collections.singletonList(
						ChatColor.GRAY + "You should clear your inventory, " + ChatColor.BOLD + "NOW" + ChatColor.GRAY + "!"));
		clearInventory.setItemMeta(clearInventoryMeta);

		inventory.setItem(15, clearInventory);

		// MAGIC.

		final ItemStack magic = new ItemStack(Material.END_CRYSTAL);
		final ItemMeta magicMeta = magic.getItemMeta();

		assert magicMeta != null;

		magicMeta.setDisplayName(ChatColor.MAGIC.toString() + ChatColor.BOLD + "Magic Operator /\\*&*/\\");
		magicMeta.setLore(Arrays.asList(
						ChatColor.GRAY + "The magic operator is an operator in mathematics used only by the top mathematicians.",
						ChatColor.GRAY.toString() + ChatColor.BOLD
										+ "Only the most elite mathematicians are capable of understanding this operator...",
						ChatColor.GRAY + "This operator is also commonly used in code when the programmer is too lazy.", "",
						ChatColor.GRAY + "This operator does whatever it was intended to do.",
						ChatColor.GRAY + "In other words, it is magic. That is why it is called the " + ChatColor.ITALIC
										+ "magic operator"
										+ ChatColor.GRAY + "!"));
		magic.setItemMeta(magicMeta);

		inventory.setItem(29, magic);

		// CREATE THE SLIMY SWAMP.

		final ItemStack createTheSlimySwamp = new ItemStack(Material.SLIME_BALL);
		final ItemMeta createTheSlimySwampMeta = createTheSlimySwamp.getItemMeta();

		assert createTheSlimySwampMeta != null;

		createTheSlimySwampMeta.setDisplayName(
						ChatColor.GREEN + "Create " + THE_SLIMY_SWAMP);
		createTheSlimySwampMeta.setLore(
						Arrays.asList(ChatColor.GRAY + "Well, here it is. This is the item that will change the server forever.",
										ChatColor.GRAY + "This item quite literally creates the entirety of " + THE_SLIMY_SWAMP + ChatColor.GRAY
														+ ".",
										"",
										ChatColor.GRAY + "This is done using an advanced technology called " + SLIME_GPT
														+ ChatColor.GRAY + ".",
										SLIME_GPT + ChatColor.GRAY
														+ " is part of this plugin, it is an advanced AI.",
										SLIME_GPT + ChatColor.GRAY
														+ " will hack the system and quite literally modify the binary data in such a way that",
										THE_SLIMY_SWAMP + ChatColor.GRAY + " is complete.",
										"",
										ChatColor.RED.toString() + ChatColor.BOLD + "EXPERIMENTAL FEATURE: USE WITH CAUTION."));
		createTheSlimySwamp.setItemMeta(createTheSlimySwampMeta);

		inventory.setItem(31, createTheSlimySwamp);

		// SECRET BUTTON.

		final ItemStack secretButton = new ItemStack(Material.STICK);
		final ItemMeta secretItemMeta = secretButton.getItemMeta();

		assert secretItemMeta != null;

		secretItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Secret Button");
		secretItemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Pressing this button kills a random person in the world.",
						ChatColor.GRAY + "Delay is around one second.", ChatColor.GRAY + "Think this is fake? You can't prove it."));
		secretButton.setItemMeta(secretItemMeta);

		inventory.setItem(33, secretButton);

		// CLOSE BUTTON.

		final ItemStack closeButton = new ItemStack(Material.BARRIER);
		final ItemMeta closeButtonMeta = clearInventory.getItemMeta();
		closeButtonMeta.setDisplayName(ChatColor.RED + "Close Menu");
		closeButtonMeta.setLore(Collections.singletonList(" "));
		closeButton.setItemMeta(closeButtonMeta);

		inventory.setItem(0, closeButton);

		// FRAME.

		final ItemStack frame = new ItemStack(Material.LIME_STAINED_GLASS);
		final ItemMeta frameMeta = frame.getItemMeta();

		assert frameMeta != null;

		frameMeta.setDisplayName(" ");
		frame.setItemMeta(frameMeta);

		for (int i = 0; i < 45; i++) {
			if ((i % 9 != 0 || i == 0) && i % 9 != 8 && (i > 9 || i == 0) && i < 35) {
				continue;
			}

			inventory.setItem(i, frame);
		}

		player.openInventory(inventory);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
