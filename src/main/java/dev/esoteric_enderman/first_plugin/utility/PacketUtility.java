package dev.esoteric_enderman.first_plugin.utility;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import dev.esoteric_enderman.first_plugin.FirstPlugin;
import dev.esoteric_enderman.first_plugin.events.custom_events.NPCClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public final class PacketUtility {
	private static final PluginManager pluginManager = Bukkit.getPluginManager();

	public static void inject(@NotNull final FirstPlugin plugin, @NotNull final Player player) {
		final ChannelDuplexHandler channelHandler = new ChannelDuplexHandler() {
			@Override
			public void channelRead(@NotNull final ChannelHandlerContext context, @NotNull final Object rawPacket) throws Exception {
				if (rawPacket instanceof ServerboundInteractPacket) {
					final ServerboundInteractPacket packet = (ServerboundInteractPacket) rawPacket;

					final String obfActionFieldName = "b";

					final Field actionTypeField = packet.getClass().getDeclaredField(obfActionFieldName);
					actionTypeField.setAccessible(true);

					// ServerboundInteractPacket.Action
					final Object actionType = actionTypeField.get(packet);

					Utility.log(actionType.toString());

					final char obfActionInteractAtFieldName = 'e'; // Not the correct name according to the website, but it works.

					if (actionType.toString().split("\\$")[1].charAt(0) == obfActionInteractAtFieldName) {
						return;
					}

					/*
					 Try catch is needed here, as it is possible that the hand field
					 does not exist in actionType. This is because the player could
					 be left-clicking the NPC, and obviously you can't left-click
					 with your offhand, meaning the hand field does not exist.
					*/

					/*
					 3 Possibilities:

					 1. Player right clicks with main hand -> no exceptions thrown, code continues as usual.
					 2. Player right clicks with offhand -> return statement exists code early.
					 3. Player left clicks -> exception is thrown and caught in the empty catch block, and
					    code continues as usual.
					*/

					final String obfActionHandFieldName = "a";

					try {
						final Field hand = actionType.getClass().getDeclaredField(obfActionHandFieldName);
						hand.setAccessible(true);

						if (!hand.get(actionType).toString().equals("MAIN_HAND")) {
							return;
						}
					} catch (final NoSuchFieldException ignoredException) {
						// Not exactly the way try-catches are intended to work, but... it works :shrug:.
					}

					final String obfEntityIDFieldName = "a";

					final Field entityIDField = packet.getClass().getDeclaredField(obfEntityIDFieldName);
					entityIDField.setAccessible(true);

					// Compare entity ID with NPC ID (save it).
					// Idea: create a custom npc click event, and an NPC manager.

					final int entityID = entityIDField.getInt(packet);

					Utility.log("Entity ID: " + entityID);
					Utility.log(plugin.getNPCs().containsKey(entityID));
					Utility.log(plugin.getNPCs().get(entityID));

					if (plugin.getNPCs().containsKey(entityID)) {
						new BukkitRunnable() {
							@Override
							public void run() {
								Utility.log("Running task!");

								final NPCClickEvent event = new NPCClickEvent(plugin, player, entityID);
								pluginManager.callEvent(event);
							}
						}.runTaskLater(plugin, 1); // Runs on the next tick.
					}
				}

				super.channelRead(context, rawPacket);
			}
		};

		final Field field;
		final ServerGamePacketListenerImpl playerConnection = ((CraftPlayer) player).getHandle().connection;

		final Connection connection;
		final String playerName = player.getName();

		try {
			field = playerConnection.getClass().getDeclaredField("h");
			field.setAccessible(true);

			connection = (Connection) field.get(playerConnection);
		} catch (final NoSuchFieldException | IllegalAccessException exception) {
			DebugUtility.logError(exception, "Error while injecting packet listener into player " + playerName + "!");
			player.kickPlayer(ChatColor.RED + "Sorry, an error has occurred. Please contact a server admin!");

			return;
		}

		final ChannelPipeline pipeline = connection.channel.pipeline();
		pipeline.addBefore("packet_handler", playerName, channelHandler);
	}

	public static void eject(@NotNull final Player player) {
		final Field field;
		final ServerGamePacketListenerImpl playerConnection = ((CraftPlayer) player).getHandle().connection;

		final Connection connection;
		final String playerName = player.getName();

		try {
			field = playerConnection.getClass().getDeclaredField("h");
			field.setAccessible(true);

			connection = (Connection) field.get(playerConnection);
		} catch (final NoSuchFieldException | IllegalAccessException exception) {
			DebugUtility.logError(exception, "Error while ejecting packet listener from player " + playerName + "!");
			return;
		}

		final Channel channel = connection.channel;
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(playerName);
			return null;
		});
	}
}
