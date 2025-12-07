package net.artmaster.era_tweaks.client.screen;

import net.artmaster.era_tweaks.api.gui.ImageButton;
import net.artmaster.era_tweaks.api.container.MyAttachments;
import net.artmaster.era_tweaks.api.container.PlayerClassData;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import java.util.List;


public class ClassManageScreen extends Screen {


    private static final ResourceLocation BUTTON_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/button/skill_button_texture.png");





    PlayerClassData data;


    public ClassManageScreen() {
        super(Component.translatable("gui.era_tweaks.class_manage_screen"));
    }

    public boolean isPauseScreen() {
        return true;
    }
    public boolean shouldCloseOnEsc() {
        return true;
    }

    private double scrollX = 0;
    private double scrollY = 0;
    private double zoom = 1.0;

    private static final double MIN_ZOOM = 0.5;
    private static final double MAX_ZOOM = 2.5;

    private final List<ImageButton> nodes = List.of(

            new ImageButton(
                    0, 0, 20, 20,
                    BUTTON_TEXTURE,
                    Component.translatable("button.era_tweaks.furkan"),
                    "furkan",
                    btn -> {
                        Network.sendNewSkill("furkan");
                    }
            ),
            new ImageButton(
                    -50, 50, 20, 20,
                    BUTTON_TEXTURE,
                    Component.translatable("button.era_tweaks.maxicka13"),
                    "maxicka13",
                    btn -> {
                        Network.sendNewSkill("maxicka13");
                    }
            ),
            new ImageButton(
                    -100, -75, 20, 20,
                    BUTTON_TEXTURE,
                    Component.translatable("button.era_tweaks.wlad"),
                    "wlad",
                    btn -> {
                        Network.sendNewSkill("wladislawe");
                    }
            )


    );

    record Node(int x, int y, String name){}

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g, mouseX, mouseY, partialTick);



        super.render(g, mouseX, mouseY, partialTick);

        g.pose().pushPose();







        // APPLY CAMERA TRANSFORM
        g.pose().translate(width / 2f + scrollX, height / 2f + scrollY, 0);
        g.pose().scale((float)zoom, (float)zoom, 1f);

        // draw nodes
        for(ImageButton n : nodes){
            drawNode(g, n);
        }

        g.pose().popPose();

        double realX = (mouseX - width/2f - scrollX) / zoom;
        double realY = (mouseY - height/2f - scrollY) / zoom;

        for(var n : nodes){
            if(hit(n, realX, realY)){
                g.renderTooltip(font, Component.translatable("tooltip.era_tweaks."+n.getId()), mouseX, mouseY);
            }
        }


        assert this.data != null;
        assert this.minecraft != null;
        assert this.minecraft.player != null;
        data = this.minecraft.player.getData(MyAttachments.PLAYER_CLASS);



    }

    private void drawNode(GuiGraphics g, ImageButton node){
        int size = 30;
        int x = node.getX() - size/2;
        int y = node.getY() - size/2;

        //g.fill(x, y, x + size, y + size, 0xFF444444);
        g.blit(BUTTON_TEXTURE, x, y, 0, 0, 20, 20, 20, 20);



        g.drawString(font, node.getMessage(), x+5, y+10, 0xFFFFFFFF);
    }


    // DRAG TO SCROLL
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        this.scrollX += dx;
        this.scrollY += dy;
        return true;
    }

    // MOUSE WHEEL = ZOOM
    @Override
    public boolean mouseScrolled(double x, double x1, double y, double delta) {
        zoom += delta * 0.1;
        zoom = Mth.clamp(zoom, MIN_ZOOM, MAX_ZOOM);
        return true;
    }


    // CLICK LOGIC
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double realX = (mouseX - width/2f - scrollX) / zoom;
        double realY = (mouseY - height/2f - scrollY) / zoom;



        for(ImageButton n : nodes){
            if(hit(n, realX, realY)){
                onNodeClick(n, mouseX, mouseY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean hit(ImageButton n, double rx, double ry){
        int size = 30;
        return rx >= n.getX() - size/2 && rx <= n.getX() + size/2
                && ry >= n.getY() - size/2 && ry <= n.getY() + size/2;
    }

    private void onNodeClick(ImageButton n, double mouseX, double mouseY){
        System.out.println("Clicked " + n.getMessage());
        n.onClick(mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }


    @Override
    public boolean charTyped(char chr, int modifiers) {
        return super.charTyped(chr, modifiers);
    }
}