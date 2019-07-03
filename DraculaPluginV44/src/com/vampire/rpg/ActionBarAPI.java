package com.vampire.rpg;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;

public class ActionBarAPI {
	
	public static void sendBar(Player p,String s){
		 IChatBaseComponent barmsg = ChatSerializer.a("{\"text\":\"" + s + "\"}");
		 PacketPlayOutChat bar = new PacketPlayOutChat (barmsg,(byte) 2);
		 ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
	}
	/* public static void sendActionBar(Player p, String msg) {
	        IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
	        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
	        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	    }*/
	public static void sendBar(Player p,String s,int duration){
		// IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s +"\"}");
		 IChatBaseComponent barmsg = ChatSerializer.a("{\"text\":\"" + s + "\"}");
		 PacketPlayOutChat bar = new PacketPlayOutChat (barmsg,(byte) 2);
		 ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
		 if (duration >= 0)
		    {
		      new BukkitRunnable(){
		    	  public void run(){
		    		  ActionBarAPI.sendBar(p, s);
		    	  }
		      }.runTaskLater(Pluginc.getInstance(), duration+1);
		    
		      


		  }
		 while (duration > 60) {
		      duration -= 60;
		      int sched = duration % 60;
		      new BukkitRunnable(){

		        public void run() { 
		        	ActionBarAPI.sendBar(p, s); 
		        	} 
		        
		        }.runTaskLater(Pluginc.getInstance(), sched);
		    }
		 
	}
}
