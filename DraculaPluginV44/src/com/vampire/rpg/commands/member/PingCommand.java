package com.vampire.rpg.commands.member;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.Rank;
import com.vampire.rpg.commands.AbstractCommand;

public class PingCommand extends AbstractCommand {
	
	 public PingCommand(String... commandNames) {
	        super(commandNames);
	 }
	public int getPing(Player player) {
	    return ((CraftPlayer) player).getHandle().ping;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public void executePlayer(Player p, PlayerData pd, String[] args) {
		if(args.length==0)
			p.sendMessage(ChatColor.AQUA+"Your ping is: "+ChatColor.RESET+Integer.toString(getPing(p))+"ms");
			if(args.length>0){
				if(ItemAPI.isNotWorthy(p)){
					p.sendMessage(ChatColor.AQUA+"Your ping is: "+ChatColor.RESET+Integer.toString(getPing(p))+"ms");
					return;
				}
				Player target=Pluginc.getInstance().getServer().getPlayer(args[0]);
				if(target==null || !target.isOnline()){
					p.sendMessage("is not online");
					return;
				}
				p.sendMessage(ChatColor.AQUA+target.getName()+"'s ping is: "+ChatColor.RESET+Integer.toString(getPing(target))+"ms");
			}

	}
	
	@Override
	public void executeConsole(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}

}
