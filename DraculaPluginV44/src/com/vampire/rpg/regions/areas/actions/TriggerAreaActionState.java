package com.vampire.rpg.regions.areas.actions;

import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.regions.areas.TriggerAreaAction;

public class TriggerAreaActionState extends TriggerAreaAction {

    private String state = "";

    public TriggerAreaActionState(String state) {
        this.state = state;
    }

    @Override
    public void act(Player p, PlayerData pd) {
     //   pd.addState(state);
    }

}
