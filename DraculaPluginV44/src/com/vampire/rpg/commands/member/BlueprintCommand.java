package com.vampire.rpg.commands.member;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.crafting.CraftedAPI;
import com.vampire.rpg.crafting.CraftedItem;
import com.vampire.rpg.utils.VamMath;

public class BlueprintCommand extends AbstractCommand{
	
	public BlueprintCommand(String... commandNames) {
        super(commandNames);
    }

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executePlayer(Player p, PlayerData pd, String[] args) {
		if(args.length==0){

				CraftedItem ci=(CraftedItem) VamMath.randObject(Pluginc.allCraftedItems.toArray());
				p.sendMessage(" u got the blueprint for:"+ci.getCraftedItemName());
				CraftedAPI.giveBlueprintItem(p, ChatColor.stripColor(ci.getCraftedItemName()));
				p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 5, 1);
			return;
			}
			if(args.length==1){
				
			for(CraftedItem ci:Pluginc.getAllCraftedItems()){
				if(ChatColor.stripColor(ci.getCraftedItemName()).toLowerCase().equals(ChatColor.stripColor(args[0].toLowerCase()))){
					p.sendMessage(" u got the blueprint for:"+ci.getCraftedItemName());
					CraftedAPI.giveBlueprintItem(p, ChatColor.stripColor(ci.getCraftedItemName()));
					p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 5, 1);
					return;
					}
				}
			p.sendMessage(ChatColor.RED+"invalid name");
			return;
			}
			StringBuilder a=new StringBuilder();
			for(int i=0;i<args.length;i++){
				//a+=" ";
				a.append(args[i]);
				a.append(" ");
			}
			p.sendMessage("====>   "+a.toString().trim());
			for(CraftedItem ci:Pluginc.getAllCraftedItems()){
				if(ChatColor.stripColor(ci.getCraftedItemName()).toLowerCase().equals(ChatColor.stripColor(a.toString()).toLowerCase().trim())){
					p.sendMessage(ChatColor.BLUE+"You got the blueprint for: "+ChatColor.RESET+ci.getCraftedItemName());
					CraftedAPI.giveBlueprintItem(p, ChatColor.stripColor(ci.getCraftedItemName()));
					p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 5, 1);

					return;
					}
			//return false;
			}
			return;
		
	}

	@Override
	public void executeConsole(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
