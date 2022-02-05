package com.productsmc.products.pets;

import com.productsmc.products.Products;
import com.productsmc.products.user.User;
import com.productsmc.products.util.InventoryBuilder;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if(command.getName().equalsIgnoreCase("givepet")) {
            if(!sender.hasPermission("*")) {
                return true;
            }
            if(args.length <= 1) {
                sender.sendMessage(ChatColor.RED + "/givepet <player> <type>");
                return true;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if(player == null) {
                sender.sendMessage(ChatColor.RED + "Player doesn't exist.");
                return true;
            }
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            user.givePet(Pet.valueOf(args[1].toUpperCase()));
            player.sendMessage(Products.getPrefix() + "You were given 1x " + ProductsUtil.toProperCase(args[1]) + " Pet!");
        }
        return true;
    }

}
