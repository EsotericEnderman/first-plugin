package dev.enderman.minecraft.plugins.first.event.listeners.minecraft.player;

import dev.enderman.minecraft.plugins.first.events.custom.NPCClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class NPCClickListener implements Listener {
	@EventHandler
	public void onClick(@NotNull final NPCClickEvent event) {
		event.getClicker().sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "You clicked an NPC!");
	}
}
