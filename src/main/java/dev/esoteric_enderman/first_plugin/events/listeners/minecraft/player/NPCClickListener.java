package dev.esoteric_enderman.first_plugin.events.listeners.minecraft.player;

import dev.esoteric_enderman.first_plugin.events.custom_events.NPCClickEvent;
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
