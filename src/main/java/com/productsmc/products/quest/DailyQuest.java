package com.productsmc.products.quest;

import com.productsmc.products.Products;
import com.productsmc.products.item.Item;
import com.productsmc.products.level.Level;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DailyQuest {

    ORES("Daily: Ores", 7, 18, Item.MINING_BOMB, 5, "Mine Ores"),
    CROPS("Daily: Crops", 7, 18, Item.FARMING_BOMB, 5, "Mine Crops"),
    GENS("Daily: Gens", 0.75, 15, Item.GENERATOR_REMOTE, 1, "Upgrade Gens"),
    SHOP("Daily: Shop", 3, 20, Item.RARE_KEY, 1, "Buy Items from the Shop"),
    BOMBS("Daily: Bombs", 0.25, 24, Item.EPIC_MAT_BUNDLE, 1, "Throw any Bombs"),
    FORGING("Daily: Forging", 0.75, 23, Item.GOLDEN_NETHER_WART, 1, "Forge Items"),
    CRATES("Daily: Crates", 0.5, 22, Item.DARK_EMERALD, 1, "Open Crates"),
    DARK_ORES("Daily: Dark Ores", 2, 21, Item.DARK_BOMB, 1, "Mine Dark Ores"),
    SCRAP("Daily: Scrap", 1.25, 20, Item.RARE_KEY, 2, "Scrap Items"),
    REGROWTH("Daily: Regrowth", 4, 21, Item.RARE_KEY, 2,"Regrow Ores or Crops"),
    KILLER("Daily: Killer", 0.2,24, Item.RARE_KEY, 1, "Kill Players"),
    PICK_ABILITIES("Daily: Pick Abilities", 4, 30, Item.RARE_KEY, 3, "Activate chance based Pick Abilities"),
    HOE_ABILITIES("Daily: Hoe Abilities", 4, 30, Item.RARE_KEY, 3, "Activate chance based Hoe Abilities"),
    PET_ABILITIES("Daily: Pet Abilities", 4,  30, Item.EPIC_KEY, 2,"Activate chance based Pet Abilities"),
    THIEF("Daily: Thief", 0.25, 26, Item.EPIC_KEY, 2, "Steal Items using Steal Chance"),
    TROPHY_WINNER("Daily: Trophy Winner", 1, 44, Item.TROPHY, 1, "Complete all Daily Quests")
    ;

    private String name;
    private double amount;
    private int experience;
    private Item item;
    private int quantity;
    private String description;

    DailyQuest(String name, double amount, int experience, Item item, int quantity, String description) {
        this.name = name;
        this.amount = amount;
        this.experience = experience;
        this.item = item;
        this.quantity = quantity;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public long getAmount(int level) {
        return this == TROPHY_WINNER ? 1 : Math.round(amount * level) + 1;
    }

    public long getExperience(int level) {
        return experience * level;
    }

    public Item getItem() {
        return item;
    }

    public String getDescription() {
        return description;
    }

    public void finishQuest(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

        FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();

        experience = (int) getExperience(config.getInt("daily.level"));

        player.sendMessage("");
        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + name + " Completed");
        player.sendMessage("");
        player.sendMessage("Rewards:");
        player.sendMessage(ChatColor.DARK_GRAY + "> " + item.getRarity().getColor() + "1x " + item.getName());
        player.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.GOLD + "" + experience + "x Experience");
        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_GRAY + "Congratulations!");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.25f);

        if(item == Item.PET_SLOT) {
            user.setPetSlots(user.getPetSlots() + 1);
        } else if(item == Item.GEN_SLOT) {
            user.setSlots(user.getSlots() + 1);
        } else {
            player.getInventory().addItem(item.getItem());
        }

        user.setExperience(user.getExperience() + experience);

        if(user.getExperience() >= Level.getExperienceNeeded(user.getLevel() + 1)) {
            Level.playerLevelUp(player, user.getLevel() + 1);
        }

    }

    public static List<DailyQuest> getQuestCycle(int quest, int cycle) {

        List<DailyQuest> quests = new ArrayList<>();

        switch(cycle) {
            case 1:

                quests.add(DailyQuest.GENS);
                quests.add(DailyQuest.SHOP);

                if(quest >= 4) {
                    quests.add(DailyQuest.CROPS);
                }

                if(quest >= 5) {
                    quests.add(DailyQuest.THIEF);
                }

                quests.add(DailyQuest.TROPHY_WINNER);
                break;

            case 2:

                quests.add(DailyQuest.CROPS);
                quests.add(DailyQuest.ORES);

                if(quest >= 4) {
                    quests.add(DailyQuest.FORGING);
                }

                if(quest >= 5) {
                    quests.add(DailyQuest.HOE_ABILITIES);
                }

                quests.add(DailyQuest.TROPHY_WINNER);
                break;

            case 3:

                quests.add(DailyQuest.CROPS);
                quests.add(DailyQuest.KILLER);

                if(quest >= 4) {
                    quests.add(DailyQuest.REGROWTH);
                }

                if(quest >= 5) {
                    quests.add(DailyQuest.PICK_ABILITIES);
                }

                quests.add(DailyQuest.TROPHY_WINNER);
                break;

            case 4:

                quests.add(DailyQuest.DARK_ORES);
                quests.add(DailyQuest.CRATES);

                if(quest >= 4) {
                    quests.add(DailyQuest.SHOP);
                }

                if(quest >= 5) {
                    quests.add(DailyQuest.PET_ABILITIES);
                }

                quests.add(DailyQuest.TROPHY_WINNER);
                break;

            case 5:

                quests.add(DailyQuest.ORES);
                quests.add(DailyQuest.CROPS);

                if(quest >= 4) {
                    quests.add(DailyQuest.SCRAP);
                }

                if(quest >= 5) {
                    quests.add(DailyQuest.THIEF);
                }

                quests.add(DailyQuest.TROPHY_WINNER);
                break;

            case 6:

                quests.add(DailyQuest.DARK_ORES);
                quests.add(DailyQuest.REGROWTH);

                if(quest >= 4) {
                    quests.add(DailyQuest.BOMBS);
                }

                if(quest >= 5) {
                    quests.add(DailyQuest.PICK_ABILITIES);
                }

                quests.add(DailyQuest.TROPHY_WINNER);
                break;

            case 7:

                quests.add(DailyQuest.FORGING);
                quests.add(DailyQuest.CRATES);

                if(quest >= 4) {
                    quests.add(DailyQuest.SCRAP);
                }

                if(quest >= 5) {
                    quests.add(DailyQuest.HOE_ABILITIES);
                }

                quests.add(DailyQuest.TROPHY_WINNER);
                break;

            case 8:

                quests.add(DailyQuest.KILLER);
                quests.add(DailyQuest.ORES);

                if(quest >= 4) {
                    quests.add(DailyQuest.BOMBS);
                }

                if(quest >= 5) {
                    quests.add(DailyQuest.PET_ABILITIES);
                }

                quests.add(DailyQuest.TROPHY_WINNER);
                break;

        }

        return quests;
    }

    public static void checkCompletedDailies(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {

                int completed = 0;
                User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
                Map<DailyQuest, Integer> dailies = user.getDailies();
                FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();

                for(DailyQuest quest : dailies.keySet()) {

                    if(dailies.get(quest) == -1) {
                        completed++;
                    }

                }

                if(completed == dailies.size() - 1) {

                    dailies.put(DailyQuest.TROPHY_WINNER, 1);
                    DailyQuest.TROPHY_WINNER.finishQuest(player);

                }

            }

        }.runTaskLater(Products.getInstance(), 2);

    }

}
