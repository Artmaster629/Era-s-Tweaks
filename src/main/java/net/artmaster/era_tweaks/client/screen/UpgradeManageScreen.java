package net.artmaster.era_tweaks.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.network.Network;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.custom.gui.ImageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@OnlyIn(Dist.CLIENT)
public class UpgradeManageScreen extends Screen {







    private static final ResourceLocation CENTER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/attribute_manage_texture_bg_2.png");

    private static final ResourceLocation EMPTY =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/empty.png");


    private static final ResourceLocation INTELLECT =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/intellect.png");
    private static final ResourceLocation INTELLECT_BASE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/intellect_base.png");

    private static final ResourceLocation BODY =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/body.png");
    private static final ResourceLocation BODY_BASE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/body_base.png");

    private static final ResourceLocation SOCIETY =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/society.png");
    private static final ResourceLocation SOCIETY_BASE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/society_base.png");



    private static final ResourceLocation SKILL_ACTIVE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/actives_icons/active_enabled.png");

    private static final ResourceLocation SKILL_NOT_ACTIVE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/actives_icons/active_disabled.png");

    private static final ResourceLocation SKILL_ON_COOLDOWN =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/actives_icons/active_on_cooldown.png");





    private boolean isOpenTooltipIntellect = false;
    private boolean isOpenTooltipBody = false;
    private boolean isOpenTooltipSociety = false;

    PlayerSAttrubitesData data;
    PlayerClassData classData;
    String className;




    public UpgradeManageScreen() {
        super(Component.translatable("gui.era_tweaks.upgrade_manage_screen"));
    }

    public boolean isPauseScreen() {
        return false;
    }
    public boolean shouldCloseOnEsc() {
        return true;
    }




    public void refresh() {
        this.clearWidgets();
        this.init();
    }

