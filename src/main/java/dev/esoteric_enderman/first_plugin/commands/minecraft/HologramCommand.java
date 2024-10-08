package dev.esoteric_enderman.first_plugin.commands.minecraft;

import dev.esoteric_enderman.first_plugin.types.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class HologramCommand extends AbstractCommand {
	private final float LINE_HEIGHT = 0.3F;

	public HologramCommand() {
		super(
						"hologram",
						"Spawn a cool hologram!",
						"/hologram",
						new Integer[]{0},
						new String[]{"holo", "hg"},
						"first_plugin.hologram",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
		final Player player = (Player) sender;

		final Location playerLocation = player.getLocation();

		// Note: Use the EntityInteractEvent event to make armour stands clickable.
		//
		// Use this array syntax as there is no difference and it is shorter.
		// Maybe make a utility method for creating armour stands...? (if I actually
		// need to make armour stands for something)
		final String[] lines = {
						ChatColor.AQUA.toString() + ChatColor.BOLD + "Hey there! I am a bit of floating text.",
						ChatColor.GREEN + "Hope you're doing well.",
						ChatColor.RED + "It's difficult to survive with this plugin on the server...",
						"But you can do it! Tip: receive some " + ChatColor.BOLD + "OP" + ChatColor.RESET
										+ " guns by typing " + ChatColor.UNDERLINE + "/give-guns" + ChatColor.RESET + " in chat!"
		};

		for (final String line : lines) {
			final ArmorStand armourStand = (ArmorStand) player.getWorld().spawnEntity(playerLocation,
							EntityType.ARMOR_STAND);

			armourStand.setInvisible(true);
			armourStand.setGravity(false);
			armourStand.setInvulnerable(true);

			armourStand.setCustomName(line);
			armourStand.setCustomNameVisible(true);

			playerLocation.subtract(new Vector(0, LINE_HEIGHT, 0));
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
