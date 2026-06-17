package com.ZRedCon.zrbloodmachine;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LifeTreeLeavesBlock extends LeavesBlock {
    public LifeTreeLeavesBlock(Properties props) {
        super(props);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(4) != 0) return;
        if (!level.getBlockState(pos.below()).isAir()) return;

        float x = pos.getX() + random.nextFloat();
        float y = pos.getY() - 0.05f;
        float z = pos.getZ() + random.nextFloat();

        level.addParticle(ParticleInit.BLOOD_DRIP.get(), x, y, z, 0.0, 0.0, 0.0);
    }
}