    private void createButtons() {

        //        if (isOpenTooltipIntellect) {
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("УМЕНИЕ"),
//                                    (btn) -> {
//                                        isOpenTooltipIntellect = !isOpenTooltipIntellect;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 7,
//                                    this.height / 2 + 85,
//                                    60,
//                                    15)
//                            .build()
//            );
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("ОПЫТ"),
//                                    (btn) -> {
//                                        isOpenTooltipIntellect = !isOpenTooltipIntellect;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 67,
//                                    this.height / 2 + 85,
//                                    60,
//                                    15)
//                            .build()
//            );
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("ФЕРМЕРСТВО"),
//                                    (btn) -> {
//                                        isOpenTooltipIntellect = !isOpenTooltipIntellect;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 7,
//                                    this.height / 2 + 100,
//                                    60,
//                                    15)
//                            .build()
//            );
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("УДАЧА"),
//                                    (btn) -> {
//                                        isOpenTooltipIntellect = !isOpenTooltipIntellect;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 67,
//                                    this.height / 2 + 100,
//                                    60,
//                                    15)
//                            .build()
//            );
//        }
//
//        if (isOpenTooltipBody) {
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("ЗДОРОВЬЕ"),
//                                    (btn) -> {
//                                        isOpenTooltipBody = !isOpenTooltipBody;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 7,
//                                    this.height / 2 + 85,
//                                    60,
//                                    15)
//                            .build()
//            );
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("СИЛА"),
//                                    (btn) -> {
//                                        isOpenTooltipBody = !isOpenTooltipBody;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 67,
//                                    this.height / 2 + 85,
//                                    60,
//                                    15)
//                            .build()
//            );
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("БРОНЯ"),
//                                    (btn) -> {
//                                        isOpenTooltipBody = !isOpenTooltipBody;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 7,
//                                    this.height / 2 + 100,
//                                    60,
//                                    15)
//                            .build()
//            );
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("СОПРОТИВЛЕНИЕ"),
//                                    (btn) -> {
//                                        isOpenTooltipBody = !isOpenTooltipBody;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 67,
//                                    this.height / 2 + 100,
//                                    60,
//                                    15)
//                            .build()
//            );
//        }
//
//        if (isOpenTooltipSociety) {
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("???"),
//                                    (btn) -> {
//                                        isOpenTooltipBody = !isOpenTooltipBody;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 7,
//                                    this.height / 2 + 85,
//                                    60,
//                                    15)
//                            .build()
//            );
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("???"),
//                                    (btn) -> {
//                                        isOpenTooltipBody = !isOpenTooltipBody;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 67,
//                                    this.height / 2 + 85,
//                                    60,
//                                    15)
//                            .build()
//            );
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("???"),
//                                    (btn) -> {
//                                        isOpenTooltipBody = !isOpenTooltipBody;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 7,
//                                    this.height / 2 + 100,
//                                    60,
//                                    15)
//                            .build()
//            );
//            this.addRenderableWidget(
//                    Button.builder(
//                                    Component.literal("???"),
//                                    (btn) -> {
//                                        isOpenTooltipBody = !isOpenTooltipBody;
//                                    }
//                            ).bounds(
//                                    this.width / 2 + 67,
//                                    this.height / 2 + 100,
//                                    60,
//                                    15)
//                            .build()
//            );
//
//
//
//
//        }


        var classdata = this.minecraft.player.getData(ModAttachments.PLAYER_CLASS);
        data = this.minecraft.player.getData(ModAttachments.PLAYER_SKILLS);
        className = classdata.getPlayerClass();
        classData = classdata;

        this.addRenderableWidget(
                Button.builder(
                                Component.translatable("gui.era_tweaks.player_class_menu_button"),
                                (btn) -> {
                                    this.minecraft.setScreen(new ClassManageScreen());
                                }
                        ).bounds(
                                this.width / 2 - 72,
                                this.height / 2 - 77,
                                40,
                                12)
                        .build()
        );

        this.addRenderableWidget(new ImageButton(
                this.width / 2 - 70,
                this.height / 2 + 85,
                32,
                32,
                EMPTY,
                Component.literal(""),
                "intellect_upgrades_open",
                List.of(Component.literal((int) data.getIntellectProgress()+"/100%")),
                btn -> {
                    isOpenTooltipSociety = false;
                    isOpenTooltipBody = false;
                    isOpenTooltipIntellect = !isOpenTooltipIntellect;
                    refresh();
                }));

        this.addRenderableWidget(new ImageButton(
                this.width / 2 - 15,
                this.height / 2 + 85,
                32,
                32,
                EMPTY,
                Component.literal(""),
                "body_upgrades_open",
                List.of(Component.literal((int) data.getBodyProgress()+"/100%")),
                btn -> {
                    isOpenTooltipIntellect = false;
                    isOpenTooltipSociety = false;
                    isOpenTooltipBody = !isOpenTooltipBody;
                    refresh();
                }
                ));

        this.addRenderableWidget(new ImageButton(
                this.width / 2 + 40,
                this.height / 2 + 85,
                32,
                32,
                EMPTY,
                Component.literal(""),
                "society_upgrades_open",
                List.of(Component.literal((int) data.getSocietyProgress()+"/100%")),
                btn -> {
                    isOpenTooltipIntellect = false;
                    isOpenTooltipBody = false;
                    isOpenTooltipSociety = !isOpenTooltipSociety;
                    refresh();
                }
                ));




        ResourceLocation ACTIVE_1_TEXTURE =
                classData.isActive1onCooldown() ? SKILL_ON_COOLDOWN :
                        classData.isActive1Enabled()    ? SKILL_ACTIVE :
                                SKILL_NOT_ACTIVE;

        ResourceLocation ACTIVE_2_TEXTURE =
                classData.isActive2onCooldown() ? SKILL_ON_COOLDOWN :
                        classData.isActive2Enabled()    ? SKILL_ACTIVE :
                                SKILL_NOT_ACTIVE;

        ResourceLocation ACTIVE_3_TEXTURE =
                classData.isActive3onCooldown() ? SKILL_ON_COOLDOWN :
                        classData.isActive3Enabled()    ? SKILL_ACTIVE :
                                SKILL_NOT_ACTIVE;

        ResourceLocation ACTIVE_4_TEXTURE =
                classData.isActive4onCooldown() ? SKILL_ON_COOLDOWN :
                        classData.isActive4Enabled()    ? SKILL_ACTIVE :
                                SKILL_NOT_ACTIVE;



        this.addRenderableWidget(new ImageButton(
                (this.width / 2) - 108, (this.height/2) - 43, 52, 52,
                ACTIVE_1_TEXTURE,
                Component.translatable("АКТИВ 1"),
                "active_1_button",
                List.of(Component.translatable("tooltip.era_tweaks.active1_"+classData.getPlayerSubClass(),
                        classData.isActive1Enabled(),
                        classData.isActive1onCooldown()
                        )
                ),
                btn -> {
                    Network.toServerAction("active1", 10);
                    //refresh();
                }
        ));

        this.addRenderableWidget(new ImageButton(
                (this.width / 2) - 108, (this.height/2) + 12, 52, 52,
                ACTIVE_2_TEXTURE,
                Component.translatable("АКТИВ 2"),
                "active_2_button",
                List.of(Component.translatable("tooltip.era_tweaks.active2_"+classData.getPlayerSubClass(),
                                classData.isActive2Enabled(),
                                classData.isActive2onCooldown()
                        )
                ),
                btn -> {
                    Network.toServerAction("active2", 10);
                    //refresh();
                }
        ));

        this.addRenderableWidget(new ImageButton(
                (this.width / 2) + 55, (this.height/2) - 43, 52, 52,
                ACTIVE_3_TEXTURE,
                Component.translatable("АКТИВ 3"),
                "active_3_button",
                List.of(Component.translatable("tooltip.era_tweaks.active3_"+classData.getPlayerSubClass(),
                                classData.isActive3Enabled(),
                                classData.isActive3onCooldown()
                        )
                ),
                btn -> {
                    Network.toServerAction("active3", 10);
                    //refresh();
                }
        ));

        this.addRenderableWidget(new ImageButton(
                (this.width / 2) + 55, (this.height/2) + 12, 52, 52,
                ACTIVE_4_TEXTURE,
                Component.translatable("АКТИВ 4"),
                "active_4_button",
                List.of(Component.translatable("tooltip.era_tweaks.active4_"+classData.getPlayerSubClass(),
                                classData.isActive4Enabled(),
                                classData.isActive4onCooldown()
                        )
                ),
                btn -> {
                    Network.toServerAction("active4", 10);
                    //refresh();
                }
        ));

    }



