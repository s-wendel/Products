package com.productsmc.products.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

	ItemStack item;
	ItemMeta itemMeta;
	
	public ItemBuilder(ItemStack item) {
		this.item = item;
		itemMeta = item.getItemMeta();
	}

	public ItemBuilder(Material material) {
		this.item = new ItemStack(material);
		itemMeta = item.getItemMeta();
	}
	
	public ItemBuilder setName(String name) {
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		return this;
	}
	
	public String getName() {
		if(!item.hasItemMeta() || !itemMeta.hasDisplayName()) {
			String name = "";
			String[] split = item.getType().name().split("_");
			for(String string : split)
				name.concat(string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase());
			return name;
		}
		return itemMeta.getDisplayName();
	}
	
	public ItemBuilder addLore(String line) {
		List<String> lines = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();
		lines.add(ChatColor.translateAlternateColorCodes('&', line));
		itemMeta.setLore(lines);
		return this;
	}
	
	public ItemBuilder setLore(int slot, String line) {
		List<String> lines = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();
		lines.set(slot, ChatColor.translateAlternateColorCodes('&', line));
		itemMeta.setLore(lines);
		return this;
	}
	
	public ItemBuilder setLore(String... lines) {
		List<String> newLines = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();
		for(String line : lines)
			newLines.add(ChatColor.translateAlternateColorCodes('&', line));
		itemMeta.setLore(newLines);
		return this;
	}
	
	public boolean removeLore(int slot) {
		List<String> lines = null;
		try {
			lines = itemMeta.getLore();
		} catch(Exception e) {
			return false;
		}
		if(lines.get(slot) == null || lines.get(slot).isEmpty())
			return false;
		lines.remove(slot);
		itemMeta.setLore(lines);
		return true;
	}
	
	public ItemBuilder addEnchantment(Enchantment enchant, int level) {
		item.addUnsafeEnchantment(enchant, level);
		return this;
	}
	
	public void removeEnchantment(Enchantment enchant) {
		item.removeEnchantment(enchant);
	}
	
	public ItemBuilder addFlag(ItemFlag flag) {
		itemMeta.addItemFlags(new ItemFlag[] { flag } );
		return this;
	}
	
	public void removeFlag(ItemFlag flag) {
		itemMeta.removeItemFlags(new ItemFlag[] { flag } );
	}
	
	public ItemBuilder setAmount(int amount) {
		item.setAmount(amount);
		return this;
	}
	
	public ItemBuilder setUnbreakable(boolean unbreakable) {
		itemMeta.setUnbreakable(unbreakable);
		return this;
	}
	
	public ItemStack toItemStack() {
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public ItemStack build() {
		item.setItemMeta(itemMeta);
		return item;
	}
	
}
