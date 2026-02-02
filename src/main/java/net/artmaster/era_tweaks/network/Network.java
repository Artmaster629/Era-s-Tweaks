package net.artmaster.era_tweaks.network;

import com.alrex.parcool.api.Attributes;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.player_classes.assistant.alchemist.Alchemist;
import net.artmaster.era_tweaks.custom.player_classes.assistant.common_assistant.CommonAssistant;
import net.artmaster.era_tweaks.custom.player_classes.assistant.smith.Smith;
import net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.Bowman;
import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.Priest;
import net.artmaster.era_tweaks.network.main_packets.ClientActionPacket;
import net.artmaster.era_tweaks.network.main_packets.ServerActionPacket;
import net.artmaster.era_tweaks.network.main_packets.DataPacket;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.OverlayAttributesData;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.custom.player_classes.warrior.scout.custom.ScoutUberData;
import net.artmaster.era_tweaks.custom.player_classes.warrior.common_warrior.CommonWarrior;
import net.artmaster.era_tweaks.custom.player_classes.warrior.paladin.Paladin;
import net.artmaster.era_tweaks.custom.player_classes.warrior.scout.Scout;
import net.artmaster.era_tweaks.client.ClientPacketHandler;
import net.artmaster.era_tweaks.registry.ModAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Map;
import java.util.UUID;

