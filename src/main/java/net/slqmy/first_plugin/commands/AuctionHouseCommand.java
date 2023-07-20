package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.utility.Utility;
import net.slqmy.first_plugin.utility.types.AuctionHouseGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class AuctionHouseCommand implements CommandExecutor {
  @Override
  public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
      @NotNull final String label,
      @NotNull final String[] args) {
    if (args.length != 0) {
      return false;
    }

    if (sender instanceof Player) {
      ((Player) sender).openInventory(new AuctionHouseGUI(1).getInventory());
    } else {
      Utility.log("/auction-house is a player-only command!");

      return false;
    }

    return true;
  }
}
