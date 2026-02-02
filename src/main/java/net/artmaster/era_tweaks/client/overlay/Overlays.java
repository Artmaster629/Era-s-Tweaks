package net.artmaster.era_tweaks.client.overlay;


import net.artmaster.era_tweaks.registry.ModAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber({Dist.CLIENT})
public class Overlays {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            GuiGraphics g = event.getGuiGraphics();
            var overlaysData = player.getData(ModAttachments.OVERLAYS_DATA);
            var progress = (double) Math.round(overlaysData.getProgress()*100)/100;
            var progress_bar = (int)overlaysData.getCommonProgress();
            if (progress_bar > 100) progress_bar = 100;


            int sw = g.guiWidth();
            int sh = g.guiHeight();

            int barWidth = 100;
            int barHeight = 2;

            int marginBottom = 10;
            int marginText = 10;
            int barY = sh - marginBottom;
            int barX = 0; //sw/2 - barWidth / 2;



            if (overlaysData.getSendGUIType() == 3) {
                // фон
                g.fill(
                        barX,
                        barY,
                        barX + barWidth,
                        barY + barHeight,
                        0xFF000000
                );
                if (progress > 0) {
                    double normalized = Math.clamp(progress_bar / 100.0, 0.0, 1.0);
                    int filled = (int)(barWidth * normalized);
                    g.fill(
                            barX,
                            barY,
                            barX + filled,
                            barY + barHeight,
                            0xFF3AC131
                    );

                    if (progress <= 100) {
                        g.drawCenteredString(
                                Minecraft.getInstance().font,
                                "Социум: +" + (int) progress,
                                barWidth/2,
                                barY - marginText,
                                0xFFFFFFFF
                        );
                    }
                }
            }
            if (overlaysData.getSendGUIType() == 2) {
                // фон
                g.fill(
                        barX,
                        barY,
                        barX + barWidth,
                        barY + barHeight,
                        0xFF000000
                );
                if (progress > 0) {
                    double normalized = Math.clamp(progress_bar / 100.0, 0.0, 1.0);
                    int filled = (int)(barWidth * normalized);
                    g.fill(
                            barX,
                            barY,
                            barX + filled,
                            barY + barHeight,
                            0xFFB63A2F
                    );

                    if (progress <= 100) {
                        g.drawCenteredString(
                                Minecraft.getInstance().font,
                                "Тело: " +progress_bar+"%",
                                barWidth/2,
                                barY - marginText,
                                0xFFFFFFFF
                        );
                    }
                }
            }
            if (overlaysData.getSendGUIType() == 1) {
                // фон
                g.fill(
                        barX,
                        barY,
                        barX + barWidth,
                        barY + barHeight,
                        0xFF000000
                );
                if (progress > 0) {
                    double normalized = Math.clamp(progress_bar / 100.0, 0.0, 1.0);
                    int filled = (int)(barWidth * normalized);
                    g.fill(
                            barX,
                            barY,
                            barX + filled,
                            barY + barHeight,
                            0xFF316FC1
                    );

                    if (progress <= 100) {
                        g.drawCenteredString(
                                Minecraft.getInstance().font,
                                "Интеллект: " +progress_bar+"%",
                                barWidth/2,
                                barY - marginText,
                                0xFFFFFFFF
                        );
                    }
                }
            }

        }

    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void renderUber(RenderGuiEvent.Pre event) {

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            GuiGraphics g = event.getGuiGraphics();
            var classData = player.getData(ModAttachments.PLAYER_CLASS);
            if (classData.getPlayerSubClass().equals("scout") && classData.getPlayerSkills().contains("scout_skill_2_2")) {
                var overlaysData = player.getData(ModAttachments.UBER);
                double rawProgress = overlaysData.getUberProgress();
                double progress = Math.round(rawProgress * 100.0) / 100.0;

                int sw = g.guiWidth();
                int sh = g.guiHeight();

                int barWidth = 180;
                int barHeight = 1;

                int marginBottom = 23;
                int marginText = 8;
                int barY = sh - marginBottom;
                int barX = sw/2 - barWidth / 2;
                // фон
                g.fill(
                        barX,
                        barY,
                        barX + barWidth,
                        barY + barHeight,
                        0xFF000000
                );
                if (rawProgress > 0) {
                    double normalized = Math.clamp(rawProgress / 100.0, 0.0, 1.0);
                    int filled = (int)(barWidth * normalized);

// заполнение
                    g.fill(
                            barX,
                            barY,
                            barX + filled,
                            barY + barHeight,
                            0xFFFFDE41
                    );

                    if (rawProgress <= 100) {
                        g.drawCenteredString(
                                Minecraft.getInstance().font,
                                "УБЕР: +" + (int) progress,
                                sw / 2,
                                barY - marginText,
                                0xFFFFFFFF
                        );
                    }
                }

            }


        }

    }
}
