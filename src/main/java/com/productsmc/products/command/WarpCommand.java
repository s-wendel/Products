package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.user.User;
import com.productsmc.products.warp.Warp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("warp")) {
            player.sendMessage("");
            player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Warps");
            player.sendMessage(ChatColor.YELLOW + "/crates " + ChatColor.WHITE + "Open up Keys");
            player.sendMessage(ChatColor.YELLOW + "/mine " + ChatColor.WHITE + "Mine various ores for Mats " + ChatColor.RED + "(Level 4+)");
            player.sendMessage(ChatColor.YELLOW + "/farm " + ChatColor.WHITE + "Harvest various crops for Mats" + ChatColor.RED + "(Level 8+)");
            player.sendMessage(ChatColor.YELLOW + "/spawn " + ChatColor.WHITE + "Travel to the village");
            player.sendMessage("");
        }
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            player.teleport(Warp.SPAWN.getLocation());
        }
        if (cmd.getName().equalsIgnoreCase("crates")) {
            player.teleport(Warp.CRATES.getLocation());
        }
        if (cmd.getName().equalsIgnoreCase("farm")) {
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            if(user.getLevel() >= 8) {
                player.teleport(Warp.FARM.getLocation());
                player.sendMessage(Products.getPrefix() + "You arrived at the " + ChatColor.YELLOW + "Lush Meadows" + ChatColor.WHITE + "!");
            } else {
                player.sendMessage(ChatColor.RED + "You must be Level 8 to Farm. Type '/quests' to level up!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("mine")) {
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            if(user.getLevel() >= 4) {
                player.teleport(Warp.MINE.getLocation());
                player.sendMessage(Products.getPrefix() + "You arrived at the " + ChatColor.YELLOW + "Tangle Caverns" + ChatColor.WHITE + "!");
            } else {
                player.sendMessage(ChatColor.RED + "You must be Level 4 to Mine. Type '/quests' to level up!");
            }
        }
        return true;
    }

}
