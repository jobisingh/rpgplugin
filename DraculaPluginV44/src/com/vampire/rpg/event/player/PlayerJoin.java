package com.vampire.rpg.event.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.sql.Timestamp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import com.vampire.rpg.ActionBarAPI;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.Rank;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

public class PlayerJoin implements Listener {
	//Statement statemnet= Pluginc.instance
	public Boolean noSQL= false;
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent ev){
		Player p=(Player)ev.getPlayer();
		Inventory inv= Bukkit.createInventory(p, 54, "Bank");
		VamScheduler.schedule(Pluginc.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Pluginc.getInstance().addPD(p);
				p.setMaxHealth(20);
				p.setHealth(20);
				p.setFoodLevel(20);
			//	Pluginc.getInstance().loadData(p);
				if(noSQL){
				Pluginc.getInstance().getPD(p).bank= inv;
				Pluginc.getInstance().getPD(p).firstplayed= new Timestamp(System.currentTimeMillis());
				}
			}
		});
		//Pluginc.getInstance().addPD(p);
	//	if(Pluginc.getInstance().getConfig().contains("backpacks."+p.getUniqueId())){
	//		Pluginc.getInstance().loadContents(Pluginc.getInstance().getConfig().getConfigurationSection("backpacks."+p.getUniqueId()), inv);
	//	}
		//sp.sendMessage("KYSSSSSSSS");
	//	p.setMaxHealth(20);
	//	p.setHealth(20);
	//	p.setFoodLevel(20);
	//	Pluginc.getInstance().loadData(p);
	/*	if(noSQL){
		Pluginc.getInstance().getPD(p).bank= inv;
		Pluginc.getInstance().getPD(p).firstplayed= new Timestamp(System.currentTimeMillis());
		}*/
		//Pluginc.getInstance().addPD(p);
		//p.setMaxHealth(20);
		//Pluginc.getInstance().loadData(p);
	//	PlayerStats.caculateStats(p);
	//	Song song= NBSDecoder.parse(new File(Pluginc.getInstance().getDataFolder(), "Pokemon.nbs"));
	//	SongPlayer sp = new RadioSongPlayer(song);
	//	sp.setAutoDestroy(false);
	//	sp.addPlayer(ev.getPlayer());
	//	sp.setPlaying(true);
	//	sp.setFadeType(FadeType.FADE_LINEAR);
	//	RegionManager.checkRegion(p);
		//ResultSet result = statement.executeQuery("SELECT * FROM PlayerData WHERE BALANCE = 0;");
		//List<String> bankruptPlayers = new ArrayList<String>();
		//while (result.next()) {
		//    String name = result.getString("PLAYERNAME");
		//    bankruptPlayers.add(name);
		//}
		/*try {
			p.sendMessage(org.bukkit.ChatColor.GREEN+"Your Country is:"+getCountry(p.getAddress()));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public static String getCountry(InetSocketAddress ip) throws Exception {
        URL url = new URL("http://ip-api.com/json/" + ip.getHostName());
        BufferedReader stream = new BufferedReader(new InputStreamReader(
                url.openStream()));
        StringBuilder entirePage = new StringBuilder();
        String inputLine;
        while ((inputLine = stream.readLine()) != null)
            entirePage.append(inputLine);
        stream.close();
        if(!(entirePage.toString().contains("\"country\":\"")))
            return null;
        return entirePage.toString().split("\"country\":\"")[1].split("\",")[0];
    }
}
