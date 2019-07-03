package com.vampire.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.sql.SQLManager;
import com.vampire.rpg.utils.VamMessages;

public class StartDatabaseCommand extends AbstractCommand {


	public StartDatabaseCommand(String... commandNames) {
		super(commandNames);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		System.out.println(ChatColor.GOLD+"tyring to start a db...");
		SQLManager.createTable();
		VamMessages.announce("created db");
		
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
