package com.vampire.rpg.event.player;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.comphenix.packetwrapper.WrapperPlayServerCustomSoundEffect;
import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.packetwrapper.WrapperPlayServerNamedSoundEffect;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.SoundCategory;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.options.Option;
import com.vampire.rpg.utils.entities.CustomBlaze;
import com.vampire.rpg.utils.entities.CustomItem;

import net.minecraft.server.v1_10_R1.DataWatcher;
import net.minecraft.server.v1_10_R1.DataWatcher.Item;
import net.minecraft.server.v1_10_R1.DataWatcherObject;
import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.PacketDataSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn;

public class PlayerDropItem implements Listener{
		@EventHandler
		public void onPlayerDropItem(PlayerDropItemEvent e){
			if(e.getItemDrop().getItemStack().hasItemMeta()){
				String name= e.getItemDrop().getItemStack().getItemMeta().getDisplayName();
				if(ChatColor.getByChar(name)!=ChatColor.WHITE){
				//	e.getItemDrop().setCustomName(name);
				//	e.getItemDrop().setCustomNameVisible(true);
					for(Player p:Pluginc.getInstance().getServer().getOnlinePlayers()){
						PlayerData pd=Pluginc.getPD(p);
						if(!pd.loadedSQL)
							continue;
						if(!pd.getOption(Option.TAGS_ABOVE_ITEMS))
							continue;
						if(p.getName().equals(Pluginc.Owner_Name))
							continue;
					/*	DataWatcher d=null;
						Field f;
						Field f3;
						try {
							f = Entity.class.getDeclaredField("aA");
							f.setAccessible(true);
							DataWatcherObject<String> iWantThis = (DataWatcherObject<String>) f.get(Entity.class);
							d.set(iWantThis, name);
							f3 = Entity.class.getDeclaredField("aB");
							f3.setAccessible(true);
							DataWatcherObject<Boolean> iWantThis2 = (DataWatcherObject<Boolean>) f3.get(Entity.class);
							d.set(iWantThis2, Boolean.valueOf(true));
						} catch (NoSuchFieldException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SecurityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} //NoSuchFieldException
					//	f.setAccessible(true);
						//DataWatcherObject<String> iWantThis = null;						
						 catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} //IllegalAccessException
					//	d.set(new DataWatcherObject<String>(0, null), "ffs");
					//	d.register(iWantThis, name);
					//	DataWatcherObject wat= new DataWatcherObject(2, DataWatcherSerializer<String>());
					//	d.set(2,"TEST");
					//	d.set(3,true);									
					//	ProtocolLibrary.getProtocolManager().sendServerPacket(p, new PacketContainer());*/
					/*	PacketContainer pc= new PacketContainer(PacketType.Play.Server.NAMED_SOUND_EFFECT);
					    WrapperPlayServerNamedSoundEffect wrap= new WrapperPlayServerNamedSoundEffect(pc);
					    wrap.setSoundEffect(Sound.ENTITY_HUSK_HURT);
					    wrap.setSoundCategory(SoundCategory.HOSTILE);
					    wrap.setPitch(7);
					    wrap.setVolume(6);
					    wrap.sendPacket(p);*/
						/*	PacketContainer pc= new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
						WrapperPlayServerEntityMetadata wrapper= new WrapperPlayServerEntityMetadata(pc);
						wrapper.setEntityID(e.getItemDrop().getEntityId());
						List<WrappedWatchableObject> list= wrapper.getMetadata();
						WrappedWatchableObject oname= new WrappedWatchableObject(name);
						oname.setValue(name);			
						list.set(2, oname);
						WrappedWatchableObject oboolean= new WrappedWatchableObject(true);
						oboolean.setValue(true);
						list.set(3, oboolean);
						wrapper.setMetadata(list);
						wrapper.sendPacket(p);*/
					//	PacketPlayOutEntityMetadata pack= new PacketPlayOutEntityMetadata(e.getItemDrop().getEntityId(),d , false);
					//	((CraftPlayer)p).getHandle().playerConnection.sendPacket(pack);
						//((CraftPlayer)p).getHandle().
					}
				//	PacketPlayOutNamedEntitySpawn a= new PacketPlayOutNamedEntitySpawn(e.getPlayer());
				//	PacketPlayOutEntityMetadata pa= new PacketPlayOutEntityMetadata(f);
				//	e.getItemDrop().setCustomNameVisible(true);
				}
			}
			//e.getItemDrop().setCustomName("check");
			//e.getItemDrop().setCustomNameVisible(true);
		}
		@EventHandler
		public void onPlayerPickupItem(PlayerPickupItemEvent e){
		}
	//	public DataWatcher clone(Entity e){
	//		DataWatcher d=new DataWatcher(e);
		//	DataWatcher temp=((CraftEntity)e).getHandle().getDataWatcher();
		/*	d.register(Entity.aa, );
		    d.register(Entity.az, Integer.valueOf(300));
		    d.register(Entity.aB, Boolean.valueOf(false));
		    d.register(Entity.aA, "");
		    d.register(Entity.aC, Boolean.valueOf(false));
		    d.register(Entity.aD, Boolean.valueOf(false));
		}
		public DataWatcher clone(DataWatcher d){
			DataWatcher da= new DataWatcher(d.);
			for(Item<?> i:d.c()){
				da.set(i.a(), i.b());
			}
			return;*/
		//}
}
