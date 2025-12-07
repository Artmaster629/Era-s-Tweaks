package net.artmaster.era_tweaks.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

//Кнопка с поддержкой текстур
public class ImageButton extends AbstractWidget {
    private final ResourceLocation texture;
    private final String id;
    private final Consumer<ImageButton> onPress;

    public ImageButton(int x, int y, int width, int height,
                       ResourceLocation texture,
                       Component message,
                       String id,
                       Consumer<ImageButton> onPress) {
        super(x, y, width, height, message);
        this.texture = texture;
        this.id = id;
        this.onPress = onPress;
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



    }

    public String getId() {
        return id;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narration) {
    }
}
