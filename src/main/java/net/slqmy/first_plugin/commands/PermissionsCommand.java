package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.utility.Utility;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public final class PermissionsCommand implements CommandExecutor {
	private static final String PERMISSION_STRING = "first_plugin.secret_message";

	private final Main plugin;

	// In actually good plugins remember to remove unnecessary data when the player
	// leaves the server.
	private final HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();

	public PermissionsCommand(@NotNull final Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			// Remember to have permission attachments for each player!
			final PermissionAttachment attachment;
			if (!permissions.containsKey(player.getUniqueId())) {
				// Load permissions on plugin startup, as they are not kept track of.
				attachment = player.addAttachment(plugin);

				permissions.put(player.getUniqueId(), attachment);
			} else {
				attachment = permissions.get(player.getUniqueId());
			}

			// Note: Ops always have every permission, that's just how op works.
			if (player.hasPermission(PERMISSION_STRING)) {
				attachment.unsetPermission(PERMISSION_STRING);

				player.sendMessage(ChatColor.GREEN + "Removed permission!");
			} else {
				attachment.setPermission(PERMISSION_STRING, true);

				player.sendMessage(ChatColor.GREEN + "Added permission!");
			}
		} else {
			Utility.log("/permissions is a player-only command!");

			return false;
		}

		return true;
	}
}
