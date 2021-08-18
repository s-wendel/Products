package com.productsmc.products.user;

import com.productsmc.products.item.Mat;
import com.productsmc.products.profession.Profession;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

	private Map<UUID, User> users;
	
	public UserManager() {
		users = new HashMap<UUID, User>();
	}
	
	public User addUser(User user) {
		users.put(user.getID(), user);
		return user;
	}
	
	public User getUser(UUID user) {
		return users.containsKey(user) ? users.get(user) : null;
	}
	
	public void removeUser(UUID user) {
		users.remove(user);
	}
	
	public void syncData(UUID id) {
		UserConfig userConfig = new UserConfig(id);
		FileConfiguration config = userConfig.getConfig();
		User user = users.get(id);
		config.set("player.coins", user.getCoins());
		config.set("player.shards", user.getShards());
		config.set("player.slots", user.getSlots());
		config.set("player.itemsSold", user.getItemsSold());
		config.set("player.killed", user.getKilled());
		int index = 0;
		for(Location location : user.getGens().keySet()) {
			config.set("player.gens." + index + ".gen", user.getGen(location));
			config.set("player.gens." + index + ".location", location);
			index++;
		}
		for(Profession profession : Profession.values()) {
			config.set("player.levels." + profession.toString(), user.getLevel(profession));
			config.set("player.experience." + profession.toString(), user.getExperience(profession));
		}
		for(Mat mat : Mat.values())
			config.set("player.mats." + mat.toString(), user.getMat(mat));
		userConfig.saveConfig();
	}
	
}
