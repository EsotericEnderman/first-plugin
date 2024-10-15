package dev.enderman.minecraft.plugins.first.commands.minecraft;

import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class HealCommand extends AbstractCommand {
	private static final int MAX_FOOD_LEVEL = 20;

	private static final int MAX_SATURATION = 20;

	private static final PotionEffectType[] negativePotionEffects = {
					PotionEffectType.BLINDNESS,
					PotionEffectType.BAD_OMEN,
					PotionEffectType.DARKNESS,
					PotionEffectType.INSTANT_DAMAGE,
					PotionEffectType.HUNGER,
					PotionEffectType.LEVITATION,
					PotionEffectType.POISON,
					PotionEffectType.SLOWNESS,
					PotionEffectType.MINING_FATIGUE,
					PotionEffectType.UNLUCK,
					PotionEffectType.WEAKNESS,
					PotionEffectType.WITHER,
	};

	private static final String BAR = ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "| ";

	public HealCommand() {
		super(
						"heal",
						"Heal yourself, satiate your hunger, restore your saturation, cure yourself of all bad potions effects, and be extinguished.",
						"/heal",
						new Integer[]{0},
						new String[]{"health", "h"},
						"first_plugin.heal",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
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

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