import static net.artmaster.era_tweaks.custom.sattributes.SAttrubitesAdding.addXpProgress;

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
                DataPacket.TYPE,
                DataPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> ClientPacketHandler.handleSyncPlayerClass(packet))
        );

        registrarClient.playToClient(
                StaminaPacket.TYPE,
                StaminaPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> ClientPacketHandler.handleStaminaConsume(packet))
        );

        registrarClient.playToClient(
                ClientActionPacket.TYPE,
                ClientActionPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    ClientPacketHandler.handleClientAction(packet);
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
                ServerActionPacket.TYPE,
                ServerActionPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();

                    var data = player.getData(ModAttachments.PLAYER_CLASS);

                    if (packet.actionType() == 1) {
                        boolean isIncluded = false;

                        for (String skill : data.getPlayerSkills()) {
                            if (skill.equals(packet.key())) {
                                isIncluded = true;
                            }
                        }


                        if (!isIncluded) {
                            data.getPlayerSkills().add(packet.key());
                            data.removeUpgradesPoints(1);
                            data.setUpgradesPointsProgress(0);
                            syncClasses(player);

                            ChangesApplier.apply(data, player, packet.key());
//                            if (packet.key().contains("warrior")) {
//                                CommonWarrior.ApplyChanges.makeChangesToClass(data, player);
//                            }
//
//                            if (packet.key().contains("paladin")) {
//                                Paladin.ApplyChanges.makeChangesToClass(data, player);
//                            }
//
//                            if (packet.key().contains("alchemist")) {
//                                Alchemist.ApplyChanges.makeChangesToClass(data, player);
//                            }
//
//                            if (packet.key().contains("scout")) {
//                                Scout.ApplyChanges.makeChangesToClass(data, player);
//
//                            }
//
//                            if (packet.key().contains("assistant")) {
//                                CommonAssistant.ApplyChanges.makeChangesToClass(data, player);
//                            }
//
//                            if (packet.key().contains("smith")) {
//                                Smith.ApplyChanges.makeChangesToClass(data, player);
//                            }


                        }
                    }
                    if (packet.actionType() == 2) {
                        data.changePlayerClass(packet.key());
                        if (packet.key().equals("warrior")) {
                            CommonWarrior.ApplyChanges.makeChangesToClass(data, player);
                        }

                        if (packet.key().equals("wizard")) {
                            //CommonWizard.ApplyChanges.makeChangesToClass(data, player);
                        }

                        if (packet.key().equals("assistant")) {
                            CommonAssistant.ApplyChanges.makeChangesToClass(data, player);
                        }
                        syncSkills(player);
                        syncClasses(player);
                    }
                    if (packet.actionType() == 3) {
                        data.changePlayerSubClass(packet.key());
                        syncSkills(player);
                        syncClasses(player);
                    }
                    if (packet.actionType() == 4) {
                        player.closeContainer();
                    }
                    if (packet.actionType() == 5) {
                        player.addEffect((new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3)));
                    }
                    if (packet.actionType() == 6) {
                        player.displayClientMessage(Component.translatable(packet.key()), false);
                    }
                    if (packet.actionType() == 7) {
                        player.displayClientMessage(Component.literal(packet.key()), true);
                    }
                    if (packet.actionType() == 8) {
                        player.setJumping(false);
                    }
                    if (packet.actionType() == 9) {
                        syncSkills(player);
                        syncClasses(player);
                    }

                    if (packet.actionType() == 10) {
                        if (packet.key().equals("active1")) {
                            boolean value = !data.isActive1Enabled();
                            data.setActive1(value);
                            System.out.println("Toggled to " + value);
                            syncClasses(player);



                            ///Кастомные ивенты по кнопкам
                            if (player.level() instanceof ServerLevel level) {
                                if (data.getPlayerSkills().contains("priest_skill_1") && data.isActive1Enabled() && !data.isActive1onCooldown()) {
                                    Priest.Actions.onPriestSkill1_And_4(player, level, data);
                                }
                            }
                            if (data.getPlayerSkills().contains("alchemist_skill_3")) {
                                if (data.isActive1Enabled() && !data.isActive1onCooldown()) {
                                    Alchemist.Actions.onAlchemistSkill3(player, data);
                                }
                            }
                        }
                        if (packet.key().equals("active2")) {
                            boolean value = !data.isActive2Enabled();
                            data.setActive2(value);
                            System.out.println("Toggled to " + value);
                            syncClasses(player);

                            ///Кастомные ивенты по кнопкам
                            if (player.level() instanceof ServerLevel level) {
                                if (data.getPlayerSkills().contains("priest_skill_2") && data.isActive2Enabled()) {
                                    Priest.Actions.onPriestSkill2(player, level);
                                }
                            }
                        }

                        if (packet.key().equals("active3")) {
                            boolean value = !data.isActive3Enabled();
                            data.setActive3(value);
                            System.out.println("Toggled to " + value);
                            syncClasses(player);
                        }

                        if (packet.key().equals("active4")) {
                            boolean value = !data.isActive4Enabled();
                            data.setActive4(value);
                            System.out.println("Toggled to " + value);

                            ///Кастомные ивенты по кнопкам
                            if (player.level() instanceof ServerLevel level) {
                                if (data.getPlayerSkills().contains("priest_skill_5_1") && data.isActive4Enabled() && !data.isActive4onCooldown()) {
                                    Priest.Actions.onPriestSkill5_1(player, level, data);
                                }
                            }
                        }




                        syncClasses(player);
                    }

                    if (packet.actionType() == 12) {
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60));
                    }

                })
        );

        registrarClient.playToServer(
                StaminaDataPacket.TYPE,
                StaminaDataPacket.CODEC,
                (packet, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);


                    double base_multiplier = packet.stamina();
                    double attribute = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);

                    double multiplier = (1.0 + (attribute/100)) * base_multiplier;

                    if (data.getBodyLevel() < 50) {
                        data.setBodyProgress(data.getBodyProgress()+multiplier);
                        var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
                        overlayData.setSendGUIType(2);
                        overlayData.setProgress(multiplier);
                        overlayData.setCommonProgress(data.getBodyProgress());
                        overlayData.setHideTicks(120);
                        syncWithOverlay(player);
                    }
                    if (data.getBodyProgress() >= 100) {
                        data.setBodyProgress(0);
                        data.setBodyLevel(data.getBodyLevel()+1);
                        int oldLevel = data.getBodyLevel()-1;
                        Network.toClientAction(player, "Ваш уровень Тела повышен с "+oldLevel+" на "+data.getBodyLevel(), 2);
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
                        addXpProgress(player);
                    }
                    syncSkills(player);
                    syncClasses(player);
                })
        );





    }



    ///Отправка действия из-под сервера на клиент (сервер -> клиент)
    public static void toClientAction(ServerPlayer player, String key, int actionType) {
        PacketDistributor.sendToPlayer(player, new ClientActionPacket(key, actionType));
    }


    ///Отправка действия из-под клиента на сервер (клиент -> сервер)
    public static void toServerAction(String key, int actionType) {
        Minecraft.getInstance().getConnection().send(new ServerActionPacket(key, actionType));
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


        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
        CompoundTag tag = PlayerSAttrubitesData.save(data);

        PacketDistributor.sendToPlayer(player, new DataPacket(tag, 1)); // 1 - атрибуты
    }


    public static void syncClasses(ServerPlayer player) {
        PlayerClassData data = player.getData(ModAttachments.PLAYER_CLASS);
        CompoundTag tag = PlayerClassData.save(data);

        PacketDistributor.sendToPlayer(player, new DataPacket(tag, 2)); // 2 - класс
    }

    public static void syncWithOverlay(ServerPlayer player) {
        OverlayAttributesData data = player.getData(ModAttachments.OVERLAYS_DATA);
        CompoundTag tag = OverlayAttributesData.save(data);

        PacketDistributor.sendToPlayer(player, new DataPacket(tag, 3)); // 3 - оверлей
    }

    public static void syncUberWithOverlay(ServerPlayer player) {
        ScoutUberData data = player.getData(ModAttachments.UBER);
        CompoundTag tag = ScoutUberData.save(data);

        PacketDistributor.sendToPlayer(player, new DataPacket(tag, 4)); // 4 - убер
    }

    // 1 - атрибуты
    // 2 - класс
    // 3 - оверлей
    // 4 - убер







    public static void clientStaminaSet(ServerPlayer player, int stamina) {
        PacketDistributor.sendToPlayer(player, new StaminaPacket(stamina));
    }

    public static void serverDataStaminaSet(double stamina) {
        Minecraft.getInstance().getConnection().send(new StaminaDataPacket(stamina));
    }
}
