package net.slqmy.first_plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class SkullCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

			SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
			skullMeta.setOwningPlayer(player);
			skullMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + player.getName());

			player.getInventory().addItem(skull);
		}

		return false;
	}
}
