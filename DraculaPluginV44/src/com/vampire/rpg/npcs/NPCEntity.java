package com.vampire.rpg.npcs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.crafting.CraftingVillager;
import com.vampire.rpg.npcs.generics.GenericNPC;
import com.vampire.rpg.quests.QuestVillager;
import com.vampire.rpg.shops.ShopVillager;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTags;
import com.vampire.rpg.utils.VamTicks;
import com.vampire.rpg.utils.entities.Leashable;

public abstract class NPCEntity {

    public transient int id;

    public String name;
    private Location loc;
    public NPCType type;

    private transient int hashCode;
    private transient boolean foundHash = false;

    public transient LivingEntity le = null;
    public transient ArmorStand as = null;

    public Location getClonedLoc() {
        return loc.clone();
    }

    public NPCEntity(int id, String name, NPCType type, Location loc) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.loc = loc;
    }

    public NPCEntity(int id, String name, NPCType type, double x, double y, double z, String world) {
        this.id = id;
        this.name = name;
        this.type = type;
        World w = NPCManager.plugin.getServer().getWorld(world);
        if (w == null) {
            NPCManager.plugin.getServer().createWorld(new WorldCreator(world));
            System.out.println("WARNING: NON-EXISTING WORLD: " + world + " - NOW GENERATING");
        }
        this.loc = new Location(w, x, y, z);
    }

    public abstract ChatColor getColor();

    public void register() {
        NPCManager.register(this);
    }

    @Override
    public int hashCode() {
        if (foundHash)
            return hashCode;
        foundHash = true;
        hashCode = (name + "@id").hashCode();
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof NPCEntity))
            return false;
        NPCEntity o = (NPCEntity) other;
        return o.id == this.id;
    }

    public void despawn() {
        if (le != null) {
            NPCManager.npcs.remove(le.getUniqueId());
            le.remove();
        }
        le = null;
        if (as != null) {
            NPCManager.npcs.remove(as.getUniqueId());
            as.remove();
        }
        as = null;
    }

    public String getSub() {
        if (this instanceof QuestVillager) {
            return "Quest NPC";
        } else if (this instanceof ShopVillager) {
            return "Shop NPC";
        } /*else if (this instanceof DungeonVillager) {
            return "Dungeon NPC";
        }*/
    	if(this instanceof CraftingVillager){
    		return "Crafting NPC";
    	}
        return "NPC";
    }

    protected boolean spawn() {
        if (loc.getChunk().isLoaded()) {
            despawn();
            le = type.createEntity(loc.clone().add(0, 0.5, 0));
            boolean mobile = false;
            if (this instanceof GenericNPC) {
                ((Leashable) ((CraftLivingEntity) le).getHandle()).allowWalk(((GenericNPC) this).leash);
                mobile = true;
            }
            String displayName = getColor() + name;
            le.setCustomName(displayName);
            le.setCustomNameVisible(true);
            if (le instanceof Villager) {
            	if(this instanceof CraftingVillager)
            		((Villager)le).setProfession(Profession.BLACKSMITH);
                if (this instanceof QuestVillager) {
                    ((Villager) le).setProfession(Profession.PRIEST);
                }/* else if (this instanceof ShopVillager) {
                    ((Villager) le).setProfession(Profession.LIBRARIAN);
                } else if (this instanceof DungeonVillager) {
                    ((Villager) le).setProfession(Profession.BUTCHER);
                } else if (this instanceof HorseVillager) {
                    ((Villager) le).setProfession(Profession.BLACKSMITH);
                }*/
            }
            if (!mobile) {
                double mult = type.tagHeight;
                as = VamTags.makeStand(ChatColor.GRAY + getSub(), loc.clone().add(0, ((CraftLivingEntity) le).getHandle().getHeadHeight() * mult, 0), true);
                NPCManager.npcs.put(as.getUniqueId(), this);
            }
            NPCManager.npcs.put(le.getUniqueId(), this);
            VamScheduler.schedule(NPCManager.plugin, new Runnable() {
                public void run() {
                    if (loc.getChunk().isLoaded() && le != null && le.isValid()) {
                        le.teleport(loc);
                    }
                }
            }, VamTicks.seconds(1));
            return true;
        }
        return false;
    }

    public abstract void interact(Player p, PlayerData pd);

    public void say(Player p, String s) {
        p.sendMessage("");
        p.sendMessage(getColor() + name + ChatColor.WHITE + ": " + s);
    }

    @Override
    public String toString() {
        return "NPC: " + name;
    }
}
