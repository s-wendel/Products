package com.productsmc.products.warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Warp {

    VILLAGE_FR_FARM("Village", -822.5, 64, 680.5, -89.3f, 0.1f),
    FARM_FR_VILLAGE("Lush Meadows", -887.5, 77, 677.5, 90.4f, -2.7f),
    VILLAGE_FR_MINE("Village", -766.5, 59, 569.5, 1.1f, 0.3f),
    MINE_FR_VILLAGE("Tangle Caverns", -760.5, 65, 515.5, 179.8f, 0.4f),
    SPAWN("Village", -769.5, 55, 687.5, -133.6f, -1.2f),
    FARM("Lush Meadows", -887.5, 72, 677.5, 90.4f, -2.7f),
    MINE("Tangle Caverns", -760.5, 60, 515.5, 179.8f, 0.4f),
    CRATES("Crates", -763.5, 55, 634.5, -89.8f, -1.7f),
    SMALL_FARM("Small Farm", -741.5, 61, 716.5, 41.8f, -2.9f),
    ;

    private String name;
    private double x, y, z;
    private float yaw, pitch;

    Warp(String name, double x, double y, double z, float yaw, float pitch) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld("ul_Spawn"), x, y, z, yaw, pitch);
    }

}
