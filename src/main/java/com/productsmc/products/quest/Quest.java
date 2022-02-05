package com.productsmc.products.quest;

import com.productsmc.products.Products;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Mat;
import com.productsmc.products.level.Level;
import com.productsmc.products.user.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public enum Quest {

    TANGLE_CAVERNS_I("Tangle Caverns I", QuestCategory.MINING, Material.VINE, 1, 200, Item.COMMON_KEY, "Discover Tangle Caverns"),
    COAL_MINER_I("Coal Miner I", QuestCategory.MINING, Material.COAL_ORE, 10, 25, Item.DARK_COAL, "Mine Coal Ore"),
    COAL_MINER_II("Coal Miner II", QuestCategory.MINING, Material.COAL_ORE, 25, 50, Item.COMMON_KEY,"Mine Coal Ore"),
    COAL_MINER_III("Coal Miner III", QuestCategory.MINING, Material.COAL_ORE, 100, 150, Item.DARK_BOMB ,"Mine Coal Ore"),
    COAL_MINER_IV("Coal Miner IV", QuestCategory.MINING, Material.COAL_ORE, 250, 500, Item.DARK_HELMET,"Mine Coal Ore"),
    COAL_MINER_V("Coal Miner V", QuestCategory.MINING, Material.COAL_ORE, 1000, 1000, Item.RARE_KEY, "Mine Coal Ore"),
    IRON_MINER_I("Iron Miner I", QuestCategory.MINING, Material.IRON_ORE, 10, 25, Item.DARK_IRON, "Mine Iron Ore"),
    IRON_MINER_II("Iron Miner II", QuestCategory.MINING, Material.IRON_ORE, 25, 50, Item.COMMON_KEY,"Mine Iron Ore"),
    IRON_MINER_III("Iron Miner III", QuestCategory.MINING, Material.IRON_ORE, 100, 150, Item.DARK_BOMB ,"Mine Iron Ore"),
    IRON_MINER_IV("Iron Miner IV", QuestCategory.MINING, Material.IRON_ORE, 250, 500, Item.DARK_CHESTPLATE,"Mine Iron Ore"),
    IRON_MINER_V("Iron Miner V", QuestCategory.MINING, Material.IRON_ORE, 1000, 1000, Item.RARE_KEY, "Mine Iron Ore"),
    COPPER_MINER_I("Copper Miner I", QuestCategory.MINING, Material.COPPER_ORE, 10, 25, Item.DARK_COPPER, "Mine Copper Ore"),
    COPPER_MINER_II("Copper Miner II", QuestCategory.MINING, Material.COPPER_ORE, 25, 50, Item.COMMON_KEY,"Mine Copper Ore"),
    COPPER_MINER_III("Copper Miner III", QuestCategory.MINING, Material.COPPER_ORE, 100, 150, Item.UNCOMMON_KEY ,"Mine Copper Ore"),
    COPPER_MINER_IV("Copper Miner IV", QuestCategory.MINING, Material.COPPER_ORE, 250, 500, Item.DARK_LEGGINGS,"Mine Copper Ore"),
    COPPER_MINER_V("Copper Miner V", QuestCategory.MINING, Material.COPPER_ORE, 1000, 1000, Item.RARE_KEY, "Mine Copper Ore"),
    GOLD_MINER_I("Gold Miner I", QuestCategory.MINING, Material.GOLD_ORE, 10, 25, Item.DARK_GOLD, "Mine Gold Ore"),
    GOLD_MINER_II("Gold Miner II", QuestCategory.MINING, Material.GOLD_ORE, 25, 50, Item.BRISK_PICK,"Mine Gold Ore"),
    GOLD_MINER_III("Gold Miner III", QuestCategory.MINING, Material.GOLD_ORE, 100, 150, Item.UNCOMMON_KEY ,"Mine Gold Ore"),
    GOLD_MINER_IV("Gold Miner IV", QuestCategory.MINING, Material.GOLD_ORE, 250, 500, Item.DARK_BOOTS,"Mine Gold Ore"),
    GOLD_MINER_V("Gold Miner V", QuestCategory.MINING, Material.GOLD_ORE, 1000, 1000, Item.RARE_KEY, "Mine Gold Ore"),
    REDSTONE_MINER_I("Redstone Miner I", QuestCategory.MINING, Material.REDSTONE_ORE, 10, 25, Item.DARK_REDSTONE, "Mine Redstone Ore"),
    REDSTONE_MINER_II("Redstone Miner II", QuestCategory.MINING, Material.REDSTONE_ORE, 25, 50, Item.COMMON_KEY,"Mine Redstone Ore"),
    REDSTONE_MINER_III("Redstone Miner III", QuestCategory.MINING, Material.REDSTONE_ORE, 100, 150, Item.UNCOMMON_KEY ,"Mine Redstone Ore"),
    REDSTONE_MINER_IV("Redstone Miner IV", QuestCategory.MINING, Material.REDSTONE_ORE, 250, 500, Item.ORE_UPGRADER,"Mine Redstone Ore"),
    REDSTONE_MINER_V("Redstone Miner V", QuestCategory.MINING, Material.REDSTONE_ORE, 1000, 1000, Item.RARE_KEY, "Mine Redstone Ore"),
    LAPIS_MINER_I("Lapis Miner I", QuestCategory.MINING, Material.LAPIS_ORE, 10, 25, Item.DARK_LAPIS, "Mine Lapis Ore"),
    LAPIS_MINER_II("Lapis Miner II", QuestCategory.MINING, Material.LAPIS_ORE, 25, 50, Item.COMMON_KEY,"Mine Lapis Ore"),
    LAPIS_MINER_III("Lapis Miner III", QuestCategory.MINING, Material.LAPIS_ORE, 100, 150, Item.UNCOMMON_KEY ,"Mine Lapis Ore"),
    LAPIS_MINER_IV("Lapis Miner IV", QuestCategory.MINING, Material.LAPIS_ORE, 250, 500, Item.ORE_UPGRADER,"Mine Lapis Ore"),
    LAPIS_MINER_V("Lapis Miner V", QuestCategory.MINING, Material.LAPIS_ORE, 1000, 1000, Item.RARE_KEY, "Mine Lapis Ore"),
    DIAMOND_MINER_I("Diamond Miner I", QuestCategory.MINING, Material.DIAMOND_ORE, 10, 25, Item.DARK_DIAMOND, "Mine Diamond Ore"),
    DIAMOND_MINER_II("Diamond Miner II", QuestCategory.MINING, Material.DIAMOND_ORE, 25, 50, Item.MINING_BOMB,"Mine Diamond Ore"),
    DIAMOND_MINER_III("Diamond Miner III", QuestCategory.MINING, Material.DIAMOND_ORE, 100, 150, Item.DARK_BOMB,"Mine Diamond Ore"),
    DIAMOND_MINER_IV("Diamond Miner IV", QuestCategory.MINING, Material.DIAMOND_ORE, 250, 500, Item.EPIC_KEY,"Mine Diamond Ore"),
    DIAMOND_MINER_V("Diamond Miner V", QuestCategory.MINING, Material.DIAMOND_ORE, 1000, 1000, Item.LEGENDARY_KEY, "Mine Diamond Ore"),
    EMERALD_MINER_I("Emerald Miner I", QuestCategory.MINING, Material.EMERALD_ORE, 10, 25, Item.DARK_EMERALD, "Mine Emerald Ore"),
    EMERALD_MINER_II("Emerald Miner II", QuestCategory.MINING, Material.EMERALD_ORE, 25, 50, Item.MINING_BOMB,"Mine Emerald Ore"),
    EMERALD_MINER_III("Emerald Miner III", QuestCategory.MINING, Material.EMERALD_ORE, 100, 150, Item.DARK_BOMB,"Mine Emerald Ore"),
    EMERALD_MINER_IV("Emerald Miner IV", QuestCategory.MINING, Material.EMERALD_ORE, 250, 500, Item.EPIC_KEY,"Mine Emerald Ore"),
    EMERALD_MINER_V("Emerald Miner V", QuestCategory.MINING, Material.EMERALD_ORE, 1000, 1000, Item.LEGENDARY_KEY, "Mine Emerald Ore"),
    DARK_ORES_I("Dark Ores I", QuestCategory.MINING, Material.DEEPSLATE_COAL_ORE, 15, 150, Item.COMMON_KEY, "Mine Dark Ores"),
    DARK_ORES_II("Dark Ores II", QuestCategory.MINING, Material.DEEPSLATE_IRON_ORE, 30, 300, Item.UNCOMMON_MAT_BUNDLE, "Mine Dark Ores"),
    DARK_ORES_III("Dark Ores III", QuestCategory.MINING, Material.DEEPSLATE_COPPER_ORE, 60, 600, Item.RARE_KEY, "Mine Dark Ores"),
    DARK_ORES_IV("Dark Ores IV", QuestCategory.MINING, Material.DEEPSLATE_GOLD_ORE, 120, 1200, Item.RARE_MAT_BUNDLE, "Mine Dark Ores"),
    DARK_ORES_V("Dark Ores V", QuestCategory.MINING, Material.DEEPSLATE_REDSTONE_ORE, 240, 2400, Item.EPIC_KEY, "Mine Dark Ores"),
    HUNTER_I("Hunter I", QuestCategory.MINING, Material.WOODEN_SWORD, 5, 75, Item.COMMON_KEY, "Kill Players"),
    HUNTER_II("Hunter II", QuestCategory.MINING, Material.STONE_SWORD, 15, 150, Item.COMMON_KEY, "Kill Players"),
    HUNTER_III("Hunter III", QuestCategory.MINING, Material.IRON_SWORD, 35, 300, Item.UNCOMMON_KEY, "Kill Players"),
    HUNTER_IV("Hunter IV", QuestCategory.MINING, Material.GOLDEN_SWORD, 50, 500, Item.MINING_BOMB, "Kill Players"),
    HUNTER_V("Hunter V", QuestCategory.MINING, Material.DIAMOND_SWORD, 100, 1000, Item.DARK_BOMB, "Kill Players"),
    GENOISSEUR_I("Genoisseur I", QuestCategory.GENS, Material.COAL_BLOCK, 25, 125, Item.COMMON_KEY, "Upgrade Generators"),
    GENOISSEUR_II("Genoisseur II", QuestCategory.GENS, Material.IRON_BLOCK, 75, 275, Item.COMMON_MAT_BUNDLE, "Upgrade Generators"),
    GENOISSEUR_III("Genoisseur III", QuestCategory.GENS, Material.COPPER_BLOCK, 250, 400, Item.UNCOMMON_KEY, "Upgrade Generators"),
    GENOISSEUR_IV("Genoisseur IV", QuestCategory.GENS, Material.GOLD_BLOCK, 750, 700, Item.RARE_KEY, "Upgrade Generators"),
    GENOISSEUR_V("Genoisseur V", QuestCategory.GENS, Material.REDSTONE_BLOCK, 2500, 1250, Item.EPIC_KEY, "Upgrade Generators"),
    FLOATING_GENS_I("Floating Gens I", QuestCategory.GENS, Material.PURPUR_BLOCK, 5, 100, Item.COMMON_KEY, "Upgrade to Shulker Generators"),
    FLOATING_GENS_II("Floating Gens II", QuestCategory.GENS, Material.PURPUR_BLOCK, 15, 250, Item.UNCOMMON_KEY, "Upgrade to Shulker Generators"),
    FLOATING_GENS_III("Floating Gens III", QuestCategory.GENS, Material.PURPUR_BLOCK, 35, 500, Item.RARE_MAT_BUNDLE, "Upgrade to Shulker Generators"),
    FLOATING_GENS_IV("Floating Gens IV", QuestCategory.GENS, Material.PURPUR_BLOCK, 75, 1000, Item.EPIC_KEY, "Upgrade to Shulker Generators"),
    FLOATING_GENS_V("Floating Gens V", QuestCategory.GENS, Material.PURPUR_BLOCK, 200, 1500, Item.GEN_SLOT, "Upgrade to Shulker Generators"),
    MERCHANT_I("Merchant I", QuestCategory.GENS, Material.GOLD_INGOT, 100, 100, Item.COMMON_KEY, "Sell Items"),
    MERCHANT_II("Merchant II", QuestCategory.GENS, Material.GOLD_INGOT, 500, 200, Item.DARK_REDSTONE, "Sell Items"),
    MERCHANT_III("Merchant III", QuestCategory.GENS, Material.GOLD_INGOT, 2500, 400, Item.UNCOMMON_MAT_BUNDLE, "Sell Items"),
    MERCHANT_IV("Merchant IV", QuestCategory.GENS, Material.GOLD_INGOT, 10000, 1000, Item.MERCHANT_HELMET, "Sell Items"),
    MERCHANT_V("Merchant V", QuestCategory.GENS, Material.GOLD_INGOT, 100000, 2000, Item.EPIC_KEY, "Sell Items"),
    CRATE_OPENER_I("Crate Opener I", QuestCategory.CRATES, Material.NETHER_STAR, 5, 125, Item.SPRAY_BOTTLE, "Open Crates"),
    CRATE_OPENER_II("Crate Opener II", QuestCategory.CRATES, Material.NETHER_STAR, 25, 250, Item.DARK_BOMB, "Open Crates"),
    CRATE_OPENER_III("Crate Opener III", QuestCategory.CRATES, Material.NETHER_STAR, 100, 500, Item.GEN_SLOT, "Open Crates"),
    CRATE_OPENER_IV("Crate Opener IV", QuestCategory.CRATES, Material.NETHER_STAR, 500, 2500, Item.EPIC_KEY, "Open Crates"),
    CRATE_OPENER_V("Crate Opener V", QuestCategory.CRATES, Material.NETHER_STAR, 1000, 5000, Item.LEGENDARY_MAT_BUNDLE, "Open Crates"),
    LEGENDARY_ITEM_I("Legendary Item I", QuestCategory.CRATES, Material.CHEST_MINECART, 3, 100, Item.RARE_KEY, "Open Legendary Items from Crates"),
    LEGENDARY_ITEM_II("Legendary Item II", QuestCategory.CRATES, Material.CHEST_MINECART, 5, 500, Item.EPIC_KEY, "Open Legendary Items from Crates"),
    LEGENDARY_ITEM_III("Legendary Item III", QuestCategory.CRATES, Material.CHEST_MINECART, 8, 1000, Item.GEN_SLOT, "Open Legendary Items from Crates"),
    LEGENDARY_ITEM_IV("Legendary Item IV", QuestCategory.CRATES, Material.CHEST_MINECART, 15, 1500, Item.LEGENDARY_KEY, "Open Legendary Items from Crates"),
    LEGENDARY_ITEM_V("Legendary Item V", QuestCategory.CRATES, Material.CHEST_MINECART, 20, 3000, Item.UNCOMMON_LOOTBOX, "Open Legendary Items from Crates"),
    THIEF_I("Thief I", QuestCategory.CRATES, Material.SHEARS, 2, 150, Item.RARE_KEY, "Steal Items using Steal Chance"),
    THIEF_II("Thief II", QuestCategory.CRATES, Material.SHEARS, 4, 350, Item.RARE_MAT_BUNDLE, "Steal Items using Steal Chance"),
    THIEF_III("Thief III", QuestCategory.CRATES, Material.SHEARS, 12, 750, Item.EPIC_KEY, "Steal Items using Steal Chance"),
    THIEF_IV("Thief IV", QuestCategory.CRATES, Material.SHEARS, 36, 1500, Item.RAINBOW_GEM, "Steal Items using Steal Chance"),
    THIEF_V("Thief V", QuestCategory.CRATES, Material.SHEARS, 108, 5000, Item.RAINBOW_CROP, "Steal Items using Steal Chance"),
    ITEM_FORGER_I("Item Forger I", QuestCategory.FORGING, Material.ANVIL, 5, 25, Item.COMMON_KEY, "Forge Items"),
    ITEM_FORGER_II("Item Forger II", QuestCategory.FORGING, Material.ANVIL, 20, 50, Item.COMMON_MAT_BUNDLE, "Forge Items"),
    ITEM_FORGER_III("Item Forger III", QuestCategory.FORGING, Material.ANVIL, 75, 150, Item.UNCOMMON_KEY, "Forge Items"),
    ITEM_FORGER_IV("Item Forger IV", QuestCategory.FORGING, Material.ANVIL, 150, 400, Item.RARE_KEY, "Forge Items"),
    ITEM_FORGER_V("Item Forger V", QuestCategory.FORGING, Material.ANVIL, 500, 1250, Item.GEN_SLOT, "Forge Items"),
    LEGENDARY_FORGING_I("Legendary Forging I", QuestCategory.FORGING, Material.HOPPER, 3, 100, Item.RARE_KEY, "Forge Legendary Items"),
    LEGENDARY_FORGING_II("Legendary Forging II", QuestCategory.FORGING, Material.HOPPER, 6, 200, Item.GEN_SLOT, "Forge Legendary Items"),
    LEGENDARY_FORGING_III("Legendary Forging III", QuestCategory.FORGING, Material.HOPPER, 12, 400, Item.RARE_MAT_BUNDLE, "Forge Legendary Items"),
    LEGENDARY_FORGING_IV("Legendary Forging IV", QuestCategory.FORGING, Material.HOPPER, 24, 800, Item.GEN_SLOT, "Forge Legendary Items"),
    LEGENDARY_FORGING_V("Legendary Forging V", QuestCategory.FORGING, Material.HOPPER, 48, 1600, Item.PET_SLOT, "Forge Legendary Items"),
    MATERIAL_MAKING_I("Material Making I", QuestCategory.FORGING, Material.RAW_GOLD, 10, 50, Item.COMMON_KEY, "Forge Mats"),
    MATERIAL_MAKING_II("Material Making II", QuestCategory.FORGING, Material.RAW_GOLD, 25, 125, Item.UNCOMMON_KEY, "Forge Mats"),
    MATERIAL_MAKING_III("Material Making III", QuestCategory.FORGING, Material.RAW_GOLD, 50, 225, Item.RARE_KEY, "Forge Mats"),
    MATERIAL_MAKING_IV("Material Making IV", QuestCategory.FORGING, Material.RAW_GOLD, 100, 400, Item.EPIC_MAT_BUNDLE, "Forge Mats"),
    MATERIAL_MAKING_V("Material Making V", QuestCategory.FORGING, Material.RAW_GOLD, 200, 800, Item.EPIC_KEY, "Forge Mats"),
    MAGIC_WAND_I("Magic Wand I", QuestCategory.FORGING, Material.BLAZE_ROD, 1, 500, Item.GEN_SLOT, "Forge a Sell Wand"),
    BIG_HAUL_I("Big Haul I", QuestCategory.FORGING, Material.RAW_COPPER, 5, 25, Item.COMMON_MAT_BUNDLE, "Scrap Items"),
    BIG_HAUL_II("Big Haul II", QuestCategory.FORGING, Material.RAW_COPPER, 20, 100, Item.UNCOMMON_MAT_BUNDLE, "Scrap Items"),
    BIG_HAUL_III("Big Haul III", QuestCategory.FORGING, Material.RAW_COPPER, 65, 500, Item.RARE_MAT_BUNDLE, "Scrap Items"),
    BIG_HAUL_IV("Big Haul IV", QuestCategory.FORGING, Material.RAW_COPPER, 125, 1250, Item.EPIC_MAT_BUNDLE, "Scrap Items"),
    BIG_HAUL_V("Big Haul V", QuestCategory.FORGING, Material.RAW_COPPER, 200, 3000, Item.GEN_SLOT, "Scrap Items"),
    INVALUABLE_WASTE_I("Invaluable Waste I", QuestCategory.FORGING, Material.RAW_IRON_BLOCK, 3, 250, Item.COMMON_KEY, "Scrap Rare+ Items"),
    INVALUABLE_WASTE_II("Invaluable Waste II", QuestCategory.FORGING, Material.RAW_IRON_BLOCK, 9, 500, Item.UNCOMMON_KEY, "Scrap Rare+ Items"),
    INVALUABLE_WASTE_III("Invaluable Waste III", QuestCategory.FORGING, Material.RAW_IRON_BLOCK, 18, 1000, Item.UNCOMMON_MAT_BUNDLE, "Scrap Rare+ Items"),
    INVALUABLE_WASTE_IV("Invaluable Waste IV", QuestCategory.FORGING, Material.RAW_IRON_BLOCK, 36, 2000, Item.GEN_SLOT, "Scrap Rare+ Items"),
    INVALUABLE_WASTE_V("Invaluable Waste V", QuestCategory.FORGING, Material.RAW_IRON_BLOCK, 72, 4000, Item.GEN_SLOT, "Scrap Rare+ Items"),
    STOMACH_THAT_I("Stomach That I", QuestCategory.FORGING, Material.GLOW_BERRIES, 3, 50, Item.COMMON_MAT_BUNDLE, "Eat Snacks"),
    STOMACH_THAT_II("Stomach That II", QuestCategory.FORGING, Material.GLOW_BERRIES, 6, 125, Item.UNCOMMON_KEY, "Eat Snacks"),
    STOMACH_THAT_III("Stomach That III", QuestCategory.FORGING, Material.GLOW_BERRIES, 12, 275, Item.BLOOD_BLADE, "Eat Snacks"),
    STOMACH_THAT_IV("Stomach That IV", QuestCategory.FORGING, Material.GLOW_BERRIES, 36, 700, Item.RARE_KEY, "Eat Snacks"),
    STOMACH_THAT_V("Stomach That V", QuestCategory.FORGING, Material.GLOW_BERRIES, 108, 2500, Item.MERCHANT_LEGGINGS, "Eat Snacks"),
    PET_COLLECTOR_I("Pet Collector I", QuestCategory.PETS, Material.CREEPER_HEAD, 5, 200, Item.UNCOMMON_KEY, "Collect Pets"),
    PET_COLLECTOR_II("Pet Collector II", QuestCategory.PETS, Material.CREEPER_HEAD, 15, 400, Item.RARE_KEY, "Collect Pets"),
    PET_COLLECTOR_III("Pet Collector III", QuestCategory.PETS, Material.CREEPER_HEAD, 25, 800, Item.GEN_SLOT, "Collect Pets"),
    PET_COLLECTOR_IV("Pet Collector IV", QuestCategory.PETS, Material.CREEPER_HEAD, 50, 1600, Item.EPIC_KEY, "Collect Pets"),
    PET_COLLECTOR_V("Pet Collector V", QuestCategory.PETS, Material.CREEPER_HEAD, 100, 3200, Item.PET_SLOT, "Collect Pets"),
    ZOOLOGIST_I("Zoologist I", QuestCategory.PETS, Material.DRAGON_HEAD, 2, 500, Item.UNCOMMON_KEY, "Collect Legendary Pets"),
    ZOOLOGIST_II("Zoologist II", QuestCategory.PETS, Material.DRAGON_HEAD, 4, 1500, Item.RARE_KEY, "Collect Legendary Pets"),
    ZOOLOGIST_III("Zoologist III", QuestCategory.PETS, Material.DRAGON_HEAD, 8, 2000, Item.EPIC_MAT_BUNDLE, "Collect Legendary Pets"),
    ZOOLOGIST_IV("Zoologist IV", QuestCategory.PETS, Material.DRAGON_HEAD, 16, 3000, Item.COMMON_LOOTBOX, "Collect Legendary Pets"),
    ZOOLOGIST_V("Zoologist V", QuestCategory.PETS, Material.DRAGON_HEAD, 32, 5000, Item.LEGENDARY_KEY, "Collect Legendary Pets"),
    ABILITY_MASTER_I("Ability Master I", QuestCategory.PETS, Material.SCUTE, 1, 1, Item.COMMON_KEY, "Activate Pet Abilities"),
    ABILITY_MASTER_II("Ability Master II", QuestCategory.PETS, Material.SCUTE, 10, 10, Item.UNCOMMON_KEY, "Activate Pet Abilities"),
    ABILITY_MASTER_III("Ability Master III", QuestCategory.PETS, Material.SCUTE, 100, 100, Item.RARE_KEY, "Activate Pet Abilities"),
    ABILITY_MASTER_IV("Ability Master IV", QuestCategory.PETS, Material.SCUTE, 1000, 1000, Item.EPIC_KEY, "Activate Pet Abilities"),
    ABILITY_MASTER_V("Ability Master V", QuestCategory.PETS, Material.SCUTE, 10000, 10000, Item.EPIC_LOOTBOX, "Activate Pet Abilities"),
    //CROWN_HOLDER_I("Crown Holder I", QuestCategory.MINING, Material.LEATHER_HELMET, 1, 750, Item.RARE_KEY, "Win KOTHs"),
    //CROWN_HOLDER_II("Crown Holder II", QuestCategory.MINING, Material.CHAINMAIL_HELMET, 3, 1500, Item.LEGENDARY_KEY, "Win KOTHs"),
    //CROWN_HOLDER_III("Crown Holder III", QuestCategory.MINING, Material.IRON_HELMET, 5, 3000, Item.COMMON_LOOTBOX, "Win KOTHs"),
    //CROWN_HOLDER_IV("Crown Holder IV", QuestCategory.MINING, Material.GOLDEN_HELMET, 8, 4000, Item.UNCOMMON_LOOTBOX, "Win KOTHs"),
    //CROWN_HOLDER_V("Crown Holder V", QuestCategory.MINING, Material.DIAMOND_HELMET, 10, 5000, Item.RARE_LOOTBOX, "Win KOTHs"),
    LUSH_MEADOWS_I("Lush Meadows I", QuestCategory.FARMING, Material.DARK_OAK_WOOD, 1, 200, Item.COMMON_KEY, "Discover Lush Meadows"),
    POTATO_HARVESTER_I("Potato Harvester I", QuestCategory.FARMING, Material.POTATO, 100, 50, Item.GOLDEN_POTATO, "Harvest Potatoes"),
    POTATO_HARVESTER_II("Potato Harvester II", QuestCategory.FARMING, Material.POTATO, 250, 75, Item.COMMON_KEY,"Harvest Potatoes"),
    POTATO_HARVESTER_III("Potato Harvester III", QuestCategory.FARMING, Material.POTATO, 750, 200, Item.UNCOMMON_KEY ,"Harvest Potatoes"),
    POTATO_HARVESTER_IV("Potato Harvester IV", QuestCategory.FARMING, Material.POTATO, 2500, 500, Item.RARE_KEY,"Harvest Potatoes"),
    POTATO_HARVESTER_V("Potato Harvester V", QuestCategory.FARMING, Material.POTATO, 10000, 1250, Item.GEN_SLOT, "Harvest Potatoes"),
    CARROT_HARVESTER_I("Carrot Harvester I", QuestCategory.FARMING, Material.CARROT, 100, 50, Item.GOLDEN_CARROT, "Harvest Carrots"),
    CARROT_HARVESTER_II("Carrot Harvester II", QuestCategory.FARMING, Material.CARROT, 250, 75, Item.GOLDEN_CARROT,"Harvest Carrots"),
    CARROT_HARVESTER_III("Carrot Harvester III", QuestCategory.FARMING, Material.CARROT, 750, 200, Item.UNCOMMON_KEY,"Harvest Carrots"),
    CARROT_HARVESTER_IV("Carrot Harvester IV", QuestCategory.FARMING, Material.CARROT, 2500, 500, Item.RARE_KEY,"Harvest Carrots"),
    CARROT_HARVESTER_V("Carrot Harvester V", QuestCategory.FARMING, Material.CARROT, 10000, 1250, Item.EPIC_KEY, "Harvest Carrots"),
    BEETROOT_HARVESTER_I("Beetroot Harvester I", QuestCategory.FARMING, Material.BEETROOT, 100, 75, Item.GOLDEN_BEETROOT, "Harvest Beetroots"),
    BEETROOT_HARVESTER_II("Beetroot Harvester II", QuestCategory.FARMING, Material.BEETROOT, 250, 100, Item.UNCOMMON_KEY,"Harvest Beetroots"),
    BEETROOT_HARVESTER_III("Beetroot Harvester III", QuestCategory.FARMING, Material.BEETROOT, 750, 300, Item.RARE_KEY,"Harvest Beetroots"),
    BEETROOT_HARVESTER_IV("Beetroot Harvester IV", QuestCategory.FARMING, Material.BEETROOT, 2500, 600, Item.RARE_KEY,"Harvest Beetroots"),
    BEETROOT_HARVESTER_V("Beetroot Harvester V", QuestCategory.FARMING, Material.BEETROOT, 10000, 1500, Item.GEN_SLOT, "Harvest Beetroots"),
    WHEAT_HARVESTER_I("Wheat Harvester I", QuestCategory.FARMING, Material.WHEAT, 100, 100, Item.RARE_KEY, "Harvest Wheat"),
    WHEAT_HARVESTER_II("Wheat Harvester II", QuestCategory.FARMING, Material.WHEAT, 250, 200, Item.GOLDEN_WHEAT,"Harvest Wheat"),
    WHEAT_HARVESTER_III("Wheat Harvester III", QuestCategory.FARMING, Material.WHEAT, 750, 500, Item.EPIC_KEY ,"Harvest Wheat"),
    WHEAT_HARVESTER_IV("Wheat Harvester IV", QuestCategory.FARMING, Material.WHEAT, 2500, 1000, Item.GEN_SLOT,"Harvest Wheat"),
    WHEAT_HARVESTER_V("Wheat Harvester V", QuestCategory.FARMING, Material.WHEAT, 10000, 2000, Item.EPIC_KEY, "Harvest Wheat"),
    WART_HARVESTER_I("Wart Harvester I", QuestCategory.FARMING, Material.NETHER_WART, 100, 200, Item.GOLDEN_NETHER_WART, "Harvest Nether Wart"),
    WART_HARVESTER_II("Wart Harvester II", QuestCategory.FARMING, Material.NETHER_WART, 250, 300, Item.DARK_GOLD,"Harvest Nether Wart"),
    WART_HARVESTER_III("Wart Harvester III", QuestCategory.FARMING, Material.NETHER_WART, 750, 600, Item.EPIC_KEY ,"Harvest Nether Wart"),
    WART_HARVESTER_IV("Wart Harvester IV", QuestCategory.FARMING, Material.NETHER_WART, 2500, 1250, Item.GEN_SLOT,"Harvest Nether Wart"),
    WART_HARVESTER_V("Wart Harvester V", QuestCategory.FARMING, Material.NETHER_WART, 10000, 2500, Item.COMMON_LOOTBOX, "Harvest Nether Wart"),
    PLOT_FARMING_I("Plot Farming I", QuestCategory.FARMING, Material.GRASS, 9, 9, Item.COMMON_MAT_BUNDLE, "Harvest Crops at Your Plot"),
    PLOT_FARMING_II("Plot Farming II", QuestCategory.FARMING, Material.GRASS, 99, 99, Item.UNCOMMON_MAT_BUNDLE, "Harvest Crops at Your Plot"),
    PLOT_FARMING_III("Plot Farming III", QuestCategory.FARMING, Material.GRASS, 999, 99, Item.RARE_MAT_BUNDLE, "Harvest Crops at Your Plot"),
    PLOT_FARMING_IV("Plot Farming IV", QuestCategory.FARMING, Material.GRASS, 9999, 999, Item.EPIC_MAT_BUNDLE, "Harvest Crops at Your Plot"),
    PLOT_FARMING_V("Plot Farming V", QuestCategory.FARMING, Material.GRASS, 99999, 9999, Item.UNCOMMON_LOOTBOX, "Harvest Crops at Your Plot"),
    BOMB_THROWER_I("Bomb Thrower I", QuestCategory.FARMING, Material.SLIME_BALL, 3, 100, Item.COMMON_KEY, "Throw any Bomb"),
    BOMB_THROWER_II("Bomb Thrower II", QuestCategory.FARMING, Material.SLIME_BALL, 9, 250, Item.UNCOMMON_KEY, "Throw any Bomb"),
    BOMB_THROWER_III("Bomb Thrower III", QuestCategory.FARMING, Material.SLIME_BALL, 27, 750, Item.GEN_SLOT, "Throw any Bomb"),
    BOMB_THROWER_IV("Bomb Thrower IV", QuestCategory.FARMING, Material.SLIME_BALL, 54, 1750, Item.MERCHANT_BOOTS, "Throw any Bomb"),
    BOMB_THROWER_V("Bomb Thrower V", QuestCategory.FARMING, Material.SLIME_BALL, 108, 3500, Item.EPIC_KEY, "Throw any Bomb"),
    // SELL ITEMS
    // USE SELL WAND
    ;

    private String name;
    private QuestCategory type;
    private Material icon;
    private Item forge;
    private int amount;
    private int experience;
    private Item item;
    private String description;

    Quest(String name, QuestCategory type, Material icon, int amount, int experience, Item item, String description) {
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.amount = amount;
        this.experience = experience;
        this.item = item;
        this.description = description;
    }

    public static List<Quest> getQuests(QuestCategory category) {
        List<Quest> quests = new ArrayList<>();
        for(Quest quest : Quest.values()) {
            if(quest.getCategory() == category) {
                quests.add(quest);
            }
        }
        return quests;
    }

    public String getName() {
        return name;
    }

    public int getPlayerProgress(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        // I regret nothing.
        if(this.toString().startsWith("COAL_MINER")) {
            return user.getMat(Mat.COAL_ORE);
        } else if(this.toString().startsWith("IRON_MINER")) {
            return user.getMat(Mat.IRON_ORE);
        } else if(this.toString().startsWith("COPPER_MINER")) {
            return user.getMat(Mat.COPPER_ORE);
        } else if(this.toString().startsWith("GOLD_MINER")) {
            return user.getMat(Mat.GOLD_ORE);
        } else if(this.toString().startsWith("REDSTONE_MINER")) {
            return user.getMat(Mat.REDSTONE_ORE);
        } else if(this.toString().startsWith("LAPIS_MINER")) {
            return user.getMat(Mat.LAPIS_ORE);
        } else if(this.toString().startsWith("DIAMOND_MINER")) {
            return user.getMat(Mat.DIAMOND_ORE);
        } else if(this.toString().startsWith("EMERALD_MINER")) {
            return user.getMat(Mat.EMERALD_ORE);
        } else if(this.toString().startsWith("DARK_ORES")) {
            return user.getMat(Mat.DARK_COAL) + user.getMat(Mat.DARK_IRON) + user.getMat(Mat.DARK_COPPER) + user.getMat(Mat.DARK_GOLD) + user.getMat(Mat.DARK_REDSTONE) + user.getMat(Mat.DARK_LAPIS) + user.getMat(Mat.DARK_DIAMOND) + user.getMat(Mat.DARK_EMERALD);
        } else if(this.toString().startsWith("HUNTER")) {
            return user.getKilled();
        } else if(this.toString().startsWith("GENOISSEUR")) {
            return user.getGensUpgraded();
        } else if(this.toString().startsWith("FLOATING_GENS")) {
            return user.getShulkersUpgraded();
        } else if(this.toString().startsWith("CRATE_OPENER")) {
            return user.getCratesOpened();
        } else if(this.toString().startsWith("LEGENDARY_ITEM")) {
            return user.getLegendaryCrateItems();
        } else if(this.toString().startsWith("ITEM_FORGER")) {
            return user.getItemsForged();
        } else if(this.toString().startsWith("LEGENDARY_FORGING")) {
            return user.getLegendaryItemsForged();
        } else if(this.toString().startsWith("MATERIAL_MAKING")) {
            return user.getMatsForged();
        } else if(this.toString().startsWith("PET_COLLECTOR")) {
            return user.getPetsCollected();
        } else if(this.toString().startsWith("ZOOLOGIST")) {
            return user.getLegendaryPetsCollected();
        } else if(this.toString().startsWith("CROWN_HOLDER")) {
            return user.getKothsWon();
        } else if(this.toString().startsWith("TANGLE_CAVERNS")) {
            return user.getCompletedQuests().contains(Quest.TANGLE_CAVERNS_I) ? 1 : 0;
        } else if(this.toString().startsWith("LUSH_MEADOWS")) {
            return user.getCompletedQuests().contains(Quest.LUSH_MEADOWS_I) ? 1 : 0;
        } else if(this.toString().startsWith("POTATO_HARVESTER")) {
            return user.getMat(Mat.POTATO);
        } else if(this.toString().startsWith("CARROT_HARVESTER")) {
            return user.getMat(Mat.CARROT);
        } else if(this.toString().startsWith("BEETROOT_HARVESTER")) {
            return user.getMat(Mat.BEETROOT);
        } else if(this.toString().startsWith("WHEAT_HARVESTER")) {
            return user.getMat(Mat.WHEAT);
        } else if(this.toString().startsWith("WART_HARVESTER")) {
            return user.getMat(Mat.NETHER_WART);
        } else if(this.toString().startsWith("MAGIC_WAND")) {
            return user.getSellWandsForged();
        } else if(this.toString().startsWith("LUSH_MEADOWS")) {
            return user.getLushMeadows();
        } else if(this.toString().startsWith("TANGLE_CAVERNS")) {
            return user.getTangleCaverns();
        } else if(this.toString().startsWith("BIG_HAUL")) {
            return user.getItemsScrapped();
        } else if(this.toString().startsWith("INVALUABLE_WASTE")) {
            return user.getRareItemsScrapped();
        } else if(this.toString().startsWith("STOMACH_THAT")) {
            return user.getSnacksConsumed();
        } else if(this.toString().startsWith("THIEF")) {
            return user.getStolenItems();
        } else if(this.toString().startsWith("MERCHANT")) {
            return user.getItemsSold();
        } else if(this.toString().startsWith("ABILITY_MASTER")) {
            return user.getPetAbilities();
        } else if(this.toString().startsWith("PLOT_FARMING")) {
            return user.getCropsAtPlot();
        } else if(this.toString().startsWith("BOMB_THROWER")) {
            return user.getBombsThrown();
        }
        return -1;
    }

    public static int getQuestAmount(QuestCategory category) {
        return getQuests(category).size();
    }

    public QuestCategory getCategory() {
        return type;
    }

    public Material getIcon() {
        return icon;
    }

    public Item getForge() {
        return forge;
    }

    public int getAmount() {
        return amount;
    }

    public int getExperience() {
        return experience;
    }

    public Item getItem() {
        return item;
    }

    public String getDescription() {
        return description;
    }

    public void finishQuest(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        user.getCompletedQuests().add(this);
        player.sendMessage("");
        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + name + " Completed");
        player.sendMessage("");
        player.sendMessage("Rewards:");
        player.sendMessage(ChatColor.DARK_GRAY + "> " + item.getRarity().getColor() + "1x " + item.getName());
        player.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.GOLD + "" + experience + "x Experience");
        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_GRAY + "Congratulations!");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.25f);

        if(item == Item.PET_SLOT) {
            user.setPetSlots(user.getPetSlots() + 1);
        } else if(item == Item.GEN_SLOT) {
            user.setSlots(user.getSlots() + 1);
        } else {
            player.getInventory().addItem(item.getItem());
        }

        user.setExperience(user.getExperience() + experience);

        if(user.getExperience() >= Level.getExperienceNeeded(user.getLevel() + 1)) {
            Level.playerLevelUp(player, user.getLevel() + 1);
        }

    }

}
