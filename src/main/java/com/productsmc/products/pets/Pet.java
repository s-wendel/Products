package com.productsmc.products.pets;

import com.productsmc.products.Products;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Rarity;
import com.productsmc.products.profession.farming.FarmingListener;
import com.productsmc.products.profession.mining.MiningListener;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.util.ProductsUtil;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public enum Pet {

    // PETS MUST HAVE 3 LINES OF DESCRIPTION
    CHICKEN("Chicken", Rarity.COMMON, "7d3a8ace-e045-4eba-ab71-71dbf525daf1", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==", "1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893", "Chicken Bombs", "Every crop harvested has a 1% Chance to", "summon Chicken Bombs that harvest crops in a big area", ""),
    CREEPER("Creeper", Rarity.COMMON, "ee5df8d7-c61e-4f23-b9f1-9de33dc81da9", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODMxYmEzOWExNmNhNDQyY2FiOTM1MDQwZGRjNzI2ZWRhZDlkZDI3NmVjZmFmNmZlZTk5NzY3NjhiYjI3NmJjMCJ9fX0=", "831ba39a16ca442cab935040ddc726edad9dd276ecfaf6fee9976768bb276bc0", "Aww Man", "Every block mined has a 2% Chance to create", "an explosion that mines blocks in a big area", ""),
    TURTLE("Turtle", Rarity.UNCOMMON, "245f22b4-2c7c-4a9c-86fa-9ec64c54e4fa", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGE0MDUwZTdhYWNjNDUzOTIwMjY1OGZkYzMzOWRkMTgyZDdlMzIyZjlmYmNjNGQ1Zjk5YjU3MThhIn19fQ==", "0a4050e7aacc4539202658fdc339dd182d7e322f9fbcc4d5f99b5718a", "Hard Shell", "Gain 2 Hearts while this pet is equipped", "These hearts are non-stackable", ""),
    MERCHANT("Merchant", Rarity.UNCOMMON, "943947ea-3e1a-4fdc-85e5-f538379f05e9", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWYxMzc5YTgyMjkwZDdhYmUxZWZhYWJiYzcwNzEwZmYyZWMwMmRkMzRhZGUzODZiYzAwYzkzMGM0NjFjZjkzMiJ9fX0=", "5f1379a82290d7abe1efaabbc70710ff2ec02dd34ade386bc00c930c461cf932", "True Trade", "Every player hit, block mined, or crop harvested has", "a 1% Chance to give a random, rare item", ""),
    PUFFERFISH("Pufferfish", Rarity.RARE, "258e3114-368c-48a1-85fd-be580912f0df", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxNTI4NzZiYzNhOTZkZDJhMjI5OTI0NWVkYjNiZWVmNjQ3YzhhNTZhYzg4NTNhNjg3YzNlN2I1ZDhiYiJ9fX0=", "17152876bc3a96dd2a2299245edb3beef647c8a56ac8853a687c3e7b5d8bb", "Poisonous Toxins", "Everytime you are damaged you reflect", "10% Damage back to the damager" , ""),
    TIGER("Tiger", Rarity.RARE, "a93c91d3-40dd-4843-9065-2b8834ce46ee", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWVjYjQ1MmM5MTEzOWU3NGY1ZWRkYWRlMzc3YzdkYzk5NmEyMGI4NTJmZTk0MjA4MDFmMWFhYzk4MjZmN2YxIn19fQ==", "1ecb452c91139e74f5eddade377c7dc996a20b852fe9420801f1aac9826f7f1", "Fast Legs", "Gain Haste I infinitely", "while this pet is equipped", ""),
    WITCH("Witch", Rarity.EPIC, "7f92b3d6-5ee0-4ab6-afae-2206b9514a63", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlMTNkMTg0NzRmYzk0ZWQ1NWFlYjcwNjk1NjZlNDY4N2Q3NzNkYWMxNmY0YzNmODcyMmZjOTViZjlmMmRmYSJ9fX0=", "20e13d18474fc94ed55aeb7069566e4687d773dac16f4c3f8722fc95bf9f2dfa", "Aching Potion", "Every player hit has a 10% Chance to give", "the hit player Slowness I, Weakness I, &", "lose 5% of their Max Health"),
    THIEF("Thief", Rarity.EPIC, "c5f99f02-8d19-492a-9dfd-d331c29a9e42", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWNiNDI3YTljNGZkYjRlYzQyYzA0OTFmYzQ3MzEyOWQ3Y2M3NDkwM2JjYzYwM2Q5ODU3Nzg3MDZiMWI0MiJ9fX0=", "9cb427a9c4fdb4ec42c0491fc473129d7cc74903bcc603d985778706b1b42", "Natural Scoundrel", "Gain 2% Steal Chance (Chance of getting", "getting double items from Crates, Trading", "or Forging)"),
    PARROT("Parrot", Rarity.EPIC, "e9754a09-69c6-4e01-8f5e-ad640f0359df", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ0ODJhNjVkMDA0YmNlYjcxNDEzNDk2MzY5MTE2MzBkZmZiYTY4ZmYyMWI4ZDUxZDkxNzc4NTBmOTQ0YzZmZiJ9fX0=", "4d482a65d004bceb7141349636911630dffba68ff21b8d51d9177850f944c6ff", "Rally Burst", "Gain 1.25x Pet Ability Rate (Pet", "abilities activate faster) (Non Stackable)", ""),
    GOLEM("Golem", Rarity.LEGENDARY, "15ca9840-5053-4931-b9a0-29a2c7e64175", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2M3NTM1NzFlYjVkZTFjYjM2YjU5M2E5MGY5OTcxZTI3OGFkMzdkZWVhN2Q5OGFjZmFhZWUyZTU5ZjNmYSJ9fX0=", "7c753571eb5de1cb36b593a90f9971e278ad37deea7d98acfaaee2e59f3fa", "Rock Hard", "Gain Resistance I & Strength I infinitely", "while this pet is equipped", ""),
    WARDEN("Warden", Rarity.LEGENDARY, "bec780d5-c7c0-4771-822d-cc5936ba5eb6", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGY3YjMzMGY4N2U1YTY1NDJhNzFkYmY4OTIzNGVjNWM0YjM3ZWVhMmQzMDE2NjE2MDIyMmZkZThmMmFhN2E5MiJ9fX0=", "4f7b330f87e5a6542a71dbf89234ec5c4b37eea2d30166160222fde8f2aa7a92", "Dark Aura", "Gain +0.5% Dark Bomb Chance (Summon a Dark Bomb", "on yourself) In addition, gain +3% Dark Ore Chance", "")
    /**
     * 3 COMMONS
     * 2 UNCOMMONS
     * 2 RARES
     * 3 EPICS
     * 2 LEGENDARIES
     */
    ;

    private String name;
    private Rarity rarity;
    private String uuid;
    private String value;
    private String url;
    private String passive;
    private String[] passiveDescription;

    public static Pet getRandom(Rarity rarity) {
        int random = new Random().nextInt(101);
        switch(rarity) {
            case COMMON:
                if(random <= 50) {
                    return Pet.CHICKEN;
                }
                return Pet.CREEPER;
            case UNCOMMON:
                if(random <= 50) {
                    return Pet.MERCHANT;
                }
                return Pet.TURTLE;
            case RARE:
                if(random <= 50) {
                    return Pet.PUFFERFISH;
                }
                return Pet.TIGER;
            case EPIC:
                if(random <= 50) {
                    return Pet.WITCH;
                }
                return Pet.THIEF;
            case LEGENDARY:
                if(random <= 50) {
                    return Pet.GOLEM;
                }
                return Pet.WARDEN;
        }
        return Pet.CHICKEN;
    }

    Pet(String name, Rarity rarity, String uuid, String value, String url, String passive, String... passiveDescription) {
        this.name = name;
        this.rarity = rarity;
        this.uuid = uuid;
        this.value = value;
        this.url = url;
        this.passive = passive;
        this.passiveDescription = passiveDescription;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getPassive() {
        return passive;
    }

    public String[] getPassiveDescription() {
        return passiveDescription;
    }

    public ItemStack getHead() {
        return ProductsUtil.getHead(uuid, value, url);
    }

    public void ability(Player player, Player hit, Location location, int damage) {
        List<Block> list = new ArrayList<>();
        Random random = new Random();

        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

        FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
        DailyQuest daily = DailyQuest.PET_ABILITIES;
        Map<DailyQuest, Integer> dailies = user.getDailies();
        int progress = -1;

        if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

            progress = dailies.get(daily);

            if(progress + 1 >= daily.getAmount(config.getInt("daily.level"))) {

                daily.finishQuest(player);
                dailies.put(daily, -1);
                DailyQuest.checkCompletedDailies(player);

            } else {
                dailies.put(daily, progress + 1);
            }

        }

        switch (this) {

            case WARDEN:

                if (random.nextInt(200) <= 1) {

                    user.setPetAbilities(user.getPetAbilities() + 1);

                    for(String roman : ProductsUtil.getRomanNumerals()) {
                        Quest quest = Quest.valueOf("ABILITY_MASTER_" + roman);
                        if(!user.getCompletedQuests().contains(quest) && user.getPetAbilities() >= quest.getAmount()) {
                            quest.finishQuest(player);
                        }
                    }

                    for (double i = 0; i <= Math.PI; i += Math.PI / 5) {
                        double radius = Math.sin(i) * 2.5;
                        double y = Math.cos(i) * 2.5;
                        for (double a = 0; a < Math.PI * 2; a+= Math.PI / 5) {
                            double x = Math.cos(a) * radius;
                            double z = Math.sin(a) * radius;
                            location.add(x, y, z);
                            location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(200 + random.nextInt(19), 20 + random.nextInt(4), 20 + random.nextInt(4)), 120 + new Random().nextInt(100)));
                            location.subtract(x, y, z);
                        }
                    }

                    int bx = location.getBlockX();
                    int by = location.getBlockY();
                    int bz = location.getBlockZ();

                    for(int x = bx - 2; x <= bx + 2; x++) {
                        for(int y = by - 2; y <= by + 2; y++) {
                            for(int z = bz - 2; z <= bz + 2; z++) {
                                double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                                if(distance < 7) {
                                    Location check = new Location(location.getWorld(), x, y, z);
                                    Block block = check.getBlock();
                                    if(block.getType().name().endsWith("ORE") && !block.getType().name().startsWith("DEEPSLATE")) {
                                        block.setType(Material.valueOf("DEEPSLATE_" + block.getType().name()));
                                    }
                                }
                            }
                        }
                    }
                    location.getWorld().playSound(location, Sound.ENTITY_IRON_GOLEM_DEATH, 1, 2);

                }

            case TIGER:

                user.setPetAbilities(user.getPetAbilities() + 1);

                for(String roman : ProductsUtil.getRomanNumerals()) {
                    Quest quest = Quest.valueOf("ABILITY_MASTER_" + roman);
                    if(!user.getCompletedQuests().contains(quest) && user.getPetAbilities() >= quest.getAmount()) {
                        quest.finishQuest(player);
                    }
                }


                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999999, 0, false));
                break;
            case TURTLE:

                user.setPetAbilities(user.getPetAbilities() + 1);

                for(String roman : ProductsUtil.getRomanNumerals()) {
                    Quest quest = Quest.valueOf("ABILITY_MASTER_" + roman);
                    if(!user.getCompletedQuests().contains(quest) && user.getPetAbilities() >= quest.getAmount()) {
                        quest.finishQuest(player);
                    }
                }


                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999999, 0, false));
                break;
            case GOLEM:

                user.setPetAbilities(user.getPetAbilities() + 1);

                for(String roman : ProductsUtil.getRomanNumerals()) {
                    Quest quest = Quest.valueOf("ABILITY_MASTER_" + roman);
                    if(!user.getCompletedQuests().contains(quest) && user.getPetAbilities() >= quest.getAmount()) {
                        quest.finishQuest(player);
                    }
                }


                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999999, 0, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999999, 0, false));
                break;
            case MERCHANT:
                if (random.nextInt(100) == 0) {

                    user.setPetAbilities(user.getPetAbilities() + 1);

                    for(String roman : ProductsUtil.getRomanNumerals()) {
                        Quest quest = Quest.valueOf("ABILITY_MASTER_" + roman);
                        if(!user.getCompletedQuests().contains(quest) && user.getPetAbilities() >= quest.getAmount()) {
                            quest.finishQuest(player);
                        }
                    }

                    Item[] items = new Item[] {Item.SPRAY_BOTTLE, Item.STEEL_SWORD, Item.COMMON_KEY, Item.DARK_COAL, Item.DARK_IRON, Item.DARK_COPPER, Item.GOLDEN_CARROT, Item.GOLDEN_BEETROOT };
                    player.getInventory().addItem(items[random.nextInt(items.length)].getItem());
                }
                break;
            case CHICKEN:
                if (random.nextInt(100) == 0) {

                    user.setPetAbilities(user.getPetAbilities() + 1);

                    for(String roman : ProductsUtil.getRomanNumerals()) {
                        Quest quest = Quest.valueOf("ABILITY_MASTER_" + roman);
                        if(!user.getCompletedQuests().contains(quest) && user.getPetAbilities() >= quest.getAmount()) {
                            quest.finishQuest(player);
                        }
                    }

                    for (int b = 0; b < 2; b++) {
                        int number = random.nextInt(5);
                        if (random.nextInt(2) == 0)
                            number = -number;
                        location = new Location(location.getWorld(), location.getX() + number, location.getY(), location.getZ() + number);
                        for (double i = 0; i <= Math.PI; i += Math.PI / 6) {
                            double radius = Math.sin(i) * 3;
                            double y = Math.cos(i) * 3;
                            for (double a = 0; a < Math.PI * 2; a += Math.PI / 6) {
                                double x = Math.cos(a) * radius;
                                double z = Math.sin(a) * radius;
                                location.add(x, y, z);
                                location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(240 + new Random().nextInt(16), 240 + new Random().nextInt(16), 255), 120 + new Random().nextInt(100)));
                                location.subtract(x, y, z);
                            }
                        }

                        int bx = location.getBlockX();
                        int by = location.getBlockY();
                        int bz = location.getBlockZ();

                        for (int x = bx - 3; x <= bx + 3; x++) {
                            for (int y = by - 3; y <= by + 3; y++) {
                                for (int z = bz - 3; z <= bz + 3; z++) {
                                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                                    if (distance < 9) {
                                        list.add(new Location(location.getWorld(), x, y, z).getBlock());
                                    }
                                }
                            }
                        }

                    }
                    FarmingListener.registerBlocks(player, list, true);
                    location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.25f);
                }
                break;
            case CREEPER:
                if (random.nextInt(100) <= 1) {

                    user.setPetAbilities(user.getPetAbilities() + 1);

                    for(String roman : ProductsUtil.getRomanNumerals()) {
                        Quest quest = Quest.valueOf("ABILITY_MASTER_" + roman);
                        if(!user.getCompletedQuests().contains(quest) && user.getPetAbilities() >= quest.getAmount()) {
                            quest.finishQuest(player);
                        }
                    }

                    for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                        double radius = Math.sin(i) * 4;
                        double y = Math.cos(i) * 4;
                        for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
                            double x = Math.cos(a) * radius;
                            double z = Math.sin(a) * radius;
                            location.add(x, y, z);
                            location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(0 + random.nextInt(16), 240 + random.nextInt(16), 0 + random.nextInt(16)), 120 + new Random().nextInt(100)));
                            location.subtract(x, y, z);
                        }
                    }

                    int bx = location.getBlockX();
                    int by = location.getBlockY();
                    int bz = location.getBlockZ();

                    for (int x = bx - 4; x <= bx + 4; x++) {
                        for (int y = by - 4; y <= by + 4; y++) {
                            for (int z = bz - 4; z <= bz + 4; z++) {
                                double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                                if (distance < 16) {
                                    list.add(new Location(location.getWorld(), x, y, z).getBlock());
                                }
                            }
                        }
                    }

                    //MiningListener.registerBlocks(player, list, true);
                    location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                }
                break;
            case PUFFERFISH:

                user.setPetAbilities(user.getPetAbilities() + 1);

                for(String roman : ProductsUtil.getRomanNumerals()) {
                    Quest quest = Quest.valueOf("ABILITY_MASTER_" + roman);
                    if(!user.getCompletedQuests().contains(quest) && user.getPetAbilities() >= quest.getAmount()) {
                        quest.finishQuest(player);
                    }
                }

                hit.setHealth(hit.getHealth() - (damage * 0.9) >= 0 ? hit.getHealth() - (damage * 0.9) : 0);

                hit.getWorld().playSound(hit.getLocation(), Sound.ENTITY_PUFFER_FISH_FLOP, 1f, 1.25f);

                break;
            case WITCH:
                if (random.nextInt(100) <= 10) {

                    user.setPetAbilities(user.getPetAbilities() + 1);

                    for(String roman : ProductsUtil.getRomanNumerals()) {
                        Quest quest = Quest.valueOf("ABILITY_MASTER_" + roman);
                        if(!user.getCompletedQuests().contains(quest) && user.getPetAbilities() >= quest.getAmount()) {
                            quest.finishQuest(player);
                        }
                    }

                    double modifier = hit.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 0.05;
                    hit.setHealth(player.getHealth() - modifier >= 0 ? player.getHealth() - modifier : 0);
                    hit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 45, 0));
                    hit.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 45, 0));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_HURT, 1f, 1.25f);
                }
                break;
        }
    }

}
