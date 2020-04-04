package com.vampire.rpg.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.classes.ClassType;
import com.vampire.rpg.utils.VamMath;

public enum EquipType {
    /* Melee */
    SWORD(new Material[] { // Knights | Melee fighter
            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.IRON_SWORD,
            Material.GOLDEN_SWORD,
            Material.DIAMOND_SWORD
    }, "Sword"),
    MACE(new Material[] { // Paladins | Melee tank
            Material.WOODEN_SHOVEL,
            Material.STONE_SHOVEL,
            Material.IRON_SHOVEL,
            Material.GOLDEN_SHOVEL,
            Material.DIAMOND_SHOVEL
    }, "Mace"),
    DAGGER(new Material[] { // Assassins | Melee burst
            Material.SHEARS,
            Material.SHEARS,
            Material.SHEARS,
            Material.SHEARS,
            Material.SHEARS
    }, "Dagger"),
    BATTLE_AXE(new Material[]{ // Barbarians | Melee fighter
    		Material.WOODEN_AXE,
    		Material.STONE_AXE,
    		Material.IRON_AXE,
    		Material.GOLDEN_AXE,
    		Material.DIAMOND_AXE
    },"Battle Axe"),
    /* Mid-range */
    ELIXIR(new Material[] { // Alchemists | Mid-range burst
            Material.DRAGON_BREATH,
            Material.DRAGON_BREATH,
            Material.DRAGON_BREATH,
            Material.DRAGON_BREATH,
            Material.DRAGON_BREATH
    }, "Elixir"),
    SCYTHE(new Material[] { // Reapers | Mid-range fighter
            Material.WOODEN_HOE,
            Material.STONE_HOE,
            Material.IRON_HOE,
            Material.GOLDEN_HOE,
            Material.DIAMOND_HOE
    }, "Scythe"),
    /* High-range */
    BOW(new Material[] { // Archers | High-range fighter
            Material.BOW,
            Material.BOW,
            Material.BOW,
            Material.BOW,
            Material.BOW
    }, "Bow"),
 /*   PICKAXE(new Material[]{
    		Material.WOOD_PICKAXE,
    		Material.STONE_PICKAXE,
    		Material.IRON_PICKAXE,
    		Material.GOLD_PICKAXE,
    		Material.DIAMOND_PICKAXE
    },"Pickaxe"),*/
    WAND(new Material[] { // Magicians | High-range burst
            Material.STICK,
            Material.STICK,
            Material.STICK,
            Material.STICK,
            Material.STICK
    }, "Wand"),
    /* Armors */
    HELMET(new Material[] { // Helmets
            Material.LEATHER_HELMET,
            Material.CHAINMAIL_HELMET,
            Material.IRON_HELMET,
            Material.GOLDEN_HELMET,
            Material.DIAMOND_HELMET
    }, "Helmet"),
    CHESTPLATE(new Material[] { // Chestplates
            Material.LEATHER_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE,
            Material.IRON_CHESTPLATE,
            Material.GOLDEN_CHESTPLATE,
            Material.DIAMOND_CHESTPLATE
    }, "Chestplate"),
    LEGGINGS(new Material[] { // Leggings
            Material.LEATHER_LEGGINGS,
            Material.CHAINMAIL_LEGGINGS,
            Material.IRON_LEGGINGS,
            Material.GOLDEN_LEGGINGS,
            Material.DIAMOND_LEGGINGS
    }, "Leggings"),
    BOOTS(new Material[] { // Boots
            Material.LEATHER_BOOTS,
            Material.CHAINMAIL_BOOTS,
            Material.IRON_BOOTS,
            Material.GOLDEN_BOOTS,
            Material.DIAMOND_BOOTS
    }, "Boots")

    ;

    private Material[] tierMaterials;
    public String equipName;
    private static EquipType[] armors = null;

    public Material getMaterial(int level) {
        return tierMaterials[ItemBalance.getTierZeroIndex(level)];
    }

    public static boolean isWeapon(ItemStack item) {
        return SWORD.isType(item) ||BATTLE_AXE.isType(item)|| MACE.isType(item) || DAGGER.isType(item) || ELIXIR.isType(item) || SCYTHE.isType(item) || BOW.isType(item)||  WAND.isType(item);
    }

    public static boolean isArmor(ItemStack item) {
        return HELMET.isType(item) || CHESTPLATE.isType(item) || LEGGINGS.isType(item) || BOOTS.isType(item);
    }

    public static boolean isWeapon(EquipType et) {
        return et == SWORD || et == MACE || et == DAGGER || et == ELIXIR || et == SCYTHE || et == BOW || et == WAND || et==BATTLE_AXE;
    }

    public static boolean isArmor(EquipType et) {
        return et == HELMET || et == CHESTPLATE || et == LEGGINGS || et == BOOTS;
    }

    public static EquipType random() {
        return EquipType.values()[(int) (Math.random() * EquipType.values().length)];
    }

    public static EquipType randomArmor() {
        if (armors == null)
            armors = new EquipType[] { HELMET, CHESTPLATE, LEGGINGS, BOOTS };
        return VamMath.randObject(armors);
    }
    public boolean isType(ItemStack item) {
        if (item == null || item.getType() == null)
            return false;
        Material m = item.getType();
        for (Material m2 : tierMaterials)
            if (m == m2)
                return true;
        return false;
    }

    EquipType(Material[] tierMaterials, String equipName) {
        this.tierMaterials = tierMaterials;
        this.equipName = equipName;
    }

    public static EquipType getWeaponForClass(ClassType classType) {
        switch (classType) {
     //       case ALCHEMIST:
    //            return ELIXIR;
            case ARCHER:
                return BOW;
            case ASSASSIN:
                return DAGGER;
            case BARBARIAN:
            	return BATTLE_AXE;
            case CRUSADER:
                return SWORD;
            case PALADIN:
                return MACE;
            case REAPER:
                return SCYTHE;
            case WIZARD:
                return WAND;              
            default:
                return SWORD;
        }
    }
}
