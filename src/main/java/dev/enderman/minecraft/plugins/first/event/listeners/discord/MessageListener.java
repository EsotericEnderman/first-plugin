package dev.enderman.minecraft.plugins.first.event.listeners.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull final MessageReceivedEvent event) {
		if (event.getChannel().getId().equals("1109899306300883010")) {
			Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[" + ChatColor.BLUE + "Discord" + ChatColor.LIGHT_PURPLE + "]" + event.getAuthor().getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage().getContentRaw());
		}
	}
}
