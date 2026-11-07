package com.omerbenda.playersaddle.mixin;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Player.class)
public abstract class PlayerRiderOffsetMixin extends LivingEntity {
  @Shadow
  public abstract @NotNull EntityDimensions getDimensions(@NotNull Pose pose);

  protected PlayerRiderOffsetMixin(EntityType<? extends LivingEntity> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public double getPassengersRidingOffset() {
    EntityDimensions dimensions = this.getDimensions(this.getPose());

    return (double) dimensions.height * 0.75D + 0.35D;
  }
}
