package com.productsmc.products.user;

import com.productsmc.products.item.Mat;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
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

	public Map<UUID, User> getUsers() {
		return users;
	}
	
	public User getUser(UUID user) {
		return users.containsKey(user) ? users.get(user) : null;
	}
	
	public void removeUser(UUID user) {
		users.remove(user);
	}
	
	public void saveData(UUID id) {
		UserConfig userConfig = new UserConfig(id);
		FileConfiguration config = userConfig.getConfig();
		User user = users.get(id);
		config.set("player.coins", user.getCoins());
		config.set("player.shards", user.getShards());
		config.set("player.slots", user.getSlots());
		config.set("player.itemsSold", user.getItemsSold());
		config.set("player.killed", user.getKilled());
		config.set("player.multiplier", user.getMultiplier());
		config.set("player.level", user.getLevel());
		config.set("player.experience", user.getExperience());
		int index = 0;
		config.set("player.gens", null);
		for(Location location : user.getGens()) {
			config.set("player.gens." + index, location);
			index++;
		}
		for(Mat mat : Mat.values())
			config.set("player.mats." + mat.toString(), user.getMat(mat));
		config.set("player.pets", null);
		config.set("player.equippedPets", null);
		for(int i = 0; i < user.getPets().size(); i++) {
			config.set("player.pets." + i, user.getPets().get(i).toString());
		}
		for(int i = 0; i < user.getEquippedPets().size(); i++) {
			config.set("player.equippedPets." + i, user.getEquippedPets().get(i).toString());
		}
		config.set("player.petSlots", user.getPetSlots());
		for(Quest quest : user.getCompletedQuests()) {
			config.set("player.quests." + quest.toString(), true);
		}
		config.set("player.questSlots", user.getQuestSlots());

		config.set("daily.quests", null);

		for(DailyQuest quest : user.getDailies().keySet()) {
			config.set("daily.quests." + quest.toString(), user.getDailies().get(quest));
		}

		config.set("player.gensUpgraded", user.getGensUpgraded());
		config.set("player.shulkersUpgraded", user.getShulkersUpgraded());
		config.set("player.cratesOpened", user.getCratesOpened());
		config.set("player.legendaryCrateItems", user.getLegendaryCrateItems());
		config.set("player.itemsForged", user.getItemsForged());
		config.set("player.legendaryItemsForged", user.getLegendaryItemsForged());
		config.set("player.matsForged", user.getMatsForged());
		config.set("player.petsCollected", user.getPetsCollected());
		config.set("player.legendaryPetsCollected", user.getLegendaryPetsCollected());
		config.set("player.kothsWon", user.getKothsWon());
		config.set("player.sellWandsForged", user.getSellWandsForged());
		config.set("player.lushMeadows", user.getLushMeadows());
		config.set("player.tangleCaverns", user.getTangleCaverns());
		config.set("player.itemsScrapped", user.getItemsScrapped());
		config.set("player.rareItemsScrapped", user.getRareItemsScrapped());
		config.set("player.snacksConsumed", user.getSnacksConsumed());
		config.set("player.stolenItems", user.getStolenItems());
		config.set("player.petAbilities", user.getPetAbilities());
		config.set("player.cropsAtPlot", user.getCropsAtPlot());
		config.set("player.bombsThrown", user.getBombsThrown());

		if(user.getDiscordId() != null) {
			config.set("player.discord", user.getDiscordId());
		}

		if(user.getTrail() != null) {
			config.set("player.trail", user.getTrail().toString());
		} else {
			config.set("player.trail", null);
		}

		userConfig.saveConfig();
	}
	
}
