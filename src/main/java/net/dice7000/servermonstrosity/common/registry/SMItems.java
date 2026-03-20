package net.dice7000.servermonstrosity.common.registry;

import net.dice7000.servermonstrosity.ServerMonstrosity;
import net.dice7000.servermonstrosity.common.block.ServerBlock;
import net.dice7000.servermonstrosity.common.item.ServerPartItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SMItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ServerMonstrosity.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ServerMonstrosity.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ServerMonstrosity.MOD_ID);


    public static final RegistryObject<Block> SERVER_BLOCK = BLOCKS.register("server_block", ServerBlock::new);

    public static final RegistryObject<Item> ELECTRO_CHIP_ITEM = ITEMS.register("electro_chip", ServerPartItem::new);
    public static final RegistryObject<Item> SERVER_CHIP_ITEM  = ITEMS.register("server_chip" , ServerPartItem::new);
    public static final RegistryObject<Item> SERVER_PART_ITEM  = ITEMS.register("server_part" , ServerPartItem::new);
    public static final RegistryObject<Item> RACK_SERVER_ITEM  = ITEMS.register("rack_server" , ServerPartItem::new);
    public static final RegistryObject<Item> SERVER_BLOCK_ITEM = ITEMS.register("server_block",
            () -> new BlockItem(SERVER_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<CreativeModeTab> SM_TAB = TABS.register("sm_tab", () -> CreativeModeTab.builder()
            .title(Component.literal("Server Monstrosity"))
            .icon(() -> SERVER_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems(((parameters, output) -> {
                output.accept(ELECTRO_CHIP_ITEM.get());
                output.accept( SERVER_CHIP_ITEM.get());
                output.accept( SERVER_PART_ITEM.get());
                output.accept( RACK_SERVER_ITEM.get());
                output.accept(SERVER_BLOCK_ITEM.get());
            }))
            .build());
    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TABS.register(modEventBus);
    }
}
