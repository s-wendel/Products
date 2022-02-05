package com.productsmc.products.level;

import com.productsmc.products.Products;
import com.productsmc.products.badge.Badge;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Rarity;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.trail.Trail;
import com.productsmc.products.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Level {

    //134390 XP TOTLA
    public static int getExperienceNeeded(int level) {
        return (int) Math.round((level <= 15 ? 40 * Math.pow(1.2, level) : (40 * Math.pow(1.25, 15)) + 40 * Math.pow(1.05, level))/10.0) * 10;
    }

    public static String getReward(int level) {
        switch(level) {
            case 2: case 12: case 19: case 34: case 42:
                return ChatColor.DARK_AQUA + "1x Rare Key";
            case 3: case 11: case 21: case 31: case 41: case 51: case 61: case 71: case 81: case 91: case 101: case 111: case 121:
                return ChatColor.LIGHT_PURPLE + "1x Gen Slot";
            case 35:
                return ChatColor.DARK_GREEN + "1x Uncommon Pet";
            case 45: case 69:
                return ChatColor.DARK_AQUA + "1x Rare Pet";
            case 65: case 98:
                return ChatColor.AQUA + "1x Epic Pet";
            case 78: case 97:
                return ChatColor.LIGHT_PURPLE + "1x Legendary Pet";
            case 46: case 59: case 66: case 74: case 96: case 85:
                return ChatColor.DARK_AQUA + "1x Rare Mat Bundle";
            case 47: case 58: case 64: case 76: case 94: case 95: case 89:
                return ChatColor.AQUA + "1x Epic Mat Bundle";
            case 24: case 54: case 84: case 114:
                return ChatColor.AQUA + "0.1x Multiplier";
            case 4:
                return "1x Unlocked Mining! " + ChatColor.DARK_GRAY + "/mine";
            case 5: case 14: case 26:
                return ChatColor.DARK_AQUA + "2x Mining Bomb";
            case 40: case 48: case 57: case 67: case 79: case 93: case 52:
                return ChatColor.DARK_AQUA + "3x Mining Bomb";
            case 13: case 27: case 32:
                return ChatColor.DARK_AQUA + "2x Farming Bomb";
            case 39: case 49: case 53: case 68: case 80: case 92: case 86:
                return ChatColor.DARK_AQUA + "3x Farming Bomb";
            case 20:
                return "1x Chicken Pet";
            case 23:
                return "1x Creeper Pet";
            case 6: case 43: case 73:
                return "2x Common Key";
            case 7: case 16: case 28: case 55: case 72: case 87: case 70:
                return ChatColor.LIGHT_PURPLE + "2x Dark Bomb";
            case 8:
                return "1x Unlocked Farming! " + ChatColor.DARK_GRAY + "/farming";
            case 9: case 17: case 22: case 29: case 36: case 44: case 56: case 82:
                return ChatColor.DARK_GREEN + "4x Spray Bottle";
            case 10:
                return "1x Unlocked Flight! " + ChatColor.DARK_GRAY + "/fly";
            case 18: case 33: case 37: case 60: case 83: case 30:
                return ChatColor.AQUA + "1x Epic Key";
            case 88: case 63:
                return ChatColor.AQUA + "2x Epic Key";
            case 62:
                return "1x Common Lootbox";
            case 25:
                return "1x Sparks Trail";
            case 99:
                return "1x Crimson Flames Trail";
            case 100:
                return "1x Star Badge";
            case 50:
                return ChatColor.DARK_GREEN + "1x Clouds Trail";
            case 38: case 77: case 90:
                return ChatColor.LIGHT_PURPLE + "1x Legendary Key";
            case 15: case 75:
                return ChatColor.RED + "1x Daily Quest Slot";
            default:
                if(level % 25 == 0) {
                    return ChatColor.LIGHT_PURPLE + "4x Legendary Key";
                }
                else if(level % 10 == 0) {
                    return ChatColor.LIGHT_PURPLE + "1x Legendary Key";
                }
                else if(level % 5 == 0) {
                    return ChatColor.AQUA + "4x Epic Key";
                }
                else if(level % 3 == 0) {
                    return ChatColor.AQUA + "2x Epic Key";
                }
                else if(level % 2 == 0) {
                    return ChatColor.AQUA + "1x Epic Mat Bundle";
                }
                return ChatColor.DARK_AQUA + "1x Rare Key";
        }
    }

    public static void giveReward(String reward, Player player) {

        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

        reward = ChatColor.stripColor(reward);
        String[] split = reward.split("x ");

        int quantity = 0;

        if(!split[0].contains(".")) {
            quantity = Integer.parseInt(split[0]);
        }

        reward = split[1];
        Item item = Item.of(reward);

        if(item == null || reward.endsWith("Slot")) {
            ItemStack itemStack = null;
            switch(reward) {
                case "Star Badge":
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + Badge.STAR.getPermission());
                    break;
                case "Sparks Trail":
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + Trail.SPARKS.getPermission());
                    break;
                case "Cloud Trail":
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + Trail.CLOUDS.getPermission());
                    break;
                case "Crimson Flames Trail":
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + Trail.CRIMSON_FLAMES.getPermission());
                    break;
                case "Epic Mat Bundle":
                    itemStack = Item.EPIC_MAT_BUNDLE.getItem();
                    itemStack.setAmount(quantity);
                    player.getInventory().addItem(itemStack);
                    break;
                case "Rare Mat Bundle":
                    itemStack = Item.RARE_MAT_BUNDLE.getItem();
                    itemStack.setAmount(quantity);
                    player.getInventory().addItem(itemStack);
                    break;
                case "Uncommon Mat Bundle":
                    itemStack = Item.UNCOMMON_MAT_BUNDLE.getItem();
                    itemStack.setAmount(quantity);
                    player.getInventory().addItem(itemStack);
                    break;
                case "Common Mat Bundle":
                    itemStack = Item.COMMON_MAT_BUNDLE.getItem();
                    itemStack.setAmount(quantity);
                    player.getInventory().addItem(itemStack);
                    break;
                case "Daily Quest Slot":
                    user.setQuestSlots(user.getQuestSlots() + 1);
                    break;
                case "Creeper Pet":
                    user.givePet(Pet.CREEPER);
                    break;
                case "Chicken Pet":
                    user.givePet(Pet.CHICKEN);
                    break;
                case "Common Pet":
                    user.givePet(Pet.getRandom(Rarity.COMMON));
                    break;
                case "Uncommon Pet":
                    user.givePet(Pet.getRandom(Rarity.UNCOMMON));
                    break;
                case "Rare Pet":
                    user.givePet(Pet.getRandom(Rarity.RARE));
                    break;
                case "Epic Pet":
                    user.givePet(Pet.getRandom(Rarity.EPIC));
                    break;
                case "Legendary Pet":
                    user.givePet(Pet.getRandom(Rarity.LEGENDARY));
                    break;
                case "Gen Slot":
                    user.setSlots(user.getSlots() + quantity);
                    break;
                case "Pet Slot":
                    user.setPetSlots(user.getPetSlots() + quantity);
                    break;
                case "Multiplier":
                    user.setMultiplier(user.getMultiplier() + 0.1);
                default:
                    if(reward.endsWith("Trail")) {
                        reward = reward.replaceAll(" ", "_");
                        reward = reward.replace("Trail", "");
                        reward = reward.toUpperCase().substring(0, reward.length() - 1);
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + player.getName() + " permission set " + Trail.valueOf(reward).getPermission());
                    } else if(reward.endsWith("Badge")) {
                        reward = reward.replaceAll(" ", "_");
                        reward = reward.replace("Badge", "");
                        reward = reward.toUpperCase().substring(0, reward.length() - 1);
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + player.getName() + " permission set " + Badge.valueOf(reward).getPermission());
                    } else {

                    }
            }
        } else {
            ItemStack itemStack = item.getItem();
            itemStack.setAmount(quantity);
            player.getInventory().addItem(itemStack);
        }

    }

    public static void playerLevelUp(Player player, int level) {

        String reward = getReward(level);
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
        ItemStack item = null;

        giveReward(reward, player);

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        player.sendMessage("");
        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Level " + level);
        player.sendMessage("");
        player.sendMessage("Rewards:");
        player.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + reward);

        if(level == 4) {
            player.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.WHITE + "1x Unlocked Forging! " + ChatColor.DARK_GRAY + "/forge");
        }

        for(Item unlock : Item.getUnlocks(level)) {
            player.sendMessage(ChatColor.DARK_GRAY + "> " + unlock.getRarity().getColor() + unlock.getName() + " Recipe " + ChatColor.DARK_GRAY + "/forge");
        }

        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_GRAY + "Nice job!");

        user.setLevel(level);
        user.setExperience(user.getExperience() - getExperienceNeeded(level));

        if(user.getExperience() >= getExperienceNeeded(user.getLevel() + 1)) {
            playerLevelUp(player, user.getLevel() + 1);
        }

    }

}
