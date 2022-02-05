package com.productsmc.products.pads;

import com.productsmc.products.Products;
import com.productsmc.products.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PadListener implements Listener {

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Pad pad = Pad.getLaunchPad(player);
        if(pad != null) {
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            if(System.currentTimeMillis() >= user.padCooldown + 5000) {
                pad.run(player);
                user.padCooldown = System.currentTimeMillis();
            }
        }
    }

}
