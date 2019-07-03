package com.vampire.rpg.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.PlayerData;


public abstract class MenuGeneralRunnable<T extends PlayerData> {

    @SuppressWarnings("unchecked")
    public void onClick(Player p, ItemStack item, int slot) {
        PlayerData pd = MenuManager.plugin.getPD(p);
        if (p != null && pd != null && item != null && item.getType() != Material.AIR)
            execute(p, (T) pd, item, slot);
    }

    public abstract void execute(final Player p, T pd, ItemStack item, int slot);

}
