package net.slqmy.first_plugin.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.slqmy.first_plugin.utility.Utility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Spigot;
import org.jetbrains.annotations.NotNull;

public final class GiveBookCommand implements CommandExecutor {
	private static final String SLQMY = "Slqmy";

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
			@NotNull final String label,
			@NotNull final String[] args) {
		if (args.length != 0) {
			return false;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;

			final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

			final BookMeta bookMeta = (BookMeta) book.getItemMeta();
			assert bookMeta != null;

			final String bookTitle = ChatColor.RED + "The Entire History of " + ChatColor.GREEN
					+ ChatColor.BOLD
					+ "The Slimy Swamp" + ChatColor.RED + ", I Guess";
			bookMeta.setTitle(bookTitle);
			bookMeta.setDisplayName(bookTitle);
			bookMeta.setAuthor(ChatColor.GREEN.toString() + ChatColor.BOLD + SLQMY);

			final Spigot spigotBook = bookMeta.spigot();

			final TextComponent plainFirstPage = new TextComponent(
					ChatColor.GREEN.toString() + ChatColor.BOLD + "The Slimy Swamp" + ChatColor.RESET
							+ "was a Minecraft server founded by" + ChatColor.GREEN + ChatColor.BOLD + SLQMY
							+ ChatColor.RESET + ".\n\n"
							+ "It had very humble beginnings, with the first three players being" + ChatColor.GREEN + ChatColor.BOLD
							+ SLQMY + ChatColor.RESET + ", " + ChatColor.BLACK + "rolyPolyVole" + ChatColor.RESET
							+ "and" + ChatColor.GOLD + "Crafty_Crafter2" + ChatColor.RESET + ".\n"
							+ "It was a really cool server, so it grew in size.\n"
							+ "It had many players.\n"
							+ "And the players had lots of fun.\n"
							+ "Until one day the server died - no one plays on it anymore."
							+ "But it will be revived! Hopefully.\n");

			spigotBook.addPage(new BaseComponent[] { plainFirstPage });

			// Could be really useful for large books that have many pages.
			// There could be a contents section, where each section / chapter is clickable,
			// and it brings you to that section / chapter.
			final TextComponent clickable = new TextComponent(
					ChatColor.BLUE.toString() + ChatColor.BOLD + "Click me to go back to the first page!\n");
			clickable.setClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "1"));

			final TextComponent none = new TextComponent(
					ChatColor.GRAY + "This text does nothing... whether you click it or hover over it... \n");

			final TextComponent hoverable = new TextComponent(ChatColor.AQUA + "Hover over me to see the secret message...");
			hoverable
					.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("The secret message is... 'troll'!")));

			spigotBook.addPage(new BaseComponent[] { clickable, none, hoverable });

			book.setItemMeta(bookMeta);

			player.getInventory().addItem(book);
			player.openBook(book);
		} else {
			Utility.log("/give-book is a player-only command!");

			return false;
		}

		return true;
	}
}
