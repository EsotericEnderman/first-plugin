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

public final class MessageCommand extends AbstractCommand {
	private final RankManager rankManager;
	private final Map<UUID, UUID> recentMessages;

	public MessageCommand(@NotNull final Main plugin) {
		super(
						"message",
						"Message another player.",
						"/message <player> <message>",
						new Integer[]{-2},
						new String[]{"msg", "w"},
						"first_plugin.message",
						true
		);

		rankManager = plugin.getRankSystem().getRankManager();
		recentMessages = plugin.getRecentMessages();
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final Player player = (Player) sender;

		final String target = args[0];
		final Player targetPlayer = Bukkit.getPlayerExact(target);

		if (targetPlayer == null) {
			player.sendMessage(ChatColor.RED + "Player not found.");
			return true;
		}

		final String targetName = targetPlayer.getName();
		final String playerName = player.getName();

		final UUID playerUUID = player.getUniqueId();
		final UUID targetUUID = targetPlayer.getUniqueId();

		if (playerUUID.equals(targetUUID)) {
			player.sendMessage(ChatColor.RED + "You can't message yourself!");
			return false;
		}

		final StringBuilder message = new StringBuilder();

		for (int i = 1; i < args.length; i++) {
			message.append(args[i]).append(" ");
		}

		final Rank playerRank = rankManager.getPlayerRank(playerUUID, false);
		final Rank targetRank = rankManager.getPlayerRank(targetUUID, false);

		final String playerDisplayName = playerRank.getDisplayName();
		final String targetDisplayName = targetRank.getDisplayName();

		final String playerDisplay = playerDisplayName.equals(ChatColor.RESET.toString() + ChatColor.RESET) ? ChatColor.RESET.toString() : playerDisplayName + " ";
		final String targetDisplay = targetDisplayName.equals(ChatColor.RESET.toString() + ChatColor.RESET) ? ChatColor.RESET.toString() : targetDisplayName + " ";

		player.sendMessage(playerDisplay + "You " + ChatColor.AQUA + "» " + targetDisplay + targetName + ": " + message);
		targetPlayer.sendMessage(playerDisplay + playerName + ChatColor.AQUA + " » " + targetDisplay + "You: " + message);


		if (!recentMessages.containsKey(targetUUID) || recentMessages.get(targetUUID) != playerUUID) {
			recentMessages.put(targetUUID, playerUUID);
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
