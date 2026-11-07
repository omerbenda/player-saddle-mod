package com.omerbenda.playersaddle;

import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
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
    CuriosApi.registerCurio(
        Items.SADDLE,
        new ICurioItem() {
          @Override
          public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
            ICurioItem.super.onUnequip(slotContext, newStack, stack);

            LivingEntity entity = slotContext.entity();

            for (Entity passenger : entity.getPassengers()) {
              passenger.unRide();
            }

            if (entity instanceof ServerPlayer serverPlayer) {
              syncPassengers(serverPlayer);
            }
          }
        });
  }

  private void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract evt) {
    if (!(evt.getTarget() instanceof LivingEntity targetEntity)) {
      return;
    }

    Player sourcePlayer = evt.getEntity();

    if (hasItemCurio(targetEntity, Items.SADDLE) && targetEntity.getPassengers().isEmpty()) {
      boolean success = sourcePlayer.startRiding(targetEntity, true);

      if (!success) {
        return;
      }

      if (targetEntity instanceof ServerPlayer targetedServerPlayer) {
        syncPassengers(targetedServerPlayer);
      }

      evt.setCanceled(true);
      evt.setCancellationResult(InteractionResult.SUCCESS);
    }
  }

  private static void syncPassengers(ServerPlayer serverPlayer) {
    serverPlayer.connection.send(new ClientboundSetPassengersPacket(serverPlayer));
  }

  private static boolean hasItemCurio(LivingEntity entity, Item itemType) {
    return CuriosApi.getCuriosInventory(entity)
        .map(inventory -> inventory.findFirstCurio(itemType).isPresent())
        .orElse(false);
  }
}
