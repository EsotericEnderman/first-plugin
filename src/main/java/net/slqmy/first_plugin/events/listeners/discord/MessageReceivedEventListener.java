package net.slqmy.first_plugin.events.listeners.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class MessageReceivedEventListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull final MessageReceivedEvent event) {
		if (event.getChannel().getId().equals("1109899306300883010")) {
			Bukkit.broadcastMessage(event.getAuthor().getName() + ": " + event.getMessage().getContentRaw());
		}
	}
}
