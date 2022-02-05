package com.productsmc.products.profession.mining;

import com.productsmc.products.Products;
import com.productsmc.products.pets.Pet;
import com.productsmc.products.user.User;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EggListener implements Listener {

	@EventHandler
	public void eggHeaver(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(player.getWorld().getName().equals("ul_Spawn") && item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Egg Heaver")) {
				if(System.currentTimeMillis() >= user.eggCooldown+750) {
					player.launchProjectile(Egg.class);
					user.eggCooldown = System.currentTimeMillis();
				}
			}
		}
	}

	@EventHandler
	public void chickenSpawn(CreatureSpawnEvent event) {
		if(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void projectileHit(ProjectileHitEvent event) {
		if(event.getEntity() instanceof Egg) {
			event.setCancelled(true);
			Player player = (Player) event.getEntity().getShooter();
			Block block = event.getHitBlock();
			Location location = block.getLocation();		
	        List<Block> blocks = new ArrayList<>(); 
        	blocks.add(block);
	        
	        switch(event.getHitBlockFace()) {
	        case UP: case DOWN:
	        	blocks.add(block.getRelative(BlockFace.NORTH));
	        	blocks.add(block.getRelative(BlockFace.EAST));
	        	blocks.add(block.getRelative(BlockFace.SOUTH));
	        	blocks.add(block.getRelative(BlockFace.WEST));
	        	break;
	        case EAST: case WEST:
	        	blocks.add(block.getRelative(BlockFace.NORTH));
	        	blocks.add(block.getRelative(BlockFace.UP));
	        	blocks.add(block.getRelative(BlockFace.SOUTH));
	        	blocks.add(block.getRelative(BlockFace.DOWN));
	        	break;
	        case NORTH: case SOUTH:
	        	blocks.add(block.getRelative(BlockFace.EAST));
	        	blocks.add(block.getRelative(BlockFace.UP));
	        	blocks.add(block.getRelative(BlockFace.WEST));
	        	blocks.add(block.getRelative(BlockFace.DOWN));
	        	break;
	        default:
	        	break;
	        }

	        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
			for(Pet pet : user.getEquippedPets()) {
				switch(pet) {
					case CREEPER:
						Pet.CREEPER.ability(player, null, event.getHitBlock().getLocation(), 0);
						break;
				}
			}
	         
			location.getWorld().playSound(location, Sound.ENTITY_AXOLOTL_SPLASH, 1, 2);
	        MiningListener.registerBlocks(player, blocks, false);
	        
		}
	}
	
}
