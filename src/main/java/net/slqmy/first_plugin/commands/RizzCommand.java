package net.slqmy.first_plugin.commands;

import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import net.slqmy.first_plugin.FirstPlugin;
import net.slqmy.first_plugin.utility.Utility;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class RizzCommand implements CommandExecutor {
	private final FirstPlugin plugin;

	public RizzCommand(final FirstPlugin plugin) {
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
			Player player = (Player) sender;

			// Don't load files like this, use enums or classes that pre-parse the songs.
			RadioSongPlayer songPlayer = new RadioSongPlayer(
					NBSDecoder.parse(new File(plugin.getDataFolder(), "What is Love.nbs")));

			songPlayer.addPlayer(player);
			songPlayer.setPlaying(true);

			// songPlayer.destroy(); if I want to stop playing forever.
			// songPlayer.setPlaying(false); to pause.
		} else {
			Utility.log("/rizz is a player-only command!");

			return false;
		}

		return true;
	}
}
