package com.vampire.rpg.utils.entities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.vampire.rpg.mobs.EntityRegistrar;
import com.vampire.rpg.utils.PlayerList.ReflectionUtil;

import net.minecraft.server.v1_13_R2.BiomeBase;
import net.minecraft.server.v1_13_R2.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.DataConverterRegistry;
import net.minecraft.server.v1_13_R2.DataConverterTypes;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityBlaze;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.EntityInsentient;
import net.minecraft.server.v1_13_R2.EntityLiving;
import net.minecraft.server.v1_13_R2.EntityPositionTypes;
import net.minecraft.server.v1_13_R2.EntitySmallFireball;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.EntityVillager;
import net.minecraft.server.v1_13_R2.EnumCreatureType;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.MathHelper;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.PathfinderGoal;
import net.minecraft.server.v1_13_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_13_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_13_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_13_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_13_R2.SharedConstants;
import net.minecraft.server.v1_13_R2.World;

public class CustomBlaze extends EntityBlaze implements Leashable, INMSType {

    public CustomBlaze(World world) {
        super(world);
        try {
            Field gsa = PathfinderGoalSelector.class.getDeclaredField("b");
            gsa.setAccessible(true);
            gsa.set(this.goalSelector, Sets.newLinkedHashSet());
            gsa.set(this.targetSelector, Sets.newLinkedHashSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));

        this.goalSelector.a(4, new PathfinderGoalBlazeFireball(this));
        
       // registerEntity("Monster", this,CustomBlaze.class, CustomBlaze::new, true);
    }

    @Override
    public void allowWalk(int leash) {
        this.goalSelector.a(5, new PathfinderGoalMobWander(this, 1.0D, 40, leash));
    }

    static class PathfinderGoalBlazeFireball extends PathfinderGoal {
        private final EntityBlaze a;
        private int b;
        private int c;

        public PathfinderGoalBlazeFireball(EntityBlaze paramEntityBlaze) {
            this.a = paramEntityBlaze;
            a(3);
        }

        public boolean a() {
            EntityLiving localEntityLiving = this.a.getGoalTarget();
            if ((localEntityLiving == null) || (!localEntityLiving.isAlive())) {
                return false;
            }
            return true;
        }

        public void c() {
            this.b = 0;
        }

        public void d() {
            this.a.a(false);
        }

        public void e() {
            this.c -= 1;

            EntityLiving localEntityLiving = this.a.getGoalTarget();

            double d1 = this.a.h(localEntityLiving);
            if (d1 < 4.0D) {
                if (this.c <= 0) {
                    this.c = 20;
                    this.a.B(localEntityLiving);
                }
                this.a.getControllerMove().a(localEntityLiving.locX, localEntityLiving.locY, localEntityLiving.locZ, 1.0D);
            } else if (d1 < 256.0D) {
                double d2 = localEntityLiving.locX - this.a.locX;
                double d3 = localEntityLiving.getBoundingBox().a() + localEntityLiving.length / 2.0F - (this.a.locY + this.a.length / 2.0F);
                double d4 = localEntityLiving.locZ - this.a.locZ;
                if (this.c <= 0) {
                    this.b += 1;
                    if (this.b == 1) {
                        this.c = 60;
                        this.a.a(true);
                    } else if (this.b <= 4) {
                        this.c = 6;
                    } else {
                        this.c = 100;
                        this.b = 0;
                        this.a.a(false);
                    }
                    if (this.b > 1) {
                        float f = MathHelper.c(MathHelper.sqrt(d1)) * 0.05F;

                        this.a.world.a(null, 1018, new BlockPosition((int) this.a.locX, (int) this.a.locY, (int) this.a.locZ), 0);
                        for (int i = 0; i < 1; i++) {
                            EntitySmallFireball localEntitySmallFireball = new EntitySmallFireball(this.a.world, this.a, d2 + this.a.getRandom().nextGaussian() * f, d3, d4 + this.a.getRandom().nextGaussian() * f);
                            localEntitySmallFireball.locY = (this.a.locY + this.a.length / 2.0F + 0.5D);
                            this.a.world.addEntity(localEntitySmallFireball);
                        }
                    }
                }
                this.a.getControllerLook().a(localEntityLiving, 10.0F, 10.0F);
            } else {
                this.a.getNavigation().q();
                this.a.getControllerMove().a(localEntityLiving.locX, localEntityLiving.locY, localEntityLiving.locZ, 1.0D);
            }
            super.e();
        }
    }
    
