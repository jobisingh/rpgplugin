package com.vampire.rpg.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class VamSound {
    public static void playSound(Player p, Sound s) {
        p.playSound(p.getLocation(), s, 10, 1);
    }

    public static void playSound(Player p, Sound s, float pitch) {
        p.playSound(p.getLocation(), s, 10, pitch);
    }

    public static void playSound(Player p, Sound s, float volume, float pitch) {
        p.playSound(p.getLocation(), s, volume, pitch);
    }
    public static void playSoundNearby(Player p, Sound s) {
        p.getWorld().playSound(p.getLocation(), s, 10, 1);
    }
}