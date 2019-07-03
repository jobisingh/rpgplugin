package com.vampire.rpg.parties;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.Rank;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

public class PartyManager extends AbstractManager{
public PartyManager(Pluginc plugin) {
		super(plugin);
	}
protected static final String PREFIX = ChatColor.GRAY + "[Party] " + ChatColor.YELLOW;
protected static final String PREFIX_SYSTEM = ChatColor.GRAY + "[Party] " + ChatColor.DARK_AQUA;
	public static ArrayList<Party> parties= new ArrayList<Party>();
	public static void createParty(Player p){
		PlayerData pd=Pluginc.getPD(p);
		if(pd.party!=null){
			p.sendMessage(ChatColor.RED+"You are already in a party! leave your current one to create a new one");
			return;
		}
		Party pk= new Party(p);
		pd.party = pk;
		parties.add(pk);
		p.sendMessage(ChatColor.DARK_AQUA+" You've created a new party!");
	}
	public static boolean sameParty(Player p1,Player p2){
		  PlayerData pd = Pluginc.getPD(p1);
	      PlayerData pd2 = Pluginc.getPD(p2);
	      if (pd == null || pd2 == null)
	            return false;
	        if (pd.party == null || pd2.party == null)
	            return false;
	        return pd.party == pd2.party;
	}
	
	public static void createParty(Player p, PlayerData pd) {
        if (pd.party != null) {
            p.sendMessage(ChatColor.RED + " You are already in a party!");
            p.sendMessage(ChatColor.RED + " Leave your current party to create a new one.");
        } else {
            Party party = new Party(p);
            pd.party = party;
            parties.add(party);
            p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.GREEN + "You have created a new party!");
        }

    }

	
	@Override
	public void initialize() {
		 VamScheduler.scheduleRepeating(plugin, new Runnable() {
	            public void run() {
	                for (int k = 0; k < parties.size(); k++) {
	                    Party p = parties.get(k);
	                    if (p.uuids.size() == 0) {
	                        parties.remove(k);
	                        if (p != null)
	                            p.destroy();
	                        k--;
	                        if (k < 0)
	                            k = 0;
	                    }
	                }
	            }
	        }, VamTicks.seconds(30));
		
	}
	public static void updatePlayerForAll(String name, int hp, Rank rank, Party party) {
        if (parties != null) {
            for (Party p : parties) {
                if (p == null)
                    continue;
                p.updatePlayer(name, hp, rank, party != null && party == p);
            }
        }

    }

}
