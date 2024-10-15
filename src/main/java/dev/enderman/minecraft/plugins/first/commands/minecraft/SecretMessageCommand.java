package dev.enderman.minecraft.plugins.first.commands.minecraft;

import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SecretMessageCommand extends AbstractCommand {
	public SecretMessageCommand() {
		super(
						"secret-message",
						"View the secret message... what could that be?",
						"/secret-message",
						new Integer[]{0},
						new String[]{"sm"},
						"first_plugin.secret_command",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
		final Player player = (Player) sender;

		if (player.hasPermission("first_plugin.secret_message")) {
			player.sendMessage(
							ChatColor.GREEN + "The secret message is... " + ChatColor.BOLD + "'troll'" + ChatColor.GREEN + "!");
		} else {
			player.sendMessage(ChatColor.RED + "You don't have permission to see the secret message... get "
							+ ChatColor.BOLD + "'troll'" + ChatColor.RED + "-ed!");
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
