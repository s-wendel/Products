package com.productsmc.products.crate;

import com.productsmc.products.Products;
import com.productsmc.products.generator.Generator;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Rarity;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.util.InventoryBuilder;
import com.productsmc.products.util.ItemBuilder;
import com.productsmc.products.util.ItemButton;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;

public class CrateListener implements Listener {

    Random random = new Random();

    @EventHandler
    public void cratePreview(PlayerInteractEvent event) {
        if(event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock().getType().name().endsWith("GLASS") && event.getClickedBlock().getLocation().getWorld().getName().equals("ul_Spawn")) {
            Location location = event.getClickedBlock().getLocation();
            for(Crate crate : Products.getInstance().getCrateManager().getCrates().values()) {
                if(location.getBlockX() == crate.getLocation().getBlockX() && location.getBlockY() == crate.getLocation().getBlockY() && location.getBlockZ() == crate.getLocation().getBlockZ()) {
                    InventoryBuilder builder = new InventoryBuilder(5, crate.getName() + " Crate");
                    event.setCancelled(true);
                    rotateItems(true);
                    Item rotational = crate.getRotational();
                    builder.setItemButton(10, new ItemButton(
                            new ItemBuilder(rotational.getItem())
                                    .setLore("",
                                            ChatColor.GRAY + "Chance: " + ChatColor.YELLOW + "2%",
                                            ChatColor.YELLOW + "Refreshes in " + ProductsUtil.toTime(Products.getInstance().getConfig().getLong("rotationals") - System.currentTimeMillis()))
                                    .toItemStack(),
                            click -> {
                                click.setCancelled(true);
                                Player player = (Player) click.getWhoClicked();
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            }));
                    int i = 11;
                    for(Item item : crate.getItems()) {
                        builder.setItemButton(i, new ItemButton(
                                new ItemBuilder(item.getItem())
                                        .setLore("",
                                                ChatColor.GRAY + "Chance: " + ChatColor.YELLOW + Math.round(98 / crate.getItems().size()) + "%")
                                        .toItemStack(),
                                click -> {
                                    click.setCancelled(true);
                                    Player player = (Player) click.getWhoClicked();
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                }));
                        i = i % 9 == 7 ? i + 3 : i + 1;
                    }
                    event.getPlayer().openInventory(builder.getInventory());
                    event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_CHEST_OPEN, 1f, 0.75f);
                }
            }
        }
    }

    @EventHandler
    public void crateOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().name().endsWith("GLASS") && event.getClickedBlock().getLocation().getWorld().getName().equals("ul_Spawn")) {
            Location location = event.getClickedBlock().getLocation();
            for (Crate crate : Products.getInstance().getCrateManager().getCrates().values()) {
                if (location.getBlockX() == crate.getLocation().getBlockX() && location.getBlockY() == crate.getLocation().getBlockY() && location.getBlockZ() == crate.getLocation().getBlockZ()) {
                    if (event.getHand() != EquipmentSlot.HAND) {
                        return;
                    }
                    event.setCancelled(true);
                    rotateItems(true);
                    ItemStack item = player.getInventory().getItemInMainHand();
                    User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
                    if(item.isSimilar(crate.getKey())) {

                        FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
                        DailyQuest daily = DailyQuest.CRATES;
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

                        item.setAmount(item.getAmount() - 1);
                        int number = random.nextInt(101);
                        int stealChance = 0;
                        if (ProductsUtil.hasArmorSet(player, "Rogue")) {
                            stealChance += 4;
                        }
                        for(Pet pet : user.getEquippedPets()) {
                            switch (pet) {
                                case THIEF:
                                    stealChance += 2;
                                    break;
                            }
                        }
                        if(number <= 1) {
                            giveReward(player, crate.getRotational(), false);
                            if(stealChance > 0) {
                                if(random.nextInt(101) <= stealChance) {
                                    giveReward(player, crate.getRotational(), true);
                                    user.setStolenItems(user.getStolenItems() + 1);
                                    for(String roman : ProductsUtil.getRomanNumerals()) {
                                        Quest quest = Quest.valueOf("THIEF_" + roman);
                                        if(!user.getCompletedQuests().contains(quest) && user.getStolenItems() >= quest.getAmount()) {
                                            quest.finishQuest(player);
                                        }

                                        daily = DailyQuest.THIEF;

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

                                    }
                                }
                            }
                            user.setCratesOpened(user.getCratesOpened() + 1);
                            for(String roman : ProductsUtil.getRomanNumerals()) {
                                Quest quest = Quest.valueOf("CRATE_OPENER_" + roman);
                                if(!user.getCompletedQuests().contains(quest) && user.getCratesOpened() >= quest.getAmount()) {
                                    quest.finishQuest(player);
                                }
                            }
                        } else {
                            Item reward = crate.getItems().get(random.nextInt(crate.getItems().size()));
                            if(reward.getRarity() == Rarity.LEGENDARY) {
                                user.setLegendaryCrateItems(user.getLegendaryCrateItems() + 1);
                                for(String roman : ProductsUtil.getRomanNumerals()) {
                                    Quest quest = Quest.valueOf("LEGENDARY_ITEM_" + roman);
                                    if(!user.getCompletedQuests().contains(quest) && user.getLegendaryCrateItems() >= quest.getAmount()) {
                                        quest.finishQuest(player);
                                    }
                                }
                            }
                            user.setCratesOpened(user.getCratesOpened() + 1);
                            for(String roman : ProductsUtil.getRomanNumerals()) {
                                Quest quest = Quest.valueOf("CRATE_OPENER_" + roman);
                                if(!user.getCompletedQuests().contains(quest) && user.getCratesOpened() >= quest.getAmount()) {
                                    quest.finishQuest(player);
                                }
                            }
                            giveReward(player, reward, false);
                            if(stealChance > 0) {
                                if(random.nextInt(101) <= stealChance) {
                                    giveReward(player, reward, true);
                                    user.setStolenItems(user.getStolenItems() + 1);
                                    for(String roman : ProductsUtil.getRomanNumerals()) {
                                        Quest quest = Quest.valueOf("THIEF_" + roman);
                                        if(!user.getCompletedQuests().contains(quest) && user.getStolenItems() >= quest.getAmount()) {
                                            quest.finishQuest(player);
                                        }

                                        daily = DailyQuest.THIEF;

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

                                    }
                                }
                            }
                        }
                        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1.25f);
                    } else {
                        player.sendMessage(Products.getPrefix()+ "You have to hold the correct crate key!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                    }
                    break;
                }
            }
        }
    }

    public void giveReward(Player player, Item item, boolean stole) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        switch(item) {
            case COAL_GENERATOR:
                player.getInventory().addItem(Generator.COAL.getGenerator(1));
                break;
            case IRON_GENERATOR:
                player.getInventory().addItem(Generator.IRON.getGenerator(1));
                break;
            case COPPER_GENERATOR:
                player.getInventory().addItem(Generator.COPPER.getGenerator(1));
                break;
            case GOLD_GENERATOR:
                player.getInventory().addItem(Generator.GOLD.getGenerator(1));
                break;
            case REDSTONE_GENERATOR:
                player.getInventory().addItem(Generator.REDSTONE.getGenerator(1));
                break;
            case LAPIS_GENERATOR:
                player.getInventory().addItem(Generator.LAPIS.getGenerator(1));
                break;
            case DIAMOND_GENERATOR:
                player.getInventory().addItem(Generator.DIAMOND.getGenerator(1));
                break;
            case EMERALD_GENERATOR:
                player.getInventory().addItem(Generator.EMERALD.getGenerator(1));
                break;
            case NETHERITE_GENERATOR:
                player.getInventory().addItem(Generator.NETHERITE.getGenerator(1));
                break;
            case OBSIDIAN_GENERATOR:
                player.getInventory().addItem(Generator.OBSIDIAN.getGenerator(1));
                break;
            case GLOWSTONE_GENERATOR:
                player.getInventory().addItem(Generator.GLOWSTONE.getGenerator(1));
                break;
            case MAGMA_GENERATOR:
                player.getInventory().addItem(Generator.MAGMA.getGenerator(1));
                break;
            case BLAZE_GENERATOR:
                player.getInventory().addItem(Generator.BLAZE.getGenerator(1));
                break;
            case ENDER_GENERATOR:
                player.getInventory().addItem(Generator.ENDER.getGenerator(1));
                break;
            case SHULKER_GENERATOR:
                player.getInventory().addItem(Generator.SHULKER.getGenerator(1));
                break;
            case GEN_SLOT:
                user.setSlots(user.getSlots() + 1);
                break;
            case TWO_GEN_SLOTS:
                user.setSlots(user.getSlots() + 2);
                break;
            case SMALL_MULTIPLIER:
                user.setMultiplier(user.getMultiplier() + 0.15);
                break;
            case PET_SLOT:
                user.setPetSlots(user.getPetSlots() + 1);
                break;
            case COMMON_PET:
                user.givePet(Pet.getRandom(Rarity.COMMON));
                break;
            case UNCOMMON_PET:
                user.givePet(Pet.getRandom(Rarity.UNCOMMON));
                break;
            case RARE_PET:
                user.givePet(Pet.getRandom(Rarity.RARE));
                break;
            case EPIC_PET:
                user.givePet(Pet.getRandom(Rarity.EPIC));
                break;
            case LEGENDARY_PET:
                user.givePet(Pet.getRandom(Rarity.LEGENDARY));
                break;
            default:
                player.getInventory().addItem(item.getItem());
                break;
        }
        player.sendMessage(Products.getPrefix() + "You won " + ChatColor.YELLOW + "1x " + item.getName() + ChatColor.WHITE + "! " + (stole ? ChatColor.RED + "(Stolen)" : ""));

    }

    public static void rotateItems(boolean need) {
        long millis = Products.getInstance().getConfig().getLong("rotationals");
        Random random = new Random();
        if(need) {
            if(System.currentTimeMillis() >= millis) {
                Products.getInstance().getConfig().set("rotationals", System.currentTimeMillis() + (3600000 - System.currentTimeMillis() % 3600000));
                Products.getInstance().saveConfig();
                Bukkit.broadcastMessage(Products.getPrefix() +  "Crate Rotationals have been reset!");
                for (Crate crate : Products.getInstance().getCrateManager().getCrates().values()) {
                    crate.setRotational(crate.getRotationals().get(random.nextInt(crate.getRotationals().size())));
                }
            }
        } else {
            Products.getInstance().saveConfig();
            Bukkit.broadcastMessage(Products.getPrefix() +  "Crate Rotationals have been reset!");
            for(Crate crate : Products.getInstance().getCrateManager().getCrates().values()) {
                crate.setRotational(crate.getRotationals().get(random.nextInt(crate.getRotationals().size())));
            }
        }
    }

}
