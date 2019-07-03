package com.vampire.rpg.professions;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.commands.AddMoney;
import com.vampire.rpg.items.EquipType;
import com.vampire.rpg.items.ItemBalance;
import com.vampire.rpg.items.StatBalance;
import com.vampire.rpg.items.stats.StatAccumulator;
import com.vampire.rpg.items.stats.StatParser;
import com.vampire.rpg.menus.MenuGeneralRunnable;
import com.vampire.rpg.menus.MenuManager;
import com.vampire.rpg.utils.BannerBuilder;
import com.vampire.rpg.utils.VamSound;

public class ProffesionManager extends AbstractManager {
	public ProffesionManager(Pluginc plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}
	public static ItemStack MiningBanner= new BannerBuilder(DyeColor.WHITE).addPattern(DyeColor.LIGHT_BLUE, PatternType.HALF_HORIZONTAL).
			addPattern(DyeColor.BROWN, PatternType.STRIPE_CENTER).addPattern(DyeColor.WHITE,  PatternType.HALF_HORIZONTAL).
			addPattern(DyeColor.BROWN, PatternType.STRAIGHT_CROSS).addPattern(DyeColor.LIGHT_BLUE, PatternType.STRIPE_MIDDLE).addPattern(DyeColor.WHITE, PatternType.STRIPE_TOP).
			addPattern(DyeColor.WHITE, PatternType.CURLY_BORDER).generateItem();
	@Override
	public void initialize() {
	}
	public static void OpenInv(Player p,PlayerData pd){
		Inventory i=MenuManager.createMenu(p, "Professions", 6, new Object[][]{
			{5,0,Material.BEACON,ChatColor.AQUA+"Profession Rewards",new Object[]{
					ChatColor.WHITE,
					"Click to see all the rewards awarded from leveling up professions"
			},new Runnable() {
				
				@Override
				public void run() {				
					Inventory rewards=MenuManager.createMenu(p, "Profession Rewards",6, new Object[][]{
						{0,0,MiningBanner,"Mining Banner",new Object[]{
								ChatColor.RED,
								"Level 100 in Mining is required"
						},new Runnable() {							
							@Override
							public void run() {
								if(pd.getProfessionLevel(Profession.MINING)<100){							
									p.sendMessage(ChatColor.RED+"You do not meet the requirements!");
									return;
								}
								AddMoney.equipBanner(p, MiningBanner);
							}
						}							
						},
					});
					p.openInventory(rewards);
				}
			}				
			},
			{0,0,Material.IRON_PICKAXE,"Mining",new Object[]{
					ChatColor.WHITE,
					"Click"
			},new Runnable() {
				
				@Override
				public void run() {
					// TODO Open mining inv		
				}
			}				
			},
			{0,1,Material.ANVIL,ChatColor.GREEN+"Blacksmithing",new Object[]{
					
			},new Runnable() {
				
				@Override
				public void run() {
					// TODO open blacksmithing inv
					
				}
			}			
			},
		});
		p.openInventory(i);
	}
	public static void openBlacksmithMenu(Player p,PlayerData pd){
		p.closeInventory();
		Inventory i=MenuManager.createMenu(p, "Blacksmithing", 5, new Object[][]{
			{3,4,Material.ANVIL,ChatColor.DARK_PURPLE+"Click to upgrade item",new Object[]{
					ChatColor.YELLOW,
					"Current cost: 0 gold ",
			},new Runnable() {				
				@Override
				public void run() {					
				}
			}				
			},
		});
		p.openInventory(i);
		MenuManager.addMenuGeneralClick(p,new MenuGeneralRunnable<PlayerData>(){
			ItemStack upgradedItem;
			int totalworth;
			int required=0;
			@Override
			public void execute(Player p, PlayerData pd, ItemStack item, int slot) {
				if(pd.currentModifiableInventory!=null && item.hasItemMeta()){
					Inventory i = pd.currentModifiableInventory;
                    ItemMeta im = item.getItemMeta();
                    if (!(EquipType.isArmor(item) || EquipType.isWeapon(item))) {
                        p.sendMessage(ChatColor.RED + "This item cannot be upgraded.");
                        return;
                    }
                    if (im.hasDisplayName() && im.hasLore()) {
                    	 int level = -1;
                    	 required=0;
                         for (String s : im.getLore()) {
                             if (s.contains("Level")) {
                                 s = ChatColor.stripColor(s).replaceAll("[^0-9]", "");
                                 level = Integer.parseInt(s);
                                 break;
                             }
                         }
                         if (level > 0) {
                        	 if(upgradedItem!=null){
                        		 p.sendMessage(ChatColor.RED+"An item is already selected!");
                        		 return;
                        	 }
                        	 upgradedItem=item;
                        	 int rarity = 0;
                             String dispName = im.getDisplayName();
                             for (int k = 0; k < ItemBalance.RARITY_NAMES.length; k++) {
                                 String s = ItemBalance.RARITY_NAMES[k];
                                 if (dispName.contains(s)) {
                                     rarity = k;
                                 }
                             }
                             if(dispName.contains("+")){
                            	  required = Integer.valueOf(ChatColor.stripColor(dispName.substring(dispName.indexOf("+")+1,dispName.indexOf("+")+2)));
                             }
                             totalworth=StatBalance.getUpgradeCosts(level, rarity, required);
                             ItemStack newItem =  new ItemStack(item);
                             ItemMeta newMeta = newItem.getItemMeta();
                         //    newItem.setData(item.getData());
                             ArrayList<String> lore=new ArrayList<String>();
                             StatAccumulator sa= new StatAccumulator();
                             for(String s:newMeta.getLore()){
                            	 s = ChatColor.stripColor(s).trim();
                            	 if(!StatParser.isStatLine(s)){
                            		 lore.add(s);
                            	 }else{
                                 sa.parseAndAccumulate(s);
                            	 }
                             }
                             newMeta.setLore(sa.lore(0.1));
                             for(String s :lore){
                            	 newMeta.getLore().add(s);
                             }
                             newMeta.getLore().add(ChatColor.RED+"Click to remove");
                             newMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                             newMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                             newMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                             newItem.setItemMeta(newMeta);
                             MenuManager.modifyMenu(p, i, new Object[][]{
                            	 {2,4,newItem,dispName,newMeta.getLore()
                            	 ,new Runnable() {								
									@Override
									public void run() {
										 MenuManager.modifyMenu(p, i, new Object[][] {
                                             {
                                                     2,
                                                     4,
                                                     null,
                                                     im.getDisplayName(),
                                                     new Object[] {
                                                             ChatColor.GRAY,
                                                             "removed. error code 111: non-removed menu item",

                                                     }, new Runnable() {
                                                         public void run() {
                                                         }
                                                     }
                                             }
                                     });
										 upgradedItem=null;
										 MenuManager.modifyMenu(p, i, new Object[][] {
                                             {3,4,Material.ANVIL,ChatColor.DARK_PURPLE+"Click to upgrade item",new Object[]{
                                 					ChatColor.YELLOW,
                                					"Current cost: 0 gold ",
                                			},new Runnable() {				
                                				@Override
                                				public void run() {					
                                				}
                                			}				
                                			},
                                		});
									}
								}                            		 
                              }
                            		 
                             });
                             MenuManager.modifyMenu(p, i, new Object[][]{
                            	 {3,4,Material.ANVIL,ChatColor.DARK_PURPLE+"Click to upgrade item",new Object[]{
                      					ChatColor.YELLOW,
                     					"Upgrade cost: "+String.valueOf(totalworth)+" gold ",
                     			},new Runnable() {				
                     				@Override
                     				public void run() {
                     					 MenuManager.modifyMenu(p, i, new Object[][] {
                                             {
                                                     2,
                                                     4,
                                                     null,
                                                     im.getDisplayName(),
                                                     new Object[] {
                                                             ChatColor.GRAY,
                                                             "removed. error code 111: non-removed menu item",

                                                     }, new Runnable() {
                                                         public void run() {
                                                         }
                                                     }
                                             }
                     					 });
                     					upgradedItem=null;
                     					 required++;
                     					//im.setDisplayName(im.getDisplayName()+" +"+String.valueOf(required));
                     					//StatAccumulator sa= new StatAccumulator();
                     				//	for(String s2:item.getItemMeta().getLore()){
                     				//		sa.parseAndAccumulate(s2);
                     			//		}
                     					
                     					sa.updgradeStats(1.1);
                     					ItemStack itemNew= new ItemStack(item);
                     					ItemMeta meta=item.getItemMeta();
                     					meta.setLore(sa.lore());
                     					for(String s:lore){
                     						meta.getLore().add(s);
                     					}
                     					if(required==1){
                     					meta.setDisplayName(im.getDisplayName()+" +"+String.valueOf(required));
                     					}else{
                     						meta.setDisplayName(im.getDisplayName().substring(0,im.getDisplayName().indexOf("+")).trim() +" +"+String.valueOf(required));
                     					}
                     					itemNew.setItemMeta(meta);
                     					p.getInventory().remove(item);
                     					p.getInventory().addItem(itemNew);
                     					VamSound.playSound(p, Sound.ENTITY_WOLF_GROWL, 10, 5);
                     					MenuManager.modifyMenu(p, i, new Object[][]{
                     						{3,4,Material.ANVIL,ChatColor.DARK_PURPLE+"Click to upgrade item",new Object[]{
                                  					ChatColor.YELLOW,
                                 					"Upgrade cost: "+"0 gold ",
                                 			},new Runnable() {				
                                 				@Override
                                 				public void run() {
                                 					
                                 				}
                     						}
                     						}
                     					});                   					
                     					//TODO take the amount of money needed
                     				}
                     			}				
                     			},
                            		                           
                             });
                         }
                    }
				}
				
			}
			
		});
		  pd.currentModifiableInventory = i;
	}
}
