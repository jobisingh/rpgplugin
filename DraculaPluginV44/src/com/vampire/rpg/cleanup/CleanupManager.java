package com.vampire.rpg.cleanup;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.utils.VamMessages;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

public class CleanupManager extends AbstractManager {

	public CleanupManager(Pluginc plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		VamScheduler.scheduleRepeating(plugin, new Runnable() {
			
			@Override
			public void run() {
			//	ConsoleCommandSender console= plugin.getServer().getConsoleSender();
				VamMessages.announce(ChatColor.RED+"Cleaning up worlds hopefully,sorry for any inconviense");
				for(World w:Pluginc.getInstance().getServer().getWorlds()){
					if(w.getName().equals("world"))
						continue;
					for(Entity e:w.getEntities()){
						if(!(e instanceof Player))
								e.remove();
					}
				}				
				
			}
		}, VamTicks.minutes(10));

	}

}
