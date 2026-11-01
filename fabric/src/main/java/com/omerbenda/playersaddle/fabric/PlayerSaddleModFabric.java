package com.omerbenda.playersaddle.fabric;

import net.fabricmc.api.ModInitializer;

import com.omerbenda.playersaddle.PlayerSaddleMod;

public final class PlayerSaddleModFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    PlayerSaddleMod.init();
  }
}
