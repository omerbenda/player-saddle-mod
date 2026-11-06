package com.omerbenda.playersaddle;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
    final IEventBus modEventBus = context.getModEventBus();
    final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

    modEventBus.addListener(this::onSetup);
    forgeEventBus.addListener(this::onPlayerEntityInteract);
  }

  private void onSetup(FMLCommonSetupEvent evt) {
    CuriosApi.registerCurio(Items.SADDLE, new ICurioItem() {});
  }

  private void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract evt) {
    if (!(evt.getTarget() instanceof Player targetedPlayer)) {
      return;
    }

    Player sourcePlayer = evt.getEntity();

    if (hasItemCurio(targetedPlayer, Items.SADDLE)) {
      sourcePlayer.startRiding(targetedPlayer);
    }
  }

  private static boolean hasItemCurio(LivingEntity entity, Item itemType) {
    return CuriosApi.getCuriosInventory(entity)
        .map(inventory -> inventory.findFirstCurio(itemType).isPresent())
        .orElse(false);
  }
}
