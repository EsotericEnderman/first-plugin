package dev.enderman.minecraft.plugins.first.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class IsHoldingSpongePlaceholder extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "is_holding_sponge";
	}

	@Override
	public @NotNull String getAuthor() {
		return "slqmy";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0";
	}

	@Override
	public @Nullable String onPlaceholderRequest(final Player player, @NotNull final String params) {
		return String.valueOf(player.getInventory().getItemInMainHand().getType() == Material.SPONGE);
	}
}
