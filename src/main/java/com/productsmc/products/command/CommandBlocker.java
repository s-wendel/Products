package com.productsmc.products.command;

import com.productsmc.products.Products;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandBlocker implements Listener {

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args[0].contains(":")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Plugin specfic commands are disabled to avoid bypasses. Thanks for your understanding");
        }
    }

}
