package com.productsmc.products.item;

import com.productsmc.products.Products;
import com.productsmc.products.profession.Profession;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ItemBuilder;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public enum Mat {

	SHARDS(null, null, null),
	STONE(Material.STONE, Profession.MINING, Rarity.COMMON),
	COAL_ORE(Material.COAL_ORE, Profession.MINING, Rarity.COMMON),
	IRON_ORE(Material.IRON_ORE, Profession.MINING, Rarity.COMMON),
	COPPER_ORE(Material.COPPER_ORE, Profession.MINING, Rarity.UNCOMMON),
	GOLD_ORE(Material.GOLD_ORE, Profession.MINING, Rarity.UNCOMMON),
	REDSTONE_ORE(Material.REDSTONE_ORE, Profession.MINING, Rarity.RARE),
	LAPIS_ORE(Material.LAPIS_ORE, Profession.MINING, Rarity.RARE),
	DIAMOND_ORE(Material.DIAMOND_ORE, Profession.MINING, Rarity.EPIC),
	EMERALD_ORE(Material.EMERALD_ORE, Profession.MINING, Rarity.EPIC),
	DARK_COAL(Material.DEEPSLATE_COAL_ORE, Profession.MINING, Rarity.UNCOMMON),
	DARK_IRON(Material.DEEPSLATE_IRON_ORE, Profession.MINING, Rarity.UNCOMMON),
	DARK_COPPER(Material.DEEPSLATE_COPPER_ORE, Profession.MINING, Rarity.RARE),
	DARK_GOLD(Material.DEEPSLATE_GOLD_ORE, Profession.MINING, Rarity.RARE),
	DARK_REDSTONE(Material.DEEPSLATE_REDSTONE_ORE, Profession.MINING, Rarity.EPIC),
	DARK_LAPIS(Material.DEEPSLATE_LAPIS_ORE, Profession.MINING, Rarity.EPIC),
	DARK_DIAMOND(Material.DEEPSLATE_DIAMOND_ORE, Profession.MINING, Rarity.LEGENDARY),
	DARK_EMERALD(Material.DEEPSLATE_EMERALD_ORE, Profession.MINING, Rarity.LEGENDARY),
	CARROT(Material.CARROT, Profession.FARMING, Rarity.COMMON),
	GOLDEN_CARROT(Material.GOLDEN_CARROT, Profession.FARMING, Rarity.UNCOMMON),
	POTATO(Material.POTATO, Profession.FARMING, Rarity.COMMON),
	GOLDEN_POTATO(Material.RAW_GOLD, Profession.FARMING, Rarity.UNCOMMON),
	BEETROOT(Material.BEETROOT, Profession.FARMING, Rarity.UNCOMMON),
	BEETROOT_SEED(Material.BEETROOT_SEEDS, Profession.FARMING, Rarity.COMMON),
	GOLDEN_BEETROOT(Material.RABBIT_FOOT, Profession.FARMING, Rarity.RARE),
	WHEAT(Material.WHEAT, Profession.FARMING, Rarity.RARE),
	WHEAT_SEED(Material.WHEAT_SEEDS, Profession.FARMING, Rarity.UNCOMMON),
	GOLDEN_WHEAT(Material.YELLOW_DYE, Profession.FARMING, Rarity.EPIC),
	NETHER_WART(Material.NETHER_WART, Profession.FARMING, Rarity.EPIC),
	GOLDEN_NETHER_WART(Material.GOLD_NUGGET, Profession.FARMING, Rarity.LEGENDARY),
	COLOSSAL_STONE(Material.DEEPSLATE, Profession.MINING, Rarity.EPIC),
	RAINBOW_GEM(Material.MAGMA_CREAM, Profession.MINING, Rarity.LEGENDARY),
	RAINBOW_CROP(Material.BLAZE_POWDER, Profession.FARMING, Rarity.LEGENDARY),
	TROPHY(Material.YELLOW_CANDLE, Profession.GLOBAL, Rarity.LEGENDARY),
	;
	
	private Material icon;
	private Rarity rarity;
	Profession category;
	
	Mat(Material icon, Profession category, Rarity rarity) {
		this.icon = icon;
		this.category = category;
		this.rarity = rarity;
	}
	
	public Material getIcon() {
		return icon;
	}
	
	public Rarity getRarity() {
		return rarity;
	}
	
	public Profession getCategory() {
		return category;
	}
	
	public ItemStack getItem() {
		return new ItemBuilder(icon)
				.setName(rarity.getColor() + ProductsUtil.toProperCase(this.toString()))
				.setLore(ChatColor.DARK_GRAY + ProductsUtil.toProperCase(category.toString()) + " Material",
						ChatColor.GRAY + "Can be used to forge items",
						ChatColor.GRAY + "by the Forger using '/forge'",
						"",
						rarity.getLore())
				.setUnbreakable(true)
				.addFlag(ItemFlag.HIDE_ATTRIBUTES)
				.toItemStack();
	}
	
	public void add(UUID id, int amount) {
		Bukkit.getPlayer(id).getInventory().addItem(getItem());
		User user = Products.getInstance().getUserManager().getUser(id);
		user.setMat(this, user.getMat(this)+amount);
	}
	
}
