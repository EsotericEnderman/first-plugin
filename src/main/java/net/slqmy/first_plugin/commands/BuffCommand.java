package net.slqmy.first_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.utility.Utility;

import java.util.UUID;

public final class BuffCommand implements CommandExecutor {
	private static final int GENERIC_ATTACK_DAMAGE_INCREASE = 10;

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			final ItemStack playerHeldItem = player.getInventory().getItemInMainHand();

			if (playerHeldItem.getType() == Material.AIR) {
				player.sendMessage(ChatColor.RED + "You must be holding an item to run this command!");

				return true;
			}

			final AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),
					Attribute.GENERIC_ATTACK_DAMAGE.toString(), GENERIC_ATTACK_DAMAGE_INCREASE,
					AttributeModifier.Operation.ADD_NUMBER,
					EquipmentSlot.HAND);

			final ItemMeta mainHandMeta = playerHeldItem.getItemMeta();

			mainHandMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

			playerHeldItem.setItemMeta(mainHandMeta);

			player.sendMessage(ChatColor.LIGHT_PURPLE + "Item buffed! " + ChatColor.AQUA + "Have fun!");
		} else {
			Utility.log("/buff is a player-only command!");

			return false;
		}

		return true;
	}
}
