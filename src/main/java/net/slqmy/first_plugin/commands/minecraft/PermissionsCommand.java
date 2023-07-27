package net.slqmy.first_plugin.commands.minecraft;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.types.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class PermissionsCommand extends AbstractCommand {
	private final Main plugin;

	// In actually good plugins remember to remove unnecessary data when the player
	// leaves the server.
	private final HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();

	public PermissionsCommand(@NotNull final Main plugin) {
		super(
						"permissions",
						"Give yourself a secret permission to access a very secret command!",
						"/permissions",
						new Integer[]{0},
						new String[]{"permission", "perms", "perm"},
						"first_plugin.permissions_command",
						true
		);

		this.plugin = plugin;
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
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
		if (player.hasPermission("first_plugin.secret_message")) {
			attachment.unsetPermission("first_plugin.secret_message");

			player.sendMessage(ChatColor.GREEN + "Removed permission!");
		} else {
			attachment.setPermission("first_plugin.secret_message", true);

			player.sendMessage(ChatColor.GREEN + "Added permission!");
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
