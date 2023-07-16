package net.slqmy.first_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class HologramCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			Location playerLocation = player.getLocation();

			ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(playerLocation, EntityType.ARMOR_STAND);

			stand.setInvisible(true);
			stand.setGravity(false);
			stand.setInvulnerable(true);

			stand.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Hey there! This is some floating text.");
			stand.setCustomNameVisible(true);

			// Use the EntityInteractEvent event to make armour stands clickable.

			// Use this array syntax as there is no difference and it is shorter.
			String[] lines = {
							ChatColor.RED + "First line!",
							ChatColor.GREEN + "Second line.",
							ChatColor.WHITE + "Final line..."
			};

			playerLocation.add(new Vector(10, 0, 10));

			for (String line : lines) {
				ArmorStand armourStand = (ArmorStand) player.getWorld().spawnEntity(playerLocation, EntityType.ARMOR_STAND);

				armourStand.setInvisible(true);
				armourStand.setGravity(false);
				armourStand.setInvulnerable(true);

				armourStand.setCustomName(line);
				armourStand.setCustomNameVisible(true);

				playerLocation.subtract(new Vector(0, 0.3, 0));
			}
		}

		return false;
	}
}
