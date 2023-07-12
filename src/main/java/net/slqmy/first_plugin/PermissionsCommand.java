package net.slqmy.first_plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class PermissionsCommand implements CommandExecutor {
	private final FirstPlugin firstPlugin;

	// In actually good plugins remember to remove unnecessary data when the player leaves the server.
	private final HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();

	public PermissionsCommand(FirstPlugin firstPlugin) {
		this.firstPlugin = firstPlugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			// Remember to have permission attachments for each player!
			PermissionAttachment attachment;
			if (!permissions.containsKey(player.getUniqueId())) {
				// Load permissions on plugin startup, as they are not kept track of.
				attachment = player.addAttachment(firstPlugin);

				permissions.put(player.getUniqueId(), attachment);
			} else {
				attachment = permissions.get(player.getUniqueId());
			}

			// Note: Ops always have every permission, that's just how op works.
			if (player.hasPermission("first_plugin.secret_message")) {
				attachment.unsetPermission("first_plugin.secret_message");

				player.sendMessage(ChatColor.GREEN + "Removed permission!");
			} else {
				attachment.setPermission("first_plugin.secret_message", true);

				player.sendMessage(ChatColor.GREEN + "Added permission!");
			}
		}

		return false;
	}
}
