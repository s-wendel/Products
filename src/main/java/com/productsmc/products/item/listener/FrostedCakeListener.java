package com.productsmc.products.item.listener;

import com.productsmc.products.Products;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FrostedCakeListener implements Listener {

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Frosted Cake")) {

            User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

            user.setSnacksConsumed(user.getSnacksConsumed() + 1);
            for(String roman : ProductsUtil.getRomanNumerals()) {
                Quest quest = Quest.valueOf("STOMACH_THAT_" + roman);
                if(!user.getCompletedQuests().contains(quest) && user.getSnacksConsumed() >= quest.getAmount()) {
                    quest.finishQuest(player);
                }
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 3600, 0, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3600, 0, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3600, 0, false));
            player.playSound(player.getLocation(), Sound.BLOCK_CAKE_ADD_CANDLE, 1f, 1.5f);
            item.setAmount(item.getAmount() - 1);

        }
    }

}
