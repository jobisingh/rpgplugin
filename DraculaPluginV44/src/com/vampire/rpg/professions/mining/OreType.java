package com.vampire.rpg.professions.mining;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.vampire.rpg.utils.VamMath;

public enum OreType {
	//name,Material of the ore,min lvl to mine it
	COAL("Coal",Material.COAL_ORE,1,10,20),
	IRON("Iron",Material.IRON_ORE,10,18,32),
	RUBY("Ruby",Material.REDSTONE_ORE,20,59,80),
	EMERALD("Emerald",Material.EMERALD_ORE,30,89,112),
	SAPPHIRE("Sapphire",Material.LAPIS_ORE,40,234,312),
	DRAKONITE("Drakonite",Material.QUARTZ_ORE,50,782,1000),
	;
	private String name;
	private Material mat;
	private int lvl;
	private int minExp;
	private int MaxExp;
	OreType(String s,Material m,int level,int minExp,int MaxExp){
		this.name=s;
		this.mat=m;
		this.lvl=level;
		this.minExp=minExp;
		this.MaxExp=MaxExp;
		//Block b1= new Block();
		//Location l=new Location(null, 500,500,500);
	//	Block b= l.getBlock();
	//	b.sett
	}
	public Material getMaterial(){
		return this.mat;
	}
	public String getName(){
		return this.name();
	}
	public int getLevel(){
		return this.lvl;
	}
	public int getExp(){
		return VamMath.randInt(this.minExp, this.MaxExp);
	}
}
