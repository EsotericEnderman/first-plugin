package net.slqmy.first_plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

public class BookCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

			BookMeta bookMeta = (BookMeta) book.getItemMeta();

			bookMeta.setTitle(ChatColor.RED + "The entire history of The Slimy Swamp, I guess");
			bookMeta.setAuthor(ChatColor.GREEN + "Slqmy");

		  bookMeta.addPage(
							ChatColor.GREEN + "The Slimy Swamp was founded by Slqmy."
							+ ChatColor.WHITE + "\n\nIt was a really cool server."
		          + "It had many players."
		          + "But eventually it died."
		          + "But it will be revived! Hopefully."
		  );

			bookMeta.setGeneration(BookMeta.Generation.TATTERED);

			book.setItemMeta(bookMeta);

			PlayerInventory playerInventory = player.getInventory();
			playerInventory.addItem(book);
		}

		return false;
	}
}
