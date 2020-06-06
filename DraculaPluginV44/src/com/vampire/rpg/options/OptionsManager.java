package com.vampire.rpg.options;


import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.comapss.MenuCompass;
import com.vampire.rpg.menus.MenuManager;
;

public class OptionsManager extends AbstractManager {

    public OptionsManager(Pluginc plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
    }

    public static void msgDamage(Player p, PlayerData pd, String msg) {
        if (pd.getOption(Option.DAMAGE_MESSAGES))
            p.sendMessage(msg);
    }

    public static void openMenu(Player p, PlayerData pd) {
        Inventory inventory1 = MenuManager.createMenu(p, "Fallen Gate Game Options", 6, new Object[][] {});
        MenuManager.modifyMenu(p, inventory1, new Object[][]{
  		  {0,0,Material.SIGN,"Chat Options",new Object[]{
  				  
  		  },new Runnable() {
				
				@Override
				public void run() {
					Inventory inventory = MenuManager.createMenu(p, "Chat Options", 6, new Object[][] {
						{5,0,Material.ARROW,"Go back",new Object[]{  				
			    		},new Runnable() {				
							@Override
							public void run() {
									openMenu(p, pd);
							}
						}    			
			    		}
					});
					displayOptions(p, pd, OptionType.CHAT,inventory);
					p.openInventory(inventory);
				}
  		  }  			  
  		  },
  		  {0,1,Material.FEATHER,"Message Options",new Object[]{
  				  
  		  },new Runnable() {
				
				@Override
				public void run() {
					Inventory i = MenuManager.createMenu(p, "Message Options", 6, new Object[][] {
						{5,0,Material.ARROW,"Go back",new Object[]{  				
			    		},new Runnable() {				
							@Override
							public void run() {
									openMenu(p, pd);
							}
						}    			
			    		}
					});
					displayOptions(p, pd, OptionType.CONSOLE_MESSAGES,i);
					p.openInventory(i);
				}
  		  }  			  
  		  },
  		  {0,2,Material.BRICK,"Display Options",new Object[]{
  				  
  		  },new Runnable() {			
				@Override
				public void run() {
					Inventory inventory = MenuManager.createMenu(p, "Display Options", 6, new Object[][] {
						{5,0,Material.ARROW,"Go back",new Object[]{  				
			    		},new Runnable() {				
							@Override
							public void run() {
									openMenu(p, pd);
							}
						}    			
			    		}
					});
					displayOptions(p, pd, OptionType.DISPLAY,inventory);
					p.openInventory(inventory);
				}
			}
  			  
  		  },
  		  {5,0,Material.ARROW,"Go back",new Object[]{
				
  		  },new Runnable() {				
  			  @Override
  			  public void run() {
				MenuCompass.showMenu(p, pd);
  			  }
  		  }    			
		}
  	  });
        p.openInventory(inventory1);
    }
    public static void displayOptions(Player p,PlayerData pd,OptionType ot,Inventory i){
    	int index = 0;
        int col = 0;
        int row = 0;
    	for(Option o:ot.options){
    		if (index > 8) {
                index = 0;
                row++;
            }
            col = index;
            index++;
            ItemStack item;
            boolean status;
            if (status = pd.getOption(o)) {
                item = new ItemStack(Material.LIME_WOOL, 1);
            } else {
                item = new ItemStack(Material.RED_WOOL, 1);
            }
            MenuManager.modifyMenu(p, i, new Object[][] {
                    {
                            row,
                            col,
                            item,
                            (status ? ChatColor.YELLOW : ChatColor.GRAY) + o.getDisplay(),
                            new Object[] {
                                    status ? ChatColor.GREEN : ChatColor.RED,
                                    o.getDesc(status),
                                    null,
                                    "",
                                    ChatColor.GRAY,
                                    "Click to toggle this option."
                            },
                            new Runnable() {
                                public void run() {
                                    if (pd.isValid()) {
                                        pd.toggleOption(o);
                                 //       p.sendMessage(ChatColor.BLUE+"toggled optionsmanager");
                                        displayOptions(p, pd, ot,i);
                                    }
                                }
                            }
                    }
            });
        }
    //	p.openInventory(i);
    }
    /*  public static void displayOptions(Player p, PlayerData pd, Inventory inventory) {
    	  MenuManager.modifyMenu(p, inventory, new Object[][]{
    		  {0,0,Material.SIGN,"Chat Options",new Object[]{
    				  
    		  },new Runnable() {
				
				@Override
				public void run() {
					displayOptions(p, pd, OptionType.CHAT);					
				}
    		  }  			  
    		  },
    		  {0,1,Material.FEATHER,"Message Options",new Object[]{
    				  
    		  },new Runnable() {
				
				@Override
				public void run() {
					displayOptions(p, pd, OptionType.CONSOLE_MESSAGES);				
				}
    		  }  			  
    		  },
    		  {0,2,Material.BRICK,"Display Options",new Object[]{
    				  
    		  },new Runnable() {			
				@Override
				public void run() {
					displayOptions(p, pd, OptionType.DISPLAY);					
				}
			}
    			  
    		  }
    	  });
      }
   /* public static void displayOptions(Player p, PlayerData pd, Inventory inventory) {
        int index = 0;
        int col = 0;
        int row = 0;
        for (Option so : Option.values()) {
            if (index > 8) {
                index = 0;
                row++;
            }
            col = index;
            index++;
            ItemStack item;
            boolean status;
            if (status = pd.getOption(so)) {
                item = new ItemStack(Material.WOOL, 1, DyeColor.LIME.getWoolData());
            } else {
                item = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
            }
            MenuManager.modifyMenu(p, inventory, new Object[][] {
                    {
                            row,
                            col,
                            item,
                            (status ? ChatColor.YELLOW : ChatColor.GRAY) + so.getDisplay(),
                            new Object[] {
                                    status ? ChatColor.GREEN : ChatColor.RED,
                                    so.getDesc(status),
                                    null,
                                    "",
                                    ChatColor.GRAY,
                                    "Click to toggle this option."
                            },
                            new Runnable() {
                                public void run() {
                                    if (pd.isValid()) {
                                        pd.toggleOption(so);
                                 //       p.sendMessage(ChatColor.BLUE+"toggled optionsmanager");
                                        displayOptions(p, pd, inventory);
                                    }
                                }
                            }
                    }
            });
        }
    }*/
}
