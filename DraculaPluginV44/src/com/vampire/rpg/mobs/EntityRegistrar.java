package com.vampire.rpg.mobs;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.vampire.rpg.utils.PlayerList.ReflectionUtil;
import com.vampire.rpg.utils.entities.CustomBlaze;
import com.vampire.rpg.utils.entities.CustomCaveSpider;
import com.vampire.rpg.utils.entities.CustomChicken;
import com.vampire.rpg.utils.entities.CustomCow;
import com.vampire.rpg.utils.entities.CustomHorse;
import com.vampire.rpg.utils.entities.CustomIronGolem;
import com.vampire.rpg.utils.entities.CustomMagmaCube;
import com.vampire.rpg.utils.entities.CustomMushroomCow;
import com.vampire.rpg.utils.entities.CustomOcelot;
import com.vampire.rpg.utils.entities.CustomPig;
import com.vampire.rpg.utils.entities.CustomPigZombie;
import com.vampire.rpg.utils.entities.CustomPolarBear;
import com.vampire.rpg.utils.entities.CustomRabbit;
import com.vampire.rpg.utils.entities.CustomSheep;
import com.vampire.rpg.utils.entities.CustomSilverfish;
import com.vampire.rpg.utils.entities.CustomSkeleton;
import com.vampire.rpg.utils.entities.CustomSlime;
import com.vampire.rpg.utils.entities.CustomSpider;
import com.vampire.rpg.utils.entities.CustomVillager;
import com.vampire.rpg.utils.entities.CustomWitch;
import com.vampire.rpg.utils.entities.CustomWolf;
import com.vampire.rpg.utils.entities.CustomZombie;

import net.minecraft.server.v1_13_R2.BiomeBase;
import net.minecraft.server.v1_13_R2.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_13_R2.DataConverterRegistry;
import net.minecraft.server.v1_13_R2.DataConverterTypes;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityBlaze;
import net.minecraft.server.v1_13_R2.EntityCaveSpider;
import net.minecraft.server.v1_13_R2.EntityChicken;
import net.minecraft.server.v1_13_R2.EntityCow;
import net.minecraft.server.v1_13_R2.EntityHorse;
import net.minecraft.server.v1_13_R2.EntityInsentient;
import net.minecraft.server.v1_13_R2.EntityIronGolem;
import net.minecraft.server.v1_13_R2.EntityMagmaCube;
import net.minecraft.server.v1_13_R2.EntityMushroomCow;
import net.minecraft.server.v1_13_R2.EntityOcelot;
import net.minecraft.server.v1_13_R2.EntityPig;
import net.minecraft.server.v1_13_R2.EntityPigZombie;
import net.minecraft.server.v1_13_R2.EntityPolarBear;
import net.minecraft.server.v1_13_R2.EntityPositionTypes;
import net.minecraft.server.v1_13_R2.EntityRabbit;
import net.minecraft.server.v1_13_R2.EntitySheep;
import net.minecraft.server.v1_13_R2.EntitySilverfish;
import net.minecraft.server.v1_13_R2.EntitySkeleton;
import net.minecraft.server.v1_13_R2.EntitySlime;
import net.minecraft.server.v1_13_R2.EntitySpider;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.EntityVillager;
import net.minecraft.server.v1_13_R2.EntityWitch;
import net.minecraft.server.v1_13_R2.EntityWolf;
import net.minecraft.server.v1_13_R2.EntityZombie;
import net.minecraft.server.v1_13_R2.EnumCreatureType;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.SharedConstants;
import net.minecraft.server.v1_13_R2.World;

public enum EntityRegistrar {

