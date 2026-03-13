package net.dice7000.servermonstrosity.common.registry;

import net.dice7000.servermonstrosity.ServerMonstrosity;
import net.dice7000.servermonstrosity.common.entity.ServerMonolithEntity;
import net.dice7000.servermonstrosity.common.entity.ServerMonstrosityEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SMEntity {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ServerMonstrosity.MOD_ID);

    /*
    public static final RegistryObject<EntityType<ServerMonstrosityEntity>> SERVER_MONSTROSITY = ENTITIES.register
            ("server_monstrosity", () -> EntityType.Builder
                    .of(ServerMonstrosityEntity::new, net.minecraft.world.entity.MobCategory.MONSTER)
                    .sized(3.0f, 5.75f)
                    .build("server_monstrosity"));

     */

    public static final RegistryObject<EntityType<ServerMonolithEntity>> SERVER_MONOLITH = ENTITIES.register
            ("server_monolith", () -> EntityType.Builder
                    .of(ServerMonolithEntity::new, net.minecraft.world.entity.MobCategory.MONSTER)
                    .sized(1.0F, 3.0F)
                    .build("server_monolith"));


    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}
