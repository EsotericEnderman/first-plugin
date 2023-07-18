package net.slqmy.first_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.utility.Utility;

public final class SkullCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

			final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
			assert skullMeta != null;
			skullMeta.setOwningPlayer(player);

			skull.setItemMeta(skullMeta);

			player.getInventory().addItem(skull);
		} else {
			Utility.log("/skull is a player-only command!");

			return false;
		}

		return true;
	}
}
