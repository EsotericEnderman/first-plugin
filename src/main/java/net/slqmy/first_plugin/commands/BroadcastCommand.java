package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.events.custom_events.ServerBroadcastEvent;
import net.slqmy.first_plugin.utility.Utility;
import net.wesjd.anvilgui.AnvilGUI;
import net.wesjd.anvilgui.AnvilGUI.ResponseAction;
import net.wesjd.anvilgui.AnvilGUI.StateSnapshot;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class BroadcastCommand implements CommandExecutor {
	private final FirstPlugin plugin;

	public BroadcastCommand(@NotNull final FirstPlugin plugin) {
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

			new AnvilGUI.Builder()
					.title(ChatColor.BOLD + "Message")
					.text("> Type here")
					.itemLeft(new ItemStack(Material.WRITABLE_BOOK))
					.onClick((final Integer integer, final StateSnapshot snapshot) -> {
						final String message = snapshot.getText();

						final ServerBroadcastEvent event = new ServerBroadcastEvent(player, message);
						Bukkit.getPluginManager().callEvent(event);

						if (!event.isCancelled()) {
							Bukkit.broadcastMessage(ChatColor.GOLD + "Broadcast " + ChatColor.RESET + "» "
									+ ChatColor.translateAlternateColorCodes('&', message));

							if ("hello".equalsIgnoreCase(message)) {
								player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Hello to you too! "
										+ ChatColor.RESET + ":)");
							}
						}

						return Arrays.asList(ResponseAction.close());
					})
					.plugin(plugin)
					.open(player);
		} else {
			Utility.log("/broadcast is a player-only command!");

			return false;
		}

		return true;
	}
}