    public static EntityTypes<?> typesLoc;
    public static void register() {
        String customName = "defender";
        @SuppressWarnings("unchecked")
		Map<String, Type<?>> types = (Map<String, Type<?>>) DataConverterRegistry.a().getSchema(DataFixUtils.makeKey(1628)).findChoiceType(DataConverterTypes.n).types();
        types.put("minecraft:" + customName, types.get("minecraft:zombie"));
        EntityTypes.a<Entity> a = EntityTypes.a.a(EntityBlaze.class, CustomBlaze::new);
        
        IRegistry.ENTITY_TYPE.a(new MinecraftKey(customName), a.a(customName));
    }
    
    @SuppressWarnings("unchecked") @Nullable
    public static <T extends Entity> void registerEntity(String name, INMSType type, Class<T> customClass,Function<? super World, ? extends Entity> spawnFunction,
            boolean overrideSpawn)
    {
        //Creating a minecraft key with the name
        MinecraftKey customEntityKey = MinecraftKey.a(name);
        Validate.notNull(customEntityKey, "Using an invalid name for registering a custom entity. Name: ", name);

        if(IRegistry.ENTITY_TYPE.c(customEntityKey))
        {
            Bukkit.getServer().getConsoleSender().sendMessage("An entity named " + name + " already exists.");
            return;
        }
       
        //Getting the data converter type for the default entity and adding that to the custom mob.
        Map<Object, Type<?>> typeMap = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(DataFixUtils.makeKey(1628)).findChoiceType(DataConverterTypes.n).types();
        typeMap.put(customEntityKey.toString(), typeMap.get(type.getMinecraftKey().toString()));

        EntityTypes<T> customEntityNMSEntityType = (EntityTypes<T>) EntityTypes.a(name, EntityTypes.a.a(customClass, spawnFunction));
       
        //Is an insentient entity? Also copy the EntityPositionTypes value.
        if(customClass.isAssignableFrom(EntityInsentient.class))
        {
        	Map<EntityTypes<?>, Object> positionMap = null;
			try {
				positionMap = (Map<EntityTypes<?>, Object>) getPrivateStatic(EntityPositionTypes.class, "a");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            Object entityInformation = positionMap.get(type.getNMSEntityType());
            positionMap.put(customEntityNMSEntityType, entityInformation);
            List<Object> doot = new ArrayList<Object>();
            doot.stream();
        }
       BiomeBase biome;
      // List<BiomeMeta> b = biome.getMobs(null);
       //b.get(0).b;
        //Do you want to overidde spawn and is a creature?
        if(overrideSpawn && type instanceof INMSType)
        {
            //The get nms EnumCreatureType, and override and biome spawn list in which the default mob is contained and replacing it.
            EnumCreatureType nmsCreatureType =  type.getNMSCreatureType();
            StreamSupport.stream(IRegistry.BIOME.spliterator(), false)
                    .map(biomeBase -> ((BiomeBase) biomeBase).getMobs(nmsCreatureType))
                    .filter(Objects::nonNull)
                    .flatMap(biomeMeta -> ( (List<BiomeMeta>) biomeMeta).stream() )
                    .filter(meta -> ((BiomeMeta) meta).b == type.getNMSEntityType())
                    .forEach(meta -> ((BiomeMeta) meta).b = (EntityTypes<? extends EntityInsentient>) customEntityNMSEntityType);
        }
        //This is just a wrapper which contains the class and nms EntityTypes.
        return;
        //return new CustomEntityType<>(customClass, customEntityNMSEntityType);
    }
    
    private static Object getPrivateStatic(Class<?> clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }

	@Override
	public Class<? extends Entity> getNMSClass() {
		// TODO Auto-generated method stub
		return EntityBlaze.class;
	}

	@Override
	public EntityTypes getNMSEntityType() {
		// TODO Auto-generated method stub
		return this.P();
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return EnumCreatureType.MONSTER.toString();
	}
	
    public EnumCreatureType getNMSCreatureType()
    {
        return EnumCreatureType.MONSTER;
    }
    
}
