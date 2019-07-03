package com.vampire.rpg.professions;

public enum Profession {
	FARMING("Farming"),//get EXP from farming? ,pig /food banner
	MINING("Mining"), // get EXP from mining ores //diamond piackaxe banner
	TAMING("Taming"), // get EXP from taming mounts(or pets?)? , WOLF banner
	CRAFTING("Crafting"), // get EXP from crafting items // SWORD banner
	FISHING("Fishing"), //get EXP from catching fish , FISH banner
	COOKING("Cooking"), //get EXP from baking/cooking food and eating it,TACO banner OR cake banner
	BLACKSMITHING("Blacksmithing"); // BLACKSMITH banner
	public String name;
	Profession(String s){
		this.name=s;
	}
}