package com.productsmc.products.util;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import products.Products;

public class ProductsUtil {

	public static String toProperCase(String text) {
		text = text.replaceAll("_", " ");
		String[] split = text.split(" ");
		String newText = "";
		for(String message : split)
			newText += message.substring(0,1).toUpperCase() + message.substring(1).toLowerCase() + " ";
		return newText.substring(0, newText.length()-1);
	}
	
	public static void setItemTag(ItemStack item, String key, Object value) {
        NamespacedKey namespacedKey = new NamespacedKey(Products.getInstance(), key);
        ItemMeta itemMeta = item.getItemMeta();
        if(value instanceof Double) {
            itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.DOUBLE, (double) value);
        } else if(value instanceof Integer) {
            itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, (int) value);
        } else if(value instanceof String) {
            itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, (String) value);
        }
        item.setItemMeta(itemMeta);
    }
	
	public static String getStringTagValue(ItemStack item, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(Products.getInstance(), key);
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.has(namespacedKey, PersistentDataType.STRING)) {
            return container.get(namespacedKey, PersistentDataType.STRING);
        }
        return null;
    }
	
	public static String getRank(Player player) {
		String[] split = player.getDisplayName().split(" ");
		return split[0];
	}
	
	public static String getNumberSymbol(int length) {
		if(length >= 4 && length <= 6)
			return "K";
		else if(length >= 7 && length <= 9)
			return "M";
		else if(length >= 10 && length <= 12)
			return "B";
		else if(length >= 13 && length <= 15)
			return "T";
		else if(length >= 16 && length <= 18)
			return "Q";
		return "";
	}
	
	public static String format(long number) {
		String string = String.valueOf(number);
		if (string.length() < 4)
			return string;
		return string.substring(0, 1) + "." + string.substring(2, 4) + getNumberSymbol(string.length());
	}
	
}
