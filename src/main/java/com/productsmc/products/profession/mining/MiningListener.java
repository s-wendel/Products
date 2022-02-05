package com.productsmc.products.profession.mining;

import com.productsmc.products.Products;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Item;
import com.productsmc.products.item.Mat;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.quest.DailyQuest;
import com.productsmc.products.quest.Quest;
import com.productsmc.products.user.User;
import com.productsmc.products.user.UserConfig;
import com.productsmc.products.util.ProductsUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MiningListener implements Listener {

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(!player.getWorld().getName().equals("ul_Spawn") || player.getGameMode() != GameMode.SURVIVAL) 
			return;
		event.setCancelled(true);
		String name = event.getBlock().getType().toString();
		if(name.endsWith("ORE") || name.equals("STONE") || name.equals("COBBLESTONE") || name.equals("ANDESITE") || name.equals("STONE_BRICKS") || name.equals("MOSSY_COBBLESTONE") || name.equals("LIGHT_GRAY_CONCRETE")) {

			User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

			if(user.getLevel() < 4) {
				player.sendMessage(ChatColor.RED + "You must be Level 4 to Mine. Type '/quests' to level up!");
				return;
			}

			List<Block> blocks = new ArrayList<>();
			blocks.add(event.getBlock());
			registerBlocks(player, blocks, false);
			for(Pet pet : user.getEquippedPets()) {
				switch(pet) {
					case CREEPER:
						Pet.CREEPER.ability(player, null, event.getBlock().getLocation(), 0);
						break;
					case MERCHANT:
						Pet.MERCHANT.ability(player, null,null, 0);
						break;
					case WARDEN:
						Pet.WARDEN.ability(player, null,event.getBlock().getLocation(), 0);
						break;
				}
			}
		}
	}
	
	public static void registerBlocks(Player player, List<Block> blocks, boolean ability) {
		User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
		int total = 0;
		Random random = new Random();

		int mined = 0, dark = 0;

		for(Block block : blocks) {
			Material original = block.getType();
			Material material = block.getType();
			if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
				String itemName = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
				if (itemName.equals("Ore Upgrader")) {
					material = ProductsUtil.getNextOre(material);
				}
			}

			boolean stop = true;
			int shards = 0;
			switch(material) {
			case STONE: case COBBLESTONE: case ANDESITE: case STONE_BRICKS: case MOSSY_COBBLESTONE: case LIGHT_GRAY_CONCRETE:
				mined++;
				stop = false;
				shards = 1;
				Mat.STONE.add(player.getUniqueId(), 1);
				break;
			case COAL_ORE:
				mined++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(3, 6);
				Mat.COAL_ORE.add(player.getUniqueId(), 1);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("COAL_MINER_" + roman);
					if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.COAL_ORE) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case IRON_ORE:
				mined++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(4, 7);
				Mat.IRON_ORE.add(player.getUniqueId(), 1);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("IRON_MINER_" + roman);
					if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.IRON_ORE) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case COPPER_ORE:
				mined++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(5, 8);
				Mat.COPPER_ORE.add(player.getUniqueId(), 1);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("COPPER_MINER_" + roman);
					if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.COPPER_ORE) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case GOLD_ORE:
				mined++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(6, 9);
				Mat.GOLD_ORE.add(player.getUniqueId(), 1);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("GOLD_MINER_" + roman);
					if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.GOLD_ORE) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case REDSTONE_ORE:
				mined++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(8, 11);
				Mat.REDSTONE_ORE.add(player.getUniqueId(), 1);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("REDSTONE_MINER_" + roman);
					if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.REDSTONE_ORE) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case LAPIS_ORE:
				mined++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(2, 16);
				Mat.LAPIS_ORE.add(player.getUniqueId(), 1);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("LAPIS_MINER_" + roman);
					if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.LAPIS_ORE) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case DIAMOND_ORE:
				mined++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(8, 14);
				Mat.DIAMOND_ORE.add(player.getUniqueId(), 1);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("DIAMOND_MINER_" + roman);
					if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.DIAMOND_ORE) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case EMERALD_ORE:
				mined++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(12, 16);
				Mat.EMERALD_ORE.add(player.getUniqueId(), 1);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("EMERALD_MINER_" + roman);
					if(!user.getCompletedQuests().contains(quest) && user.getMat(Mat.EMERALD_ORE) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case DEEPSLATE_COAL_ORE:
				mined++;
				dark++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(10, 25);
				Mat.DARK_COAL.add(player.getUniqueId(), 1);
				block.setType(Material.COAL_ORE);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("DARK_ORES_" + roman);
					if(!user.getCompletedQuests().contains(quest) && (user.getMat(Mat.DARK_COAL) + user.getMat(Mat.DARK_IRON) + user.getMat(Mat.DARK_COPPER) + user.getMat(Mat.DARK_GOLD) + user.getMat(Mat.DARK_REDSTONE) + user.getMat(Mat.DARK_LAPIS) + user.getMat(Mat.DARK_DIAMOND) + user.getMat(Mat.DARK_EMERALD)) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case DEEPSLATE_IRON_ORE:
				mined++;
				dark++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(15, 30);
				Mat.DARK_IRON.add(player.getUniqueId(), 1);
				block.setType(Material.IRON_ORE);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("DARK_ORES_" + roman);
					if(!user.getCompletedQuests().contains(quest) && (user.getMat(Mat.DARK_COAL) + user.getMat(Mat.DARK_IRON) + user.getMat(Mat.DARK_COPPER) + user.getMat(Mat.DARK_GOLD) + user.getMat(Mat.DARK_REDSTONE) + user.getMat(Mat.DARK_LAPIS) + user.getMat(Mat.DARK_DIAMOND) + user.getMat(Mat.DARK_EMERALD)) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case DEEPSLATE_COPPER_ORE:
				mined++;
				dark++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(20, 35);
				Mat.DARK_COPPER.add(player.getUniqueId(), 1);
				block.setType(Material.COPPER_ORE);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("DARK_ORES_" + roman);
					if(!user.getCompletedQuests().contains(quest) && (user.getMat(Mat.DARK_COAL) + user.getMat(Mat.DARK_IRON) + user.getMat(Mat.DARK_COPPER) + user.getMat(Mat.DARK_GOLD) + user.getMat(Mat.DARK_REDSTONE) + user.getMat(Mat.DARK_LAPIS) + user.getMat(Mat.DARK_DIAMOND) + user.getMat(Mat.DARK_EMERALD)) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case DEEPSLATE_GOLD_ORE:
				mined++;
				dark++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(25, 40);
				Mat.DARK_GOLD.add(player.getUniqueId(), 1);
				block.setType(Material.GOLD_ORE);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("DARK_ORES_" + roman);
					if(!user.getCompletedQuests().contains(quest) && (user.getMat(Mat.DARK_COAL) + user.getMat(Mat.DARK_IRON) + user.getMat(Mat.DARK_COPPER) + user.getMat(Mat.DARK_GOLD) + user.getMat(Mat.DARK_REDSTONE) + user.getMat(Mat.DARK_LAPIS) + user.getMat(Mat.DARK_DIAMOND) + user.getMat(Mat.DARK_EMERALD)) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case DEEPSLATE_REDSTONE_ORE:
				mined++;
				dark++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(35, 50);
				Mat.DARK_REDSTONE.add(player.getUniqueId(), 1);
				block.setType(Material.REDSTONE_ORE);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("DARK_ORES_" + roman);
					if(!user.getCompletedQuests().contains(quest) && (user.getMat(Mat.DARK_COAL) + user.getMat(Mat.DARK_IRON) + user.getMat(Mat.DARK_COPPER) + user.getMat(Mat.DARK_GOLD) + user.getMat(Mat.DARK_REDSTONE) + user.getMat(Mat.DARK_LAPIS) + user.getMat(Mat.DARK_DIAMOND) + user.getMat(Mat.DARK_EMERALD)) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case DEEPSLATE_LAPIS_ORE:
				mined++;
				dark++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(15, 80);
				Mat.DARK_LAPIS.add(player.getUniqueId(), 1);
				block.setType(Material.LAPIS_ORE);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("DARK_ORES_" + roman);
					if(!user.getCompletedQuests().contains(quest) && (user.getMat(Mat.DARK_COAL) + user.getMat(Mat.DARK_IRON) + user.getMat(Mat.DARK_COPPER) + user.getMat(Mat.DARK_GOLD) + user.getMat(Mat.DARK_REDSTONE) + user.getMat(Mat.DARK_LAPIS) + user.getMat(Mat.DARK_DIAMOND) + user.getMat(Mat.DARK_EMERALD)) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case DEEPSLATE_DIAMOND_ORE:
				mined++;
				dark++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(45, 65);
				Mat.DARK_DIAMOND.add(player.getUniqueId(), 1);
				block.setType(Material.DIAMOND_ORE);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("DARK_ORES_" + roman);
					if(!user.getCompletedQuests().contains(quest) && (user.getMat(Mat.DARK_COAL) + user.getMat(Mat.DARK_IRON) + user.getMat(Mat.DARK_COPPER) + user.getMat(Mat.DARK_GOLD) + user.getMat(Mat.DARK_REDSTONE) + user.getMat(Mat.DARK_LAPIS) + user.getMat(Mat.DARK_DIAMOND) + user.getMat(Mat.DARK_EMERALD)) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			case DEEPSLATE_EMERALD_ORE:
				mined++;
				dark++;
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(55, 75);
				Mat.DARK_EMERALD.add(player.getUniqueId(), 1);
				block.setType(Material.EMERALD_ORE);

				for(String roman : ProductsUtil.getRomanNumerals()) {
					Quest quest = Quest.valueOf("DARK_ORES_" + roman);
					if(!user.getCompletedQuests().contains(quest) && (user.getMat(Mat.DARK_COAL) + user.getMat(Mat.DARK_IRON) + user.getMat(Mat.DARK_COPPER) + user.getMat(Mat.DARK_GOLD) + user.getMat(Mat.DARK_REDSTONE) + user.getMat(Mat.DARK_LAPIS) + user.getMat(Mat.DARK_DIAMOND) + user.getMat(Mat.DARK_EMERALD)) >= quest.getAmount()) {
						quest.finishQuest(player);
					}
				}
				break;
			default:
				break;
			}
			if(stop)
				continue;
			if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
				String name = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
				if(name.equals("Miner's Pick")) {
					shards++;
				} else {
					Item tool = Item.of(name);
					if(tool != null) {
						tool.ability(player, block.getLocation());
					}
				}
			}

			total += shards;
	        user.setShards(user.getShards()+shards);
	        Location location = block.getLocation();
	        Products.getInstance().blocks.put(location, Material.valueOf(original.name().replaceAll("DEEPSLATE_", "")));
	        block.setType(Material.BEDROCK);
	        new BukkitRunnable() {
	        	@Override
	        	public void run() {
					int darkChance = 1;
					if (ProductsUtil.hasArmorSet(player, "Dark")) {
						darkChance += 3;
					}
	        		if(ThreadLocalRandom.current().nextInt(100) <= darkChance) {
	        			switch(Products.getInstance().blocks.get(location)) {
	        			case COAL_ORE:
	        				block.setType(Material.DEEPSLATE_COAL_ORE);
	        				break;
	        			case IRON_ORE:
	        				block.setType(Material.DEEPSLATE_IRON_ORE);
	        				break;
	        			case COPPER_ORE:
	        				block.setType(Material.DEEPSLATE_COPPER_ORE);
	        				break;
	        			case GOLD_ORE:
	        				block.setType(Material.DEEPSLATE_GOLD_ORE);
	        				break;
	        			case REDSTONE_ORE:
	        				block.setType(Material.DEEPSLATE_REDSTONE_ORE);
	        				break;
	        			case LAPIS_ORE:
	        				block.setType(Material.DEEPSLATE_LAPIS_ORE);
	        				break;
	        			case DIAMOND_ORE:
	        				block.setType(Material.DEEPSLATE_DIAMOND_ORE);
	        				break;
	        			case EMERALD_ORE:
	        				block.setType(Material.DEEPSLATE_EMERALD_ORE);
	        				break;
	        			default:
	        				block.setType(Products.getInstance().blocks.get(location));
	        				break;
	        			}
	        		} else {
	        			try {
							block.setType(Products.getInstance().blocks.get(location));
						} catch(Exception exception) {}
					}
	        		Products.getInstance().blocks.remove(location);
	        	}
	        }.runTaskLater(Products.getInstance(), 15*20);
		}
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 2f);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "+ " + ChatColor.YELLOW + total + "♢"));

		FileConfiguration config = new UserConfig(player.getUniqueId()).getConfig();
		DailyQuest daily = DailyQuest.ORES;
		Map<DailyQuest, Integer> dailies = user.getDailies();
		int progress = -1;

		if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

			progress = dailies.get(daily);

			if(progress + mined >= daily.getAmount(config.getInt("daily.level"))) {

				daily.finishQuest(player);
				dailies.put(daily, -1);
				DailyQuest.checkCompletedDailies(player);

			} else {
				dailies.put(daily, progress + mined);
			}

		}

		daily = DailyQuest.DARK_ORES;

		if(dailies.containsKey(daily) && dailies.get(daily) != -1) {

			progress = dailies.get(daily);

			if(progress + dark >= daily.getAmount(config.getInt("daily.level"))) {

				daily.finishQuest(player);
				dailies.put(daily, -1);
				DailyQuest.checkCompletedDailies(player);

			} else {
				dailies.put(daily, progress + dark);
			}

		}

	}
	
	@EventHandler
	public void foodChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void enchant(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.ENCHANTING_TABLE) {
			event.setCancelled(true);
		}
	}
	
}
