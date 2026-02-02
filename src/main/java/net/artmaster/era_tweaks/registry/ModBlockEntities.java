package net.artmaster.era_tweaks.registry;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block_entity.TotemBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ModMain.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemBlockEntity>> TOTEM_BE =
            BLOCK_ENTITIES.register("totem_be",
                    () -> BlockEntityType.Builder
                            .of(TotemBlockEntity::new, ModBlocks.TOTEM_BLOCK.get())
                            .build(null)
            );

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
