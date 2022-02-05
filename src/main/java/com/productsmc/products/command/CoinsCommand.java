package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.leaderboard.LeaderboardUpdater;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.UUID;

public class CoinsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("coins")) {
            if(args.length == 0) {
                User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
                player.sendMessage(Products.getPrefix() + "You have " + ChatColor.YELLOW + ProductsUtil.format(user.getCoins()) + "⛂");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
                return true;
            }
            try {
                Player target = Bukkit.getPlayer(args[0]);
                User user = Products.getInstance().getUserManager().getUser(target.getUniqueId());
                player.sendMessage(Products.getPrefix() + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " has " + ChatColor.YELLOW + ProductsUtil.format(user.getCoins()) + "⛂");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
            } catch(Exception e) {
                player.sendMessage(Products.getPrefix() + "That is not a player!");
            }
            return true;
        }
        if(cmd.getName().equalsIgnoreCase("setcoins")) {
            if(player.hasPermission("*")) {
                Player target = Bukkit.getPlayer(args[0]);
                long amount = Long.parseLong(args[1]);
                User user = Products.getInstance().getUserManager().getUser(target.getUniqueId());
                user.setCoins(amount);
                player.sendMessage(Products.getPrefix() + "Set " + target.getName() + "'s Coins to " + amount);
            }
        }
        if(cmd.getName().equalsIgnoreCase("coinstop")) {
            if(args.length > 0 && player.hasPermission("admin") && args[0].equalsIgnoreCase("update")) {
                LeaderboardUpdater.updateCoinsLeaderboard();
                return true;
            }
            player.sendMessage("");
            player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Top Coins ⛂");
            player.sendMessage("");

            LinkedHashMap<UUID, Long> topCoins = Products.getInstance().topCoins;
            int index = 1;

            for(UUID top : topCoins.keySet()) {
                Player topPlayer = Bukkit.getPlayer(top);
                player.sendMessage(ChatColor.YELLOW + "#" + index + " " + (topPlayer == null ? ChatColor.RED + Bukkit.getOfflinePlayer(top).getName() : ChatColor.GREEN + topPlayer.getName()) + ChatColor.YELLOW + " " + ProductsUtil.format(topCoins.get(top)) + "⛂");
                index++;
            }
            player.sendMessage("");
            player.sendMessage(ChatColor.DARK_GRAY + "Leaderboards update every 1 hour");
        }
        return true;
    }

}
