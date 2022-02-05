package com.productsmc.products.command;

import com.productsmc.products.Products;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("discord")) {
            TextComponent message = new TextComponent(Products.getPrefix() + ChatColor.YELLOW + "Click here" + ChatColor.WHITE + " to join the Discord" );
            message.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://discord.gg/tgqrEcdDZ5" ) );
            message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Join the Discord!" ).create() ) );
            player.spigot().sendMessage(message);
            player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "(Remember to link your Discord account using /link for free stuff!)");
        }
        return true;
    }

}
