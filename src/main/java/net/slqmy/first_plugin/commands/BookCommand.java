package net.slqmy.first_plugin.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
			assert bookMeta != null;

			bookMeta.setTitle(ChatColor.RED + "The entire history of The Slimy Swamp, I guess");
			bookMeta.setAuthor(ChatColor.GREEN + "Slqmy");

		  bookMeta.addPage(
							ChatColor.GREEN + "The Slimy Swamp was founded by Slqmy.\n\n"
							+ ChatColor.WHITE + "It was a really cool server.\n"
		          + "It had many players.\n"
		          + "But eventually it died.\n"
		          + "But it will be revived! Hopefully.\n"
		  );

			TextComponent clickable = new TextComponent(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Click me!\n\n");
			clickable.setClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "1"));

			TextComponent none = new TextComponent(ChatColor.GRAY + "This does nothing.\n");

			TextComponent hoverable = new TextComponent(ChatColor.AQUA + "Hover over me to see the secret message!");
			hoverable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Troll!")));

			bookMeta.spigot().addPage(new BaseComponent[]{ clickable, none, hoverable });

			bookMeta.setGeneration(BookMeta.Generation.ORIGINAL);

			book.setItemMeta(bookMeta);

			PlayerInventory playerInventory = player.getInventory();
			playerInventory.addItem(book);
		}

		return false;
	}
}
