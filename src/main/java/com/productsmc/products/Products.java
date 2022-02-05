package com.productsmc.products;

import com.productsmc.products.bot.CoinsTopListener;
import com.productsmc.products.bot.DiscordLinkCommand;
import com.productsmc.products.bot.RegisterPlayerListener;
import com.productsmc.products.command.*;
import com.productsmc.products.crate.BundleListener;
import com.productsmc.products.crate.Crate;
import com.productsmc.products.crate.CrateListener;
import com.productsmc.products.crate.CrateManager;
import com.productsmc.products.forging.ForgingCommand;
import com.productsmc.products.generator.Generator;
import com.productsmc.products.generator.GeneratorListener;
import com.productsmc.products.generator.GiveGeneratorCommand;
import com.productsmc.products.guide.GuideCommand;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.listener.*;
import com.productsmc.products.leaderboard.LeaderboardUpdater;
import com.productsmc.products.pads.PadListener;
import com.productsmc.products.pets.GivePetCommand;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.pets.PetCommand;
import com.productsmc.products.profession.farming.FarmingListener;
import com.productsmc.products.profession.mining.KillListener;
import com.productsmc.products.profession.mining.PlayerHitListener;
import com.productsmc.products.quest.QuestCommand;
import com.productsmc.products.scrapper.ScrapCommand;
import com.productsmc.products.shop.SellCommand;
import com.productsmc.products.shop.ShopCommand;
import com.productsmc.products.trail.TrailCommand;
import com.productsmc.products.user.*;
import com.productsmc.products.util.InventoryManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.defaults.HelpCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.productsmc.products.profession.mining.EggListener;
import com.productsmc.products.profession.mining.MiningListener;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.C;

import javax.security.auth.login.LoginException;
import java.util.*;

public class Products extends JavaPlugin {

	public static Products instance;
	private UserManager userManager;
	private InventoryManager inventoryManager;
	public Map<Location, Material> blocks;
	private CrateManager crateManager;
	public LinkedHashMap<UUID, Long> topCoins;
	public LinkedHashMap<UUID, Integer> topLevels;
	public Map<UUID, String> botTokens;

	public static final String BOT_TOKEN = "ODM1NjY2MzU2NjMxMjQwNzQ0.YISw8Q.8FEAFQjfUogo08DZfcYCQPak6xo";
	private JDA jda;

	@Override
	public void onEnable() {
		instance = this;

		JDABuilder builder = JDABuilder.createDefault(BOT_TOKEN);
		builder.setActivity(Activity.playing("products.minehut.gg"));
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS);

		try {

			jda = builder.build();
			jda.addEventListener(new CoinsTopListener());
			jda.addEventListener(new RegisterPlayerListener());

		} catch (LoginException e) {
			e.printStackTrace();
		}

