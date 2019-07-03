package com.vampire.rpg;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ItemAPI {
	public static boolean hasDur(ItemStack i){
		try{
			i.setDurability((short) 0);
			return true;
		}
		catch(Exception e){
		}
		return false;
	}
	public static boolean isNotWorthy(Player p){
		return !p.getName().equals(Pluginc.Owner_Name) && !p.getName().equals(Pluginc.Host_Name) && !p.isOp();
	}
	public static boolean hasLore(ItemStack i){
		if(i==null)
			return false;
		if(!i.hasItemMeta())
			return false;
		if(i.getItemMeta().hasLore())
			return true;
		return false;
	}
	public static ItemStack setSkin(ItemStack item, String nick) {
	        SkullMeta meta = (SkullMeta) item.getItemMeta();
	        meta.setOwner(nick);
	        item.setItemMeta(meta);
	        return item;
	    }
	public static ItemStack ColoredItem(ItemStack item,String name,List<Integer> rgb){
    	LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
    	meta.setColor(Color.fromRGB(rgb.get(0),rgb.get(1),rgb.get(2)));
    	meta.setDisplayName(name);
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	meta.spigot().setUnbreakable(true);
    	//List<String> lore = null;
    	//meta.setLore(lore);
    	item.setItemMeta(meta);
    	return item;
    }
	public static ItemStack LoreItem(ItemStack item,String name){
    	ItemMeta meta = (ItemMeta)item.getItemMeta();
    	meta.setDisplayName(name);
    	//ArrayList<String> lore=new ArrayList<String>();
    	//lore.add(lores);
    	//meta.setLore(lore);
    	item.setItemMeta(meta);
    	return item;
	}
	public static ItemStack LoreItem(ItemStack item,String name,String lores){
    	ItemMeta meta = (ItemMeta)item.getItemMeta();
    	meta.setDisplayName(name);
    	ArrayList<String> lore=new ArrayList<String>();
    	lore.add(lores);
    	meta.setLore(lore);
    	item.setItemMeta(meta);
    	return item;
	}
	 public static ItemStack getPlayerSkull(String playerName) {
	        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
	        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
	        headMeta.setOwner(playerName);
	        head.setItemMeta(headMeta);
	        return head;
	    }
	public static ItemStack LoreItem(ItemStack item,String name,String lores,boolean hideAtt){
    	ItemMeta meta = (ItemMeta)item.getItemMeta();
    	meta.setDisplayName(name);
    	if(hideAtt){
    		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	}
    	ArrayList<String> lore=new ArrayList<String>();
    	lore.add(lores);
    	meta.setLore(lore);
    	item.setItemMeta(meta);
    	return item;
	}
	public static ItemStack LoreItem(ItemStack item,String name,String lores,boolean hideAtt, boolean unBreak){
    	ItemMeta meta = (ItemMeta)item.getItemMeta();
    	meta.setDisplayName(name);
    	if(unBreak){
        	meta.spigot().setUnbreakable(true);
        	meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        	}
    	if(hideAtt){
    		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	}
    	ArrayList<String> lore=new ArrayList<String>();
    	lore.add(lores);
    	meta.setLore(lore);
    	item.setItemMeta(meta);
    	return item;
	}
	public static ItemStack LoreItem(ItemStack item,String name,String lores,boolean hideAtt, boolean unBreak,short dur){
    	ItemMeta meta = (ItemMeta)item.getItemMeta();
    	meta.setDisplayName(name);
    	if(unBreak && hasDur(item)){
        	meta.spigot().setUnbreakable(true);
        	}
    	if(hideAtt){
    		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	}
    	ArrayList<String> lore=new ArrayList<String>();
    	lore.add(lores);
    	meta.setLore(lore);
    	item.setItemMeta(meta);
    	item.setDurability(dur);
    	return item;
	}
	public static ItemStack AdLoreItem(ItemStack item,String name,String[] lo,boolean hideAtt,boolean unBreak){
    	ItemMeta meta = (ItemMeta)item.getItemMeta();
    	meta.setDisplayName(name);
    	if(unBreak && hasDur(item)){
        	meta.spigot().setUnbreakable(true);
        	}
    	if(hideAtt){
    		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	}
    	List<String> cardslist = Arrays.asList(lo);
    	ArrayList<String> lore=new ArrayList<String>();
    	for (String g:lo){
    	lore.add(g);
    	}
    	meta.setLore(cardslist);
    	item.setItemMeta(meta);
    	return item;
    }
	public static ItemStack AdLoreItem(ItemStack item,String name,ArrayList<String> lo,boolean hideAtt,boolean unBreak){
    	ItemMeta meta = (ItemMeta)item.getItemMeta();
    	meta.setDisplayName(name);
    	if(unBreak && hasDur(item)){
        	meta.spigot().setUnbreakable(true);
        	}
    	if(hideAtt){
    		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	}
    	ArrayList<String> lore=new ArrayList<String>();
    	for (String g:lo){
    	lore.add(g);
    	}
    	meta.setLore(lore);
    	item.setItemMeta(meta);
    	return item;
    }
	public static final String PREFIX = "http://textures.minecraft.net/texture/";
	public static ItemStack getSkull(String url) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        if(url.isEmpty())return head;
       
       
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }
	public static ItemStack InvSkull(String name,String url){
		ItemStack head= ItemAPI.getSkull(ItemAPI.PREFIX+url); 
		ItemMeta meta= head.getItemMeta();
		meta.setDisplayName(name);
		head.setItemMeta(meta);
		return head;
		
	}
}
