package net.artmaster.era_tweaks.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.client.screen.ClassManageScreen;
import net.artmaster.era_tweaks.client.screen.UpgradeManageScreen;
import net.artmaster.era_tweaks.network.Network;
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
                    GLFW.GLFW_KEY_G,                             // клавиша по умолчанию
                    "key.category.era_tweaks"                         // категория в Controls
            );

    public static final KeyMapping OPEN_CLASS_SCREEN =
            new KeyMapping(
                    "key.era_tweaks.open_class_screen",                     // translation key
                    InputConstants.Type.KEYSYM,                  // тип ввода
                    GLFW.GLFW_KEY_H,                             // клавиша по умолчанию
                    "key.category.era_tweaks"                         // категория в Controls
            );

    public static final KeyMapping PASSIVE_SKILL_1 =
            new KeyMapping(
                    "key.era_tweaks.passive_skill_1",                     // translation key
                    InputConstants.Type.KEYSYM,                  // тип ввода
                    GLFW.GLFW_KEY_KP_1,                             // клавиша по умолчанию
                    "key.category.era_tweaks"                         // категория в Controls
            );
    public static final KeyMapping PASSIVE_SKILL_2 =
            new KeyMapping(
                    "key.era_tweaks.passive_skill_2",                     // translation key
                    InputConstants.Type.KEYSYM,                  // тип ввода
                    GLFW.GLFW_KEY_KP_2,                             // клавиша по умолчанию
                    "key.category.era_tweaks"                         // категория в Controls
            );
    public static final KeyMapping PASSIVE_SKILL_3 =
            new KeyMapping(
                    "key.era_tweaks.passive_skill_3",                     // translation key
                    InputConstants.Type.KEYSYM,                  // тип ввода
                    GLFW.GLFW_KEY_KP_3,                             // клавиша по умолчанию
                    "key.category.era_tweaks"                         // категория в Controls
            );

    public static final KeyMapping PASSIVE_SKILL_4 =
            new KeyMapping(
                    "key.era_tweaks.passive_skill_4",                     // translation key
                    InputConstants.Type.KEYSYM,                  // тип ввода
                    GLFW.GLFW_KEY_KP_4,                             // клавиша по умолчанию
                    "key.category.era_tweaks"                         // категория в Controls
            );





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

            while (PASSIVE_SKILL_1.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    Network.toServerAction("active1", 10);
                }
            }

            while (PASSIVE_SKILL_2.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    Network.toServerAction("active2", 10);
                }
            }

            while (PASSIVE_SKILL_3.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    Network.toServerAction("active3", 10);
                }
            }

            while (PASSIVE_SKILL_4.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    Network.toServerAction("active4", 10);
                }
            }
        }
    }

    @EventBusSubscriber(modid = ModMain.MODID, value = Dist.CLIENT)
    public static class ClientKeybinds {
        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(OPEN_UPGRADE_SCREEN);
            event.register(OPEN_CLASS_SCREEN);
            event.register(PASSIVE_SKILL_1);
            event.register(PASSIVE_SKILL_2);
            event.register(PASSIVE_SKILL_3);
            event.register(PASSIVE_SKILL_4);

        }
    }
}