		userManager = new UserManager();
		inventoryManager = new InventoryManager();
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new UserListener(), this);
		getServer().getPluginManager().registerEvents(new MiningListener(), this);
		getServer().getPluginManager().registerEvents(new EggListener(), this);
		getServer().getPluginManager().registerEvents(new FarmingListener(), this);
		getServer().getPluginManager().registerEvents(new GeneratorListener(), this);
		getServer().getPluginManager().registerEvents(new KillListener(), this);
		getServer().getPluginManager().registerEvents(new GeneratorRemoteListener(), this);
		getServer().getPluginManager().registerEvents(new MagicBucketListener(), this);
		getServer().getPluginManager().registerEvents(new BuffBerryListener(), this);
		getServer().getPluginManager().registerEvents(new BombListener(), this);
		getServer().getPluginManager().registerEvents(new BottleListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new CrateListener(), this);
		getServer().getPluginManager().registerEvents(new PadListener(), this);
		getServer().getPluginManager().registerEvents(new SellCommand(), this);
		getServer().getPluginManager().registerEvents(new PlayerHitListener(), this);
		getServer().getPluginManager().registerEvents(new FlightEvent(), this);
		getServer().getPluginManager().registerEvents(new BundleListener(), this);
		getServer().getPluginManager().registerEvents(new CommandBlocker(), this);
		getServer().getPluginManager().registerEvents(new FrostedCakeListener(), this);
		getCommand("shards").setExecutor(new ShardCommand());
		getCommand("setshards").setExecutor(new ShardCommand());
		getCommand("coins").setExecutor(new CoinsCommand());
		getCommand("link").setExecutor(new DiscordLinkCommand());
		getCommand("setcoins").setExecutor(new CoinsCommand());
		getCommand("test").setExecutor(new TestCommand());
		getCommand("givepet").setExecutor(new GivePetCommand());
		getCommand("pets").setExecutor(new PetCommand());
		getCommand("givegen").setExecutor(new GiveGeneratorCommand());
		getCommand("forge").setExecutor(new ForgingCommand());
		getCommand("quests").setExecutor(new QuestCommand());
		getCommand("giveitem").setExecutor(new ItemCommand());
		getCommand("level").setExecutor(new LevelCommand());
		getCommand("setlevel").setExecutor(new LevelCommand());
		getCommand("setexperience").setExecutor(new LevelCommand());
		getCommand("refreshcrates").setExecutor(new CrateRefreshCommand());
		getCommand("shop").setExecutor(new ShopCommand());
		getCommand("addgens").setExecutor(new GensCommand());
		getCommand("setgens").setExecutor(new GensCommand());
		getCommand("cleargens").setExecutor(new GensCommand());
		getCommand("addmulti").setExecutor(new MultiCommand());
		getCommand("setmulti").setExecutor(new MultiCommand());
		getCommand("sell").setExecutor(new SellCommand());
		getCommand("coinstop").setExecutor(new CoinsCommand());
		getCommand("spawn").setExecutor(new WarpCommand());
		getCommand("crates").setExecutor(new WarpCommand());
		getCommand("start").setExecutor(new StartCommand());
		getCommand("guide").setExecutor(new GuideCommand());
		getCommand("pay").setExecutor(new PayCommand());
		getCommand("shardpay").setExecutor(new PayCommand());
		getCommand("mine").setExecutor(new WarpCommand());
		getCommand("farm").setExecutor(new WarpCommand());
		getCommand("warp").setExecutor(new WarpCommand());
		getCommand("discord").setExecutor(new DiscordCommand());
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("stats").setExecutor(new StatsCommand());
		getCommand("scrap").setExecutor(new ScrapCommand());
		getCommand("leveltop").setExecutor(new LevelCommand());
		getCommand("trails").setExecutor(new TrailCommand());
		getCommand("checkquests").setExecutor(new TestQuestCommand());
		blocks = new HashMap<Location, Material>();
		crateManager = new CrateManager();
		addCrates();

		botTokens = new HashMap<>();

		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					userManager.saveData(player.getUniqueId());
				}
			}
		}.runTaskTimer(this, 12000, 12000);

		new BukkitRunnable () {
			@Override
			public void run() {
				int random = new Random().nextInt(101);

				for(Player player : Bukkit.getOnlinePlayers()) {
					User user = userManager.getUser(player.getUniqueId());

					if(user.getDiscordId() == null) {
						if(random <= 33) {
							player.sendMessage(Products.getPrefix() + "Join our Discord for " + ChatColor.YELLOW + "free stuff" + ChatColor.WHITE + " using " + ChatColor.YELLOW + "/discord!");
						} else if(random <= 66) {
							player.sendMessage(Products.getPrefix() + "Link your Discord now for " + ChatColor.YELLOW + "free stuff" + ChatColor.WHITE + " by typing " + ChatColor.YELLOW + "/link");
						} else {
							player.sendMessage(Products.getPrefix() + "Check out the Store using " + ChatColor.YELLOW + "/buy");
						}
					} else {
						player.sendMessage(Products.getPrefix() + "Check out the Store using " + ChatColor.YELLOW + "/buy");
					}

				}

			}
		}.runTaskTimer(this, 6000, 6000);

		topCoins = new LinkedHashMap<>();
		topLevels = new LinkedHashMap<>();


		new BukkitRunnable() {
			public void run() {
				LeaderboardUpdater.updateCoinsLeaderboard();
			}
		}.runTaskTimer(Products.getInstance(), 2400, 72000);

		new BukkitRunnable() {
			public void run() {
				LeaderboardUpdater.updateLevelLeaderboard();
			}
		}.runTaskTimer(Products.getInstance(), 2400, 72000);

	}
	
	@Override
	public void onDisable() {
		for(Location location : blocks.keySet())
			location.getBlock().setType(blocks.get(location));
		for(User user : userManager.getUsers().values()) {
			userManager.saveData(user.getID());
		}
		instance = null;
		jda.shutdown();
	}
	
	public static String getPrefix() {
		return ChatColor.YELLOW + "" + ChatColor.BOLD + "PRODUCTS " + ChatColor.GRAY + "> " + ChatColor.WHITE;
	}
	
	public static Products getInstance() {
		return instance;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public CrateManager getCrateManager() {
		return crateManager;
	}

	public void addCrates() {
		List<Item> commonItems = new ArrayList<>();

		commonItems.add(Item.STEEL_SWORD);
		commonItems.add(Item.BUFF_BERRY);
		commonItems.add(Item.IRON_GENERATOR);
		commonItems.add(Item.COPPER_GENERATOR);
		commonItems.add(Item.SPRAY_BOTTLE);

		List<Item> commonRotationals = new ArrayList<>();

		commonRotationals.add(Item.GOLD_GENERATOR);
		commonRotationals.add(Item.UNCOMMON_KEY);
		commonRotationals.add(Item.COBALT_PICK);
		commonRotationals.add(Item.GENERATOR_REMOTE);

		crateManager.addCrate(new Crate("Common", new Location(Bukkit.getWorld("ul_Spawn"), -760, 56, 629), Item.COMMON_KEY.getItem(), commonItems, commonRotationals));

		List<Item> uncommonItems = new ArrayList<>();

		uncommonItems.add(Item.CRYOLANCE);
		uncommonItems.add(Item.FERTILIZER);
		uncommonItems.add(Item.DARK_HELMET);
		uncommonItems.add(Item.DARK_CHESTPLATE);
		uncommonItems.add(Item.DARK_LEGGINGS);
		uncommonItems.add(Item.DARK_BOOTS);
		uncommonItems.add(Item.GOLD_GENERATOR);
		uncommonItems.add(Item.REDSTONE_GENERATOR);
		uncommonItems.add(Item.COMMON_PET);

		List<Item> uncommonRotationals = new ArrayList<>();

		uncommonRotationals.add(Item.DARK_BOMB);
		uncommonRotationals.add(Item.RARE_KEY);
		uncommonRotationals.add(Item.ORE_UPGRADER);
		uncommonRotationals.add(Item.COMMON_LOOTBOX);
		uncommonRotationals.add(Item.UNCOMMON_PET);

		crateManager.addCrate(new Crate("Uncommon", new Location(Bukkit.getWorld("ul_Spawn"), -760, 56, 639), Item.UNCOMMON_KEY.getItem(), uncommonItems, uncommonRotationals));

		List<Item> rareItems = new ArrayList<>();

		rareItems.add(Item.UNCOMMON_PET);
		rareItems.add(Item.UNCOMMON_MAT_BUNDLE);
		rareItems.add(Item.RARE_PET);
		rareItems.add(Item.OBSIDIAN_GENERATOR);
		rareItems.add(Item.MINING_BOMB);
		rareItems.add(Item.FARMING_BOMB);

		List<Item> rareRotationals = new ArrayList<>();

		rareRotationals.add(Item.EGG_HEAVER);
		rareRotationals.add(Item.EPIC_PET);
		rareRotationals.add(Item.UNCOMMON_LOOTBOX);
		rareRotationals.add(Item.DARK_BOMB);
		rareRotationals.add(Item.EPIC_KEY);


		crateManager.addCrate(new Crate("Rare", new Location(Bukkit.getWorld("ul_Spawn"), -758, 56, 631), Item.RARE_KEY.getItem(), rareItems, rareRotationals));

		List<Item> epicItems = new ArrayList<>();

		epicItems.add(Item.RARE_PET);
		epicItems.add(Item.EPIC_MAT_BUNDLE);
		epicItems.add(Item.EPIC_PET);
		epicItems.add(Item.DARK_BOMB);
		epicItems.add(Item.GEN_SLOT);

		List<Item> epicRotationals = new ArrayList<>();

		epicRotationals.add(Item.LEGENDARY_KEY);
		epicRotationals.add(Item.LEGENDARY_PET);
		epicRotationals.add(Item.RARE_LOOTBOX);

		crateManager.addCrate(new Crate("Epic", new Location(Bukkit.getWorld("ul_Spawn"), -758, 56, 637), Item.EPIC_KEY.getItem(), epicItems, epicRotationals));

		List<Item> legendaryItems = new ArrayList<>();

		legendaryItems.add(Item.LEGENDARY_PET);
		legendaryItems.add(Item.TWO_GEN_SLOTS);
		legendaryItems.add(Item.GEN_SLOT);
		legendaryItems.add(Item.SMALL_MULTIPLIER);

		List<Item> legendaryRotationals = new ArrayList<>();

		legendaryRotationals.add(Item.PET_SLOT);
		legendaryRotationals.add(Item.EPIC_LOOTBOX);

		crateManager.addCrate(new Crate("Legendary", new Location(Bukkit.getWorld("ul_Spawn"), -756, 56, 634), Item.LEGENDARY_KEY.getItem(), legendaryItems, legendaryRotationals));

		CrateListener.rotateItems(false);

	}

}
