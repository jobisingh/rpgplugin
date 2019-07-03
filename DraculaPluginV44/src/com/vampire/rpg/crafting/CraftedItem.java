package com.vampire.rpg.crafting;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CraftedItem {
	private ItemStack CraftedItemStack;
	private  ItemStack[] recipe;
	public CraftedItem(ItemStack finished,ItemStack[] recipe){
		this.CraftedItemStack=finished;
		this.recipe=recipe;
	}
	public void setDurabillity(short du){
			CraftedItemStack.setDurability(du);
	}
	public  ItemStack getCraftedItem(){
		return CraftedItemStack;
		
	}
	public ItemStack[] getRecipe(){
		return recipe;
		
	}
	public Material getCraftedItemMat(){
		return CraftedItemStack.getType();
		
	}
	public String getCraftedItemName(){
		return CraftedItemStack.getItemMeta().getDisplayName();
		
	}
}
