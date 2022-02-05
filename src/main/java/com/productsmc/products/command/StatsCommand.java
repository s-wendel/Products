package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.level.Level;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        if(cmd.getName().equalsIgnoreCase("stats")) {
            Player player = null;
            if(args.length == 0) {
                player = (Player) sender;
            } else {
                try {
                    player = Bukkit.getPlayer(args[0]);
                } catch(Exception e) {}
            }
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            long experience = Level.getExperienceNeeded(user.getLevel() + 1);
            sender.sendMessage("");
            sender.sendMessage("      "  + ChatColor.YELLOW + ChatColor.BOLD + player.getName() + " Stats");
            sender.sendMessage("");
            sender.sendMessage("Coins: " + ChatColor.YELLOW + ProductsUtil.format(user.getCoins()) + "⛂");
            sender.sendMessage("Shards: " + ChatColor.YELLOW + ProductsUtil.format(user.getShards()) + "♢");
            sender.sendMessage("Level: " + ChatColor.YELLOW + user.getLevel() + "✫");
            sender.sendMessage("Experience: " + ChatColor.YELLOW + user.getExperience() + "/" + experience + " " + ChatColor.GRAY + (Math.round((double) user.getExperience() / experience * 100)) + "%");

            String pets = "";
            for(Pet pet : user.getEquippedPets()) {
                pets += pet.getRarity().getColor() + pet.getName() + ChatColor.WHITE + ", ";
            }
            int quests = Quest.values().length;

            sender.sendMessage("Pets: " + (pets.equals("") ? ChatColor.YELLOW + "None" : pets.substring(0, pets.length() - 2)));
            sender.sendMessage("Playtime: " + ChatColor.YELLOW + ProductsUtil.toTime(player.getStatistic(Statistic.PLAY_ONE_MINUTE) * 50));
            sender.sendMessage("Quests: " + ChatColor.YELLOW + user.getCompletedQuests().size() + "/" + quests + ChatColor.GRAY + " " + (Math.round((double) user.getCompletedQuests().size() / quests * 100)) + "%");
            sender.sendMessage("");

            return true;
        }
        return true;
    }

}
