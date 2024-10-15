package dev.enderman.minecraft.plugins.first.commands.minecraft;

import me.clip.placeholderapi.PlaceholderAPI;
import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class PAPICommand extends AbstractCommand {
	public PAPICommand() {
		super(
						"papi-test",
						"Test the PAPI placeholder I made.",
						"/papi-test",
						new Integer[] { 0 },
						new String[]{ "pt", "papit" },
						"first_plugin.papi_command",
						true
		);
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
		sender.sendMessage(
						PlaceholderAPI.setPlaceholders((Player) sender, ChatColor.GREEN + "The highest group on this server is %luckperms_highest_group_by_weight%.\nI am holding sponge. %is_holding_sponge%")
		);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
