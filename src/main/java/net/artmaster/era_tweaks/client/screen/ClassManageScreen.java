package net.artmaster.era_tweaks.client.screen;

import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.custom.gui.SkillButton;
import net.artmaster.era_tweaks.custom.player_classes.SkillChecksManager;
import net.artmaster.era_tweaks.utils.TooltipUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClassManageScreen extends Screen {


    private static final ResourceLocation BUTTON_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/button/skill_button_texture.png");

    private static final ResourceLocation BUTTON_DENIED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/button/skill_button_denied_texture.png");

    private static final ResourceLocation BUTTON_ACCEPTED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/button/skill_button_accepted_texture.png");

    private static final ResourceLocation BG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/class_manage_texture_bg.png");



    PlayerClassData data;
    PlayerSAttrubitesData sAttrubitesData;


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
    private double zoom = 0.5;

    private int buttonWidth = 64;
    private int buttonHeight = 76;

    private static final double MIN_ZOOM = 0.5;
    private static final double MAX_ZOOM = 2.5;



    int page = 1;



    List<SkillButton> nodes;

    record Node(int x, int y, String name){}


    public void setPage(int pageNumber) {
        page=pageNumber;
        rebuildNodes();
    }

    //Следующая страница
    public void nextPage() {
        if (page >= 3) return;
        page+=1;
        rebuildNodes();
    }

    //Предыдущая страница
    public void prevPage() {
        if (page <= 1) return;
        page-=1;
        rebuildNodes();
    }

    public void refreshTooltips() {
        this.clearTooltipForNextRenderPass();
    }



    int globalClipW = 0;
    int globalClipH = 0;
    int globalClipY = 0;
    int globalClipX = 0;

    @Override
    protected void init() {
        LocalPlayer player = minecraft.player;
        if (player == null) return;

        data = player.getData(ModAttachments.PLAYER_CLASS);
        sAttrubitesData = player.getData(ModAttachments.PLAYER_SKILLS);

        this.addRenderableWidget(
                Button.builder(
                                Component.literal("<-"),
                                (btn) -> {
                                    prevPage();
                                }
                        ).bounds(
                                this.width / 2 - 20,
                                this.height / 2 + 150,
                                20,
                                20)
                        .build()
        );
        this.addRenderableWidget(
                Button.builder(
                                Component.translatable("->"),
                                (btn) -> {
                                    nextPage();
                                }
                        ).bounds(
                                this.width / 2 + 20,
                                this.height / 2 + 150,
                                20,
                                20)
                        .build()
        );

        nodes = SkillChecksManager.getNodes(page, BUTTON_TEXTURE, this.minecraft.player, data, sAttrubitesData, buttonWidth, buttonHeight);


    }

    private void rebuildNodes() {
        this.clearTooltipForNextRenderPass();
        nodes = SkillChecksManager.getNodes(page, BUTTON_TEXTURE, minecraft.player, data, sAttrubitesData, buttonWidth, buttonHeight);
    }


    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        assert this.data != null;
        assert this.minecraft != null;
        assert this.minecraft.player != null;


        assert nodes != null;




        super.render(g, mouseX, mouseY, partialTick);


        int texW = this.width;
        int texH = this.height;

        int texX = (this.width - texW) / 2;
        int texY = (this.height - texH) / 2;

        g.blit(
                BG_TEXTURE,
                texX, texY,
                0, 0,
                texW, texH,
                texW, texH
        );

        for (Renderable renderable : this.renderables) {
            renderable.render(g, mouseX, mouseY, partialTick);
        }

        int clipX = 20;
        int clipY = 65;
        int clipW = this.width-20;  //this.width/2+300;
        int clipH = this.height-20;  //this.height/2+155;

        g.pose().pushPose();
        // перенос базовой системы внутрь окна
        g.pose().translate(clipX, clipY, 0);
        // клип
        g.enableScissor(clipX, clipY, clipW, clipH);

        // центрировать
        g.pose().translate(clipW/2f + scrollX, clipH/2f + scrollY, 0);
        g.pose().scale((float)zoom, (float)zoom, 1f);

        double realX = (float)((mouseX - clipX - (clipW/2f) - scrollX) / zoom);
        double realY = (float)((mouseY - clipY - (clipH/2f) - scrollY) / zoom);
        //линии
        for (SkillButton node : nodes) {
            for (SkillButton parent : nodes) {
                if (node.getParents().skills().contains(parent.getId())) {
                    drawLine(g, parent.getX(), parent.getY(), node.getX(), node.getY(), 0xFFFFFFFF);
                }
            }
            for (SkillButton excluding : nodes) {
                if (node.getExcludingId().equals(excluding.getId())) {
                    drawLine(g, excluding.getX(), excluding.getY(), node.getX(), node.getY(), 0xFFB63A2F);
                }
            }

        }

        for(SkillButton n : nodes){
            drawNode(g, n);
            globalClipW = clipW;
            globalClipH = clipH;
            globalClipX = clipX;
            globalClipY = clipY;
        }

        g.disableScissor();
        g.pose().popPose();

        for(SkillButton n : nodes){
            if(hit(n, realX, realY)){
                g.renderComponentTooltip(font, n.getTooltipCustom(), mouseX, mouseY);
            }
        }

        float scale = 2.0f;
        int centerX = this.width / 2;
        float drawX = centerX / scale;

        g.pose().pushPose();
        g.pose().scale(scale, scale, 1.0f);
        g.drawCenteredString(
                this.font,
                SkillChecksManager.getTitleClass(page),
                (int) 40,
                5,
                0xFFFFFF
        );
        g.drawCenteredString(
                this.font,
                String.valueOf(data.getUpgradesPoints()),
                (int) drawX,
                7,
                0xFFFFFF
        );
        g.drawString(this.font, "?", 10, 12, 0xFFFFFF);
        g.pose().popPose();
        if (mouseX >= 10 && mouseX <= 52 && mouseY >= 12 && mouseY <= 52) {
            g.renderComponentTooltip(this.font, TooltipUtil.splitTooltip("tooltip.era_tweaks.beta_guide"), mouseX, mouseY);
        }

        g.drawCenteredString(
                this.font,
                String.valueOf((double) Math.round(data.getUpgradesPointsProgress()*100)/100+"/5"),
                centerX,
                40,
                0xFFFFFF
        );
    }

    private void drawLine(GuiGraphics g, int x1, int y1, int x2, int y2, int color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int steps = Math.max(dx, dy);
        if (steps <= 0) steps = 1;

        for (int i = 0; i <= steps; i++) {
            int x = x1 + i * (x2 - x1) / steps;
            int y = y1 + i * (y2 - y1) / steps;
            g.fill(x, y, x+1, y+1, color);
        }
    }

    private void drawNode(GuiGraphics g, SkillButton node){
        int x = node.getX() - buttonWidth/2;
        int y = node.getY() - buttonHeight/2;

        ResourceLocation finalTexture = BUTTON_TEXTURE;

        if (node.getParents().isRequiredAll()) {
            for (String p : node.getParents().skills()) {
                if (!data.getPlayerSkills().contains(p) && !p.equals("none")) {
                    finalTexture = BUTTON_DENIED_TEXTURE;
                    break;
                }
            }
        } else {
            if (!data.getPlayerSkills().contains(node.getParents().skills().getFirst()) && !data.getPlayerSkills().contains(node.getParents().skills().getLast())) {
                finalTexture = BUTTON_DENIED_TEXTURE;
            } else {
                finalTexture = BUTTON_TEXTURE;
            }

        }
        if (data.getPlayerSkills().contains(node.getExcludingId())) {finalTexture = BUTTON_DENIED_TEXTURE;}
        if (data.getPlayerSkills().contains(node.getId())) {finalTexture = BUTTON_ACCEPTED_TEXTURE;}
        g.blit(finalTexture, x, y, 0, 0, buttonWidth, buttonHeight, buttonWidth, buttonHeight);
        if (node.getItem() instanceof Item item) {
            float scale = 2.2f;
            float drawX = x / scale;
            float drawY = y / scale;

            g.pose().pushPose();
            g.pose().scale(scale, scale, 1.0f);
            g.renderItem(new ItemStack(item), (int)drawX+6, (int)drawY+1);
            g.pose().popPose();
        }

        else if (node.getItem() instanceof DeferredHolder deferredHolder && deferredHolder.get() instanceof Item item) {

            float scale = 2.2f;
            float drawX = x / scale;
            float drawY = y / scale;

            g.pose().pushPose();
            g.pose().scale(scale, scale, 1.0f);
            g.renderItem(new ItemStack(item), (int)drawX+6, (int)drawY+1);
            g.pose().popPose();
        }

        float scale = 0.75f;
        float drawX = x / scale;
        float drawY = y / scale;

        g.pose().pushPose();
        g.pose().scale(scale, scale, 1.0f);
        List<Component> textFull = TooltipUtil.splitTooltip(node.getMessage().getString());
        for (int i=0; i<textFull.size(); i++) {
            g.drawCenteredString(font, textFull.get(i), (int) drawX+42, (int) drawY+75+(i*8), 0xFFFFFFFF);
        }

        g.pose().popPose();



    }



    //DRAG TO SCROLL
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        boolean returning = true;

        int clipW = globalClipW;
        int clipH = globalClipH;
        int clipX = globalClipX;
        int clipY = globalClipY;
        this.scrollX += dx;
        this.scrollY += dy;
        //System.out.println("X: "+dx+", Y:"+dy);
        for (SkillButton skill : nodes) {
            if (skill.getId().equals("common_warrior_skill_2")) {
                double screenX =
                        clipX + clipW / 2.0 + (skill.getX() + scrollX) * zoom;

                double screenY =
                        clipY + clipH / 2.0 + (skill.getY() + scrollY) * zoom;
                //System.out.println(skill.getId()+"X: "+screenX+", Y:"+screenY);
                if (screenX < 50 || screenY < 64) {
                    returning = false;
                }

            }
        }
        return returning;
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
        double realX = (float)((mouseX - globalClipX - (globalClipW/2f) - scrollX) / zoom);
        double realY = (float)((mouseY - globalClipY - (globalClipH/2f) - scrollY) / zoom);



        for(SkillButton n : nodes){
            if(hit(n, realX, realY)){
                onNodeClick(n, mouseX, mouseY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean hit(SkillButton n, double rx, double ry){
        return rx >= n.getX() - buttonWidth/2 && rx <= n.getX() + buttonWidth/2
                && ry >= n.getY() - buttonHeight/2 && ry <= n.getY() + buttonHeight/2;
    }

    private void onNodeClick(SkillButton n, double mouseX, double mouseY){
        //System.out.println("Clicked " + n.getMessage());
        if (data.getPlayerSkills().contains(n.getId())) {
            System.out.println("accepted");
            return;
        }
        if (data.getUpgradesPoints() < 1) {
            System.out.println("not enough upgrades points");
            return;
        }
        if (data.getPlayerSkills().contains(n.getExcludingId()) && !n.getExcludingId().equals("none")) {
            System.out.println("excluding");
            return;
        }
        for (String skill : n.getParents().skills()) {
            if (n.getParents().isRequiredAll()) {
                if (!data.getPlayerSkills().contains(skill)) {
                    System.out.println("not all");
                    return;
                }
            }
            else {
                if (!data.getPlayerSkills().contains(n.getParents().skills().getFirst()) && !data.getPlayerSkills().contains(n.getParents().skills().getLast())) {
                    System.out.println("not any");
                    return;
                }
            }

        } //Родительский
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