package com.productsmc.products.user;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import products.Products;
import products.generator.Generator;
import products.item.Mat;
import products.profession.Profession;
import products.util.PScoreboard;
import products.util.ProductsUtil;

import java.util.HashMap;
import java.util.Map;

public class UserListener implements Listener {

	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UserConfig userConfig = new UserConfig(player.getUniqueId());
		Map<Profession, Integer> levels = new HashMap<>();
		Map<Profession, Integer> experience = new HashMap<>();			
		Map<Mat, Integer> mats = new HashMap<>();
		Map<Location, Generator> gens = new HashMap<>();
		if(!userConfig.getConfig().isSet("player.multiplier")) { // Player is new
			for(Profession profession : Profession.values()) {
				levels.put(profession, 1);
				experience.put(profession, 0);
			}
			for(Mat mat : Mat.values())
				mats.put(mat, 0);
			Products.getInstance().getUserManager().addUser(new User(player.getUniqueId(), 500, 50, 25, 1, gens, levels, experience, mats, 0, 0));
		} else { // Player is not new
			FileConfiguration config = userConfig.getConfig();
			if(config.isSet("player.gens"))
				for(String key : config.getConfigurationSection("player.gens.").getKeys(false)) 
					gens.put(config.getLocation("player.gens." + key + ".location"), Generator.valueOf(config.getString("player.gens." + key + ".gen")));
			for(Profession profession : Profession.values()) {
				levels.put(profession, config.getInt("player.levels." + profession.toString()));
				experience.put(profession, config.getInt("player.experience." + profession.toString()));
			}
			for(Mat mat : Mat.values())
				mats.put(mat, config.getInt("player.mats." + mat.toString()));
			Products.getInstance().getUserManager().addUser(new User(player.getUniqueId(), 
					config.getLong("player.coins"), 
					config.getLong("player.shards"), 
					config.getInt("player.slots"),
					config.getDouble("player.multiplier"),
					gens,
					levels,
					experience,
					mats,
					config.getInt("player.itemsSold"),
					config.getInt("player.killed")
			));
		}
		
		PScoreboard board = new PScoreboard();
		User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(player == null || !player.isOnline())
					cancel();
				
				board.setRow("one", ChatColor.AQUA + "" + ChatColor.WHITE, 11, "");
				board.setRow("main", ChatColor.BLACK + "" + ChatColor.WHITE, 10, ChatColor.YELLOW + "" + ChatColor.BOLD + "MAIN");
				board.setRow("coins", ChatColor.BLUE + "" + ChatColor.WHITE, 9, "  Coins: " + ChatColor.YELLOW + ProductsUtil.format(user.getCoins()) + "⛂");
				board.setRow("shards", ChatColor.DARK_AQUA + "" + ChatColor.WHITE, 8, "  Shards: " + ChatColor.YELLOW + ProductsUtil.format(user.getShards()) + "♢");
				board.setRow("rank", ChatColor.DARK_BLUE + "" + ChatColor.WHITE, 7, "  Rank: " + ProductsUtil.getRank(player));
				board.setRow("two", ChatColor.DARK_GRAY + "" + ChatColor.WHITE, 6, "");
				board.setRow("plot", ChatColor.DARK_GREEN + "" + ChatColor.WHITE, 5, ChatColor.YELLOW + "" + ChatColor.BOLD + "PLOT");				
				board.setRow("gens", ChatColor.DARK_PURPLE + "" + ChatColor.WHITE, 4, "  Gens: " + ChatColor.YELLOW + "0/125");
				board.setRow("multi", ChatColor.DARK_RED + "" + ChatColor.WHITE, 3, "  Multiplier: " + ChatColor.YELLOW + user.getMultiplier() + "x");
				board.setRow("three", ChatColor.GOLD + "" + ChatColor.WHITE, 2, "");
				board.setRow("address", ChatColor.GRAY + "" + ChatColor.WHITE, 1, ChatColor.GRAY + "products.minehut.gg");
				board.show(player);
				
			}
			
		}.runTaskTimer(Products.getInstance(), 20, 2);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Products.getInstance().getUserManager().syncData(player.getUniqueId());
			}
			
		}.runTaskTimer(Products.getInstance(), 20 * 15, 20 * 15);
		
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Products.getInstance().getUserManager().syncData(player.getUniqueId());
	}
	
}
