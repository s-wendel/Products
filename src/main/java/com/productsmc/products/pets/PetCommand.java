package com.productsmc.products.pets;

import com.productsmc.products.Products;
import com.productsmc.products.item.Rarity;
import com.productsmc.products.user.User;
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
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if(command.getName().equalsIgnoreCase("pets")) {
            Player player = (Player) sender;
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            InventoryBuilder inventory = new InventoryBuilder(6, "Pets");
            List<Pet> pets = user.getClonedPets();
            int slot = 10;
            for(Pet pet : user.getEquippedPets()) {
                pets.remove(pet);
                if(slot % 9 == 8)
                    slot += 2;
                inventory.setItemButton(slot, new ItemButton(
                        new ItemBuilder(pet.getHead())
                                .setName(pet.getRarity().getColor() + pet.getName() + ChatColor.GREEN + " (Equipped)")
                                .setLore("",
                                        "&6Passive: &e" + pet.getPassive(),
                                        "&f" + pet.getPassiveDescription()[0],
                                        "&f" + pet.getPassiveDescription()[1],
                                        "&f" + pet.getPassiveDescription()[2],
                                        "",
                                        "&8Left-Click to unequip",
                                        "",
                                        pet.getRarity().getLore())
                                .toItemStack(),
                event -> {
                    event.setCancelled(true);
                    user.getEquippedPets().remove(pet);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f);
                    player.sendMessage(Products.getPrefix() + "Your " + ProductsUtil.toProperCase(pet.toString()) + " Pet was unequipped.");
                    player.performCommand("pets");
                    if(pet == Pet.TURTLE) {
                        player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                    } else if(pet == Pet.GOLEM) {
                        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    }
                }));
                slot++;
            }
            for(Pet pet : pets) {
                if(slot % 9 == 8)
                    slot += 2;
                int scrap = 0;
                Rarity rarity = pet.getRarity();
                if(rarity == Rarity.COMMON) {
                    scrap = 250;
                } else if(rarity == Rarity.UNCOMMON) {
                    scrap = 650;
                } else if(rarity == Rarity.RARE) {
                    scrap = 1250;
                } else if(rarity == Rarity.EPIC) {
                    scrap = 2500;
                } else if(rarity == Rarity.LEGENDARY) {
                    scrap = 4000;
                } else if(rarity == Rarity.EVENT) {
                    scrap = 10000;
                }
                final int shards = scrap;
                inventory.setItemButton(slot, new ItemButton(
                        new ItemBuilder(pet.getHead())
                                .setName(pet.getRarity().getColor() + pet.getName())
                                .setLore("",
                                        "&6Passive: &e" + pet.getPassive(),
                                        "&f" + pet.getPassiveDescription()[0],
                                        "&f" + pet.getPassiveDescription()[1],
                                        "&f" + pet.getPassiveDescription()[2],
                                        "",
                                        "&8Left-Click to equip",
                                        "&8Shift Right-Click to scrap (No Confirmation)",
                                        "&8Scrapping pet will give you: " + scrap + "♢",
                                        "",
                                        pet.getRarity().getLore())
                                .toItemStack(),
                        event -> {
                            if(event.getClick() == ClickType.SHIFT_RIGHT) {
                                event.setCancelled(true);
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f);
                                user.getPets().remove(pet);
                                player.performCommand("pets");
                                user.setShards(user.getShards() + shards);
                                player.sendMessage(Products.getPrefix() + "Your " + ProductsUtil.toProperCase(pet.toString()) + " Pet was deleted");
                            } else {
                                event.setCancelled(true);
                                if(user.getEquippedPets().size() + 1 > user.getPetSlots()) {
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.75f);
                                    player.sendMessage(Products.getPrefix() + "Your " + ProductsUtil.toProperCase(pet.toString()) + " Pet could not be equipped." + ChatColor.RED + " (Max pet slots " + user.getPetSlots() + "/" + user.getPetSlots() + ")");
                                } else {
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f);
                                    player.sendMessage(Products.getPrefix() + "Your " + ProductsUtil.toProperCase(pet.toString()) + " Pet was equipped.");
                                    user.getEquippedPets().add(pet);
                                    if(pet == Pet.TURTLE) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999999, 0, false));
                                    } else if(pet == Pet.GOLEM) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999999, 0, false));
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999999, 0, false));
                                    } else if(pet == Pet.TIGER) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999999, 0, false));
                                    }
                                    player.performCommand("pets");
                                }
                            }
                        }));
                slot++;
            }
            for(int i = 0; i < 21; i++) {
                if(slot >= 35) {
                    break;
                }
                if(slot % 9 == 8) {
                    slot += 2;
                }
                inventory.setItemButton(slot, new ItemButton(
                        new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                                .setName("&eEmpty Slot")
                                .setLore("&7Earn pets from crates,",
                                        "&7and other ways to equip them here.")
                                .toItemStack(),
                        event -> {
                            event.setCancelled(true);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.75f);
                        }));
                slot++;
            }

            inventory.setItemButton(47, new ItemButton(
                    new ItemBuilder(Material.BARRIER)
                            .setName(ChatColor.YELLOW + "Unequip All")
                            .setLore(ChatColor.GRAY + "Unequips every pet currently equipped",
                                    "",
                                    ChatColor.DARK_GRAY + "Left-Click to unequip all pets")
                            .toItemStack(),
                    event -> {
                        event.setCancelled(true);
                        user.getEquippedPets().clear();
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f);
                        player.sendMessage(Products.getPrefix() + "All your Pets were unequipped.");
                        player.performCommand("pets");
                        player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    }));

            inventory.setItemButton(51, new ItemButton(
                    new ItemBuilder(Material.KNOWLEDGE_BOOK)
                            .setName(ChatColor.YELLOW + "View All")
                            .setLore(ChatColor.GRAY + "View every type of pet",
                                    "",
                                    ChatColor.DARK_GRAY + "Left-Click to view all pets")
                            .toItemStack(),
                    event -> {

                        event.setCancelled(true);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f);

                        InventoryBuilder builder = new InventoryBuilder(6, "Pets > View All");

                        int index = 10;

                        for(Pet pet : Pet.values()) {

                            if(index % 9 == 8) {
                                index += 2;
                            }

                            builder.setItemButton(index, new ItemButton(
                                    new ItemBuilder(pet.getHead())
                                            .setName(pet.getRarity().getColor() + pet.getName())
                                            .setLore("",
                                                    "&6Passive: &e" + pet.getPassive(),
                                                    "&f" + pet.getPassiveDescription()[0],
                                                    "&f" + pet.getPassiveDescription()[1],
                                                    "&f" + pet.getPassiveDescription()[2],
                                                    "",
                                                    pet.getRarity().getLore())
                                            .toItemStack(),
                                    click -> {

                                        click.setCancelled(true);
                                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.75f);

                                    }));

                            index++;

                        }

                        builder.setItemButton(49, new ItemButton(
                                new ItemBuilder(Material.OAK_SIGN)
                                        .setName(ChatColor.YELLOW + "Go Back")
                                        .setLore(ChatColor.GRAY + "Click to go back")
                                        .toItemStack(),
                                clicked -> {
                                    player.performCommand("pets");
                                }
                        ));

                        player.openInventory(builder.getInventory());

                    }));

            player.openInventory(inventory.getInventory());
        }
        return true;
    }
}
