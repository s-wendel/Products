package com.productsmc.products.item;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import products.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Item {

	private ItemType type;
	
	public Item(ItemType type) {
		this.type = type;
	}
	
	public void ability(Player player, Location location) {
		if(type == null)
			return;
		switch(type) {
		case BRISK_PICK:
			player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 0, true, false, false));
			break;
		case COBALT_PICK:
			int random = new Random().nextInt(101);
			
			if(random <= 5) {
				for (double i = 0; i <= Math.PI; i += Math.PI / 15) {
					double radius = Math.sin(i) * 5;
					double y = Math.cos(i) * 5;
					for (double a = 0; a < Math.PI * 2; a+= Math.PI / 15) {
						double x = Math.cos(a) * radius;
						double z = Math.sin(a) * radius;
						location.add(x, y, z);
						location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(40 + new Random().nextInt(70), 120 + new Random().nextInt(40), 255), 120 + new Random().nextInt(100)));
						location.subtract(x, y, z);
					}
				}
				
				int bx = location.getBlockX();
		        int by = location.getBlockY();
		        int bz = location.getBlockZ();

		        for(int x = bx - 5; x <= bx + 5; x++) {
		        	for(int y = by - 5; y <= by + 5; y++) {
		        		for(int z = bz - 5; z <= bz + 5; z++) {
		        			double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
		        			if(distance < 25) { 
		                    	Location check = new Location(location.getWorld(), x, y, z);
	                			Map<Location, Material> blocks = Products.getInstance().blocks;
	                			List<Location> remove = new ArrayList<>();
		                    	for(Location ore : blocks.keySet()) {
		                    		if(check.equals(ore)) {
		                    			Block block = ore.getBlock();
		                    			int darkChance = 4;
		                    			if(ThreadLocalRandom.current().nextInt(100) <= darkChance) {
	                	        			switch(blocks.get(ore)) {
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
	                	        				ore.getBlock().setType(blocks.get(ore));
	                	        				break;
	                	        			}
	                	        		} else
	                	        			ore.getBlock().setType(blocks.get(ore));
		                    			remove.add(ore);
		                    		}
		                    	}
		                    	for(Location removed : remove)
		                    		blocks.remove(removed);
		        			}
		        		}
		        	}
		        }
				location.getWorld().playSound(location, Sound.ENTITY_IRON_GOLEM_DEATH, 1, 2);
			}
			
			break;
		default:
			break;
		}
	}

}
