package com.vampire.rpg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.mobs.MobManager;
import com.vampire.rpg.professions.mining.OreManager;
import com.vampire.rpg.utils.VamMessages;

public class ReloadOres implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player)){
			OreManager.reload();
			VamMessages.announce("reloading Ores");
			return true;
		}
		Player p=(Player)sender;
		if(ItemAPI.isNotWorthy(p)){
			p.sendMessage("No perms");
			return true;
		}
		OreManager.reload();
		VamMessages.announce("reloading Ores");
		return true;
	}

}
