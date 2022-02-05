package com.productsmc.products.quest;

import org.bukkit.Material;

public enum QuestCategory {

    GENS(Material.COPPER_BLOCK, 28, 1),
    CRATES(Material.NETHER_STAR, 29, 1),
    PETS(Material.CREEPER_HEAD, 30, 1),
    FORGING(Material.RAW_GOLD, 32, 4),
    MINING(Material.IRON_PICKAXE, 33, 4),
    FARMING(Material.STONE_HOE, 34, 8)
    ;

    private Material icon;
    private int slot;
    private int level;

    QuestCategory(Material icon, int slot, int level) {
        this.icon = icon;
        this.slot = slot;
        this.level = level;
    }

    public int getSlot() {
        return slot;
    }

    public Material getIcon() {
        return icon;
    }

    public int getLevel() {
        return level;
    }

}
