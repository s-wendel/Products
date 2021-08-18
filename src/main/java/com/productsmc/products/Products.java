package com.productsmc.products;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import com.productsmc.products.command.ShardCommand;
import com.productsmc.products.profession.mining.EggListener;
import com.productsmc.products.profession.mining.MiningListener;
import com.productsmc.products.user.UserListener;
import com.productsmc.products.user.UserManager;

import java.util.HashMap;
import java.util.Map;

public class Products extends JavaPlugin {

	public static Products instance;
	private UserManager userManager;
	public Map<Location, Material> blocks;
	
	@Override
	public void onEnable() {
		instance = this;
		userManager = new UserManager();
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new UserListener(), this);
		getServer().getPluginManager().registerEvents(new MiningListener(), this);
		getServer().getPluginManager().registerEvents(new EggListener(), this);
		getCommand("shards").setExecutor(new ShardCommand());
		blocks = new HashMap<Location, Material>();
	}
	
	@Override
	public void onDisable() {
		for(Location location : blocks.keySet())
			location.getBlock().setType(blocks.get(location));
		instance = null;
	}
	
	public static String getPrefix() {
		return ChatColor.YELLOW + "" + ChatColor.BOLD + "PRODUCTS " + ChatColor.GRAY + "� " + ChatColor.WHITE;
	}
	
	public static Products getInstance() {
		return instance;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
	
}
