package com.vampire.rpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.mailboxes.MailboxItem;
import com.vampire.rpg.menus.MenuManager;

public class MailboxCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		Player p=(Player)sender;
		PlayerData pd=Pluginc.getPD(p);
		if(!pd.loadedSQL)
			return true;
	MailboxItem.OpenMenu(p, pd);
		return false;
	}

}
