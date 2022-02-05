package com.productsmc.products.user;

import com.productsmc.products.generator.Generator;
import com.productsmc.products.item.Mat;
import com.productsmc.products.item.Rarity;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.profession.Profession;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.quest.QuestCategory;
import com.productsmc.products.trail.Trail;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class User {

	private UUID id;
	private long coins, shards;
	private int genSlots;
	private double multiplier;
	private List<Location> gens;
	private int level;
	private int experience;
	private Map<Mat, Integer> mats;
	private int itemsSold;
	private int killed;
	public long eggCooldown, padCooldown;
	public boolean scrapConfirmation;
	public List<Pet> pets;
	public List<Pet> equippedPets;
	private int petSlots;
	private List<Quest> completedQuests;
	private int questSlots;
	private int gensUpgraded, shulkersUpgraded, cratesOpened, legendaryCrateItems, itemsForged, legendaryItemsForged, matsForged, petsCollected, legendaryPetsCollected, kothsWon, sellWandsForged, tangleCaverns, lushMeadows, itemsScrapped, rareItemsScrapped, snacksConsumed, stolenItems, petAbilities, cropsAtPlot, bombsThrown = 0;
	private Map<DailyQuest, Integer> dailies;
	private Trail trail;
	private String discordId;

	public User(UUID id, long coins, long shards, int genSlots, double multiplier, List<Location> gens, int level, int experience, Map<Mat, Integer> mats, int itemsSold, int killed, List<Pet> pets, List<Pet> equippedPets, int petSlots, List<Quest> completedQuests, int questSlots, Map<DailyQuest, Integer> dailies) {
		this.id = id;
		this.coins = coins;
		this.shards = shards;
		this.genSlots = genSlots;
		this.multiplier = multiplier;
		this.gens = gens;
		this.level = level;
		this.experience = experience;
		this.mats = mats;
		this.itemsSold = itemsSold;
		this.killed = killed;
		eggCooldown = System.currentTimeMillis();
		padCooldown = System.currentTimeMillis();
		scrapConfirmation = false;
		this.pets = pets;
		this.equippedPets = equippedPets;
		this.petSlots = petSlots;
		this.completedQuests = completedQuests;
		this.questSlots = questSlots;
		this.dailies = dailies;
		trail = null;
		discordId = null;
	}

	public Map<DailyQuest, Integer> getDailies() {
		return dailies;
	}

	public int getQuestSlots() {
		return questSlots;
	}

	public void setQuestSlots(int questSlots) {
		this.questSlots = questSlots;
	}

	public UUID getID() {
		return id;
	}
	
	public void setCoins(long coins) {
		this.coins = coins;
	}
	
	public long getCoins() {
		return coins;
	}
	
	public void setShards(long shards) {
		this.shards = shards;
	}
	
	public long getShards() {
		return shards;
	}
	
	public void setSlots(int genSlots) {
		this.genSlots = genSlots;
	}
	
	public int getSlots() {
		return genSlots;
	}
	
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}
	
	public double getMultiplier() {
		return Math.round(multiplier * 100) / 100.0;
	}

	public void addGen(Location location) {
		gens.add(location);
	}
	
	public Generator getGen(Location location) {
		return gens.contains(location) ? Generator.of(location.getBlock().getType()) : null;
	}
	
	public void removeGen(Location location) {
		gens.remove(location);
	}
	
	public List<Location> getGens() {
		return gens;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getExperience() {
		return experience;
	}

	public void setMat(Mat mat, int amount) {
		mats.put(mat, amount);
	}
	
	public int getMat(Mat mat) {
		return mats.get(mat);
	}
	
	public int getBlocks() {
		int total = 0;
		for(Mat mat : Mat.values())
			if(mat.getCategory() == Profession.MINING)
				total += mats.get(mat);
		return total;
	}
	
	public int getCrops() {
		int total = 0;
		for(Mat mat : Mat.values())
			if(mat.getCategory() == Profession.FARMING)
				total += mats.get(mat);
		return total;
	}
	
	public void setItemsSold(int itemsSold) {
		this.itemsSold = itemsSold;
	}
	
	public int getItemsSold() {
		return itemsSold;
	}
	
	public void setKilled(int killed) {
		this.killed = killed;
	}
	
	public int getKilled() {
		return killed;
	}

	public void setTrail(Trail trail) {
		this.trail = trail;
	}

	public Trail getTrail() {
		return trail;
	}

	public void setDiscordId(String discordId) {
		this.discordId = discordId;
	}

	public String getDiscordId() {
		return discordId;
	}

	public void givePet(Pet pet) {
		if (pets.size() + 1 > 21) {
			Player player = Bukkit.getPlayer(id);
			if (player != null) {
				player.sendMessage(ChatColor.RED + "Your pet was not given because your pet storage is full. Type '/pets' and Shift Right-Click any unwanted pets.");
			}
		} else if (pets.size() >= 19) {
			petsCollected += 1;
			for(String roman : ProductsUtil.getRomanNumerals()) {
				Quest quest = Quest.valueOf("PET_COLLECTOR_" + roman);
				if(!completedQuests.contains(quest) && petsCollected >= quest.getAmount()) {
					quest.finishQuest(Bukkit.getPlayer(id));
				}
			}
			if(pet.getRarity() == Rarity.LEGENDARY) {
				legendaryPetsCollected += 1;
				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("ZOOLOGIST_" + roman);
					if(!completedQuests.contains(quest) && legendaryPetsCollected >= quest.getAmount()) {
						quest.finishQuest(Bukkit.getPlayer(id));
					}
				}
			}
			Player player = Bukkit.getPlayer(id);
			if (player != null) {
				player.sendMessage(ChatColor.RED + "Your pet storage is almost full. Please note that you will lose pets when gaining them while having a full storage.");
			}
			pets.add(pet);
		} else {
			petsCollected += 1;
			for(String roman : ProductsUtil.getRomanNumerals()) {
				Quest quest = Quest.valueOf("PET_COLLECTOR_" + roman);
				if(!completedQuests.contains(quest) && petsCollected >= quest.getAmount()) {
					quest.finishQuest(Bukkit.getPlayer(id));
				}
			}
			if(pet.getRarity() == Rarity.LEGENDARY) {
				legendaryPetsCollected += 1;
				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("ZOOLOGIST_" + roman);
					if(!completedQuests.contains(quest) && legendaryPetsCollected >= quest.getAmount()) {
						quest.finishQuest(Bukkit.getPlayer(id));
					}
				}
			}
			pets.add(pet);
		}
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public List<Pet> getClonedPets() {
		return new ArrayList<Pet>(pets);
	}

	public void setEquippedPets(List<Pet> pets) {
		this.equippedPets = equippedPets;
	}

	public List<Pet> getEquippedPets() {
		return equippedPets;
	}

	public void setPetSlots(int petSlots) {
		this.petSlots = petSlots;
	}

	public int getPetSlots() {
		return petSlots;
	}

	public List<Quest> getCompletedQuests() {
		return completedQuests;
	}

	public int getQuestAmount(QuestCategory category) {
		int total = 0;
		for (Quest quest : completedQuests) {
			if (quest.getCategory() == category) {
				total ++;
			}
		}
		return total;
	}

	public int getGensUpgraded() {
		return gensUpgraded;
	}

	public void setGensUpgraded(int gensUpgraded) {
		this.gensUpgraded = gensUpgraded;
	}

	public int getShulkersUpgraded() {
		return shulkersUpgraded;
	}

	public void setShulkersUpgraded(int shulkersUpgraded) {
		this.shulkersUpgraded = shulkersUpgraded;
	}

	public int getCratesOpened() {
		return cratesOpened;
	}

	public void setCratesOpened(int cratesOpened) {
		this.cratesOpened = cratesOpened;
	}

	public int getLegendaryCrateItems() {
		return legendaryCrateItems;
	}

	public void setLegendaryCrateItems(int legendaryCrateItems) {
		this.legendaryCrateItems = legendaryCrateItems;
	}

	public int getItemsForged() {
		return itemsForged;
	}

	public void setItemsForged(int itemsForged) {
		this.itemsForged = itemsForged;
	}

	public int getLegendaryItemsForged() {
		return legendaryItemsForged;
	}

	public void setLegendaryItemsForged(int legendaryItemsForged) {
		this.legendaryItemsForged = legendaryItemsForged;
	}

	public int getMatsForged() {
		return matsForged;
	}

	public void setMatsForged(int matsForged) {
		this.matsForged = matsForged;
	}

	public int getPetsCollected() {
		return petsCollected;
	}

	public void setPetsCollected(int petsCollected) {
		this.petsCollected = petsCollected;
	}

	public int getLegendaryPetsCollected() {
		return legendaryPetsCollected;
	}

	public void setLegendaryPetsCollected(int legendaryPetsCollected) {
		this.legendaryPetsCollected = legendaryPetsCollected;
	}

	public int getKothsWon() {
		return kothsWon;
	}

	public void setKothsWon(int kothsWon) {
		this.kothsWon = kothsWon;
	}

	public int getSellWandsForged() {
		return sellWandsForged;
	}

	public void setSellWandsForged(int sellWandsForged) {
		this.sellWandsForged = sellWandsForged;
	}

	public int getLushMeadows() {
		return lushMeadows;
	}

	public void setLushMeadows(int lushMeadows) {
		this.lushMeadows = lushMeadows;
	}

	public int getTangleCaverns() {
		return tangleCaverns;
	}

	public void setTangleCaverns(int tangleCaverns) {
		this.tangleCaverns = tangleCaverns;
	}

	public int getItemsScrapped() {
		return itemsScrapped;
	}

	public void setItemsScrapped(int itemsScrapped) {
		this.itemsScrapped = itemsScrapped;
	}

	public int getRareItemsScrapped() {
		return rareItemsScrapped;
	}

	public void setRareItemsScrapped(int rareItemsScrapped) {
		this.rareItemsScrapped = rareItemsScrapped;
	}

	public int getSnacksConsumed() {
		return snacksConsumed;
	}

	public void setSnacksConsumed(int snacksConsumed) {
		this.snacksConsumed = snacksConsumed;
	}

	public int getStolenItems() {
		return stolenItems;
	}

	public void setStolenItems(int stolenItems) {
		this.stolenItems = stolenItems;
	}

	public int getPetAbilities() {
		return petAbilities;
	}

	public void setPetAbilities(int petAbilities) {
		this.petAbilities = petAbilities;
	}

	public int getCropsAtPlot() {
		return cropsAtPlot;
	}

	public void setCropsAtPlot(int cropsAtPlot) {
		this.cropsAtPlot = cropsAtPlot;
	}

	public int getBombsThrown() {
		return bombsThrown;
	}

	public void setBombsThrown(int bombsThrown) {
		this.bombsThrown = bombsThrown;
	}

}
