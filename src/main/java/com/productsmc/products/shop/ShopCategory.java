package com.productsmc.products.shop;

import org.bukkit.Material;

public enum ShopCategory {

    BLOCKS(Material.JUNGLE_PLANKS, 11),
    DECORATION(Material.BLUE_ORCHID, 13),
    GENS(Material.COPPER_BLOCK, 15),
    TOOLS(Material.IRON_PICKAXE, 30),
    MISC(Material.LAVA_BUCKET, 32);

    private Material icon;
    private int slot;

    ShopCategory(Material icon, int slot) {
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
