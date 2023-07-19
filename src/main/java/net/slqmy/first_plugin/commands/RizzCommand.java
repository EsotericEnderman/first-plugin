package net.slqmy.first_plugin.commands;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.Utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;

public final class RizzCommand implements CommandExecutor {
	private final Song whatIsLove;

	public RizzCommand(final FirstPlugin plugin) {
		this.whatIsLove = NBSDecoder.parse(new File(plugin.getDataFolder(), "What is Love.nbs"));
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 1 || "".equals(Arrays.toString(args).trim())) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			final String targetName = args[0];
			final Player target = Bukkit.getPlayer(targetName);

			if (target == null) {
				player.sendMessage(ChatColor.RED + "Player not found!");
				return false;
			}

			final EntitySongPlayer songPlayer = new EntitySongPlayer(whatIsLove);

			songPlayer.setEntity(player);
			songPlayer.addPlayer(player);
			songPlayer.addPlayer(target);
			songPlayer.setPlaying(true);

			Utility.setSkin(player,
					"ewogICJ0aW1lc3RhbXAiIDogMTYxOTc3MjYyNzI0MiwKICAicHJvZmlsZUlkIiA6ICJjMGYzYjI3YTUwMDE0YzVhYjIxZDc5ZGRlMTAxZGZlMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDEzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc3NDJjMDNjYjgwZGMxNGFmNTMzZjIwYzA1NDczNmZhMmMxNmU2YzIxNTFhMTE1MGUyOGVhOWYwNWM0MTcwNDQiCiAgICB9CiAgfQp9");
			Utility.setSkin(target,
					"ewogICJ0aW1lc3RhbXAiIDogMTYxOTcxNDczNzc3NywKICAicHJvZmlsZUlkIiA6ICJmNDY0NTcxNDNkMTU0ZmEwOTkxNjBlNGJmNzI3ZGNiOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJSZWxhcGFnbzA1IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzFkNmVjOGNiNTJjYWY2NzI1NWZmZGM2ZTEzYmE4M2M5NDNjMTQxMGIwNDVlOTJkOGIwYWM2MjAyMjM5ZTk3YjAiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==");

			// songPlayer.destroy(); if I want to stop playing forever.
			// songPlayer.setPlaying(false); to pause.
		} else {
			Utility.log("/rizz is a player-only command!");

			return false;
		}

		return true;
	}
}
