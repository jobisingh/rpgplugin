package com.vampire.rpg.commands.owner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.holograms.HologramManager;
import com.vampire.rpg.sql.SQLManager;
import com.vampire.rpg.utils.VamMessages;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamSerializer;

public class ViewBankCommand extends AbstractCommand{
	
	public ViewBankCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        
    }

    @Override
    public void executePlayer(Player p, PlayerData pd, String[] args) {
    	if(args.length==0){
			p.sendMessage(ChatColor.RED+"/<command> [Player]");
			return;
		}		
		 Player target = Pluginc.getInstance().getServer().getPlayerExact(args[0]);
	        if (target != null && target.isOnline() && Pluginc.getInstance().getPD(target) != null) {
	            PlayerData pd2 = Pluginc.getPD(target);
	            p.openInventory((pd2.bank));
	            p.sendMessage(ChatColor.GRAY+">>"+ChatColor.WHITE+" Opened bank of " + args[0]);
	            return;
	        }
	        else{
	        	p.sendMessage("Trying to access "+args[0]+"'s bank from database...");
	        	VamScheduler.scheduleAsync(Pluginc.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						SQLManager.OpenConnetion();
							PreparedStatement sql;
							try {
								sql = SQLManager.connection.prepareStatement("SELECT Bank FROM main WHERE name=?;");
								sql.setString(1, args[0]);
								ResultSet result = sql.executeQuery();
								boolean containsplayer = result.next();
								if(!containsplayer){
									sql.close();
									result.close();
									p.sendMessage(ChatColor.RED+"ERROR: Player has no data");
									return;
								}
								Inventory inv= Bukkit.createInventory(p, 54, "Bank");
								//pd.loadBank(result.getString("Bank"),inv);	
								p.openInventory(loadBank(result.getString("Bank"), inv));
								p.sendMessage(ChatColor.GRAY+">>"+ChatColor.WHITE+" Opened bank of " + args[0]);
								sql.execute();
								sql.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								p.sendMessage(ChatColor.RED+"ERROR: Player has no data");
							}					
					//	}
						//	catch(Exception e){
						//	p.sendMessage(ChatColor.RED+"ERROR: Player has no data");
					//	}
							SQLManager.closeConnetion();
					}

				});
	        	return;
	        }
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    	
    }

	public Inventory loadBank(String s,Inventory inv){
		HashMap<Integer, ItemStack> map = new HashMap<Integer, ItemStack>();
		if (s == null){
		//	this.bank=inv;
        return inv;
		}
		if(s.equals("null")){
		//	this.bank=inv;
				return inv;
		}
	    String[] data = s.split("@");
	    if (data.length == 0 || (data.length == 1 && data[0].equals(""))){
	    //	this.bank=inv;
	        return inv;
	    }
	    for (String temp : data) {
	        try {
	            // don't use split in case item serialization contains ::
	            String a = temp.substring(0, temp.indexOf("::"));
	            String b = temp.substring(temp.indexOf("::") + "::".length());
	            int k = Integer.parseInt(a);
	            ItemStack item = VamSerializer.deserializeItemStack(b);
	           map.put(k, item);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	  //  this.bank=inv;
	   // bank.clear();
	   for (Entry<Integer, ItemStack> e : map.entrySet()) {
	      inv.setItem(e.getKey(), e.getValue());
	    }
	  return inv;
	}

}
