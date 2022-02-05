package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("admin")) {
            return true;
        }
        if(cmd.getName().equalsIgnoreCase("test")) {

        }
        return true;
    }

}