package com.productsmc.products.crate;

import com.productsmc.products.Products;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Mat;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ItemBuilder;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BundleListener implements Listener {

    Random random = new Random();

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                if(item.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Mat Bundle")) {
                    commonMat(player);
                    item.setAmount(item.getAmount() - 1);
                    event.setCancelled(true);
                } else if(item.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Mat Bundle")) {
                    uncommonMat(player);
                    item.setAmount(item.getAmount() - 1);
                    event.setCancelled(true);
                } else if(item.getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Mat Bundle")) {
                    rareMat(player);
                    item.setAmount(item.getAmount() - 1);
                    event.setCancelled(true);
                } else if(item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Mat Bundle")) {
                    epicMat(player);
                    item.setAmount(item.getAmount() - 1);
                    event.setCancelled(true);
                } else if(item.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Mat Bundle")) {
                    legendaryMat(player);
                    item.setAmount(item.getAmount() - 1);
                    event.setCancelled(true);
                }
                switch(ChatColor.stripColor(item.getItemMeta().getDisplayName()).toUpperCase()) {
                    case "COMMON LOOTBOX":
                        commonLootbox(player);
                        item.setAmount(item.getAmount() - 1);
                        event.setCancelled(true);
                        break;
                    case "UNCOMMON LOOTBOX":
                        uncommonLootbox(player);
                        item.setAmount(item.getAmount() - 1);
                        event.setCancelled(true);
                        break;
                    case "RARE LOOTBOX":
                        rareLootbox(player);
                        item.setAmount(item.getAmount() - 1);
                        event.setCancelled(true);
                        break;
                    case "EPIC LOOTBOX":
                        epicLootbox(player);
                        item.setAmount(item.getAmount() - 1);
                        event.setCancelled(true);
                        break;
                    case "LEGENDARY LOOTBOX":
                        legendaryLootbox(player);
                        item.setAmount(item.getAmount() - 1);
                        event.setCancelled(true);
                        break;
                }
            }
        }
    }

    public void commonMat(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            int reward = random.nextInt(8);
            Mat mat = null;
            int amount = 1;
            if(reward <= 0) {
                mat = Mat.COAL_ORE;
                amount = 25;
            } else if(reward <= 1) {
                mat = Mat.IRON_ORE;
                amount = 25;
            } else if(reward <= 2) {
                mat = Mat.CARROT;
                amount = 100;
            } else if(reward <= 3) {
                mat = Mat.POTATO;
                amount = 100;
            } else if(reward <= 4) {
                mat = Mat.DARK_COAL;
                amount = 1;
            } else if(reward <= 5) {
                mat = Mat.DARK_IRON;
                amount = 1;
            } else if(reward <= 6) {
                mat = Mat.GOLDEN_CARROT;
                amount = 1;
            } else {
                mat = Mat.GOLDEN_POTATO;
                amount = 1;
            }
            ItemStack item = mat.getItem();
            item.setAmount(amount);
            player.getInventory().addItem(item);
            rewards.add(ChatColor.DARK_GRAY + "> " + mat.getRarity().getColor() + amount + "x " + ProductsUtil.toProperCase(mat.name()));
        }
        player.sendMessage("");
        player.sendMessage("      " + ChatColor.WHITE + ChatColor.BOLD + "COMMON MAT BUNDLE");
        for(String message : rewards) {
            player.sendMessage(message);
        }
        player.sendMessage("");
    }

    public void uncommonMat(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int reward = random.nextInt(6);
            Mat mat = null;
            int amount = 1;
            if (reward <= 0) {
                mat = Mat.COPPER_ORE;
                amount = 25;
            } else if (reward <= 1) {
                mat = Mat.GOLD_ORE;
                amount = 25;
            } else if (reward <= 2) {
                mat = Mat.BEETROOT;
                amount = 100;
            } else if (reward <= 3) {
                mat = Mat.DARK_COPPER;
                amount = 1;
            } else if (reward <= 4) {
                mat = Mat.DARK_GOLD;
                amount = 1;
            } else {
                mat = Mat.GOLDEN_BEETROOT;
                amount = 1;
            }
            ItemStack item = mat.getItem();
            item.setAmount(amount);
            player.getInventory().addItem(item);
            rewards.add(ChatColor.DARK_GRAY + "> " + mat.getRarity().getColor() + amount + "x " + ProductsUtil.toProperCase(mat.name()));

        }
        player.sendMessage("");
        player.sendMessage("      " + ChatColor.DARK_GREEN + ChatColor.BOLD + "UNCOMMON MAT BUNDLE");
        for (String message : rewards) {
            player.sendMessage(message);
        }
        player.sendMessage("");

    }

    public void rareMat(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int reward = random.nextInt(6);
            Mat mat = null;
            int amount = 1;
            if (reward <= 0) {
                mat = Mat.REDSTONE_ORE;
                amount = 25;
            } else if (reward <= 1) {
                mat = Mat.LAPIS_ORE;
                amount = 25;
            } else if (reward <= 2) {
                mat = Mat.WHEAT;
                amount = 100;
            } else if (reward <= 3) {
                mat = Mat.DARK_REDSTONE;
                amount = 1;
            } else if (reward <= 4) {
                mat = Mat.DARK_LAPIS;
                amount = 1;
            } else {
                mat = Mat.GOLDEN_WHEAT;
                amount = 1;
            }
            ItemStack item = mat.getItem();
            item.setAmount(amount);
            player.getInventory().addItem(item);
            rewards.add(ChatColor.DARK_GRAY + "> " + mat.getRarity().getColor() + amount + "x " + ProductsUtil.toProperCase(mat.name()));
        }
        player.sendMessage("");
        player.sendMessage("      " + ChatColor.DARK_AQUA + ChatColor.BOLD + "RARE MAT BUNDLE");
        for (String message : rewards) {
            player.sendMessage(message);
        }
        player.sendMessage("");

    }

    public void epicMat(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int reward = random.nextInt(6);
            Mat mat = null;
            int amount = 1;
            if (reward <= 0) {
                mat = Mat.DIAMOND_ORE;
                amount = 25;
            } else if (reward <= 1) {
                mat = Mat.EMERALD_ORE;
                amount = 25;
            } else if (reward <= 2) {
                mat = Mat.NETHER_WART;
                amount = 100;
            } else if (reward <= 3) {
                mat = Mat.DARK_DIAMOND;
                amount = 1;
            } else if (reward <= 4) {
                mat = Mat.DARK_EMERALD;
                amount = 1;
            } else {
                mat = Mat.GOLDEN_NETHER_WART;
                amount = 1;
            }
            ItemStack item = mat.getItem();
            item.setAmount(amount);
            player.getInventory().addItem(item);
            rewards.add(ChatColor.DARK_GRAY + "> " + mat.getRarity().getColor() + amount + "x " + ProductsUtil.toProperCase(mat.name()));
        }

        player.sendMessage("");
        player.sendMessage("      " + ChatColor.AQUA + ChatColor.BOLD + "EPIC MAT BUNDLE");
        for (String message : rewards) {
            player.sendMessage(message);
        }
        player.sendMessage("");
    }

    public void legendaryMat(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int reward = random.nextInt(3);
            Mat mat = null;
            int amount = 1;
            if (reward <= 0) {
                mat = Mat.TROPHY;
                amount = 1;
            } else if (reward <= 1) {
                mat = Mat.RAINBOW_CROP;
                amount = 1;
            } else if (reward <= 2) {
                mat = Mat.RAINBOW_GEM;
                amount = 1;
            }
            ItemStack item = mat.getItem();
            item.setAmount(amount);
            player.getInventory().addItem(item);
            rewards.add(ChatColor.DARK_GRAY + "> " + mat.getRarity().getColor() + amount + "x " + ProductsUtil.toProperCase(mat.name()));
        }

        player.sendMessage("");
        player.sendMessage("      " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "LEGENDARY MAT BUNDLE");
        for (String message : rewards) {
            player.sendMessage(message);
        }
        player.sendMessage("");
    }


    public void commonLootbox(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            int reward = random.nextInt(9);
            Item item1 = null;
            int amount1 = 1;
            Item item2 = null;
            int amount2 = 1;
            if(reward <= 0) {
                item1 = Item.MINING_BOMB;
                item2 = Item.FARMING_BOMB;
                amount1 = 2;
                amount2 = 2;
            } else if(reward <= 1) {
                item1 = Item.DARK_BOMB;
                amount1 = 2;
            } else if(reward <= 2) {
                item1 = Item.COMMON_KEY;
                amount1 = 5;
            } else if(reward <= 3) {
                item1 = Item.UNCOMMON_KEY;
                amount1 = 3;
            } else if(reward <= 4) {
                item1 = Item.RARE_KEY;
                amount1 = 2;
            } else if(reward <= 5) {
                item1 = Item.COMMON_MAT_BUNDLE;
                amount1 = 3;
            } else if(reward <= 6) {
                item1 = Item.UNCOMMON_MAT_BUNDLE;
                amount1 = 2;
            } else if(reward <= 7) {
                item1 = Item.COMMON_PET;
                user.givePet(random.nextInt(101) <= 50 ? Pet.CREEPER : Pet.CHICKEN);
            } else {
                int armor = random.nextInt(4);
                if(armor == 0) {
                    item1 = Item.MERCHANT_HELMET;
                } else if(armor == 1) {
                    item1 = Item.MERCHANT_CHESTPLATE;
                } else if(armor == 2) {
                    item1 = Item.MERCHANT_LEGGINGS;
                } else {
                    item1 = Item.MERCHANT_BOOTS;
                }
            }
            rewards.add(ChatColor.DARK_GRAY + "> " + item1.getRarity().getColor() + amount1 + "x " + item1.getName());
            if(item1 != Item.COMMON_PET) {
                ItemStack newItem1 = item1.getItem();
                newItem1.setAmount(amount1);
                player.getInventory().addItem(newItem1);
            }
            if(item2 != null) {
                ItemStack newItem2 = item2.getItem();
                newItem2.setAmount(amount2);
                player.getInventory().addItem(newItem2);
                rewards.add(ChatColor.DARK_GRAY + "> " + item2.getRarity().getColor() + amount2 + "x " + item2.getName());
            }
        }
        int finalReward = random.nextInt(4);
        if(finalReward == 0) {
            player.getInventory().addItem(new ItemBuilder(Material.ORANGE_DYE)
                    .setName(ChatColor.YELLOW + "Amber Rank")
                    .setLore(ChatColor.GRAY + "Right-Click to redeem this rank")
                    .toItemStack());
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.GOLD + "1x Amber Rank");
        } else {
            double multi = Math.round(ThreadLocalRandom.current().nextDouble(0.03, 0.05) * 100) / 100.0;
            user.setMultiplier(user.getMultiplier() + multi);
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + multi + "x Multiplier");
        }
        player.sendMessage("");
        player.sendMessage("      " + ChatColor.WHITE + ChatColor.BOLD + "COMMON LOOTBOX");
        for(String message : rewards) {
            player.sendMessage(message);
        }
        player.sendMessage("");
    }

    public void uncommonLootbox(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            int reward = random.nextInt(8);
            Item item1 = null;
            int amount1 = 1;
            Item item2 = null;
            int amount2 = 1;
            if(reward <= 0) {
                item1 = Item.MINING_BOMB;
                item2 = Item.FARMING_BOMB;
                amount1 = 4;
                amount2 = 4;
            } else if(reward <= 1) {
                item1 = Item.DARK_BOMB;
                amount1 = 2;
            } else if(reward <= 2) {
                item1 = Item.UNCOMMON_KEY;
                amount1 = 5;
            } else if(reward <= 3) {
                item1 = Item.RARE_KEY;
                amount1 = 4;
            } else if(reward <= 4) {
                item1 = Item.RARE_MAT_BUNDLE;
                amount1 = 3;
            } else if(reward <= 5) {
                item1 = Item.UNCOMMON_PET;
                user.givePet(random.nextInt(101) <= 50 ? Pet.TURTLE : Pet.MERCHANT);
            } else if(reward <= 6) {
                int armor = random.nextInt(4);
                if(armor == 0) {
                    item1 = Item.MERCHANT_HELMET;
                } else if(armor == 1) {
                    item1 = Item.MERCHANT_CHESTPLATE;
                } else if(armor == 2) {
                    item1 = Item.MERCHANT_LEGGINGS;
                } else {
                    item1 = Item.MERCHANT_BOOTS;
                }
            } else {
                item1 = Item.GEN_SLOT;
                user.setSlots(user.getSlots() + 1);
            }
            rewards.add(ChatColor.DARK_GRAY + "> " + item1.getRarity().getColor() + amount1 + "x " + item1.getName());
            if(item1 != Item.UNCOMMON_PET && item1 != Item.GEN_SLOT) {
                ItemStack newItem1 = item1.getItem();
                newItem1.setAmount(amount1);
                player.getInventory().addItem(newItem1);
            }
            if(item2 != null) {
                ItemStack newItem2 = item2.getItem();
                newItem2.setAmount(amount2);
                player.getInventory().addItem(newItem2);
                rewards.add(ChatColor.DARK_GRAY + "> " + item2.getRarity().getColor() + amount2 + "x " + item2.getName());
            }
        }
        int finalReward = random.nextInt(4);
        if(finalReward == 0) {
            player.getInventory().addItem(new ItemBuilder(Material.ORANGE_DYE)
                    .setName(ChatColor.YELLOW + "Amber Rank")
                    .setLore(ChatColor.GRAY + "Right-Click to redeem this rank")
                    .toItemStack());
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.GOLD + "1x Amber Rank");
        } else {
            double multi = Math.round(ThreadLocalRandom.current().nextDouble(0.06, 0.08) * 100) / 100.0;
            user.setMultiplier(user.getMultiplier() + multi);
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + multi + "x Multiplier");
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("      " + ChatColor.DARK_GREEN + ChatColor.BOLD + "UNCOMMON LOOTBOX");
        for(String message : rewards) {
            Bukkit.broadcastMessage(message);
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Lootbox opened by " + player.getName());
    }

    public void rareLootbox(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            int reward = random.nextInt(8);
            Item item1 = null;
            int amount1 = 1;
            Item item2 = null;
            int amount2 = 1;
            if(reward <= 0) {
                item1 = Item.MINING_BOMB;
                item2 = Item.FARMING_BOMB;
                amount1 = 6;
                amount2 = 6;
            } else if(reward <= 1) {
                item1 = Item.DARK_BOMB;
                amount1 = 3;
            } else if(reward <= 2) {
                item1 = Item.RARE_KEY;
                amount1 = 5;
            } else if(reward <= 3) {
                item1 = Item.EPIC_KEY;
                amount1 = 3;
            } else if(reward <= 4) {
                item1 = Item.EPIC_MAT_BUNDLE;
                amount1 = 2;
            } else if(reward <= 5) {
                item1 = Item.RARE_PET;
                user.givePet(Pet.PUFFERFISH);
            } else if(reward <= 6) {
                int armor = random.nextInt(4);
                if(armor == 0) {
                    item1 = Item.ROGUE_CAP;
                } else if(armor == 1) {
                    item1 = Item.ROGUE_SHIRT;
                } else if(armor == 2) {
                    item1 = Item.ROGUE_PANTS;
                } else {
                    item1 = Item.ROGUE_SHOES;
                }
            } else {
                item1 = Item.GEN_SLOT;
                user.setSlots(user.getSlots() + 1);
            }
            rewards.add(ChatColor.DARK_GRAY + "> " + item1.getRarity().getColor() + amount1 + "x " + item1.getName());
            if(item1 != Item.RARE_PET && item1 != Item.GEN_SLOT) {
                ItemStack newItem1 = item1.getItem();
                newItem1.setAmount(amount1);
                player.getInventory().addItem(newItem1);
            }
            if(item2 != null) {
                ItemStack newItem2 = item2.getItem();
                newItem2.setAmount(amount2);
                player.getInventory().addItem(newItem2);
                rewards.add(ChatColor.DARK_GRAY + "> " + item2.getRarity().getColor() + amount2 + "x " + item2.getName());
            }
        }
        int finalReward = random.nextInt(5);
        if(finalReward == 0) {
            player.getInventory().addItem(new ItemBuilder(Material.RED_DYE)
                    .setName(ChatColor.YELLOW + "Ruby Rank")
                    .setLore(ChatColor.GRAY + "Right-Click to redeem this rank")
                    .toItemStack());
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.RED + "1x Ruby Rank");
        } else {
            double multi = Math.round(ThreadLocalRandom.current().nextDouble(0.1, 0.13) * 100) / 100.0;
            user.setMultiplier(user.getMultiplier() + multi);
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + multi + "x Multiplier");
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("      " + ChatColor.DARK_AQUA + ChatColor.BOLD + "RARE LOOTBOX");
        for(String message : rewards) {
            Bukkit.broadcastMessage(message);
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Lootbox opened by " + player.getName());
    }

    public void epicLootbox(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            int reward = random.nextInt(8);
            Item item1 = null;
            int amount1 = 1;
            Item item2 = null;
            int amount2 = 1;
            if(reward <= 0) {
                item1 = Item.MINING_BOMB;
                item2 = Item.FARMING_BOMB;
                amount1 = 7;
                amount2 = 7;
            } else if(reward <= 1) {
                item1 = Item.DARK_BOMB;
                amount1 = 3;
                item2 = Item.SPRAY_BOTTLE;
                amount2 = 5;
            } else if(reward <= 2) {
                item1 = Item.EPIC_KEY;
                amount1 = 5;
            } else if(reward <= 3) {
                item1 = Item.LEGENDARY_KEY;
                amount1 = 2;
            } else if(reward <= 4) {
                item1 = Item.EPIC_MAT_BUNDLE;
                amount1 = 3;
            } else if(reward <= 5) {
                item1 = Item.EPIC_PET;
                user.givePet(Pet.WITCH);
            } else if(reward <= 6) {
                int armor = random.nextInt(4);
                if(armor == 0) {
                    item1 = Item.ROGUE_CAP;
                } else if(armor == 1) {
                    item1 = Item.ROGUE_SHIRT;
                } else if(armor == 2) {
                    item1 = Item.ROGUE_PANTS;
                } else {
                    item1 = Item.ROGUE_SHOES;
                }
            } else {
                item1 = Item.GEN_SLOT;
                user.setSlots(user.getSlots() + 2);
            }
            if(item1 == Item.GEN_SLOT) {
                rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "2x Gen Slot");
            } else {
                rewards.add(ChatColor.DARK_GRAY + "> " + item1.getRarity().getColor() + amount1 + "x " + item1.getName());
            }
            if(item1 != Item.EPIC_PET && item1 != Item.GEN_SLOT) {
                ItemStack newItem1 = item1.getItem();
                newItem1.setAmount(amount1);
                player.getInventory().addItem(newItem1);
            }
            if(item2 != null) {
                ItemStack newItem2 = item2.getItem();
                newItem2.setAmount(amount2);
                player.getInventory().addItem(newItem2);
                rewards.add(ChatColor.DARK_GRAY + "> " + item2.getRarity().getColor() + amount2 + "x " + item2.getName());
            }
        }
        int finalReward = random.nextInt(5);
        if(finalReward == 0) {
            player.getInventory().addItem(new ItemBuilder(Material.RED_DYE)
                    .setName(ChatColor.YELLOW + "Ruby Rank")
                    .setLore(ChatColor.GRAY + "Right-Click to redeem this rank")
                    .toItemStack());
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.RED + "1x Ruby Rank");
        } else {
            double multi = Math.round(ThreadLocalRandom.current().nextDouble(0.13, 0.16) * 100) / 100.0;
            user.setMultiplier(user.getMultiplier() + multi);
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + multi + "x Multiplier");
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("      " + ChatColor.AQUA + ChatColor.BOLD + "EPIC LOOTBOX");
        for(String message : rewards) {
            Bukkit.broadcastMessage(message);
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Lootbox opened by " + player.getName());
    }

    public void legendaryLootbox(Player player) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        List<String> rewards = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            int reward = random.nextInt(8);
            Item item1 = null;
            int amount1 = 1;
            Item item2 = null;
            int amount2 = 1;
            if(reward <= 0) {
                item1 = Item.MINING_BOMB;
                item2 = Item.FARMING_BOMB;
                amount1 = 8;
                amount2 = 8;
            } else if(reward <= 1) {
                item1 = Item.DARK_BOMB;
                amount1 = 4;
                item1 = Item.SPRAY_BOTTLE;
                amount1 = 5;
            } else if(reward <= 2) {
                item1 = Item.EPIC_KEY;
                amount1 = 6;
            } else if(reward <= 3) {
                item1 = Item.LEGENDARY_KEY;
                amount1 = 3;
            } else if(reward <= 4) {
                item1 = Item.EPIC_MAT_BUNDLE;
                amount1 = 5;
            } else if(reward <= 5) {
                item1 = Item.LEGENDARY_PET;
                user.givePet(Pet.GOLEM);
            } else if(reward <= 6) {
                int armor = random.nextInt(4);
                if(armor == 0) {
                    item1 = Item.ROGUE_CAP;
                } else if(armor == 1) {
                    item1 = Item.ROGUE_SHIRT;
                } else if(armor == 2) {
                    item1 = Item.ROGUE_PANTS;
                } else {
                    item1 = Item.ROGUE_SHOES;
                }
            } else {
                item1 = Item.GEN_SLOT;
                user.setSlots(user.getSlots() + 3);
            }
            if(item1 == Item.GEN_SLOT) {
                rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "3x Gen Slot");
            } else {
                rewards.add(ChatColor.DARK_GRAY + "> " + item1.getRarity().getColor() + amount1 + "x " + item1.getName());
            }
            if(item1 != Item.LEGENDARY_PET && item1 != Item.GEN_SLOT) {
                ItemStack newItem1 = item1.getItem();
                newItem1.setAmount(amount1);
                player.getInventory().addItem(newItem1);
            }
            if(item2 != null) {
                ItemStack newItem2 = item2.getItem();
                newItem2.setAmount(amount2);
                player.getInventory().addItem(newItem2);
                rewards.add(ChatColor.DARK_GRAY + "> " + item2.getRarity().getColor() + amount2 + "x " + item2.getName());
            }
        }
        int finalReward = random.nextInt(6);
        if(finalReward == 0) {
            player.getInventory().addItem(new ItemBuilder(Material.RED_DYE)
                    .setName(ChatColor.YELLOW + "Opal Rank")
                    .setLore(ChatColor.GRAY + "Right-Click to redeem this rank")
                    .toItemStack());
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.LIGHT_PURPLE + "1x Opal Rank");
        } else {
            double multi = Math.round(ThreadLocalRandom.current().nextDouble(0.17, 0.2) * 100) / 100.0;
            user.setMultiplier(user.getMultiplier() + multi);
            rewards.add(ChatColor.DARK_GRAY + "> " + ChatColor.AQUA + multi + "x Multiplier");
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("   " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "LEGENDARY LOOTBOX");
        for(String message : rewards) {
            Bukkit.broadcastMessage(message);
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Lootbox opened by " + player.getName());
    }

}
