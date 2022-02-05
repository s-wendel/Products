package com.productsmc.products.crate;

import java.util.HashMap;
import java.util.Map;

public class CrateManager {

    private Map<String, Crate> crates;

    public CrateManager() {
        crates = new HashMap<>();
    }

    public void addCrate(Crate crate) {
        crates.put(crate.getName(), crate);
    }

    public Crate getCrate(String name) {
        return crates.get(name);
    }

    public Map<String, Crate> getCrates() {
        return crates;
    }

}
