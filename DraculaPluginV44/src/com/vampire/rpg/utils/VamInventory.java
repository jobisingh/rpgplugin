package com.vampire.rpg.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VamInventory {
	public static int getItemLoc(int row,int columm){
		int h=(row-1)*9;
		int g= h+columm-1;
		return g;
	}
	public static boolean hasInventorySpace(Player p, ItemStack item) {
        int free = 0;
        for (int k = 0; k < 36; k++) {
            ItemStack i = p.getInventory().getItem(k);
            if (i == null) {
                free += item.getMaxStackSize();
            } else if (i.isSimilar(item)) {
                free += item.getMaxStackSize() - i.getAmount();
            }
        }
        return free >= item.getAmount();
    }

    public static boolean hasEmptySpaces(Player p, int count) {
        int empty = 0;
        for (int k = 0; k < 36; k++) {
            if (p.getInventory().getItem(k) == null)
                empty++;
        }
        // code below is WRONG
        //        for (ItemStack i : p.getInventory().getContents()) {
        //            if (i == null) {
        //                empty++;
        //            }
        //        }
        return empty >= count;
    }
}
