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

import java.util.UUID;

public class BuffCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			ItemStack playerHeldItem = player.getInventory().getItemInMainHand();

			if (playerHeldItem != null && playerHeldItem.getType() != Material.AIR) {
				AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_ATTACK_DAMAGE.toString(), 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

				ItemMeta mainHandMeta = playerHeldItem.getItemMeta();
				mainHandMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
				playerHeldItem.setItemMeta(mainHandMeta);

				player.sendMessage(ChatColor.MAGIC + "Item buffed!");
			}
		}

		return false;
	}
}
