package com.productsmc.products.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;

public class InventoryBuilder {

    // The title
    private String title;
    // The inventory
    private Inventory inventory;
    // Pages for pagination
    private LinkedList<InventoryPage> pages;
    // Current page
    private int current;

    public InventoryBuilder(int rows, String title) {
        inventory = Bukkit.createInventory(null, rows*9, title);
        pages = new LinkedList<InventoryPage>();
        this.title = title;
    }

    /**
     * Get the inventory
     * @return the inventory
     */

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Get the amount of rows in the inventory
     * @return the rows
     */

    public int getRows() {
        return inventory.getSize()/9;
    }

    /**
     * Get the size of the inventory
     * @return the size
     */

    public int getSize() {
        return inventory.getSize();
    }

    /**
     * Set different patterns of a material around the inventory
     * @param item the item
     * @param pattern a pattern
     */

    public void setPattern(ItemStack item, InventoryPattern pattern) {
        int columnOne = 0;
        int columnTwo = 8;
        switch(pattern) {
            case FILL:
                for(int i = 0; i < inventory.getSize(); i++)
                    if(inventory.getItem(i) == null)
                        setItem(i, item);
                break;
            case BORDER:
                for(int i = 0; i < 9; i++)
                    if(inventory.getItem(i) == null)
                        setItem(i, item);
                for(int i = inventory.getSize()-8; i < inventory.getSize(); i++)
                    if(inventory.getItem(i) == null)
                        setItem(i, item);
                for(int i = 0; i < getRows(); i++) {
                    if(inventory.getItem(columnOne) == null)
                        setItem(i, item);
                    if(inventory.getItem(columnTwo) == null)
                        setItem(i, item);
                    columnOne += 9;
                    columnTwo += 9;
                }
                break;
            case SIDES:
                for(int i = 0; i < getRows(); i++) {
                    if(inventory.getItem(columnOne) == null)
                        setItem(i, item);
                    if(inventory.getItem(columnTwo) == null)
                        setItem(i, item);
                    columnOne += 9;
                    columnTwo += 9;
                }
                break;
            case TOP_BOTTOM:
                for(int i = 0; i < 9; i++)
                    if(inventory.getItem(i) == null)
                        setItem(i, item);
                for(int i = inventory.getSize()-9; i < inventory.getSize(); i++)
                    if(inventory.getItem(i) == null)
                        setItem(i, item);
                break;
        }
    }

    /**
     * Set an item at a slot
     * @param slot the slot
     * @param item the item
     */

    public void setItem(int slot, ItemStack item) {
        ItemStack set = item;
        if(item instanceof ItemButton)
            set = ((ItemButton) item).getItem();
        inventory.setItem(slot, set);
        for(InventoryPage page : pages)
            page.getInventory().setItem(slot, set);
    }

    /**
     * Set an item button at a slot
     * @param slot the slot
     * @param item the item button
     */

    public void setItemButton(int slot, ItemButton item) {
        inventory.setItem(slot, item.getItem());
        for(InventoryPage page : pages)
            page.getInventory().setItem(slot, item.getItem());
    }

    /**
     * Shortcut to open an inventory to a player
     * @param player the player
     */

    public void openInventory(Player player) {
        if(pages != null && !pages.isEmpty())
            player.openInventory(pages.get(0).getInventory());
        else
            player.openInventory(inventory);
    }

    /**
     * Get a page from the page number
     * @param slot the slot
     * @return the page
     */

    public InventoryPage getPage(int slot) {
        current = slot;
        return pages.get(slot);
    }

    /**
     * Get the first page
     * @return the first page
     */

    public InventoryPage getFirstPage() {
        return pages.get(0);
    }

    /**
     * Get the next page, if there isn't one return this page
     * @return the next page
     */

    public InventoryPage getNextPage() {
        current = current+1 >= pages.size() ? current : current+1;
        return pages.get(current);
    }

    /**
     * Get the previous page, if there isn't one return this page
     * @return the previous page
     */

    public InventoryPage getPreviousPage() {
        current = current-1 < 0 ? current : current-1;
        return pages.get(current);
    }

    /**
     * Get the last page
     * @return the last page
     */

    public InventoryPage getLastPage() {
        current = pages.indexOf(pages.getLast());
        return pages.get(current);
    }

    /**
     * Get the current page
     * @return the current page
     */

    public InventoryPage getCurrentPage() {
        return pages.get(current);
    }

    public int getCurrentPageNumber() {
        return current;
    }

    public LinkedList<InventoryPage> getPages() {
        return pages;
    }

    /**
     * Get the title
     * @return the title of the inventory
     */

    public String getTitle() {
        return title;
    }

    /**
     * Add pagination and pages
     * @param items the items and item buttons
     * @param slots an array to iterate items over, [0]: starting slot, [1]: ending slot
     */

    public void addPagination(ItemStack[] items, int[] slots) {
        Inventory newInventory = Bukkit.createInventory(null, inventory.getSize(), title);
        newInventory.setContents(inventory.getContents());
        slots[1] += 1;
        for(int i = 0; i < 1; i++) {
            ItemStack[] pageItems = new ItemStack[slots[1]-slots[0]];
            for(int j = 0; j < pageItems.length; j++) {
                if(j >= items.length || items[j] == null)
                    break;
                pageItems[j] = items[j];
            }
            InventoryPage page = new InventoryPage(newInventory, pageItems, slots);
            pages.add(page);
            if(items.length > pageItems.length) {
                i = -1;
                ItemStack[] newItems = new ItemStack[items.length-pageItems.length];
                int count = 0;
                for(int j = pageItems.length; j < items.length; j++) {
                    if(j >= items.length || items[j] == null)
                        break;
                    newItems[count] = items[j];
                    count++;
                }
                items = newItems;
            }
        }
    }

}
