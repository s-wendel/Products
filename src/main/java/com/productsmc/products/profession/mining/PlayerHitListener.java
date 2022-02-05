package com.productsmc.products.profession.mining;

import com.productsmc.products.Products;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PlayerHitListener implements Listener {

    Random random = new Random();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerHit(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            if(event.isCancelled()) {
                return;
            }
            Player damager = (Player) event.getDamager();
            Player hit = (Player) event.getEntity();
            ItemStack weapon = damager.getInventory().getItemInMainHand();
            boolean slowImmune = false;
            boolean weakImmune = false;
            double reducedDamage = 1;
            int armorPieces = 0;
            for(ItemStack armor : hit.getInventory().getArmorContents()) {
                if(armor != null) {
                    armorPieces++;
                    if(armor.hasItemMeta() && armor.getItemMeta().hasDisplayName()) {
                        switch(ChatColor.stripColor(armor.getItemMeta().getDisplayName()).toUpperCase()) {
                            case "INHIBITOR":
                                weakImmune = true;
                                break;
                            case "BASTION":
                                reducedDamage -= 0.35;
                                break;
                            case "ZENITH":
                                slowImmune = true;
                                break;
                        }
                    }
                }
            }
            User user = Products.getInstance().getUserManager().getUser(damager.getUniqueId());
            if(!event.isCancelled()) {
                for(Pet pet : user.getEquippedPets()) {
                    switch(pet) {
                        case WITCH:
                            Pet.WITCH.ability(damager, hit, hit.getLocation(), 0);
                            break;
                        case MERCHANT:
                            Pet.MERCHANT.ability(damager, hit, null, 0);
                            break;
                        case PUFFERFISH:
                            Pet.PUFFERFISH.ability(damager, hit, hit.getLocation(), 0);
                            break;
                    }
                }
            }
            event.setDamage(event.getDamage() * reducedDamage);
            if(weapon != null && weapon.hasItemMeta() && weapon.getItemMeta().hasDisplayName()) {
                String name = ChatColor.stripColor(weapon.getItemMeta().getDisplayName()).toUpperCase();
                switch(name) {
                    case "CRYOLANCE":
                        if(!slowImmune && hit.getHealth() > 0) {
                            hit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0, false));
                        }
                        break;
                    case "DETONATOR":
                        if(random.nextInt(101) <= 14) {
                            hit.getWorld().playSound(hit.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.25f);
                            event.setDamage(event.getDamage() * 2);
                        }
                        break;
                    case "STEEL SWORD":
                        if(armorPieces >= 1) {
                            event.setDamage(event.getDamage() * 1.15);
                        }
                        break;
                    case "POISON FUNGUS":
                        double interval = event.getDamage() * 0.25;
                        if(event.getDamage() > 0 && !event.isCancelled()) {
                            event.setDamage(0);
                            new BukkitRunnable() {
                                int ticks = 0;
                                @Override
                                public void run() {

                                    if(ticks == 1) {
                                        cancel();
                                    }
                                    if(hit.getHealth() - interval <= 0) {
                                        hit.setHealth(0);
                                        cancel();
                                    }
                                    hit.setHealth(hit.getHealth() - interval);
                                    ticks++;
                                }
                            }.runTaskTimer(Products.getInstance(), 20, 20);
                        }
                        break;
                    case "BLOOD BLADE":
                        if(hit.isDead() || hit.getHealth() - event.getDamage() <= 0) {
                            damager.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0, false));
                            damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, false));
                        }
                        break;
                }
            }
        }
    }

}