package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.user.User;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("fly")) {
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            if(user.getLevel() >= 10 || player.hasPermission("amber")) {
                if(player.getWorld().getName().equals("ul_Spawn")) {
                    player.sendMessage(Products.getPrefix() + "You are not allowed to fly in this world!");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    return true;
                }
                player.setAllowFlight(player.getAllowFlight() ? false : true);
                player.setFlying(player.getAllowFlight() ? true : false);
                player.sendMessage(Products.getPrefix() + "You are no" + (player.getAllowFlight() ? "w" : " longer") + " flying!");
                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1, 1);
                return true;
            }
            player.sendMessage(Products.getPrefix() + "You unlock Fly at Level 10!");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            return true;
        }
        return true;
    }

}
