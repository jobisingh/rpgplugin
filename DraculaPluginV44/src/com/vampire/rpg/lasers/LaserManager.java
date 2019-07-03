package com.vampire.rpg.lasers;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.utils.VamScheduler;

public class LaserManager extends AbstractManager {
	public static ArrayList<Laser> lasers= new ArrayList<Laser>();
	public LaserManager(Pluginc plugin) {
		super(plugin);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}
	public static void addLaser(Laser l,Player p){
		l.Spawn(p);
		VamScheduler.schedule(Pluginc.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				removeLaser(l);
				
			}
		}, l.getTicks());
	}
	public static void removeLaser(Laser l){
		if(lasers.contains(l))
		lasers.remove(l);
		l.Despawn();
	}
	public void reload(){
//		kill();
		
	}
	public static void kill(){
		if(lasers!=null){
			for(Laser l:lasers)
				removeLaser(l);
			lasers.clear();
		}
	}

}
