package com.vampire.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.mobs.MobManager;
import com.vampire.rpg.shops.ShopManager;
import com.vampire.rpg.utils.VamMessages;

public  class ReloadMobsCommand extends AbstractCommand{
	
	 public ReloadMobsCommand(String... commandNames) {
	        super(commandNames);
	    }

	    @Override
	    public void execute(CommandSender sender, String[] args) {
	    	MobManager.reload();
	        VamMessages.announce(ChatColor.RED + "Mobs and spawns reloaded for updates. Sorry for the inconvenience.");
	    }

	    @Override
	    public void executePlayer(Player p, PlayerData pd, String[] args) {
	    	
	    }

	    @Override
	    public void executeConsole(CommandSender sender, String[] args) {
	    }
}
