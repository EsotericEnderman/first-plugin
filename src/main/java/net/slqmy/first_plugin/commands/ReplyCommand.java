package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.Utility;
import net.slqmy.rank_system.managers.RankManager;
import net.slqmy.rank_system.types.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public final class ReplyCommand implements CommandExecutor {
	private final RankManager rankManager;
	private final Map<UUID, UUID> recentMessages;

	public ReplyCommand(@NotNull final FirstPlugin plugin) {
		this.rankManager = plugin.getRankSystem().getRankManager();
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
				final UUID targetUUID = recentMessages.get(playerUUID);

				final Player targetPlayer = Bukkit.getPlayer(targetUUID);

				if (targetPlayer == null) {
					player.sendMessage(ChatColor.RED + "That player is not online!");
				} else {
					final StringBuilder message = new StringBuilder();

					for (final String word : args) {
						message.append(word).append(" ");
					}

					final Rank playerRank = rankManager.getPlayerRank(playerUUID, false);
					final Rank targetRank = rankManager.getPlayerRank(targetUUID, false);

					final String playerDisplayName = playerRank.getDisplayName();
					final String targetDisplayName = targetRank.getDisplayName();

					final String playerDisplay = playerDisplayName.equals(ChatColor.RESET.toString() + ChatColor.RESET) ? ChatColor.RESET.toString() : playerDisplayName + " ";
					final String targetDisplay = targetDisplayName.equals(ChatColor.RESET.toString() + ChatColor.RESET) ? ChatColor.RESET.toString() : targetDisplayName + " ";

					player.sendMessage(playerDisplay + "You " + ChatColor.AQUA + "» " + targetDisplay +  targetPlayer.getName() + ": " + message);
					targetPlayer.sendMessage(playerDisplay + player.getName() + ChatColor.AQUA +  " » " + targetDisplay + "You: " + message);

					if (!recentMessages.containsKey(targetUUID) || recentMessages.get(targetUUID) != playerUUID) {
						recentMessages.put(targetUUID, playerUUID);
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
