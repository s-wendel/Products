package com.productsmc.products.generator;

import com.productsmc.products.Products;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveGeneratorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if(command.getName().equalsIgnoreCase("givegen")) {
            if(!sender.hasPermission("*")) {
                return true;
            }
            if(args.length <= 2) {
                sender.sendMessage(ChatColor.RED + "/givegen <player> <type> <amount>");
                return true;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if(player == null) {
                sender.sendMessage(ChatColor.RED + "Player doesn't exist.");
                return true;
            }
            int amount = Integer.parseInt(args[2]);
            player.getInventory().addItem(Generator.valueOf(args[1].toUpperCase()).getGenerator(amount));
            player.sendMessage(Products.getPrefix() + "You were given " + amount + "x " + ProductsUtil.toProperCase(args[1]) + " Generator!");
        }
        return true;
    }

}
