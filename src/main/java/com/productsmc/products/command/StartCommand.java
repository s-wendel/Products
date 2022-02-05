package com.productsmc.products.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("start")) {
            player.performCommand("p a");
            player.sendMessage("");
            player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "WELCOME TO PRODUCTS!");
            player.sendMessage("");
            player.sendMessage(ChatColor.WHITE + "Place your " + ChatColor.YELLOW + "Gens" + ChatColor.WHITE + " for Coins");
            player.sendMessage(ChatColor.WHITE + "Type " + ChatColor.YELLOW + "'/sell'" + ChatColor.WHITE + " to sell your Coins");
            player.sendMessage(ChatColor.WHITE + "Use your " + ChatColor.YELLOW + "Sell Wand" + ChatColor.WHITE + " on chests for quick selling");
            player.sendMessage(ChatColor.WHITE + "Type " + ChatColor.YELLOW + "'/quests'" + ChatColor.WHITE + " for Questing to level up!");
            player.sendMessage("");
            player.sendMessage(ChatColor.DARK_GRAY + "Type '/help' for additional help");
        }
        return true;
    }

}
