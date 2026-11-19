package com.omerbenda.playersaddle;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class PSServerConfig {
  public static final PSServerConfig CONFIG;
  public static final ForgeConfigSpec CONFIG_SPEC;

  static {
    Pair<PSServerConfig, ForgeConfigSpec> specPair =
        new ForgeConfigSpec.Builder().configure(PSServerConfig::new);
    CONFIG = specPair.getLeft();
    CONFIG_SPEC = specPair.getRight();
  }

  public final ForgeConfigSpec.BooleanValue canHurtRider;

  private PSServerConfig(ForgeConfigSpec.Builder builder) {
    builder.comment("General settings for the Player Saddle mod").push("general");

    canHurtRider =
        builder
            .comment("Toggle for allowing players to attack their riders")
            .translation("playersaddle.config.can_hurt_rider")
            .define("canHurtRider", false);

    builder.pop();
  }
}
