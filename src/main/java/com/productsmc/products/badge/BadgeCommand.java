/*package com.productsmc.products.trail;

import com.productsmc.products.Products;
import com.productsmc.products.badge.Badge;
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
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class BadgeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (command.getName().equalsIgnoreCase("badges")) {

            Player player = (Player) sender;
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            InventoryBuilder inventory = new InventoryBuilder(5, "Badges");

            int index = 10;

            for(Badge badge : Badge.values()) {

                inventory.setItemButton(index, new ItemButton(new ItemBuilder(badge.getIcon())
                        .setName(trail.getRarity().getColor() + trail.getName() + " Trail")
                        .setLore(ChatColor.GRAY + trail.getDescription(),
                                ChatColor.GRAY + "Left-Click to equip Trail " + (player.hasPermission(trail.getPermission()) ? ChatColor.GREEN + "(Unlocked)" : ChatColor.RED + "(Locked)"),
                                "",
                                trail.getRarity().getLore(),
                                "",
                                ChatColor.DARK_GRAY + "From Season " + trail.getSeason())
                        .toItemStack(),
                        event -> {

                            event.setCancelled(true);

                            if(player.hasPermission(trail.getPermission())) {
                                user.setTrail(trail);
                                player.sendMessage(Products.getPrefix() + "Your " + trail.getName() + " Trail was equipped!");
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f);
                            } else {
                                player.sendMessage(Products.getPrefix() + "You do not have access to this trail.");
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                            }

                        }));

                index++;

            }

            inventory.setItemButton(40,
                    new ItemButton(new ItemBuilder(Material.BARRIER)
                            .setName(ChatColor.YELLOW + "Clear Trail")
                            .setLore(ChatColor.GRAY + "Unequip your current Trail",
                                    "",
                                    ChatColor.DARK_GRAY + "Left-Click to unequip your Trail")
                            .toItemStack(),
                            event -> {

                                event.setCancelled(true);
                                user.setTrail(null);
                                player.sendMessage(Products.getPrefix() + "Your Trail was unequipped!");
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f);

                            }));

            player.openInventory(inventory.getInventory());

        }
        return true;
    }

}*/
