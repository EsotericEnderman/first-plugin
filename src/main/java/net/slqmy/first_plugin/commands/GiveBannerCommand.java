package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.types.AbstractCommand;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GiveBannerCommand extends AbstractCommand {

	public GiveBannerCommand() {
		super(
						"give-banner",
						"Give yourself a fancy banner that I definitely didn't steal from the Hoglin Rider plugin.",
						"/give-banner",
						new Integer[]{0},
						new String[]{"gb", "banner"},
						"first_plugin.banner",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
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

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
