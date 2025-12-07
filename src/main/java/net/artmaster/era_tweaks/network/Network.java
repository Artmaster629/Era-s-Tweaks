package net.artmaster.era_tweaks.network;

import com.alrex.parcool.api.Attributes;
import com.alrex.parcool.api.Stamina;
import net.artmaster.era_tweaks.ModMain;

import net.artmaster.era_tweaks.api.SkillsManager;
import net.artmaster.era_tweaks.api.container.MyAttachments;
import net.artmaster.era_tweaks.api.container.PlayerClassData;
import net.artmaster.era_tweaks.api.container.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.client.ClientPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.UUID;

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

        registrarClient.playToClient(
                SyncPlayerSkillsPacket.TYPE,
                SyncPlayerSkillsPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    var player = Minecraft.getInstance().player;
                    if (player == null) return;



                    var data = player.getData(MyAttachments.PLAYER_SKILLS);
                    PlayerSAttrubitesData.load(data, packet.data());
                })
        );

        registrarClient.playToClient(
                SyncPlayerClassPacket.TYPE,
                SyncPlayerClassPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    var player = Minecraft.getInstance().player;
                    if (player == null) return;



                    var data = player.getData(MyAttachments.PLAYER_CLASS);
                    PlayerClassData.load(data, packet.data());
                })
        );

        registrarClient.playToClient(
                StaminaPacket.TYPE,
                StaminaPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    var player = Minecraft.getInstance().player;
                    if (player == null) return;


                    var stamina = Stamina.get(player);
                    stamina.consume(packet.stamina());
                })
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

        registrarClient.playToServer(
                SyncPlayerClassToServerPacket.TYPE,
                SyncPlayerClassToServerPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();

                    var data = player.getData(MyAttachments.PLAYER_CLASS);

                    boolean isIncluded = false;

                    for (String skill : data.getPlayerSkills()) {
                        if (skill.equals(packet.skill())) {
                            isIncluded = true;
                        }
                    }


                    if (!isIncluded) {
                        data.getPlayerSkills().add(packet.skill());
                        syncClasses(player);
                        SkillsManager.makeChangesToClass(data, player);
                    }







                })
        );

        registrarClient.playToServer(
                StaminaDataPacket.TYPE,
                StaminaDataPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    PlayerSAttrubitesData data = player.getData(MyAttachments.PLAYER_SKILLS);
                    if (data.getStaminaLevel() < 100) {
                        data.setStaminaProgress(data.getStaminaProgress()+packet.stamina());
                    }
                    if (data.getStaminaProgress() >= 100) {
                        data.setStaminaProgress(0);
                        data.setStaminaLevel(data.getStaminaLevel()+1);
                        int oldLevel = data.getStaminaLevel()-1;
                        player.displayClientMessage(Component.literal("Ваш уровень повысился с "+oldLevel+" на "+data.getStaminaLevel()), false);
                        AttributeInstance maxStamina = player.getAttribute(Attributes.MAX_STAMINA);
                        AttributeModifier modifier1 = new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                                0.02,
                                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        );

                        AttributeInstance staminaRecovery = player.getAttribute(Attributes.STAMINA_RECOVERY);
                        AttributeModifier modifier2 = new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                                0.02,
                                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        );
                        maxStamina.addPermanentModifier(modifier1);
                        staminaRecovery.addPermanentModifier(modifier2);
                    }
                    syncSkills(player);
                })
        );





    }







    // Отправка нажатия кнопки (клиент -> сервер)
    public static void sendNewSkill(String skill) {
        Minecraft.getInstance().getConnection().send(new SyncPlayerClassToServerPacket(skill));
    }

    // Отправка GUI (сервер -> клиент)
    public static void sendOpenGui(ServerPlayer player, String resourceId) {
        player.connection.send(new OpenGuiPacket(resourceId));
    }

    public static void sendCommand(String command) {
            Minecraft.getInstance().getConnection().send(new RunCommandPacket(command));
    }



    // Отправка команды (сервер -> клиент)
    public static void sendCommand(ServerPlayer player, String command) {
        player.server.getCommands().performPrefixedCommand(
                player.createCommandSourceStack(),
                command
        );
    }



    public static void syncSkills(ServerPlayer player) {
        PlayerSAttrubitesData data = player.getData(MyAttachments.PLAYER_SKILLS);
        CompoundTag tag = PlayerSAttrubitesData.save(data);

        PacketDistributor.sendToPlayer(player, new SyncPlayerSkillsPacket(tag));
    }
    public static void syncClasses(ServerPlayer player) {
        PlayerClassData data = player.getData(MyAttachments.PLAYER_CLASS);
        CompoundTag tag = PlayerClassData.save(data);

        PacketDistributor.sendToPlayer(player, new SyncPlayerClassPacket(tag));
    }



    public static void clientStaminaSet(ServerPlayer player, int stamina) {
        PacketDistributor.sendToPlayer(player, new StaminaPacket(stamina));
    }

    public static void serverDataStaminaSet(double stamina) {
        Minecraft.getInstance().getConnection().send(new StaminaDataPacket(stamina));
    }
}
