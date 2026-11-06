package com.omerbenda.playersaddle;

import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mod(PlayerSaddle.MOD_ID)
public class PlayerSaddle {
  public static final String MOD_ID = "playersaddle";

  public PlayerSaddle(FMLJavaModLoadingContext context) {
    final IEventBus eventBus = context.getModEventBus();
    eventBus.addListener(this::setup);
  }

  private void setup(FMLCommonSetupEvent evt) {
    CuriosApi.registerCurio(Items.SADDLE, new ICurioItem() {});
  }
}
