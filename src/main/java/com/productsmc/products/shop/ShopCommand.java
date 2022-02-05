package com.productsmc.products.shop;

import com.productsmc.products.Products;
import com.productsmc.products.forging.ForgingCategory;
import com.productsmc.products.generator.Generator;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Mat;
import com.productsmc.products.item.Rarity;
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

public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("shop")) {
            HashMap<ShopCategory, List<ShopItem>> itemMap = new HashMap<>();
            for(ShopItem item : ShopItem.values()) {
                ShopCategory category = item.getCategory();
                List<ShopItem> items = itemMap.containsKey(category) ? itemMap.get(category) : new ArrayList<>();
                items.add(item);
                itemMap.put(category, items);
            }
            if(args.length == 0) {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
                InventoryBuilder categories = new InventoryBuilder(5, "Shop");
                for(ShopCategory category : ShopCategory.values()) {
                    categories.setItemButton(category.getSlot(), new ItemButton(
                            new ItemBuilder(category.getIcon())
                                    .setName(ChatColor.YELLOW + ProductsUtil.toProperCase(category.toString()))
                                    .setLore(ChatColor.GRAY + "All buyable " + ProductsUtil.toProperCase(category.toString()),
                                            "",
                                            ChatColor.DARK_GRAY + "Left-Click to view")
                                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                                    .toItemStack(),
                            event -> {
                                shopCategory(player, itemMap, category);
                            }
                    ));
                }
                player.openInventory(categories.getInventory());
                return true;
            }
            try {
                shopCategory(player, itemMap, ShopCategory.valueOf(args[0].toUpperCase()));
            } catch(Exception exception) {
                player.sendMessage(Products.getPrefix() + "/shop <category>");
            }
        }
        return true;
    }

    public void shopCategory(Player player, HashMap<ShopCategory, List<ShopItem>> itemMap, ShopCategory category) {
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
        InventoryBuilder builder = new InventoryBuilder(5, "Shop > " + ProductsUtil.toProperCase(category.toString()));
        int slot = 0;
        ItemStack[] items = new ItemStack[itemMap.get(category).size()];
        for(ShopItem item : itemMap.get(category)) {
            List<String> description = new ArrayList<>();
            if(item.getCoins() > 0) {
                int amount = item.getCoins();
                description.add(user.getCoins() >= amount ? ChatColor.DARK_GREEN + "[✔] " + ChatColor.GREEN + amount + "⛂" : ChatColor.DARK_RED + "[✘] " + ChatColor.RED + amount + "⛂");
            }
            if(item.getShards() > 0) {
                int amount = item.getShards();
                description.add(user.getShards() >= amount ? ChatColor.DARK_GREEN + "[✔] " + ChatColor.GREEN + amount + "♢" : ChatColor.DARK_RED + "[✘] " + ChatColor.RED + amount + "♢");
            }
            description.add("");
            description.add(ChatColor.DARK_GRAY + "Left-Click to purchase");
            description.add("");
            Rarity rarity = item.getItem() == null ? Rarity.COMMON : item.getItem().getRarity();
            description.add(rarity.getLore());
            String name = item.getItem()== null ? ProductsUtil.toProperCase(item.name()) : item.getItem().getName();
            int quantity = item.getAmount() == 0 ? 1 : item.getAmount();
            items[slot] = new ItemButton(
                    new ItemBuilder(item.getItem() == null ? item.getMaterial() : item.getItem().getIcon())
                            .setName(rarity.getColor() + "" + quantity + "x " + name)
                            .setLore(description.toArray(new String[description.size()]))
                            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                            .toItemStack(),
                    clicked -> {
                        clicked.setCancelled(true);
                        boolean go = true;
                        if(user.getShards() < item.getShards()) {
                            go = false;
                        }
                        if(user.getCoins() < item.getCoins()) {
                            go = false;
                        }
                        if(go) {
                            user.setCoins(user.getCoins() - item.getCoins());
                            user.setShards(user.getShards() - item.getShards());

                            FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
                            DailyQuest daily = DailyQuest.SHOP;
                            Map<DailyQuest, Integer> dailies = user.getDailies();
                            int progress = -1;

                            if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

                                progress = dailies.get(daily);

                                if(progress + quantity >= daily.getAmount(config.getInt("daily.level"))) {

                                    daily.finishQuest(player);
                                    dailies.put(daily, -1);
                                    DailyQuest.checkCompletedDailies(player);

                                } else {
                                    dailies.put(daily, progress + quantity);
                                }


                            }

                            if(item.name().endsWith("GEN")) {
                                switch(item){
                                    case COAL_GEN:
                                        player.getInventory().addItem(Generator.COAL.getGenerator(1));
                                        break;
                                    case IRON_GEN:
                                        player.getInventory().addItem(Generator.IRON.getGenerator(1));
                                        break;
                                    case COPPER_GEN:
                                        player.getInventory().addItem(Generator.COPPER.getGenerator(1));
                                        break;
                                    case GOLD_GEN:
                                        player.getInventory().addItem(Generator.GOLD.getGenerator(1));
                                        break;
                                    case REDSTONE_GEN:
                                        player.getInventory().addItem(Generator.REDSTONE.getGenerator(1));
                                        break;
                                    case LAPIS_GEN:
                                        player.getInventory().addItem(Generator.LAPIS.getGenerator(1));
                                        break;
                                    case DIAMOND_GEN:
                                        player.getInventory().addItem(Generator.DIAMOND.getGenerator(1));
                                        break;
                                    case EMERALD_GEN:
                                        player.getInventory().addItem(Generator.EMERALD.getGenerator(1));
                                        break;
                                    case NETHERITE_GEN:
                                        player.getInventory().addItem(Generator.NETHERITE.getGenerator(1));
                                        break;
                                    case OBSIDIAN_GEN:
                                        player.getInventory().addItem(Generator.OBSIDIAN.getGenerator(1));
                                        break;
                                    case GLOWSTONE_GEN:
                                        player.getInventory().addItem(Generator.GLOWSTONE.getGenerator(1));
                                        break;
                                    case MAGMA_GEN:
                                        player.getInventory().addItem(Generator.MAGMA.getGenerator(1));
                                        break;
                                    case BLAZE_GEN:
                                        player.getInventory().addItem(Generator.BLAZE.getGenerator(1));
                                        break;
                                    case ENDER_GEN:
                                        player.getInventory().addItem(Generator.ENDER.getGenerator(1));
                                        break;
                                    case SHULKER_GEN:
                                        player.getInventory().addItem(Generator.SHULKER.getGenerator(1));
                                        break;
                                }
                            } else {
                                player.getInventory().addItem(item.getItem() == null ? new ItemStack(item.getMaterial(), item.getAmount()) : item.getItem().getItem());
                            }
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1.5f);
                            player.sendMessage(Products.getPrefix() + "You purchased " + quantity + "x " + name);
                        } else {
                            player.sendMessage(Products.getPrefix() + "You don't have enough resources for this action.");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                        }
                    }
            ).getItem();
            slot++;
        }
        builder.addPagination(items, new int[] { 0, 35 });

        builder.setItemButton(40, new ItemButton(
                new ItemBuilder(Material.OAK_SIGN)
                        .setName(ChatColor.YELLOW + "Go Back")
                        .setLore(ChatColor.GRAY + "Click to go back")
                        .toItemStack(),
                clicked -> {
                    player.performCommand("shop");
                }
        ));

        builder.setItemButton(37, new ItemButton(
                new ItemBuilder(Material.ARROW)
                        .setName(ChatColor.YELLOW + "Previous Page")
                        .setLore(ChatColor.GRAY + "Click to go to the previous page")
                        .toItemStack(),
                clicked -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
                    player.openInventory(builder.getPreviousPage().getInventory());
                }
        ));

        builder.setItemButton(43, new ItemButton(
                new ItemBuilder(Material.ARROW)
                        .setName(ChatColor.YELLOW + "Next Page")
                        .setLore(ChatColor.GRAY + "Click to go to the next page")
                        .toItemStack(),
                clicked -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);
                    player.openInventory(builder.getNextPage().getInventory());
                }
        ));

        player.openInventory(builder.getPage(0).getInventory());
    }

}
