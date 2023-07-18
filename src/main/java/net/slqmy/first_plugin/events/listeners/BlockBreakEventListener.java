package net.slqmy.first_plugin.events.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public final class BlockBreakEventListener implements Listener {
	@EventHandler
	public void onBlockBreak(@NotNull final BlockBreakEvent event) {
		final Player player = event.getPlayer();

		player.getScoreboard().getTeam("blocks_broken")
				.setSuffix(ChatColor.YELLOW.toString() + player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));
	}
}
