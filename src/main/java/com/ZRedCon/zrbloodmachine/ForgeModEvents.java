package com.ZRedCon.zrbloodmachine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZRBloodMachine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeModEvents {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();

        // Death logic must run only on the server (the authoritative world).
        if (entity.level().isClientSide()) return;

        ServerLevel level = (ServerLevel) entity.level();
        BlockPos deathPos = entity.blockPosition();

        
    }
}
