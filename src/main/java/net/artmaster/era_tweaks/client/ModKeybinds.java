package net.artmaster.era_tweaks.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.client.screen.ClassManageScreen;
import net.artmaster.era_tweaks.client.screen.UpgradeManageScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final KeyMapping OPEN_UPGRADE_SCREEN =
            new KeyMapping(
                    "key.era_tweaks.open_upgrade_screen",                     // translation key
                    InputConstants.Type.KEYSYM,                  // тип ввода
                    GLFW.GLFW_KEY_G,                             // клавиша по умолчанию (G)
                    "key.category.era_tweaks"                         // категория в Controls
            );

    public static final KeyMapping OPEN_CLASS_SCREEN =
            new KeyMapping(
                    "key.era_tweaks.open_class_screen",                     // translation key
                    InputConstants.Type.KEYSYM,                  // тип ввода
                    GLFW.GLFW_KEY_H,                             // клавиша по умолчанию (G)
                    "key.category.era_tweaks"                         // категория в Controls
            );

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(OPEN_UPGRADE_SCREEN);
        event.register(OPEN_CLASS_SCREEN);
    }

    @EventBusSubscriber(modid = ModMain.MODID, value = Dist.CLIENT)
    public static class ClientTickHandler {
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            //consumeClick возвращает true столько раз, сколько нажатий
            while (OPEN_UPGRADE_SCREEN.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.setScreen(new UpgradeManageScreen());
                }
            }

            while (OPEN_CLASS_SCREEN.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.setScreen(new ClassManageScreen());
                }
            }
        }
    }
}
