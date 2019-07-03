package com.vampire.rpg.comapss;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.menus.MenuManager;
import com.vampire.rpg.options.OptionsManager;
import com.vampire.rpg.particles.ParticleManager;
import com.vampire.rpg.professions.ProffesionManager;
import com.vampire.rpg.regions.RegionManager;
import com.vampire.rpg.utils.fanciful.FancyMessage;

import net.minecraft.server.v1_10_R1.RunnableSaveScoreboard;

public class MenuCompass extends AbstractManager implements Listener{

	public MenuCompass(Pluginc plugin) {
		super(plugin);		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	public static void showMenu(Player p,PlayerData pd){
		Inventory i= MenuManager.createMenu(p, p.getName()+"'s Menu", 6, new Object[][]{
			{1,1,Material.REDSTONE_COMPARATOR,ChatColor.WHITE+"Settings",new Object[]{
					ChatColor.WHITE,
					"Click here to access your settings"
			},new Runnable() {
				
				@Override
				public void run() {
					OptionsManager.openMenu(p, pd);
					
				}
			}				
			},
			{1,4,Material.GOLD_BLOCK,ChatColor.GREEN+"Shop",new Object[]{
					ChatColor.WHITE,
					"Click here to get to the shop",
					ChatColor.WHITE,
					"In the shop you can purchase awesome cosmetics and pets!"
			},new Runnable() {
				
				@Override
				public void run() {
					FancyMessage fm= new FancyMessage();
					fm.text("Click here to get to the shop!").color(ChatColor.DARK_PURPLE);
					fm.send(p);
					
				}
			}				
			},
			{1,7,ItemAPI.getPlayerSkull(p.getName()),ChatColor.WHITE+"Stats",new Object[]{
					ChatColor.WHITE,
					"Click here to view your stats",
			},new Runnable() {
				
				@Override
				public void run() {
					// TODO Open a book with player's stats
					
				}
			}				
			},
			{3,1,Material.GOLD_PICKAXE,ChatColor.WHITE+"Proffesions",new Object[]{
					ChatColor.WHITE,
					"Click here to view your proffesions",
			},new Runnable() {
				
				@Override
				public void run() {
					ProffesionManager.OpenInv(p, pd);
					
				}
			}			
			},
			{3,4,Material.EYE_OF_ENDER,ChatColor.WHITE+"Effects",new Object[]{
					ChatColor.WHITE,
					"Click here to view your cosmetics and effects"
			},new Runnable() {
				
				@Override
				public void run() {
					ParticleManager.showMenu(p, pd);
					
				}
			}				
			},
			{3,7,Material.BOOK,ChatColor.WHITE+"Achievements",new Object[]{
					ChatColor.WHITE,
					"Click here to view the achievements"
			},new Runnable() {
				
				@Override
				public void run() {
					// TODO Open Achievements
					
				}
			}				
			},
			{5,8,Material.CHEST,ChatColor.WHITE+"Bank",new Object[]{
					
			},new Runnable() {				
				@Override
				public void run() {
					pd.openBank();			
				}
			}
				
			},
			{0,8,Material.ANVIL,ChatColor.WHITE+"Blacksmithing",new Object[]{
					
			},new Runnable() {				
				@Override
				public void run() {
					ProffesionManager.openBlacksmithMenu(p, pd);					
				}
			}
				
			}
		});
		p.openInventory(i);
	}
}
