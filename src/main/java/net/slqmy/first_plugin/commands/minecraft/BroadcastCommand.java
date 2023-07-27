package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.events.custom_events.ServerBroadcastEvent;
import net.slqmy.first_plugin.types.AbstractCommand;
import net.wesjd.anvilgui.AnvilGUI;
import net.wesjd.anvilgui.AnvilGUI.ResponseAction;
import net.wesjd.anvilgui.AnvilGUI.StateSnapshot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public final class BroadcastCommand extends AbstractCommand {

	private final PluginManager pluginManager = Bukkit.getPluginManager();

	private final AnvilGUI.Builder builder;

	public BroadcastCommand(@NotNull final Main plugin) {
		super(
						"broadcast",
						"Broadcast something to everyone on the server.",
						"/broadcast",
						new Integer[]{0},
						new String[]{"megaphone", "shout", "announce"},
						"first_plugin.broadcast",
						true
		);

		builder = new AnvilGUI.Builder()
						.title(ChatColor.BOLD + "Message")
						.text("Type here")
						.itemLeft(new ItemStack(Material.WRITABLE_BOOK))
						.onClick((final Integer integer, final StateSnapshot snapshot) -> {
							final Player player = snapshot.getPlayer();
							final String message = snapshot.getText();

							final ServerBroadcastEvent event = new ServerBroadcastEvent(player, message);
							pluginManager.callEvent(event);

							if (!event.isCancelled()) {
								Bukkit.broadcastMessage(ChatColor.GOLD + "Broadcast " + ChatColor.RESET + "» "
												+ ChatColor.translateAlternateColorCodes('&', message));

								if ("hello".equalsIgnoreCase(message)) {
									player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Hello to you too! "
													+ ChatColor.RESET + ":)");
								}
							}

							return Collections.singletonList(ResponseAction.close());
						})
						.plugin(plugin);
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
		final Player player = (Player) sender;

		builder.open(player);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
