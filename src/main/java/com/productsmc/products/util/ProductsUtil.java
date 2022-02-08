package com.productsmc.products.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.productsmc.products.Products;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.yaml.snakeyaml.introspector.Property;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

	public static String getArmorName(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
			return name.split(" ") [0];
		}
		return "";
	}

	public static boolean hasArmorSet(Player player, String set) {
		for (ItemStack armor : player.getInventory().getArmorContents()) {
			if (!getArmorName(armor).equals(set)) {
				return false;
			}
		}
		return true;
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

	public static Material getNextOre(Material ore) {
		switch(ore) {
			case STONE:
				return Material.COAL_ORE;
			case COAL_ORE:
				return Material.IRON_ORE;
			case IRON_ORE:
				return Material.COPPER_ORE;
			case COPPER_ORE:
				return Material.GOLD_ORE;
			case GOLD_ORE:
				return Material.REDSTONE_ORE;
			case REDSTONE_ORE:
				return Material.LAPIS_ORE;
			case LAPIS_ORE:
				return Material.DIAMOND_ORE;
			case DIAMOND_ORE:
				return Material.EMERALD_ORE;
			case DEEPSLATE_COAL_ORE:
				return Material.DEEPSLATE_IRON_ORE;
			case DEEPSLATE_IRON_ORE:
				return Material.DEEPSLATE_COPPER_ORE;
			case DEEPSLATE_COPPER_ORE:
				return Material.DEEPSLATE_GOLD_ORE;
			case DEEPSLATE_GOLD_ORE:
				return Material.DEEPSLATE_REDSTONE_ORE;
			case DEEPSLATE_REDSTONE_ORE:
				return Material.DEEPSLATE_LAPIS_ORE;
			case DEEPSLATE_LAPIS_ORE:
				return Material.DEEPSLATE_DIAMOND_ORE;
			case DEEPSLATE_DIAMOND_ORE:
				return Material.DEEPSLATE_EMERALD_ORE;
			default:
				return ore;
		}
	}

	private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
	static {
		suffixes.put(1_000L, "K");
		suffixes.put(1_000_000L, "M");
		suffixes.put(1_000_000_000L, "B");
		suffixes.put(1_000_000_000_000L, "T");
		suffixes.put(1_000_000_000_000_000L, "Qd");
		suffixes.put(1_000_000_000_000_000_000L, "Qn");
	}

	public static String[] getRomanNumerals() {
		return new String[] { "I", "II", "III", "IV", "V" };
	}

	public static String format(long value) {
		//Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
		if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
		if (value < 0) return "-" + format(-value);
		if (value < 1000) return Long.toString(value); //deal with easy case

		Map.Entry<Long, String> e = suffixes.floorEntry(value);
		Long divideBy = e.getKey();
		String suffix = e.getValue();

		long truncated = value / (divideBy / 10); //the number part of the output times 10
		return (truncated / 10d) + suffix;
	}

	public static ItemStack getHead(String uuid, String value, String url) {
		PlayerProfile head = Bukkit.createProfile(UUID.fromString(uuid), "skin127569846");
		head.setProperty(new ProfileProperty("textures", value, url));
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skullMeta.setPlayerProfile(head);
		skull.setItemMeta(skullMeta);
		return skull;
	}

	public static String toTime(long time) {
		StringBuilder builder = new StringBuilder();
		if(time >= 86400000) {
			long days = TimeUnit.MILLISECONDS.toDays(time);
			builder.append(days + "d ");
			time -= TimeUnit.DAYS.toMillis(days);
		}
		if(time >= 3600000) {
			long hours = TimeUnit.MILLISECONDS.toHours(time);
			builder.append(hours + "h ");
			time -= TimeUnit.HOURS.toMillis(hours);
		}
		if(time >= 60000) {
			long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
			builder.append(minutes + "m ");
			time -= TimeUnit.MINUTES.toMillis(minutes);
		}
		if(time >= 1000) {
			long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
			builder.append(seconds + "s ");
			time -= TimeUnit.SECONDS.toMillis(seconds);
		}
		String text = builder.toString();
		if(text.isEmpty())
			return "0s";
		return text;
	}
	
}
