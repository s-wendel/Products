package com.productsmc.products.item.listener;

import com.productsmc.products.Products;
import com.productsmc.products.generator.Generator;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GeneratorRemoteListener implements Listener {

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Generator Remote") && player.isSneaking()) {
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            event.setCancelled(true);
            for(Location location : user.getGens()) {
                Block block = location.getBlock();
                Generator generator = Generator.of(block.getType());
                block.setType(Material.AIR);
                player.getInventory().addItem(generator.getGenerator(1));
            }
            user.getGens().clear();
            player.sendMessage(Products.getPrefix() + "You removed all of your generators");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);

        }
    }

}
