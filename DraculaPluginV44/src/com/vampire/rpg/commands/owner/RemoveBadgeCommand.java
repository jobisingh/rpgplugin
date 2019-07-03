package com.vampire.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.badges.Badge;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.utils.VamMessages;

public class RemoveBadgeCommand extends AbstractCommand {
	
	public RemoveBadgeCommand(String... commandNames) {
        super(commandNames);
    }
	@Override
	public void execute(CommandSender sender, String[] args) {
		 if (args.length == 2) {
	            Badge badge = Badge.valueOf(args[1].toUpperCase());
	            Player p2 = Pluginc.getInstance().getServer().getPlayer(args[0]);
	            if (p2 != null && p2.isValid() && p2.isOnline()) {
	                PlayerData pd2 = Pluginc.getPD(p2);
	                pd2.badges.remove(badge);
	                sender.sendMessage(ChatColor.GREEN + p2.getName() + "'s badge " + badge.getDisplayName() + ChatColor.GREEN + " was removed.");		            
	            } else {
	                sender.sendMessage("User is not online.");
	            }
	        }
		
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
