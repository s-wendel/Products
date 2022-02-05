package com.productsmc.products.generator;

import com.productsmc.products.item.Rarity;
import com.productsmc.products.util.ItemBuilder;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public enum Generator {

	COAL(Material.COAL_BLOCK, Material.COAL, Rarity.COMMON, 10, 1, 500),
	IRON(Material.IRON_BLOCK, Material.IRON_INGOT, Rarity.COMMON, 20, 2, 2500),
	COPPER(Material.WAXED_COPPER_BLOCK, Material.COPPER_INGOT, Rarity.COMMON, 30, 3, 7500),
	GOLD(Material.GOLD_BLOCK, Material.GOLD_INGOT, Rarity.COMMON, 40, 4, 25000),
	REDSTONE(Material.REDSTONE_BLOCK, Material.REDSTONE, Rarity.UNCOMMON, 50, 5, 60000),
	LAPIS(Material.LAPIS_BLOCK, Material.LAPIS_LAZULI, Rarity.UNCOMMON, 75, 6, 125000),
	DIAMOND(Material.DIAMOND_BLOCK, Material.DIAMOND, Rarity.UNCOMMON, 100, 7, 250000),
	EMERALD(Material.EMERALD_BLOCK, Material.EMERALD, Rarity.UNCOMMON, 125, 8, 500000),
	NETHERITE(Material.NETHERITE_BLOCK, Material.NETHERITE_INGOT, Rarity.RARE, 150, 9, 800000),
	OBSIDIAN(Material.OBSIDIAN, Material.NETHER_BRICK, Rarity.RARE, 175, 10, 1300000),
	GLOWSTONE(Material.GLOWSTONE, Material.GLOWSTONE_DUST, Rarity.RARE, 200, 11, 1900000),
	MAGMA(Material.MAGMA_BLOCK, Material.MAGMA_CREAM, Rarity.RARE, 250, 12, 2750000),
	BLAZE(Material.HONEYCOMB_BLOCK, Material.BLAZE_POWDER, Rarity.EPIC, 300, 13, 4000000),
	ENDER(Material.END_STONE, Material.ENDER_EYE, Rarity.EPIC, 350, 14, 6000000),
	SHULKER(Material.PURPUR_BLOCK, Material.SHULKER_SHELL, Rarity.EPIC, 400, 15, 13500000),
	SLIME(Material.SLIME_BLOCK, Material.SLIME_BALL, Rarity.EPIC, 450, 16, 25000000),
	MUSHROOM(Material.RED_MUSHROOM_BLOCK, Material.RED_MUSHROOM, Rarity.LEGENDARY, 500, 17, 999999999999L)
	;
	
	private Material block;
	private Material drop;
	private Rarity rarity;
	private int worth;
	private int tier;
	private long upgrade;
	
	Generator(Material block, Material drop, Rarity rarity, int worth, int tier, long upgrade) {
		this.block = block;
		this.drop = drop;
		this.rarity = rarity;
		this.worth = worth;
		this.tier = tier;
		this.upgrade = upgrade;
	}

	public static List<Material> getMaterials() {
		List<Material> materials = new ArrayList<>();
		for(Generator gen : Generator.values()) {
			materials.add(gen.getBlock());
		}
		return materials;
	}

	public static Generator of(Material block) {
		switch(block) {
			case COAL_BLOCK:
				return Generator.COAL;
			case IRON_BLOCK:
				return Generator.IRON;
			case WAXED_COPPER_BLOCK:
				return Generator.COPPER;
			case GOLD_BLOCK:
				return Generator.GOLD;
			case REDSTONE_BLOCK:
				return Generator.REDSTONE;
			case LAPIS_BLOCK:
				return Generator.LAPIS;
			case DIAMOND_BLOCK:
				return Generator.DIAMOND;
			case EMERALD_BLOCK:
				return Generator.EMERALD;
			case NETHERITE_BLOCK:
				return Generator.NETHERITE;
			case OBSIDIAN:
				return Generator.OBSIDIAN;
			case GLOWSTONE:
				return Generator.GLOWSTONE;
			case MAGMA_BLOCK:
				return Generator.MAGMA;
			case HONEYCOMB_BLOCK:
				return Generator.BLAZE;
			case END_STONE:
				return Generator.ENDER;
			case PURPUR_BLOCK:
				return Generator.SHULKER;
			case SLIME_BLOCK:
				return Generator.SLIME;
			case RED_MUSHROOM_BLOCK:
				return Generator.MUSHROOM;
			default:
				return null;
		}
	}

	public static Generator ofDrop(Material block) {
		switch(block) {
			case COAL:
				return Generator.COAL;
			case IRON_INGOT:
				return Generator.IRON;
			case COPPER_INGOT:
				return Generator.COPPER;
			case GOLD_INGOT:
				return Generator.GOLD;
			case REDSTONE:
				return Generator.REDSTONE;
			case LAPIS_LAZULI:
				return Generator.LAPIS;
			case DIAMOND:
				return Generator.DIAMOND;
			case EMERALD:
				return Generator.EMERALD;
			case NETHERITE_INGOT:
				return Generator.NETHERITE;
			case NETHER_BRICK:
				return Generator.OBSIDIAN;
			case GLOWSTONE_DUST:
				return Generator.GLOWSTONE;
			case MAGMA_CREAM:
				return Generator.MAGMA;
			case BLAZE_POWDER:
				return Generator.BLAZE;
			case ENDER_EYE:
				return Generator.ENDER;
			case SHULKER_SHELL:
				return Generator.SHULKER;
			case SLIME_BALL:
				return Generator.SLIME;
			case RED_MUSHROOM:
				return Generator.MUSHROOM;
			default:
				return null;
		}
	}

	public static Generator of(ItemStack item) {
		for(Generator generator : Generator.values()) {
			if(generator.getGenerator(1).isSimilar(item)) {
				return generator;
			}
		}
		return null;
	}

	public Material getBlock() {
		return block;
	}
	
	public ItemStack getGenerator(int amount) {
		return new ItemBuilder(block)
				.setName(ChatColor.YELLOW + ProductsUtil.toProperCase(this.toString() + " Generator"))
				.setLore(ChatColor.WHITE + "Place anywhere on your island",
						ChatColor.WHITE + "to generate sellable items!",
						"",
						rarity.getLore())
				.setAmount(amount)
				.toItemStack();
	}
	
	public Material getDrop() {
		return block;
	}
	
	public ItemStack getItem() {
		return new ItemBuilder(drop)
				.setName(ChatColor.YELLOW + ProductsUtil.toProperCase(drop.name().toString()))
				.setLore(ChatColor.WHITE + "Type " + ChatColor.YELLOW + "'/sell'" + ChatColor.WHITE + " to sell this!",
						"",
						rarity.getLore())
				.toItemStack();
	}
	
	public int getWorth() {
		return worth;
	}
	
	public int getTier() {
		return tier;
	}

	public long getUpgradeCost() {
		return upgrade;
	}

	public Generator next(Generator generator) {
		for(Generator gen : Generator.values()) {
			if(gen.getTier() == generator.getTier()+1){
				return gen;
			}
		}
		return null;
	}

	public void tick(Location location) {
		location.add(0.5, 1.25, 0.5);
		location.getWorld().dropItem(location, getItem()).setVelocity(new Vector());
		location.subtract(0.5, 1.25, 0.5);
	}
	
}