    @Override
    protected void init() {
        assert this.minecraft != null;
        assert this.minecraft.player != null;
        assert data != null;




        createButtons();

    }




    private void drawCustomBackground(GuiGraphics guiGraphics) {
        int texW = 215;
        int texH = 261;

        int texX = (this.width - texW) / 2;
        int texY = (this.height - texH) / 2;

        guiGraphics.blit(
                CENTER_TEXTURE,
                texX, texY,
                0, 0,
                texW, texH,
                texW, texH
        );
    }

    private void renderCustomContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        assert this.minecraft != null;
        Player player = this.minecraft.player;
        assert player != null;
        LivingEntity livingEntity = this.minecraft.player;
        renderEntityFollowMouse(guiGraphics, this.width / 2, this.height / 2 + 70, 60, mouseX, mouseY, livingEntity);





        float scale = 2.0f;
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        float drawX = centerX / scale;
        float drawY = (centerY - 120) / scale; // минус -  выше центра

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0f);
        guiGraphics.drawCenteredString(
                this.font,
                Component.translatable("text.era_tweaks."+className+"_class"),
                (int) drawX,
                (int) drawY,
                0xFFFFFF
        );



        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(1.5f, 1.5f, 1.0f);

        guiGraphics.drawCenteredString(
                this.font,
                Component.translatable("text.era_tweaks."+classData.getPlayerSubClass()+"_subclass"),
                (int) ((centerX) / 1.5f),
                (int) ((centerY-100) /1.5f),
                0xFFFFFF
        );
        guiGraphics.pose().popPose();
    }



    private void renderAttributes(GuiGraphics guiGraphics) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(1.5f, 1.5f, 1.0f);
        int centerX = this.width / 2;
        int centerY = this.height / 2;



        guiGraphics.drawCenteredString(
                this.font,
                Component.literal(""+data.getIntellectLevel()),
                (int) ((centerX - 54) / 1.5f), //разница -16
                (int) ((centerY + 95)  / 1.5f), //разница 10
                0xFFFFFF
        );
        guiGraphics.drawCenteredString(
                this.font,
                Component.literal(""+data.getBodyLevel()),
                (int) ((centerX + 1) / 1.5f),
                (int) ((centerY + 95)  / 1.5f),
                0xFFFFFF
        );
        guiGraphics.drawCenteredString(
                this.font,
                Component.literal(""+data.getSocietyLevel()),
                (int) ((centerX + 56) / 1.5f),
                (int) ((centerY + 95)  / 1.5f),
                0xFFFFFF
        );
        guiGraphics.pose().popPose();


        double percent_intellect = Mth.clamp(data.getIntellectProgress() / 100.0, 0.0, 1.0);
        int texSize = 32;
        int topOffsetIntellect = 5;
        int contentHeightIntellect = 23;
        int fillIntellect = (int)(contentHeightIntellect * percent_intellect);
        guiGraphics.blit(
                INTELLECT_BASE,
                this.width/2 - 70,
                this.height/2 + 85,
                0, 0,
                texSize, texSize,
                texSize, texSize
        );
        RenderSystem.setShaderColor(0F, 0F, 1F, 1F);
        guiGraphics.blit(
                INTELLECT,
                this.width/2 - 70,
                this.height/2 + 85 + topOffsetIntellect + (contentHeightIntellect - fillIntellect),
                0,
                topOffsetIntellect + (contentHeightIntellect - fillIntellect),
                texSize,
                fillIntellect,
                texSize,
                texSize
        );
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);


        double percent_body = Mth.clamp(data.getBodyProgress() / 100.0, 0.0, 1.0);
        int topOffsetBody = 1;
        int contentHeightBody = 30;
        int fillBody = (int)(contentHeightBody * percent_body);
        guiGraphics.blit(
                BODY_BASE,
                this.width/2 - 15,
                this.height/2 + 85,
                0, 0,
                texSize, texSize,
                texSize, texSize
        );
        RenderSystem.setShaderColor(1F, 0F, 0F, 1F);
        guiGraphics.blit(
                BODY,
                this.width/2 - 15,
                this.height/2 + 85 + topOffsetBody + (contentHeightBody - fillBody),
                0,
                topOffsetBody + (contentHeightBody - fillBody),
                texSize,
                fillBody,
                texSize,
                texSize
        );
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        double percent_society = Mth.clamp(data.getSocietyProgress() / 100.0, 0.0, 1.0);
        int topOffsetSociety = 1;
        int contentHeightSociety = 30;
        int fillSociety = (int)(contentHeightSociety * percent_society);
        guiGraphics.blit(
                SOCIETY_BASE,
                this.width/2 + 40,
                this.height/2 + 85,
                0, 0,
                texSize, texSize,
                texSize, texSize
        );
        RenderSystem.setShaderColor(0F, 1F, 0F, 1F);
        guiGraphics.blit(
                SOCIETY,
                this.width/2 + 40,
                this.height/2 + 85 + topOffsetSociety + (contentHeightSociety - fillSociety),
                0,
                topOffsetSociety + (contentHeightSociety - fillSociety),
                texSize,
                fillSociety,
                texSize,
                texSize
        );
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        guiGraphics.pose().popPose();

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {


        // 1. Ванильный блюр и затемнение мира
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);

        // 2. Кастомный фон (сверху над блюром, но под кнопками)
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 50);
        drawCustomBackground(guiGraphics);
        guiGraphics.pose().popPose();

        // 3. Кнопки
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, delta);
        }

        // 4. Остальное содержимое GUI
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 100);
        renderCustomContent(guiGraphics, mouseX, mouseY, delta);
        // 5. Атрибуты
        renderAttributes(guiGraphics);


    }

    public static void renderEntityFollowMouse(
            GuiGraphics guiGraphics,
            int x,
            int y,
            int scale,
            double mouseX,
            double mouseY,
            LivingEntity entity
    ) {
        float dx = x - (float) mouseX;
        float dy = y - (float) mouseY;

        float yaw = (float) Math.atan(dx / 40.0F);
        float pitch = (float) Math.atan(dy / 40.0F);

        Quaternionf pose = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf cameraOrientation = new Quaternionf()
                .rotateX(pitch * 20.0F * ((float) Math.PI / 180F));

        pose.mul(cameraOrientation);

        // сохранить старые углы
        float oldBodyRot = entity.yBodyRot;
        float oldYRot = entity.getYRot();
        float oldXRot = entity.getXRot();
        float oldHeadRot = entity.yHeadRot;
        float oldHeadRotO = entity.yHeadRotO;

        // ванильная логика
        entity.yBodyRot = 180.0F + yaw * 20.0F;
        entity.setYRot(180.0F + yaw * 40.0F);
        entity.setXRot(-pitch * 20.0F);
        entity.yHeadRot = entity.getYRot();
        entity.yHeadRotO = entity.getYRot();

        InventoryScreen.renderEntityInInventory(
                guiGraphics,
                x,
                y,
                scale,
                new Vector3f(0, 0, 0),
                pose,
                cameraOrientation,
                entity
        );

        // вернуть назад
        entity.yBodyRot = oldBodyRot;
        entity.setYRot(oldYRot);
        entity.setXRot(oldXRot);
        entity.yHeadRot = oldHeadRot;
        entity.yHeadRotO = oldHeadRotO;
    }



    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }


    @Override
    public boolean charTyped(char chr, int modifiers) {
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        return super.mouseClicked(mouseX, mouseY, button);
    }



}

// отрисовка миникарты(экспериментальная функция, сейчас неактивна)
//        XaeroMinimapSession session = XaeroMinimapSession.getCurrentSession();
//        if (session == null) return;
//
//        MinimapProcessor processor = session.getMinimapProcessor();
//        if (processor == null) return;
//
//        int size = processor.getMinimapSize();
//        int boxSize = size;
//        int width = this.width;
//        int height = this.height;
//        double scale = 0.75;
//
//        CustomVertexConsumers cvc = new CustomVertexConsumers();
//
//
//        processor.onRender(
//                guiGraphics,
//                this.textBox.getWidth()*5, -100,
//                width, height,
//                scale,
//                size, boxSize,
//                delta,
//                cvc
//        );
//
//        guiGraphics.pose().popPose();
////        guiGraphics.pose().translate(50, 50, 0); // x=50, y=50
////        guiGraphics.pose().scale(0.5f, 0.5f, 1.0f); // уменьшаем карту в 2 раза
