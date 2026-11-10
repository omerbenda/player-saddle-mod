package com.omerbenda.playersaddle.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HeadSaddleCurioRenderer implements ICurioRenderer {
  private static final ResourceLocation SADDLE_TEXTURE =
      ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/pig/pig_saddle.png");

  private final ModelPart model;

  public HeadSaddleCurioRenderer() {
    this.model = Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PIG_SADDLE);
  }

  @Override
  public <T extends LivingEntity, M extends EntityModel<T>> void render(
      ItemStack stack,
      SlotContext slotContext,
      PoseStack poseStack,
      RenderLayerParent<T, M> renderLayerParent,
      MultiBufferSource renderTypeBuffer,
      int light,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch) {
    if (!(renderLayerParent.getModel() instanceof HumanoidModel<?> humanoidModel)) {
      return;
    }

    poseStack.pushPose();

    humanoidModel.getHead().translateAndRotate(poseStack);

    poseStack.translate(0.0D, -0.75D, 0.0D);
    poseStack.scale(0.65F, 0.65F, 0.65F);
    poseStack.translate(0.0D, -0.25D, 0.0D);

    VertexConsumer vertexConsumer =
        ItemRenderer.getArmorFoilBuffer(
            renderTypeBuffer, RenderType.armorCutoutNoCull(SADDLE_TEXTURE), false, stack.hasFoil());

    this.model.render(
        poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

    poseStack.popPose();
  }
}
