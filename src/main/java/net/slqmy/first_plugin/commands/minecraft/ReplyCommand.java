package net.slqmy.first_plugin.commands.minecraft;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.types.AbstractCommand;
import net.slqmy.rank_system.managers.RankManager;
import net.slqmy.rank_system.types.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ReplyCommand extends AbstractCommand {
	private final RankManager rankManager;
	private final Map<UUID, UUID> recentMessages;

	public ReplyCommand(@NotNull final Main plugin) {
		super(
						"reply",
						"Reply to a message from another player.",
						"/reply <message>",
						new Integer[]{-1},
						new String[]{"r"},
						"first_plugin.reply",
						true
		);

		rankManager = plugin.getRankSystem().getRankManager();
		recentMessages = plugin.getRecentMessages();
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
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

				player.sendMessage(playerDisplay + "You " + ChatColor.AQUA + "» " + targetDisplay + targetPlayer.getName() + ": " + message);
				targetPlayer.sendMessage(playerDisplay + player.getName() + ChatColor.AQUA + " » " + targetDisplay + "You: " + message);

				if (!recentMessages.containsKey(targetUUID) || recentMessages.get(targetUUID) != playerUUID) {
					recentMessages.put(targetUUID, playerUUID);
				}
			}
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
