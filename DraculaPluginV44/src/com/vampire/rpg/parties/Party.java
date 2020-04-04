package com.vampire.rpg.parties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.Rank;
import com.vampire.rpg.chat.ChatFilter;
import com.vampire.rpg.chat.ChatManager;
import com.vampire.rpg.options.Option;
import com.vampire.rpg.utils.VamSound;
import com.vampire.rpg.utils.fanciful.FancyMessage;

public class Party {
	public HashMap<String, Long> lastInv = new HashMap<String, Long>();
	
	 private static int ID = 1;

	 public int id = ID++;
	    
	public int MAX_PLAYERS=7;
	
	private UUID leadersUUID;
	
	private Player leader;
	

    private ArrayList<Player> cachedPlayers = new ArrayList<Player>();
    
	public ArrayList<UUID> uuids = new ArrayList<UUID>();
	
	private boolean lootshare = false;
	
    private long lastPlayerUpdate = 0;
    
	private Scoreboard board;
	public Party(Player p){
		this.leader=p;
		this.leadersUUID=p.getUniqueId();
		board=Bukkit.getScoreboardManager().getNewScoreboard();
		 Objective objective = board.registerNewObjective("hpdisplay", "dummy");
	     objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
	     objective.setDisplayName(ChatColor.DARK_RED + "\u2764");
	     Objective objective_side = board.registerNewObjective("partyhpside", "dummy");
	     objective_side.setDisplaySlot(DisplaySlot.SIDEBAR);
	     objective_side.setDisplayName(ChatColor.YELLOW + "Party Member " + ChatColor.RED + "   HP" + ChatColor.RESET);
	     addPlayer(p);
	}
	public void addPlayer(Player p){
		if(isFull()){
			p.sendMessage(ChatColor.RED+"Party is full already");
			sendMessage(p.getName()+" tried to join the party, but failed because it's full",this.uuids);
			return;
		}
		lastPlayerUpdate = 0;
        p.setScoreboard(board);
		sendMessage(p.getName()+" joined "+this.leader.getName()+"'s"+" party!",this.uuids);
		uuids.add(p.getUniqueId());
		p.setScoreboard(board);
		PlayerData pd = Pluginc.getPD(p);
        if (pd != null) {
            pd.party = this;
        }
		//sendMessage(p.getName()+" joined "+this.leader.getName()+"'s"+" party!",this.uuids);
	}
	public void LeavePlayer(UUID uuid, String name){
		 if (uuid == null)
	            return;
	        board.resetScores(name);
	        Player player = Pluginc.getInstance().getServer().getPlayer(uuid);
	        if (player != null && player.isOnline() && player.isValid() && ((CraftPlayer) player).getHandle() != null && ((CraftPlayer) player).getHandle().playerConnection != null) {
	            if (!((CraftPlayer) player).getHandle().playerConnection.isDisconnected()) {
	                try {
	                    player.setScoreboard(PlayerData.board);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        if (this.leadersUUID != null && uuid == this.leadersUUID) {
	            sendMessage(ChatColor.DARK_GREEN+name + " has left the party.",this.uuids);
	            if (player != null && player.isOnline()) {
	                PlayerData pd = Pluginc.getInstance().getPD(player);
	                if (pd != null)
	                    pd.party = null;
	            }
	            this.uuids.remove(uuid);
	            lastPlayerUpdate = 0;
	            if (this.uuids.size() == 0) {
	                destroy();
	            } else {
	                for (int k = 0; k < uuids.size(); k++) {
	                    UUID nu = uuids.get(k);
	                    Player temp = Pluginc.getInstance().getServer().getPlayer(nu);
	                    if (temp != null && temp.isOnline()) {
	                        sendMessage(ChatColor.DARK_GREEN+temp.getName() + " is the new party leader.",this.uuids);
	                        this.leadersUUID = nu;
	                      //  this.leaderName = temp.getName();
	                        break;
	                    }
	                }
	            }
	        } else {
	            sendMessage(ChatColor.DARK_GREEN+name + " has left the party.",this.uuids);
	            if (player != null && player.isOnline()) {
	                PlayerData pd = Pluginc.getPD(player);
	                if (pd != null)
	                    pd.party = null;
	            }
	            this.uuids.remove(uuid);
	            lastPlayerUpdate = 0;
	            if (this.uuids.size() == 0)
	                destroy();
	        }
	}
	public void KickPlayer(Player p){
		if(p!=null){
			p.sendMessage(ChatColor.DARK_GREEN+"You were kicked out of the party");
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
			LeavePlayer(p.getUniqueId(), p.getName());
			sendMessage(ChatColor.DARK_GREEN+p.getName() +" has been kicked out of the party", uuids);
		}
	}
	 public void updatePlayer(String name, int hp, Rank rank, boolean inParty) {
	        if (board == null)
	            return;
	        final Score score = board.getObjective("hpdisplay").getScore(name);
	        score.setScore(hp);

	        if (inParty) {
	            final Score score_side = board.getObjective("partyhpside").getScore(name);
	            score_side.setScore(hp);
	        }

	        Team team = board.getTeam(rank.rankDisplayName);
	        if (team == null) {
	            team = board.registerNewTeam(rank.rankDisplayName);
	            team.setPrefix(rank.nameColor + "");
	            team.setAllowFriendlyFire(true);
	            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
	        }
	        if (!team.hasEntry(name))
	            team.addEntry(name);
	    }

	public boolean isFull(){
		if(this.uuids==null)
			return true;
		return this.uuids.size() >= MAX_PLAYERS;
	}
	public void promotePlayer(Player promoter,Player p){
		PlayerData pd=Pluginc.getPD(p);
		if(!isLeader(promoter)){
			promoter.sendMessage(ChatColor.RED+"You can't promote players if you are not the leader!");
			return;
		}
		if(pd.party==null || !PartyManager.sameParty(promoter, p)){
			promoter.sendMessage(ChatColor.RED+p.getName()+" isn't in your party!");
			return;
		}
		if(p!=null){
			this.leader=p;
			this.leadersUUID=p.getUniqueId();
			sendMessage(p.getName()+" has been promoted to leader!", this.uuids);
			return;
		}
	}
	public void sendInvite(Player p,PlayerData pd,Player inviter){
		 if (pd.party == this) {
	            inviter.sendMessage(ChatColor.RED + p.getName() + " is already in the party!");
	            return;
	        }
	        if (lastInv.containsKey(p.getName())) {
	            if (System.currentTimeMillis() - lastInv.get(p.getName()) < 15000) {
	                inviter.sendMessage(ChatColor.RED + p.getName() + " was invited to the party in the last 15 seconds. Please wait a bit before inviting them again.");
	                return;
	            }
	        }
		lastInv.put(p.getName(), System.currentTimeMillis());
		 pd.invitedParty = this;
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
		p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.GREEN + "You have received a party invite from " + ChatColor.YELLOW + inviter.getName() + ChatColor.GREEN + "!");
		FancyMessage fm = new FancyMessage();
        fm.text(">> ");
        fm.color(ChatColor.GRAY);
        fm.then("Click Here");
        fm.color(ChatColor.YELLOW);
        fm.command("/party accept");
        fm.then(" to accept the party invitation!");
        fm.color(ChatColor.GREEN);
        fm.send(p);
        fm = new FancyMessage();
        fm.text(">> ");
        fm.color(ChatColor.GRAY);
        fm.then("To decline the invitation, ");
        fm.color(ChatColor.RED);
        fm.then("Click Here");
        fm.color(ChatColor.YELLOW);
        fm.command("/party decline");
        fm.then(".");
        fm.color(ChatColor.RED);
        fm.send(p);
        sendMessage(ChatColor.BLUE+p.getName()+" has been invited ", this.uuids);
		//p.sendMessage("to accept the invitation, type "+ChatColor.GREEN+"/party accept");
		//p.sendMessage("to decline the invitation, type "+ChatColor.RED+"/party decline");
	}
	public void destroy(){
		uuids.clear();
		uuids=null;
		leader = null;
        leadersUUID = null;
		PartyManager.parties.remove(this);
		 board = null;
		//PartyManager.as
	}
	public boolean isLootShare(){
		return lootshare;
	}
	
	public double getExpMultiplier() {
        int size = this.uuids.size();
        if (size < 0)
            size = 0;
        switch (size) {
            case 0:
            case 1:
                return 0.00;
            case 2:
                return 0.10;
            case 3:
                return 0.15;
            case 4:
                return 0.20;
            default:
            case 5:
                return 0.25;
        }
    }
	
	public void sendMessage(String s,ArrayList<UUID> pl){
		if(pl==null)
			return;
		for(UUID p:pl){
			if(p!=null){
				Player pr=Bukkit.getPlayer(p);
				pr.sendMessage(s);
				pr.playSound(pr.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
			}
		}
	}
	public void sendMessage(String s) {
        String fMsg = PartyManager.PREFIX_SYSTEM + s;
        for (Player p : getPlayers()) {
            p.sendMessage(fMsg);
            VamSound.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        }
    }
	public void sendMessage(Player sender, String s) {
        String msg = PartyManager.PREFIX + sender.getName() + ChatColor.WHITE + ": " + s;
        String msgFiltered = null;
        for (Player p : getPlayers()) {
            if (p == null || !p.isOnline())
                continue;
            try {
                if (PartyManager.plugin.getPD(p) != null && PartyManager.plugin.getPD(p).getOption(Option.CHAT_FILTER)) {
                    if (msgFiltered == null)
                        msgFiltered = PartyManager.PREFIX + sender.getName() + ChatColor.WHITE + ": " + ChatFilter.getFiltered(s);
                    p.sendMessage(msgFiltered);
                } else {
                    p.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (String m : ChatManager.monitors) {
            Player monitor = PartyManager.plugin.getServer().getPlayerExact(m);
            if (monitor != null && monitor.isOnline()) {
                PlayerData mpd = PartyManager.plugin.getPD(monitor);
                if (mpd != null && mpd.party != this) {
                    monitor.sendMessage(ChatColor.DARK_GRAY + "[MONITOR]");
                    monitor.sendMessage(ChatColor.GRAY + "ID " + id + " " + msg);
                }
            }
        }
    }

/*	 public void sendMessage(String s) {
	        String fMsg = ChatColor.DARK_GREEN + s;
	        for (Player p : this.uuids) {
	            p.sendMessage(fMsg);
	            VamSound.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
	        }
	    }*/
	public ArrayList<Player> getPlayers() {
        if (System.currentTimeMillis() - lastPlayerUpdate > 5000) {
            lastPlayerUpdate = System.currentTimeMillis();
            cachedPlayers.clear();
            ArrayList<UUID> toRemove = new ArrayList<UUID>();
            for (UUID uuid : uuids) {
                Player p = PartyManager.plugin.getServer().getPlayer(uuid);
                if (p != null && p.isOnline()) {
                    cachedPlayers.add(p);
                } else {
                    toRemove.add(uuid);
                }
            }
            uuids.removeAll(toRemove);
            return cachedPlayers;
        } else {
            for (int k = 0; k < cachedPlayers.size(); k++) {
                Player p = cachedPlayers.get(k);
                if (p == null || !p.isOnline()) {
                    cachedPlayers.remove(k);
                    k--;
                    if (k < 0)
                        k = 0;
                }
            }
            return cachedPlayers;
        }
    }
	 public boolean isLeader(Player p) {
	        return p.equals(leader) && p.getUniqueId().equals(leadersUUID);
	    }
	 
	 public void updatePlayer(Player p) {
	        if (board == null)
	            return;
	       // final Score score = board.getObjective("hpdisplay").getScore(name);
	       // score.setScore(hp);

	     /*   if (PartyManager.inParty(p)) {
	            final Score score_side = board.getObjective("partyhpside").getScore(p);
	            score_side.setScore((int) Pluginc.getInstance().getPD(p).hp);
	        }*/

	        //Team team = board.registerNewTeam("test");
	        //team.setPrefix(ChatColor.RED+"s");
	    }
	 
	public void toggleLootShare(){
		lootshare=!lootshare;
		if(lootshare){
		sendMessage(ChatColor.BLUE+"Lootshare has been turned "+ChatColor.GREEN+"ON"+ChatColor.DARK_BLUE+" by the leader", uuids);
		sendMessage(ChatColor.RED+"This means that other party members can pickup your loot", uuids);
		}
		else{
			sendMessage(ChatColor.BLUE+"Lootshare has been turned "+ChatColor.RED+"OFF"+ChatColor.DARK_BLUE+" by the leader", uuids);
		}
	}
}

