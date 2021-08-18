package com.productsmc.products.profession;

public enum Profession {

	MERCHANT,
	MINING,
	FARMING,
	COMBAT;
	
	public int getExperienceFor(int level) {
		switch(this) {
		case MERCHANT:
			return Math.round(level*25*2^((level/10+1)/2));
		case COMBAT:
			return 6+(int) Math.round(Math.pow(level, 1.5));
		case FARMING:
			return 10+(int) Math.round(Math.pow(50, 2));
		case MINING:
			return 7+(int) Math.round(Math.pow(50, 1.6));
		}
		return 0;
	}
	
}
