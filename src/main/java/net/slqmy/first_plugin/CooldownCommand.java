package net.slqmy.first_plugin;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownCommand implements CommandExecutor {

	private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (!cooldown.asMap().containsKey(player.getUniqueId())) {
				player.sendMessage(ChatColor.GREEN + "Command successful! Cool-down set to 5 seconds");

				cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 5000);
			} else {
				long timeDifferenceMilliseconds = cooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis();

				player.sendMessage(ChatColor.RED + "You must wait " + (timeDifferenceMilliseconds / 1000) + " more seconds until you can run this command again!");
			}
		}

		return false;
	}
}
