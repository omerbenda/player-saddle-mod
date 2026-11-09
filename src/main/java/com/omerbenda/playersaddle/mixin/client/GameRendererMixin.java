package com.omerbenda.playersaddle.mixin.client;

import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
  @Shadow @Final Minecraft minecraft;

  @ModifyArg(
      method = "pick",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/world/entity/projectile/ProjectileUtil;getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;"),
      index = 4)
  private Predicate<Entity> playersaddle$filterRiderTargets(Predicate<Entity> originalPredicate) {
    Entity cameraEntity = this.minecraft.getCameraEntity();

    if (cameraEntity == null) {
      return originalPredicate;
    }

    return target ->
        originalPredicate.test(target)
            && !playersaddle$shouldIgnoreRiderTarget(cameraEntity, target);
  }

  @Unique
  private static boolean playersaddle$shouldIgnoreRiderTarget(Entity cameraEntity, Entity target) {
    if (!cameraEntity.isPassengerOfSameVehicle(target)) {
      return false;
    }

    if (!cameraEntity.isShiftKeyDown()) {
      return true;
    }

    if (cameraEntity.hasIndirectPassenger(target) && cameraEntity.getXRot() < 0.0F) {
      return false;
    }

    return !target.hasIndirectPassenger(cameraEntity) || !(cameraEntity.getXRot() > 0.0F);
  }
}
