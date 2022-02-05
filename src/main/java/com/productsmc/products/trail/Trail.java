package com.productsmc.products.trail;

import com.productsmc.products.item.Rarity;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;

public enum Trail {

    SNOWFLAKES("Snowflakes", Rarity.EVENT, Particle.SNOWFLAKE, "Obtained from the Winter Event", Material.SNOWBALL, 2),
    COBALT_FLAMES("Cobalt Flames", Rarity.LEGENDARY, Particle.SOUL_FIRE_FLAME, "Obtained from the Store", Material.TUBE_CORAL, 2),
    CRIMSON_FLAMES("Crimson Flames", Rarity.EPIC, Particle.FLAME, "Obtained by being Level 99", Material.FIRE_CORAL, 2),
    REVITALIZE("Revitalize", Rarity.RARE, Particle.SPELL_WITCH, "Exclusive to Super Fans", Material.PURPLE_DYE, 2),
    CLOUDS("Clouds", Rarity.UNCOMMON, Particle.CLOUD, "Obtained by being Level 50", Material.WHITE_WOOL, 2),
    SPARKS("Sparks", Rarity.COMMON, Particle.FIREWORKS_SPARK, "Obtained by being Level 25", Material.FIREWORK_ROCKET, 2),
    CROP("Crop", Rarity.COMMON, Particle.COMPOSTER, "Obtained by completing Potato Harvester V", Material.WHEAT, 2),
    DISCORD("Discord", Rarity.COMMON, Particle.SCRAPE, "Obtained by linking your Discord", Material.LIGHT_BLUE_WOOL, 2)
    ;

    private String name;
    private Rarity rarity;
    private Particle particle;
    private String description;
    private Material icon;
    private int season;

    Trail(String name, Rarity rarity, Particle particle, String description, Material icon, int season) {
        this.name = name;
        this.rarity = rarity;
        this.particle = particle;
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

    public Particle getParticle() {
        return particle;
    }

    public String getDescription() {
        return description;
    }

    public int getSeason() {
        return season;
    }

    public String getPermission() {
        return "trail" + name;
    }

    public Material getIcon() {
        return icon;
    }



}
