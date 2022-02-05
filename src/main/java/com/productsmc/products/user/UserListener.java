package com.productsmc.products.user;

import com.productsmc.products.Products;
import com.productsmc.products.generator.Generator;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Mat;
import com.productsmc.products.level.Level;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.profession.Profession;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.trail.Trail;
import com.productsmc.products.util.PScoreboard;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListener implements Listener {

	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UserConfig userConfig = new UserConfig(player.getUniqueId());
		Map<Mat, Integer> mats = new HashMap<>();
		List<Location> gens = new ArrayList<>();
		if(!userConfig.getConfig().isSet("player.multiplier")) { // Player is new
			for(Mat mat : Mat.values()) {
				mats.put(mat, 0);
			}

			Map<DailyQuest, Integer> dailies = new HashMap<>();
			/*int cycle = 1;

			for(DailyQuest quest : DailyQuest.getQuestCycle(3, cycle)) {
				dailies.put(quest, 0);
			}

			FileConfiguration config = userConfig.getConfig();

			config.set("daily.cycle", cycle);
			config.set("daily.reset", System.currentTimeMillis());
			config.set("daily.level", 1);*/

			userConfig.saveConfig();

			Products.getInstance().getUserManager().addUser(new User(player.getUniqueId(), 500, 50, 25, 1, gens, 1, 0, mats, 0, 0, new ArrayList<Pet>(),  new ArrayList<Pet>(), 2, new ArrayList<Quest>(), 3, dailies));


			player.getInventory().addItem(Generator.COAL.getGenerator(5));
			player.getInventory().addItem(Item.COMMON_SELL_WAND.getItem());
			Bukkit.broadcastMessage(Products.getPrefix() + player.getName() + " has joined for the first time! " + ChatColor.YELLOW + " #" + Bukkit.getOfflinePlayers().length + ChatColor.GREEN + " (/help and /start to begin!)");
			Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + player.getName() + " parent set default");
			player.sendMessage(Products.getPrefix() + "Type " + ChatColor.YELLOW + "/start" +  ChatColor.WHITE + " to begin!");

		} else { // Player is not new
			FileConfiguration config = userConfig.getConfig();
			if(config.isSet("player.gens")) {
				for (String key : config.getConfigurationSection("player.gens").getKeys(false)) {
					gens.add(config.getLocation("player.gens." + key));
				}
			}
			for(Mat mat : Mat.values()) {
				mats.put(mat, config.getInt("player.mats." + mat.toString()));
			}

			List<Pet> pets = new ArrayList<>();
			List<Pet> equippedPets = new ArrayList<>();
			List<Quest> completedQuests = new ArrayList<>();

			if(config.isSet("player.pets")) {
				for(String key : config.getConfigurationSection("player.pets").getKeys(false)) {
					pets.add(Pet.valueOf(config.getString("player.pets." + key)));
				}
			}
			if(config.isSet("player.equippedPets")) {
				for(String key : config.getConfigurationSection("player.equippedPets").getKeys(false)) {
					equippedPets.add(Pet.valueOf(config.getString("player.equippedPets." + key)));
				}
			}
			if(config.isSet("player.quests")) {
				for(String key : config.getConfigurationSection("player.quests").getKeys(false)) {
					if (config.getBoolean("player.quests." + key)) {
						completedQuests.add(Quest.valueOf(key));
					}
				}
			}

			int level = config.getInt("player.level");
			int experience = config.getInt("player.experience");

			Map<DailyQuest, Integer> dailies = new HashMap<>();

			/*if(System.currentTimeMillis() - config.getLong("daily.reset") >= 86400000) {

				int cycle = config.getInt("daily.cycle") == 8 ? 1 : config.getInt("daily.cycle") + 1;

				config.set("daily.cycle", cycle);
				config.set("daily.reset", System.currentTimeMillis());
				config.set("daily.level", level);

				userConfig.saveConfig();

				for(DailyQuest quest : DailyQuest.getQuestCycle(config.getInt("player.questSlots"), cycle)) {
					dailies.put(quest, 0);
				}

				player.sendMessage(Products.getPrefix() + "Your Daily Quests have been reset!");

			} else {

				for(String quest : config.getConfigurationSection("daily.quests").getKeys(false)) {
					dailies.put(DailyQuest.valueOf(quest), config.getInt("daily.quests." + quest));
				}

			}*/

			if(level == 5 && experience == 0) {
				player.sendMessage("");
				player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Hey you!");
				player.sendMessage("");
				player.sendMessage("Looks like the Daily Quest bug affected you. If this is true follow these steps:");
				player.sendMessage("1. Join the Discord using /discord");
				player.sendMessage("2. Contact swendel and let him know. swendel#1000");
				player.sendMessage("");
			}

			if(experience >= 100000 || experience < 0) {
				experience = 0;
			}

			User user = new User(player.getUniqueId(),
					config.getLong("player.coins"), 
					config.getLong("player.shards"), 
					config.getInt("player.slots"),
					config.getDouble("player.multiplier"),
					gens,
					level,
					experience,
					mats,
					config.getInt("player.itemsSold"),
					config.getInt("player.killed"),
					pets,
					equippedPets,
					config.getInt("player.petSlots"),
					completedQuests,
					config.getInt("player.questSlots"),
					dailies
			);

			user.setGensUpgraded(config.getInt("player.gensUpgraded"));
			user.setShulkersUpgraded(config.getInt("player.shulkersUpgraded"));
			user.setCratesOpened(config.getInt("player.cratesOpened"));
			user.setLegendaryCrateItems(config.getInt("player.legendaryCrateItems"));
			user.setItemsForged(config.getInt("player.itemsForged"));
			user.setLegendaryItemsForged(config.getInt("player.legendaryItemsForged"));
			user.setMatsForged(config.getInt("player.matsForged"));
			user.setPetsCollected(config.getInt("player.petsCollected"));
			user.setLegendaryPetsCollected(config.getInt("player.legendaryPetsCollected"));
			user.setKothsWon(config.getInt("player.kothsWon"));
			user.setSellWandsForged(config.getInt("player.sellWandsForged"));
			user.setLushMeadows(config.getInt("player.lushMeadows"));
			user.setTangleCaverns(config.getInt("player.tangleCaverns"));
			user.setItemsScrapped(config.getInt("player.itemsScrapped"));
			user.setRareItemsScrapped(config.getInt("player.rareItemsScrapped"));
			user.setSnacksConsumed(config.getInt("player.snacksConsumed"));
			user.setStolenItems(config.getInt("player.stolenItems"));
			user.setPetAbilities(config.getInt("player.petAbilities"));
			user.setCropsAtPlot(config.getInt("player.cropsAtPlot"));
			user.setBombsThrown(config.getInt("player.bombsThrown"));

			if(config.isSet("player.discord")) {
				user.setDiscordId(config.getString("player.discord"));
			}

			if(config.isSet("player.trail")) {
				user.setTrail(Trail.valueOf(config.getString("player.trail")));
			}


			Products.getInstance().getUserManager().addUser(user);

			for(Pet pet : user.getEquippedPets()) {
				switch(pet) {
					case GOLEM:
						Pet.GOLEM.ability(player, null,null, 0);
						break;
					case TURTLE:
						Pet.TURTLE.ability(player, null,null, 0);
						break;
					case TIGER:
						Pet.TIGER.ability(player, null, null, 0);
						break;
				}
			}

		}
		
		PScoreboard board = new PScoreboard();
		User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(player == null || !player.isOnline())
					cancel();
				
				board.setRow("one", ChatColor.AQUA + "" + ChatColor.WHITE, 12, "");
				board.setRow("main", ChatColor.BLACK + "" + ChatColor.WHITE, 11, ChatColor.YELLOW + "" + ChatColor.BOLD + "MAIN");
				board.setRow("coins", ChatColor.BLUE + "" + ChatColor.WHITE, 10, "  Coins: " + ChatColor.YELLOW + ProductsUtil.format(user.getCoins()) + "⛂");
				board.setRow("shards", ChatColor.DARK_AQUA + "" + ChatColor.WHITE, 9, "  Shards: " + ChatColor.YELLOW + ProductsUtil.format(user.getShards()) + "♢");
				board.setRow("rank", ChatColor.DARK_BLUE + "" + ChatColor.WHITE, 8, "  Rank: " + ProductsUtil.getRank(player));
				board.setRow("level", ChatColor.YELLOW + "" + ChatColor.WHITE, 7, "  Level: " + ChatColor.YELLOW + user.getLevel() + "✫ " + ChatColor.GRAY + (Math.round((double) user.getExperience() / Level.getExperienceNeeded(user.getLevel() + 1) * 100)) + "%");
				board.setRow("two", ChatColor.DARK_GRAY + "" + ChatColor.WHITE, 6, "");
				board.setRow("plot", ChatColor.DARK_GREEN + "" + ChatColor.WHITE, 5, ChatColor.YELLOW + "" + ChatColor.BOLD + "PLOT");				
				board.setRow("gens", ChatColor.DARK_PURPLE + "" + ChatColor.WHITE, 4, "  Gens: " + ChatColor.YELLOW + user.getGens().size() + "/" + user.getSlots());
				board.setRow("multi", ChatColor.DARK_RED + "" + ChatColor.WHITE, 3, "  Multiplier: " + ChatColor.YELLOW + user.getMultiplier() + "x");
				board.setRow("three", ChatColor.GOLD + "" + ChatColor.WHITE, 2, "");
				board.setRow("address", ChatColor.GRAY + "" + ChatColor.WHITE, 1, ChatColor.GRAY + "products.minehut.gg");
				board.show(player);
				
			}
			
		}.runTaskTimer(Products.getInstance(), 20, 2);

		new BukkitRunnable() {

			int count = 0;

			@Override
			public void run() {

				if(user == null || player == null || !player.isOnline()) {
					cancel();
				}

				count++;

				if(count == 15) {
					for(Location location : user.getGens()){
						Generator.of(location.getBlock().getType()).tick(location);
					}
					count = 0;
				}

			}

		}.runTaskTimer(Products.getInstance(), 10 * 15, 20);

		new BukkitRunnable() {

			@Override
			public void run() {

				if(Bukkit.getPlayer(user.getID()) == null) {
					cancel();
				}

				if(user.getTrail() != null) {
					player.getWorld().spawnParticle(user.getTrail().getParticle(), player.getLocation().clone().add(0, 0.35, 0), 3, 0.25, 0.25, 0.25, 0);
				}

			}

		}.runTaskTimer(Products.getInstance(), 20, 2);

	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void playerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Products.getInstance().getUserManager().saveData(player.getUniqueId());
		Products.getInstance().getUserManager().removeUser(player.getUniqueId());
	}
	
}
