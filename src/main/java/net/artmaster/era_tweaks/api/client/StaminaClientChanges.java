package net.artmaster.era_tweaks.api.client;


import com.alrex.parcool.api.Stamina;
import net.artmaster.era_tweaks.ModMain;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@Mod("era_tweaks")
@EventBusSubscriber(modid = ModMain.MODID, value = Dist.CLIENT)
public class StaminaClientChanges {


    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        if (Stamina.get(player).isExhausted()) {
            KeyMapping jump = mc.options.keyJump;
            while (jump.consumeClick()) {
                player.input.jumping = false;
                player.setJumping(false);
            }

        }
    }
}

