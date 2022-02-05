package com.productsmc.products.util;

import com.productsmc.products.Products;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;
import java.util.function.Consumer;

public class ItemButton extends ItemStack {

    // The key to be set to the item
    private final NamespacedKey key = new NamespacedKey(Products.getInstance(), "itemButton");
    // The actions to be ran
    private Consumer<InventoryClickEvent> action;
    // The item
    private ItemStack item;
    // The id
    private UUID id;

    /**
     * Call a new ItemButton class with an item and actions to be ran on click
     * @param item the item
     * @param action action being ran on click
     */

    public ItemButton(ItemStack item, Consumer<InventoryClickEvent> action) {
        this.item = item;
        this.action = action;
        id = UUID.randomUUID();
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta != null) {
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            if(!container.has(key, PersistentDataType.STRING)) {
                container.set(key, PersistentDataType.STRING, id.toString());
                item.setItemMeta(itemMeta);
                Products.getInstance().getInventoryManager().addItem(this);
            }
        }
    }

    /**
     * Call a quick instance of an ItemButton class
     * @param item the item
     * @param action action being ran on click
     * @return new ItemButton instance
     */

    public static ItemButton of(ItemStack item, Consumer<InventoryClickEvent> action) {
        return new ItemButton(item, action);
    }

    /**
     * The method to run the actions
     * @param event the event
     */

    public void run(InventoryClickEvent event) {
        action.accept(event);
    }

    /**
     * Get the item
     * @return the item
     */

    public ItemStack getItem() {
        return item;
    }

    /**
     * The item's id used for persistent data
     * @return the id
     */

    public UUID getID() {
        return id;
    }

    /**
     * Get the actions to be ran
     * @return actions
     */

    public Consumer<InventoryClickEvent> getAction() {
        return action;
    }

}