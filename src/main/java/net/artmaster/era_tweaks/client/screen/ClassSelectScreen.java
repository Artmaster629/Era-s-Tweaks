package net.artmaster.era_tweaks.client.screen;

import net.artmaster.era_tweaks.api.container.MyAttachments;
import net.artmaster.era_tweaks.api.container.PlayerClassData;
import net.artmaster.era_tweaks.api.container.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.api.gui.ImageButton;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClassSelectScreen extends Screen {







    private static final ResourceLocation CENTER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/attribute_manage_texture_bg.png");


    private static final ResourceLocation BUTTON_WIZARD_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/button/button_wizard.png");
    private static final ResourceLocation BUTTON_WARRIOR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/button/button_warrior.png");
    private static final ResourceLocation BUTTON_ASSISTANT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/button/button_assistant.png");




    PlayerSAttrubitesData data;
    PlayerClassData classdata;
    String className;


    public ClassSelectScreen() {
        super(Component.translatable("gui.era_tweaks.class_select_screen"));
    }

    public boolean isPauseScreen() {
        return true;
    }
    public boolean shouldCloseOnEsc() {
        return true;
    }


    public void refresh() {
        this.clearWidgets();
        createButtons();
    }


    private void createButtons() {
        if (classdata.getPlayerClass().equals("unknown")) {
            this.addRenderableWidget(new ImageButton(
                    ((this.width - 40)/2) - 150, ((this.height - 125)/2), 40, 125,
                    BUTTON_WARRIOR_TEXTURE,
                    Component.translatable("text.era_tweaks.warrior_class"),
                    "warrior_select_class",
                    List.of(Component.translatable("tooltip.era_tweaks.warrior_desc")),
                    btn -> {
                        Network.serverDataAction("warrior", 2);
                        refresh();
                        refresh();
                    }
            ));
            this.addRenderableWidget(new ImageButton(
                    ((this.width - 40)/2), ((this.height - 125)/2), 40, 125,
                    BUTTON_WIZARD_TEXTURE,
                    Component.translatable("text.era_tweaks.wizard_class"),
                    "wizard_select_class",
                    List.of(Component.translatable("tooltip.era_tweaks.wizard_desc")),
                    btn -> {
                        Network.serverDataAction("wizard", 2);
                        refresh();
                        refresh();
                    }
            ));
            this.addRenderableWidget(new ImageButton(
                    ((this.width - 40)/2) + 150, ((this.height - 125)/2), 40, 125,
                    BUTTON_ASSISTANT_TEXTURE,
                    Component.translatable("text.era_tweaks.assistant_class"),
                    "assistant_select_class",
                    List.of(Component.translatable("tooltip.era_tweaks.assistant_desc")),
                    btn -> {
                        Network.serverDataAction("assistant", 2);
                        refresh();
                        refresh();
                    }
            ));
        } else {
            String subclass1 = "unknown_subclass";
            String subclass2 = "unknown_subclass";
            String subclass3 = "unknown_subclass";
            if (classdata.getPlayerClass().equals("assistant")) {
                subclass1 = "commissar";
                subclass2 = "alchemist";
                subclass3 = "smith";
            }
            if (classdata.getPlayerClass().equals("wizard")) {
                subclass1 = "druid";
                subclass2 = "priest";
                subclass3 = "necromancer";
            }
            if (classdata.getPlayerClass().equals("warrior")) {
                subclass1 = "paladin";
                subclass2 = "bowman";
                subclass3 = "scout";
            }

            final String subclass_left = subclass1;
            final String subclass_center = subclass2;
            final String subclass_right = subclass3;

            this.addRenderableWidget(new ImageButton(
                    ((this.width - 40)/2) - 150, ((this.height - 125)/2), 40, 125,
                    BUTTON_WARRIOR_TEXTURE,
                    Component.translatable("text.era_tweaks."+subclass_left+"_subclass"),
                    subclass_left+"_select_subclass",
                    List.of(Component.translatable("tooltip.era_tweaks."+subclass_left+"_desc")),
                    btn -> {
                        
                        Network.serverDataAction(subclass_left, 3);
                        Network.serverDataAction("", 4);
                    }
            ));
            this.addRenderableWidget(new ImageButton(
                    ((this.width - 40)/2), ((this.height - 125)/2), 40, 125,
                    BUTTON_WIZARD_TEXTURE,
                    Component.translatable("text.era_tweaks."+subclass_center+"_subclass"),
                    subclass_center+"_select_subclass",
                    List.of(Component.translatable("tooltip.era_tweaks."+subclass_center+"_desc")),
                    btn -> {
                        Network.serverDataAction(subclass_center, 3);
                        Network.serverDataAction("", 4);
                    }


            ));
            this.addRenderableWidget(new ImageButton(
                    ((this.width - 40)/2) + 150, ((this.height - 125)/2), 40, 125,
                    BUTTON_ASSISTANT_TEXTURE,
                    Component.translatable("text.era_tweaks."+subclass_right+"_subclass"),
                    subclass_right+"_select_subclass",
                    List.of(Component.translatable("tooltip.era_tweaks."+subclass_right+"_desc")),
                    btn -> {
                        Network.serverDataAction(subclass_right, 3);
                        Network.serverDataAction("", 4);
                    }
            ));
        }

    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        assert this.minecraft.player != null;
        assert data != null;

        classdata = minecraft.player.getData(MyAttachments.PLAYER_CLASS);
        data = minecraft.player.getData(MyAttachments.PLAYER_SKILLS);
        className = classdata.getPlayerClass();

        if (!classdata.getPlayerSubClass().equals("unknown")) {
            Network.serverDataAction("", 4);
            return;
        }



        createButtons();


    }
    private void renderCustomContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        assert this.minecraft != null;
        Player player = this.minecraft.player;
        assert player != null;

//        if (classdata.getPlayerClass().equals("unknown")) {
//            if (mouseX > ((this.width - 40)/2)-150 && mouseX < ((this.width - 40)/2)-110 && mouseY > ((this.height - 125)/2) && mouseY < ((this.height - 125)/2)+125) {
//                guiGraphics.renderTooltip(font, Component.translatable("tooltip.era_tweaks.warrior_desc"), mouseX, mouseY);
//            }
//            if (mouseX > ((this.width - 40)/2) && mouseX < ((this.width - 40)/2)+40 && mouseY > ((this.height - 125)/2) && mouseY < ((this.height - 125)/2)+125) {
//                guiGraphics.renderTooltip(font, Component.translatable("tooltip.era_tweaks.wizard_desc"), mouseX, mouseY);
//            }
//            if (mouseX > ((this.width - 40)/2)+150 && mouseX < ((this.width - 40)/2)+190 && mouseY > ((this.height - 125)/2) && mouseY < ((this.height - 125)/2)+125) {
//                guiGraphics.renderTooltip(font, Component.translatable("tooltip.era_tweaks.assistant_desc"), mouseX, mouseY);
//            }
//        } else {
//            String subclass1 = "unknown_subclass";
//            String subclass2 = "unknown_subclass";
//            String subclass3 = "unknown_subclass";
//            if (classdata.getPlayerClass().equals("assistant")) {
//                subclass1 = "commissar";
//                subclass2 = "alchemist";
//                subclass3 = "smith";
//            }
//            if (classdata.getPlayerClass().equals("wizard")) {
//                subclass1 = "druid";
//                subclass2 = "priest";
//                subclass3 = "necromancer";
//            }
//            if (classdata.getPlayerClass().equals("warrior")) {
//                subclass1 = "paladin";
//                subclass2 = "bowman";
//                subclass3 = "scout";
//            }
//
//            if (mouseX > ((this.width - 40)/2)-150 && mouseX < ((this.width - 40)/2)-110 && mouseY > ((this.height - 125)/2) && mouseY < ((this.height - 125)/2)+125) {
//                guiGraphics.renderTooltip(font, Component.translatable("tooltip.era_tweaks."+subclass1+"_desc"), mouseX, mouseY);
//            }
//            if (mouseX > ((this.width - 40)/2) && mouseX < ((this.width - 40)/2)+40 && mouseY > ((this.height - 125)/2) && mouseY < ((this.height - 125)/2)+125) {
//                guiGraphics.renderTooltip(font, Component.translatable("tooltip.era_tweaks."+subclass2+"_desc"), mouseX, mouseY);
//            }
//            if (mouseX > ((this.width - 40)/2)+150 && mouseX < ((this.width - 40)/2)+190 && mouseY > ((this.height - 125)/2) && mouseY < ((this.height - 125)/2)+125) {
//                guiGraphics.renderTooltip(font, Component.translatable("tooltip.era_tweaks."+subclass3+"_desc"), mouseX, mouseY);
//            }
//        }










        float scale = 2.0f;

        int centerX = this.width / 2;

        float drawX = centerX / scale;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0f);


        if (classdata.getPlayerClass().equals("unknown")) {
            guiGraphics.drawCenteredString(
                    this.font,
                    Component.translatable("title.era_tweaks.select_class"),
                    (int) drawX,
                    10,
                    0xFFFFFF
            );
        } else {
            guiGraphics.drawCenteredString(
                    this.font,
                    Component.translatable("title.era_tweaks.select_subclass"),
                    (int) drawX,
                    10,
                    0xFFFFFF
            );
        }



        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(1.5f, 1.5f, 1.0f);
        guiGraphics.pose().popPose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {




        // 1. Ванильный blur и затемнение мира
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);

        // 2. Кастомный фон (сверху над блюром, но под кнопками)
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 50);

        //drawCustomBackground(guiGraphics);

        guiGraphics.pose().popPose();

        // 3. Рисуем кнопки
        // ВНИМАНИЕ: это супер важно — рисовать их ПОСЛЕ фона
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, delta);
        }

        // 4. Рисуем остальное содержимое GUI
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 100);
        renderCustomContent(guiGraphics, mouseX, mouseY, delta);
        if(!className.equals(classdata.getPlayerClass())) {
            className = classdata.getPlayerClass();
            this.clearWidgets();
            createButtons();
        }
        guiGraphics.pose().popPose();





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
