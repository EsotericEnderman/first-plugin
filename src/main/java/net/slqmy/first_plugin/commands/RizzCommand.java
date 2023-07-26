package net.slqmy.first_plugin.commands;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.types.AbstractCommand;
import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public final class RizzCommand extends AbstractCommand {
	private final Song whatIsLove;

	public RizzCommand(@NotNull final Main plugin) {
		super(
						"rizz",
						"Rizz someone up with this command!",
						"/rizz <player>",
						new Integer[]{1},
						new String[]{},
						"first_plugin.rizz",
						true
		);

		whatIsLove = NBSDecoder.parse(new File(plugin.getDataFolder(), "What is Love.nbs"));
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
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

		Utility.setSkin(player, "ewogICJ0aW1lc3RhbXAiIDogMTYxOTc3MjYyNzI0MiwKICAicHJvZmlsZUlkIiA6ICJjMGYzYjI3YTUwMDE0YzVhYjIxZDc5ZGRlMTAxZGZlMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDEzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc3NDJjMDNjYjgwZGMxNGFmNTMzZjIwYzA1NDczNmZhMmMxNmU2YzIxNTFhMTE1MGUyOGVhOWYwNWM0MTcwNDQiCiAgICB9CiAgfQp9");
		Utility.setSkin(target, "ewogICJ0aW1lc3RhbXAiIDogMTYxOTcxNDczNzc3NywKICAicHJvZmlsZUlkIiA6ICJmNDY0NTcxNDNkMTU0ZmEwOTkxNjBlNGJmNzI3ZGNiOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJSZWxhcGFnbzA1IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzFkNmVjOGNiNTJjYWY2NzI1NWZmZGM2ZTEzYmE4M2M5NDNjMTQxMGIwNDVlOTJkOGIwYWM2MjAyMjM5ZTk3YjAiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==");

		// songPlayer.destroy(); to stop playing forever.
		// songPlayer.setPlaying(false); to pause.

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
