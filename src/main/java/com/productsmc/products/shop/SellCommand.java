package com.productsmc.products.shop;

import com.productsmc.products.Products;
import com.productsmc.products.generator.Generator;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellCommand implements CommandExecutor, Listener {

    public void sellItems(Player player, Inventory inventory, double extraMulti) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        long total = 0;
        int amount = 0;
        for(ItemStack item : inventory.getContents()) {
            if(item != null) {
                Generator generator = Generator.ofDrop(item.getType());
                if(generator != null) {
                    ItemStack drop = generator.getItem();
                    if(item.isSimilar(drop)) {
                        total += item.getAmount() * generator.getWorth();
                        amount += item.getAmount();
                        item.setAmount(0);
                    }
                }
            }
        }
        user.setItemsSold(user.getItemsSold() + amount);

        for(String roman : ProductsUtil.getRomanNumerals()) {
            Quest quest = Quest.valueOf("MERCHANT_" + roman);
            if(!user.getCompletedQuests().contains(quest) && user.getItemsSold() >= quest.getAmount()) {
                quest.finishQuest(player);
            }
        }

        double multi = user.getMultiplier() * (1 + extraMulti);
        total = Math.round(total * multi);
        player.sendMessage(Products.getPrefix() + "Sold " + amount + "x Items for " + ChatColor.YELLOW + ProductsUtil.format(total) + "⛂ " + ChatColor.DARK_GRAY + "(" + multi + "x)");
        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_THROW, 1f, 1.25f);
        user.setCoins(user.getCoins() + total);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("sell")) {
            sellItems(player, player.getInventory(), 0);
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteract(PlayerInteractEvent event) {
        if(!event.isCancelled() && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND && (event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType() == Material.HOPPER)) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Sell Wand")) {
                event.setCancelled(true);
                double extraMulti = 0;
                if (ProductsUtil.hasArmorSet(player, "Merchant")) {
                    extraMulti += 0.05;
                }
                String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
                if (name.equals("1.5x Sell Wand")) {
                    extraMulti += 0.5;
                } else if (name.equals("2x Sell Wand")) {
                    extraMulti += 1;
                } else if (name.equals("2.5x Sell Wand")) {
                    extraMulti += 1.5;
                } else if (name.equals("3x Sell Wand")) {
                    extraMulti += 2;
                }
                if(event.getClickedBlock().getType() == Material.CHEST) {
                    Chest chest = (Chest) event.getClickedBlock().getState();
                    sellItems(player, chest.getInventory(), extraMulti);
                } else if(event.getClickedBlock().getType() == Material.HOPPER) {
                    Hopper hopper = (Hopper) event.getClickedBlock().getState();
                    sellItems(player, hopper.getInventory(), extraMulti);
                }
            }
        }
    }

}
