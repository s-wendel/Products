package com.productsmc.products.guide;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Guide {

    START("Start", Material.WOODEN_PICKAXE, 1, ChatColor.GRAY + "Type " + ChatColor.YELLOW + "/start" + ChatColor.GRAY + " to create a plot", ChatColor.GRAY + "on this plot, you can place your " + ChatColor.YELLOW + "Coal Generator", ChatColor.GRAY + "Every 10 seconds they can be sold using " + ChatColor.YELLOW + "/sell", ChatColor.GRAY + "to earn "  + ChatColor.YELLOW + "Coins ⛂", "", ChatColor.GRAY + "Earn levels by completing quests using " + ChatColor.YELLOW + "/quests", ChatColor.GRAY + "Levels give you rewards and unlocks such as Fly", ChatColor.GRAY + "and new Islands!"),
    GENS("Gens", Material.COAL, 1,ChatColor.GRAY + "Gens are a way of gaining " + ChatColor.YELLOW + "Coins ⛂", ChatColor.GRAY + "You can place your Gen at your plot.", ChatColor.GRAY + "Gens produce items every " + ChatColor.YELLOW + "10 seconds", ChatColor.GRAY + "Get more Gens by typing " + ChatColor.YELLOW + "/shop", ChatColor.GRAY + "To upgrade a gen; Right-Click it", ChatColor.GRAY + "To remove a gen; Left-Click it", "", ChatColor.GRAY + "You can place a limited amount of Gens. Your scoreboard", ChatColor.GRAY + "will show you how many left you have.", ChatColor.GRAY + "Earn more through " + ChatColor.YELLOW + "crates"),
    LEVELS("Levels", Material.GLOWSTONE_DUST, 1, ChatColor.GRAY + "Levels give various rewards and unlocks", ChatColor.GRAY + "By typing " + ChatColor.YELLOW + "/levels" + ChatColor.GRAY + " you can check rewards,", ChatColor.GRAY + "unlocks, & requirements for Levels", ChatColor.GRAY + "Levels are also used for " + ChatColor.YELLOW + "Payouts"),
    QUESTS("Quests", Material.BOOK, 1, ChatColor.GRAY + "Quests are a way to earn " + ChatColor.YELLOW + "Rewards/Experience", ChatColor.GRAY + "You can access Quests by typing " + ChatColor.YELLOW + "/quests", ChatColor.GRAY + "There are over a hundred quests to complete!"),
    ECONOMY("Economy", Material.EMERALD, 1, ChatColor.GRAY + "There a few currencies. There are " + ChatColor.YELLOW + "Coins ⛂", ChatColor.YELLOW + "Shards ♢" + ChatColor.GRAY + ", & " + ChatColor.YELLOW + "Mats.", "", ChatColor.GRAY + "Coins are used for /shop, Gens, & Leaderboards.", ChatColor.GRAY + "They can be obtained from Gens", "", ChatColor.GRAY + "Shards are used for /forge & /shop.", ChatColor.GRAY + "They are obtained from Mining & Farming.", "", ChatColor.GRAY + "Mats are used for /forge to create items", ChatColor.GRAY + "they are a big aspect of the game", ChatColor.GRAY + "They can be earned from Mining & Farming"),
    MATS("Mats", Material.YELLOW_DYE, 4, ChatColor.GRAY + "Mats are a big component of the game.", ChatColor.GRAY + "Earn Mats from Mining or Farming.", "", ChatColor.GRAY + "You are able to use these Mats in " + ChatColor.YELLOW + "/forge", ChatColor.GRAY + "to forge various items", ChatColor.GRAY + "You can earn Mats from " + ChatColor.YELLOW + "Mat Bundles" + ChatColor.GRAY + " in crates", ChatColor.GRAY + "as well as The Trader & Scrapper", ChatColor.GRAY + "located in different points of the Map"),
    PLOTS("Plots", Material.GRASS, 1, ChatColor.GRAY + "Plots are a way to store items and build.", ChatColor.GRAY + "If you don't have a plot, type " + ChatColor.YELLOW + "/start", ChatColor.GRAY + "if you do, type " + ChatColor.YELLOW + "/p home" + ChatColor.GRAY + " to go back!", "", ChatColor.GRAY + "Here are some various commands useful for plots:", "", ChatColor.YELLOW + "/p add " + ChatColor.GRAY + "Add a player to your plot", ChatColor.YELLOW + "/p remove " + ChatColor.GRAY + "Remove a player from your plot", ChatColor.YELLOW + "/p visit " + ChatColor.GRAY + "Visit a player's plot", ChatColor.YELLOW + "/p deny <p,*> " + ChatColor.GRAY + "Prevent players from entering"),
    MINING("Mining", Material.IRON_PICKAXE, 4, ChatColor.GRAY + "Access your current mine by typing " + ChatColor.YELLOW + "/mine", ChatColor.GRAY + "You can mine blocks to earn Mats & Shards", ChatColor.GRAY + "Use these resources in " + ChatColor.YELLOW + "/forge"),
    FARMING("Farming", Material.IRON_HOE, 8, ChatColor.GRAY + "Access your current farm by typing " + ChatColor.YELLOW + "/farm", ChatColor.GRAY + "You can harvest crops to earn Mats & Shards", ChatColor.GRAY + "Use these resources in " + ChatColor.YELLOW + "/forge", "", ChatColor.GRAY + "You can also plant crops at your Plot", ChatColor.GRAY + "Crops at your plot have " + ChatColor.YELLOW + "+16% Growth Speed"),
    PAYOUTS("Payouts", Material.DIAMOND, 1, ChatColor.GRAY + "You can earn Payouts by earning a", ChatColor.GRAY + "leaderboard position. Check leaderboards by", ChatColor.GRAY + "typing " + ChatColor.YELLOW + "/baltop or /leveltop"),
    ;

    private String name;
    private Material material;
    private int level;
    private String[] description;

    Guide(String name, Material material, int level, String... description) {
        this.name = name;
        this.material = material;
        this.level = level;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public int getLevel() { return level; }

    public String[] getDescription() {
        return description;
    }

}
