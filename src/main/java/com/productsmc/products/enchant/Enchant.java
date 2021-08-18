package com.productsmc.products.enchant;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class Enchant {

	protected String name;
	protected boolean isCombat;
	protected boolean isMining;
	protected boolean isFarming;
	
	public abstract int execute(int level, Player player, LivingEntity entity, Location location);
	
	public String getName() {
		return name;
	}
	
	public boolean isCombatEnchant() {
		return isCombat;
	}
	
	public boolean isMiningEnchant() {
		return isMining;
	}
	
	public boolean isFarmingEnchant() {
		return isFarming;
	}
	
}
