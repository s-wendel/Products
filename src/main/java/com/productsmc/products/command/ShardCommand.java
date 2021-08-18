package com.productsmc.products.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import products.Products;
import products.user.User;
import products.util.ProductsUtil;

public class ShardCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("shards")) {
			User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
			player.sendMessage(Products.getPrefix() + "You have " + ChatColor.YELLOW + ProductsUtil.format(user.getShards()) + "♢");
			player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
		}
		return true;
	}
	
}
