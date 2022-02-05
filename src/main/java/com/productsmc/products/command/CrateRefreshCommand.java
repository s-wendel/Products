package com.productsmc.products.command;

import com.productsmc.products.Products;
import com.productsmc.products.crate.CrateListener;
import com.productsmc.products.item.Item;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrateRefreshCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("admin")) {
            return true;
        }
        if(cmd.getName().equalsIgnoreCase("refreshcrates")) {
            CrateListener.rotateItems(false);
        }
        return true;
    }

}
