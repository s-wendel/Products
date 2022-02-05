package com.productsmc.products.command;

import com.productsmc.products.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!sender.hasPermission("admin")) {
            return true;
        }
        if(cmd.getName().equalsIgnoreCase("giveitem")) {
            if(args.length <= 2) {
                sender.sendMessage(ChatColor.RED + "/giveitem <player> <item> <amount>");
                return true;
            }
            ItemStack item = new ItemStack(Item.valueOf(args[1].toUpperCase()).getItem());
            item.setAmount(Integer.parseInt(args[2]));
            Player target = Bukkit.getPlayer(args[0]);
            target.getInventory().addItem(item);
        }
        return true;
    }

}
