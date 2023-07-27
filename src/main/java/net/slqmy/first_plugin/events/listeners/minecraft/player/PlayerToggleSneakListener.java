package net.slqmy.first_plugin.events.listeners.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

public final class PlayerToggleSneakEventListener implements Listener {
	@EventHandler
	public void onPlayerToggleSneak(@NotNull final PlayerToggleSneakEvent event) {
		final Player player = event.getPlayer();
		final ItemStack mainHand = player.getInventory().getItemInMainHand();

		if (event.isSneaking() && mainHand.getType() == Material.MUSIC_DISC_PIGSTEP) {
			final Location playerLocation = player.getLocation();

			final Firework firework = player.getWorld().spawn(playerLocation, Firework.class);
			final FireworkMeta fireworkMeta = firework.getFireworkMeta();

			fireworkMeta.addEffect(
							FireworkEffect.builder()
											.withColor(Color.RED)
											.withColor(Color.BLACK)
											.with(FireworkEffect.Type.CREEPER)
											.withTrail()
											.withFlicker()
											.withFade(Color.BLACK)
											.build());

			fireworkMeta.setPower(3);

			firework.setFireworkMeta(fireworkMeta);

			// Volume goes from 0 to 1, and if its above 1, it increases the distance at
			// which it can be heard.
			//
			// Pitch is from 0.5 to 2.0. It is the speed at which it is played.
			// 0.5 = half as fast, 2.0 = twice as fast (if I remember correctly).
			player.playSound(playerLocation, Sound.ENTITY_ARMOR_STAND_FALL, 1, 2.0F);

			// You can do something similar by doing this:
			// Bukkit.getWorld("world").playSound(player.getLocation(),
			// Sound.ENTITY_ARMOR_STAND_FALL, 1.0F, 2.0F);.

			player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1.5F);
			player.playEffect(playerLocation, Effect.RECORD_PLAY, Material.MUSIC_DISC_PIGSTEP);

			final Block targetBlock = player.getTargetBlockExact(5);

			if (targetBlock != null) {
				final Location targetBlockLocation = targetBlock.getLocation();

				if (targetBlock.getType() == Material.OAK_SIGN) {
					player.sendSignChange(targetBlockLocation, new String[]{
									"I am a sign!",
									"",
									"And no one else",
									"can see this!"
					});
				} else {
					final BlockData targetBlockData = Material.DIAMOND_BLOCK.createBlockData();

					player.sendBlockChange(targetBlockLocation, targetBlockData);
				}
			}

			final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
			serverPlayer.connection.send(new ClientboundAnimatePacket(serverPlayer, 0));
			serverPlayer.connection.send(new ClientboundBlockDestructionPacket(
							0, new BlockPos(playerLocation.getBlockX(), playerLocation.getBlockY() - 1, playerLocation.getBlockZ()), 8)
			);
			serverPlayer.connection.send(new ClientboundSetSimulationDistancePacket(2));
		}
	}
}
