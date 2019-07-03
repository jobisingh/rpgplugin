package com.vampire.rpg.mailboxes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.menus.MenuItem;
import com.vampire.rpg.menus.MenuManager;
import com.vampire.rpg.utils.VamInventory;
import com.vampire.rpg.utils.VamSerializer;

public class MailboxItem {
	//  static final int MINUTES_PER_HOUR = 60;
	///  static final int SECONDS_PER_MINUTE = 60;
	//  static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	public ItemStack Reward;
//	public ItemStack represnt;
	public LocalDateTime ExpireDate;
	public MailboxItem(ItemStack reward,LocalDateTime d,PlayerData pd,boolean sendMessage){
		this.Reward=reward;	
		this.ExpireDate=d;
		if(pd.Mailbox!=null)
			pd.Mailbox.add(this);
		if(sendMessage)
			pd.getPlayer().sendMessage(ChatColor.GOLD+ChatColor.stripColor(this.Reward.getItemMeta().getDisplayName())+" has been added to your mailbox!");
	}
	public MailboxItem(ItemStack reward,/*ItemStack represent,*/long days,PlayerData pd,boolean sendMessage){
		this.Reward=reward;	
		//represnt=represent;
	//	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		this.ExpireDate=now.plusDays(days);
		if(pd.Mailbox!=null)
		pd.Mailbox.add(this);
		if(sendMessage)
			pd.getPlayer().sendMessage(ChatColor.GOLD+ChatColor.stripColor(this.Reward.getItemMeta().getDisplayName())+" has been added to your mailbox!");
	}
	public static void OpenMenu(Player p,PlayerData pd){
		Inventory i = MenuManager.createMenu(p, p.getName()+"'s Mailbox", 6, new Object[][] {});
		checkItems(pd);
		MailboxItem.displayMalibox(p, pd, i);
		p.openInventory(i);
	}
	public static void displayMalibox(Player p,PlayerData pd,Inventory i){
		
		 int index = 0;
	     int col = 0;
	     int row = 0;	 
		     for(MailboxItem m:pd.Mailbox){
		    	 if (index > 8) {
		                index = 0;
		                row++;
		            }
		            col = index;
		            index++;	     
		            ItemStack item = m.Reward.clone();
		            List<String> lore=item.getItemMeta().getLore();	
		            LocalDateTime now= LocalDateTime.now();
	            lore.add(ChatColor.RED+"Expires in:");	                
			            LocalDateTime tempDateTime = LocalDateTime.from( now );
			            long days = tempDateTime.until( m.ExpireDate, ChronoUnit.DAYS);
			            tempDateTime = tempDateTime.plusDays( days );		
		
			            long hours = tempDateTime.until( m.ExpireDate, ChronoUnit.HOURS);
			            tempDateTime = tempDateTime.plusHours( hours );
		
			            long minutes = tempDateTime.until( m.ExpireDate, ChronoUnit.MINUTES);
			            tempDateTime = tempDateTime.plusMinutes( minutes );
		
			            long seconds = tempDateTime.until( m.ExpireDate, ChronoUnit.SECONDS);
			            
			            if(days>0){
			            	lore.add(ChatColor.RED+String.valueOf(days)+" days, "+String.valueOf(hours+" hours and "+ String.valueOf(minutes)+" minutes."));			          
			            }else{
			            	lore.add(ChatColor.RED+String.valueOf(hours)+" hours, "+ String.valueOf(minutes)+" minutes and "+String.valueOf(seconds)+" seconds.");
			            }
			            lore.add(ChatColor.AQUA+"Click to claim");			    			   
		            MenuManager.modifyMenu(p, i, new MenuItem[]{
		            	new MenuItem(row,col,item,item.getItemMeta().getDisplayName(),lore,new Runnable() {
							
							@Override
							public void run() {
								if(VamInventory.hasEmptySpaces(p, 1)){
									if(now.isBefore(m.ExpireDate)){
									if(pd.Mailbox.contains(m)){
									pd.Mailbox.remove(m);
									p.getInventory().addItem(m.Reward);
									p.closeInventory();
									OpenMenu(p, pd);
										}
									}else{
										p.sendMessage("Has expired");
										if(pd.Mailbox.contains(m)){
											pd.Mailbox.remove(m);
										}
											p.closeInventory();
											displayMalibox(p, pd, i);								
									}
									displayMalibox(p, pd, i);
								}else{
									p.sendMessage(ChatColor.RED+"NO ROOM");
								}							
							}
						}),                    
		            });       
	            }
	     }
	public static void checkItems(PlayerData pd){
		ArrayList<MailboxItem> temp= new ArrayList<MailboxItem>();
		boolean send=false;
		 LocalDateTime now= LocalDateTime.now();
		for(MailboxItem m:pd.Mailbox){
			 if(m.ExpireDate.isBefore(now)){
					if(pd.Mailbox.contains(m)){				
					send=true;
					}
	            }else{
	            	temp.add(m);
	            }
						
		}
		pd.Mailbox=temp;
		if(send)
			pd.getPlayer().sendMessage("removed expired stuff");
	}
	@Override
	public String toString(){
		String s=VamSerializer.serializeItemStack(Reward) +"::"+ExpireDate.toString();
		return s;
		
	}
}
