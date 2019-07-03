package com.vampire.rpg.event.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.crafting.CraftedAPI;
import com.vampire.rpg.crafting.CraftedItem;
import com.vampire.rpg.items.EquipType;
import com.vampire.rpg.items.stats.StatAccumulator;


public class InventoryClick implements Listener {
	public ArrayList<ItemStack> armor = new ArrayList<ItemStack>(); 
	public static HashMap<Player,ItemStack> itemInhand= new HashMap<Player,ItemStack>();
/*	@EventHandler
	public void onItemScroll(PlayerItemHeldEvent e){
		Player p=(Player)e.getPlayer();
		ItemStack item= p.getEquipment().getItemInMainHand();
		if(!itemInhand.containsKey(p)){
			itemInhand.put(p, item);
		}
		else{
			if(itemInhand.get(p).equals(item))
				return;
			itemInhand.put(p, value)
		}
	}*/
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		Player p=(Player)e.getPlayer();
		p.setMaxHealth(20.0D);
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)){
			return;
		}
	//	if(e.getCurrentItem() ==null){
	//		return;
	//	}
	//	if(e.getCurrentItem().getType()==Material.AIR){
	//		//e.setCancelled(true);
			//return;
	//	}
		Player p=(Player)e.getWhoClicked();
		PlayerInventory pinv= p.getInventory();
		if(e.getClickedInventory()==null)
			return;
		if(p.getInventory()==null)
			return;
	/*	if(e.getClickedInventory().equals(p.getInventory())){
			if(!e.getSlotType().equals(SlotType.ARMOR)){
				return;
			}
			//if(!EquipType.isArmor(e.getCursor())){
		///		p.sendMessage("not");
		//		return;
		//	}
			
			
			
			//if player equips gear on a new slot
			if(e.getCurrentItem() == null){
			//	StatAccumulator.clearStats(p);
				armor.clear();
				armor.add(p.getEquipment().getHelmet());
		        armor.add(p.getEquipment().getChestplate());
		        armor.add(p.getEquipment().getLeggings());
		        armor.add(p.getEquipment().getBoots());
		        for (ItemStack i : armor) {
		         //   PlayerStats.addStats(p,i, false);
		            //addSet(i);
		        }
		     //   PlayerStats.addStats(p, e.getCursor(), false);
		        StatAccumulator sat= new StatAccumulator();
		  //      sat.finalize(p);
		   //     PlayerStats.updateHpOnHearts(p);
		   //     p.sendMessage("mul is "+String.valueOf(PlayerStats.getHpMul(p)));
		  //      p.sendMessage("hp is "+String.valueOf(PlayerStats.getHP(p)));
		        return;
			}
			
			
			//switch(e.getRawSlot()){
		///	case 5:
			//	
		//		break;
		//	default:
	//		}
			//if(EquipType.e.getCurrentItem())
			ItemStack item=p.getEquipment().getItemInMainHand();
		//	StatAccumulator.clearStats(p);
			armor.clear();
			switch(e.getRawSlot()){
			case 5:
				armor.add(p.getEquipment().getChestplate());
			    armor.add(p.getEquipment().getLeggings());
			    armor.add(p.getEquipment().getBoots());
			    armor.add(e.getCursor());
				break;
			case 6:
				armor.add(p.getEquipment().getHelmet());
				armor.add(p.getEquipment().getLeggings());
		        armor.add(p.getEquipment().getBoots());
		        armor.add(e.getCursor());
				break;
			case 7:
				armor.add(p.getEquipment().getHelmet());
		        armor.add(p.getEquipment().getChestplate());
		        armor.add(p.getEquipment().getBoots());
		        armor.add(e.getCursor());
				break;
			case 8:
				armor.add(p.getEquipment().getHelmet());
		        armor.add(p.getEquipment().getChestplate());
		        armor.add(p.getEquipment().getLeggings());
		        armor.add(e.getCursor());
		        break;
			default:
				p.sendMessage("ERROR");
			}
	        for (ItemStack i : armor) {
	        //    PlayerStats.addStats(p,i, false);
	            //addSet(i);
	        }
	      //  PlayerStats.addStats(p, e.getCursor(), false);
	        StatAccumulator sat= new StatAccumulator();
	     //   sat.finalize(p);
	      //  PlayerStats.updateHpOnHearts(p);
	     //   p.sendMessage("hp is "+String.valueOf(PlayerStats.getHP(p)));
	     //   p.sendMessage("mul is "+String.valueOf(PlayerStats.getHpMul(p)));
		}*/
		
		
		
		
		
		//crafting code
		
		if(e.getInventory().getName()=="Crafting"){
			if(e.getClickedInventory() ==pinv){
				e.setCancelled(true);
				return;
			}
			if(e.getClick().isShiftClick()){
				e.setCancelled(true);
				return;
			}
			if(e.getCursor().getType()!=Material.AIR){
				e.setCancelled(true);
				return;
			}
			if(!CraftedAPI.isCrafted(e.getCurrentItem())){
				e.setCancelled(true);
		//f		p.sendMessage("debug");
			}
			else{
				Inventory inv= Bukkit.createInventory(null, 54, "Crafter");
				e.setCancelled(true);
				ItemStack is= CraftedAPI.getCraftedItemClass(e.getCurrentItem()).getCraftedItem();
				inv.setItem(22,is);
				inv.setItem(30, ItemAPI.LoreItem(new ItemStack(Material.WOOL,1,(short)13 ),ChatColor.DARK_GREEN+"Confirm",ChatColor.BLUE+"Click Here To Craft Item" ));
				inv.setItem(32, ItemAPI.LoreItem(new ItemStack(Material.WOOL,1,(short)14 ),ChatColor.RED+"Cancel","Click Here To Cancel"));
				p.openInventory(inv);
			}
		}
		if(e.getInventory().getName()=="Crafter"){
			if(e.getClickedInventory() ==pinv){
				e.setCancelled(true);
				return;
			}
			if(e.getClick().isShiftClick()){
				e.setCancelled(true);
				return;
			}
			if(e.getCursor().getType()!=Material.AIR){
				e.setCancelled(true);
				return;
			}
			if(e.getCurrentItem().getType()==Material.WOOL && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"Cancel")){
				e.setCancelled(true);
				CraftedAPI.OpenCraftingInv(p);
				return;
			}
			if(e.getCurrentItem().getType()==Material.WOOL && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN+"Confirm")){
				e.setCancelled(true);
				if(CraftedAPI.hasRecipe(p, CraftedAPI.getCraftedItemClass(e.getClickedInventory().getItem(22)))){
					for(int id=0;id<CraftedAPI.getCraftedItemClass(e.getClickedInventory().getItem(22)).getRecipe().length;id++){
					//p.getInventory().remove(CraftedAPI.getCraftedItemClass(e.getClickedInventory().getItem(22)).getRecipe()[id]);
					p.getInventory().removeItem(CraftedAPI.getCraftedItemClass(e.getClickedInventory().getItem(22)).getRecipe()[id]);
					}
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE,0.6F, 2);
					new BukkitRunnable(){
						public void run(){
					p.playSound(p.getLocation(), Sound.BLOCK_LAVA_AMBIENT,0.6F, 2);
					p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE,0.6F, 2);
					p.playSound(p.getLocation(), Sound.ENTITY_CAT_HISS,0.6F, 2);
					p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SKELETON_HURT,0.6F, 2);
					p.playSound(p.getLocation(), Sound.BLOCK_GRAVEL_PLACE,0.6F, 2);
					p.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER,0.6F, 2);
						new BukkitRunnable(){
							public void run(){
								p.getWorld().strikeLightningEffect(p.getLocation());
							}
						}.runTaskLater(Pluginc.getInstance(), 10);
							}
					}.runTaskLater(Pluginc.getInstance(), 10);
					//p.getWorld().strikeLightningEffect(p.getLocation());
					p.getInventory().addItem(e.getClickedInventory().getItem(22));
					return;
				}
				p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND,0.5F, 2);
				//p.playSound(p.getLocation(), Sound., arg2, arg3);
				p.sendMessage(ChatColor.RED+"You do not have the necessary items!");
				return;
			}
			e.setCancelled(true);
			return;
		}
	}
}
