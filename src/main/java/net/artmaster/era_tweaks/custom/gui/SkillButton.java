package net.artmaster.era_tweaks.custom.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SkillButton extends AbstractWidget {
    private final ResourceLocation texture;
    private final String id;
    private final SkillCondition parents;
    private final String excludingId;
    private final Consumer<SkillButton> onPress;
    private final Supplier<List<Component>> tooltip;
    private final Object item;

    public SkillButton(int x, int y, int width, int height,
                       ResourceLocation texture,
                       Component message,
                       String id,
                       SkillCondition parents,
                       String excludingId,
                       Supplier<List<Component>> tooltip,
                       Object item,
                       Consumer<SkillButton> onPress) {
        super(x, y, width, height, message);
        this.texture = texture;
        this.id = id;
        this.tooltip = tooltip;
        this.parents = parents;
        this.excludingId = excludingId;
        this.onPress = onPress;
        this.item = item;
    }


    @Override
    public void onClick(double mouseX, double mouseY) {
        onPress.accept(this);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.blit(texture, getX(), getY(), 0, 0, width, height, width, height);
        int textY = getY() + height - 45;
        guiGraphics.drawCenteredString(
                Minecraft.getInstance().font,
                getMessage(),
                getX() + width / 2,
                textY,
                0xFFFFFF
        );

        if (mouseX > getX() && mouseX < getX()+getWidth() && mouseY > getY() && mouseY < getY()+getHeight()) {
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, tooltip.get(), mouseX, mouseY);
        }



    }



    public List<Component> getTooltipCustom() {
        return tooltip.get(); // ВСЕГДА актуальный tooltip
    }
    public String getId() {
        return id;
    }
    public SkillCondition getParents() {
        return parents;
    }
    public String getExcludingId() {
        return excludingId;
    }

    public Object getItem() {
        return item;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narration) {
    }



}
