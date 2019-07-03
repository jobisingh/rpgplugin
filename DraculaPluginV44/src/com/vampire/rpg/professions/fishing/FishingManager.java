package com.vampire.rpg.professions.fishing;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;

public class FishingManager extends AbstractManager implements Listener{

	public FishingManager(Pluginc plugin) {
		super(plugin);		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	@EventHandler
	public void onFish(PlayerFishEvent e){
		if(e.getState()==State.CAUGHT_ENTITY)
			return;
	//	e.setExpToDrop(0);
	//	Player p=e.getPlayer();
	//	PlayerData pd=Pluginc.getPD(p);
	}

}
