package com.productsmc.products.user;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.productsmc.products.Products;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UserConfig {

private UUID player;
	
	public UserConfig(UUID player) {
		this.player = player;
		setupConfig();
	}
	
	private File file;
	private FileConfiguration config;
	
	public void setupConfig() {
		file = new File(Products.getInstance().getDataFolder().getPath() + File.separator + "players", player + ".yml");
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		config = new YamlConfiguration();	
		try { config.load(file); } catch (IOException | InvalidConfigurationException e) {}
    }
	
	public FileConfiguration getConfig() {
		return config;
	}

	public void saveConfig() {
		try { config.save(file); } catch (IOException e) { System.out.println("Couldn't save file for " + file.getName() + "!"); }
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}
	
}
