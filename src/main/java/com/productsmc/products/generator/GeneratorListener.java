package com.productsmc.products.generator;

import com.productsmc.products.Products;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GeneratorListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void placeGenerator(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item != null && player.getWorld().getName().equals("s2plot") && !event.isCancelled()) {
            Generator generator = Generator.of(item);
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            if(generator != null) {
                if(user.getGens().size() + 1 <= user.getSlots()) {
                    user.addGen(event.getBlock().getLocation());
                    player.sendMessage(Products.getPrefix() + "You placed 1x " + ProductsUtil.toProperCase(generator.toString()) + " Generator " + ChatColor.YELLOW + "(" + user.getGens().size() + "/" + user.getSlots() + ")");
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
                } else {
                    event.setCancelled(true);
                    player.sendMessage(Products.getPrefix() + "You don't have enough Gen Slots! (" + user.getGens().size() + "/" + user.getSlots() + ")");
                }
            }
        }
    }

    @EventHandler
    public void removeGenerator(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if(block == null || block.getType() == Material.AIR) {
                return;
            }
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            Generator generator = user.getGen(block.getLocation());
            if(generator != null) {
                user.removeGen(block.getLocation());
                block.setType(Material.AIR);
                player.sendMessage(Products.getPrefix() + "You removed 1x " + ProductsUtil.toProperCase(generator.toString()) + " Generator " + ChatColor.YELLOW + "(" + user.getGens().size() + "/" + user.getSlots() + ")");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
                player.getInventory().addItem(generator.getGenerator(1));
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getWorld().getName().equals("2")) {
            Material material = event.getBlock().getType();
            if(Generator.getMaterials().contains(material)) {
                event.setCancelled(true);
                player.sendMessage(Products.getPrefix() + "You can't break other people's Gens!");
            }
        }
    }

    @EventHandler
    public void upgradeGenerator(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && player.isSneaking() && event.getHand() == EquipmentSlot.HAND) {
            Block block = event.getClickedBlock();
            if(block.getType() == Material.RED_MUSHROOM_BLOCK) {
                player.sendMessage(Products.getPrefix() + "This generator is maxed. You cannot upgrade it any further");
                return;
            }
            Generator generator = Generator.of(block.getType());
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            if(generator != null && user.getGens().contains(block.getLocation())) {
                Generator next = generator.next(generator);
                if(next != null && user.getCoins() >= generator.getUpgradeCost()) {
                    user.setCoins(user.getCoins()-generator.getUpgradeCost());
                    block.setType(next.getBlock());
                    user.setGensUpgraded(user.getGensUpgraded() + 1);

                    FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
                    DailyQuest daily = DailyQuest.GENS;
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

                    for(String roman : ProductsUtil.getRomanNumerals()) {
                        Quest quest = Quest.valueOf("GENOISSEUR_" + roman);
                        if(!user.getCompletedQuests().contains(quest) && user.getGensUpgraded() >= quest.getAmount()) {
                            quest.finishQuest(player);
                        }
                    }
                    if(next == Generator.SHULKER) {
                        user.setShulkersUpgraded(user.getShulkersUpgraded() + 1);
                        for(String roman : ProductsUtil.getRomanNumerals()) {
                            Quest quest = Quest.valueOf("FLOATING_GENS_" + roman);
                            if(!user.getCompletedQuests().contains(quest) && user.getShulkersUpgraded() >= quest.getAmount()) {
                                quest.finishQuest(player);
                            }
                        }
                    }
                    player.sendMessage(Products.getPrefix() + "You upgraded 1x " + ProductsUtil.toProperCase(generator.toString()) + " Generator to " + ProductsUtil.toProperCase(next.toString()));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                } else {
                    player.sendMessage(Products.getPrefix() + "You need " + (ProductsUtil.format(generator.getUpgradeCost()-user.getCoins())) + "⛂ more to upgrade this!");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
                }
            }
        }
    }

}
