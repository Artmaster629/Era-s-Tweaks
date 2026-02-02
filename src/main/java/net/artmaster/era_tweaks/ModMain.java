package net.artmaster.era_tweaks;


import com.mojang.logging.LogUtils;

import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block_entity.TotemBlockEntityRenderer;
import net.artmaster.era_tweaks.registry.ModAttributes;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.config.*;
import net.artmaster.era_tweaks.registry.ModBlockEntities;
import net.artmaster.era_tweaks.registry.ModBlocks;
import net.artmaster.era_tweaks.utils.ServerScheduler;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ModMain.MODID)
public class ModMain {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "era_tweaks";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ModMain(IEventBus modEventBus) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModAttachments.register();
        BlockXpConfig.load();
        ItemXpConfig.load();
        HarvestXpConfig.load();
        DeniedConfig.load();
        DeniedByClassConfig.load();

        modEventBus.addListener(this::clientSetup);
    }




    @OnlyIn(Dist.CLIENT)
    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("registered client totembe");
            BlockEntityRenderers.register(
                    ModBlockEntities.TOTEM_BE.get(),
                    TotemBlockEntityRenderer::new
            );
            ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.TOTEM_BLOCK.get(),
                    RenderType.cutout()
            );
        });
    }



    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("Готов служить Мастерии!");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Готов служить Мастерии на сервере!");
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        ServerScheduler.tick();
    }
}
