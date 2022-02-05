package com.productsmc.products.bot;

import com.productsmc.products.Products;
import com.productsmc.products.util.ProductsUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.UUID;

public class CoinsTopListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getMessage().getContentRaw().equalsIgnoreCase("$baltop")) {
            EmbedBuilder eb = new EmbedBuilder();

            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("⛂ Coins Top ⛂");
            builder.setColor(new Color(50, 158, 168));

            LinkedHashMap<UUID, Long> topCoins = Products.getInstance().topCoins;
            int index = 1;

            for(UUID top : topCoins.keySet()) {

                OfflinePlayer topPlayer = Bukkit.getOfflinePlayer(top);
                builder.addField("#" + index + " " + topPlayer.getName(), ProductsUtil.format(topCoins.get(top)) + "⛂", false);
                index++;

            }

            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        }

    }

}
