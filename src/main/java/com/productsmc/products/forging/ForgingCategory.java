package com.productsmc.products.forging;

import org.bukkit.Material;

public enum ForgingCategory {

    ARMOR(Material.CHAINMAIL_CHESTPLATE, 10),
    COMBAT(Material.GOLDEN_SWORD, 12),
    TOOLS(Material.WOODEN_PICKAXE, 14),
    TROPHY(Material.YELLOW_CANDLE, 16),
    KEYS(Material.NETHER_STAR, 28),
    MATS(Material.LIGHTNING_ROD,30),
    SNACKS(Material.CAKE, 32),
    MISC(Material.COMPARATOR, 34);

    private Material icon;
    private int slot;

    ForgingCategory(Material icon, int slot) {
        this.icon = icon;
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public Material getIcon() {
        return icon;
    }

}
