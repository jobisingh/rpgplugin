package com.vampire.rpg.event.player;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.Rank;
import com.vampire.rpg.crafting.CraftedItem;
import com.vampire.rpg.items.EquipType;
import com.vampire.rpg.utils.VamTags;

public class PlayerInteract implements Listener{
	public ArrayList<ItemStack> armor = new ArrayList<ItemStack>();
	public EquipType getArmorType(ItemStack i){
		if(EquipType.BOOTS.isType(i))
			return EquipType.BOOTS;
		if(EquipType.LEGGINGS.isType(i))
			return EquipType.LEGGINGS;
		if(EquipType.CHESTPLATE.isType(i))
			return EquipType.CHESTPLATE;
		if(EquipType.HELMET.isType(i))
			return EquipType.HELMET;
		return null;
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p=e.getPlayer();
		if(e.getItem()!=null && e.getItem().getType()!=null && e.getItem().getType()==Material.GOLD_NUGGET){
			Location l= p.getLocation().add(p.getLocation().getDirection().setY(0).normalize().multiply(7));
		//	VamTags.makeStand("", l, marker);
		}
		if(e.getItem()!=null && e.getItem().getType()!=null && e.getItem().getType()==Material.GRAVEL){
			Inventory inv=Bukkit.createInventory(null, 9, "stats");
			PlayerData pd=Pluginc.getPD(p);
			if(!pd.loadedSQL)
				return;
			ItemStack i= ItemAPI.AdLoreItem(new ItemStack(Material.APPLE), "stats", new String[]{"hp:"+String.valueOf(pd.getCurrentMaxHP()),"def:"+String.valueOf(pd.defense),"base dmg:"+String.valueOf(pd.damageLow),"max dmg:"+String.valueOf(pd.damageHigh)}, true, false);
			inv.addItem(i);
			p.openInventory(inv);
		}
		PlayerData pd=Pluginc.getPD(p);
		if(!pd.loadedSQL)
			return;
		if(e.getItem()!=null && e.getItem().getType()!=null && e.getItem().getType()==Material.BOOKSHELF){
			if(pd.rank.checkIsAtLeast(Rank.OWNER)){
				p.sendMessage(ChatColor.AQUA+"You are now setting a mobs spawn in:");
				Block b= e.getClickedBlock();
				String x= String.valueOf(b.getX());
				String y= String.valueOf(b.getY());
				String z= String.valueOf(b.getZ());
				p.sendMessage(ChatColor.DARK_GREEN+"X:"+x+", "+"Y:"+y+", Z:"+z);
				p.sendMessage(ChatColor.DARK_BLUE+"/setmobspawn [MobName] [SpawnRange] [RespawnDelay] [leash] []");
			}
			return;					
		}
/*		if(e.getItem()==null){
			//p.sendMessage("null");
			return;
		}
		if(e.getAction()==Action.LEFT_CLICK_AIR || e.getAction()==Action.LEFT_CLICK_BLOCK){
			//p.sendMessage("left");
			if(p.getItemInHand().getType().equals(Material.STICK) && e.getClickedBlock() !=null && !e.getClickedBlock().getType().equals(Material.AIR)){
				Block b= e.getClickedBlock();
				String x= String.valueOf(b.getX());
				String y= String.valueOf(b.getY());
				String z= String.valueOf(b.getZ());
				p.sendMessage(ChatColor.AQUA+x+","+y+","+z);
			}
				
			//	p.sendMessage("asd is: "+Pluginc.testing.get(p).toString())
			return;
		}
		if(EquipType.isArmor(e.getItem())){
			if(getArmorType(e.getItem()).equals(EquipType.BOOTS) && p.getInventory().getBoots()==null){
			
				StatAccumulator.clearStats(p);
				armor.clear();
				armor.add(p.getEquipment().getHelmet());
				armor.add(p.getEquipment().getChestplate());
				armor.add(p.getEquipment().getLeggings());
	        	armor.add(p.getEquipment().getBoots());
	        	for (ItemStack i : armor) {
	        //		PlayerStats.addStats(p,i, false);
	        		//addSet(i);
	        	}
	        //		PlayerStats.addStats(p, e.getItem(), false);
	        	  StatAccumulator sat= new StatAccumulator();
			      sat.finalize(p);
			//      PlayerStats.updateHpOnHearts(p);
			      p.sendMessage("mul is "+String.valueOf(PlayerStats.getHpMul(p)));
			       p.sendMessage("hp is "+String.valueOf(PlayerStats.getHP(p)));
			}
			if(getArmorType(e.getItem()).equals(EquipType.LEGGINGS) && p.getInventory().getLeggings()==null){
				
				StatAccumulator.clearStats(p);
				armor.clear();
				armor.add(p.getEquipment().getHelmet());
				armor.add(p.getEquipment().getChestplate());
				armor.add(p.getEquipment().getLeggings());
	        	armor.add(p.getEquipment().getBoots());
	        	for (ItemStack i : armor) {
	        		PlayerStats.addStats(p,i, false);
	        		//addSet(i);
	        	}
	        		PlayerStats.addStats(p, e.getItem(), false);
	        	  StatAccumulator sat= new StatAccumulator();
			      sat.finalize(p);
			      PlayerStats.updateHpOnHearts(p);
			      p.sendMessage("mul is "+String.valueOf(PlayerStats.getHpMul(p)));
			        p.sendMessage("hp is "+String.valueOf(PlayerStats.getHP(p)));
			}
			if(getArmorType(e.getItem()).equals(EquipType.CHESTPLATE) && p.getInventory().getChestplate()==null){
				
				StatAccumulator.clearStats(p);
				armor.clear();
				armor.add(p.getEquipment().getHelmet());
				armor.add(p.getEquipment().getChestplate());
				armor.add(p.getEquipment().getLeggings());
	        	armor.add(p.getEquipment().getBoots());
	        	for (ItemStack i : armor) {
	        		PlayerStats.addStats(p,i, false);
	        		//addSet(i);
	        	}
	        		PlayerStats.addStats(p, e.getItem(), false);
	        	  StatAccumulator sat= new StatAccumulator();
			      sat.finalize(p);
			      PlayerStats.updateHpOnHearts(p);
			      p.sendMessage("mul is "+String.valueOf(PlayerStats.getHpMul(p)));
			        p.sendMessage("hp is "+String.valueOf(PlayerStats.getHP(p)));
			}
			if(getArmorType(e.getItem()).equals(EquipType.HELMET) && p.getInventory().getHelmet()==null){
				
				StatAccumulator.clearStats(p);
				armor.clear();
				armor.add(p.getEquipment().getHelmet());
				armor.add(p.getEquipment().getChestplate());
				armor.add(p.getEquipment().getLeggings());
	        	armor.add(p.getEquipment().getBoots());
	        	for (ItemStack i : armor) {
	        		PlayerStats.addStats(p,i, false);
	        		//addSet(i);
	        	}
	        		PlayerStats.addStats(p, e.getItem(), false);
	        	  StatAccumulator sat= new StatAccumulator();
			      sat.finalize(p);
			      PlayerStats.updateHpOnHearts(p);
			}
			return;
		}
	
			//StatAccumulator.clearStats(p);
			//armor.clear();
			//armor.add(p.getEquipment().getHelmet());
	       // armor.add(p.getEquipment().getChestplate());
	      //  armor.add(p.getEquipment().getLeggings());
	      //  armor.add(p.getEquipment().getBoots());
	       // PlayerStats.addStats(p, e.getCursor(), false);
	      //  PlayerStats.updateHpOnHearts(p);
	     //   p.sendMessage("hp is "+String.valueOf(PlayerStats.getHP(p)));
		if(!e.getItem().hasItemMeta()){
			//p.sendMessage("nope");
			return;
		}
		//if(p.isOp()){
		//	p.sendMessage("nope");
		//}*/
		//Player p=e.getPlayer();
	//	PlayerData pd = Pluginc.getPD(p);
		if(e.getItem()!=null && e.getItem().getItemMeta()!=null &&e.getItem().getItemMeta().hasDisplayName() && e.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE+"Blueprint")){
			ItemStack i=e.getItem();
			String a=i.getItemMeta().getLore().get(1);
			
			for(CraftedItem ci:Pluginc.allCraftedItems){
				if(ChatColor.stripColor(ci.getCraftedItemName()).toLowerCase().equals(ChatColor.stripColor(a).toLowerCase())){
					if(pd.knownRecipes != null && !pd.knownRecipes.contains(ci)){
						pd.knownRecipes.add(ci);
						p.sendMessage("good job, u unlocked:"+(ci.getCraftedItemName()));
						return;
					}
					if( pd.knownRecipes!=null){
						if(pd.knownRecipes.contains(ci)){
							p.sendMessage("u have already");
							return;
						}
					}
					p.sendMessage("Type /craft and learn about it before");
				}
			}
		}
	}
}
