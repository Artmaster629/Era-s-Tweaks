package net.artmaster.era_tweaks.client;

import net.artmaster.era_tweaks.api.upgrades.MyAttachments;
import net.artmaster.era_tweaks.api.upgrades.PlayerSkillsData;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;


public class UpgradeManageScreen extends Screen {








    PlayerSkillsData data;


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
        data = minecraft.player.getData(MyAttachments.PLAYER_SKILLS);
    }




    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta); // фон
        super.render(guiGraphics, mouseX, mouseY, delta);

        assert this.minecraft != null;
        Player player = this.minecraft.player;
        assert player != null;





        guiGraphics.drawCenteredString(this.font, Component.literal(
                "Строительство: "+data.getBuildingLevel()+" уровень, "+data.getBuildingProgress()+"/100%"
        ), this.width/2, this.height/2, 0xFFFFFF);









//        float bigScale = 2.0f; // в 2 раза больше обычного 1.0f
//        guiGraphics.pose().pushPose();
//        guiGraphics.pose().scale(bigScale, bigScale, 1.0f);
//
//        int x = (int) (this.textBox.getWidth()*1.655 / 2f / bigScale);
//        int y = (int) (20 / bigScale);
//
//        guiGraphics.drawCenteredString(this.font, partyManageTitleText, x, y, 0xFFFFFF);
//
//        guiGraphics.pose().popPose();
//
//        guiGraphics.pose().pushPose();
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
