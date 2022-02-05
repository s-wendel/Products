package com.productsmc.products.item.listener;

import com.productsmc.products.Products;
import com.productsmc.products.generator.Generator;
import com.productsmc.products.user.User;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MagicBucketListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBucket(PlayerBucketEmptyEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(event.isCancelled()) {
            return;
        }

        Material liquid = item.getType() == Material.LAVA_BUCKET ? Material.LAVA : Material.WATER;
        Block block = event.getBlock();

        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        if(item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Magic")) {

            if(liquid == Material.WATER && block.getBlockData() instanceof Waterlogged){
                Waterlogged log = (Waterlogged) block.getBlockData();
                log.setWaterlogged(true);
                block.setBlockData(log);
            } else {
                player.getWorld().getBlockAt(x, y, z).setType(liquid);

            }
            event.setCancelled(true);
        }

    }

}
