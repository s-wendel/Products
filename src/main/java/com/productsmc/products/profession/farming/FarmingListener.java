package com.productsmc.products.profession.farming;

import com.productsmc.products.Products;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Mat;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FarmingListener implements Listener {

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() != GameMode.SURVIVAL)
            return;



        if(event.getBlock().getBlockData() instanceof Ageable) {
            Ageable crop = (Ageable) event.getBlock().getBlockData();
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

            event.setCancelled(true);

            if(user.getLevel() < 8) {
                player.sendMessage(ChatColor.RED + "You must be Level 8 to Farm. Type '/quests' to level up!");
                return;
            }

            if(crop.getAge() == crop.getMaximumAge()) {
                List<Block> blocks = new ArrayList<>();
                blocks.add(event.getBlock());
                registerBlocks(player, blocks, false);
                for(Pet pet : user.getEquippedPets()) {
                    switch(pet) {
                        case CHICKEN:
                            Pet.CHICKEN.ability(player, null, event.getBlock().getLocation(), 0);
                            break;
                        case MERCHANT:
                            Pet.MERCHANT.ability(player, null,null, 0);
                            break;
                    }
                }
            } else {
                Material material = event.getBlock().getType();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 2f);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "+ " + ChatColor.YELLOW + "0♢"));
                if(!player.getWorld().getName().equals("ul_Spawn")) {
                    if(!player.getInventory().getItemInMainHand().getType().name().endsWith("HOE")) {
                        switch(material) {
                            case POTATOES:
                                Mat.POTATO.add(player.getUniqueId(), 1);
                                break;
                            case CARROTS:
                                Mat.CARROT.add(player.getUniqueId(), 1);
                                break;
                            case BEETROOTS:
                                Mat.BEETROOT_SEED.add(player.getUniqueId(), 1);
                                break;
                            case WHEAT:
                                Mat.WHEAT_SEED.add(player.getUniqueId(), 1);
                                break;
                            case NETHER_WART:
                                Mat.NETHER_WART.add(player.getUniqueId(), 1);
                                break;
                            default:
                                break;
                        }
                        event.getBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }

    public static void registerBlocks(Player player, List<Block> blocks, boolean ability) {
        int total = 0;
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

        int crops = 0;
        int potatoes = 0, carrots = 0, beetroot = 0, wheat = 0, wart = 0;

        for(Block block : blocks) {
            Material material = block.getType();

            boolean stop = true;
            int shards = 0;
            switch(material) {
                case POTATOES:

                    crops++;
                    stop = false;
                    shards = ThreadLocalRandom.current().nextInt(1, 3);
                    Mat.POTATO.add(player.getUniqueId(), 1);

                    potatoes++;
                    
                    break;
                case CARROTS:

                    crops++;
                    stop = false;
                    shards = ThreadLocalRandom.current().nextInt(1, 3);
                    Mat.CARROT.add(player.getUniqueId(), 1);

                    carrots++;

                    break;
                case BEETROOTS:

                    crops++;
                    stop = false;
                    shards = ThreadLocalRandom.current().nextInt(1, 4);
                    Mat.BEETROOT.add(player.getUniqueId(), 1);

                    beetroot++;

                    break;
                case WHEAT:

                    crops++;
                    stop = false;
                    shards = ThreadLocalRandom.current().nextInt(2, 5);
                    Mat.WHEAT_SEED.add(player.getUniqueId(), 1);
                    Mat.WHEAT.add(player.getUniqueId(), 1);

                    wheat++;

                    break;
                case NETHER_WART:

                    crops++;
                    stop = false;
                    shards = ThreadLocalRandom.current().nextInt(1, 6);
                    Mat.NETHER_WART.add(player.getUniqueId(), 1);

                    wart++;

                    break;
                default:
                    break;
            }
            if(stop)
                continue;
            if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
                String name = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                if(name.equals("Farmer's Tool")) {
                    shards++;
                } else {
                    Item tool = Item.of(name);
                    if(tool != null) {
                        tool.ability(player, block.getLocation());
                    }
                }
            }
            total += shards;
            user.setShards(user.getShards()+shards);
            block.setType(block.getType());
        }

        if(!player.getWorld().getName().equals("ul_Spawn")) {

            user.setCropsAtPlot(user.getCropsAtPlot() + crops);

            for(String roman : ProductsUtil.getRomanNumerals()) {
                Quest quest = Quest.valueOf("PLOT_FARMING_" + roman);
                if(!user.getCompletedQuests().contains(quest) && user.getCropsAtPlot() >= quest.getAmount()) {
                    quest.finishQuest(player);
                }
            }
        }

        for(String roman : ProductsUtil.getRomanNumerals()) {
            Quest quest = Quest.valueOf("POTATO_HARVESTER_" + roman);
            if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.POTATO) >= quest.getAmount()) {
                quest.finishQuest(player);
            }
        }

        for(String roman : ProductsUtil.getRomanNumerals()) {
            Quest quest = Quest.valueOf("CARROT_HARVESTER_" + roman);
            if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.CARROT) >= quest.getAmount()) {
                quest.finishQuest(player);
            }
        }

        for(String roman : ProductsUtil.getRomanNumerals()) {
            Quest quest = Quest.valueOf("BEETROOT_HARVESTER_" + roman);
            if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.BEETROOT) >= quest.getAmount()) {
                quest.finishQuest(player);
            }
        }

        for(String roman : ProductsUtil.getRomanNumerals()) {
            Quest quest = Quest.valueOf("WHEAT_HARVESTER_" + roman);
            if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.WHEAT) >= quest.getAmount()) {
                quest.finishQuest(player);
            }
        }

        for(String roman : ProductsUtil.getRomanNumerals()) {
            Quest quest = Quest.valueOf("WART_HARVESTER_" + roman);
            if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.NETHER_WART) >= quest.getAmount()) {
                quest.finishQuest(player);
            }
        }

        FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
        DailyQuest daily = DailyQuest.CROPS;
        Map<DailyQuest, Integer> dailies = user.getDailies();
        int progress = -1;

        if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

            progress = dailies.get(daily);

            if(progress + crops >= daily.getAmount(config.getInt("daily.level"))) {

                daily.finishQuest(player);
                dailies.put(daily, -1);
                DailyQuest.checkCompletedDailies(player);

            } else {
                dailies.put(daily, progress + crops);
            }

        }

        if(total > 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 2f);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "+ " + ChatColor.YELLOW + total + "♢"));
        }
    }

}
