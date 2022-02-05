package com.productsmc.products.forging;

import com.productsmc.products.Products;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Mat;
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

public class ForgingCommand implements CommandExecutor {

    Random random = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

        if(cmd.getName().equalsIgnoreCase("forge")) {

            if(user.getLevel() < 4) {
                player.sendMessage(ChatColor.RED + "You unlock Forging at Level 4!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                return true;
            }

            HashMap<ForgingCategory, List<Item>> itemMap = new HashMap<>();
            for(Item item : Item.values()) {
                if(user.getLevel() >= item.getUnlock()) {
                    ForgingCategory category = item.getCategory();
                    List<Item> items = itemMap.containsKey(category) ? itemMap.get(category) : new ArrayList<>();
                    items.add(item);
                    itemMap.put(category, items);
                }
            }
            if(args.length == 0) {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
                InventoryBuilder categories = new InventoryBuilder(5, "Forging");
                for(ForgingCategory category : ForgingCategory.values()) {
                    String[] itemShowcase = new String[itemMap.get(category).size()+4];
                    int index = 2;
                    for(Item item : itemMap.get(category)) {
                        itemShowcase[index] = ChatColor.DARK_GRAY + "> " + item.getRarity().getColor() + item.getName();
                        index++;
                    }
                    itemShowcase[0] = ChatColor.GRAY + "Contains the following forgables:";
                    itemShowcase[1] = "";
                    itemShowcase[itemShowcase.length-2] = "";
                    itemShowcase[itemShowcase.length-1] = ChatColor.DARK_GRAY + "Left-Click to view";
                    categories.setItemButton(category.getSlot(), new ItemButton(
                            new ItemBuilder(category.getIcon())
                                    .setName(ChatColor.YELLOW + ProductsUtil.toProperCase(category.toString()))
                                    .setLore(itemShowcase)
                                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                                    .toItemStack(),
                            event -> {
                                forgeCategory(player, itemMap, category);
                            }
                    ));
                }
                player.openInventory(categories.getInventory());
                return true;
            }
            try {
                forgeCategory(player, itemMap, ForgingCategory.valueOf(args[0].toUpperCase()));
            } catch(Exception exception) {
                player.sendMessage(Products.getPrefix() + "/forge <category>");
            }
        }
        return true;
    }

