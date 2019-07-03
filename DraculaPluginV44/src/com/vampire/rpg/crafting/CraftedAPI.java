package com.vampire.rpg.crafting;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.items.EquipType;
import com.vampire.rpg.items.ItemCreator;
import com.vampire.rpg.utils.VamInventory;

public class CraftedAPI {
	/*public static boolean hasItem(PlayerInventory pinv,ItemStack i){
		for(int k=0;k<36;k++){
			ItemStack is=pinv.getItem(k);
			if(is!=null &&is.isSimilar(i))
				return true;
			if(is.getAmount()>1){
				is.setAmount(1);
				if(is.isSimilar(i))
					return true;
			}
		}
		return false;
	}*/
	public static CraftedItem stringToCraft(String s){
		for(CraftedItem c:Pluginc.allCraftedItems){
			if(ChatColor.stripColor(c.getCraftedItemName()).equals(ChatColor.stripColor(s))){
				return c;
			}
		}
		return null;
	}
	public static String serializeRecipes(ArrayList<CraftedItem> asd){
		StringBuilder s= new StringBuilder();
		if(asd==null){
			s.append("null");
			return s.toString();
		}
		if(asd.size()==0){
			s.append("null");
			return s.toString();
		}
		for(CraftedItem i:asd){
			s.append(ChatColor.stripColor(i.getCraftedItemName()));
			s.append(":");
		}
		String as= s.toString().trim();
		if (as.endsWith(":"))
	        as = as.substring(0, s.length() - 1);
		return as;
	}
	public static ArrayList<CraftedItem> deserializeStringToRecipes(String s){
		if(s==null){
			return null;
		}
		if(s.equals("null"))
			return new ArrayList<CraftedItem>();
		String[] da= s.split(":");
		ArrayList<CraftedItem> fag= new ArrayList<CraftedItem>();
		for(CraftedItem sd:Pluginc.allCraftedItems){
			for(String f:da){
				if(ChatColor.stripColor(sd.getCraftedItemName()).equals(f)){
					fag.add(sd);
				}
			}
		}
		return fag;
		
	}
	public static void OpenCraftingInv(Player p){
		PlayerData pd= Pluginc.getInstance().getPD(p);
		if(!pd.HasLoadedSQL())
			return;
		Inventory crafting= Bukkit.createInventory(null, 54,"Crafting");
	//	if(!p.isOp()){
		if(pd.knownRecipes!=null && pd.knownRecipes.size()>0){
			for(CraftedItem ci: pd.knownRecipes){
				ItemStack WhattItemlooksLIke= new ItemStack(ci.getCraftedItem().getType());
				ItemMeta meta= WhattItemlooksLIke.getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.setDisplayName(ci.getCraftedItemName());
				ArrayList<String> a= new ArrayList<String>();
				//a.add("test");
				a.add(ChatColor.WHITE+"Requires:");
				for(int id=0;id<ci.getRecipe().length;id++){
						if(p.getInventory().contains(ci.getRecipe()[id]) || p.getInventory().containsAtLeast(ci.getRecipe()[id], 2)){
							if(!ci.getRecipe()[id].hasItemMeta() ||!ci.getRecipe()[id].getItemMeta().hasDisplayName()){
								a.add(ChatColor.GREEN+"✔"+" "+ChatColor.RESET+ChatColor.GREEN+ChatColor.stripColor(ci.getRecipe()[id].getType().toString()));
							}
							else{
							a.add(ChatColor.GREEN+"✔"+" "+ChatColor.RESET+ChatColor.GREEN+ChatColor.stripColor(ci.getRecipe()[id].getItemMeta().getDisplayName()));
							}
						}
						else if(ci.getRecipe()[id].hasItemMeta() && ci.getRecipe()[id].getItemMeta().hasDisplayName()){
							a.add(ChatColor.RED+"✖"+" "+ChatColor.RESET+ChatColor.RED+ChatColor.stripColor(ci.getRecipe()[id].getItemMeta().getDisplayName()));
							}
							else{
								//p.sendMessage("kys");
								//a.add(ChatColor.AQUA+"wtf");
								a.add(ChatColor.RED+"✖"+" "+ChatColor.RESET+ChatColor.RED+ChatColor.stripColor(ci.getRecipe()[id].getType().toString()));
								//a.add("test ");
							}
				}
				a.add("");
				a.add(ChatColor.WHITE+"Click to view item");
				meta.setLore(a);
				WhattItemlooksLIke.setItemMeta(meta);
				crafting.addItem(WhattItemlooksLIke);
					}
			}
		else{
			pd.knownRecipes= new ArrayList<CraftedItem>();
			//pd.loadTo(p);
			p.sendMessage("looks like this is your first time crafting! hover over the book to learn");
		}
//	}
		/*else{
			for(CraftedItem ci: Pluginc.getAllCraftedItems()){
				ItemStack WhattItemlooksLIke= new ItemStack(ci.getCraftedItem().getType());
				ItemMeta meta= WhattItemlooksLIke.getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.setDisplayName(ci.getCraftedItemName());
				ArrayList<String> a= new ArrayList<String>();
				//a.add("test");
				for(int id=0;id<ci.getRecipe().length;id++){
						if(p.getInventory().contains(ci.getRecipe()[id]) || p.getInventory().containsAtLeast(ci.getRecipe()[id], 2)){
							if(!ci.getRecipe()[id].hasItemMeta() ||!ci.getRecipe()[id].getItemMeta().hasDisplayName()){
								a.add(ChatColor.GREEN+"✔"+" "+ChatColor.RESET+ChatColor.GREEN+ChatColor.stripColor(ci.getRecipe()[id].getType().toString()));
							}
							else{
							a.add(ChatColor.GREEN+"✔"+" "+ChatColor.RESET+ChatColor.GREEN+ChatColor.stripColor(ci.getRecipe()[id].getItemMeta().getDisplayName()));
							}
						}
						else if(ci.getRecipe()[id].hasItemMeta() && ci.getRecipe()[id].getItemMeta().hasDisplayName()){
							a.add(ChatColor.RED+"✖"+" "+ChatColor.RESET+ChatColor.RED+ChatColor.stripColor(ci.getRecipe()[id].getItemMeta().getDisplayName()));
							}
							else{
								//p.sendMessage("kys");
								//a.add(ChatColor.AQUA+"wtf");
								a.add(ChatColor.RED+"✖"+" "+ChatColor.RESET+ChatColor.RED+ChatColor.stripColor(ci.getRecipe()[id].getType().toString()));
								//a.add("test ");
							}
				}
				meta.setLore(a);
				WhattItemlooksLIke.setItemMeta(meta);
				crafting.addItem(WhattItemlooksLIke);
			}
		}*/
		ItemStack barrier= ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER");
		crafting.setItem(36, barrier);
		crafting.setItem(37, barrier);
		crafting.setItem(38, barrier);
		crafting.setItem(39, barrier);
		crafting.setItem(40, barrier);
		crafting.setItem(41, barrier);
		crafting.setItem(42, barrier);
		crafting.setItem(43, barrier);
		crafting.setItem(44, barrier);
		ArrayList<String> fa= new ArrayList<String>();
		fa.add(ChatColor.WHITE+"In this menu you can see");
		fa.add(ChatColor.WHITE+"all of your known craftable items.");
		fa.add(ChatColor.WHITE+"Ingredients that you have will");
		fa.add(ChatColor.WHITE+"be colored in "+ChatColor.GREEN+"green"+ChatColor.WHITE+" and the");
		fa.add(ChatColor.WHITE+"ones you don't- in "+ChatColor.RED+"red.");
		//fa.add("ones you don't- in red.");
		fa.add(ChatColor.WHITE+"If you have all the ingredients,");
		fa.add(ChatColor.WHITE+"you can "+ChatColor.BOLD+"click the item"+ChatColor.RESET+ChatColor.WHITE+ " and");
		fa.add(ChatColor.WHITE+"you'll see the full stats of the item");
		crafting.setItem(45, ItemAPI.AdLoreItem(new ItemStack(Material.BOOK_AND_QUILL), ChatColor.BLUE+"What's this?", fa, true, false));
		//crafting.setItem(36, new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14));
		//crafting.setItem(36, new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14));
	
		p.openInventory(crafting);
	}
	/*public static void OpenCraftingInv(Player p){
		
		Inventory crafting= Bukkit.createInventory(null, 54,"Crafting");
		for(CraftedItem ci: Pluginc.getAllCraftedItems()){
			ItemStack WhattItemlooksLIke= new ItemStack(ci.getCraftedItem().getType());
			ItemMeta meta= WhattItemlooksLIke.getItemMeta();
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.setDisplayName(ci.getCraftedItemName());
			ArrayList<String> a= new ArrayList<String>();
			//a.add("test");
			for(int id=0;id<ci.getRecipe().length;id++){
					if(p.getInventory().contains(ci.getRecipe()[id]) || p.getInventory().containsAtLeast(ci.getRecipe()[id], 2)){
						if(!ci.getRecipe()[id].hasItemMeta() ||!ci.getRecipe()[id].getItemMeta().hasDisplayName()){
							a.add(ChatColor.GREEN+"✔"+" "+ChatColor.RESET+ChatColor.GREEN+ChatColor.stripColor(ci.getRecipe()[id].getType().toString()));
						}
						else{
						a.add(ChatColor.GREEN+"✔"+" "+ChatColor.RESET+ChatColor.GREEN+ChatColor.stripColor(ci.getRecipe()[id].getItemMeta().getDisplayName()));
						}
					}
					else if(ci.getRecipe()[id].hasItemMeta() && ci.getRecipe()[id].getItemMeta().hasDisplayName()){
						a.add(ChatColor.RED+"✖"+" "+ChatColor.RESET+ChatColor.RED+ChatColor.stripColor(ci.getRecipe()[id].getItemMeta().getDisplayName()));
						}
						else{
							//p.sendMessage("kys");
							//a.add(ChatColor.AQUA+"wtf");
							a.add(ChatColor.RED+"✖"+" "+ChatColor.RESET+ChatColor.RED+ChatColor.stripColor(ci.getRecipe()[id].getType().toString()));
							//a.add("test ");
						}
			}
			meta.setLore(a);
			WhattItemlooksLIke.setItemMeta(meta);
			crafting.addItem(WhattItemlooksLIke);
		}
		crafting.setItem(36, ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER"));
		crafting.setItem(37, ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER"));
		crafting.setItem(38, ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER"));
		crafting.setItem(39, ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER"));
		crafting.setItem(40, ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER"));
		crafting.setItem(41, ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER"));
		crafting.setItem(42, ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER"));
		crafting.setItem(43, ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER"));
		crafting.setItem(44, ItemAPI.LoreItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14),ChatColor.RED+"BORDER"));
		ArrayList<String> fa= new ArrayList<String>();
		fa.add(ChatColor.WHITE+"In this menu you can see");
		fa.add(ChatColor.WHITE+"all of your known craftable items.");
		fa.add(ChatColor.WHITE+"Ingredients that you have will");
		fa.add(ChatColor.WHITE+"be colored in green and the");
		fa.add(ChatColor.WHITE+"ones you don't- in red.");
		//fa.add("ones you don't- in red.");
		fa.add(ChatColor.WHITE+"If you have all the ingredients,");
		fa.add(ChatColor.WHITE+"you can click the item and");
		fa.add(ChatColor.WHITE+"you'll see the full stats of the item");
		crafting.setItem(45, ItemAPI.AdLoreItem(new ItemStack(Material.BOOK_AND_QUILL), ChatColor.BLUE+"What's this?", fa, true, false));
		//crafting.setItem(36, new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14));
		//crafting.setItem(36, new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14));


		p.openInventory(crafting);
	}*/
	public static void giveBlueprintItem(Player p,String name){
		PlayerInventory pinv=p.getInventory();
		ItemStack i= new ItemStack(Material.PAPER);
		//i.getItemMeta().setDisplayName(ChatColor.AQUA+"Blueprint");
		ArrayList<String> a= new ArrayList<String>();
		ItemMeta meta= i.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE+"Blueprint");
		//meta.setLore(a);
		a.add(ChatColor.GRAY+"Right click to unlock:");
		a.add(ChatColor.DARK_PURPLE+name);
		meta.setLore(a);
		i.setItemMeta(meta);
		pinv.addItem(i);
	}
	public static boolean isCrafted(ItemStack i){
		if(!i.hasItemMeta()){
			return false;
		}
		if(!i.getItemMeta().hasDisplayName()){
			return false;
		}
		for(CraftedItem ce:Pluginc.getAllCraftedItems()){
			if(ce.getCraftedItemName().equals(i.getItemMeta().getDisplayName()) && ce.getCraftedItemMat()==i.getType()){
				return true;
			}
		}
	return false;
	}
	public static CraftedItem getCraftedItemClass(ItemStack i){
		for(CraftedItem ce:Pluginc.getAllCraftedItems()){
			if(ce.getCraftedItemName().equals(i.getItemMeta().getDisplayName()) && ce.getCraftedItemMat()==i.getType()){
				return ce;
			}
		}
		return null;
	}
	public static boolean hasRecipe(Player p,CraftedItem ce){
		int items=0;
		for(int id=0;id<ce.getRecipe().length;id++){
			if(p.getInventory().containsAtLeast(ce.getRecipe()[id],2) || p.getInventory().contains(ce.getRecipe()[id])){
				items++;
			}
		}
		if(items==ce.getRecipe().length){
			p.sendMessage(String.valueOf(items));
			return true;
		}
		return false;
	}
	public static void registerCraftedItems(Pluginc as){
		ArrayList<String> a= new ArrayList<String>();
		a.add(ChatColor.WHITE+"A mighty sword,");
		a.add(org.bukkit.ChatColor.WHITE+"blessed by the gods themselves");
		//CraftedItem a1= new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.DIAMOND_SWORD), ChatColor.RED+"Demon Slayer", ChatColor.DARK_PURPLE+"A mighty sworda1", true, true), new ItemStack[]{new ItemStack(Material.DIAMOND),new ItemStack(Material.CHAINMAIL_BOOTS)});
		//CraftedItem a2= new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.WOOD_SWORD), ChatColor.RED+"ad Slayer", ChatColor.DARK_PURPLE+"A mighty LOLa2", true, true), new ItemStack[]{new ItemStack(Material.DIAMOND),new ItemStack(Material.CHAINMAIL_BOOTS)});
		//CraftedItem a3= new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.ACACIA_DOOR_ITEM), ChatColor.RED+"ad Slayer", ChatColor.DARK_PURPLE+"A mighty LOLa3", true, true), new ItemStack[]{new ItemStack(Material.DIAMOND),new ItemStack(Material.CHAINMAIL_BOOTS)});
		//CraftedItem a4=new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.ACACIA_DOOR_ITEM), ChatColor.WHITE+"asd", ChatColor.DARK_PURPLE+"A mighty LOLa4", true, true),new ItemStack[]{new ItemStack(Material.DIAMOND),new ItemStack(Material.CHAINMAIL_BOOTS)});
		//CraftedItem a5=new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.CARROT_ITEM), ChatColor.WHITE+"asd", ChatColor.DARK_PURPLE+"A mighty LOLa5",true,true), new ItemStack[]{new ItemStack(Material.DIAMOND),ItemAPI.ColoredItem(new ItemStack(Material.LEATHER_CHESTPLATE),ChatColor.RED+"Saiyan Chestplate",Arrays.asList(170, 0, 150))});
		as.allCraftedItems.add(new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.DIAMOND_SWORD), ChatColor.RED+"Demon Slayer", ChatColor.DARK_PURPLE+"A mighty sword", true, true), new ItemStack[]{
				new ItemStack(Material.DIAMOND),
				new ItemStack(Material.CHAINMAIL_BOOTS)}));
		as.allCraftedItems.add(new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.WOOD_SWORD), ChatColor.RED+"ad Slayer", ChatColor.DARK_AQUA+"TROLOLLOLOL", true, true), new ItemStack[]{
				new ItemStack(Material.DIAMOND),
				new ItemStack(Material.CHAINMAIL_BOOTS)}));
		as.allCraftedItems.add(new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.ACACIA_DOOR_ITEM), ChatColor.RED+"ads Slayer", ChatColor.DARK_PURPLE+"A mighty LOLa3", true, true), new ItemStack[]{
				new ItemStack(Material.DIAMOND),
				new ItemStack(Material.CHAINMAIL_BOOTS)}));
		as.allCraftedItems.add(new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.ACACIA_DOOR_ITEM), ChatColor.WHITE+"Door", ChatColor.RED+"The Door to Nothing", true, true),new ItemStack[]{
				new ItemStack(Material.DIAMOND),
				new ItemStack(Material.CHAINMAIL_BOOTS)}));
		as.allCraftedItems.add(new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.CARROT_ITEM), ChatColor.WHITE+"Carrot of death", ChatColor.DARK_PURPLE+"A mighty LOLa5",true,true), new ItemStack[]{
				new ItemStack(Material.DIAMOND),
				ItemAPI.ColoredItem(new ItemStack(Material.LEATHER_CHESTPLATE),ChatColor.RED+"Saiyan Chestplate",Arrays.asList(170, 0, 150))}));
		as.allCraftedItems.add(new CraftedItem(ItemAPI.AdLoreItem(new ItemStack(Material.IRON_SWORD),ChatColor.DARK_PURPLE+"Ash Bringer", a,true,true), new ItemStack[]{
				new ItemStack(Material.SULPHUR),
				ItemAPI.LoreItem(new ItemStack(Material.IRON_SWORD), "Normal Sword", null),
				ItemAPI.LoreItem(new ItemStack(Material.HARD_CLAY,1,(short)9), ChatColor.AQUA+"Paladin Runes", null)}));
		//as.allCraftedItems.add(new CraftedItem(ItemAPI.LoreItem(new ItemStack(Material.STICK), ChatColor.GREEN+"\u2695"+ChatColor.LIGHT_PURPLE+"Stick Of Healing"+ChatColor.GREEN+"\u2695"),new ItemStack[]{ItemAPI.LoreItem(new ItemStack(Material.TIPPED_ARROW), "Healing Arrow"),ItemAPI.LoreItem(new ItemStack(Material.STICK), "Stick"),ItemAPI.LoreItem(new ItemStack(Material.NETHER_STAR), "Power Star")}));
		as.allCraftedItems.add(new CraftedItem(ItemCreator.createItemArmor(ChatColor.LIGHT_PURPLE+"Dwarven Breastplate", EquipType.CHESTPLATE, 28, 215, 0, 24, 0, 0, 0, 32, 2, 12, true),new ItemStack[]{
				new ItemStack(Material.ANVIL)}));
	}
}