    Villager("Villager", 120, EntityType.VILLAGER, EntityVillager.class, CustomVillager.class, CustomVillager::new),
    Skeleton("Skeleton", 51, EntityType.SKELETON, EntitySkeleton.class, CustomSkeleton.class, CustomSkeleton::new),
    Zombie("Zombie", 54, EntityType.ZOMBIE, EntityZombie.class, CustomZombie.class, CustomZombie::new),
    Slime("Slime", 55, EntityType.SLIME, EntitySlime.class, CustomSlime.class, CustomSlime::new),
    Chicken("Chicken", 93, EntityType.CHICKEN, EntityChicken.class, CustomChicken.class, CustomChicken::new),
    Cow("Cow", 92, EntityType.COW, EntityCow.class, CustomCow.class, CustomCow::new),
    Spider("Spider", 52, EntityType.SPIDER, EntitySpider.class, CustomSpider.class, CustomSpider::new),
    Blaze("Blaze", 61, EntityType.BLAZE, EntityBlaze.class, CustomBlaze.class, CustomBlaze::new),
    Iron_Golem("VillagerGolem", 99, EntityType.IRON_GOLEM, EntityIronGolem.class, CustomIronGolem.class, CustomIronGolem::new),
    Wolf("Wolf", 95, EntityType.WOLF, EntityWolf.class, CustomWolf.class, CustomWolf::new),
    Silverfish("Silverfish", 60, EntityType.SILVERFISH, EntitySilverfish.class, CustomSilverfish.class, CustomSilverfish::new),
    Pig_Zombie("PigZombie", 57, EntityType.PIG_ZOMBIE, EntityPigZombie.class, CustomPigZombie.class, CustomPigZombie::new),
    Magma_Cube("LavaSlime", 62, EntityType.MAGMA_CUBE, EntityMagmaCube.class, CustomMagmaCube.class, CustomMagmaCube::new),
    Cave_Spider("CaveSpider", 59, EntityType.CAVE_SPIDER, EntityCaveSpider.class, CustomCaveSpider.class, CustomCaveSpider::new),
    Pig("Pig", 90, EntityType.PIG, EntityPig.class, CustomPig.class, CustomPig::new),
    Sheep("Sheep", 91, EntityType.SHEEP, EntitySheep.class, CustomSheep.class, CustomSheep::new),
    MushroomCow("MushroomCow", 96, EntityType.MUSHROOM_COW, EntityMushroomCow.class, CustomMushroomCow.class, CustomMushroomCow::new),
    Rabbit("Rabbit", 101, EntityType.RABBIT, EntityRabbit.class, CustomRabbit.class, CustomRabbit::new),
    Witch("Witch", 66, EntityType.WITCH, EntityWitch.class, CustomWitch.class, CustomWitch::new),
    Horse("EntityHorse", 100, EntityType.HORSE, EntityHorse.class, CustomHorse.class, CustomHorse::new),
    PolarBear("PolarBear", 102, EntityType.POLAR_BEAR, EntityPolarBear.class, CustomPolarBear.class, CustomPolarBear::new),
    Ozelot("Ozelot", 98, EntityType.OCELOT, EntityOcelot.class, CustomOcelot.class, CustomOcelot::new)
    
    ;

    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;
    private Function<? super World, ? extends Entity> function;

    private EntityRegistrar(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass, Function<? super World, ? extends Entity> function) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<? extends EntityInsentient> getNMSClass() {
        return nmsClass;
    }

    public Class<? extends EntityInsentient> getCustomClass() {
        return customClass;
    }

    /**
     * Register our entities.
     */
    
