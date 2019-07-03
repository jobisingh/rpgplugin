package com.vampire.rpg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;

public class QuestResetCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player)){
			return true;
		}
		Player p=(Player)sender;
		PlayerData pd=Pluginc.getPD(p);
		if(arg3.length<1){
			p.sendMessage(ChatColor.RED+"/resetquest [questname]");
			return true;
		}
		if(pd.questProgress.containsKey(arg3[0])){
			pd.questProgress.remove(arg3[0]);
			p.sendMessage("reset quest");
		}else{
			p.sendMessage(ChatColor.RED+"ERROR: "+ChatColor.RESET+arg3[0]+" is not a known quest to humanity");
		}
		return true;
	}

}
