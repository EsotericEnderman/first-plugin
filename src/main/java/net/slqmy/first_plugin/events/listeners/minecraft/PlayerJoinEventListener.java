package net.slqmy.first_plugin.events.listeners.minecraft;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.*;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

public final class PlayerJoinEventListener implements Listener {
	private static final ScoreboardManager SCOREBOARD_MANAGER = Bukkit.getScoreboardManager();

	private final Main plugin;
	private final BossBar bossBar;

	public PlayerJoinEventListener(@NotNull final Main plugin) {
		this.plugin = plugin;
		this.bossBar = plugin.getBossBar();
	}

	@EventHandler
	public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		assert SCOREBOARD_MANAGER != null;

		final Scoreboard board = SCOREBOARD_MANAGER.getNewScoreboard();
		final Objective objective = board.registerNewObjective("epic-board", Criteria.create("dummy"), "epic-board");

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		// Colour codes count as 2 characters.
		objective.setDisplayName(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "  THE SLIMY SWAMP  ");

		// Every line on a scoreboard has to be unique. Simply increase number of spaces
		// to have multiple spaces.

		final Score address = objective
				.getScore(
						ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "→ " + ChatColor.GRAY + ChatColor.UNDERLINE + "localhost"
								+ ChatColor.RESET
								+ ChatColor.DARK_GRAY + ChatColor.BOLD + " | " + ChatColor.GRAY + ChatColor.UNDERLINE + "0");
		address.setScore(1);

		final Score space = objective.getScore("");
		space.setScore(2);

		final Team blocksBroken = board.registerNewTeam("blocks_broken");
		// Convention is to use ChatColours here.
		blocksBroken.addEntry(ChatColor.AQUA.toString());
		blocksBroken.setPrefix(ChatColor.AQUA.toString() + ChatColor.BOLD + "Stone mined: ");
		blocksBroken.setSuffix(
				ChatColor.YELLOW.toString() + ChatColor.UNDERLINE + player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));

		final Score blocksBrokenScore = objective.getScore(ChatColor.AQUA.toString());
		blocksBrokenScore.setScore(3);

		final Score name = objective
				.getScore(ChatColor.GREEN.toString() + ChatColor.BOLD + "Name: " + ChatColor.YELLOW + ChatColor.UNDERLINE
						+ player.getName());
		name.setScore(4);

		bossBar.addPlayer(player);

		player.setResourcePack("assets/Trading Wanderer v1.1 - 1.20.1.zip");
		player.setResourcePack("assets/sword-v1-13-1551616579.zip");

		player.sendTitle(ChatColor.GREEN + "Welcome to " + ChatColor.BOLD + "The Slimy Swamp" + ChatColor.GREEN + "!",
				ChatColor.YELLOW + "Thank you for joining. Enjoy your stay!", 20,
				100, 20);

		player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
				TextComponent.fromLegacyText(ChatColor.AQUA + "Welcome! Have fun!"));

		player.setPlayerListHeader(
				ChatColor.GREEN.toString() + ChatColor.BOLD + "The Slimy Swamp\n" + ChatColor.RESET + "Enjoy your stay!\n");
		player.setPlayerListFooter(ChatColor.AQUA + "\nHope you have fun!");

		// Clickable.
		// None.
		// Hoverable.

		final TextComponent clickable = new TextComponent(ChatColor.RED + "Click here to receive some guns!");
		clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "give-guns"));

		final TextComponent none = new TextComponent(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "\n\nWarning:"
				+ ChatColor.RED + " These guns are very powerful!");

		final TextComponent hoverable = new TextComponent(
				ChatColor.YELLOW + "\n\nHover over this to view the secret message!");
		hoverable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Troll!")));

		final Spigot spigotPlayer = player.spigot();

		spigotPlayer.sendMessage(clickable, none, hoverable);

		// Please join our Discord server!

		final TextComponent start = new TextComponent(ChatColor.LIGHT_PURPLE + "\nPlease join our ");
		final TextComponent link = new TextComponent(
				ChatColor.BLUE.toString() + ChatColor.UNDERLINE + "Discord server");

		link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.discord.gg/SjAGgJaCYc"));
		link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click to join!")));

		final TextComponent end = new TextComponent(ChatColor.LIGHT_PURPLE + "!\n");

		start.addExtra(link);
		start.addExtra(end);

		spigotPlayer.sendMessage(start);

		Utility.log("Damage taken (before incrementing): " + player.getStatistic(Statistic.DAMAGE_TAKEN));
		player.incrementStatistic(Statistic.DAMAGE_TAKEN, 10_000);
		Utility.log("Damage taken (after incrementing): " + player.getStatistic(Statistic.DAMAGE_TAKEN));

		player.giveExpLevels(5);

		final ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		final ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
		final ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

		final LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
		final LeatherArmorMeta chestPlateMeta = (LeatherArmorMeta) chestPlate.getItemMeta();
		final LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
		final LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();

		assert helmetMeta != null;
		helmetMeta.setColor(Color.RED);

		assert chestPlateMeta != null;
		chestPlateMeta.setColor(Color.GREEN);

		assert leggingsMeta != null;
		leggingsMeta.setColor(Color.BLUE);

		assert bootsMeta != null;
		bootsMeta.setColor(Color.fromRGB(179, 255, 255));

		helmet.setItemMeta(helmetMeta);
		chestPlate.setItemMeta(chestPlateMeta);
		leggings.setItemMeta(leggingsMeta);
		boots.setItemMeta(bootsMeta);

		final Inventory playerInventory = player.getInventory();

		playerInventory.addItem(helmet);
		playerInventory.addItem(chestPlate);
		playerInventory.addItem(leggings);
		playerInventory.addItem(boots);

		final ItemStack sword = new ItemStack(Material.PAPER);
		final ItemMeta swordMeta = sword.getItemMeta();
		assert swordMeta != null;
		swordMeta.setCustomModelData(1000000);
		sword.setItemMeta(swordMeta);

		playerInventory.addItem(sword);

		final PotionEffect potionEffect = new PotionEffect(PotionEffectType.JUMP, 200, 20, false, false, false);

		player.addPotionEffect(potionEffect);

		for (final PotionEffect effect : player.getActivePotionEffects()) {
			Utility.log(player.getName() + " has the potion effect " + effect.getType() + ".");
		}

		final PersistentDataContainer container = player.getPersistentDataContainer();
		container.set(new NamespacedKey(plugin, "player_value"), PersistentDataType.STRING, "Epic slime!");

		if (player.getPersistentDataContainer().has(new NamespacedKey(plugin, "player_value"),
				PersistentDataType.STRING)) {
			Utility.log("player_value: " + player.getPersistentDataContainer().get(new NamespacedKey(plugin, "player_value"),
					PersistentDataType.STRING));
		}
	}
}
