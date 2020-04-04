package com.vampire.rpg.event.player;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.material.Banner;

import com.vampire.rpg.Pluginc;

public class PlayerLogin implements Listener {
	public Pluginc plugin;
	public PlayerLogin(Pluginc pl){
		this.plugin=pl;
	}
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e){
		Player p=e.getPlayer();
		//Banner b=new Banner();
		ItemStack i=new ItemStack(Material.WHITE_BANNER);
		BannerMeta meta=(BannerMeta) i.getItemMeta();
		meta.setBaseColor(DyeColor.LIGHT_BLUE);
		meta.addPattern(new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM));
		meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
		meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CREEPER));
		meta.addPattern(new Pattern(DyeColor.PINK, PatternType.FLOWER));
		meta.addPattern(new Pattern(DyeColor.PINK, PatternType.MOJANG));
		i.setItemMeta(meta);
	//	p.sendMessage("wtf");
	//	p.setMaxHealth(20.0D);
		//p.setHealthScaled(false);
		//p.setMaxHealth(PlayerStats.getHP(p));
		
		/*p.sendMessage("kys is: "+Pluginc.testing.get(p).toString());
		if(Pluginc.testing.containsKey(p)){
			
		p.sendMessage("kys is: "+Pluginc.testing.get(p).toString());
		}
		if(!p.hasPlayedBefore()){
			new BukkitRunnable(){
				public void run(){
					if(p.hasPlayedBefore())return;
					Location tutorial= new Location(p.getWorld(), plugin.getConfig().getDouble("tutorial.x"), plugin.getConfig().getDouble("tutorial.y"), plugin.getConfig().getDouble("tutorial.z"));
					p.teleport(tutorial);
					Bukkit.broadcastMessage(ChatColor.GREEN+"Welcome "+p.getName()+" to the server!");
				}
			}.runTaskLater(Pluginc.getInstance(), 20);
			
		}*/
	}
}
