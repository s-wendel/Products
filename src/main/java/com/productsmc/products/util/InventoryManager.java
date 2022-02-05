package com.productsmc.products.util;

import com.productsmc.products.Products;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class InventoryManager {

    // Stored item buttons using their id
    private HashMap<UUID, ItemButton> items;

    public InventoryManager() {
        items = new HashMap<UUID, ItemButton>();
        Products.getInstance().getServer().getPluginManager().registerEvents(new InventoryListener(), Products.getInstance());
        new BukkitRunnable() {
            public void run() {
                if(!items.isEmpty())
                    for(ItemButton button : items.values())
                        if(button.getItem() == null)
                            items.remove(button.getID());
            }
        }.runTaskTimer(Products.getInstance(), 1200, 1200);
    }

    /**
     * Add an item button
     * @param item the item button
     */

    public void addItem(ItemButton item) {
        items.put(item.getID(), item);
    }

    /**
     * Get the actions to be ran on click
     * @param id the id
     * @return the actions
     */

    public Consumer<InventoryClickEvent> getAction(UUID id) {
        return items.get(id).getAction();
    }

    class InventoryListener implements Listener {

        /**
         * Fired on click of an inventory
         * @param event inventoryclickevent
         */

        @EventHandler
        public void inventoryClick(InventoryClickEvent event) {
            ItemStack item = event.getCurrentItem();
            final NamespacedKey key = new NamespacedKey(Products.getInstance(), "itemButton");

            if(item == null)
                return;
            ItemMeta itemMeta = item.getItemMeta();

            if(itemMeta == null)
                return;
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            if(container.has(key, PersistentDataType.STRING)) {
                UUID id = UUID.fromString(container.get(key, PersistentDataType.STRING));

                if(!items.containsKey(id))
                    return;

                event.setCancelled(true);
                items.get(id).run(event);
            }

        }

    }

}
