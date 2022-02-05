package com.productsmc.products.scrapper;

import com.productsmc.products.Products;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Mat;
import com.productsmc.products.item.Rarity;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class ScrapCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("scrap")) {
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            if(user.scrapConfirmation) {
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.58f, 1.25f);

                ItemStack itemStack = player.getInventory().getItemInMainHand();
                int amount = itemStack.getAmount();

                if(itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName()) {
                    player.sendMessage(Products.getPrefix() + "This item is not scrapable.");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 1f);
                    return true;
                }

                Item item = Item.of(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()));

                if(item == null || item.getName().endsWith("Generator")) {
                    player.sendMessage(Products.getPrefix() + "This item is not scrapable.");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 1f);
                    return true;
                }

                player.getInventory().removeItem(itemStack);

                player.sendMessage("");
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Scrap Complete");
                player.sendMessage("");
                player.sendMessage("You have received the following items:");
                player.sendMessage("");

                if(!item.getRawMats().equals("UNFORGABLE")) {
                    for(Mat mat : item.getMats().keySet()) {
                        if(mat != Mat.SHARDS) {
                            ItemStack matItem = mat.getItem();
                            matItem.setAmount(item.getMats().get(mat) / 3 * amount);
                            player.getInventory().addItem(matItem);
                            player.sendMessage(ChatColor.DARK_GRAY + "> " + mat.getRarity().getColor() + (item.getMats().get(mat) / 3 * amount) + "x " + ProductsUtil.toProperCase(mat.name()));
                        } else {
                            user.setShards(user.getShards() + (item.getMats().get(mat) / 3 * amount));
                            player.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + (item.getMats().get(mat) / 3 * amount) + "♢");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + (100 * amount) + "♢");
                    user.setShards(user.getShards() + 100 * amount);
                }

                user.setItemsScrapped(user.getItemsScrapped() + amount);
                for(String roman : ProductsUtil.getRomanNumerals()) {
                    Quest quest = Quest.valueOf("BIG_HAUL_" + roman);
                    if(!user.getCompletedQuests().contains(quest) && user.getItemsScrapped() >= quest.getAmount()) {
                        quest.finishQuest(player);
                    }
                }

                FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
                DailyQuest daily = DailyQuest.SCRAP;
                Map<DailyQuest, Integer> dailies = user.getDailies();
                int progress = -1;

                if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

                    progress = dailies.get(daily);

                    if(progress + 1 >= daily.getAmount(config.getInt("daily.level"))) {

                        daily.finishQuest(player);
                        dailies.put(daily, -1);
                        DailyQuest.checkCompletedDailies(player);

                    } else {
                        dailies.put(daily, progress + 1);
                    }


                }

                Rarity rarity = item.getRarity();

                if(rarity == Rarity.RARE || rarity == Rarity.EPIC || rarity == Rarity.LEGENDARY) {
                    user.setRareItemsScrapped(user.getRareItemsScrapped() + amount);
                    for(String roman : ProductsUtil.getRomanNumerals()) {
                        Quest quest = Quest.valueOf("INVALUABLE_WASTE_" + roman);
                        if(!user.getCompletedQuests().contains(quest) && user.getRareItemsScrapped() >= quest.getAmount()) {
                            quest.finishQuest(player);
                        }
                    }
                }

                player.sendMessage("");
                user.scrapConfirmation = false;

            } else {
                ItemStack itemStack = player.getInventory().getItemInMainHand();

                if(itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName()) {
                    player.sendMessage(Products.getPrefix() + "This item is not scrapable.");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 1f);
                    return true;
                }

                Item item = Item.of(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()));
                int amount = itemStack.getAmount();

                if(item == null || item.getName().endsWith("Generator")) {
                    player.sendMessage(Products.getPrefix() + "This item is not scrapable.");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 1f);
                    return true;
                }

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.25f, 1f);

                player.sendMessage("");
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Scrap Confirmation");
                player.sendMessage("");
                player.sendMessage("You will not receive your item back. Type '/scrap' again for the following");
                player.sendMessage("");

                if(!item.getRawMats().equals("UNFORGABLE")) {
                    for(Mat mat : item.getMats().keySet()) {
                        if(mat != Mat.SHARDS) {
                            player.sendMessage(ChatColor.DARK_GRAY + "> " + mat.getRarity().getColor() + (item.getMats().get(mat) / 3 * amount) + "x " + ProductsUtil.toProperCase(mat.name()));
                        } else {
                            player.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + (item.getMats().get(mat) / 3 * amount) + "♢");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + (100 * amount) + "♢");
                }

                player.sendMessage("");

                user.scrapConfirmation = true;

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(user.scrapConfirmation) {
                            user.scrapConfirmation = false;
                        }
                    }
                }.runTaskLater(Products.getInstance(), 300);
            }
        }
        return true;
    }

}
