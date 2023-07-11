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

public class CustomSkullCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			if (arguments.length == 0) {
				player.sendMessage(ChatColor.RED + "Please provide one argument!");
			} else {
				ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

				SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

				// GameProfile profile = new GameProfile(UUID.randomUUID(), null);

				skullMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + player.getName());

				player.getInventory().addItem(skull);
			}
		}

		return false;
	}
}
