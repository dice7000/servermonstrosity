package net.dice7000.servermonstrosity.common.event;

import net.dice7000.servermonstrosity.ServerMonstrosity;
import net.dice7000.servermonstrosity.common.entity.IHasBossBar;
import net.dice7000.servermonstrosity.common.entity.ServerMonolithEntity;
import net.dice7000.servermonstrosity.common.entity.ServerMonstrosityEntity;
import net.dice7000.servermonstrosity.common.registry.SMEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SMEvent {
    @Mod.EventBusSubscriber(modid = ServerMonstrosity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent public static void registerAttributes(EntityAttributeCreationEvent event) {
            //event.put(SMEntity.SERVER_MONSTROSITY.get(), ServerMonstrosityEntity.createAttributes().build());
            event.put(SMEntity.SERVER_MONOLITH.get(), ServerMonolithEntity.createAttributes().build());
        }
    }

    @Mod.EventBusSubscriber(modid = ServerMonstrosity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusEvents {
        @SubscribeEvent public static void onStartTracking(PlayerEvent.StartTracking event) {
            if (event.getTarget() instanceof IHasBossBar mob) {
                mob.getBossEvent().addPlayer((ServerPlayer) event.getEntity());
                mob.getBossEvent().setVisible(true);
            }
        }

        @SubscribeEvent public static void onStopTracking(PlayerEvent.StopTracking event) {
            if (event.getTarget() instanceof IHasBossBar mob) {
                mob.getBossEvent().removePlayer((ServerPlayer) event.getEntity());
            }
        }
    }
}
