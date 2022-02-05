package com.productsmc.products.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryPage {

    // The inventory
    private Inventory inventory;
    // Items and item buttons
    private ItemStack[] items;
    // [0]: Starting iterator slot, [1]: Ending iterator slot
    private int[] iterator;

    public InventoryPage(Inventory inventory, ItemStack[] items, int[] iterator) {
        this.inventory = inventory;
        this.items = items;
        this.iterator = iterator;
    }

    /**
     * Get the inventory
     * @return the inventory
     */

    public Inventory getInventory() {
        int count = iterator[0];
        for(ItemStack item : items) {
            if(count >= iterator[1])
                break;
            inventory.setItem(count, item);
            count++;
        }
        return inventory;
    }

    /**
     * Get an item at a slot
     * @param slot the slot
     * @return the item
     */

    public ItemStack getItem(int slot) {
        return items[slot];
    }

    /**
     * Get an itembutton at a slot, if not throws an exception
     * @param slot the slot
     * @return the item button
     */

    public ItemButton getItemButton(int slot) {
        return (ItemButton) items[slot];
    }

    /**
     * Get all the items
     * @return the items
     */

    public ItemStack[] getItems() {
        return items;
    }

    /**
     * Get an array of the iterator slots
     * @return an array of the iterator slots
     */

    public int[] getIteratorSlots() {
        return iterator;
    }

    /**
     * Get the starting slot of the iterator
     * @return the starting slot of the iterator
     */

    public int getStartingIteratorSlot() {
        return iterator[0];
    }

    /**
     * Get the ending slot of the iterator
     * @return the ending slot of the iterator
     */

    public int getEndingIteratorSlot() {
        return iterator[1];
    }

}