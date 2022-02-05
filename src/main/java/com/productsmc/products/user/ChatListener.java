package com.productsmc.products.user;

import com.productsmc.products.Products;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        event.setFormat(ChatColor.YELLOW + "" + user.getLevel() + "✫ " + ChatColor.WHITE + " %s " + ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "%s");
    }

}
