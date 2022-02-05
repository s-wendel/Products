package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.leaderboard.LeaderboardUpdater;
import com.productsmc.products.level.Level;
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

public class LevelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("level")) {
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            long experience = Level.getExperienceNeeded(user.getLevel() + 1);
            player.sendMessage(Products.getPrefix() + "You are " + ChatColor.YELLOW +  "Level " + user.getLevel() + "✫ " + ChatColor.GRAY + " (" + user.getExperience() + "/" + experience + ") " + ChatColor.DARK_GRAY + (Math.round((double) user.getExperience() / experience * 100)) + "%");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
        }
        if(cmd.getName().equalsIgnoreCase("setlevel")) {
            if(player.hasPermission("*")) {
                Player target = Bukkit.getPlayer(args[0]);
                int level = Integer.parseInt(args[1]);
                User user = Products.getInstance().getUserManager().getUser(target.getUniqueId());
                user.setLevel(level);
                player.sendMessage(Products.getPrefix() + "Set " + target.getName() + "'s Level to " + level);
            }
        }
        if(cmd.getName().equalsIgnoreCase("setexperience")) {
            if(player.hasPermission("*")) {
                Player target = Bukkit.getPlayer(args[0]);
                int experience = Integer.parseInt(args[1]);
                User user = Products.getInstance().getUserManager().getUser(target.getUniqueId());
                user.setExperience(experience);
                player.sendMessage(Products.getPrefix() + "Set " + target.getName() + "'s Experience to " + experience);
            }
        }
        if(cmd.getName().equalsIgnoreCase("leveltop")) {
            if(args.length > 0 && player.hasPermission("admin") && args[0].equalsIgnoreCase("update")) {
                LeaderboardUpdater.updateLevelLeaderboard();
                return true;
            }
            player.sendMessage("");
            player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Top Levels ✫");
            player.sendMessage("");

            LinkedHashMap<UUID, Integer> topLevels = Products.getInstance().topLevels;
            int index = 1;

            for(UUID top : topLevels.keySet()) {
                Player topPlayer = Bukkit.getPlayer(top);
                player.sendMessage(ChatColor.YELLOW + "#" + index + " " + (topPlayer == null ? ChatColor.RED + Bukkit.getOfflinePlayer(top).getName() : ChatColor.GREEN + topPlayer.getName()) + ChatColor.YELLOW + " " + topLevels.get(top) + "✫");
                index++;
            }
            player.sendMessage("");
            player.sendMessage(ChatColor.DARK_GRAY + "Leaderboards update every 1 hour");
        }
        return true;
    }

}
