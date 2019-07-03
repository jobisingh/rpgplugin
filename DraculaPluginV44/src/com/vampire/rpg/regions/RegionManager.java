package com.vampire.rpg.regions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.options.Option;
import com.vampire.rpg.regions.areas.TriggerArea;
import com.vampire.rpg.regions.areas.TriggerAreaAction;
import com.vampire.rpg.regions.areas.actions.TriggerAreaActionDelay;

public class RegionManager extends AbstractManager implements Listener{

    protected static final String GENERAL_NAME = "The Zentrelan Continent";

    protected static final RegionPoint MIN_POINT = new RegionPoint(-1000000, -1000000, -1000000);
    protected static final RegionPoint MAX_POINT = new RegionPoint(1000000, 1000000, 1000000);
    
    public static HashMap<Player,Long> lastRegionCheck = new HashMap<Player,Long>();
    public static HashMap<Player,Region> currentRegion= new  HashMap<Player,Region>();
    
    private static ArrayList<Region> regions;
    private static ArrayList<TriggerArea> areas;

    public RegionManager(Pluginc plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        regions = new ArrayList<Region>();
       // areas = new ArrayList<TriggerArea>();
        reload();
    }

    public static void reload() {
        regions.clear();
        for (World w : Pluginc.getInstance().getServer().getWorlds()) {
            Region r = new Region(GENERAL_NAME, 1, 2, w, Arrays.asList(MIN_POINT, MAX_POINT));
            Region hell = new Region("Hell", 99, 3, w, Arrays.asList(new RegionPoint(1629, 0, -937),new RegionPoint(1550, 200, -900)));
            Region a= new Region("Tutorial", 2, 1, w, Arrays.asList(new RegionPoint(-284 ,79, -405),new RegionPoint(180, 42, -300)));
            a.startTime = 3000;
            hell.startTime= 15000;
            hell.weather="downfall";
            regions.add(r);
            regions.add(hell);
            regions.add(a);
    //        Region r2= new Region("Building",2,1,w,Arrays.asList(new RegionPoint(-848,1,-535),new RegionPoint(-880, 234, -520)));
         //   regions.add(r2);
            //-284 79 -405
           // 180 42 -300
        }
        File dir = new File(plugin.getDataFolder(), "regions");
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith(".txt")) {
                readRegion(f);
            }
        }
        System.out.println("Loaded " + regions.size() + " regions.");
        
        dir = new File(plugin.getDataFolder(), "areas");
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith(".txt")) {
                readArea(f);
            }
        }
     //   System.out.println("Loaded " + areas.size() + " trigger areas.");
    }

    public static void readArea(File f) {
        try (Scanner scan = new Scanner(f)) {
            World w = plugin.getServer().getWorld(scan.nextLine().trim());
            if (w == null)
                throw new Exception("Invalid world");
            ArrayList<RegionPoint> points = new ArrayList<RegionPoint>();
            String s = null;
            while (scan.hasNextLine()) {
                s = scan.nextLine();
                if (s.startsWith("###"))
                    break;
                String[] data = s.trim().split(" ");
                if (data.length != 3)
                    break;
                points.add(new RegionPoint(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            }
            if (s == null) {
                throw new Exception("Error reading area " + f.getName());
            }
  			ArrayList<TriggerAreaAction> actions = new ArrayList<TriggerAreaAction>();
            long delay = 0;
            while (scan.hasNextLine()) {
                s = scan.nextLine();
                TriggerAreaAction taa = TriggerAreaAction.parse(s);
                if (taa == null)
                    continue;
                if (taa instanceof TriggerAreaActionDelay) {
                    delay = ((TriggerAreaActionDelay) taa).delay;
                    continue;
                }
                actions.add(taa);
            }
            String regionName = f.getName().substring(0, f.getName().indexOf(".txt"));
            if (regionName.contains("."))
                regionName = regionName.substring(0, regionName.lastIndexOf("."));
            TriggerArea ta = new TriggerArea(regionName, w, delay, points, actions);
            areas.add(ta);
        } catch (Exception e) {
            System.out.println("Error reading area " + f);
            e.printStackTrace();
        }
    }

    public static void readRegion(File f) {
        try (Scanner scan = new Scanner(f)) {
            ArrayList<RegionPoint> points = new ArrayList<RegionPoint>();
            String s = null;
            while (scan.hasNextLine()) {
                s = scan.nextLine();
                String[] data = s.trim().split(" ");
                if (data.length != 3)
                    break;
                points.add(new RegionPoint(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            }
            if (s == null) {
                throw new Exception("Error reading region " + f.getName());
            }
            int recLv = Integer.parseInt(s.trim()); // use s because s is read in from last thing
            int dangerLv = Integer.parseInt(scan.nextLine().trim());
            World w = plugin.getServer().getWorld(scan.nextLine().trim());
            String[] c = scan.nextLine().trim().split(" ");
            if (w == null)
                return;
            String regionName = f.getName().substring(0, f.getName().indexOf(".txt"));
            if (regionName.contains("."))
                regionName = regionName.substring(0, regionName.lastIndexOf("."));
            Region r = new Region(regionName, recLv, dangerLv, w, points);
            r.startTime = Long.parseLong(c[0]);
            r.endTime = Long.parseLong(c[1]);
            r.timeDiff = r.endTime - r.startTime;
            r.cycleLengthSeconds = Integer.parseInt(c[2]);
            regions.add(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlock().equals(event.getTo().getBlock()))
            return;
        initiateCheck(event.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
    	new BukkitRunnable() {
			
			@Override
			public void run() {
				initiateCheck(event.getPlayer());
		//		event.getPlayer().sendMessage("did it");
			}
		}.runTaskLater(Pluginc.getInstance(), 15);
      //  RScheduler.schedule(plugin, () -> {
     //       initiateCheck(event.getPlayer());
     //   }, RTicks.seconds(1));
    }

    private void initiateCheck(Player p) {
        if (p == null)
            return;
        final PlayerData pd = plugin.getPD(p);
        if (pd != null && pd.isValid()) {
            try {
                checkRegion(p);
         //       checkArea(p, pd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkArea(Player p, PlayerData pd) {
        if (!pd.loadedSQL)
            return;
        if (System.currentTimeMillis() - pd.lastAreaCheck > 200) {
          //  WorldBossManager.checkLocation(p);
            pd.lastAreaCheck = System.currentTimeMillis();
            TriggerArea ta = getArea(p);
            if (ta != null) {
                // only run triggerarea action once a second after the first time
                // longer cooldown must be implemented manually in TriggerArea implementations
                if (ta != pd.lastArea) { // last area has changed, force trigger immediately
                    pd.lastArea = ta;
                    pd.lastAreaTriggered = System.currentTimeMillis();
                    ta.runActions(p, pd);
                } else { // area hasn't changed (ta == last)
                    if (System.currentTimeMillis() - pd.lastAreaTriggered > 1000) { // has it been 1 sec? if so then trigger
                        pd.lastAreaTriggered = System.currentTimeMillis();
                        ta.runActions(p, pd);
                    }
                }
            }

        }
    }

    public static void checkRegion(Player p) {
    	PlayerData pd= Pluginc.getInstance().getPD(p);
    	if(pd==null)
    		return;
       if (!pd.loadedSQL)
           return;
        boolean changedRegion = false;
        if (System.currentTimeMillis() - pd.lastRegionCheck > 200) {
            pd.lastRegionCheck=System.currentTimeMillis();
            Region r = getRegion(p);
	            if (pd.region==null||pd.region!= r) {
	            	//currentRegion.put(p, r);
	                Region last = pd.region;
	               pd.region=r;
	                r.sendWelcome(p, last);
	                changedRegion = true;
	                pd.lastRegionCheck=System.currentTimeMillis();
	                p.resetPlayerTime();	              
	                p.setPlayerTime(r.startTime, false);
	                p.resetPlayerWeather();
	                if(pd.getOption(Option.WEATHER_REGIONS))
	                p.setPlayerWeather(WeatherType.valueOf(r.weather.toUpperCase()));
	            }
        	}
        }
        //long delay = System.currentTimeMillis() - lastRegionCheck.get(p) < 10000 ? 100 : 1000;
      //  if (changedRegion || System.currentTimeMillis() - pd.lastRegionTimeUpdate > delay) {
       //     updateTime(p, pd);
     //   }  

  //  public static void updateTime(Player p, PlayerDataRPG pd) {
  //      if (pd.region != null) {
  //          pd.lastRegionTimeUpdate = System.currentTimeMillis();
            //            PacketManager.registerTime(p, pd.region.getTime());
   //     }
  //  }

    public static TriggerArea getArea(Player p) {
        return getArea(p.getLocation());
    }

    public static TriggerArea getArea(Location loc) {
        TriggerArea curr = null;
        for (TriggerArea a : areas) {
            if (a.isWithin(loc)) {
                if (curr == null || a.size < curr.size)
                    curr = a;
            }
        }
        return curr;
    }

    public static Region getRegion(Player p) {
        return getRegion(p.getLocation());
    }

    public static Region getRegion(Location loc) {
        Region curr = null;
        for (Region r : regions) {
            if (r.isWithin(loc)) {
                if (curr == null || r.size < curr.size)
                    curr = r;
            }
        }
        if (curr == null) {
            // This should only happen if a world is loaded manually after regions have loaded
            Region r = new Region(GENERAL_NAME, 1, 2, loc.getWorld(), Arrays.asList(MIN_POINT, MAX_POINT));
            regions.add(r);
            return r;
        }
        return curr;
    }
}