    public void forgeCategory(Player player, HashMap<ForgingCategory, List<Item>> itemMap, ForgingCategory category) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
        InventoryBuilder builder = new InventoryBuilder(5, "Forging > " + ProductsUtil.toProperCase(category.toString()));
        int slot = 10;
        for(Item item : itemMap.get(category)) {
            if(slot % 9 == 8) {
                slot += 2;
            }
            int length = item.getDescription().length;
            List<String> description = new ArrayList(Arrays.asList(item.getDescription()));
            description.add("");
            int mats = 2;
            for(Mat mat : item.getMats().keySet()) {
                int amount = item.getMats().get(mat);
                if(mat == Mat.SHARDS) {
                    description.add(user.getShards() >= amount ? ChatColor.DARK_GREEN + "[✔] " + ChatColor.GREEN + amount + "♢" : ChatColor.DARK_RED + "[✘] " + ChatColor.RED + amount + "♢");
                } else {
                    description.add(player.getInventory().containsAtLeast(mat.getItem(), amount) ? ChatColor.DARK_GREEN + "[✔] " + ChatColor.GREEN + amount + "x " + ProductsUtil.toProperCase(mat.toString()) : ChatColor.DARK_RED + "[✘] " + ChatColor.RED + amount + "x " + ProductsUtil.toProperCase(mat.toString()));
                }
                mats++;
            }
            description.add("");
            description.add(ChatColor.DARK_GRAY + "Left-Click to forge");
            description.add("");
            description.add(item.getRarity().getLore());
            builder.setItemButton(slot, new ItemButton(
                    new ItemBuilder(item.getIcon())
                            .setName(item.getRarity().getColor() + item.getName())
                            .setLore(description.toArray(new String[description.size()]))
                            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                            .toItemStack(),
                    clicked -> {
                        clicked.setCancelled(true);
                        boolean go = true;
                        for(Mat mat : item.getMats().keySet()) {
                            int amount = item.getMats().get(mat);
                            if(mat == Mat.SHARDS) {
                                if(user.getShards() < amount) {
                                    go = false;
                                }
                            } else {
                                if(!player.getInventory().containsAtLeast(mat.getItem(), item.getMats().get(mat))) {
                                    go = false;
                                }
                            }
                        }
                        if(go) {
                            /*switch(item){
                                case SMALL_BAG_UPGRADE: case MEDIUM_BAG_UPGRADE: case LARGE_BAG_UPGRADE:
                                    //TODO
                                    break;*/
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
                                    for(Mat mat : item.getMats().keySet()) {
                                        int amount = item.getMats().get(mat);
                                        if(mat == Mat.SHARDS) {
                                            user.setShards(user.getShards() - amount);
                                        } else {
                                            ItemStack itemStack = mat.getItem();
                                            itemStack.setAmount(amount);
                                            player.getInventory().removeItem(itemStack);
                                        }
                                    }
                                    if(category == ForgingCategory.TROPHY) {
                                        if(item == Item.TROPHY_SMALL_MULTIPLIER) {
                                            user.setMultiplier(user.getMultiplier() + 0.2);
                                        } else if(item == Item.TROPHY_PARROT) {
                                            user.givePet(Pet.PARROT);
                                        } else if(item == Item.TROPHY_PET_SLOT) {
                                            user.setPetSlots(user.getPetSlots() + 1);
                                        } else {
                                            player.getInventory().addItem(item.getItem());
                                        }
                                    } else {
                                        player.getInventory().addItem(item.getItem());
                                    }
                                    player.sendMessage(Products.getPrefix() + "You forged 1x " + item.getName());
                                    user.setItemsForged(user.getItemsForged() + 1);
                                    if(stealChance > 0) {
                                        if(random.nextInt(101) <= stealChance) {
                                            player.getInventory().addItem(item.getItem());
                                            player.sendMessage(Products.getPrefix() + "You forged 1x " + item.getName() + ChatColor.RED + " (Stolen)");
                                            user.setStolenItems(user.getStolenItems() + 1);
                                            for(String roman : ProductsUtil.getRomanNumerals()) {
                                                Quest quest = Quest.valueOf("THIEF_" + roman);
                                                if(!user.getCompletedQuests().contains(quest) && user.getStolenItems() >= quest.getAmount()) {
                                                    quest.finishQuest(player);
                                                }

                                                FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
                                                DailyQuest daily = DailyQuest.THIEF;
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

                                            }
                                        }
                                    }
                                    for(String roman : ProductsUtil.getRomanNumerals()) {
                                        Quest quest = Quest.valueOf("ITEM_FORGER_" + roman);
                                        if(!user.getCompletedQuests().contains(quest) && user.getItemsForged() >= quest.getAmount()) {
                                            quest.finishQuest(player);
                                        }
                                    }
                                    if(item.getRarity() == Rarity.LEGENDARY) {
                                        user.setLegendaryItemsForged(user.getLegendaryItemsForged() + 1);
                                        for(String roman : ProductsUtil.getRomanNumerals()) {
                                            Quest quest = Quest.valueOf("LEGENDARY_FORGING_" + roman);
                                            if(!user.getCompletedQuests().contains(quest) && user.getLegendaryItemsForged() >= quest.getAmount()) {
                                                quest.finishQuest(player);
                                            }
                                        }
                                    }
                                    if(item.getName().contains("Sell Wand")) {
                                        user.setSellWandsForged(user.getSellWandsForged() + 1);
                                        Quest quest = Quest.valueOf("MAGIC_WAND_I");
                                        if(!user.getCompletedQuests().contains(quest) && user.getSellWandsForged() >= quest.getAmount()) {
                                            quest.finishQuest(player);
                                        }
                                    }
                                    if(category == ForgingCategory.MATS) {
                                        user.setMatsForged(user.getMatsForged() + 1);
                                        for(String roman : ProductsUtil.getRomanNumerals()) {
                                            Quest quest = Quest.valueOf("MATERIAL_MAKING_" + roman);
                                            if(!user.getCompletedQuests().contains(quest) && user.getMatsForged() >= quest.getAmount()) {
                                                quest.finishQuest(player);
                                            }
                                        }
                                    }
                                    new BukkitRunnable() {
                                        public void run() {
                                            player.performCommand("forge " + category.toString());
                                        }
                                    }.runTaskLater(Products.getInstance(), 1);

                            FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
                            DailyQuest daily = DailyQuest.FORGING;
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

                        } else {
                            player.sendMessage(Products.getPrefix() + "You don't have enough resources for this action.");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                        }
                    }
            ));
            slot++;
        }
        builder.setItemButton(40, new ItemButton(
                new ItemBuilder(Material.OAK_SIGN)
                        .setName(ChatColor.YELLOW + "Go Back")
                        .setLore(ChatColor.GRAY + "Click to go back")
                        .toItemStack(),
                clicked -> {
                    player.performCommand("forge");
                }
        ));
        player.openInventory(builder.getInventory());
    }
}
