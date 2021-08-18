package com.productsmc.products.item;

public enum ItemType {

	MINER_PICK("Miner's Pick"),
	BRISK_PICK("Brisk Pick"),
	COBALT_PICK("Cobalt Pick"),
	ORE_EXTRACTOR("Ore Extractor"),
	EGG_HEAVER("Egg Heaver");
	
	private String name;
	
	ItemType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static ItemType of(String name) {
		for(ItemType type : ItemType.values()) {
			if(type.getName().equals(name)) {
				return type;
			}
		}
		return null;
	}
	
}
