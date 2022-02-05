package com.productsmc.products.bot;

import com.productsmc.products.Products;
import com.productsmc.products.trail.Trail;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.UUID;

public class RegisterPlayerListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();

        if(message.startsWith("$link")) {
            try {
                String[] split = message.split(" ");
                for(UUID player : Products.getInstance().botTokens.keySet()) {
                    if(split[1].equals(Products.getInstance().botTokens.get(player))) {

                        try {

                            User user = Products.getInstance().getUserManager().getUser(player);

                            if(user.getDiscordId() != null) {

                                EmbedBuilder builder = new EmbedBuilder();

                                builder.setTitle("❌ Discord Linked ❌");
                                builder.setColor(new Color(50, 158, 168));

                                builder.addField("Link Failure", "You have already linked your account!", false);

                                event.getChannel().sendMessageEmbeds(builder.build()).queue();

                                return;
                            }

                            new BukkitRunnable() {

                                @Override
                                public void run() {

                                    user.setDiscordId(event.getAuthor().getId());

                                    Player onlinePlayer = Bukkit.getPlayer(player);

                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + onlinePlayer.getName() + " permission set " + Trail.DISCORD.getPermission());
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + onlinePlayer.getName() + " parent add amber");


                                    /** REMOVE  LATER!! **/
                                    user.setSlots(user.getSlots() + 10);
                                    user.setMultiplier(user.getMultiplier() + 0.1);

                                    onlinePlayer.sendMessage(Products.getPrefix() + "You have received 1x Discord Trail & 1x Amber Rank for verifying your Discord!");
                                    onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5f, 1.5f);

                                    EmbedBuilder builder = new EmbedBuilder();
                                    builder.setTitle("✔ Discord Linked ✔");
                                    builder.setColor(new Color(50, 158, 168));
                                    builder.addField("Link Successful", "You have received your rewards!", false);
                                    event.getChannel().sendMessageEmbeds(builder.build()).queue();

                                    event.getGuild().loadMembers();

                                    Member member = event.getMember();

                                    member.modifyNickname("[✔] " + onlinePlayer.getName()).complete();

                                    if(onlinePlayer.hasPermission("products")) {
                                        event.getGuild().addRoleToMember(member, event.getGuild().getRoleById("784675071863685151")).complete();
                                    } else if(onlinePlayer.hasPermission("opal")) {
                                        event.getGuild().addRoleToMember(member, event.getGuild().getRoleById("784222781851566080")).complete();
                                    } else if(onlinePlayer.hasPermission("sapphire")) {
                                        event.getGuild().addRoleToMember(member, event.getGuild().getRoleById("784222782451220510")).complete();
                                    } else if(onlinePlayer.hasPermission("ruby")) {
                                        event.getGuild().addRoleToMember(member, event.getGuild().getRoleById("784222782878777397")).complete();
                                    } else if(onlinePlayer.hasPermission("topaz")) {
                                        event.getGuild().addRoleToMember(member, event.getGuild().getRoleById("784222784682590222")).complete();
                                    } else if(onlinePlayer.hasPermission("amber")) {
                                        event.getGuild().addRoleToMember(member, event.getGuild().getRoleById("784222784808943638")).complete();
                                    }

                                    event.getGuild().addRoleToMember(member, event.getGuild().getRoleById("916867757096722442")).complete();





                                }

                            }.runTaskLater(Products.getInstance(), 1);

                            return;

                        } catch(Exception e) {

                            EmbedBuilder builder = new EmbedBuilder();

                            builder.setTitle("❌ Discord Linked ❌");
                            builder.setColor(new Color(50, 158, 168));

                            builder.addField("Link Failure", "You must be online to receive rewards", false);

                            event.getChannel().sendMessageEmbeds(builder.build()).queue();

                            return;

                        }

                    }

                }

                EmbedBuilder builder = new EmbedBuilder();

                builder.setTitle("❌ Discord Linked ❌");
                builder.setColor(new Color(50, 158, 168));

                builder.addField("Link Failure", "Code does not match any available codes", false);

                event.getChannel().sendMessageEmbeds(builder.build()).queue();

           } catch(Exception e) {

                EmbedBuilder builder = new EmbedBuilder();

                builder.setTitle("❌ Discord Linked ❌");
                builder.setColor(new Color(50, 158, 168));

                builder.addField("Link Failure", "Syntax: $link <code>", false);

                event.getChannel().sendMessageEmbeds(builder.build()).queue();

            }

        }

    }

}
