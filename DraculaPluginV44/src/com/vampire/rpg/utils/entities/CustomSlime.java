package com.vampire.rpg.utils.entities;

import net.minecraft.server.v1_13_R2.EntitySlime;
import net.minecraft.server.v1_13_R2.World;

public class CustomSlime extends EntitySlime {

    public CustomSlime(World world) {
        super(world);
        /*
         * try {
            Field gsa = PathfinderGoalSelector.class.getDeclaredField("b");
            gsa.setAccessible(true);
            gsa.set(this.goalSelector, Sets.newLinkedHashSet());
            gsa.set(this.targetSelector, Sets.newLinkedHashSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        //                this.goalSelector.a(1, new PathfinderGoalFloat(this));
        //                this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        //                this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
    }

    public boolean isTag = false;

    
    //THIS MIGHT NOT BE RIGHT
    @Override
    public void W() {
        if (isTag)
            return;
        super.W();
    }

}
