package com.productsmc.products.pads;

import com.productsmc.products.Products;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.warp.Warp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public enum Pad {

    FARM_T_VILLAGE(Warp.VILLAGE_FR_FARM, 1,-882, 71, 676, -885, 73, 679),
    VILLAGE_T_FARM(Warp.FARM_FR_VILLAGE, 8, -826, 58, 679, -829, 60, 682),
    MINE_T_VILLAGE(Warp.VILLAGE_FR_MINE,1, -759, 59, 518, -762, 62, 521),
    VILLAGE_T_MINE(Warp.MINE_FR_VILLAGE, 4, -765, 53, 564, -768, 55, 567),
    ;

    private Warp destination;
    private int level;
    private double minX, minY, minZ, maxX, maxY, maxZ;

    Pad(Warp destination, int level, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.destination = destination;
        this.level = level;
        this.minX = Math.abs(minX);
        this.minY = Math.abs(minY);
        this.minZ = Math.abs(minZ);
        this.maxX = Math.abs(maxX);
        this.maxY = Math.abs(maxY);
        this.maxZ = Math.abs(maxZ);
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public Warp getDestination() {
        return destination;
    }

    public static Pad getLaunchPad(Player player) {
        Location location = player.getLocation();
        if(!location.getWorld().getName().equals("ul_Spawn")){
            return null;
        }
        double x = Math.abs(player.getLocation().getX()), y = Math.abs(player.getLocation().getY()),  z = Math.abs(player.getLocation().getZ());
        for(Pad pad : Pad.values()) {
            if(x >= pad.getMinX() && y >= pad.getMinY() && z >= pad.getMinZ() && x <= pad.getMaxX() && y <= pad.getMaxY() && z <= pad.getMaxZ()) {
                return pad;
            }
        }
        return null;
    }

    public void run(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        if(user.getLevel() >= level) {
            player.setVelocity(new Vector(0, 1.5, 0));
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(destination.getLocation());
                    player.sendMessage(Products.getPrefix() + "You arrived at the " + ChatColor.YELLOW + destination.getName() + ChatColor.WHITE + "!");
                    player.playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH, 1, 1);
                    player.playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH_SMALL, 1, 1);
                    if(destination == Warp.FARM_FR_VILLAGE) {
                        user.setLushMeadows(user.getLushMeadows() + 1);
                        Quest quest = Quest.valueOf("LUSH_MEADOWS_I");
                        if(!user.getCompletedQuests().contains(quest) && user.getLushMeadows() >= quest.getAmount()) {
                            quest.finishQuest(player);
                        }
                    } else if(destination == Warp.MINE_FR_VILLAGE) {
                        user.setTangleCaverns(user.getTangleCaverns() + 1);
                        Quest quest = Quest.valueOf("TANGLE_CAVERNS_I");
                        if(!user.getCompletedQuests().contains(quest) && user.getTangleCaverns() >= quest.getAmount()) {
                            quest.finishQuest(player);
                        }
                    }
                }
            }.runTaskLater(Products.getInstance(), 15);
        } else {
            player.sendMessage(ChatColor.RED + "It appears you aren't high enough level for this... Come back later to access this Launch Pad!");
            player.setVelocity(player.getLocation().getDirection().multiply(-1).setY(1));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
        }
    }

}
