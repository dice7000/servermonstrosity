package net.dice7000.servermonstrosity.common.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class SMRegistry {
    public static void register(IEventBus modEventBus) {
        SMItems.register(modEventBus);
        SMEntity.register(modEventBus);
    }
}
