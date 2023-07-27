package net.slqmy.first_plugin.commands.minecraft;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.types.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class NPCCommand extends AbstractCommand {
	private final Main plugin;

	public NPCCommand(@NotNull final Main plugin) {
		super(
						"npc",
						"Spawns a custom player NPC.",
						"/npc",
						new Integer[]{0},
						new String[]{"human", "spawn-npc", "spawn-human"},
						"first_plugin.npc",
						true
		);

		this.plugin = plugin;
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, final String @NotNull [] args) {
		final Player player = (Player) sender;
		final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();

		final MinecraftServer server = serverPlayer.getServer();
		assert server != null;

		final GameProfile npcProfile = new GameProfile(UUID.randomUUID(), "NPC");
		npcProfile.getProperties().put(
						"textures",
						new Property(
										"textures", "ewogICJ0aW1lc3RhbXAiIDogMTY5MDM3OTQ5MTM1NCwKICAicHJvZmlsZUlkIiA6ICJmYjAwYjM5Yjk5MWM0YmNmODA4OWJmMDJhNjg5MDA2MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJTbHFteSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yNWM5MGI5YjMwZGVmYmRlYjllNDYxY2RkY2EzNzdlYmIwM2IyNGFlYTVhZDUxOTRmNGZjMjAwOWM3YmFjNTdjIgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzQwYzBlMDNkZDI0YTExYjE1YThiMzNjMmE3ZTllMzJhYmIyMDUxYjI0ODFkMGJhN2RlZmQ2MzVjYTdhOTMzIgogICAgfQogIH0KfQ==", "B9uOksbPyJuLIFLejdbGZSJ7RRE/KGhKsh+o9GwyZokleMngVfCVzY1YrP4vmGYPAT26UbW3HeXkDwdTwAKj9o0SdNPf6KYhuySxEluNS8fD3obZ5N2TfTVS/FXVFqs6+Yyn515Dm67Jljy+mZII1nstro1EkMkCdbccwsDfLwvOSjF27WSHWueSq/J4BvJgCJkrjpIG0hP7p6mlswTPA71+TpJ8K/TNT81qf/NRMaHFDvXpWQk87iJF3b+ZwumU6HP+xZ6Mkeyf125v7g0jQQYoqR3yJHeI7YVmocETeTw79NsvHmH+rDnPIvoPu4Cdjg0IlDa2SmGCkd9ukgvKLhSjY8v0aUcKJ1l03XMNAlFeocwjNlceVfk2/sHCvWvysc7cJvnvE+F9cmRZmFnWdl/8M0Qq7s+/rTiYzqHQFFpgC7t81CNs0/GNX3B92eo44NLaO+85dvcF56kF5oG3dxPYkeNeXSuq3Wunya7ONAJgcLv762ul8zj7CMOeh6RTKgflIg5FYk/7cRPaKf6pwqLs8aB7bZIlruox044QXAJRsNRAkI4DMJfao/Jcc8chNAD1+/Zos5zf1CvEfvzR1b2TnrAOEEi0Zg0BUG84T3Hkz6rlwJ9mR1mhaqoqhKdOYBeSg8at76pFTt7IB0nsy/t/6FJ8a2QK5mI/G8AwyDc="
						)
		);

		final ServerPlayer npc = new ServerPlayer(
						server,
						((CraftWorld) player.getWorld()).getHandle().getLevel(),
						npcProfile
		);

		plugin.getNPCs().put(npc.getBukkitEntity().getEntityId(), npc.getUUID());
		// Many more methods available, such as adding items to the NPC's inventory, could be useful.
		npc.setPose(Pose.SWIMMING); // Doesn't seem to work though...

		final Location playerLocation = player.getLocation();

		npc.setPos(
						playerLocation.getX(),
						playerLocation.getY(),
						playerLocation.getZ()
		);

		final SynchedEntityData data = npc.getEntityData();

		final byte bitmask = 125;
		data.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), bitmask);

		// If I want everyone online to be able to see the NPC,
		// Then I have to send packets to everyone.

		final ServerGamePacketListenerImpl connection = serverPlayer.connection;
		connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npc));

		connection.send(new ClientboundAddPlayerPacket(npc));

		connection.send(
						new ClientboundSetEntityDataPacket(
										npc.getBukkitEntity().getEntityId(),
										Collections.singletonList(SynchedEntityData.DataValue.create(
														new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), bitmask)
										)
						)
		);

		// Modulus not needed here unless the angle is greater than 360 degrees.
		final byte yaw = 30 * 256 / 360;
		final byte pitch = 60 * 256 / 360;

		connection.send(new ClientboundRotateHeadPacket(npc, yaw));

		connection.send(
						new ClientboundMoveEntityPacket.Rot(
										npc.getBukkitEntity().getEntityId(),
										yaw,
										pitch,
										true
						)
		);

		connection.send(
						new ClientboundSetEquipmentPacket(
										npc.getBukkitEntity().getEntityId(),
										Collections.singletonList(
														new Pair<>(
																		EquipmentSlot.MAINHAND,
																		CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_SWORD)
																		)
														)
										)
						)
		);

		Bukkit.getScheduler().runTaskLater(plugin, () ->
						connection.send(
										new ClientboundPlayerInfoRemovePacket(
														Collections.singletonList(npc.getUUID())
										)
						), 20
		);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull final CommandSender sender, final String @NotNull [] args) {
		return null;
	}
}
