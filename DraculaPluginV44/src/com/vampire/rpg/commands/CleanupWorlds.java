package com.vampire.rpg.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.vampire.rpg.Pluginc;
import com.vampire.rpg.npcs.NPCManager;
import com.vampire.rpg.utils.VamMessages;

public class CleanupWorlds implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		VamMessages.announce("Cleaning up worlds hopefully");
		for(World w:Pluginc.getInstance().getServer().getWorlds()){
			if(w.getName().equals("world")){
				for(Entity e:w.getEntities()){
					if(!(e instanceof Player) && !NPCManager.npcs.containsKey(e.getUniqueId()))
						e.remove();
			}
				}
			else{	
				for(Entity e:w.getEntities()){
					if(!(e instanceof Player))
							e.remove();
				}
			}
		}
		return false;
	}

}
