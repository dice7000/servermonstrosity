package net.dice7000.servermonstrosity;

import com.mojang.logging.LogUtils;
import net.dice7000.servermonstrosity.client.renderer.SMonoRenderer;
import net.dice7000.servermonstrosity.client.renderer.SMonsRenderer;
import net.dice7000.servermonstrosity.common.registry.SMEntity;
import net.dice7000.servermonstrosity.common.registry.SMRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ServerMonstrosity.MOD_ID)
public class ServerMonstrosity {
    public static final String MOD_ID = "servermonstrosity";
    private static final Logger LOGGER = LogUtils.getLogger();
    public ServerMonstrosity(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        SMRegistry.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        //context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    public static ResourceLocation SMLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
    @SubscribeEvent public void onServerStarting(ServerStartingEvent event) {
    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent public static void onClientSetup(FMLClientSetupEvent event) {
            //EntityRenderers.register(SMEntity.SERVER_MONSTROSITY.get(), SMonsRenderer::new);
            EntityRenderers.register(SMEntity.SERVER_MONOLITH.get(), SMonoRenderer::new);
        }
    }
}
