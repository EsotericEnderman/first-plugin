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

import net.slqmy.first_plugin.utility.Utility;

public final class GiveBannerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;
			final PlayerInventory playerInventory = player.getInventory();

			final ItemStack banner = new ItemStack(Material.BLACK_BANNER);
			final BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

			assert bannerMeta != null;
			bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.SKULL));
			bannerMeta.addPattern(new Pattern(DyeColor.RED, PatternType.GRADIENT));
			bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP));
			bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM));
			bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.SKULL));

			banner.setItemMeta(bannerMeta);

			playerInventory.addItem(banner);
		} else {
			Utility.log("/give-banner is a player-only command!");

			return false;
		}

		return true;
	}
}
