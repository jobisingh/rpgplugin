package com.vampire.rpg.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.vampire.rpg.Pluginc;

public class Vanish implements CommandExecutor,Listener{

	private ArrayList<Player> vanished= new ArrayList<Player>();
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "Nope!, you are not a player!");
            return true;
		}
		Player p=(Player)sender;
		if(!p.getName().equals(Pluginc.Owner_Name) && !p.getName().equals(Pluginc.Host_Name)){
			p.sendMessage("No perms");
			return true;
		}
		if(!p.isOp()){
			p.sendMessage(ChatColor.RED+"u dont have permission");
		}
		if(!vanished.contains(p)){
		for(Player pl:Bukkit.getServer().getOnlinePlayers()){
			pl.hidePlayer(p);
		}
		vanished.add(p);
		p.sendMessage(ChatColor.GREEN+"vanished");
		}
		else{
			for(Player pl:Bukkit.getServer().getOnlinePlayers()){
				pl.showPlayer(p);
			}
			vanished.remove(p);
			p.sendMessage(ChatColor.GREEN+"unvanished");
		}
		return false;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		for(Player p:vanished){
			e.getPlayer().hidePlayer(p);
			}
		}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		vanished.remove(e.getPlayer());
		
	}

}
