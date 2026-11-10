package com.omerbenda.playersaddle;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class PSCurioHelper {
  public static boolean hasItemCurio(LivingEntity entity, Item itemType) {
    return CuriosApi.getCuriosInventory(entity)
        .map(inventory -> inventory.findFirstCurio(itemType).isPresent())
        .orElse(false);
  }
}
