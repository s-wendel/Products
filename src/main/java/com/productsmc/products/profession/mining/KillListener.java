package com.productsmc.products.profession.mining;

import com.productsmc.products.Products;
import com.productsmc.products.item.Mat;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class KillListener implements Listener {

    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {

        if(event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            user.setKilled(user.getKilled() + 1);
            for(String roman : ProductsUtil.getRomanNumerals()) {
                Quest quest = Quest.valueOf("HUNTER_" + roman);
                if(!user.getCompletedQuests().contains(quest) && user.getKilled() >= quest.getAmount()) {
                    quest.finishQuest(player);
                }
            }

            FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
            DailyQuest daily = DailyQuest.KILLER;
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

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player killed = event.getPlayer();
                User killedUser = Products.getInstance().getUserManager().getUser(killed.getUniqueId());
                for(Pet pet : killedUser.getEquippedPets()) {
                    switch(pet) {
                        case GOLEM:
                            Pet.GOLEM.ability(killed, null,null, 0);
                            break;
                        case TURTLE:
                            Pet.TURTLE.ability(killed, null,null, 0);
                            break;
                        case TIGER:
                            Pet.TIGER.ability(killed, null,null, 0);
                            break;
                    }
                }
            }
        }.runTaskLater(Products.getInstance(), 5);
    }

    /*@EventHandler
    public void playerHit(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
            for(Pet pet : user.getEquippedPets()) {
                switch(pet) {
                    case PHOENIX:
                        Pet.PHOENIX.ability(player, player.getLocation(), 0);
                        break;
                }
            }
        }
    }*/

}
