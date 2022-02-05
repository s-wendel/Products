package com.productsmc.products.item.listener;

import com.productsmc.products.Products;
import com.productsmc.products.profession.farming.FarmingListener;
import com.productsmc.products.profession.mining.MiningListener;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BombListener implements Listener {

    @EventHandler
    public void miningBomb(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Prevention of using while opening crates
        if(!player.getWorld().getName().equals("ul_Spawn") || event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().name().endsWith("GLASS") || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if(item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Mining Bomb")) {

            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

            user.setBombsThrown(user.getBombsThrown() + 1);

            for(String roman : ProductsUtil.getRomanNumerals()) {
                Quest quest = Quest.valueOf("BOMB_THROWER_" + roman);
                if(!user.getCompletedQuests().contains(quest) && user.getBombsThrown() >= quest.getAmount()) {
                    quest.finishQuest(player);
                }
            }

            FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
            DailyQuest daily = DailyQuest.BOMBS;
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

            Snowball ball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
            ball.setShooter(player);
            ball.setVelocity(player.getLocation().getDirection().multiply(1.5));
            ball.setMetadata("miningBomb", new FixedMetadataValue(Products.getInstance(), true));
            item.setAmount(item.getAmount() - 1);

        }

    }

    @EventHandler
    public void farmingBomb(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Prevention of using while opening crates
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().name().endsWith("GLASS") || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if(item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Farming Bomb")) {

            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

            user.setBombsThrown(user.getBombsThrown() + 1);

            for(String roman : ProductsUtil.getRomanNumerals()) {
                Quest quest = Quest.valueOf("BOMB_THROWER_" + roman);
                if(!user.getCompletedQuests().contains(quest) && user.getBombsThrown() >= quest.getAmount()) {
                    quest.finishQuest(player);
                }
            }

            FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
            DailyQuest daily = DailyQuest.BOMBS;
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

            Snowball ball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
            ball.setShooter(player);
            ball.setVelocity(player.getLocation().getDirection().multiply(1.5));
            ball.setMetadata("farmingBomb", new FixedMetadataValue(Products.getInstance(), true));
            item.setAmount(item.getAmount() - 1);

        }

    }

    @EventHandler
    public void darkBomb(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Prevention of using while opening crates
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().name().endsWith("GLASS") || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if(item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Dark Bomb")) {

            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

            user.setBombsThrown(user.getBombsThrown() + 1);

            for(String roman : ProductsUtil.getRomanNumerals()) {
                Quest quest = Quest.valueOf("BOMB_THROWER_" + roman);
                if(!user.getCompletedQuests().contains(quest) && user.getBombsThrown() >= quest.getAmount()) {
                    quest.finishQuest(player);
                }
            }

            FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
            DailyQuest daily = DailyQuest.BOMBS;
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

            Snowball ball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
            ball.setShooter(player);
            ball.setVelocity(player.getLocation().getDirection().multiply(1.5));
            ball.setMetadata("darkBomb", new FixedMetadataValue(Products.getInstance(), true));
            item.setAmount(item.getAmount() - 1);

        }

    }

    @EventHandler
    public void farmingBomb(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball && event.getEntity().getShooter() instanceof Player && event.getEntity().hasMetadata("farmingBomb")) {

            Location location = event.getHitBlock().getLocation();
            Random random = new Random();
            List<Block> list = new ArrayList<>();
            Player player = (Player) event.getEntity().getShooter();

            ItemStack item = player.getInventory().getItemInMainHand();

            for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                double radius = Math.sin(i) * 8;
                double y = Math.cos(i) * 8;
                for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
                    double x = Math.cos(a) * radius;
                    double z = Math.sin(a) * radius;
                    location.add(x, y, z);
                    location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(0 + random.nextInt(16), 240 + random.nextInt(16), 0 + random.nextInt(16)), 120 + new Random().nextInt(100)));
                    location.subtract(x, y, z);
                }
            }

            int bx = location.getBlockX();
            int by = location.getBlockY();
            int bz = location.getBlockZ();

            for (int x = bx - 8; x <= bx + 8; x++) {
                for (int y = by - 8; y <= by + 8; y++) {
                    for (int z = bz - 8; z <= bz + 8; z++) {
                        double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                        if (distance < 64) {
                            list.add(new Location(location.getWorld(), x, y, z).getBlock());
                        }
                    }
                }
            }

            FarmingListener.registerBlocks(player, list, true);
            location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);

        }
    }

    @EventHandler
    public void miningBomb(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball && event.getEntity().getShooter() instanceof Player && event.getEntity().hasMetadata("miningBomb")) {

            Location location = event.getHitBlock().getLocation();
            Random random = new Random();
            List<Block> list = new ArrayList<>();
            Player player = (Player) event.getEntity().getShooter();

            ItemStack item = player.getInventory().getItemInMainHand();

            for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                double radius = Math.sin(i) * 6;
                double y = Math.cos(i) * 6;
                for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
                    double x = Math.cos(a) * radius;
                    double z = Math.sin(a) * radius;
                    location.add(x, y, z);
                    location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(0 + random.nextInt(16), 240 + random.nextInt(16), 0 + random.nextInt(16)), 120 + new Random().nextInt(100)));
                    location.subtract(x, y, z);
                }
            }

            int bx = location.getBlockX();
            int by = location.getBlockY();
            int bz = location.getBlockZ();

            for (int x = bx - 6; x <= bx + 6; x++) {
                for (int y = by - 6; y <= by + 6; y++) {
                    for (int z = bz - 6; z <= bz + 6; z++) {
                        double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                        if (distance < 36) {
                            list.add(new Location(location.getWorld(), x, y, z).getBlock());
                        }
                    }
                }
            }

            MiningListener.registerBlocks(player, list, true);
            location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);

        }
    }

    @EventHandler
    public void darkBomb(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball && event.getEntity().getShooter() instanceof Player && event.getEntity().hasMetadata("darkBomb")) {

            Location location = event.getHitBlock().getLocation();
            Random random = new Random();
            List<Block> list = new ArrayList<>();
            Player player = (Player) event.getEntity().getShooter();

            ItemStack item = player.getInventory().getItemInMainHand();

            for (double i = 0; i <= Math.PI; i += Math.PI / 5) {
                double radius = Math.sin(i) * 3;
                double y = Math.cos(i) * 3;
                for (double a = 0; a < Math.PI * 2; a+= Math.PI / 5) {
                    double x = Math.cos(a) * radius;
                    double z = Math.sin(a) * radius;
                    location.add(x, y, z);
                    location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(200 + random.nextInt(19), 20 + random.nextInt(4), 20 + random.nextInt(4)), 120 + new Random().nextInt(100)));
                    location.subtract(x, y, z);
                }
            }

            int bx = location.getBlockX();
            int by = location.getBlockY();
            int bz = location.getBlockZ();

            for(int x = bx - 2; x <= bx + 2; x++) {
                for(int y = by - 2; y <= by + 2; y++) {
                    for(int z = bz - 2; z <= bz + 2; z++) {
                        double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                        if(distance < 7) {
                            Location check = new Location(location.getWorld(), x, y, z);
                            Block block = check.getBlock();
                            if(block.getType().name().endsWith("ORE") && !block.getType().name().startsWith("DEEPSLATE")) {
                                block.setType(Material.valueOf("DEEPSLATE_" + block.getType().name()));
                            }
                        }
                    }
                }
            }
            location.getWorld().playSound(location, Sound.ENTITY_IRON_GOLEM_DEATH, 1, 2);
            item.setAmount(item.getAmount() - 1);

        }
    }


}
