package com.productsmc.products.leaderboard;

import com.productsmc.products.Products;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class LeaderboardUpdater {

    public static void updateCoinsLeaderboard() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            Products.getInstance().getUserManager().saveData(player.getUniqueId());
        }
        Bukkit.broadcastMessage(Products.getPrefix() + "Updating Coins Leaderboards...");
        File directory = new File(Products.getInstance().getDataFolder() + File.separator + "players");
        File[] files = directory.listFiles();
        List<UUID> players = new ArrayList<>();
        if(files != null) {
            for(File file : files) {
                players.add(UUID.fromString(file.getName().replace(".yml", "")));
            }
        }
        Collections.sort(players, new CoinsComparator());
        Products.getInstance().topCoins.clear();
        for(int i = 0; i < (files.length < 10 ? files.length : 10); i++) {
            UUID player = players.get(i);
            Products.getInstance().topCoins.put(player, new UserConfig(player).getConfig().getLong("player.coins"));
        }
        Bukkit.broadcastMessage(Products.getPrefix() + "Coins Leaderboards was updated!");
    }

   public static void updateLevelLeaderboard() {
       for(Player player : Bukkit.getOnlinePlayers()) {
           Products.getInstance().getUserManager().saveData(player.getUniqueId());
       }
       Bukkit.broadcastMessage(Products.getPrefix() + "Updating Level Leaderboards...");
       File directory = new File(Products.getInstance().getDataFolder() + File.separator + "players");
       File[] files = directory.listFiles();
       List<UUID> players = new ArrayList<>();
       if(files != null) {
           for(File file : files) {
               players.add(UUID.fromString(file.getName().replace(".yml", "")));
           }
       }
       Collections.sort(players, new LevelComparator());
       Products.getInstance().topLevels.clear();
       for(int i = 0; i < (files.length < 10 ? files.length : 10); i++) {
           UUID player = players.get(i);
           Products.getInstance().topLevels.put(player, new UserConfig(player).getConfig().getInt("player.level"));
       }
       Bukkit.broadcastMessage(Products.getPrefix() + "Level Leaderboards was updated!");
   }

}
