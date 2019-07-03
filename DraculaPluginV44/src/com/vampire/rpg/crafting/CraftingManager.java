package com.vampire.rpg.crafting;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.npcs.NPCManager;

public class CraftingManager extends AbstractManager{
	
	public static ArrayList<CraftingVillager> villagers = new ArrayList<CraftingVillager>();
	public CraftingManager(Pluginc plugin) {
		super(plugin);
	}
	@Override
    public void initialize() {
        reload();
    }
	public static void shutdown(){
		if(Pluginc.getInstance().getServer()==null)
			return;
		if(Pluginc.getInstance().getServer().getWorlds()==null)
			return;
		for(World w:Pluginc.getInstance().getServer().getWorlds()){
			for(Entity e:w.getEntities()){
				if(e.getName().equals(ChatColor.DARK_PURPLE+"Blacksmith") && !NPCManager.npcs.containsKey(e.getUniqueId())){
					e.remove();
				}
			}
		}
		for (CraftingVillager hv : villagers) {
            NPCManager.unregister(hv);
        }
   	 villagers.clear();
	}
    public static void reload() {
    	 for (CraftingVillager hv : villagers) {
             NPCManager.unregister(hv);
         }
    	 villagers.clear();
    	 for(World w:Pluginc.getInstance().getServer().getWorlds()){
 			for(Entity e:w.getEntities()){
 				if(e.getName().equals(ChatColor.DARK_PURPLE+"Blacksmith") && !NPCManager.npcs.containsKey(e.getUniqueId())){
 					e.remove();
 				}
 			}
 		}
    	//for(World w:Pluginc.getInstance().getServer().getWorlds()){
    //	 World w=Pluginc.getInstance().getServer().getWorld("world");
    	//	CraftingVillager gv = new CraftingVillager(1, "Blacksmith", 1644.5, 4.0, -903.5, w.getName());
    	//	CraftingVillager gv = new CraftingVillager(1, "Blacksmith", -239, 63, 677, w.getName());

    	//	gv.register();
    	//	villagers.add(gv);
    	// CraftingVillager gv = new CraftingVillager(1, "Blacksmith", -848.5, 4.0, -535.5, w.getName());
    	//	 gv.register();
    		// System.out.println(ChatColor.RED+"FFSSSSSSSSS");
    //		 villagers.add(gv);
    		//CraftingVillager gv1 = new CraftingVillager(2, "Blacksmith", 1644.5, 4.0, -913.5, w.getName());
    	//	 gv1.register();
    	//	 villagers.add(gv1);   	
    }

}
