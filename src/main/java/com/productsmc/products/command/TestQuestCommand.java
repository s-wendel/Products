package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestQuestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!sender.hasPermission("admin")) {
            return true;
        }
        User user = Products.getInstance().getUserManager().getUser(Bukkit.getPlayer(args[0]).getUniqueId());
        if(cmd.getName().equalsIgnoreCase("checkquests")) {
            int xp = 0;
            for(Quest quest : user.getCompletedQuests()) {
                xp += quest.getExperience();
            }
            sender.sendMessage("User should have " + xp + " xp.");
        }
        return true;
    }
}
