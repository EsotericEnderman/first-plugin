package net.slqmy.first_plugin.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.slqmy.first_plugin.*;
import net.slqmy.first_plugin.Utility;
import net.slqmy.first_plugin.maps.Caet;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.type.Cake;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.util.*;

public class EventListener implements Listener {
	private FirstPlugin firstPlugin;
	private BossBar bossBar;
	private Map<UUID, UUID> hashmap;
	private boolean chatEnabled;
	// private List<UUID> chatEnabled = new ArrayList<>();

	// chatEnabled.contains;
	// chatEnabled.add;

	private List<UUID> movementDisabled = new ArrayList<>();

	public EventListener(FirstPlugin firstPlugin, BossBar bossBar, Map<UUID, UUID> hashmap) {
		this.bossBar = bossBar;
		this.hashmap = hashmap;
		this.firstPlugin = firstPlugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (movementDisabled.contains(player.getUniqueId())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "Trolled! Stop moving! You are frozen.");
		}
	}

	@EventHandler
	public void OnPlayerEggThrow(PlayerEggThrowEvent event) {
		event.getPlayer().sendMessage("You just threw an egg... why?");
		if (event.isHatching()) {
			event.getPlayer().sendMessage("Also, it hatched! Let there be life!");
		}
	}

	@EventHandler
	public void OnEntitySpawn(CreatureSpawnEvent event) {
		// event.setCancelled(true);
		// event.getEntity().setGlowing(true);
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		event.setDroppedExp(1000);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {

		if (!(event.getEntity() instanceof Player)) {
			// event.getEntity().remove();
		};
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

		Objective objective = board.registerNewObjective("epic-board", Criteria.create("dummy"), "epic-board");

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		// Colour codes count as 2 characters.
		objective.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "EPIC SERVER");

		// Every line on a scoreboard has to be unique. Simply increase number of spaces to have multiple spaces.

		Score website = objective.getScore(ChatColor.YELLOW + "https://www.the-slimy-swamp.net");
		website.setScore(1);

		Score space = objective.getScore("");
		space.setScore(2);

		Score name = objective.getScore(ChatColor.GREEN + "Name: " + player.getName());
		name.setScore(3);

		Team blocksBroken = board.registerNewTeam("blocks_broken");
		// Convention is to use ChatColours here.
		blocksBroken.addEntry(ChatColor.AQUA.toString());
		blocksBroken.setPrefix(ChatColor.AQUA + "Blocks broken: ");
		blocksBroken.setSuffix(ChatColor.YELLOW.toString() + player.getStatistic(Statistic.MINE_BLOCK));

		Score blocksBrokenScore = objective.getScore(ChatColor.AQUA.toString());
		blocksBrokenScore.setScore(4);

		bossBar.addPlayer(player);

		NametagManager.setNametags(player, board);
		NametagManager.createNewTag(player);

		player.setResourcePack("https://filebin.net/py532smdnybfgkxs/Trading_Wanderer_v1.1_-_1.20.1.zip");

		player.sendTitle(ChatColor.GREEN + "Welcome to The Slimy Swamp!", ChatColor.YELLOW + "Thank you for joining.", 20, 100,  20);

		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§lWelcome! Have fun!"));

		player.setPlayerListHeader(ChatColor.GREEN + "The Slimy Swamp\n" + ChatColor.WHITE + "Enjoy your stay!\n");
		player.setPlayerListFooter(ChatColor.RED + "\nHey!\n" + "This is another line!");

		// Clickable
		// None
		// Hoverable

		TextComponent clickable = new TextComponent(ChatColor.RED + "Click here to receive some guns!");
		clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "give-guns"));

		TextComponent none = new TextComponent(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "\n\nWarning:" + ChatColor.RED + " These guns are very powerful!");

		TextComponent hoverable = new TextComponent(ChatColor.YELLOW + "\n\nHover over this to view the secret message!");
		hoverable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Troll!")));

		Player.Spigot spigotPlayer = player.spigot();

		spigotPlayer.sendMessage(clickable, none, hoverable);

		// Please join our Discord server!

		TextComponent start = new TextComponent(ChatColor.DARK_PURPLE + "Please join our ");
		TextComponent link = new TextComponent(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Discord server");

		link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.discord.gg/SjAGgJaCYc"));
		link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click to join!")));

		TextComponent end = new TextComponent(ChatColor.DARK_PURPLE + "!");

		start.addExtra(link);
		start.addExtra(end);

		spigotPlayer.sendMessage(start);

		Utility.setSkin(player);

		System.out.println(player.getStatistic(Statistic.DAMAGE_TAKEN));
		player.incrementStatistic(Statistic.DAMAGE_TAKEN, 10_000);
		System.out.println(player.getStatistic(Statistic.DAMAGE_TAKEN));

		player.giveExpLevels(5);

		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

		LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
		LeatherArmorMeta chestPlateMeta = (LeatherArmorMeta) chestPlate.getItemMeta();
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();

		helmetMeta.setColor(Color.RED);
		chestPlateMeta.setColor(Color.GREEN);
		leggingsMeta.setColor(Color.BLUE);
		bootsMeta.setColor(Color.fromRGB(179 , 255, 255));

		helmet.setItemMeta(helmetMeta);
		chestPlate.setItemMeta(chestPlateMeta);
		leggings.setItemMeta(leggingsMeta);
		boots.setItemMeta(bootsMeta);

		Inventory playerInventory = player.getInventory();

		playerInventory.addItem(helmet);
		playerInventory.addItem(chestPlate);
		playerInventory.addItem(leggings);
		playerInventory.addItem(boots);

		PotionEffect potionEffect = new PotionEffect(PotionEffectType.JUMP, 2000, 20, true, false, false);

		player.addPotionEffect(potionEffect);

		for (PotionEffect effect : player.getActivePotionEffects()) {
			System.out.println(player.getName() + " has the potion effect " + effect.getType() + ".");
		}

		PersistentDataContainer container = player.getPersistentDataContainer();
		container.set(new NamespacedKey(firstPlugin, "player_value"), PersistentDataType.STRING, "Epic slime!");

		if (player.getPersistentDataContainer().has(new NamespacedKey(firstPlugin, "player_value"), PersistentDataType.STRING)) {
			System.out.println(player.getPersistentDataContainer().get(new NamespacedKey(firstPlugin, "player_value"), PersistentDataType.STRING));
		}
	}

	@EventHandler
	public void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent event) {
		PlayerResourcePackStatusEvent.Status status = event.getStatus();

		System.out.println(status.name());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		hashmap.remove(player.getUniqueId());
		NametagManager.removeTag(player);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();

		event.getPlayer().getScoreboard().getTeam("blocks_broken").setSuffix(ChatColor.YELLOW.toString() + player.getStatistic(Statistic.MINE_BLOCK));
	}

	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();

		ItemStack mainHand = player.getInventory().getItemInMainHand();

		if (event.isSneaking() && mainHand != null && mainHand.getType().equals(Material.MUSIC_DISC_PIGSTEP)) {
			Location playerLocation = player.getLocation();

			Firework firework = player.getWorld().spawn(playerLocation, Firework.class);
			FireworkMeta fireworkMeta = firework.getFireworkMeta();

			fireworkMeta.addEffect(
							FireworkEffect.builder()
											.withColor(Color.RED)
											.withColor(Color.BLACK)
											.with(FireworkEffect.Type.CREEPER)
											.withTrail()
											.withFlicker()
											.withFade(Color.BLACK)
											.build()
			);

			fireworkMeta.setPower(3);

			firework.setFireworkMeta(fireworkMeta);

			// Volume goes from 0 to 1, and if its above 1, it increases the distance at which it can be heard.
			// Pitch is from 0.5 to 2.0. It is the speed at which it is played.
			// 0.5 = half as fast, 2.0 = twice as fast (if I remember correctly).
			player.playSound(playerLocation, Sound.ENTITY_ARMOR_STAND_FALL, 1.0F, 2.0F);

			// Basically the same thing:
			// Bukkit.getWorld("world").playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_FALL, 1.0F, 2.0F);

			player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0F, 2.0F);

			player.playEffect(playerLocation, Effect.RECORD_PLAY, Material.MUSIC_DISC_PIGSTEP);

			Block targetBlock = player.getTargetBlockExact(5);

			Location targetBlockLocation = targetBlock.getLocation();

			if (targetBlock != null) {
				if (targetBlock.getType().equals(Material.OAK_SIGN)) {
					player.sendSignChange(targetBlockLocation, new String[]{
								"I am a sign!",
								"",
							  "And no one else can see this!",
								""
					});
				} else {
					BlockData targetBlockData = Material.DIAMOND_BLOCK.createBlockData();

					player.sendBlockChange(targetBlockLocation, targetBlockData);
				}
			}
		}
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		ProjectileSource projectileSource = event.getEntity().getShooter();

		if (projectileSource instanceof Player) {
			Player player = (Player) projectileSource;

			Entity projectile = event.getEntity();
			ItemStack heldItem = player.getInventory().getItemInMainHand();

			if (projectile instanceof Trident && heldItem != null && heldItem.getType() != null && heldItem.getType().equals(Material.TRIDENT)) {
				event.setCancelled(true);
				player.sendMessage("You can't throw tridents on this server! This is a feature or something.");
			} else if (projectile instanceof EnderPearl) {
				if (player.isInsideVehicle()) {
					Entity vehicle = player.getVehicle();

					if (vehicle instanceof EnderPearl) {
						vehicle.remove();
					}
				}

				projectile.addPassenger(player);
			}
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile projectile = event.getEntity();

		ProjectileSource projectileSource = projectile.getShooter();
		Location location = projectile.getLocation();

		if (projectileSource instanceof Player) {
			Player player = (Player) projectileSource;

			Location playerLocation = player.getLocation();

			double x = location.getX();
			double y = location.getY();
			double z = location.getZ();

			player.sendMessage("You threw a projectile! It landed at the following location: (" + x + ", " + y + ", " + z + "). Also, it was going at " + projectile.getVelocity().length() + "m/s.");

			double x0 = playerLocation.getX();
			double y0 = playerLocation.getY();
			double z0 = playerLocation.getZ();

			double dx = Math.abs(x - x0);
			double dy = Math.abs(y - y0);
			double dz = Math.abs(z - z0);

			double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));

			player.sendMessage("The location is " + distance + " blocks away!");

			if (projectile instanceof Egg) {
				location.getWorld().spawnEntity(location.add(0, 0.5F, 0), EntityType.CHICKEN);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		ItemStack mainHand = player.getInventory().getItemInMainHand();
		ItemMeta mainHandMeta = mainHand.getItemMeta();

		if (mainHand != null && mainHandMeta != null) {
			String itemName = mainHand.getItemMeta().getDisplayName();

			Material mainHandMaterial = mainHand.getType();

			if (event.getAction().equals(Action.RIGHT_CLICK_AIR) && mainHandMaterial.equals(Material.DIAMOND_HOE)) {
				Egg egg = player.launchProjectile(Egg.class, player.getLocation().getDirection());

				egg.setBounce(true);
				egg.setGravity(false);
				egg.setGlowing(true);

				player.spawnParticle(Particle.CRIT, player.getLocation(), 5, 2, 2, 2);
			} else if (mainHandMaterial.equals(Material.NETHER_STAR) && event.getHand().equals(EquipmentSlot.HAND)) {
				chatEnabled = !chatEnabled;

				player.sendMessage("Chat " + chatEnabled + "!");
			} else if (mainHandMaterial.equals(Material.LEVER)) {
				if (movementDisabled.contains(player.getUniqueId())) {
					movementDisabled.remove(player.getUniqueId());
				} else {
					movementDisabled.add(player.getUniqueId());
				}
			} else if (mainHandMaterial.equals(Material.WOODEN_HOE) && itemName.equals(ChatColor.AQUA + "Revolver")) {
				player.launchProjectile(Egg.class, player.getLocation().getDirection().multiply(350));
				player.sendMessage(ChatColor.GREEN + "You have shot the egg!");
			} else if (mainHandMaterial.equals(Material.IRON_HOE) && itemName.equals(ChatColor.DARK_BLUE + "Mini-gun")) {
				player.launchProjectile(Snowball.class, player.getLocation().getDirection().multiply(350));
				player.sendMessage(ChatColor.GREEN + "You have shot the snowball!");
			} else if (mainHandMaterial.equals(Material.NETHERITE_HOE) && itemName.equals(ChatColor.RED + "Shotgun")) {
				Trident trident = player.launchProjectile(Trident.class, player.getLocation().getDirection().multiply(350));

				trident.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
				player.sendMessage(ChatColor.GREEN + "You have shot the trident!");
			} else if (mainHandMaterial.equals(Material.GOLDEN_HOE) && itemName.equals(ChatColor.DARK_GREEN + "Pistol")) {
				Arrow arrow = player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(350));

				arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
				player.sendMessage(ChatColor.GREEN + "You have shot the arrow!");
			}

			if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				BlockState blockState = event.getClickedBlock().getState();
				BlockData blockData = blockState.getBlockData();

				if (blockData instanceof Cake) {
					Cake cake = (Cake) blockData;

					int bites = cake.getBites();

					if (bites == cake.getMaximumBites() - 1) {
            cake.setBites(0);
					}
				} else if (blockData instanceof GlassPane) {
					((GlassPane) blockData).setWaterlogged(true);
				} else if (blockData instanceof Rail) {
					((Rail) blockData).setShape(Rail.Shape.ASCENDING_NORTH);
				}

				blockState.setBlockData(blockData);
				blockState.update();
			}
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();

		Entity rightClicked = event.getRightClicked();

		if (player.isSneaking()) {
			player.addPassenger(rightClicked);
		} else {
			rightClicked.addPassenger(player);
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (!chatEnabled) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "Chat is disabled!");
		}
	}

	@EventHandler
	public void onServerBroadcast(ServerBroadcastEvent event) {
		if (event.getMessage().equalsIgnoreCase("troll")) {
			event.setCancelled(true);

			event.getPlayer().sendMessage("Nope! Can't say that!");
		}
	}

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		event.setMaxPlayers(-5);
		event.setMotd(ChatColor.AQUA + "Local host server - for testing my plugins!\n" + ChatColor.WHITE + "Enjoy your stay!");

		try {
			event.setServerIcon(Bukkit.loadServerIcon(new File("assets/server-icon.png")));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		/*
		 Better method and safest way:
		 inventory.equals(SomeInventoryClassThatICreated);
		*/

		if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle()).equals(ChatColor.RED.toString() + ChatColor.BOLD + "ADMIN MENU") && event.getCurrentItem() != null) {
			int slot = event.getRawSlot();

			switch (slot) {
				// CLOSE.
				case 0:
					break;
				// RANDOM TELEPORT.
				case 11:
					Random random = new Random();

					World world = player.getWorld();
					WorldBorder worldBorder = world.getWorldBorder();
					double worldSize = worldBorder.getSize();
					Location center = worldBorder.getCenter();

					double x = random.nextDouble(-worldSize / 2 + center.getX(), worldSize / 2 + center.getX());
					double y = random.nextDouble(-64, 384);
					double z = random.nextDouble(-worldSize / 2 + center.getZ(), worldSize / 2 + center.getZ());

					player.teleport(new Location(world, x, y, z));
					player.sendMessage(ChatColor.AQUA + "You teleported to (" + x + ", " + y + ", " + z + ").");

					break;

				// KILL YOURSELF.
				case 13:
					player.setHealth(0);
					player.sendMessage(ChatColor.RED + "RIP. You killed yourself.");

					break;

				// CLEAR INVENTORY.
				case 15:
					player.closeInventory();

					Inventory playerInventory = player.getInventory();
					playerInventory.clear();

					player.sendMessage(ChatColor.GOLD + "You have cleared your inventory");

					return;
				case 29:
					break;
				case 31:
					player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[Error]:" + ChatColor.RESET + ChatColor.RED + " SlimePointerException: slime");

					break;
				case 33:
					player.sendMessage(ChatColor.ITALIC + "Why?");

					break;
				default:
					event.setCancelled(true);
					return;
			}

			player.closeInventory();
		}
	}

	@EventHandler
	public void onMapInitialise(MapInitializeEvent event) {
		MapView view = event.getMap();

		for (MapRenderer renderer : view.getRenderers()) {
			view.removeRenderer(renderer);
		}

		// Idea: make a map image loading system.

		view.addRenderer(new Caet());
	}
}
