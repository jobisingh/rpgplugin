package com.vampire.rpg.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class BannerBuilder {
	DyeColor baseColor;
	BannerMeta meta;
	public BannerBuilder(DyeColor dye){
		this.baseColor=dye;
		ItemStack i=new ItemStack(Material.BANNER);
		this.meta=(BannerMeta)i.getItemMeta();
		this.meta.setBaseColor(dye);
	}
	/*public void addPatterns(DyeColor dye,PatternType p){
		this.meta.addPattern(new Pattern(dye,p));
	}*/
	public BannerBuilder addPattern(DyeColor dye,PatternType p){
		BannerBuilder b= new BannerBuilder(this.baseColor);
		b.meta=this.meta;
		b.meta.addPattern(new Pattern(dye,p));
		return b;
	}
	public BannerMeta generate(){
		return this.meta;
	}
	public ItemStack generateItem(){
		ItemStack i=new ItemStack(Material.BANNER);
		this.meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		i.setItemMeta(this.meta);
		return i;
	}
}
