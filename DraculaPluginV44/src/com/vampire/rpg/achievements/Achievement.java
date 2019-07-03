package com.vampire.rpg.achievements;

import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;

public abstract class Achievement {
	public int value;
	public Achievement(int value1){
		this.value=value1;
	}
	public abstract void check(PlayerData pd,Player p);
	public void complete(PlayerData pd){
		//TODO add to completed achivements
	}
}
