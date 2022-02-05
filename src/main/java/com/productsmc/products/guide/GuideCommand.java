package com.productsmc.products.guide;

import com.productsmc.products.Products;
import com.productsmc.products.user.User;
import com.productsmc.products.util.InventoryBuilder;
import com.productsmc.products.util.ItemBuilder;
import com.productsmc.products.util.ItemButton;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class GuideCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("guide")) {

            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            InventoryBuilder builder = new InventoryBuilder(5, "Guides");

            int index = 10;
            for(Guide guide : Guide.values()) {
                if(user.getLevel() >= guide.getLevel()) {
                    if(index % 9 == 8) {
                        index += 2;
                    }
                    builder.setItemButton(index, new ItemButton(new ItemBuilder(guide.getMaterial())
                            .setName(ChatColor.YELLOW + guide.getName())
                            .setLore(guide.getDescription())
                            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                            .toItemStack(),
                            event -> {
                                event.setCancelled(true);
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            }));
                    index++;
                }
            }
            builder.setItemButton(38, new ItemButton(new ItemBuilder(Material.LIGHT_BLUE_DYE)
                    .setName(ChatColor.YELLOW + "Discord")
                    .setLore(ChatColor.GRAY + "Click to join our Discord")
                    .toItemStack(),
                    event -> {
                        event.setCancelled(true);
                        player.closeInventory();
                        player.performCommand("discord");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                    }));
            builder.setItemButton(40, new ItemButton(new ItemBuilder(Material.PLAYER_HEAD)
                    .setName(ChatColor.YELLOW + "Player Guides")
                    .setLore(ChatColor.GRAY + "Click to visit Player Guides")
                    .toItemStack(),
                    event -> {
                        event.setCancelled(true);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                    }));
            builder.setItemButton(42, new ItemButton(new ItemBuilder(Material.GOLD_INGOT)
                    .setName(ChatColor.YELLOW + "Store")
                    .setLore(ChatColor.GRAY + "Click to visit our Store")
                    .toItemStack(),
                    event -> {
                        event.setCancelled(true);
                        player.closeInventory();
                        player.performCommand("buy");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                    }));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.25f);
            player.openInventory(builder.getInventory());
        }
        return true;
    }

}
