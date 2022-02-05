package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MultiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!sender.hasPermission("admin")) {
            return true;
        }
        User user = Products.getInstance().getUserManager().getUser(Bukkit.getPlayer(args[0]).getUniqueId());
        if(cmd.getName().equalsIgnoreCase("addmulti")) {
            user.setMultiplier(user.getMultiplier() + Double.parseDouble(args[1]));
        }
        if(cmd.getName().equalsIgnoreCase("setmulti")) {
            user.setMultiplier(Double.parseDouble(args[1]));
        }
        return true;
    }

}
