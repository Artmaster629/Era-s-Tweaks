package net.artmaster.era_tweaks.client.screen;

import net.artmaster.era_tweaks.api.container.MyAttachments;
import net.artmaster.era_tweaks.api.container.PlayerSAttrubitesData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UpgradeManageScreen extends Screen {







    private static final ResourceLocation CENTER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/attribute_manage_texture_bg_2.png");




    PlayerSAttrubitesData data;
    String className;


    public UpgradeManageScreen() {
        super(Component.translatable("gui.era_tweaks.upgrade_manage_screen"));
    }

    public boolean isPauseScreen() {
        return true;
    }
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        assert this.minecraft.player != null;
        assert data != null;

        var classdata = minecraft.player.getData(MyAttachments.PLAYER_CLASS);
        data = minecraft.player.getData(MyAttachments.PLAYER_SKILLS);
        className = classdata.getPlayerClass();

        this.addRenderableWidget(
                Button.builder(
                                Component.translatable("gui.era_tweaks.player_class_menu_button"),
                                (btn) -> {
                                    this.minecraft.setScreen(new ClassManageScreen());
                                }
                        ).bounds(
                                this.width / 2 - 120,
                                this.height / 2 - 50,
                                100,
                                20)
                        .build()
        );

    }




    private void drawCustomBackground(GuiGraphics guiGraphics) {
        int texW = 285;
        int texH = 206;

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



        guiGraphics.drawString(
                this.font,
                Component.literal("Строительство"),
                this.width / 2 + 10,
                this.height / 2 - 45,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal("Добыча"),
                this.width / 2 + 10,
                this.height / 2 - 25,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal("Создание"),
                this.width / 2 + 10,
                this.height / 2 - 5,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal("Фермерство"),
                this.width / 2 + 10,
                this.height / 2 + 15,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal("Боевое искусство"),
                this.width / 2 + 10,
                this.height / 2 + 35,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal("Магия"),
                this.width / 2 + 10,
                this.height / 2 + 55,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal("Выносливость"),
                this.width / 2 + 10,
                this.height / 2 + 75,
                0xFFFFFF
        );






        guiGraphics.drawString(
                this.font,
                Component.literal(""+data.getBuildingLevel()),
                this.width / 2 + 112,
                this.height / 2 - 45,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal(""+data.getMiningLevel()),
                this.width / 2 + 112,
                this.height / 2 - 25,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal(""+data.getCraftingLevel()),
                this.width / 2 + 112,
                this.height / 2 - 5,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal(""+data.getFarmingLevel()),
                this.width / 2 + 112,
                this.height / 2 + 15,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal(""+data.getFightLevel()),
                this.width / 2 + 112,
                this.height / 2 + 35,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal(""+data.getMagicLevel()),
                this.width / 2 + 112,
                this.height / 2 + 55,
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                Component.literal(""+data.getStaminaLevel()),
                this.width / 2 + 112,
                this.height / 2 + 75,
                0xFFFFFF
        );


        int barX = (this.width / 2) + 10;
        int barY = (this.height / 2) - 20;
        int barWidth = 115;
        int barHeight = 5;



        //Строительство
        guiGraphics.fill(
                barX,
                barY-15,
                barX + barWidth,
                barY-15 + barHeight,
                0xFF262626
        );
        double progress_build = data.getBuildingProgress();
        int filled_build = (int)(barWidth * (progress_build / 100.0));
        if (filled_build > 0) {
            guiGraphics.fill(
                    barX,
                    barY-15,
                    barX + filled_build,
                    barY-15 + barHeight,
                    0xFFAA00AA
            );
        }

        if (mouseX > barX && mouseX < barX+barWidth && mouseY > barY-15 && mouseY < barY-15+barHeight) {
            guiGraphics.renderTooltip(font, Component.literal((int) data.getBuildingProgress()+"/100%"), mouseX, mouseY);
        }

        //Добыча
        guiGraphics.fill(
                barX,
                barY+5,
                barX + barWidth,
                barY+5 + barHeight,
                0xFF262626
        );
        double progress_mining = data.getMiningProgress();
        int filled_mining = (int)(barWidth * (progress_mining / 100.0));
        if (filled_mining > 0) {
            guiGraphics.fill(
                    barX,
                    barY+5,
                    barX + filled_mining,
                    barY+5 + barHeight,
                    0xFFAA00AA
            );
        }

        if (mouseX > barX && mouseX < barX+barWidth && mouseY > barY+5 && mouseY < barY+5+barHeight) {
            guiGraphics.renderTooltip(font, Component.literal((int) data.getMiningProgress()+"/100%"), mouseX, mouseY);
        }

        //Создание
        guiGraphics.fill(
                barX,
                barY+25,
                barX + barWidth,
                barY+25 + barHeight,
                0xFF262626
        );
        double progress_crafting = data.getCraftingProgress();
        int filled_crafting = (int)(barWidth * (progress_crafting / 100.0));
        if (filled_crafting > 0) {
            guiGraphics.fill(
                    barX,
                    barY+25,
                    barX + filled_crafting,
                    barY+25 + barHeight,
                    0xFFAA00AA
            );
        }

        if (mouseX > barX && mouseX < barX+barWidth && mouseY > barY+25 && mouseY < barY+25+barHeight) {
            guiGraphics.renderTooltip(font, Component.literal((int) data.getCraftingProgress()+"/100%"), mouseX, mouseY);
        }

        //Фермерство
        guiGraphics.fill(
                barX,
                barY+45,
                barX + barWidth,
                barY+45 + barHeight,
                0xFF262626
        );
        double progress_farming = data.getFarmingProgress();
        int filled_farming = (int)(barWidth * (progress_farming / 100.0));
        if (filled_farming > 0) {
            guiGraphics.fill(
                    barX,
                    barY+45,
                    barX + filled_farming,
                    barY+45 + barHeight,
                    0xFFAA00AA
            );
        }

        if (mouseX > barX && mouseX < barX+barWidth && mouseY > barY+45 && mouseY < barY+45+barHeight) {
            guiGraphics.renderTooltip(font, Component.literal((int) data.getFarmingProgress()+"/100%"), mouseX, mouseY);
        }

        //Боевое искусство
        guiGraphics.fill(
                barX,
                barY+65,
                barX + barWidth,
                barY+65 + barHeight,
                0xFF262626
        );
        double progress_fight = data.getFightProgress();
        int filled_fight = (int)(barWidth * (progress_fight / 100.0));
        if (filled_fight > 0) {
            guiGraphics.fill(
                    barX,
                    barY+65,
                    barX + filled_fight,
                    barY+65 + barHeight,
                    0xFFAA00AA
            );
        }

        if (mouseX > barX && mouseX < barX+barWidth && mouseY > barY+65 && mouseY < barY+65+barHeight) {
            guiGraphics.renderTooltip(font, Component.literal((int) data.getFightProgress()+"/100%"), mouseX, mouseY);
        }

        //Магия
        guiGraphics.fill(
                barX,
                barY+85,
                barX + barWidth,
                barY+85 + barHeight,
                0xFF262626
        );
        double progress_magic = data.getMagicProgress();
        int filled_magic = (int)(barWidth * (progress_magic / 100.0));
        if (filled_magic > 0) {
            guiGraphics.fill(
                    barX,
                    barY+85,
                    barX + filled_magic,
                    barY+85 + barHeight,
                    0xFFAA00AA
            );
        }

        if (mouseX > barX && mouseX < barX+barWidth && mouseY > barY+85 && mouseY < barY+85+barHeight) {
            guiGraphics.renderTooltip(font, Component.literal((int) data.getMagicProgress()+"/100%"), mouseX, mouseY);
        }

        //Выносливость
        guiGraphics.fill(
                barX,
                barY+105,
                barX + barWidth,
                barY+105 + barHeight,
                0xFF262626
        );
        double progress_stamina = data.getStaminaProgress();
        int filled_stamina = (int)(barWidth * (progress_stamina / 100.0));
        if (filled_stamina > 0) {
            guiGraphics.fill(
                    barX,
                    barY+105,
                    barX + filled_stamina,
                    barY+105 + barHeight,
                    0xFFAA00AA
            );
        }

        if (mouseX > barX && mouseX < barX+barWidth && mouseY > barY+105 && mouseY < barY+105+barHeight) {
            guiGraphics.renderTooltip(font, Component.literal((int) data.getStaminaProgress()+"/100%"), mouseX, mouseY);
        }







        float scale = 2.0f;

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        float drawX = centerX / scale;
        float drawY = (centerY - 90) / scale; // минус -  выше центра

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

        guiGraphics.drawString(
                this.font,
                "Управление",
                (int) ((centerX - 105) / 1.5f),
                (int) ((centerY - 62) / 1.5f),
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                "Атрибуты",
                (int) ((centerX + 30) / 1.5f),
                (int) ((centerY - 62) / 1.5f),
                0xFFFFFF
        );

        guiGraphics.drawString(
                this.font,
                "Показатели",
                (int) ((centerX - 105) / 1.5f),
                (int) ((centerY - 7) / 1.5f),
                0xFFFFFF
        );

        guiGraphics.pose().popPose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {


        // 1. Ванильный blur и затемнение мира
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);

        // 2. Кастомный фон (сверху над блюром, но под кнопками)
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 50);
        drawCustomBackground(guiGraphics);
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
