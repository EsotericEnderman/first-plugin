package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.FirstPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {

	private final List<UUID> vanished = new ArrayList<>();
	private final FirstPlugin firstPlugin;

	public VanishCommand(FirstPlugin firstPlugin) {
		this.firstPlugin = firstPlugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			if (vanished.contains(player.getUniqueId())) {
				vanished.remove(player.getUniqueId());

				for (Player target : Bukkit.getOnlinePlayers()) {
					target.showPlayer(firstPlugin, player);
				}

				player.sendMessage(ChatColor.AQUA + "Where did you come from?");
			} else {
				vanished.add(player.getUniqueId());

				for (Player target : Bukkit.getOnlinePlayers()) {
					target.hidePlayer(firstPlugin, player);
				}

				player.sendMessage(ChatColor.AQUA + "Where did that guy go?");
			}
		}

		return false;
	}
}
