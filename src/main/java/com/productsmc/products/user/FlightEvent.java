package com.productsmc.products.user;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FlightEvent implements Listener {

    @EventHandler
    public void playerFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if(player.getWorld().getName().equals("ul_Spawn") && !player.hasPermission("srmod")) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

}
