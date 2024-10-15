package dev.enderman.minecraft.plugins.first.commands.minecraft;

import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SkullCommand extends AbstractCommand {
	public SkullCommand() {
		super(
						"skull",
						"Get your (or someone else's) skull! (player head). Despite what people say, this command has nothing to do with the skull emoji.",
						"/skull (player name)",
						new Integer[]{0, 1},
						new String[]{"head", "player-skull"},
						"first_plugin.skull",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final Player player = (Player) sender;

		final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		assert skullMeta != null;

		if (args.length == 0) {
			skullMeta.setOwningPlayer(player);
		} else {
			final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

			if (target.getName() == null) {
				player.sendMessage(ChatColor.RED + "Player not found!");

				return false;
			}

			skullMeta.setOwningPlayer(target);
		}

		skull.setItemMeta(skullMeta);

		player.getInventory().addItem(skull);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
