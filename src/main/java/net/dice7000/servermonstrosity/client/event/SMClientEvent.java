package net.dice7000.servermonstrosity.client.event;

import net.dice7000.servermonstrosity.ServerMonstrosity;
import net.dice7000.servermonstrosity.client.model.ServerMonolithModel;
import net.dice7000.servermonstrosity.client.model.ServerMonstrosityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SMClientEvent {
    @Mod.EventBusSubscriber(modid = ServerMonstrosity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            //event.registerLayerDefinition(ServerMonstrosityModel.LAYER_LOCATION, ServerMonstrosityModel::createBodyLayer);
            event.registerLayerDefinition(ServerMonolithModel.LAYER_LOCATION, ServerMonolithModel::createBodyLayer);
        }
    }
}
