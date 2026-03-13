package net.dice7000.servermonstrosity.common.registry;

import net.dice7000.servermonstrosity.ServerMonstrosity;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SMItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ServerMonstrosity.MOD_ID);
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties()));
    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
