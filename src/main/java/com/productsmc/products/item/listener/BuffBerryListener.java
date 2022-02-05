package com.productsmc.products.item.listener;

import com.productsmc.products.Products;
import com.productsmc.products.generator.Generator;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BuffBerryListener implements Listener {

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Buff Berry")) {

            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

            user.setSnacksConsumed(user.getSnacksConsumed() + 1);
            for(String roman : ProductsUtil.getRomanNumerals()) {
                Quest quest = Quest.valueOf("STOMACH_THAT_" + roman);
                if(!user.getCompletedQuests().contains(quest) && user.getSnacksConsumed() >= quest.getAmount()) {
                    quest.finishQuest(player);
                }
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3600, 0, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3600, 0, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 3600, 0, false));
            player.playSound(player.getLocation(), Sound.ITEM_GLOW_INK_SAC_USE, 1f, 1.5f);
            item.setAmount(item.getAmount() - 1);

        }
    }

}
