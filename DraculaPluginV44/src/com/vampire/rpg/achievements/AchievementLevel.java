package com.vampire.rpg.achievements;

import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;

public class AchievementLevel extends Achievement {

	public AchievementLevel(int levelValue) {
		super(levelValue);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void check(PlayerData pd, Player p) {
		if(pd.level>=value){
			
		}

	}

}
