package net.slqmy.first_plugin.commands;

import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import net.slqmy.first_plugin.FirstPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RizzCommand implements CommandExecutor {

	private final FirstPlugin plugin;

	public RizzCommand(FirstPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			// Don't load files like this, use enums or classes that pre-parse the songs.
			RadioSongPlayer songPlayer = new RadioSongPlayer(NBSDecoder.parse(new File(plugin.getDataFolder(), "What is Love.nbs")));

			songPlayer.addPlayer(player);
			songPlayer.setPlaying(true);

			// songPlayer.destroy(); if I want to stop playing forever.
			// songPlayer.setPlaying(false); to pause.
		}

		return false;
	}
}
