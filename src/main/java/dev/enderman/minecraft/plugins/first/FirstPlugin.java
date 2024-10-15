package dev.enderman.minecraft.plugins.first;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.sk89q.worldedit.WorldEdit;
import dev.enderman.minecraft.plugins.first.commands.minecraft.*;
import dev.enderman.minecraft.plugins.first.event.listeners.minecraft.hoglin.*;
import dev.enderman.minecraft.plugins.first.event.listeners.minecraft.player.*;
import dev.esoteric_enderman.first_plugin.commands.minecraft.*;
import dev.esoteric_enderman.first_plugin.events.listeners.minecraft.hoglin_rider.*;
import dev.esoteric_enderman.first_plugin.events.listeners.minecraft.player.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import dev.enderman.minecraft.plugins.first.commands.discord.GiveRoleCommand;
import dev.enderman.minecraft.plugins.first.data.Data;
import dev.enderman.minecraft.plugins.first.event.listeners.discord.MessageListener;
import dev.enderman.minecraft.plugins.first.event.listeners.minecraft.item.MapListener;
import dev.enderman.minecraft.plugins.first.event.listeners.minecraft.projectile.EggThrowListener;
import dev.enderman.minecraft.plugins.first.event.listeners.minecraft.projectile.GunHitListener;
import dev.enderman.minecraft.plugins.first.event.listeners.minecraft.projectile.ProjectileHitListener;
import dev.enderman.minecraft.plugins.first.event.listeners.minecraft.projectile.ProjectileLaunchListener;
import dev.enderman.minecraft.plugins.first.event.listeners.minecraft.server.ServerBroadcastEventListener;
import dev.enderman.minecraft.plugins.first.event.listeners.minecraft.server.ServerListPingEventListener;
import dev.enderman.minecraft.plugins.first.managers.ConfigManager;
import dev.enderman.minecraft.plugins.first.managers.PlayerManager;
import dev.enderman.minecraft.plugins.first.placeholders.IsHoldingSpongePlaceholder;
import dev.enderman.minecraft.plugins.first.utility.DebugUtility;
import dev.enderman.minecraft.plugins.first.utility.HoglinRiderUtility;
import dev.enderman.minecraft.plugins.first.utility.Utility;
import dev.enderman.minecraft.plugins.first.utility.types.Cuboid;
import dev.enderman.minecraft.plugins.first.utility.types.Pair;
import org.bson.Document;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public final class FirstPlugin extends JavaPlugin implements PluginMessageListener {
	private static final PluginManager pluginManager = Bukkit.getPluginManager();

	private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();

	private final BossBar bossBar = Bukkit.createBossBar(
			ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Wither Storm",
			BarColor.PURPLE,
			BarStyle.SEGMENTED_20
	// BarFlag.CREATE_FOG, - a bit annoying, but good for atmosphere. Could be used
	// in actual boss fights or server-wide events to add more immersion. (Also, I'm
	// not sure if this actually does anything)
	// BarFlag.DARKEN_SKY, - Same as above, but this definitely does something.
	// BarFlag.PLAY_BOSS_MUSIC - Seems to not do anything... (I might be wrong).
	);

	private final PlayerManager playerManager = new PlayerManager();

	private final Map<UUID, UUID> recentMessages = new HashMap<>();

	private final Map<UUID, StringBuilder> aiConversations = new HashMap<>();

	private final Map<Integer, UUID> npcs = new HashMap<>();

	private final List<UUID> movementDisabled = new ArrayList<>();

	private Database database;

	private JDA jda;

	private Cuboid latestFill;

	private NamespacedKey isPistolBulletKey;
	private NamespacedKey isShotgunBulletKey;
	private NamespacedKey isMiniGunBulletKey;
	private NamespacedKey isGatlingGunBulletKey;

	private boolean chatEnabled = true;

	public Database getDatabase() {
		return database;
	}

	public JDA getJDA() {
		return jda;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public Map<UUID, UUID> getRecentMessages() {
		return recentMessages;
	}

	public Map<UUID, StringBuilder> getAiConversations() {
		return aiConversations;
	}

	public Map<Integer, UUID> getNPCs() {
		return npcs;
	}

	public List<UUID> getMovementDisabled() {
		return movementDisabled;
	}

	public Cuboid getLatestFill() {
		return latestFill;
	}

	public void setLatestFill(@NotNull final Cuboid newFill) {
		latestFill = newFill;
	}

	public boolean isChatEnabled() {
		return chatEnabled;
	}

	public void setChatEnabled(final boolean state) {
		if (state == chatEnabled) {
			throw new IllegalArgumentException("Chat is already " + (state ? "enabled" : "disabled") + "!");
		}

		chatEnabled = state;
	}

	public BossBar getBossBar() {
		return bossBar;
	}

	// Maybe make some sort of manager for this in other plugins.
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

	@Override
	public void onEnable() {
		// Plugin startup logic.

		Utility.log("Creating plugin data folder...");

		final File dataFolder = getDataFolder();
		final boolean success = dataFolder.mkdir();

		if (success) {
			Utility.log("Plugin data folder successfully created!");
		} else {
			Utility.log("Plugin data folder already exists.");
		}

		// If there is no config, one is generated by the plugin.
		final YamlConfiguration config = (YamlConfiguration) getConfig();

		config.options().copyDefaults();
		saveDefaultConfig();

		saveResource("en.yml", false);
		saveResource("nl.yml", false);

		final Pair<File, YamlConfiguration> tuple;

		try {
			tuple = Utility.initiateYAMLFile("data", this);
		} catch (final IOException exception) {
			DebugUtility.logError(exception, "Error creating file 'data.yml'. Cancelled plugin startup!");
			return;
		}

		assert tuple != null;
		final File file = tuple.first;

		Utility.log("Attempting to create file 'data.json'.");

		final File jsonFile = new File(dataFolder, "data.json");

		try {
			final boolean fileCreated = jsonFile.createNewFile();

			if (!fileCreated) {
				Utility.log("File already exists.");
			} else {
				Utility.log("File created successfully.");
			}
		} catch (final IOException exception) {
			DebugUtility.logError(exception, "Can't load data.json! Error: ");
		}

		final Data rawData = new Data("Slqmy", "Hello world!", new Date());

		final Gson gson = new Gson();

		try {
			final Writer writer = new FileWriter(jsonFile, false);

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
		} catch (final FileNotFoundException | NullPointerException exception) {
			DebugUtility.logError(exception, "Can't load data.json! Error: ");
		}

		database = new Database(this);

		try {
			database.connect();
		} catch (final SQLException | NullPointerException exception) {
			DebugUtility.logError(exception, "There was an error while connection to the database!");
		}

		Utility.log("Connected to SQL database? " + (database.isConnected() ? "yes" : "no") + "!");

		final String connectionString = "mongodb+srv://firstplugin:" + config.getString("MongoDB-Password")
				+ "@datacluster.z5vohpt.mongodb.net/📄・First-Plugin?retryWrites=true&w=majority";

		try (final MongoClient client = MongoClients.create(connectionString)) {
			final MongoDatabase database = client.getDatabase("📄・First-Plugin");
			final MongoCollection<Document> playerData = database.getCollection("Player Data");

			Utility.log("Number of documents in player data collection: " + playerData.countDocuments());

			final Document document = new Document();

			document.put("uuid", UUID.randomUUID());
			document.put("rank", "Owner");
			document.put("coins", 5);

			// Inserting the document: playerData.insertOne(document);. (commented out
			// because it was overly annoying)

			// To replace a document: playerData.replaceOne(Filters.eq("uuid",
			// UUID.randomUUID()), document);.
			// To update a document: playerData.updateOne(Filters.eq("uuid",
			// UUID.randomUUID()), document);.

			try (final MongoCursor<Document> cursor = playerData.find(Filters.eq("rank", "Owner")).cursor()) {
				while (cursor.hasNext()) {
					final Document output = cursor.next();

					Utility.log("How many coins does the owner have? " + output.get("coins") + ".");
					playerData.deleteOne(output);
				}
			}
		} catch (final MongoTimeoutException exception) {
			exception.printStackTrace();
		}

		String botToken = config.getString("Discord-Bot-Token");

		if (botToken != null) {
			final JDABuilder builder = JDABuilder.createDefault(config.getString("Discord-Bot-Token"));

			builder.setActivity(Activity.playing("on The Slimy Swamp"));
			builder.setStatus(OnlineStatus.DO_NOT_DISTURB);

			builder.setEnabledIntents(Arrays.asList(GatewayIntent.values()));

			builder.addEventListeners(new MessageListener());

			jda = builder.build();
		}

		// Handling commands.

		new HealCommand();
		new PlayersCommand();
		new SecretMessageCommand();
		new GiveGunsCommand();
		new GiveBookCommand();
		new GiveBannerCommand();
		new PunishCommand();
		new MenuCommand();
		new HypixelMenuCommand();
		new ComplexGamingMenuCommand();
		new BuffCommand();
		new SkullCommand();
		new CooldownCommand();
		new HologramCommand();
		new AuctionHouseCommand();
		new GiveSnowballsCommand();
		new GiveRodCommand();
		new LaunchCommand();
		new FruitCommand();
		new SpawnRiderCommand();
		new PAPICommand();

		new NPCCommand(this);
		new ConfigCommand(this);
		new SetConfigCommand(this);
		new VanishCommand(this);
		new PermissionsCommand(this);
		new RizzCommand(this);
		new FillCommand(this);
		new GiveRoleCommand(this);

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
		assert stickMeta != null;
		stickMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Epic Stick!");
		stickMeta.addEnchant(Enchantment.SHARPNESS, 15, true);

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

		// Again, maybe make a manager for this. And maybe for the boss bar as well.
		isPistolBulletKey = new NamespacedKey(this, "is_pistol_bullet");
		isShotgunBulletKey = new NamespacedKey(this, "is_shotgun_bullet");
		isMiniGunBulletKey = new NamespacedKey(this, "is_mini-gun_bullet");
		isGatlingGunBulletKey = new NamespacedKey(this, "is_gatling_gun_bullet");

		pluginManager.registerEvents(new HoglinRiderDamageListener(), this);
		pluginManager.registerEvents(new HoglinRiderRegenerateListener(), this);
		pluginManager.registerEvents(new HoglinRiderTargetEntityListener(), this);
		pluginManager.registerEvents(new GUIListener(), this);
		pluginManager.registerEvents(new MapListener(), this);
		pluginManager.registerEvents(new PlayerInteractEntityListener(), this);
		pluginManager.registerEvents(new PlayerResourcePackStatusListener(), this);
		pluginManager.registerEvents(new PlayerToggleSneakListener(), this);
		pluginManager.registerEvents(new ServerBroadcastEventListener(), this);
		pluginManager.registerEvents(new ServerListPingEventListener(), this);
		pluginManager.registerEvents(new NPCClickListener(), this);

		pluginManager.registerEvents(new ChatListener(this), this);
		pluginManager.registerEvents(new ConnectionListener(this), this);
		pluginManager.registerEvents(new HoglinRiderSpawnListener(this), this);
		pluginManager.registerEvents(new GunHitListener(this), this);
		pluginManager.registerEvents(new HoglinRiderDeathListener(this), this);
		pluginManager.registerEvents(new HoglinRiderMoveEventListener(this), this);
		pluginManager.registerEvents(new EggThrowListener(this), this);
		pluginManager.registerEvents(new PlayerInteractListener(this), this);
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
		pluginManager.registerEvents(new PlayerMoveListener(this), this);
		pluginManager.registerEvents(new ProjectileHitListener(this), this);
		pluginManager.registerEvents(new ProjectileLaunchListener(this), this);
		pluginManager.registerEvents(new PlayerDeathListener(), this);

		HoglinRiderUtility.manageHoglinRiders(this);

		// If plugin doesn't support Maven, then add it by going to file => project
		// structure => libraries.
		
		WorldEdit worldEdit = null;

		try {
			worldEdit = WorldEdit.getInstance();
		} catch (NoClassDefFoundError error) {
			error.printStackTrace();
		}

		Utility.log("Is WorldEdit API working? " + (worldEdit == null ? "no" : "yes") + "...");	

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

		final ByteArrayDataOutput out = ByteStreams.newDataOutput();

		/*
		 * Sending a player to a different server:
		 * 
		 * out.writeUTF("Connect");
		 * out.writeUTF("Server-2");
		 * final Player player;
		 * player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
		 * 
		 * Getting persistent data from items:
		 * Create a new ItemStack: ItemStack sponge = new ItemStack(Material.SPONGE);.
		 * Get the ItemMeta: ItemMeta spongeMeta = sponge.getItemMeta();.
		 * Get the persistent data container: spongeMeta.getPersistentDataContainer();.
		 * Get block state by: Bukkit.getWorld("world").getBlockAt(1, 1, 1).getState();.
		 * 
		 * Determine what it is.
		 * E.g., a sign.
		 * To get the sign's data container, use sign.getPersistentDataContainer();.
		 * ! VERY IMPORTANT!
		 * FOR ALL TILE ENTITIES:
		 * 
		 * Use block.update(); when updating tile entities (blocks like chests, hoppers,
		 * etc.)
		 */

		Bukkit.getScheduler().runTaskLater(this, () -> {
			out.writeUTF("PlayerCount");
			out.writeUTF("Server-2");

			final ByteArrayDataOutput kickPlayer = ByteStreams.newDataOutput();
			kickPlayer.writeUTF("KickPlayerRaw");
			kickPlayer.writeUTF("Slvmy");
			kickPlayer.writeUTF("{\"text\":\"Get kicked!\"}");

			final Player player = Bukkit.getPlayer("Slqmy");

			if (player != null) {
				player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
				player.sendPluginMessage(this, "BungeeCord", kickPlayer.toByteArray());
			}
		}, 200);

		// NMS introduction code:
		// final ServerPlayer player = ((CraftPlayer)
		// Bukkit.getPlayer("Slqmy")).getHandle();

		try {
			new IsHoldingSpongePlaceholder().register();
		} catch (NoClassDefFoundError error) {
			error.printStackTrace();
		}

		// Check the version and adjust this variable accordingly.
		final InterfaceExample example = new InterfaceExample1_8();

		// ...

		// Can also use this: https://github.com/CryptoMorin/XSeries.

		example.doSomething();

		Utility.log(new ConfigManager(this).getMessage("welcome-message"));

		// Takes ~5 minutes after the server starts to update, then every ~30 minutes.

		int pluginId = 19300;
		try {
			Metrics metrics = new Metrics(this, pluginId);
			metrics.addCustomChart(new SimplePie("languages_used", () -> config.getString("Language")));
		} catch (IllegalStateException exception) {
			exception.printStackTrace();
		}

		Utility.log("The plugin 'FirstPlugin' has been fully enabled!");

		final String WORLD_NAME = "world";
		final World world = Bukkit.getWorld(WORLD_NAME);

		assert world != null;
		final Location displaysLocation = world.getBlockAt(0, 120, 0).getLocation();

		// TEXT DISPLAY

		final TextDisplay textDisplay = (TextDisplay) world.spawnEntity(displaysLocation, EntityType.TEXT_DISPLAY);

		textDisplay.setText(ChatColor.DARK_GREEN.toString() + ChatColor.STRIKETHROUGH + "  " + ChatColor.GREEN
				+ ChatColor.BOLD + "THE" + ChatColor.DARK_GREEN + ChatColor.STRIKETHROUGH + " " + ChatColor.GREEN
				+ ChatColor.BOLD + "SLIMY" + ChatColor.DARK_GREEN + ChatColor.STRIKETHROUGH + " " + ChatColor.GREEN
				+ ChatColor.BOLD + "SWAMP"
				+ ChatColor.DARK_GREEN + ChatColor.STRIKETHROUGH + "  ");
		textDisplay.setShadowed(true);
		textDisplay.setBrightness(new Display.Brightness(15, 15));
		textDisplay.setTransformation(
				new Transformation(new Vector3f(0, 0, 0), new AxisAngle4f(0, 0, 0, 0), new Vector3f(7.5F, 7.5F, 7.5F),
						new AxisAngle4f(0, 0, 0, 0)));
		textDisplay.setBillboard(Billboard.CENTER);

		// BLOCK DISPLAY

		final BlockDisplay blockDisplay = (BlockDisplay) world.spawnEntity(displaysLocation.add(5, -2, 0),
				EntityType.BLOCK_DISPLAY);
		blockDisplay.setBlock(Material.END_PORTAL_FRAME.createBlockData());
		blockDisplay.setBrightness(new Display.Brightness(15, 15));

		// ITEM DISPLAY

		final ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);

		final ItemMeta diamondSwordMeta = diamondSword.getItemMeta();
		assert diamondSwordMeta != null;
		diamondSwordMeta.addEnchant(Enchantment.SHARPNESS, 1, false);

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

		// Use armourStand.remove(); to get rid of armour stands (or most entities
		// really).

		final ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT, 4);
		final ItemMeta netheriteMeta = netherite.getItemMeta();

		assert netheriteMeta != null;
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
		world.setStorm(true);
		world.setWeatherDuration(400);

		/* BukkitTask bukkitTask = */
		SCHEDULER.runTaskLater/* Asynchronously */(this,
				() -> Bukkit.broadcastMessage(
						"Server has started! Up for " + ChatColor.BOLD + "10" + ChatColor.RESET
								+ " seconds and counting."),
				200);

		// Use bukkitTask.cancel(); to cancel tasks.

		SCHEDULER.runTaskTimer/* Asynchronously */(this,
				() -> Bukkit
						.broadcastMessage("This executes every " + ChatColor.BOLD + "1500" + ChatColor.RESET
								+ " seconds! ...and "
								+ ChatColor.BOLD + "10" + ChatColor.RESET + " seconds after the server has started."),
				200, 30_000);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic.
		if (database != null) {
			database.disconnect();
		}
	}

	@Override
	public void onPluginMessageReceived(@NotNull final String channel, @NotNull final Player player,
			final byte @NotNull [] data) {
		if (!channel.equals("BungeeCord")) {
			return;
		}

		final ByteArrayDataInput in = ByteStreams.newDataInput(data);
		final String subChannel = in.readUTF();

		if (subChannel.equals("PlayerCount")) {
			final String server = in.readUTF();
			final int players = in.readInt();

			Utility.log("Server " + server + " has " + players + " players online!");
		}
	}
}
