package com.productsmc.products.generator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import products.item.Rarity;
import products.util.ItemBuilder;
import products.util.ProductsUtil;

public enum Generator {

	COAL(Material.COAL_BLOCK, Material.COAL, Rarity.COMMON, 10, 1),
	IRON(Material.IRON_BLOCK, Material.IRON_INGOT, Rarity.COMMON, 20, 2),
	COPPER(Material.COPPER_BLOCK, Material.COPPER_INGOT, Rarity.COMMON, 30, 3),
	GOLD(Material.GOLD_BLOCK, Material.GOLD_INGOT, Rarity.COMMON, 40, 4),
	REDSTONE(Material.REDSTONE_BLOCK, Material.REDSTONE, Rarity.UNCOMMON, 50, 5),
	LAPIS(Material.LAPIS_BLOCK, Material.LAPIS_LAZULI, Rarity.UNCOMMON, 75, 6),
	DIAMOND(Material.DIAMOND_BLOCK, Material.DIAMOND, Rarity.UNCOMMON, 100, 7),
	EMERALD(Material.EMERALD_BLOCK, Material.EMERALD, Rarity.UNCOMMON, 125, 8),
	NETHERITE(Material.NETHERITE_BLOCK, Material.NETHERITE_INGOT, Rarity.RARE, 150, 9),
	OBSIDIAN(Material.OBSIDIAN, Material.NETHER_BRICK, Rarity.RARE, 175, 10),
	GLOWSTONE(Material.GLOWSTONE, Material.GLOWSTONE_DUST, Rarity.RARE, 200, 11),
	MAGMA(Material.MAGMA_BLOCK, Material.MAGMA_CREAM, Rarity.RARE, 250, 12),
	BLAZE(Material.HONEYCOMB_BLOCK, Material.BLAZE_POWDER, Rarity.EPIC, 300, 13),
	ENDER(Material.END_STONE, Material.ENDER_EYE, Rarity.EPIC, 350, 14),
	SHULKER(Material.PURPUR_BLOCK, Material.SHULKER_SHELL, Rarity.EPIC, 400, 15);
	
	private Material block;
	private Material drop;
	private Rarity rarity;
	private int worth;
	private int tier;
	
	Generator(Material block, Material drop, Rarity rarity, int worth, int tier) {
		this.block = block;
		this.drop = drop;
		this.rarity = rarity;
		this.worth = worth;
		this.tier = tier;
	}
	
	public Material getBlock() {
		return block;
	}
	
	public ItemStack getGenerator() {
		return new ItemBuilder(block)
				.setName(ChatColor.YELLOW + ProductsUtil.toProperCase(this.toString() + " Generator"))
				.setLore(ChatColor.WHITE + "Place anywhere on your island",
						ChatColor.WHITE + "to generate sellable items!",
						"",
						rarity.getLore())
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
	
}
