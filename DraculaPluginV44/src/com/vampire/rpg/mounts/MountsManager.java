package com.vampire.rpg.mounts;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftHorse;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftInventoryHorse;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamMessages;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamScheduler.Halter;
import com.vampire.rpg.utils.VamTicks;
import com.vampire.rpg.utils.entities.CustomHorse;

import de.slikey.effectlib.util.ParticleEffect;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class MountsManager extends AbstractManager implements Listener{
	public static HashMap<UUID,Mount> mountsUUID= new HashMap<UUID,Mount>();
	public int KYS=5;
	public MountsManager(Pluginc plugin) {
		super(plugin);
	}
	 @EventHandler
	    public void onHorseInv(InventoryClickEvent event) {
	        if (event.getInventory() instanceof CraftInventoryHorse)
	            event.setCancelled(true);
	    }
	 @EventHandler
	 public void onHorseCatch(PlayerInteractEntityEvent e){
		 if(e.getPlayer().getItemInHand()!=null ){
			 if(e.getPlayer().getItemInHand().getItemMeta()!=null){
				 if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()!=null){
			 if( e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("Mount Catcher")){
				 if(mountsUUID.keySet().contains(e.getRightClicked().getUniqueId())){
					 Entity en=e.getRightClicked();
					 if(en.getPassenger()==null){
						 Mount m=mountsUUID.get(en.getUniqueId());
						 ItemStack i= ItemAPI.LoreItem(new ItemStack(Material.SADDLE), m.getDisplay()+" Mount",ChatColor.GRAY+"Right click to release mount");
						 net.minecraft.server.v1_10_R1.ItemStack itemstack = CraftItemStack.asNMSCopy(i);
					        NBTTagCompound comp = itemstack.getTag();
					        if(comp == null)
					            comp = new NBTTagCompound();
					        comp.setString("mountInfo", m.toString());
					        itemstack.setTag(comp);
					        i = CraftItemStack.asBukkitCopy(itemstack);
					        itemstack = CraftItemStack.asNMSCopy(i);					 
						    e.getPlayer().getInventory().addItem(i);
						    m.despawn();
					 }else{
						 return;
					 }
				 }else{
					 return;
				 }
			 }
			 }
		   } 
			 return;
		}
	 }
	 @EventHandler
	    public void onDismount(VehicleExitEvent event) {
	        if (event.getExited() instanceof Player) {
	            Player p = (Player) event.getExited();
	            if (plugin.getPD(p) != null)
	                plugin.getPD(p).riding = false;
	        }
	        if (mountsUUID.keySet().contains(event.getVehicle().getUniqueId())) {
	            Vehicle v = event.getVehicle();
	            VamScheduler.schedule(plugin, new Runnable() {
	                public void run() {
	                    if (v != null && v.isValid()) {
	                        if (v instanceof Horse) {
	                            if (((CraftHorse) v).getHandle() instanceof CustomHorse) {
	                                ((CustomHorse) ((CraftHorse) v).getHandle()).rearUp();
	                            }
	                        }
	                    }
	                }
	            }, 10);
	            VamScheduler.schedule(plugin, new Runnable() {
	                public void run() {
	                    if (v != null && v.isValid()) {
	                        VamParticles.show(ParticleEffect.CLOUD, v.getLocation().add(0, 1, 0), 10);
	                        v.remove();
	                    }
	                }
	            }, 35);
	        }
	    }
	 @EventHandler
	 public void onMountRelease(PlayerInteractEvent e ){
		 if(e.getAction()!=Action.RIGHT_CLICK_AIR && e.getAction()!=Action.RIGHT_CLICK_BLOCK)
			 return;
		 if(e.getItem()==null || e.getItem().getType()==Material.AIR)
			 return;
		 if(e.getItem().getItemMeta()!=null){			
			 if(e.getItem().getItemMeta().getDisplayName()!=null){
				 if(e.getItem().getItemMeta().getDisplayName().contains("Mount")){
					 net.minecraft.server.v1_10_R1.ItemStack itemstack = CraftItemStack.asNMSCopy(e.getItem());
					 if(itemstack.getTag().hasKey("mountInfo")){
						 String s=itemstack.getTag().getString("mountInfo");
						 String[] sa= s.trim().split(":");
						 Mount m= new Mount(MountRarity.valueOf(sa[0]));
						 m.horseVariant=Horse.Variant.valueOf(sa[1]);
						 m.horseColor=Horse.Color.valueOf(sa[2]);
						 m.horseStyle=Horse.Style.valueOf(sa[3]);
						 m.spawn(e.getPlayer());
					 }
				 }
			 }
		 }
	 }
	@Override
	public void initialize() {
	//	int multiplier=VamMath.randInt(1, 3);
	//	int minutes= VamMath.randInt(5, 10);
	/*	VamScheduler.scheduleRepeating(plugin, new Runnable() {
			
			@Override
			public void run() {			
				if(plugin.getServer().getOnlinePlayers().size()!=0){
			//		if(Math.random()>0.1){
						Player[] pl = new Player[plugin.getServer().getOnlinePlayers().size()];
						pl=plugin.getServer().getOnlinePlayers().toArray(pl);
						Player p= VamMath.randObject(pl);
						Mount m=createMount(p.getLocation());
						VamScheduler.schedule(plugin, new Runnable() {
							
							@Override
							public void run() {
								m.remove();
								
							}
						},VamTicks.minutes(1));
						if(m.tier==3)
						VamMessages.announce("Spawned "+m.getDisplay()+" Mount");
		//			}
	//			}else{
					return;
	//			}
				
				}
			}
		}, VamTicks.minutes(2));*/

	}
	public Mount createMount(Location l){
		if(Math.random()>0.5){
			MountRarity ma= MountRarity.COMMON;
			Mount m= new Mount(ma);
			  m.horseColor = VamMath.randObject(Horse.Color.values());
			  m.horseStyle = VamMath.randObject(Horse.Style.values());
			  m.horseVariant = VamMath.randObject(Horse.Variant.values());
			  m.spawn(l);
			  return m;
		}else if(Math.random()>0.2){
			MountRarity ma= MountRarity.RARE;
			Mount m= new Mount(ma);
			  m.horseColor = VamMath.randObject(Horse.Color.values());
			  m.horseStyle = VamMath.randObject(Horse.Style.values());
			  m.horseVariant = VamMath.randObject(Horse.Variant.values());
			  m.spawn(l);
			  return m;
		}else{
			MountRarity ma= MountRarity.DRAKONIC;
			Mount m= new Mount(ma);
			  m.horseColor = VamMath.randObject(Horse.Color.values());
			  m.horseStyle = VamMath.randObject(Horse.Style.values());
			  m.horseVariant = VamMath.randObject(Horse.Variant.values());
			  m.spawn(l);
			  return m;
		}
	}

}
