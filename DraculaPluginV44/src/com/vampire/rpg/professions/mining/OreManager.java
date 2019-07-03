package com.vampire.rpg.professions.mining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.professions.Profession;
import com.vampire.rpg.utils.ChunkWrapper;
import com.vampire.rpg.utils.VamMessages;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

public class OreManager extends AbstractManager implements Listener{
	public static HashMap<Location,Ore> ores = new HashMap<Location,Ore>();
	public static ArrayList<Material> picks= new ArrayList<Material>();
    private static HashMap<ChunkWrapper, HashSet<Ore>> oresPerChunk = new HashMap<ChunkWrapper, HashSet<Ore>>();
    
	public OreManager(Pluginc plugin) {
		super(plugin);
	}
	public static void reload() {
		ores.clear();
		oresPerChunk.clear();
		picks.clear();
		//make read files l8r
		//register(new Ore(new Location(Pluginc.getInstance().getServer().getWorld("world"),1635,4,-899), OreType.SAPPHIRE, VamTicks.seconds(5)));
		register(new Ore(new Location(Pluginc.getInstance().getServer().getWorld("world"),189,74,303), OreType.COAL, VamTicks.seconds(3)));
		picks.add(Material.WOOD_PICKAXE);
		picks.add(Material.STONE_PICKAXE);
		picks.add(Material.IRON_PICKAXE);
		picks.add(Material.GOLD_PICKAXE);
		picks.add(Material.DIAMOND_PICKAXE);
	//	for(Location l:ores.keySet())
	//		System.out.println(l.getX()+" "+l.getY()+" "+l.getZ());

	}
	@Override
	public void initialize() {
		reload();
		
	}
	public static void register(Ore o){
		ChunkWrapper cw=new ChunkWrapper(o.getLocation().clone().getChunk());		
        if (!oresPerChunk.containsKey(cw)) {
            oresPerChunk.put(cw, new HashSet<Ore>());
        }
       oresPerChunk.get(cw).add(o);
       o.spawn();
	}
	@EventHandler(priority=EventPriority.HIGH)
	public void onOreMine(BlockBreakEvent e){
		Player p=e.getPlayer();
		PlayerData pd= Pluginc.getPD(p);
		if(pd==null){
			e.setCancelled(true);
			return;
		}
		if(!pd.loadedSQL){
			e.setCancelled(true);
			return;
		}
		for(Location l:OreManager.ores.keySet()){
			if(l.equals(e.getBlock().getLocation()) && e.getBlock().getType()!=Material.BEDROCK){
				e.setExpToDrop(0);
				Ore o=OreManager.ores.get(l);
				e.setCancelled(true);
				if(check(p,o)){
					addExp(p, pd, o);
					o.getLocation().getBlock().setType(Material.BEDROCK);
					o.despawn();
					p.getWorld().dropItemNaturally(o.getLocation().clone().add(0,1,0), new ItemStack(o.getType().getMaterial()));
					
					VamScheduler.schedule(Pluginc.getInstance(), new Runnable() {
						
						@Override
						public void run() {
							o.spawn();							
						}
					}, o.getRespawn());
				}
			}
		}
	}
	public boolean check(Player p,Ore o){
		if(!picks.contains(p.getEquipment().getItemInMainHand().getType())){
			p.sendMessage(ChatColor.RED+"You must use a pickaxe");
			return false;
		}
		PlayerData pd=Pluginc.getPD(p);	
		if(pd.getProfessionLevel(Profession.MINING)<o.getType().getLevel()){
			p.sendMessage(ChatColor.RED+"Your mining level is too low!");
			p.sendMessage(ChatColor.RED+"You need "+String.valueOf(o.getType().getLevel()-pd.getProfessionLevel(Profession.MINING))+" more levels");
			return false;
		}		
		return true;
	}
	public int getExpForNextLevel(int currLvl){
		if(currLvl>=100)
			return 12312233;
       return currLvl*30+35;
	}
	public void addExp(Player p, PlayerData pd,Ore o){
		int amount=o.getType().getExp();
		pd.setProfessionExp(Profession.MINING, pd.getProfessionExp(Profession.MINING)+amount);
		int level=pd.getProfessionLevel(Profession.MINING);
		int exp=pd.getProfessionExp(Profession.MINING);
		 p.sendMessage(ChatColor.GRAY + ">> +" + amount + " EXP [" + exp + "/" + getExpForNextLevel(level) + "]" );
		if(exp>=getExpForNextLevel(level)){
			int extra = exp - getExpForNextLevel(level);
            pd.setProfessionExp(Profession.MINING, 0);
            pd.setProfessionLevel(Profession.MINING, pd.getProfessionLevel(Profession.MINING)+1);
            int lvel=pd.getProfessionLevel(Profession.MINING);
            pd.setProfessionExp(Profession.MINING,extra < getExpForNextLevel(lvel) ? extra : getExpForNextLevel(lvel) - 1);
            p.sendMessage(ChatColor.GOLD + " You leveled up mining! Your mining level now is " + ChatColor.YELLOW + lvel + ChatColor.GOLD + ".");
		}
	}
}
