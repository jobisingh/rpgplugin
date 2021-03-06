package com.vampire.rpg.holograms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.utils.ChunkWrapper;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;
public class HologramManager extends AbstractManager {

    public static ArrayList<Hologram> holograms = new ArrayList<Hologram>();
    public static HashMap<ChunkWrapper, ArrayList<Hologram>> hologramsPerChunk = new HashMap<ChunkWrapper, ArrayList<Hologram>>();

    public HologramManager(Pluginc plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        reload();
    }

    public static void reload() {
        for (Hologram h : holograms) {
            unregister(h);
        }
        holograms.clear();
        File dir = new File(plugin.getDataFolder(), "holograms");
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith(".txt")) {
                readHologram(f);
            }
        }
    }
    public static void a(){
    	if(holograms!=null)
    	 for (Hologram h : holograms) {
             unregister(h);
         }
         holograms.clear();
    }
    
    //Toc create a hologram, make a text file in the hologram folder. Then inside use this format per hologram. Hologram Format: -888.00 66.00 482.00 WorldName &4Hello World
    public static void readHologram(File f) {
        Scanner scan = null;
        try {
            scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String s = scan.nextLine().trim();
                if (s.startsWith("//") || s.length() == 0)
                    continue;
                try {
                    
                    String[] data = s.split(" ");
                    double x = Double.parseDouble(data[0]);
                    double y = Double.parseDouble(data[1]);
                    double z = Double.parseDouble(data[2]);
                    World w = plugin.getServer().getWorld(data[3]);
                    if (w == null) {
                        throw new Exception("Error: could not find holo world at line " + s + " in file " + f);
                    }
                    Location loc = new Location(w, x, y, z);
                    StringBuilder sb = new StringBuilder();
                    for (int k = 4; k < data.length; k++) {
                        sb.append(data[k]);
                        sb.append(' ');
                    }
                    Hologram holo = new Hologram(ChatColor.translateAlternateColorCodes('&', sb.toString().trim()), loc);
                    holo.spawn();
                    register(holo);
                    
                } catch (Exception e) {
                	
                    System.out.println("Error reading hologram on line " + s + " in file " + f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scan != null)
                scan.close();
        }
    }

    public static void register(Hologram holo) {
        ChunkWrapper cw = new ChunkWrapper(holo.loc.getChunk());
        if (!hologramsPerChunk.containsKey(cw)) {
            hologramsPerChunk.put(cw, new ArrayList<Hologram>());
        }
        hologramsPerChunk.get(cw).add(holo);
        holograms.add(holo);
    }

    public static void unregister(Hologram holo) {
        try {
            holo.despawn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ChunkWrapper cw = new ChunkWrapper(holo.loc.getChunk());
        if (hologramsPerChunk.containsKey(cw)) {
            hologramsPerChunk.get(cw).remove(holo);
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        handleChunk(event.getChunk());
    }
    
    public static void handleChunk(Chunk chunk) {
        final ChunkWrapper cw = new ChunkWrapper(chunk);
        if (!hologramsPerChunk.containsKey(cw))
            return;
        VamScheduler.schedule(plugin, new Runnable() {
            public void run() {
                ArrayList<Hologram> holos = hologramsPerChunk.get(cw);
                for (Hologram h : holos)
                    h.spawn();
            }
        }, VamTicks.seconds(2));
    }

}
