package com.vampire.rpg.utils.entities;

import net.minecraft.server.v1_13_R2.EntityCreature;
import net.minecraft.server.v1_13_R2.PathfinderGoal;
import net.minecraft.server.v1_13_R2.RandomPositionGenerator;
import net.minecraft.server.v1_13_R2.Vec3D;

public class PathfinderGoalMobWander extends PathfinderGoal {

    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private double e; //walkspeed
    private int f; //frequency
    private boolean g; //forceMoveOnce

    private int leash = 10;
    private boolean setLocation = false;
    private double startX, startY, startZ;

    public PathfinderGoalMobWander(EntityCreature paramEntityCreature, double paramDouble, int paramInt, int leash) {
        this.a = paramEntityCreature;
        this.e = paramDouble;
        this.f = paramInt;
        this.leash = leash;
        a(1);
    }

    public boolean a() {
        if(!setLocation) {
            this.startX = a.locX;
            this.startY = a.locY;
            this.startZ = a.locZ;
        }
        if (!this.g) {
            if (this.a.cg() >= 100) {
                return false;
            }
            if (this.a.getRandom().nextInt(this.f) != 0) {
                return false;
            }
        }
        Vec3D localVec3D = RandomPositionGenerator.a(this.a, 10, 10);
        if (localVec3D == null) {
            return false;
        }
        this.b = localVec3D.x;
        if (Math.abs(this.b - this.startX) > leash)
            this.b = this.startX;
        
        this.c = localVec3D.y;
        if (Math.abs(this.c - this.startY) > leash)
            this.c = this.startY;
        
        this.d = localVec3D.z;
        if (Math.abs(this.d - this.startZ) > leash)
            this.d = this.startZ;
        this.g = false;
        return true;
    }

    public boolean b() {
        return !this.a.getNavigation().p();
    }

    public void c() {
        this.a.getNavigation().a(this.b, this.c, this.d, this.e);
    }

    public void l() {
        this.g = true;
    }

    public void setTimeBetweenMovement(int paramInt) {
        this.f = paramInt;
    }

}
