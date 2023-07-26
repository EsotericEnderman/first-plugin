package net.slqmy.first_plugin.enchantments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class AutoSmeltingEnchantment extends Enchantment implements Listener {
	private static final NamespacedKey KEY = NamespacedKey.minecraft("auto_smelting");

	public AutoSmeltingEnchantment() {
		super(KEY);
	}

	@NotNull
	@Override
	public String getName() {
		return "Auto-Smelting";
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@NotNull
	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
	}

	@Override
	public boolean isTreasure() {
		return true;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean conflictsWith(@NotNull final Enchantment other) {
		return false;
	}

	@Override
	public boolean canEnchantItem(@NotNull final ItemStack item) {
		return true;
	}

	@EventHandler
	public void onBlockBreak(@NotNull final BlockBreakEvent event) {
		if (!event.isDropItems()) {
			return;
		}

		final ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

		if (meta == null) {
			return;
		}

		final Block brokenBlock = event.getBlock();

		if (brokenBlock.getType() != Material.IRON_ORE) {
			return;
		}

		if (meta.hasEnchant(this)) {
			event.setDropItems(false);

			brokenBlock.getWorld().dropItemNaturally(brokenBlock.getLocation(), new ItemStack(Material.IRON_INGOT));
		}
	}

	@EventHandler
	public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
		final ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);

		final Enchantment enchantment = Enchantment.getByKey(KEY);
		assert enchantment != null;

		pickaxe.addUnsafeEnchantment(enchantment, 1);

		final ItemMeta meta = pickaxe.getItemMeta();

		assert meta != null;
		meta.setLore(Collections.singletonList(ChatColor.GRAY + "Auto-Smelting I"));

		pickaxe.setItemMeta(meta);

		event.getPlayer().getInventory().addItem(pickaxe);
	}
}
