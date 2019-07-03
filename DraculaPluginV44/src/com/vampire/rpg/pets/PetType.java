package com.vampire.rpg.pets;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.utils.VamEntities;
import com.vampire.rpg.utils.entities.CustomMushroomCow;
import com.vampire.rpg.utils.entities.CustomZombie;

public enum PetType {
    BABY_MOOSHROOM("Baby Mooshroom", new ItemStack(Material.RED_MUSHROOM), (p, loc) -> {
        LivingEntity le = VamEntities.createLivingEntity(CustomMushroomCow.class, loc);
        ((Ageable) le).setAgeLock(true);
        ((Ageable) le).setBaby();
        return le;
    }),
    LIL_ME("Lil' Me", null, (p, loc) -> {
        LivingEntity le = VamEntities.createLivingEntity(CustomZombie.class, p.getLocation());
        le.setCustomName(ChatColor.YELLOW + "Lil' " + p.getName());
        le.setCustomNameVisible(true);
        ((Zombie) le).setBaby(true);
        ((Zombie) le).setVillagerProfession(Profession.HUSK);

        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(ItemAPI.getPlayerSkull(p.getName()));
        ee.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        ee.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ee.setBoots(new ItemStack(Material.IRON_BOOTS));
        return le;
    }),
    BABY_MISAKA("Baby Misaka", ItemAPI.getPlayerSkull("Misaka"), (p, loc) -> {
        LivingEntity le = VamEntities.createLivingEntity(CustomZombie.class, p.getLocation());
        le.setCustomName(ChatColor.YELLOW + "Baby Misaka");
        le.setCustomNameVisible(true);
        ((Zombie) le).setBaby(true);
        ((Zombie) le).setVillagerProfession(Profession.HUSK);

        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(ItemAPI.getPlayerSkull("Misaka"));
        ee.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        ee.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ee.setBoots(new ItemStack(Material.IRON_BOOTS));
        return le;
    })

    ;

    public String display;
    protected ItemStack item;
    private PetTypeSpawner spawner;

    PetType(String display, ItemStack item, PetTypeSpawner spawner) {
        this.display = display;
        this.item = item;
        this.spawner = spawner;
    }

    protected LivingEntity spawn(Player owner, Location loc) {
        return spawner.spawn(owner, loc);
    }

    @FunctionalInterface
    private static interface PetTypeSpawner {
        public LivingEntity spawn(Player p, Location loc);
    }
}
