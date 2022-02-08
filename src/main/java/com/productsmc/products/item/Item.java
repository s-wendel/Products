package com.productsmc.products.item;

import com.productsmc.products.Products;
import com.productsmc.products.forging.ForgingCategory;
import com.productsmc.products.profession.Profession;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.util.ItemBuilder;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public enum Item {

    MINER_PICK("Miner's Pick", Material.IRON_PICKAXE, Rarity.COMMON, "100X SHARDS", ForgingCategory.TOOLS, 1,ChatColor.GRAY + "Gain " + ChatColor.GREEN + "1♢ " + ChatColor.GRAY + "extra from every block mined"),
    BRISK_PICK("Brisk Pick", Material.GOLDEN_PICKAXE, Rarity.COMMON, "1000X SHARDS,25X COPPER_ORE,50X GOLD_ORE,", ForgingCategory.TOOLS, 1, ChatColor.GRAY + "Gain " + ChatColor.GREEN + "Haste I " + ChatColor.GRAY + "for every", ChatColor.GRAY + "block mined for " + ChatColor.GREEN + "4 seconds"),
    COBALT_PICK("Cobalt Pick", Material.DIAMOND_PICKAXE, Rarity.UNCOMMON, "5000X SHARDS,5X DARK_DIAMOND,50X LAPIS_ORE", ForgingCategory.TOOLS, 6,ChatColor.GRAY + "Has a " + ChatColor.GREEN + "5% Chance" + ChatColor.GRAY + " to create an aura", ChatColor.GRAY + "that regenerates blocks in a large radius", ChatColor.GRAY + "Blocks regenerated have " + ChatColor.GREEN + "+2% Dark Ore Chance"),
    ORE_UPGRADER("Ore Upgrader", Material.IRON_PICKAXE, Rarity.RARE, "15000X SHARDS,5X DARK_REDSTONE,5X DARK_EMERALD,50X EMERALD_ORE,100X REDSTONE_ORE", ForgingCategory.TOOLS, 12, ChatColor.GRAY + "Upgrades the block you mine into", ChatColor.GRAY + "the next tier and gives you the", ChatColor.GRAY + "corresponding Shards and Mats"),
    EGG_HEAVER("Egg Heaver", Material.IRON_PICKAXE, Rarity.EPIC, "25000X SHARDS,15X DARK_EMERALD,15X DARK_IRON,75X EMERALD_ORE,200X IRON_ORE,", ForgingCategory.TOOLS, 18,ChatColor.GRAY + "When " + ChatColor.GREEN + "Right-Clicked" + ChatColor.GRAY + " shoots an egg that", ChatColor.GRAY + "mines blocks in a small radius. This is", ChatColor.GRAY + "on cooldown for 0.75 seconds"),
    //DARK_SURGE("Dark Surge", Material.NETHERITE_PICKAXE, Rarity.LEGENDARY, "40000X SHARDS,2X RAINBOW_GEM,3X COLOSSAL_STONE", ForgingCategory.TOOLS, 25, "Has a " + ChatColor.GREEN + "3% Chance" + ChatColor.GRAY + "to activate", ChatColor.LIGHT_PURPLE + "1x Dark Bomb " + ChatColor.GRAY + "on the mined Block"),
    FARMER_TOOL("Farmer's Tool", Material.IRON_HOE, Rarity.COMMON, "100X SHARDS", ForgingCategory.TOOLS, 8,ChatColor.GRAY + "Gain " + ChatColor.GREEN + "1♢ " + ChatColor.GRAY + "extra from every crop mined"),
    LIGHT_HOE("Light Hoe", Material.WOODEN_HOE, Rarity.COMMON, "1000X SHARDS,200X CARROT,200X POTATO,200X BEETROOT", ForgingCategory.TOOLS, 8, ChatColor.GRAY + "Gain " + ChatColor.GREEN + "Speed I " + ChatColor.GRAY + "for every", ChatColor.GRAY + "crop mined for " + ChatColor.GREEN + "4 seconds"),
    SNACK_SICKLE("Snack Sickle", Material.WOODEN_HOE, Rarity.UNCOMMON, "2500X SHARDS,2X GOLDEN_WHEAT", ForgingCategory.TOOLS, 10, ChatColor.GRAY + "Has a " + ChatColor.GREEN + "2% Chance" + ChatColor.GRAY + " to give a", ChatColor.GRAY + "random Snack while harvesting"),
    FERTILIZER("Fertilizer", Material.DIAMOND_HOE, Rarity.UNCOMMON, "5000X SHARDS,3X GOLDEN_CARROT,3X GOLDEN_POTATO,300X CARROT,300X POTATO", ForgingCategory.TOOLS, 12, ChatColor.GRAY + "Has a " + ChatColor.GREEN + "1% Chance" + ChatColor.GRAY + " to create an aura", ChatColor.GRAY + "that regenerates crops in a huge radius"),
    //MIDAS_SCYTHE("Midas Scythe", Material.GOLDEN_HOE, Rarity.RARE, "12500X SHARDS,4X GOLDEN_WHEAT,2X GOLDEN_NETHER_WART", ForgingCategory.TOOLS, 18, ChatColor.GRAY + "Gives " + ChatColor.GREEN + "+2% Golden Crop Chance " + ChatColor.GRAY + "while harvesting"),
    MAGIC_WATER("Magic Water", Material.WATER_BUCKET, Rarity.COMMON, "500X SHARDS,2X DIAMOND_ORE,5X LAPIS_ORE", ForgingCategory.MISC, 1,ChatColor.GRAY + "Place infinite water!"),
    MAGIC_LAVA("Magic Lava", Material.LAVA_BUCKET, Rarity.COMMON, "UNFORGABLE", null, 1, ChatColor.GRAY + "Place infinite lava!"),
    GENERATOR_REMOTE("Generator Remote", Material.COMPARATOR, Rarity.UNCOMMON, "250X SHARDS,5X EMERALD_ORE,25X IRON_ORE,25X REDSTONE_ORE", ForgingCategory.MISC, 1,ChatColor.GREEN + "Shift Right-Click" + ChatColor.GRAY + " to pick up all generators"),
    BUFF_BERRY("Buff Berry", Material.SWEET_BERRIES, Rarity.RARE, "250X SHARDS,75X BEETROOT", ForgingCategory.SNACKS, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to receive " + ChatColor.GREEN + "Haste I", ChatColor.GREEN + "Speed I, & Strength I" + ChatColor.GRAY + " for 3 minutes"),
    FROSTED_CAKE("Frosted Cake", Material.CAKE, Rarity.UNCOMMON, "250X SHARDS,75X WHEAT", ForgingCategory.SNACKS, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to receive " + ChatColor.GREEN + "Health Boost I", ChatColor.GREEN + "Resistance I, & Weakness I" + ChatColor.GRAY + " for 3 minutes"),
    GOLDEN_APPLE("Golden Apple", Material.GOLDEN_APPLE, Rarity.COMMON, "100X SHARDS,1X GOLDEN_POTATO,15X GOLD_ORE", ForgingCategory.SNACKS, 1, ChatColor.GREEN + "Consume" + ChatColor.GRAY + " to gain " + ChatColor.GREEN + "Absorption & Regeneration I"),
    MINING_BOMB("Mining Bomb", Material.CLAY_BALL, Rarity.RARE, "UNFORGABLE", null, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to throw a bomb that mines", ChatColor.GRAY + "all blocks in a 6 block radius"),
    FARMING_BOMB("Farming Bomb", Material.SLIME_BALL, Rarity.RARE, "UNFORGABLE", null, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to throw a bomb that harvests", ChatColor.GRAY + "all crops in an 8 block radius"),
    //MAT_BAG("Mat Bag", Material.HOPPER, Rarity.LEGENDARY, "10000X SHARDS,3X DARK_IRON,3X GOLDEN_CARROT,5X DARK_REDSTONE,5X GOLDEN_POTATO", ForgingCategory.MISC,ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to open a mat bag"),
    //SMALL_BAG_UPGRADE("Small Bag Upgrade", Material.HOPPER, Rarity.RARE, "10000X SHARDS,5X DARK_COAL,5X GOLDEN_BEETROOT,5X DARK_IRON,5X GOLDEN_WHEAT", ForgingCategory.MISC,ChatColor.GRAY + "Sets your Mat Bag Size to Small", ChatColor.GRAY + "If you have a larger Bag Size", ChatColor.GRAY + "you will not be refunded"),
    //MEDIUM_BAG_UPGRADE("Medium Bag Upgrade", Material.HOPPER, Rarity.EPIC, "25000X SHARDS,10X DARK_COPPER,10X GOLDEN_NETHER_WART,10X DARK_GOLD", ForgingCategory.MISC,ChatColor.GRAY + "Sets your Mat Bag Size to Medium", ChatColor.GRAY + "If you have a larger Bag Size", ChatColor.GRAY + "you will not be refunded"),
    //LARGE_BAG_UPGRADE("Large Bag Upgrade", Material.HOPPER, Rarity.LEGENDARY, "50000X SHARDS,20X DARK_REDSTONE,20X GOLDEN_NETHER_WART,20X DARK_LAPIS", ForgingCategory.MISC,ChatColor.GRAY + "Sets your Mat Bag Size to Large"),
    SPRAY_BOTTLE("Spray Bottle", Material.IRON_SHOVEL, Rarity.UNCOMMON, "100X SHARDS,10X LAPIS_ORE", ForgingCategory.MISC, 8,ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to regrow crops in a 10 block radius"),
    DARK_BOMB("Dark Bomb", Material.FLINT, Rarity.EPIC, "UNFORGABLE", null, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to throw a bomb that changes", ChatColor.GRAY + "all blocks in a 3 block radius to Dark Ores"),
    DARK_HELMET("Dark Helmet", Material.CHAINMAIL_HELMET, Rarity.RARE, "2500X SHARDS,3X DARK_COAL", ForgingCategory.ARMOR, 10, ChatColor.GRAY + "While wearing the full Dark Armor", ChatColor.GRAY + "set, grants " + ChatColor.GREEN + "+3% Dark Ore Chance"),
    DARK_CHESTPLATE("Dark Chestplate", Material.CHAINMAIL_CHESTPLATE, Rarity.RARE, "2500X SHARDS,3X DARK_IRON", ForgingCategory.ARMOR, 10, ChatColor.GRAY + "While wearing the full Dark Armor", ChatColor.GRAY + "set, grants " + ChatColor.GREEN + "+3% Dark Ore Chance"),
    DARK_LEGGINGS("Dark Leggings", Material.CHAINMAIL_LEGGINGS, Rarity.RARE, "2500X SHARDS,3X DARK_COPPER", ForgingCategory.ARMOR, 10, ChatColor.GRAY + "While wearing the full Dark Armor", ChatColor.GRAY + "set, grants " + ChatColor.GREEN + "+3% Dark Ore Chance"),
    DARK_BOOTS("Dark Boots", Material.CHAINMAIL_BOOTS, Rarity.RARE, "2500X SHARDS,3X DARK_GOLD", ForgingCategory.ARMOR, 10, ChatColor.GRAY + "While wearing the full Dark Armor", ChatColor.GRAY + "set, grants " + ChatColor.GREEN + "+3% Dark Ore Chance"),
    MERCHANT_HELMET("Merchant Helmet", Material.LEATHER_HELMET, Rarity.RARE, "UNFORGABLE", null, 1, ChatColor.GRAY + "While wearing the full Merchant Armor", ChatColor.GRAY + " set, grants a " + ChatColor.GREEN + "+0.05x Sell Wand Multiplier"),
    MERCHANT_CHESTPLATE("Merchant Chestplate", Material.LEATHER_CHESTPLATE, Rarity.RARE, "UNFORGABLE", null, 1, ChatColor.GRAY + "While wearing the full Merchant Armor", ChatColor.GRAY + " set, grants a " + ChatColor.GREEN + "+0.05x Sell Wand Multiplier"),
    MERCHANT_LEGGINGS("Merchant Leggings", Material.LEATHER_LEGGINGS, Rarity.RARE, "UNFORGABLE", null, 1, ChatColor.GRAY + "While wearing the full Merchant Armor", ChatColor.GRAY + " set, grants a " + ChatColor.GREEN + "+0.05x Sell Wand Multiplier"),
    MERCHANT_BOOTS("Merchant Boots", Material.LEATHER_BOOTS, Rarity.RARE, "UNFORGABLE", null, 1, ChatColor.GRAY + "While wearing the full Merchant Armor", ChatColor.GRAY + "set, grants a " + ChatColor.GREEN + "+0.05x Sell Wand Multiplier"),
    ROGUE_CAP("Rogue Cap", Material.LEATHER_HELMET, Rarity.LEGENDARY, "1X RAINBOW_GEM", ForgingCategory.ARMOR, 25, ChatColor.GRAY + "While wearing the full Rogue Armor", ChatColor.GRAY + "set, grants a " + ChatColor.GREEN + "+5% Steal Chance", ChatColor.DARK_GRAY + "(Chance to gain double items while Trading, Crate Opening, or Forging)"),
    ROGUE_SHIRT("Rogue Shirt", Material.LEATHER_CHESTPLATE, Rarity.LEGENDARY, "1X RAINBOW_CROP", ForgingCategory.ARMOR, 25, ChatColor.GRAY + "While wearing the full Rogue Armor", ChatColor.GRAY + "set, grants a " + ChatColor.GREEN + "+5% Steal Chance", ChatColor.DARK_GRAY + "(Chance to gain double items while Trading, Crate Opening, or Forging)"),
    ROGUE_PANTS("Rogue Pants", Material.LEATHER_LEGGINGS, Rarity.LEGENDARY, "1X RAINBOW_GEM", ForgingCategory.ARMOR, 25, ChatColor.GRAY + "While wearing the full Rogue Armor", ChatColor.GRAY + "set, grants a " + ChatColor.GREEN + "+5% Steal Chance", ChatColor.DARK_GRAY + "(Chance to gain double items while Trading, Crate Opening, or Forging)"),
    ROGUE_SHOES("Rogue Shoes", Material.LEATHER_BOOTS, Rarity.LEGENDARY, "1X RAINBOW_CROP", ForgingCategory.ARMOR, 25, ChatColor.GRAY + "While wearing the full Rogue Armor", ChatColor.GRAY + " set, grants a " + ChatColor.GREEN + "+5% Steal Chance", ChatColor.DARK_GRAY + "(Chance to gain double items while Trading, Crate Opening, or Forging)"),
    INHIBITOR("Inhibitor", Material.IRON_HELMET, Rarity.UNCOMMON, "2500X SHARDS,2X DARK_IRON,35X IRON_ORE", ForgingCategory.ARMOR, 1,ChatColor.GRAY + "While wearing this, prevents Weakness"),
    BASTION("Bastion", Material.DIAMOND_CHESTPLATE, Rarity.EPIC, "1500X SHARDS,75X DIAMOND_ORE", ForgingCategory.ARMOR, 1, ChatColor.GRAY + "While wearing this, take 35% less Damage"),
    ZENITH("Zenith", Material.GOLDEN_BOOTS, Rarity.UNCOMMON, "3000X SHARDS,50X GOLD_ORE", ForgingCategory.ARMOR, 1,ChatColor.GRAY + "While wearing this, prevents Slowness"),
    STEEL_SWORD("Steel Sword", Material.IRON_SWORD, Rarity.COMMON, "250X SHARDS,15X IRON_ORE", ForgingCategory.COMBAT, 1, ChatColor.GRAY +  "Deals 15% more Damage if the", ChatColor.GRAY + "hit player is wearing armor"),
    CRYOLANCE("Cryolance", Material.IRON_SWORD, Rarity.RARE, "2500X SHARDS,1X DARK_DIAMOND,30X DIAMOND_ORE", ForgingCategory.COMBAT, 1,ChatColor.GRAY + "Gives Slowness I to enemies"),
    DETONATOR("Detonator", Material.GOLDEN_AXE, Rarity.RARE, "2500X SHARDS,1X DARK_REDSTONE,30X REDSTONE_ORE", ForgingCategory.COMBAT, 8, ChatColor.GRAY + "Has a 14% Chance to trigger an explosion", ChatColor.GRAY + "which deals 200% Damage"),
    BLOOD_BLADE("Blood Blade", Material.NETHERITE_SWORD, Rarity.EPIC, "5000X SHARDS,3X DARK_REDSTONE,40X COPPER_ORE", ForgingCategory.COMBAT, 12, ChatColor.GRAY +  "Gives Strength I & Speed I on player kills"),
    COMMON_KEY("Common Key", Material.NETHER_STAR, Rarity.COMMON, "750X SHARDS,5X COAL_ORE,15X CARROT", ForgingCategory.KEYS, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " on the "  + ChatColor.WHITE + "Common Crate", ChatColor.GRAY + "to open this key & gain rewards"),
    UNCOMMON_KEY("Uncommon Key", Material.NETHER_STAR, Rarity.UNCOMMON, "1750X SHARDS,20X IRON_ORE,1X GOLDEN_WHEAT", ForgingCategory.KEYS, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " on the "  + ChatColor.DARK_GREEN + "Uncommon Crate", ChatColor.GRAY + "to open this key & gain rewards"),
    RARE_KEY("Rare Key", Material.NETHER_STAR, Rarity.RARE, "5000X SHARDS,30X GOLD_ORE,1X DARK_GOLD,300X BEETROOT,2X GOLDEN_BEETROOT", ForgingCategory.KEYS, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " on the "  + ChatColor.DARK_AQUA + "Rare Crate", ChatColor.GRAY + "to open this key & gain rewards"),
    EPIC_KEY("Epic Key", Material.NETHER_STAR, Rarity.EPIC, "10000X SHARDS,3X GOLDEN_WHEAT,50X DIAMOND_ORE,2X DARK_DIAMOND", ForgingCategory.KEYS, 10, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " on the "  + ChatColor.AQUA + "Epic Crate", ChatColor.GRAY + "to open this key & gain rewards"),
    LEGENDARY_KEY("Legendary Key", Material.NETHER_STAR, Rarity.LEGENDARY, "15000X SHARDS,1X RAINBOW_CROP,1X RAINBOW_GEM", ForgingCategory.KEYS, 18, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " on the "  + ChatColor.LIGHT_PURPLE + "Legendary Crate", ChatColor.GRAY + "to open this key & gain rewards"),
    COMMON_LOOTBOX("Common Lootbox", Material.CHEST_MINECART, Rarity.COMMON, "UNFORGABLE", null, 1,ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to gain 4 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "2x Mining Bomb, 2x Farming Bomb", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "2x Dark Bomb", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "5x Common Key", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "3x Uncommon Key", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "2x Rare Key", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "3x Common Mat Bundle", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "2x Uncommon Mat Bundle", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "1x Merchant Armor Piece", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "1x Common Pet", "", ChatColor.GRAY + "When opened, gain " + ChatColor.GREEN + ChatColor.BOLD + "ONE" + ChatColor.GRAY + " of these rewards", "", ChatColor.DARK_GRAY + "> " + ChatColor.GOLD + "1x Amber Rank", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Flame Badge", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "0.03-0.05x Multiplier"),
    UNCOMMON_LOOTBOX("Uncommon Lootbox", Material.CHEST_MINECART, Rarity.UNCOMMON, "UNFORGABLE", null, 1,ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to gain 4 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "4x Mining Bomb, 4x Farming Bomb", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "2x Dark Bomb", ChatColor.DARK_GREEN + "> " + ChatColor.WHITE + "5x Uncommon Key", ChatColor.DARK_AQUA + "> " + ChatColor.DARK_GREEN + "4x Rare Key", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "3x Rare Mat Bundle", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "1x Merchant Armor Piece", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Gen Slot", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Uncommon Pet", "", ChatColor.GRAY + "When opened, gain " + ChatColor.GREEN + ChatColor.BOLD + "ONE" + ChatColor.GRAY + " of these rewards", "", ChatColor.DARK_GRAY + "> " + ChatColor.GOLD + "1x Amber Rank", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Flame Badge", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "0.06-0.08x Multiplier"),
    RARE_LOOTBOX("Rare Lootbox", Material.CHEST_MINECART, Rarity.RARE, "UNFORGABLE", null, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to gain 5 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "6x Mining Bomb, 6x Farming Bomb", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "3x Dark Bomb", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "5x Rare Key", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "3x Epic Key", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "2x Epic Mat Bundle", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Rogue Armor Piece", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Gen Slot", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "1x Rare Pet", "", ChatColor.GRAY + "When opened, gain " + ChatColor.GREEN + ChatColor.BOLD + "ONE" + ChatColor.GRAY + " of these rewards", "", ChatColor.DARK_GRAY + "> " + ChatColor.RED + "1x Ruby Rank", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Sword Badge", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "0.1-0.13x Multiplier"),
    EPIC_LOOTBOX("Epic Lootbox", Material.CHEST_MINECART, Rarity.EPIC, "UNFORGABLE", null, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to gain 5 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "7x Mining Bomb, 7x Farming Bomb", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "3x Dark Bomb, " + ChatColor.DARK_GREEN + "5x Spray Bottle", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "5x Epic Key", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "2x Legendary Key", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "3x Epic Mat Bundle", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Rogue Armor Piece", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "2x Gen Slot", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "1x Epic Pet", "", ChatColor.GRAY + "When opened, gain " + ChatColor.GREEN + ChatColor.BOLD + "ONE" + ChatColor.GRAY + " of these rewards", "", ChatColor.DARK_GRAY + "> " + ChatColor.RED + "1x Ruby Rank", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Sword Badge", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "0.13-0.16x Multiplier"),
    LEGENDARY_LOOTBOX("Legendary Lootbox", Material.CHEST_MINECART, Rarity.LEGENDARY, "UNFORGABLE",null, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " to gain 6 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "8x Mining Bomb, 8x Farming Bomb", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "4x Dark Bomb, " + ChatColor.DARK_GREEN + "5x Spray Bottle", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "6x Epic Key", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "3x Legendary Key", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "5x Epic Mat Bundle", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Rogue Armor Piece", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "3x Gen Slot", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Legendary Pet", "", ChatColor.GRAY + "When opened, gain " + ChatColor.GREEN + ChatColor.BOLD + "ONE" + ChatColor.GRAY + " of these rewards", "", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Opal Rank", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Star Badge", ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + "0.17-0.2x Multiplier", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Pet Slot"),
    DARK_COAL("Dark Coal", Material.DEEPSLATE_COAL_ORE, Rarity.UNCOMMON, "250X SHARDS,50X COAL_ORE", ForgingCategory.MATS, 1, ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    DARK_IRON("Dark Iron", Material.DEEPSLATE_IRON_ORE, Rarity.UNCOMMON, "250X SHARDS,50X IRON_ORE", ForgingCategory.MATS, 1, ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    DARK_COPPER("Dark Copper", Material.DEEPSLATE_COPPER_ORE, Rarity.RARE, "300X SHARDS,50X COPPER_ORE", ForgingCategory.MATS, 1, ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    DARK_GOLD("Dark Gold", Material.DEEPSLATE_GOLD_ORE, Rarity.RARE, "300X SHARDS,50X GOLD_ORE", ForgingCategory.MATS, 1, ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    DARK_REDSTONE("Dark Redstone", Material.DEEPSLATE_REDSTONE_ORE, Rarity.EPIC, "350X SHARDS,50X REDSTONE_ORE", ForgingCategory.MATS, 1, ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    DARK_LAPIS("Dark Lapis", Material.DEEPSLATE_LAPIS_ORE, Rarity.EPIC, "350X SHARDS,50X LAPIS_ORE", ForgingCategory.MATS, 1,ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    DARK_DIAMOND("Dark Diamond", Material.DEEPSLATE_DIAMOND_ORE, Rarity.LEGENDARY, "400X SHARDS,50X DIAMOND_ORE", ForgingCategory.MATS, 1,ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    DARK_EMERALD("Dark Emerald", Material.DEEPSLATE_EMERALD_ORE, Rarity.LEGENDARY, "500X SHARDS,50X EMERALD_ORE", ForgingCategory.MATS, 1, ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    RAINBOW_GEM("Rainbow Gem", Material.MAGMA_CREAM, Rarity.LEGENDARY, "1000X SHARDS,1X DARK_COAL,1X DARK_IRON,1X DARK_COPPER,1X DARK_GOLD,1X DARK_REDSTONE,1X DARK_LAPIS,1X DARK_DIAMOND,1X DARK_EMERALD", ForgingCategory.MATS, 1,ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    GOLDEN_POTATO("Golden Potato", Material.RAW_GOLD, Rarity.UNCOMMON, "250X SHARDS,500X POTATO,10X GOLD_ORE", ForgingCategory.MATS, 8,ChatColor.DARK_GRAY + "Farming Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    GOLDEN_CARROT("Golden Carrot", Material.GOLDEN_CARROT, Rarity.UNCOMMON, "250X SHARDS,400X CARROT,4X GOLD_ORE", ForgingCategory.MATS, 8, ChatColor.DARK_GRAY + "Farming Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    GOLDEN_BEETROOT("Golden Beetroot", Material.RABBIT_FOOT, Rarity.RARE, "300X SHARDS,400X BEETROOT,4X GOLD_ORE", ForgingCategory.MATS, 8, ChatColor.DARK_GRAY + "Farming Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    GOLDEN_WHEAT("Golden Wheat", Material.YELLOW_DYE, Rarity.EPIC, "400X SHARDS,400X WHEAT,4X GOLD_ORE", ForgingCategory.MATS, 8, ChatColor.DARK_GRAY + "Farming Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    GOLDEN_NETHER_WART("Golden Nether Wart", Material.GOLD_NUGGET, Rarity.LEGENDARY, "500X SHARDS,400X NETHER_WART,4X GOLD_ORE", ForgingCategory.MATS, 8,ChatColor.DARK_GRAY + "Farming Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    RAINBOW_CROP("Rainbow Crop", Material.BLAZE_POWDER, Rarity.LEGENDARY, "1000X SHARDS,1X GOLDEN_POTATO,1X GOLDEN_CARROT,1X GOLDEN_BEETROOT,1X GOLDEN_WHEAT,1X GOLDEN_NETHER_WART", ForgingCategory.MATS, 8, ChatColor.DARK_GRAY + "Farming Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    TROPHY("Trophy", Material.YELLOW_CANDLE, Rarity.LEGENDARY, "UNFORGABLE", null, 1, ChatColor.DARK_GRAY + "Global Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    PET_SLOT("Pet Slot", Material.CREEPER_HEAD, Rarity.LEGENDARY, "UNFORGABLE", null, 1, ChatColor.GRAY + "Another slot for /pets"),
    GEN_SLOT("Gen Slot", Material.COAL_BLOCK, Rarity.LEGENDARY, "UNFORGABLE", null, 1, ChatColor.GRAY + "Another slot for Gens to be placed"),
    TWO_GEN_SLOTS("2x Gen Slots", Material.COAL_BLOCK, Rarity.LEGENDARY, "UNFORGABLE", null, 1, ChatColor.GRAY + "A gen slot"),
    COLOSSAL_STONE("Colossal Stone", Material.DEEPSLATE, Rarity.EPIC, "1000X STONE", ForgingCategory.MATS, 1, ChatColor.DARK_GRAY + "Mining Material", ChatColor.GRAY + "Can be used to forge items", ChatColor.GRAY + "by the Forger using '/forge'"),
    COAL_GENERATOR("Coal Generator", Material.COAL_BLOCK, Rarity.COMMON, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Coal"),
    IRON_GENERATOR("Iron Generator", Material.IRON_BLOCK, Rarity.COMMON, "UNFORGABLE", null, 1, ChatColor.GRAY + "Drops sellable Iron"),
    COPPER_GENERATOR("Copper Generator", Material.COPPER_BLOCK, Rarity.COMMON, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Copper"),
    GOLD_GENERATOR("Gold Generator", Material.GOLD_BLOCK, Rarity.COMMON, "UNFORGABLE", null,1, ChatColor.GRAY + "Drops sellable Gold"),
    REDSTONE_GENERATOR("Redstone Generator", Material.REDSTONE_BLOCK, Rarity.UNCOMMON, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Redstone"),
    LAPIS_GENERATOR("Lapis Generator", Material.LAPIS_BLOCK, Rarity.UNCOMMON, "UNFORGABLE", null,1, ChatColor.GRAY + "Drops sellable Lapis"),
    DIAMOND_GENERATOR("Diamond Generator", Material.DIAMOND_BLOCK, Rarity.UNCOMMON, "UNFORGABLE", null,1, ChatColor.GRAY + "Drops sellable Diamond"),
    EMERALD_GENERATOR("Emerald Generator", Material.EMERALD_BLOCK, Rarity.UNCOMMON, "UNFORGABLE", null,  1,   ChatColor.GRAY + "Drops sellable Emerald"),
    NETHERITE_GENERATOR("Netherite Generator", Material.NETHERITE_BLOCK, Rarity.RARE, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Netherite"),
    OBSIDIAN_GENERATOR("Obsidian Generator", Material.OBSIDIAN, Rarity.RARE, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Obsidian"),
    GLOWSTONE_GENERATOR("Glowstone Generator", Material.GLOWSTONE, Rarity.RARE, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Glowstone"),
    MAGMA_GENERATOR("Magma Generator", Material.MAGMA_BLOCK, Rarity.RARE, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Magma Cream"),
    BLAZE_GENERATOR("Blaze Generator", Material.HONEYCOMB_BLOCK, Rarity.EPIC, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Blaze Powder"),
    ENDER_GENERATOR("Ender Generator", Material.END_STONE, Rarity.EPIC, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Ender Eyes"),
    SHULKER_GENERATOR("Shulker Generator", Material.PURPUR_BLOCK, Rarity.EPIC, "UNFORGABLE", null, 1,ChatColor.GRAY + "Drops sellable Shulker Shells"),
    COMMON_PET("Common Pet", Material.TOTEM_OF_UNDYING, Rarity.COMMON, "UNFORGABLE", null, 1,ChatColor.GRAY + "A random Common pet"),
    UNCOMMON_PET("Uncommon Pet", Material.TOTEM_OF_UNDYING, Rarity.UNCOMMON, "UNFORGABLE", null,1, ChatColor.GRAY + "A random Uncommon pet"),
    RARE_PET("Rare Pet", Material.TOTEM_OF_UNDYING, Rarity.RARE, "UNFORGABLE", null, 1,ChatColor.GRAY + "A random Rare pet"),
    EPIC_PET("Epic Pet", Material.TOTEM_OF_UNDYING, Rarity.EPIC, "UNFORGABLE", null, 1,ChatColor.GRAY + "A random Epic pet"),
    LEGENDARY_PET("Legendary Pet", Material.TOTEM_OF_UNDYING, Rarity.LEGENDARY, "UNFORGABLE", null, 1,ChatColor.GRAY + "A random Legendary pet"),
    COMMON_MAT_BUNDLE("Mat Bundle", Material.HOPPER, Rarity.COMMON, "UNFORGABLE", null, 1,ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "to gain 3 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "25x Coal Ore", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "25x Iron Ore", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "100x Carrot", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "100x Potato", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Dark Coal", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Dark Iron", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Golden Carrot", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Golden Potato"),
    UNCOMMON_MAT_BUNDLE("Mat Bundle", Material.HOPPER, Rarity.UNCOMMON, "UNFORGABLE", null, 1,ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "to gain 3 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "25x Copper Ore", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "25x Gold Ore", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "100x Beetroot", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Dark Copper", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Dark Gold", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Golden Beetroot"),
    RARE_MAT_BUNDLE("Mat Bundle", Material.HOPPER, Rarity.RARE, "UNFORGABLE", null, 1,ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "to gain 3 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "25x Redstone Ore", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "25x Lapis Ore", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "100x Wheat", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Dark Redstone", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Dark Lapis", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Golden Wheat"),
    EPIC_MAT_BUNDLE("Mat Bundle", Material.HOPPER, Rarity.EPIC, "UNFORGABLE", null, 1, ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "to gain 3 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "25x Diamond Ore", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "25x Emerald Ore", ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "100x Nether Wart", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Dark Diamond", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Dark Emerald", ChatColor.DARK_GRAY + "> " + ChatColor.DARK_GREEN + "1x Golden Nether Wart"),
    LEGENDARY_MAT_BUNDLE("Mat Bundle", Material.HOPPER, Rarity.LEGENDARY, "UNFORGABLE", null, 1, ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "to gain 3 of these rewards:", "", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Rainbow Crop", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Rainbow Gem", ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Trophy"),
    COMMON_SELL_WAND("1x Sell Wand", Material.BLAZE_ROD, Rarity.COMMON, "1000X SHARDS",  ForgingCategory.TOOLS, 1, ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "a chest to sell the contents for 1x their sell value"),
    UNCOMMON_SELL_WAND("1.5x Sell Wand", Material.BLAZE_ROD, Rarity.UNCOMMON, "10000X SHARDS,5X DARK_EMERALD,5X DARK_DIAMOND,5X GOLDEN_WHEAT,5X GOLDEN_NETHER_WART", ForgingCategory.TOOLS, 10, ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "a chest to sell the contents for 1.5x their sell value"),
    RARE_SELL_WAND("2x Sell Wand", Material.BLAZE_ROD, Rarity.RARE, "50000X SHARDS,10X DARK_EMERALD,10X DARK_DIAMOND,200X EMERALD_ORE,200X DIAMOND_ORE,10X GOLDEN_WHEAT,10X GOLDEN_NETHER_WART", ForgingCategory.TOOLS, 25, ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "a chest to sell the contents for 2x their sell value"),
    EPIC_SELL_WAND("2.5x Sell Wand", Material.BLAZE_ROD, Rarity.EPIC, "UNFORGABLE", null, 1, ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "a chest to sell the contents for 2.5x their sell value"),
    LEGENDARY_SELL_WAND("3x Sell Wand", Material.BLAZE_ROD, Rarity.LEGENDARY, "UNFORGABLE", null, 1, ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "a chest to sell the contents for 3x their sell value"),
    SMALL_MULTIPLIER("0.15x Multiplier", Material.BLAZE_ROD, Rarity.EPIC, "UNFORGABLE", null, 1, ChatColor.GRAY + "Gives you a " + ChatColor.GREEN + "0.15x Multiplier"),
    TROPHY_LEGENDARY_KEY("Legendary Key", Material.NETHER_STAR, Rarity.LEGENDARY, "2X TROPHY", ForgingCategory.TROPHY, 1, ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + " on the "  + ChatColor.LIGHT_PURPLE + "Legendary Crate", ChatColor.GRAY + "to open this key & gain rewards"),
    TROPHY_PARROT("Parrot Pet", Material.NOTE_BLOCK, Rarity.EPIC, "3X TROPHY", ForgingCategory.TROPHY, 1, ChatColor.GRAY + "Gives you a " + ChatColor.GREEN + "Parrot Pet (/pets)"),
    TROPHY_SMALL_MULTIPLIER("0.2x Multiplier", Material.BLAZE_POWDER, Rarity.EPIC, "3X TROPHY", ForgingCategory.TROPHY, 1, ChatColor.GRAY + "Gives you a " + ChatColor.GREEN + "0.2x Multiplier"),
    TROPHY_SELL_WAND("2.5x Sell Wand", Material.BLAZE_ROD, Rarity.EPIC, "7X TROPHY", ForgingCategory.TROPHY, 1, ChatColor.GREEN + "Right-Click " + ChatColor.GRAY + "a chest to sell the contents for 2.5x their sell value"),
    TROPHY_PET_SLOT("Pet Slot", Material.CREEPER_HEAD, Rarity.LEGENDARY, "3X TROPHY", ForgingCategory.TROPHY, 1,ChatColor.GRAY + "Gives you a " + ChatColor.GREEN + "Pet Slot")
    ;

    private String name;
    private Material material;
    private Rarity rarity;
    private String mats;
    private ForgingCategory category;
    private String[] description;
    private int unlock;

    Item(String name, Material material, Rarity rarity, String mats, ForgingCategory category, int unlock, String... description) {
        this.name = name;
        this.material = material;
        this.rarity = rarity;
        this.mats = mats;
        this.category = category;
        this.unlock = unlock;
        this.description = description;
    }

    public static List<Item> getUnlocks(int level) {
        List<Item> items = new ArrayList<>();
        for(Item item : Item.values()) {
            if(item.getUnlock() == level) {
                items.add(item);
            }
        }
        return items;
    }

    public String getRawMats() {
        return mats;
    }

    public Map<Mat, Integer> getMats() {
        String[] mat = mats.split(",");
        Map<Mat, Integer> mats = new HashMap<>();
        for(String string : mat) {
            String[] split = string.split("X ");
            mats.put(Mat.valueOf(split[1]), Integer.parseInt(split[0]));
        }
        return mats;
    }

    public int getUnlock() {
        return unlock;
    }

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return material;
    }

    public String[] getDescription() {
        return description;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public ForgingCategory getCategory() {
        return category;
    }

    public static Item of(String name) {
        for(Item type : Item.values()) {
            if(type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public ItemStack getItem() {
        return new ItemBuilder(material)
                .setName(rarity.getColor() + name)
                .setLore(description)
                .setLore("",
                        rarity.getLore())
                .setUnbreakable(true)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .toItemStack();
    }

    public void ability(Player player, Location location) {
        Random random = new Random();
        int number = random.nextInt(101);

        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();

        int regrown = 0;
        DailyQuest daily = DailyQuest.REGROWTH;
        Map<DailyQuest, Integer> dailies = user.getDailies();
        int progress = -1;

        switch(this) {
            case SNACK_SICKLE:
                if(number <= 1) {

                    daily = DailyQuest.HOE_ABILITIES;

                    if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

                        progress = dailies.get(daily);

                        if(progress + 1 >= daily.getAmount(config.getInt("daily.level"))) {

                            daily.finishQuest(player);
                            dailies.put(daily, -1);
                            DailyQuest.checkCompletedDailies(player);

                        } else {
                            dailies.put(daily, progress + 1);
                        }

                    }

                    if(number == 0) {
                        player.getInventory().addItem(Item.FROSTED_CAKE.getItem());
                    } else {
                        player.getInventory().addItem(Item.BUFF_BERRY.getItem());
                    }
                }
                break;
            case LIGHT_HOE:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0, true, false, true));
                break;
            case BRISK_PICK:
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 0, true, false, true));
                break;
            case FERTILIZER:

                if(number <= 1) {

                    for (double i = 0; i <= Math.PI; i += Math.PI / 15) {
                        double radius = Math.sin(i) * 6;
                        double y = Math.cos(i) * 6;
                        for (double a = 0; a < Math.PI * 2; a+= Math.PI / 15) {
                            double x = Math.cos(a) * radius;
                            double z = Math.sin(a) * radius;
                            location.add(x, y, z);
                            location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(0 + random.nextInt(16), 240 + random.nextInt(16), 0 + random.nextInt(16)), 120 + new Random().nextInt(100)));
                            location.subtract(x, y, z);
                        }
                    }

                    int bx = location.getBlockX();
                    int by = location.getBlockY();
                    int bz = location.getBlockZ();

                    for(int x = bx - 5; x <= bx + 5; x++) {
                        for(int y = by - 5; y <= by + 5; y++) {
                            for(int z = bz - 5; z <= bz + 5; z++) {
                                double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                                if(distance < 25) {
                                    Location check = new Location(location.getWorld(), x, y, z);
                                    if(check.getBlock().getBlockData() instanceof Ageable) {
                                        Ageable crop = (Ageable) check.getBlock().getBlockData();

                                        if(crop.getAge() < crop.getMaximumAge()) {
                                            regrown++;
                                        }

                                        crop.setAge(crop.getMaximumAge());
                                        check.getBlock().setBlockData(crop);

                                    }
                                }
                            }
                        }
                    }
                    location.getWorld().playSound(location, Sound.ENTITY_IRON_GOLEM_DEATH, 1, 2);

                    if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

                        progress = dailies.get(daily);

                        if(progress + regrown >= daily.getAmount(config.getInt("daily.level"))) {

                            daily.finishQuest(player);
                            dailies.put(daily, -1);
                            DailyQuest.checkCompletedDailies(player);

                        } else {
                            dailies.put(daily, progress + regrown);
                        }

                    }

                    daily = DailyQuest.HOE_ABILITIES;

                    if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

                        progress = dailies.get(daily);

                        if(progress + 1 >= daily.getAmount(config.getInt("daily.level"))) {

                            daily.finishQuest(player);
                            dailies.put(daily, -1);
                            DailyQuest.checkCompletedDailies(player);

                        } else {
                            dailies.put(daily, progress + 1);
                        }

                    }

                }

                break;
            case COBALT_PICK:
                if(number <= 5) {

                    for (double i = 0; i <= Math.PI; i += Math.PI / 15) {
                        double radius = Math.sin(i) * 5;
                        double y = Math.cos(i) * 5;
                        for (double a = 0; a < Math.PI * 2; a+= Math.PI / 15) {
                            double x = Math.cos(a) * radius;
                            double z = Math.sin(a) * radius;
                            location.add(x, y, z);
                            location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(40 + new Random().nextInt(70), 120 + new Random().nextInt(40), 255), 120 + new Random().nextInt(100)));
                            location.subtract(x, y, z);
                        }
                    }

                    int bx = location.getBlockX();
                    int by = location.getBlockY();
                    int bz = location.getBlockZ();

                    for(int x = bx - 5; x <= bx + 5; x++) {
                        for(int y = by - 5; y <= by + 5; y++) {
                            for(int z = bz - 5; z <= bz + 5; z++) {
                                double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                                if(distance < 25) {
                                    /*Location check = new Location(location.getWorld(), x, y, z);
                                    List<Location> remove = new ArrayList<>();
                                    for(Location ore : blocks.keySet()) {
                                        if(check.equals(ore)) {

                                            if(dailies.containsKey(daily)) {

                                                progress = dailies.get(daily);

                                                if(progress + 1 == daily.getAmount(config.getInt("daily.level"))) {

                                                    daily.finishQuest(player);
                                                    DailyQuest.checkCompletedDailies(player);

                                                }

                                                dailies.put(daily, progress + 1);

                                            }

                                            Block block = ore.getBlock();
                                            int darkChance = 3;
                                            if(ThreadLocalRandom.current().nextInt(100) <= darkChance) {
                                                switch(blocks.get(ore)) {
                                                    case COAL_ORE:
                                                        regrown++;
                                                        block.setType(Material.DEEPSLATE_COAL_ORE);
                                                        break;
                                                    case IRON_ORE:
                                                        regrown++;
                                                        block.setType(Material.DEEPSLATE_IRON_ORE);
                                                        break;
                                                    case COPPER_ORE:
                                                        regrown++;
                                                        block.setType(Material.DEEPSLATE_COPPER_ORE);
                                                        break;
                                                    case GOLD_ORE:
                                                        regrown++;
                                                        block.setType(Material.DEEPSLATE_GOLD_ORE);
                                                        break;
                                                    case REDSTONE_ORE:
                                                        regrown++;
                                                        block.setType(Material.DEEPSLATE_REDSTONE_ORE);
                                                        break;
                                                    case LAPIS_ORE:
                                                        regrown++;
                                                        block.setType(Material.DEEPSLATE_LAPIS_ORE);
                                                        break;
                                                    case DIAMOND_ORE:
                                                        regrown++;
                                                        block.setType(Material.DEEPSLATE_DIAMOND_ORE);
                                                        break;
                                                    case EMERALD_ORE:
                                                        regrown++;
                                                        block.setType(Material.DEEPSLATE_EMERALD_ORE);
                                                        break;
                                                    default:
                                                        regrown++;
                                                        ore.getBlock().setType(blocks.get(ore));
                                                        break;
                                                }
                                            } else
                                                ore.getBlock().setType(blocks.get(ore));
                                            remove.add(ore);
                                        }
                                    }
                                    for(Location removed : remove)
                                        blocks.remove(removed);*/
                                }
                            }
                        }
                    }
                    location.getWorld().playSound(location, Sound.ENTITY_IRON_GOLEM_DEATH, 1, 2);

                    if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

                        progress = dailies.get(daily);

                        if(progress + regrown >= daily.getAmount(config.getInt("daily.level"))) {

                            daily.finishQuest(player);
                            dailies.put(daily, -1);
                            DailyQuest.checkCompletedDailies(player);

                        } else {
                            dailies.put(daily, progress + regrown);
                        }

                    }

                    daily = DailyQuest.PICK_ABILITIES;

                    if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

                        progress = dailies.get(daily);

                        if(progress + 1 >= daily.getAmount(config.getInt("daily.level"))) {

                            daily.finishQuest(player);
                            dailies.put(daily, -1);
                            DailyQuest.checkCompletedDailies(player);

                        } else {
                            dailies.put(daily, progress + 1);
                        }

                    }

                }


                break;
            default:
                break;
        }
    }

}