    public static void register() {

    }
    /*
    @SuppressWarnings("unchecked") @Nullable
    public static <T extends Entity> void registerEntity(String name, Class<T> customClass, EntityType entityType, Function<World, T> spawnFunction,
            boolean overrideSpawn, EnumCreatureType creatureType)
    {
        //Creating a minecraft key with the name
        MinecraftKey customEntityKey = MinecraftKey.a(name);
        Validate.notNull(customEntityKey, "Using an invalid name for registering a custom entity. Name: ", name);

        if(IRegistry.ENTITY_TYPE.c(customEntityKey))
        {
            Bukkit.getLogger().info("An entity named " + name + " already exists.");
            return;
        }
       
        //Getting the data converter type for the default entity and adding that to the custom mob.
        Map<Object, Type<?>> typeMap = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(DataFixUtils.makeKey(1628)).findChoiceType(DataConverterTypes.n).types();
        typeMap.put(customEntityKey.toString(), typeMap.get(MinecraftKey.a(entityType.name()).toString()));

        EntityTypes<?> customEntityNMSEntityType = EntityTypes.a(name, EntityTypes.a.a(customClass, spawnFunction));
       
        //Is an insentient entity? Also copy the EntityPositionTypes value.
        if(customClass.isAssignableFrom(EntityInsentient.class))
        {
            Map<EntityTypes<?>, Object> positionMap = (Map<EntityTypes<?>, Object>) Validator.validateField(ReflectionUtil.getField(EntityPositionTypes.class, "a"), null);

            Object entityInformation = positionMap.get(entityType);
            positionMap.put(customEntityNMSEntityType, entityInformation);
        }
       
        //Do you want to overidde spawn and is a creature?
        if(overrideSpawn)
        {
            //The get nms EnumCreatureType, and override and biome spawn list in which the default mob is contained and replacing it.
            Field metaList = BiomeBase.class.getDeclaredField("u");
            metaList.setAccessible(true);
            for (BiomeBase base : BiomeBase.aG) {
                List<BiomeMeta> v = (List<BiomeMeta>) metaList.get(base);
                for (BiomeMeta meta : v) {
                        meta.b = (EntityTypes<? extends EntityInsentient>) typesLoc;
                        break;
                }
            }
        
        }
        //This is just a wrapper which contains the class and nms EntityTypes.
        return;
    }
    */
    public static EntityTypes<?> typesLoc;
    public static void registerEntities() {

        for (EntityRegistrar entity : values()) {
        	
        	
            String customName = entity.getName();
            @SuppressWarnings("unchecked")
        	Map<Object, Type<?>> types = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(15190).findChoiceType(DataConverterTypes.ENTITY).types();
            
            types.put("minecraft:" + customName.toLowerCase(), types.get("minecraft:" + entity.getName().toString().toLowerCase()));
            EntityTypes.a<Entity> a = EntityTypes.a.a(entity.getCustomClass(), (Function<? super World, ? extends Entity>) entity.function);
            
    		typesLoc = a.a(customName.toLowerCase());
    		IRegistry.ENTITY_TYPE.a(new MinecraftKey(customName.toLowerCase()), typesLoc);
    		//EntityTypes.a("zombie", a.a(entity.getCustomClass(), ::new));
        }
        //The get nms EnumCreatureType, and override and biome spawn list in which the default mob is contained and replacing it.
        Field metaList = null;
		try {
			metaList = BiomeBase.class.getDeclaredField("aZ");
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        metaList.setAccessible(true);
        for (BiomeBase base : BiomeBase.aG) {
            List<BiomeMeta> v = null;
			try {
				v = (List<BiomeMeta>) metaList.get(base);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            for (BiomeMeta meta : v) {
                    meta.b = (EntityTypes<? extends EntityInsentient>) typesLoc;
                    break;
            }
        }

 
    }

    /**
     * Unregister our entities to prevent memory leaks. Call on disable.
     */
    public static void unregisterEntities() {
        for (EntityRegistrar entity : values()) {
            // Remove our class references.
            try {
                ((Map<?, ?>) getPrivateStatic(EntityTypes.class, "d")).remove(entity.getCustomClass());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((Map<?, ?>) getPrivateStatic(EntityTypes.class, "f")).remove(entity.getCustomClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (EntityRegistrar entity : values())
            try {
                // Unregister each entity by writing the NMS back in place of the custom class.
                a(entity.getNMSClass(), entity.getName(), entity.getID());
            } catch (Exception e) {
                e.printStackTrace();
            }

        // Biomes#biomes was made private so use reflection to get it.
        BiomeBase[] biomes;
        try {
            biomes = (BiomeBase[]) getPrivateStatic(BiomeBase.class, "biomes");
        } catch (Exception exc) {
            // Unable to fetch.
            return;
        }
        for (BiomeBase biomeBase : biomes) {
            if (biomeBase == null)
                break;

            // The list fields changed names but update the meta regardless.
            for (String field : new String[] { "u", "v", "w", "x" })
                try {
                	/*
                    Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    List<BiomeMeta> mobList = (List<BiomeMeta>) list.get(biomeBase);

                    // Make sure the NMS class is written back over our custom class.
                    for (BiomeMeta meta : mobList)
                        for (EntityRegistrar entity : values())
                            if (entity.getCustomClass().equals(meta.b)) {
                            	
                            	
                                meta.b = entity.getNMSClass();
                            }
                    */

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * A convenience method.
     * @param clazz The class.
     * @param f The string representation of the private static field.
     * @return The object found
     * @throws Exception if unable to get the object.
     */
    private static Object getPrivateStatic(Class<?> clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }

    /*
     * Since 1.7.2 added a check in their entity registration, simply bypass it and write to the maps ourself.
     */
    @SuppressWarnings("unchecked")
    private static void a(Class<?> paramClass, String paramString, int paramInt) {
        try {
            ((Map<String, Class<?>>) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
            ((Map<Class<?>, String>) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
            ((Map<Integer, Class<?>>) getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(paramInt), paramClass);
            ((Map<Class<?>, Integer>) getPrivateStatic(EntityTypes.class, "f")).put(paramClass, Integer.valueOf(paramInt));
            ((Map<String, Integer>) getPrivateStatic(EntityTypes.class, "g")).put(paramString, Integer.valueOf(paramInt));
        } catch (Exception exc) {
            // Unable to register the new class.
        }
    }
}
