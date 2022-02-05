package com.productsmc.products.quest;

import com.productsmc.products.Products;
import com.productsmc.products.forging.ForgingCategory;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Mat;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.util.InventoryBuilder;
import com.productsmc.products.util.ItemBuilder;
import com.productsmc.products.util.ItemButton;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class QuestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        if (cmd.getName().equalsIgnoreCase("quests")) {
            if(args.length == 0) {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
                InventoryBuilder categories = new InventoryBuilder(5, "Quests");

                FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
                int index = 11;
                Map<DailyQuest, Integer> dailies = user.getDailies();

                for(DailyQuest quest : dailies.keySet()) {

                    int progress = dailies.get(quest);
                    long amount = quest.getAmount(config.getInt("daily.level"));

                    if(progress >= amount || progress == -1) {
                        categories.setItemButton(index, new ItemButton(new ItemBuilder(Material.KNOWLEDGE_BOOK)
                                .setName(ChatColor.YELLOW + quest.getName() + " Completed")
                                .setLore(ChatColor.GRAY + "This quest has been completed!",
                                        "",
                                        ChatColor.YELLOW + "Resets in " + ProductsUtil.toTime(86400000 + config.getLong("daily.reset") - System.currentTimeMillis()))
                                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                                .toItemStack(),
                                event -> {
                                    event.setCancelled(true);
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                }));
                    } else {
                        categories.setItemButton(index, new ItemButton(new ItemBuilder(Material.BOOK)
                                .setName(ChatColor.YELLOW + quest.getName())
                                .setLore(ChatColor.GRAY + quest.getDescription() + " " + ChatColor.YELLOW + progress + "/" + amount + ChatColor.DARK_GRAY + " " + (Math.round((double) progress / amount * 100)) + "%",
                                        "",
                                        ChatColor.DARK_GRAY + "> " + quest.getItem().getRarity().getColor() + "1x " + quest.getItem().getName(),
                                        ChatColor.DARK_GRAY + "> " + ChatColor.GOLD + quest.getExperience(config.getInt("daily.level")) + "x Experience",
                                        "",
                                        ChatColor.DARK_GRAY + "Rewards are received on quest completion",
                                        "",
                                        ChatColor.YELLOW + "Resets in " + ProductsUtil.toTime(86400000 + config.getLong("daily.reset") - System.currentTimeMillis()))
                                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                                .toItemStack(),
                                event -> {
                                    event.setCancelled(true);
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                }));
                    }

                    index++;

                }

                for(index = index; index < 16; index++) {

                    categories.setItemButton(index, new ItemButton(new ItemBuilder(Material.BARRIER)
                            .setName(ChatColor.YELLOW + "Daily Quests Disabled")
                            .setLore(ChatColor.GRAY + "Dailies are temporarily disabled",
                                    ChatColor.GRAY + "due to a major bug.")
                            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                            .toItemStack(),
                            event -> {
                                event.setCancelled(true);
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            }));

                }

                /*for(index = index; index < 16; index++) {

                    categories.setItemButton(index, new ItemButton(new ItemBuilder(Material.BARRIER)
                            .setName(ChatColor.YELLOW + "Locked Daily Quest")
                            .setLore(ChatColor.GRAY + "More Dailies are unlocked at",
                                    ChatColor.GREEN + "Level 15 " + ChatColor.GRAY +  "&" + ChatColor.GREEN + " Level 75")
                            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                            .toItemStack(),
                            event -> {
                                event.setCancelled(true);
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            }));

                }*/

                for(QuestCategory category : QuestCategory.values()) {
                    int completed = user.getQuestAmount(category);
                    int total = Quest.getQuestAmount(category);

                    if(user.getLevel() >= category.getLevel()) {

                        categories.setItemButton(category.getSlot(), new ItemButton(
                                new ItemBuilder(category.getIcon())
                                        .setName(ChatColor.YELLOW + ProductsUtil.toProperCase(category.toString()))
                                        .setLore(ChatColor.GRAY + "Quests Completed " + ChatColor.YELLOW + completed + "/" + total + ChatColor.DARK_GRAY + " " + (Math.round((double) completed/total*100)) + "%",
                                                "",
                                                ChatColor.DARK_GRAY + "Left-Click to view")
                                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                                        .toItemStack(),
                                event -> {
                                    questCategory(player, category);
                                }
                        ));

                    } else {

                        categories.setItemButton(category.getSlot(), new ItemButton(
                                new ItemBuilder(Material.BARRIER)
                                        .setName(ChatColor.YELLOW + ProductsUtil.toProperCase(category.toString()))
                                        .setLore(ChatColor.GRAY + "Unlock this Quest Category",
                                                ChatColor.GRAY + "at " + ChatColor.YELLOW + "Level " + category.getLevel() + ChatColor.GRAY + "!")
                                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                                        .toItemStack(),
                                event -> {
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                }
                        ));

                    }
                }
                player.openInventory(categories.getInventory());
                return true;
            }
            try {
                QuestCategory category = QuestCategory.valueOf(args[0].toUpperCase());
                if(user.getLevel() >= category.getLevel()) {
                    questCategory(player, category);
                } else {
                    player.sendMessage(Products.getPrefix() + "You unlock " + ChatColor.YELLOW + ProductsUtil.toProperCase(category.toString()) + ChatColor.GRAY + " Quests at" + ChatColor.YELLOW + "Level " + category.getLevel() + ChatColor.GRAY + "!");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                }
            } catch(Exception exception) {
                player.sendMessage(Products.getPrefix() + "/quests <category>");
            }
        }
        return true;
    }

    public void questCategory(Player player, QuestCategory category) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
        InventoryBuilder builder = new InventoryBuilder(5, "Quests > " + ProductsUtil.toProperCase(category.toString()));
        List<Quest> quests = Quest.getQuests(category);
        int index = 0;
        ItemStack[] items = new ItemStack[quests.size()];
        for(Quest quest : quests) {
            if(!user.getCompletedQuests().contains(quest)) {
                int progress = quest.getPlayerProgress(player);
                int amount = quest.getAmount();
                items[index] = new ItemButton(new ItemBuilder(quest.getIcon())
                        .setName(ChatColor.YELLOW + quest.getName())
                        .setLore(ChatColor.GRAY + quest.getDescription() + " " + ChatColor.YELLOW + progress + "/" + amount + ChatColor.DARK_GRAY + " " + (Math.round((double) progress / amount * 100)) + "%",
                                "",
                                ChatColor.DARK_GRAY + "> " + quest.getItem().getRarity().getColor() + "1x " + quest.getItem().getName(),
                                ChatColor.DARK_GRAY + "> " + ChatColor.GOLD + quest.getExperience() + "x Experience",
                                "",
                                ChatColor.DARK_GRAY + "Rewards are received on quest completion")
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .toItemStack(), event -> {
                            event.setCancelled(true);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        }).getItem();

                index++;
            }
        }
        builder.addPagination(items, new int[] { 0, 35 });
        builder.setItemButton(40, new ItemButton(
                new ItemBuilder(Material.OAK_SIGN)
                        .setName(ChatColor.YELLOW + "Go Back")
                        .setLore(ChatColor.GRAY + "Click to go back")
                        .toItemStack(),
                clicked -> {
                    player.performCommand("quests");
                }
        ));

        builder.setItemButton(37, new ItemButton(
                new ItemBuilder(Material.ARROW)
                        .setName(ChatColor.YELLOW + "Previous Page")
                        .setLore(ChatColor.GRAY + "Click to go to the previous page")
                        .toItemStack(),
                clicked -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
                    player.openInventory(builder.getPreviousPage().getInventory());
                }
        ));

        builder.setItemButton(43, new ItemButton(
                new ItemBuilder(Material.ARROW)
                        .setName(ChatColor.YELLOW + "Next Page")
                        .setLore(ChatColor.GRAY + "Click to go to the next page")
                        .toItemStack(),
                clicked -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
                    player.openInventory(builder.getNextPage().getInventory());
                }
        ));

        player.openInventory(builder.getPage(0).getInventory());
    }

}
