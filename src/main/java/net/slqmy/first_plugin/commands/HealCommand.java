package net.slqmy.first_plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			player.sendMessage(ChatColor.GREEN + "Your health has been restored!");
			player.setHealth(player.getHealthScale());

			player.sendMessage(ChatColor.DARK_BLUE + "Your hunger has been restored!");
			player.setFoodLevel(20);

			player.sendMessage(ChatColor.AQUA + "Your saturation has been restored!");
			player.setSaturation(20);

			PotionEffectType[] negativePotionEffects = {
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

			for (PotionEffectType potionEffectType : negativePotionEffects) {
				player.removePotionEffect(potionEffectType);
			}
		}

		return false;
	}
}
