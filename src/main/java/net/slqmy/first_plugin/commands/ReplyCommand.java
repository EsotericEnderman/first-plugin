package net.slqmy.first_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class ReplyCommand implements CommandExecutor {
	private final Map<UUID, UUID> recentMessages;

	public ReplyCommand(Map<UUID, UUID> recentMessages) {
		this.recentMessages = recentMessages;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			if (arguments.length == 0) {
				player.sendMessage(ChatColor.RED + "Please provide at least one argument!");
			} else {
				UUID playerUUID = player.getUniqueId();

				if (!recentMessages.containsKey(playerUUID)) {
					player.sendMessage(ChatColor.RED + "No one has messaged you yet!");
				} else {
					UUID targetUUID = recentMessages.get(playerUUID);

					Player targetPlayer = Bukkit.getPlayer(targetUUID);

					if (targetPlayer == null) {
						player.sendMessage(ChatColor.RED + "That player is not online!");
					} else {
						StringBuilder message = new StringBuilder();

						for (String word : arguments) {
							message.append(word).append(" ");
						}

						player.sendMessage(ChatColor.AQUA + "You » " + targetPlayer.getName() + ": " + ChatColor.WHITE + message);
						targetPlayer.sendMessage(ChatColor.AQUA + player.getName() + " » You: " + ChatColor.WHITE + message);
					}
				}
			}
		}

		return false;
	}
}
