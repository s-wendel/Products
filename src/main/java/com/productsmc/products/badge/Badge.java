package com.productsmc.products.badge;

import com.productsmc.products.item.Rarity;
import org.bukkit.Material;

public enum Badge {

    LIGHTNING("Lightning", Rarity.EVENT, "⚡", "Obtained from the Player Guide Contest", Material.GOLDEN_CARROT, 1),
    FLAME("Flame", Rarity.EPIC, "🔥", "Obtained from Products Rank", Material.LAVA_BUCKET, 2),
    STAR("Star", Rarity.COMMON, "★", "Obtained from Level 100", Material.GLOWSTONE_DUST, 2),
    HEART("Heart", Rarity.UNCOMMON, "❤", "Obtained from the Store", Material.RED_DYE, 2),
    MOON("Moon", Rarity.RARE, "☽", "Obtained from ", Material.END_STONE, 2)
    ;

    private String name;
    private Rarity rarity;
    private String badge; // What is shown
    private String description;
    private Material icon;
    private int season;

    Badge(String name, Rarity rarity, String badge, String description, Material icon, int season) {
        this.name = name;
        this.rarity = rarity;
        this.badge = badge;
        this.description = description;
        this.icon = icon;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getBadge() {
        return rarity.getColor() + badge;
    }

    public String getDescription() {
        return description;
    }

    public int getSeason() {
        return season;
    }

    public String getPermission() {
        return "badge" + name;
    }

    public Material getIcon() {
        return icon;
    }


}
