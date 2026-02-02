package net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block_entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TotemBlockEntityRenderer implements BlockEntityRenderer<TotemBlockEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("era_tweaks", "textures/gui/body.png");

    public TotemBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }


    @Override
    public void render(TotemBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, TEXTURE);

        Minecraft.getInstance().getTextureManager().bindForSetup(
                TEXTURE
        );
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer(); // Получаем состояние блока
        BlockState state = be.getBlockState(); // Получаем BakedModel
        BakedModel model = dispatcher.getBlockModel(state);
        PoseStack.Pose pose = poseStack.last(); // Отрисовываем куб
        dispatcher.getModelRenderer().renderModel(
                pose,
                bufferSource.getBuffer(RenderType.cutout()),
                state,
                model,
                1.0f, 1.0f, 1.0f, // RGB цвет (можешь поставить 1.0f, 1.0f, 1.0f для "белого")
                light,
                OverlayTexture.NO_OVERLAY
        );
        poseStack.popPose();
    }

}
