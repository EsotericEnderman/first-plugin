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

public final class MessageCommand implements CommandExecutor {
	private static final int ARGUMENT_LENGTH = 2;

	private final Map<UUID, UUID> recentMessages;

	public MessageCommand(@NotNull final FirstPlugin plugin) {
		this.recentMessages = plugin.getRecentMessages();
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length < ARGUMENT_LENGTH || "".equals(Arrays.toString(args).trim())) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			final String target = args[0];
			final Player targetPlayer = Bukkit.getPlayerExact(target);

			if (targetPlayer == null) {
				player.sendMessage(ChatColor.RED + "Player not found.");
				return true;
			}

			final String targetName = targetPlayer.getName();
			final String playerName = player.getName();

			if (targetName.equals(playerName)) {
				player.sendMessage(ChatColor.RED + "You can't message yourself!");
				return false;
			}

			final StringBuilder message = new StringBuilder();

			for (int i = 1; i < args.length; i++) {
				message.append(args[i]).append(" ");
			}

			player.sendMessage(ChatColor.AQUA + "You » " + targetName + ": " + ChatColor.WHITE + message);
			targetPlayer.sendMessage(ChatColor.AQUA + playerName + " » You: " + ChatColor.WHITE + message);

			final UUID playerUUID = player.getUniqueId();
			final UUID targetPlayerUUID = targetPlayer.getUniqueId();

			if (!recentMessages.containsKey(targetPlayerUUID) || recentMessages.get(targetPlayerUUID) != playerUUID) {
				recentMessages.put(targetPlayerUUID, playerUUID);
			}
		} else {
			Utility.log("/message is a player-only command!");

			return false;
		}

		return true;
	}
}
