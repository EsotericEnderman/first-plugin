package net.slqmy.first_plugin.commands;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import net.slqmy.first_plugin.utility.Utility;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public final class CooldownCommand implements CommandExecutor {
	private static final int COOLDOWN_SECONDS = 5;

	private final Cache<UUID, Long> cooldown = CacheBuilder.newBuilder()
			.expireAfterWrite(COOLDOWN_SECONDS, TimeUnit.SECONDS)
			.build();

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;
			final UUID playerUUID = player.getUniqueId();
			final ConcurrentMap<UUID, Long> map = cooldown.asMap();

			if (!map.containsKey(playerUUID)) {
				player.sendMessage(ChatColor.GREEN + "Command successful! Cool-down set to " + ChatColor.BOLD + COOLDOWN_SECONDS
						+ ChatColor.GREEN + " seconds");

				cooldown.put(playerUUID, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(COOLDOWN_SECONDS));
			} else {
				final long timeDifferenceMilliseconds = map.get(playerUUID) - System.currentTimeMillis();

				player
						.sendMessage(ChatColor.RED + "You must wait " + ChatColor.BOLD
								+ TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMilliseconds)
								+ ChatColor.RED + " more seconds until you can run this command again!");
			}
		} else {
			Utility.log("/cool-down is a player-only command!");

			return false;
		}

		return true;
	}
}
