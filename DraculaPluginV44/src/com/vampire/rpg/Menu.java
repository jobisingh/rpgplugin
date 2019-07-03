package com.vampire.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menu {
	public int Pages;
	public ArrayList<ItemStack> presetatbottom;
	public ArrayList<ItemStack> contents;
	public  String name;
	public Menu(ArrayList<ItemStack> items,ArrayList<ItemStack> bottom,int pages,String name){
		this.Pages=pages;
		this.contents=items;
		this.presetatbottom=bottom;
		this.name=name;
	}
	public static int getItemLoc(int row,int columm){
		int h=(row-1)*9;
		int g= h+columm-1;
		return g;
	}
	public HashMap<ItemStack,Integer> getBottomPlacements(ArrayList<ItemStack> as){
		HashMap<ItemStack,Integer> hash= new HashMap<ItemStack,Integer>();
		switch(as.size()){
		case 1:
			hash.put(as.get(0), getItemLoc(6, 5));
			break;
		case 2:
			hash.put(as.get(0), 48);
			hash.put(as.get(1), 49);
			break;
		case 3:
			hash.put(as.get(0), 48);
			hash.put(as.get(1), 49);
			hash.put(as.get(2), 50);
		case 4:
			hash.put(as.get(0), 47);
			hash.put(as.get(1), 48);
			hash.put(as.get(2), 49);
			hash.put(as.get(3), 50);
		case 5:
			hash.put(as.get(0), 47);
			hash.put(as.get(1), 48);
			hash.put(as.get(2), 49);
			hash.put(as.get(3), 50);
			hash.put(as.get(4), 51);
		case 6:
			hash.put(as.get(0), 46);
			hash.put(as.get(1), 47);
			hash.put(as.get(2), 48);
			hash.put(as.get(3), 49);
			hash.put(as.get(4), 50);
			hash.put(as.get(5), 51);
			break;
		case 7:
			hash.put(as.get(0), 46);
			hash.put(as.get(1), 47);
			hash.put(as.get(2), 48);
			hash.put(as.get(3), 49);
			hash.put(as.get(4), 50);
			hash.put(as.get(5), 51);
			hash.put(as.get(6), 52);
			break;
		default:
			return null;
		}
		return hash;
	}
	
	
	public ArrayList<Inventory> generateInventory(){
		//Inventory inv= Bukkit.createInventory(null, 54,this.name);
		ArrayList<Inventory> invs = new ArrayList<Inventory>();
		HashMap<ItemStack,Integer> bottomshash= getBottomPlacements(this.presetatbottom);
		if(this.contents.size()<45){
			Inventory inv= Bukkit.createInventory(null, 54,this.name);
			for(Entry<ItemStack,Integer> en:bottomshash.entrySet()){
				inv.setItem(en.getValue(), en.getKey());
			}
			for(ItemStack is:this.contents){
				inv.addItem(is);
			}
			invs.add(inv);
			return invs;
		}
		else{
			int leftoverforlastpage= this.contents.size()%45;
			int pages= (int)this.contents.size()/45 +1;
			for(int i=0;i<pages;i++){
				Inventory inv= Bukkit.createInventory(null, 54,this.name+" : Page "+String.valueOf(i+1));
				for(Entry<ItemStack,Integer> en:bottomshash.entrySet()){
					inv.setItem(en.getValue(), en.getKey());
				}
				if(i==pages-1){
					ArrayList<ItemStack> asd= new ArrayList<ItemStack>(this.contents.subList(this.contents.size()-1-leftoverforlastpage-1,this.contents.size()-1));
					for(ItemStack isc:asd){
						inv.addItem(isc);
					}
				}
				else{
				ArrayList<ItemStack> asd= new ArrayList<ItemStack>(this.contents.subList(45*i,44+(45*i)));
				for(ItemStack isc:asd){
					inv.addItem(isc);
				}
				}
				invs.add(inv);
			}
			return invs;
			
		}
			
	}
}
