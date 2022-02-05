package com.productsmc.products.leaderboard;

import com.productsmc.products.user.UserConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Comparator;
import java.util.UUID;

public class LevelComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        UUID id1 = (UUID) o1;
        UUID id2 = (UUID) o2;

        FileConfiguration config1 = new UserConfig(id1).getConfig();
        FileConfiguration config2 = new UserConfig(id2).getConfig();

        int level1 = config1.getInt("player.level");
        int level2 = config2.getInt("player.level");

        if(level1 == level2) {
            return 0;
        } else if(level1 > level2) {
            return -1;
        } else {
            return 1;
        }

    }

}
