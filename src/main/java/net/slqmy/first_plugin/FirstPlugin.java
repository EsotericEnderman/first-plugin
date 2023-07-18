package net.slqmy.first_plugin;

import com.google.gson.Gson;
import com.sk89q.worldedit.WorldEdit;
import net.slqmy.first_plugin.commands.*;
import net.slqmy.first_plugin.data.Data;
import net.slqmy.first_plugin.events.listeners.AsyncPlayerChatEventListener;
import net.slqmy.first_plugin.events.listeners.BlockBreakEventListener;
import net.slqmy.first_plugin.events.listeners.EntityDamageByEntityEventListener;
import net.slqmy.first_plugin.events.listeners.InventoryClickEventListener;
import net.slqmy.first_plugin.events.listeners.MapInitialiseEventListener;
import net.slqmy.first_plugin.events.listeners.PlayerEggThrowEventListener;
import net.slqmy.first_plugin.events.listeners.PlayerInteractEntityEventListener;
import net.slqmy.first_plugin.events.listeners.PlayerInteractEventListener;
import net.slqmy.first_plugin.events.listeners.PlayerJoinEventListener;
import net.slqmy.first_plugin.events.listeners.PlayerMoveEventListener;
import net.slqmy.first_plugin.events.listeners.PlayerQuitEventListener;
import net.slqmy.first_plugin.events.listeners.PlayerResourcePackStatusEventListener;
import net.slqmy.first_plugin.events.listeners.PlayerToggleSneakEventListener;
import net.slqmy.first_plugin.events.listeners.ProjectileHitEventListener;
import net.slqmy.first_plugin.events.listeners.ProjectileLaunchEventListener;
import net.slqmy.first_plugin.events.listeners.ServerBroadcastEventListener;
import net.slqmy.first_plugin.events.listeners.ServerListPingEventListener;
import net.slqmy.first_plugin.utility.Utility;
import net.slqmy.first_plugin.utility.types.Cuboid;
import net.slqmy.first_plugin.utility.types.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public final class FirstPlugin extends JavaPlugin {
	private static final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();
	private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
	private static final String WORLD_NAME = "world";
	private static final int MAX_LIGHT_LEVEL = 15;

	private final Map<UUID, UUID> recentMessages = new HashMap<>();
	private final List<UUID> movementDisabled = new ArrayList<>();

	private Cuboid latestFill;

	private NamespacedKey isPistolBulletKey;
	private NamespacedKey isShotgunBulletKey;
	private NamespacedKey isMiniGunBulletKey;
	private NamespacedKey isGatlingGunBulletKey;
	private NamespacedKey isPoisonLauncherBulletKey;

	private BossBar bossBar = Bukkit.createBossBar(
			ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Wither Storm",
			BarColor.PURPLE,
			BarStyle.SEGMENTED_20
	// BarFlag.CREATE_FOG, - a bit annoying, but good for atmosphere. Could be used
	// in actual boss fights or server-wide events to add more immersion. (Also, I'm
	// not sure if this actually does anything)
	// BarFlag.DARKEN_SKY, - Same as above, but this definitely does something.
	// BarFlag.PLAY_BOSS_MUSIC - Seems to not do anything... (I might be wrong).
	);

	private Boolean chatEnabled = true;

	public Map<UUID, UUID> getRecentMessages() {
		return recentMessages;
	}

	public List<UUID> getMovementDisabled() {
		return movementDisabled;
	}

	public Cuboid getLatestFill() {
		return latestFill;
	}

	public Boolean getChatEnabled() {
		return chatEnabled;
	}

	public BossBar getBossBar() {
		return bossBar;
	}

	public NamespacedKey getIsPistolBulletKey() {
		return isPistolBulletKey;
	}

	public NamespacedKey getIsShotgunBulletKey() {
		return isShotgunBulletKey;
	}

	public NamespacedKey getIsMiniGunBulletKey() {
		return isMiniGunBulletKey;
	}

	public NamespacedKey getIsGatlingGunBulletKey() {
		return isGatlingGunBulletKey;
	}

	public NamespacedKey getIsPoisonLauncherBulletKey() {
		return isPoisonLauncherBulletKey;
	}

	@Override
	public void onEnable() {
		// Plugin startup logic.
		final File dataFolder = getDataFolder();
		dataFolder.mkdir();

		// If there is no config, one is generated by the plugin.
		getConfig().options().copyDefaults();
		saveDefaultConfig();

		final Pair<File, YamlConfiguration> tuple;

		try {
			tuple = Utility.initiateYAMLFile("data", this);
		} catch (final IOException exception) {
			Utility.log("Error creating file 'data.yml'. Cancelled plugin startup!");
			Utility.log(exception.getMessage());
			exception.printStackTrace();
			Utility.log(exception);
			return;
		}

		final File file = tuple.first;

		Utility.log("Attempting to create file 'data.json'.");

		final File jsonFile = new File(dataFolder, "data.json");

		try {
			final boolean success = jsonFile.createNewFile();

			if (!success) {
				Utility.log("File already exists.");
			} else {
				Utility.log("File created successfully.");
			}
		} catch (final IOException exception) {
			Utility.log("Can't load data.json! Error: ");
			Utility.log(exception.getMessage());
			exception.printStackTrace();
			Utility.log(exception);
		}

		final Data rawData = new Data("Slqmy", "Hello world!", new Date());

		final Gson gson = new Gson();

		try {
			final Writer writer = new FileWriter(file, false);

			gson.toJson(rawData, writer);

			writer.flush();
			writer.close();

			Utility.log("Successfully saved 'data.json'!");
		} catch (final IOException exception) {
			Utility.log("Can't save data.json! Error: ");
			Utility.log(exception.getMessage());
			exception.printStackTrace();
			Utility.log(exception);
		}

		try {
			final Reader reader = new FileReader(file);

			final Data data = gson.fromJson(reader, Data.class);

			Utility.log("Player name: " + data.getPlayerName());
			Utility.log("Message: " + data.getMessage());
			Utility.log("Is best plugin? " + data.isBestPlugin());
			Utility.log("Date: " + data.getDate());
		} catch (final FileNotFoundException exception) {
			Utility.log("Can't load data.json! Error: ");
			Utility.log(exception.getMessage());
			exception.printStackTrace();
			Utility.log(exception);
		}

		// Handling commands.

		getCommand("heal").setExecutor(new HealCommand());
		getCommand("players").setExecutor(new PlayersCommand());
		getCommand("secret-message").setExecutor(new SecretMessageCommand());
		getCommand("give-guns").setExecutor(new GiveGunsCommand());
		getCommand("give-book").setExecutor(new GiveBookCommand());
		getCommand("give-banner").setExecutor(new GiveBannerCommand());
		getCommand("punish").setExecutor(new PunishCommand());
		getCommand("menu").setExecutor(new MenuCommand());
		getCommand("hypixel-menu").setExecutor(new HypixelMenuCommand());
		getCommand("complex-gaming-menu").setExecutor(new ComplexGamingMenuCommand());
		getCommand("buff").setExecutor(new BuffCommand());
		getCommand("skull").setExecutor(new SkullCommand());
		getCommand("custom-skull").setExecutor(new CustomSkullCommand());
		getCommand("cool-down").setExecutor(new CooldownCommand());
		getCommand("hologram").setExecutor(new HologramCommand());

		getCommand("broadcast").setExecutor(new BroadcastCommand(this));
		getCommand("config").setExecutor(new ConfigCommand(this));
		getCommand("set-config").setExecutor(new SetConfigCommand(this));
		getCommand("vanish").setExecutor(new VanishCommand(this));
		getCommand("message").setExecutor(new MessageCommand(this));
		getCommand("reply").setExecutor(new ReplyCommand(this));
		getCommand("permissions").setExecutor(new PermissionsCommand(this));
		getCommand("rizz").setExecutor(new RizzCommand(this));
		getCommand("fill").setExecutor(new FillCommand(this));

		final PluginCommand fruitCommand = getCommand("fruit");

		fruitCommand.setExecutor(new FruitCommand());
		fruitCommand.setTabCompleter(new FruitCommand());

		// Really easy to make recipes:
		// Maybe make a recipe manager?

		final ShapedRecipe diamondSwordRecipe = new ShapedRecipe(new NamespacedKey(this, "custom_diamond_sword"),
				new ItemStack(Material.DIAMOND_SWORD));

		diamondSwordRecipe.shape(
				" D ",
				" D ",
				" D ");

		diamondSwordRecipe.setIngredient('D', Material.DIAMOND);

		Bukkit.addRecipe(diamondSwordRecipe);

		final ShapedRecipe elytraRecipe = new ShapedRecipe(new NamespacedKey(this, "custom_elytra"),
				new ItemStack(Material.ELYTRA));

		elytraRecipe.shape(
				" L ",
				"PNP",
				"L L");

		elytraRecipe.setIngredient('L', Material.LEATHER);
		elytraRecipe.setIngredient('P', Material.PHANTOM_MEMBRANE);
		elytraRecipe.setIngredient('N', Material.NETHER_STAR);

		Bukkit.addRecipe(elytraRecipe);

		final ShapedRecipe barrierRecipe = new ShapedRecipe(new NamespacedKey(this, "custom_barrier"),
				new ItemStack(Material.BARRIER));

		barrierRecipe.shape(
				"R R",
				" R ",
				"R R");

		barrierRecipe.setIngredient('R', Material.RED_CONCRETE);

		Bukkit.addRecipe(barrierRecipe);

		final ItemStack customStick = new ItemStack(Material.STICK);

		final ItemMeta stickMeta = customStick.getItemMeta();
		stickMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Epic Stick!");
		stickMeta.addEnchant(Enchantment.DAMAGE_ALL, 7, true);

		customStick.setItemMeta(stickMeta);

		final ShapedRecipe stickRecipe = new ShapedRecipe(new NamespacedKey(this, "custom_stick"), customStick);

		stickRecipe.shape(
				"GGG",
				"GSG",
				"GGG");

		stickRecipe.setIngredient('G', Material.GOLD_BLOCK);
		stickRecipe.setIngredient('S', Material.STICK);

		Bukkit.addRecipe(stickRecipe);

		bossBar.setProgress(1);

		isPistolBulletKey = new NamespacedKey(this, "is_pistol_bullet");
		isShotgunBulletKey = new NamespacedKey(this, "is_shotgun_bullet");
		isMiniGunBulletKey = new NamespacedKey(this, "is_mini-gun_bullet");
		isGatlingGunBulletKey = new NamespacedKey(this, "is_gatling_gun_bullet");
		isPoisonLauncherBulletKey = new NamespacedKey(this, "is_poison_launcher_bullet");

		PLUGIN_MANAGER.registerEvents(new BlockBreakEventListener(), this);
		PLUGIN_MANAGER.registerEvents(new InventoryClickEventListener(), this);
		PLUGIN_MANAGER.registerEvents(new MapInitialiseEventListener(), this);
		PLUGIN_MANAGER.registerEvents(new PlayerInteractEntityEventListener(), this);
		PLUGIN_MANAGER.registerEvents(new PlayerResourcePackStatusEventListener(), this);
		PLUGIN_MANAGER.registerEvents(new PlayerToggleSneakEventListener(), this);
		PLUGIN_MANAGER.registerEvents(new ProjectileLaunchEventListener(), this);
		PLUGIN_MANAGER.registerEvents(new ServerBroadcastEventListener(), this);
		PLUGIN_MANAGER.registerEvents(new ServerListPingEventListener(), this);

		PLUGIN_MANAGER.registerEvents(new AsyncPlayerChatEventListener(this), this);
		PLUGIN_MANAGER.registerEvents(new EntityDamageByEntityEventListener(this), this);
		PLUGIN_MANAGER.registerEvents(new PlayerEggThrowEventListener(this), this);
		PLUGIN_MANAGER.registerEvents(new PlayerJoinEventListener(this), this);
		PLUGIN_MANAGER.registerEvents(new PlayerMoveEventListener(this), this);
		PLUGIN_MANAGER.registerEvents(new PlayerQuitEventListener(this), this);
		PLUGIN_MANAGER.registerEvents(new PlayerInteractEventListener(this), this);
		PLUGIN_MANAGER.registerEvents(new ProjectileHitEventListener(this), this);

		// If plugin doesn't support Maven, then add it by going to file => project
		// structure => libraries.
		final WorldEdit worldEdit = WorldEdit.getInstance();
		Utility.log("Is WorldEdit API working? " + (worldEdit == null ? "no" : "yes") + "...");
		Utility.log("The plugin 'FirstPlugin' has been fully enabled!");

		// Getting persistent data from items:
		// Create a new ItemStack: ItemStack sponge = new ItemStack(Material.SPONGE);.
		// Get the ItemMeta: ItemMeta spongeMeta = sponge.getItemMeta();.
		// Get the persistent data container: spongeMeta.getPersistentDataContainer();.

		// Get block state by: Bukkit.getWorld("world").getBlockAt(1, 1, 1).getState();.
		// Determine what it is.
		// E. g., a sign.
		// To get the sign's data container, use sign.getPersistentDataContainer();.

		// ! VERY IMPORTANT!
		// FOR ALL TILE ENTITIES:
		// Use block.update(); when updating tile entities (blocks like chests, hoppers,
		// etc. )

		final World world = Bukkit.getWorld(WORLD_NAME);

		final Location displaysLocation = world.getBlockAt(0, 120, 0).getLocation();

		// TEXT DISPLAY

		final TextDisplay textDisplay = (TextDisplay) world.spawnEntity(displaysLocation, EntityType.TEXT_DISPLAY);

		textDisplay.setText(ChatColor.DARK_GREEN.toString() + ChatColor.STRIKETHROUGH + "  " + ChatColor.GREEN
				+ ChatColor.BOLD + "THE" + ChatColor.DARK_GREEN + ChatColor.STRIKETHROUGH + " " + ChatColor.GREEN
				+ ChatColor.BOLD + "SLIMY" + ChatColor.DARK_GREEN + ChatColor.STRIKETHROUGH + " " + ChatColor.GREEN
				+ ChatColor.BOLD + "SWAMP"
				+ ChatColor.DARK_GREEN + ChatColor.STRIKETHROUGH + "  ");
		textDisplay.setShadowed(true);
		textDisplay.setBrightness(new Display.Brightness(MAX_LIGHT_LEVEL, MAX_LIGHT_LEVEL));
		textDisplay.setTransformation(
				new Transformation(new Vector3f(0, 0, 0), new AxisAngle4f(0, 0, 0, 0), new Vector3f(7.5F, 7.5F, 7.5F),
						new AxisAngle4f(0, 0, 0, 0)));
		textDisplay.setBillboard(Billboard.CENTER);

		// BLOCK DISPLAY

		final BlockDisplay blockDisplay = (BlockDisplay) world.spawnEntity(displaysLocation.add(5, -2, 0),
				EntityType.BLOCK_DISPLAY);
		blockDisplay.setBlock(Material.END_PORTAL_FRAME.createBlockData());
		blockDisplay.setBrightness(new Display.Brightness(MAX_LIGHT_LEVEL, MAX_LIGHT_LEVEL));

		// ITEM DISPLAY

		final ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);

		final ItemMeta diamondSwordMeta = diamondSword.getItemMeta();
		diamondSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);

		diamondSword.setItemMeta(diamondSwordMeta);

		final ItemDisplay itemDisplay = (ItemDisplay) world.spawnEntity(displaysLocation.subtract(10, 0, 0),
				EntityType.ITEM_DISPLAY);
		itemDisplay.setItemStack(diamondSword);
		itemDisplay.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.HEAD);

		final Entity bee = world.spawnEntity(new Location(world, 0, 320, 0), EntityType.BEE);

		bee.setCustomName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Bee Bro");
		bee.setGlowing(true);

		final ArmorStand armourStand = (ArmorStand) world.spawnEntity(new Location(world, 0, 320, 0),
				EntityType.ARMOR_STAND);

		armourStand.setArms(true);
		armourStand.setGlowing(true);

		// Use armourStand.remove(); to get rid of armour stands (or any entity really).

		final ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT, 4);
		final ItemMeta netheriteMeta = netherite.getItemMeta();

		netheriteMeta.setDisplayName(ChatColor.BLACK + "Free " + ChatColor.BOLD + "Netherite" + ChatColor.BLACK + "!");

		netherite.setItemMeta(netheriteMeta);

		world.dropItemNaturally(new Location(world, 0, 0, 0), netherite);

		final Block block = world.getBlockAt(0, 0, 0);
		Utility.log("Block at (0, 0, 0): " + block.getType());

		block.setType(Material.DIAMOND_BLOCK);

		// Will create a world. If the world already exists, it will just load that
		// world.
		//
		// For example, Bukkit.createWorld(new WorldCreator("bedwars-4032")); will
		// create a new world named "bedwars-4032".

		// Use WorldLoadEvent to check when the world has loaded.

		// You can also use player.getWorld();.

		// Use world.setStorm(true); to make it rain in a world.
		// Use world.setThundering(true); to make it thunder.
		// Use world.setThunderDuration(5 * 20); to set the thunder duration.

		// 00000 = 06:00.
		// 06000 = 12:00.
		// 12000 = 18:00.
		// 18000 = 24:00.
		world.setTime(0);

		/* BukkitTask bukkitTask = */ SCHEDULER.runTaskLater/* Asynchronously */(this,
				() -> Bukkit.broadcastMessage(
						"Server has started! Up for " + ChatColor.BOLD + "10" + ChatColor.RESET + " seconds and counting."),
				200);

		// Use bukkitTask.cancel(); to cancel tasks.

		SCHEDULER.runTaskTimer/* Asynchronously */(this,
				() -> Bukkit
						.broadcastMessage("This executes every " + ChatColor.BOLD + "1500" + ChatColor.RESET + " seconds! ...and "
								+ ChatColor.BOLD + "10" + ChatColor.RESET + " seconds after the server has started."),
				200, 30_000);
	}
}
