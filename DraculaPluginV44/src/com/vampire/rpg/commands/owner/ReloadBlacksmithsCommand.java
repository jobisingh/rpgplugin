package com.vampire.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.crafting.CraftingManager;
import com.vampire.rpg.npcs.NPCManager;

public class ReloadBlacksmithsCommand extends AbstractCommand {
	
	public ReloadBlacksmithsCommand(String... commandNames) {
        super(commandNames);
    }
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		CraftingManager.reload();
		for(World w:Pluginc.getInstance().getServer().getWorlds()){
			for(Entity e:w.getEntities()){
				if(e.getName().equals(ChatColor.DARK_PURPLE+"Blacksmith") && !NPCManager.npcs.containsKey(e.getUniqueId())){
					e.remove();
				}
			}
		}
		sender.sendMessage("reloaded blacksmiths");
		
	}

	@Override
	public void executePlayer(Player p, PlayerData pd, String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeConsole(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
