package com.vampire.rpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.crafting.CraftedAPI;
import com.vampire.rpg.crafting.CraftedItem;

public class Craft implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label2, String[] arg3) {
		if(!(sender instanceof Player)){
			return true;
		}
		Player p =(Player)sender;	
		CraftedAPI.OpenCraftingInv(p);
		return true;
	}

}
