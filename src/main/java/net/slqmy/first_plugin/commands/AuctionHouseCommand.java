package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.types.AuctionHouseGUI;
import net.slqmy.first_plugin.types.AbstractCommand;
import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class AuctionHouseCommand extends AbstractCommand {
  public AuctionHouseCommand() {
    super(
        "auction-house",
        "Open the auction house menu... what is up with this economy?",
        "/ah",
        new Integer[] { 0 },
        new String[] { "ah", "shop" },
        "first_plugin.auction_house",
        true);
  }

  @Override
  public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
    if (sender instanceof Player) {
      ((Player) sender).openInventory(new AuctionHouseGUI(1).getInventory());
    } else {
      Utility.log("/auction-house is a player-only command!");

      return false;
    }

    return true;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
    return null;
  }
}
