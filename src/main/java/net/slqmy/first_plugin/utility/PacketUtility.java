package net.slqmy.first_plugin.utility;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public final class PacketUtility {
	public static void inject(@NotNull final Player player) {
		final ChannelDuplexHandler channelHandler = new ChannelDuplexHandler() {
			@Override
			public void channelRead(@NotNull final ChannelHandlerContext context, @NotNull final Object rawPacket) throws Exception {
				if (rawPacket instanceof ServerboundInteractPacket) {
					final ServerboundInteractPacket packet = (ServerboundInteractPacket) rawPacket;

					final Field actionTypeField = packet.getClass().getDeclaredField("b");
					actionTypeField.setAccessible(true);

					final Object actionType = actionTypeField.get(packet);

					if (actionType.toString().split("\\$")[1].charAt(0) == 'c') {
						Utility.log("Packet was not of type PlayerInteract!");
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

					try {
						final Field hand = actionType.getClass().getDeclaredField("a");
						hand.setAccessible(true);

						if (!hand.get(actionType).toString().equals("MAIN_HAND")) {
							return;
						}
					} catch (final NoSuchFieldException ignoredException) {
					}

					final Field entityIDField = packet.getClass().getDeclaredField("a");
					entityIDField.setAccessible(true);

					// Compare entity ID with NPC ID (save it).
					// Idea: create a custom npc click event, and an NPC manager.

					final int entityID = entityIDField.getInt(packet);
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
			throw new RuntimeException(exception);
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
			throw new RuntimeException(exception);
		}

		final Channel channel = connection.channel;
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(playerName);
			return null;
		});
	}
}
