package com.productsmc.products.item;

import org.bukkit.ChatColor;

public enum Rarity {

	COMMON(ChatColor.WHITE, ChatColor.WHITE + "" + ChatColor.BOLD + "COMMON"),
	UNCOMMON(ChatColor.DARK_GREEN, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "UNCOMMON"),
	RARE(ChatColor.DARK_AQUA, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "RARE"),
	EPIC(ChatColor.AQUA, ChatColor.AQUA + "" + ChatColor.BOLD + "EPIC"),
	LEGENDARY(ChatColor.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "LEGENDARY"),
	MYTHIC(ChatColor.RED, ChatColor.RED + "" + ChatColor.BOLD + "MYTHIC");
	
	private ChatColor color;
	private String lore;
	
	Rarity(ChatColor color, String lore) {
		this.color = color;
		this.lore = lore;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public String getLore() {
		return lore;
	}
	
}
