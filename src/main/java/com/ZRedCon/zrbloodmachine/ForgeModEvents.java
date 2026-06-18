package com.ZRedCon.zrbloodmachine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZRBloodMachine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeModEvents {

    private static final int SEARCH_RADIUS = 5;

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        ServerLevel level = (ServerLevel) event.getEntity().level();
        BlockPos deathPos = event.getEntity().blockPosition();

        ServerPlayer killer = null;
        if (event.getSource().getEntity() instanceof ServerPlayer sp) {
            killer = sp;
        }

        LifeTreeBaseBlockEntity[] bases = findNearbyBase(level, deathPos);
        for (var base : bases)
        {
            if (base != null) {
                base.feed(level, level.getRandom(), killer);
            }
        }
    }

    private static LifeTreeBaseBlockEntity[] findNearbyBase(ServerLevel level, BlockPos origin) {
        List<LifeTreeBaseBlockEntity> bes = new ArrayList<>();
        for (BlockPos p : BlockPos.betweenClosed(
                origin.offset(-SEARCH_RADIUS, -0, -SEARCH_RADIUS),
                origin.offset(SEARCH_RADIUS, 1, SEARCH_RADIUS))) {
            if (level.getBlockState(p).is(BlockInit.LIFE_TREE_BASE.get())) {
                BlockEntity be = level.getBlockEntity(p);
                if (be instanceof LifeTreeBaseBlockEntity base) {
                    bes.add(base);
                }
            }
        }
        return bes.toArray(LifeTreeBaseBlockEntity[]::new);
    }
}
