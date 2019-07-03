package com.vampire.rpg.regions.areas.actions;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.regions.areas.TriggerAreaAction;
import com.vampire.rpg.utils.VamSound;

public class TriggerAreaActionPing extends TriggerAreaAction {

    @Override
    public void act(Player p, PlayerData pd) {
        VamSound.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }

}
