package net.artmaster.era_tweaks.network;

import net.artmaster.era_tweaks.ModMain;

import net.artmaster.era_tweaks.api.upgrades.MyAttachments;
import net.artmaster.era_tweaks.api.upgrades.PlayerSkillsData;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Objects;

@Mod("era_tweaks")
@EventBusSubscriber(modid = ModMain.MODID)
public class Network {


    public static PayloadRegistrar registrarClient;
    public static PayloadRegistrar registrarServer;

    // Регистрация пакетов
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        registrarClient = event.registrar("era_tweaks");
        registrarServer = event.registrar("era_tweaks");



        // -------------------- Клиентские пакеты (сервер -> клиент) --------------------


        registrarServer.playToClient(
                OpenGuiPacket.TYPE,
                OpenGuiPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> ClientPacketHandler.handleOpenGui(packet))
        );

        registrarServer.playToClient(
                RunCommandPacket.TYPE,
                RunCommandPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> ClientPacketHandler.handleRunCommand(packet))
        );




        // -------------------- Серверные пакеты (клиент -> сервер) --------------------
        registrarClient.playToServer(
                ButtonClickPacket.TYPE,
                ButtonClickPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    sendCommand(player, packet.command());
                })
        );

        registrarClient.playToClient(
                SyncPlayerSkillsPacket.TYPE,
                SyncPlayerSkillsPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    var player = Minecraft.getInstance().player;
                    if (player == null) return;



                    var data = player.getData(MyAttachments.PLAYER_SKILLS);
                    PlayerSkillsData.load(data, packet.data());
                })
        );

    }






    // Отправка GUI (сервер -> клиент)
    public static void sendOpenGui(ServerPlayer player, String resourceId) {
        player.connection.send(new OpenGuiPacket(resourceId));
    }



    // Отправка команды (сервер -> клиент)
    public static void sendCommand(ServerPlayer player, String command) {
        player.server.getCommands().performPrefixedCommand(
                player.createCommandSourceStack(),
                command
        );
    }

    public static void syncSkills(ServerPlayer player) {
        PlayerSkillsData data = player.getData(MyAttachments.PLAYER_SKILLS);
        CompoundTag tag = PlayerSkillsData.save(data);

        PacketDistributor.sendToPlayer(player, new SyncPlayerSkillsPacket(tag));
    }
}
