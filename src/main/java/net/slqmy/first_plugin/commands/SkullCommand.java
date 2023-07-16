package net.slqmy.first_plugin.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

			SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
			assert skullMeta != null;
			skullMeta.setOwningPlayer(player);
			skullMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + player.getName());

			skull.setItemMeta(skullMeta);

			ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

			GameProfile profile = new GameProfile(UUID.randomUUID(), null);
			profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmEyYzNlNzlkNWYzNWE5ZGNhYjE5ZTQzYzNlM2E2NTE5ZTQyNmI2NGE2MTIxM2NkMmYxZDI4YjU3MDM2ZjYifX19"));

			Field field;

			try {
				assert meta != null;
				field = meta.getClass().getDeclaredField("profile");
				field.setAccessible(true);

				field.set(meta, profile);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
				return true;
			}

			player.getInventory().addItem(skull);
		}

		return true;
	}
}
