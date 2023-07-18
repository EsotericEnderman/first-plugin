package net.slqmy.first_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.utility.Utility;

public final class HealCommand implements CommandExecutor {
	private static final int MAX_FOOD_LEVEL = 20;
	private static final int MAX_SATURATION = 20;
	private static final PotionEffectType[] negativePotionEffects = {
			PotionEffectType.BLINDNESS,
			PotionEffectType.CONFUSION,
			PotionEffectType.BAD_OMEN,
			PotionEffectType.DARKNESS,
			PotionEffectType.HARM,
			PotionEffectType.HUNGER,
			PotionEffectType.LEVITATION,
			PotionEffectType.POISON,
			PotionEffectType.SLOW,
			PotionEffectType.SLOW_DIGGING,
			PotionEffectType.UNLUCK,
			PotionEffectType.WEAKNESS,
			PotionEffectType.WITHER,
	};
	private static final String BAR = ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "| ";

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			player.sendMessage("");
			player.sendMessage(BAR + ChatColor.LIGHT_PURPLE + "Your health has been restored!");
			player.setHealth(player.getHealthScale());

			player.sendMessage(BAR + ChatColor.GOLD + "Your hunger has been satiated!");
			player.setFoodLevel(MAX_FOOD_LEVEL);

			player.sendMessage(BAR + ChatColor.DARK_RED + "Your saturation has been restored!");
			player.setSaturation(MAX_SATURATION);

			for (final PotionEffectType potionEffectType : negativePotionEffects) {
				player.removePotionEffect(potionEffectType);
			}

			player.sendMessage(BAR + ChatColor.DARK_PURPLE + "You have been cured of all negative potion effects!");

			player.setFireTicks(0);

			player.sendMessage(BAR + ChatColor.AQUA + "You have been extinguished!");
			player.sendMessage("");
		} else {
			Utility.log("/heal is a player-only command!");

			return false;
		}

		return true;
	}
}
