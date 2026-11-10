package com.omerbenda.playersaddle.mixin;

import com.omerbenda.playersaddle.PSCurioHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerRiderOffsetMixin extends LivingEntity {
  protected PlayerRiderOffsetMixin(EntityType<? extends LivingEntity> entityType, Level level) {
    super(entityType, level);
  }

  @Inject(method = "getMyRidingOffset", at = @At("RETURN"), cancellable = true)
  private void playersaddle$adjustMyRidingOffset(CallbackInfoReturnable<Double> cir) {
    if (this.getVehicle() instanceof LivingEntity vehicle
        && PSCurioHelper.hasItemCurio(vehicle, Items.SADDLE)) {
      cir.setReturnValue(cir.getReturnValue() + 0.35D);
    }
  }
}
