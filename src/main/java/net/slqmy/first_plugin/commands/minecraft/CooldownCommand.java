package net.slqmy.first_plugin.commands.minecraft;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.slqmy.first_plugin.types.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public final class CooldownCommand extends AbstractCommand {
	private final int cooldownSeconds = 5;

	private final Cache<UUID, Long> cooldown = CacheBuilder.newBuilder()
					.expireAfterWrite(cooldownSeconds, TimeUnit.SECONDS)
					.build();

	public CooldownCommand() {
		super(
						"cooldown",
						"A command with a cooldown using a cache.",
						"/cooldown",
						new Integer[]{0},
						new String[]{"cd"},
						"first_plugin.cooldown",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final Player player = (Player) sender;
		final UUID playerUUID = player.getUniqueId();
		final ConcurrentMap<UUID, Long> map = cooldown.asMap();

		if (!map.containsKey(playerUUID)) {
			player.sendMessage(ChatColor.GREEN + "Command successful! Cool-down set to " + ChatColor.BOLD + cooldownSeconds
							+ ChatColor.GREEN + " seconds");

			cooldown.put(playerUUID, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(cooldownSeconds));
		} else {
			final long timeDifferenceMilliseconds = map.get(playerUUID) - System.currentTimeMillis();

			player.sendMessage(
							ChatColor.RED + "You must wait " + ChatColor.BOLD
											+ TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMilliseconds)
											+ ChatColor.RED + " more seconds until you can run this command again!"
			);
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
