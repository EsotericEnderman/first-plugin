package net.slqmy.first_plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class MessageCommand implements CommandExecutor {
	private final Map<UUID, UUID> recentMessages;

	public MessageCommand(Map<UUID, UUID> recentMessages) {
		this.recentMessages = recentMessages;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			if (arguments.length < 2) {
				player.sendMessage(ChatColor.RED + "Improper usage! Use /message <player> <message>.");
			} else {
				String target = arguments[0];

				Player targetPlayer = Bukkit.getPlayerExact(target);
				if (targetPlayer == null) {
					player.sendMessage(ChatColor.RED + "Player not found.");
				} else if (targetPlayer.getName().equals(player.getName())) {
					player.sendMessage(ChatColor.RED + "You can't message yourself!");
				} else {
					StringBuilder message = new StringBuilder();

					for (int i = 1; i < arguments.length; i++) {
						message.append(arguments[i]).append(" ");
					}

					player.sendMessage(ChatColor.AQUA + "You » " + targetPlayer.getName() + ": " + ChatColor.WHITE + message);
					targetPlayer.sendMessage(ChatColor.AQUA + player.getName() + " » You: " + ChatColor.WHITE + message);

					UUID playerUUID = player.getUniqueId();
					UUID targetPlayerUUID = targetPlayer.getUniqueId();

					if (!recentMessages.containsKey(targetPlayerUUID) || recentMessages.get(targetPlayerUUID) != playerUUID ) {
						recentMessages.put(targetPlayerUUID, playerUUID);
					}
				}
			}
		}

		return false;
	}
}
