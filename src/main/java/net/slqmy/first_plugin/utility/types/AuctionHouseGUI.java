package net.slqmy.first_plugin.utility.types;

import net.slqmy.first_plugin.utility.InventoryUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class AuctionHouseGUI {
	private static final Random RANDOM = new Random();

	private static final String[] ITEM_NAMES = {
			"Nuclear Launch Codes",
			"My Homework",
			"Some Random Legal Documents",
			"Divorce Papers",
			"Hoglin Rider Trajectory Calculations",
			"A Very Funny Meme I Found...",
			"A Really Funny Joke... Buy This Now!",
			"My Christmas Wish List...",
			"My Will",
			"My Birth Certificate",
			"Toilet Paper",
			// AI GENERATED:
			"Enchanted Origami Instructions",
			"Paper of Infinite Wisdom",
			"Folded Map to Atlantis",
			"Ancient Treasure Hunt Clue",
			"Secret Message from the Ender Dragon",
			"Lost Diary of a Creeper",
			"Scroll of Epic Pranks",
			"Enigmatic Riddle of the Nether",
			"Mysterious Love Letter",
			"Recipe for Magical Fireworks",
			"Ancient Manuscript of Infinite Laughter",
			"Enchanted Fortune-Teller's Scroll",
			"Mysterious Love Potion Recipe",
			"Pirate's Lost Treasure Map",
			"Secret Blueprint of the Eiffel Tower",
			"Spell book for Turning Dirt into Diamonds",
			"Dragon's Guide to Prank Warfare",
			"Sacred Scroll of Memes",
			"Recipe for Ender Pearl Pancakes",
			"The Legendary Scroll of Bad Luck"
	};

	private static final String[] ITEM_DESCRIPTIONS = {
			// AI GENERATED:
			"Guaranteed to make your friends laugh or your diamonds back!",
			"Handle with care - contains 100% genuine giggles.",
			"Caution: May cause spontaneous dancing!",
			"Perfect gift for your favourite creeper enthusiast.",
			"Bottled excellence from the End dimension.",
			"Sprinkle this on your enemies for guaranteed confusion.",
			"Sword may also be used for impromptu lawn care.",
			"Warning: Attracts sheep and curious pandas.",
			"Powerful charm to summon mythical horned horses.",
			"May cause feelings of joy, sorrow, or mild irritation.",
			"Unfold to reveal the secrets of the universe!",
			"Contains the wisdom of ancient paper masters.",
			"Instructions to find the sunken city of Atlantis.",
			"Follow the clues to claim hidden treasure!",
			"A message from the elusive Ender Dragon herself.",
			"A peek into the daily life of a mischievous creeper.",
			"Pull pranks like a pro with this scroll of tricks.",
			"Can you solve the riddle of the mysterious Nether?",
			"An anonymous love letter to your secret admirer.",
			"Craft stunning fireworks with this enchanting recipe.",
			"Guaranteed to make your friends laugh or your diamonds back!",
			"Handle with care - contains 100% genuine giggles.",
			"Caution: May cause spontaneous dancing!",
			"Perfect gift for your favourite creeper enthusiast.",
			"Bottled excellence from the End dimension.",
			"Sprinkle this on your enemies for guaranteed confusion.",
			"Sword may also be used for impromptu lawn care.",
			"Warning: Attracts sheep and curious pandas.",
			"Powerful charm to summon mythical horned horses.",
			"May cause feelings of joy, sorrow, or mild irritation.",
			"Unfold to reveal the secrets of the universe!",
			"Contains the wisdom of ancient paper masters.",
			"Instructions to find the sunken city of Atlantis.",
			"Follow the clues to claim hidden treasure!",
			"A message from the elusive Ender Dragon herself.",
			"A peek into the daily life of a mischievous creeper.",
			"Pull pranks like a pro with this scroll of tricks.",
			"Can you solve the riddle of the mysterious Nether?",
			"An anonymous love letter to your secret admirer.",
			"Craft stunning fireworks with this enchanting recipe.",
	};

	private static final String[] SELLERS = {
			"Slqmy",
			"rolyPolyVole",
			"TechnoBlade",
			"Dream",
			"Steve",
			"Alex",
			// AI GENERATED:
			"Notch's Nook",
			"HermitCraft Fanatic",
			"Queen Bee Grian",
			"Grian",
			"Mumbo Jumbo",
			"Iskall85",
			"PewDiePie",
			"Skeppy",
			"CaptainSparklez",
			"Keralis",
			"Paper Wizard Zed",
			"Master Origamist Lily",
			"HermitCraft's Cartographer",
			"Scroll-scribe Johanna",
			"Paperclip Enthusiast Ruby",
			"Papyrus Archaeologist Max",
			"Inkwell Alchemist Oliver",
			"The Paper Artisan, Mia",
			"Scrolls & Secrets by Emily",
			"Parchment Historian Leo",
			"The Legendary Notch",
			"Dancing Panda Petra",
			"Laughing Slime Charlie",
			"Minecraft's Sweetheart Alexia",
	};

	private final Inventory inventory;

	public AuctionHouseGUI(final int pageNumber) {
		inventory = Bukkit.createInventory(null, 54,
				ChatColor.GOLD.toString() + ChatColor.BOLD + "Auction House " + ChatColor.DARK_GRAY + "» Page "
						+ ChatColor.YELLOW
						+ pageNumber);

		final List<ItemStack> items = new ArrayList<>();

		for (int i = 0; i < 157; i++) {
			final String itemName = ITEM_NAMES[RANDOM.nextInt(ITEM_NAMES.length)];
			final String itemDescription = ITEM_DESCRIPTIONS[RANDOM.nextInt(ITEM_DESCRIPTIONS.length)];
			final String seller = SELLERS[RANDOM.nextInt(SELLERS.length)];

			final ItemStack auctionedItem = InventoryUtility.createItem(
					Material.PAPER,
					ChatColor.YELLOW + itemName,
					ChatColor.GREEN + seller + "\n"
							+ ChatColor.DARK_GRAY + "| " + itemDescription + "\n"
							+ ChatColor.GRAY + "Price: " + ChatColor.YELLOW + RANDOM.nextInt(20) + "$");

			items.add(auctionedItem);
		}

		if (InventoryUtility.isValidPage(pageNumber - 1, items, 45)) {
			final ItemStack leftButton = new ItemStack(Material.ARROW);

			final ItemMeta leftButtonMeta = leftButton.getItemMeta();

			assert leftButtonMeta != null;
			leftButtonMeta.setDisplayName(ChatColor.AQUA + "Previous Page " + ChatColor.WHITE + (pageNumber - 1)
					+ ChatColor.DARK_GRAY + " « " + ChatColor.WHITE + pageNumber);

			leftButton.setItemMeta(leftButtonMeta);

			inventory.setItem(45, leftButton);
		}

		if (InventoryUtility.isValidPage(pageNumber + 1, items, 45)) {
			final ItemStack rightButton = new ItemStack(Material.ARROW);

			final ItemMeta rightButtonMeta = rightButton.getItemMeta();

			assert rightButtonMeta != null;

			rightButtonMeta.setDisplayName(ChatColor.AQUA + "Next Page " + ChatColor.WHITE + pageNumber + ChatColor.DARK_GRAY
					+ " » " + ChatColor.WHITE + (pageNumber + 1));

			rightButton.setItemMeta(rightButtonMeta);

			inventory.setItem(53, rightButton);
		}

		for (final ItemStack item : InventoryUtility.getPageItems(items, pageNumber, 45)) {
			inventory.setItem(inventory.firstEmpty(), item);
		}

		final ItemStack firstItem = inventory.getItem(0);
		assert firstItem != null;

		final ItemMeta firstItemMeta = firstItem.getItemMeta();
		assert firstItemMeta != null;

		firstItemMeta.setLocalizedName(String.valueOf(pageNumber));
		firstItem.setItemMeta(firstItemMeta);
	}

	public Inventory getInventory() {
		return inventory;
	}
}
