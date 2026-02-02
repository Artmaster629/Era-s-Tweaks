package net.artmaster.era_tweaks.registry;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block.TotemBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, ModMain.MODID);

    public static final DeferredHolder<Block, Block> TOTEM_BLOCK =
            BLOCKS.register("totem_block",
                    () -> new TotemBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            );

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, ModMain.MODID);

    public static final DeferredHolder<Item, Item> TOTEM_BLOCK_ITEM =
            ITEMS.register("totem_block",
                    () -> new BlockItem(ModBlocks.TOTEM_BLOCK.get(), new Item.Properties())
            );


    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}
