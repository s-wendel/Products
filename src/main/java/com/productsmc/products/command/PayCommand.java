package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("pay")) {
            if(args.length <= 1) {
                player.sendMessage(Products.getPrefix() + "/pay <player> <amount>");
                return true;
            }
            try {
                Player target = Bukkit.getPlayer(args[0]);
                int amount = Integer.parseInt(args[1]);
                User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
                User targetUser = Products.getInstance().getUserManager().getUser(target.getUniqueId());
                if(amount <= 0) {
                    player.sendMessage(Products.getPrefix() + "/pay <player> <amount>");
                    return true;
                }
                if(user.getCoins() >= amount) {
                    user.setCoins(user.getCoins() - amount);
                    targetUser.setCoins(targetUser.getCoins() + amount);
                    player.sendMessage(Products.getPrefix() + "You sent " + ChatColor.YELLOW + ProductsUtil.format(amount) + "⛂" + ChatColor.WHITE + " to " + target.getName());
                    target.sendMessage(Products.getPrefix() + "You received " + ChatColor.YELLOW + ProductsUtil.format(amount) + "⛂ " + ChatColor.WHITE + "from " + player.getName());
                }
            } catch(Exception e) {
                player.sendMessage(Products.getPrefix() + "/pay <player> <amount>");
                return true;
            }
        }
        if (cmd.getName().equalsIgnoreCase("shardpay")) {
            if(args.length <= 1) {
                player.sendMessage(Products.getPrefix() + "/shardpay <player> <amount>");
                return true;
            }
            try {
                Player target = Bukkit.getPlayer(args[0]);
                int amount = Integer.parseInt(args[1]);
                User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
                User targetUser = Products.getInstance().getUserManager().getUser(target.getUniqueId());
                if(amount <= 0) {
                    player.sendMessage(Products.getPrefix() + "/shardpay <player> <amount>");
                    return true;
                }
                if(user.getShards() >= amount) {
                    user.setShards(user.getShards() - amount);
                    targetUser.setShards(targetUser.getShards() + amount);
                    player.sendMessage(Products.getPrefix() + "You sent " + ChatColor.YELLOW + ProductsUtil.format(amount) + "♢" + ChatColor.WHITE + " to " + target.getName());
                    target.sendMessage(Products.getPrefix() + "You received " + ChatColor.YELLOW + ProductsUtil.format(amount) + "♢ " + ChatColor.WHITE + "from " + player.getName());
                }
            } catch(Exception e) {
                player.sendMessage(Products.getPrefix() + "/shardpay <player> <amount>");
                return true;
            }
        }
        return true;
    }

}
