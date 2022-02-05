package com.productsmc.products.crate;

import com.productsmc.products.item.Item;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Crate {

    private String name;
    private Location location;
    private ItemStack key;
    private List<Item> items;
    private Item rotational;
    private List<Item> rotationals;

    public Crate(String name, Location location, ItemStack key, List<Item> items, List<Item> rotationals) {
        this.name = name;
        this.location = location;
        this.key = key;
        this.items = items;
        this.rotationals = rotationals;
        rotational = Item.BUFF_BERRY;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() { return location; }

    public ItemStack getKey() {
        return key;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Item> getRotationals() {
        return rotationals;
    }

    public void setRotational(Item rotational) {
        this.rotational = rotational;
    }

    public Item getRotational() {
        return rotational;
    }

}
