package dev.enderman.minecraft.plugins.first.commands.minecraft;

import dev.enderman.minecraft.plugins.first.types.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GiveGunsCommand extends AbstractCommand {

	public GiveGunsCommand() {
		super(
						"give-guns",
						"Give yourself some very powerful cool guns!",
						"/give-guns",
						new Integer[]{0},
						new String[]{"gg", "gun", "guns"},
						"first_plugin.guns",
						true
		);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final Player player = (Player) sender;

		final PlayerInventory playerInventory = player.getInventory();

		final ItemStack pistol = new ItemStack(Material.WOODEN_HOE);
		final ItemStack shotgun = new ItemStack(Material.STONE_HOE);
		final ItemStack miniGun = new ItemStack(Material.IRON_HOE);
		final ItemStack gatlingGun = new ItemStack(Material.GOLDEN_HOE);
		final ItemStack poisonLauncher = new ItemStack(Material.DIAMOND_HOE);
		final ItemStack rocketLauncher = new ItemStack(Material.NETHERITE_HOE);

		final ItemMeta pistolMeta = pistol.getItemMeta();
		final ItemMeta shotGunMeta = shotgun.getItemMeta();
		final ItemMeta miniGunMeta = miniGun.getItemMeta();
		final ItemMeta gatlingGunMeta = gatlingGun.getItemMeta();
		final ItemMeta poisonLauncherMeta = poisonLauncher.getItemMeta();
		final ItemMeta rocketLauncherMeta = rocketLauncher.getItemMeta();

		assert pistolMeta != null;
		assert shotGunMeta != null;
		assert miniGunMeta != null;
		assert gatlingGunMeta != null;
		assert poisonLauncherMeta != null;
		assert rocketLauncherMeta != null;

		pistolMeta.setDisplayName(ChatColor.GOLD + "Pistol");
		shotGunMeta.setDisplayName(ChatColor.BLUE + "Shotgun");
		miniGunMeta.setDisplayName(ChatColor.WHITE + "Mini-Gun");
		gatlingGunMeta.setDisplayName(ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Gatling Gun");
		poisonLauncherMeta.setDisplayName(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Poison Launcher");
		rocketLauncherMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Rocket Launcher");

		pistol.setItemMeta(pistolMeta);
		shotgun.setItemMeta(shotGunMeta);
		miniGun.setItemMeta(miniGunMeta);
		gatlingGun.setItemMeta(gatlingGunMeta);
		poisonLauncher.setItemMeta(poisonLauncherMeta);
		rocketLauncher.setItemMeta(rocketLauncherMeta);

		playerInventory.addItem(pistol);
		playerInventory.addItem(shotgun);
		playerInventory.addItem(miniGun);
		playerInventory.addItem(gatlingGun);
		playerInventory.addItem(poisonLauncher);
		playerInventory.addItem(rocketLauncher);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}
}
