package net.slqmy.first_plugin.commands;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BannerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			PlayerInventory playerInventory = player.getInventory();

			ItemStack banner = new ItemStack(Material.WHITE_BANNER);
			BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

			List<Pattern> patterns = new ArrayList<>();

			patterns.add(new Pattern(DyeColor.LIME, PatternType.CREEPER));
			patterns.add(new Pattern(DyeColor.LIGHT_BLUE, PatternType.STRIPE_BOTTOM));
			patterns.add(new Pattern(DyeColor.BLUE, PatternType.STRIPE_TOP));
			patterns.add(new Pattern(DyeColor.ORANGE, PatternType.BORDER));
			bannerMeta.setPatterns(patterns);

			banner.setItemMeta(bannerMeta);

			playerInventory.addItem(banner);
		}

		return false;
	}
}
