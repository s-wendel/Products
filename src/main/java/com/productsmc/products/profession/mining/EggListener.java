package com.productsmc.products.profession.mining;

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
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import products.Products;
import products.user.User;

import java.util.ArrayList;
import java.util.List;

public class EggListener implements Listener {

	@EventHandler
	public void eggHeaver(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Egg Heaver")) {
				if(System.currentTimeMillis() >= user.eggCooldown+650) {
					player.launchProjectile(Egg.class);
					user.eggCooldown = System.currentTimeMillis();
				}
			}
		}
	}
	
	@EventHandler
	public void projectileHit(ProjectileHitEvent event) {
		if(event.getEntity() instanceof Egg) {
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
	        case NORTH: case EAST: case SOUTH: case WEST:
	        	blocks.add(block.getRelative(BlockFace.NORTH));
	        	blocks.add(block.getRelative(BlockFace.UP));
	        	blocks.add(block.getRelative(BlockFace.SOUTH));
	        	blocks.add(block.getRelative(BlockFace.DOWN));
	        	break;
	        default:
	        	break;
	        }
	         
	        location.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, location, 1);
			location.getWorld().playSound(location, Sound.ENTITY_AXOLOTL_SPLASH, 1, 2);
	        MiningListener.registerBlocks(player, blocks);
	        
		}
	}
	
}
