package com.vampire.rpg.commands.member;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.comapss.MenuCompass;
import com.vampire.rpg.commands.AbstractCommand;

public class MenuCommand extends AbstractCommand {
	
	 public MenuCommand(String... commandNames) {
	        super(commandNames);
	 }
	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executePlayer(Player p, PlayerData pd, String[] args) {
		MenuCompass.showMenu(p, pd);

	}

	@Override
	public void executeConsole(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}

}
