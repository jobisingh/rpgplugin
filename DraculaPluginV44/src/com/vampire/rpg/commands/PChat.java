package com.vampire.rpg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.parties.PartyManager;

public class PChat implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player)){
			sender.sendMessage("quit life");
			return true;
		}
		Player p=(Player)sender;
	/*	if(!p.getName().equals(Pluginc.Owner_Name) && !p.getName().equals(Pluginc.Host_Name) && !p.isOp()){
			p.sendMessage("no perms m8");
			return true;
		}*/
		if(arg3.length==0){
			p.sendMessage("/pchat <Text>");
			return true;
		}
		PlayerData pd=Pluginc.getPD(p);
		if(pd.party==null){
			p.sendMessage("u have no party");
			return true;
		}
		pd.party.sendMessage(ChatColor.GRAY+"[PARTY]"+ChatColor.RESET+p.getName()+": "+ChatColor.DARK_GREEN+arg3[0], pd.party.uuids);
		return false;
	}

}
