package dev.esoteric_enderman.first_plugin.commands.minecraft;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import dev.esoteric_enderman.first_plugin.FirstPlugin;
import dev.esoteric_enderman.first_plugin.types.AbstractCommand;
import dev.esoteric_enderman.first_plugin.utility.Utility;
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

	public RizzCommand(@NotNull final FirstPlugin plugin) {
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

		Utility.setSkin(player, "ewogICJ0aW1lc3RhbXAiIDogMTY1MDA5MTM3MDEwOCwKICAicHJvZmlsZUlkIiA6ICJmMTA0NzMxZjljYTU0NmI0OTkzNjM4NTlkZWY5N2NjNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJ6aWFkODciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDE1NzJkZGI1YTBlMWNjOTRhYWIwMWU5NzJlNDFmZGZjYjJmMDg4NDM3ZjUzMzg5MjdmZTFjYzZlMWRhNzE2ZiIKICAgIH0KICB9Cn0=", "FPE4rbHeE0uDaDesBVOEzd71jpAXftbNdB5ZAwScdxby3vYYeGkB4FXi0n9yhdxBbytYfiICaaMA9ohAtZh59/kkVryflKS9JUdvTEKl4x8NZ6HzeXL7PUOl9ve6DNDNChuGqQ/9mCD0eReKaJtIfjJntRhrIcPhotVL1SSczB/V2EC9ke0vOUN5DdJS0zpY6JFibfcXHXH6AQBjDIOw7K2I1m/NfvzNAN9D7k4rWWe88SKRuvou24ubqXye80Dh0qXgxEDkipVmKxe0YMvI2bnZgq1DHtOkSl1MktxfVoEfSevYk0R4E2G176CUepcaSjaekbFsdBbTF6+SqUUvrCxpNGsJ2T93Yx4/MIcpuXlMCWTtrtOIeUZD3gk0qA2E37XUgJEz8SSxzeLJri6QBf+85/lOGcdKRkDUlO06C5EwQbDBdDh8jCwCevkdIF+ObGkTyYEF1dGCuCtTgD1tRJ/Jx83p/O9NhmTrPDW7wn7FyowMQ0Dt3P9wnnLp7axTn2UVdLHb/PuOKA9IE3vAIUC4rHL1IzLUyFZLxMcU01bfXbz3CQeN0I9e6wujJo0bGOOe1W5GIGWAXn2G+GLQN0l7v2mNOkU8n2+b9VpfU1u4sCYlVrPNasjZr7Ua7n5Uz2WszFgg7K8kGYHyeLtLQnc47xDPCe+nuQT5aHpGrxU=");
		Utility.setSkin(target, "ewogICJ0aW1lc3RhbXAiIDogMTYyOTY0MDc4MjI1MywKICAicHJvZmlsZUlkIiA6ICIyMDA2NTVkMjMyYTE0MTc2OGIwNjQ0NWNkNTliNDg3NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaWVzdHlCbHVlXyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jMTA1ZTZmNGI5NDRmODY0ZDVjMjgyOTllOGYxYjQ2NWE4MzU2NjIzY2U2MDk3N2U0Y2RmNGU4NmMzZmEyNDRhIgogICAgfQogIH0KfQ==", "v/36HPdLfdjUvVd1nNeS5Ued+LvJSa1o/dkidMfqQmuNmu68qGVTKiUkCX3l00RPtKdpSr1CcN935+OSAJD//7cAl0o6BpoOh9og5jR7emh+/9pGjrSfhrDht3YFbWUksatCJQ7G47aaba1AZ4DulC8vVOJBqRLwJFvpoOk9uGoo7MFB7MN2mHCUUUXqZFDgbts+/2uQC7jaAGbX472lfj3uGtNdhcoldm1ym0b6ZQIQUYnHDyB+IFqsX0RZMrLzvS2tY6hWKZyN6+lR6ZMSSTVauptULUPsclNfciQylqx56GakqyUIWcqhni4GentaCFYqru+F17ofMTQiD1X03p/ckDdK+INZR+sT7GdblrfmAmrg676OWi1f8wu3iYGaOSop4Fqj+7RkSiTGeNEBB8KF+l8iNcE6KYVq3RH6ymEEopjRH9s9Bo5OwAH6Xy7v1SDBZhZXt1KeJvJNklKEhWPuv2l0fVq+9Oi6vO2oZcndJjrBBdatAyg8+vRJmWLhEFL2fSkjbqnW7fDP6blQ/DbFSsHQIBznlXmT6IYvKzTUJX/1id7UW7AsN6I1qMb5EtC44iEDRBurJYtyQL8o+4E6BSeKrQm6j2zYyBceWBFVBRJefZMjLjM6yYtdZffZyCffX3t4b+ltwJG8IM15PYnZ4oG4FzPh96vWargYJNs=");

		// songPlayer.destroy(); to stop playing forever.
		// songPlayer.setPlaying(false); to pause.

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
