package net.slqmy.first_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.Utility;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public final class ReplyCommand implements CommandExecutor {
	private final Map<UUID, UUID> recentMessages;

	public ReplyCommand(@NotNull final FirstPlugin plugin) {
		this.recentMessages = plugin.getRecentMessages();
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length == 0 || "".equals(Arrays.toString(args).trim())) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			final UUID playerUUID = player.getUniqueId();
			if (!recentMessages.containsKey(playerUUID)) {
				player.sendMessage(ChatColor.RED + "No one has messaged you yet!");
			} else {
				final UUID targetPlayerUUID = recentMessages.get(playerUUID);

				final Player targetPlayer = Bukkit.getPlayer(targetPlayerUUID);

				if (targetPlayer == null) {
					player.sendMessage(ChatColor.RED + "That player is not online!");
				} else {
					final StringBuilder message = new StringBuilder();

					for (final String word : args) {
						message.append(word).append(" ");
					}

					player.sendMessage(ChatColor.AQUA + "You » " + targetPlayer.getName() + ": " + ChatColor.WHITE + message);
					targetPlayer.sendMessage(ChatColor.AQUA + player.getName() + " » You: " + ChatColor.WHITE + message);

					if (!recentMessages.containsKey(targetPlayerUUID) || recentMessages.get(targetPlayerUUID) != playerUUID) {
						recentMessages.put(targetPlayerUUID, playerUUID);
					}
				}
			}
		} else {
			Utility.log("/reply is a player-only command!");

			return false;
		}

		return true;
	}
}
