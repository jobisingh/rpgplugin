package com.vampire.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//import com.avaje.ebeaninternal.server.deploy.parse.ValidatorFactoryManager;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.utils.VamMessages;
import com.vampire.rpg.utils.VamSound;

public class AnnounceCommand extends AbstractCommand {

	public AnnounceCommand(String... commandNames) {
        super(commandNames);
    }


	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executePlayer(Player p, PlayerData pd, String[] args) {
		if(args.length==0){
			p.sendMessage(ChatColor.RED+"/announce <Message>");
			return;
		}
		StringBuilder sb= new StringBuilder();
		for(String s:args)
			sb.append(s+ " ");
		for(Player pa:Pluginc.getInstance().getServer().getOnlinePlayers()){
			VamSound.playSound(pa, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
		}
		VamMessages.announce(ChatColor.RED+p.getName()+": "+ChatColor.YELLOW+sb.toString().trim());
		return;
	}

	@Override
	public void executeConsole(CommandSender sender, String[] args) {
		if(args.length==0){
			sender.sendMessage(ChatColor.RED+"/announce <Message>");
			return;
		}
		StringBuilder sb= new StringBuilder();
		for(String s:args)
			sb.append(s+" ");
		for(Player pa:Pluginc.getInstance().getServer().getOnlinePlayers()){
			VamSound.playSound(pa, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
		}
		VamMessages.announce(ChatColor.RED+"CONSOLE: "+ChatColor.YELLOW+sb.toString().trim());
		
	}

}
