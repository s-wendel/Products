package com.productsmc.products.profession.mining;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import products.Products;
import products.item.Item;
import products.item.ItemType;
import products.item.Mat;
import products.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MiningListener implements Listener {

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(!player.getWorld().getName().equals("ul_Spawn") || player.getGameMode() != GameMode.SURVIVAL) 
			return;
		event.setCancelled(true);
		List<Block> blocks = new ArrayList<>();
		blocks.add(event.getBlock());
		registerBlocks(player, blocks);
		
	}
	
	public static void registerBlocks(Player player, List<Block> blocks) {
		int total = 0;
		for(Block block : blocks) {
			Material material = block.getType();
			boolean stop = true;
			int shards = 0;
			switch(material) {
			case STONE: case COBBLESTONE: case ANDESITE: case STONE_BRICKS: case MOSSY_COBBLESTONE: case LIGHT_GRAY_CONCRETE:
				stop = false;
				shards = 1;
				Mat.STONE.add(player.getUniqueId(), 1);
				break;
			case COAL_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(1, 3);
				Mat.COAL_ORE.add(player.getUniqueId(), 1);
				break;
			case IRON_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(2, 4);
				Mat.IRON_ORE.add(player.getUniqueId(), 1);
				break;
			case COPPER_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(2, 5);
				Mat.COPPER_ORE.add(player.getUniqueId(), 1);
				break;
			case GOLD_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(3, 6);
				Mat.GOLD_ORE.add(player.getUniqueId(), 1);
				break;
			case REDSTONE_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(4, 8);
				Mat.REDSTONE_ORE.add(player.getUniqueId(), 1);
				break;
			case LAPIS_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(3, 11);
				Mat.LAPIS_ORE.add(player.getUniqueId(), 1);
				break;
			case DIAMOND_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(7, 10);
				Mat.DIAMOND_ORE.add(player.getUniqueId(), 1);
				break;
			case EMERALD_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(10, 13);
				Mat.EMERALD_ORE.add(player.getUniqueId(), 1);
				break;
			case DEEPSLATE_COAL_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(1, 3);
				Mat.DARK_COAL.add(player.getUniqueId(), 1);
				block.setType(Material.COAL_ORE);
				break;
			case DEEPSLATE_IRON_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(2, 4);
				Mat.DARK_IRON.add(player.getUniqueId(), 1);
				block.setType(Material.IRON_ORE);
				break;
			case DEEPSLATE_COPPER_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(2, 5);
				Mat.DARK_COPPER.add(player.getUniqueId(), 1);
				block.setType(Material.COPPER_ORE);
				break;
			case DEEPSLATE_GOLD_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(3, 6);
				Mat.DARK_GOLD.add(player.getUniqueId(), 1);
				block.setType(Material.GOLD_ORE);
				break;
			case DEEPSLATE_REDSTONE_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(4, 8);
				Mat.DARK_REDSTONE.add(player.getUniqueId(), 1);
				block.setType(Material.REDSTONE_ORE);
				break;
			case DEEPSLATE_LAPIS_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(3, 11);
				Mat.DARK_LAPIS.add(player.getUniqueId(), 1);
				block.setType(Material.LAPIS_ORE);
				break;
			case DEEPSLATE_DIAMOND_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(7, 10);
				Mat.DARK_DIAMOND.add(player.getUniqueId(), 1);
				block.setType(Material.DIAMOND_ORE);
				break;
			case DEEPSLATE_EMERALD_ORE:
				stop = false;
				shards = ThreadLocalRandom.current().nextInt(10, 13);
				Mat.DARK_EMERALD.add(player.getUniqueId(), 1);
				block.setType(Material.EMERALD_ORE);
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
					ItemType pick = ItemType.of(name);
					new Item(pick).ability(player, block.getLocation());
				}
			}
			total += shards;
	        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
	        user.setShards(user.getShards()+shards);
	        Location location = block.getLocation();
	        Products.getInstance().blocks.put(location, block.getType());
	        block.setType(Material.BEDROCK);
			int darkChance = 1;
	        new BukkitRunnable() {
	        	@Override
	        	public void run() {
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
	        		} else
	        			block.setType(Products.getInstance().blocks.get(location));
	        		Products.getInstance().blocks.remove(location);
	        	}
	        }.runTaskLater(Products.getInstance(), 15*20);
		}
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 2f);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "+ " + ChatColor.YELLOW + total + "♢"));
	}
	
	@EventHandler
	public void foodChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
	
}
