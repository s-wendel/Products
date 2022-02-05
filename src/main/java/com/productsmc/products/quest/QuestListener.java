package com.productsmc.products.quest;

import com.productsmc.products.Products;
import com.productsmc.products.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class QuestListener implements Listener {

    @EventHandler
    public void miningListener(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        Material material = event.getBlock().getType();
    }

}
