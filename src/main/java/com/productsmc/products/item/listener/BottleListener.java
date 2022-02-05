package com.productsmc.products.item.listener;

import com.productsmc.products.Products;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;

public class BottleListener implements Listener {

    @EventHandler
    public void sprayBottle(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Location location = player.getLocation();
        Random random = new Random();

        // Prevention of using while opening crates
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().name().endsWith("GLASS")) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Spray Bottle")) {

            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
            DailyQuest daily = DailyQuest.REGROWTH;
            Map<DailyQuest, Integer> dailies = user.getDailies();
            int progress = -1;
            int regrown = 0;

            for (double i = 0; i <= Math.PI; i += Math.PI / 15) {
                double radius = Math.sin(i) * 10;
                double y = Math.cos(i) * 10;
                for (double a = 0; a < Math.PI * 2; a+= Math.PI / 15) {
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

            for(int x = bx - 10; x <= bx + 10; x++) {
                for(int y = by - 10; y <= by + 10; y++) {
                    for(int z = bz - 10; z <= bz + 10; z++) {
                        double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                        if(distance < 100) {
                            Location check = new Location(location.getWorld(), x, y, z);
                            if(check.getBlock().getBlockData() instanceof Ageable) {
                                Ageable crop = (Ageable) check.getBlock().getBlockData();

                                if(crop.getAge() < crop.getMaximumAge()) {
                                    regrown++;
                                }

                                crop.setAge(crop.getMaximumAge());
                                check.getBlock().setBlockData(crop);

                            }
                        }
                    }
                }
            }
            location.getWorld().playSound(location, Sound.ENTITY_IRON_GOLEM_DEATH, 1, 2);
            item.setAmount(item.getAmount() - 1);

            if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

                progress = dailies.get(daily);

                if(progress + regrown >= daily.getAmount(config.getInt("daily.level"))) {

                    daily.finishQuest(player);
                    dailies.put(daily, -1);
                    DailyQuest.checkCompletedDailies(player);

                } else {
                    dailies.put(daily, progress + regrown);
                }

            }

        }
    }

    @EventHandler
    public void darkBottle(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Location location = player.getLocation();
        Random random = new Random();

        // Prevention of using while opening crates
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().name().endsWith("GLASS")) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Dark Bottle")) {

            for (double i = 0; i <= Math.PI; i += Math.PI / 5) {
                double radius = Math.sin(i) * 2;
                double y = Math.cos(i) * 2;
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
