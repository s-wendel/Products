package com.productsmc.products.user;

import org.bukkit.Location;
import products.generator.Generator;
import products.item.Mat;
import products.profession.Profession;

import java.util.Map;
import java.util.UUID;

public class User {

	private UUID id;
	private long coins, shards;
	private int genSlots;
	private double multiplier;
	private Map<Location, Generator> gens;
	private Map<Profession, Integer> levels;
	private Map<Profession, Integer> experience;
	private Map<Mat, Integer> mats;
	private int itemsSold;
	private int killed;
	public long eggCooldown;
	
	public User(UUID id, long coins, long shards, int genSlots, double multiplier, Map<Location, Generator> gens, Map<Profession, Integer> levels, Map<Profession, Integer> experience, Map<Mat, Integer> mats, int itemsSold, int killed) {
		this.id = id;
		this.coins = coins;
		this.shards = shards;
		this.genSlots = genSlots;
		this.multiplier = multiplier;
		this.gens = gens;
		this.levels = levels;
		this.experience = experience;
		this.mats = mats;
		this.itemsSold = itemsSold;
		this.killed = killed;
		eggCooldown = System.currentTimeMillis();
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
	
	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}
	
	public double getMultiplier() {
		return multiplier;
	}
	
	public void addGen(Location location, Generator gen) {
		gens.put(location, gen);
	}
	
	public Generator getGen(Location location) {
		return gens.containsKey(location) ? gens.get(location) : null;
	}
	
	public void removeGen(Location location) {
		gens.remove(location);
	}
	
	public Map<Location, Generator> getGens() {
		return gens;
	}
	
	public void setLevel(Profession profession, int value) {
		levels.put(profession, value);
	}
	
	public int getLevel(Profession profession) {
		return levels.get(profession);
	}
	
	public int getTotalLevel() {
		int total = 0;
		for(Profession profession : Profession.values())
			total += levels.get(profession);
		return total;
	}
	
	public Map<Profession, Integer> getLevelMap() {
		return levels;
	}
	
	public void setExperience(Profession profession, int value) {
		experience.put(profession, value);
	}
	
	public int getExperience(Profession profession) {
		return experience.get(profession);
	}
	
	public Map<Profession, Integer> getExperienceMap() {
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
	
}
