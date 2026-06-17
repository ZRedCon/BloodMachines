package com.ZRedCon.zrbloodmachine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LifeTreeSaplingBlock extends BushBlock implements BonemealableBlock{
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public LifeTreeSaplingBlock(Properties props) {
        super(props);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_,
            boolean p_50900_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos,
            BlockState p_220881_) {
        int lightLevel = level.getMaxLocalRawBrightness(pos.above());
        if (lightLevel > 7) 
            return false; 
        return random.nextFloat() > 0.5f;
    }

    @Override
    public void performBonemeal(ServerLevel p_220874_, RandomSource p_220875_, BlockPos p_220876_,
            BlockState p_220877_) {
        grow(p_220874_, p_220875_, p_220876_);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getMaxLocalRawBrightness(pos.above()) <= 7 && random.nextInt(7) == 0) {
            grow(level, random, pos);
        }
    }

    public void grow(ServerLevel level, RandomSource random, BlockPos pos) {
        int height = 4 + random.nextInt(7);
        int intersectionHeight = (int)(height * 3.0 / 4 + 0.5) - 1; // This should be 6
        BlockPos intersectionPos = pos.above(intersectionHeight);
        int crossWidth = (height - 2)/2 + 1; // This should be 3

        // Occlusion checking
        for (int i = 1; i < height; i++) {
            if (!level.getBlockState(pos.above(i)).canBeReplaced()) {
                return; 
            }
        }

        for (int i = 0; i < crossWidth; i++) {
            if(!level.getBlockState(intersectionPos.north(i)).canBeReplaced() || !level.getBlockState(intersectionPos.south(i)).canBeReplaced()) {
                return;
            }
        }

        // Building tree
        for (int i = 0; i < height; i++) {
            level.setBlock(pos.above(i), BlockInit.LIFE_TREE_LOG.get().defaultBlockState(), 3);
        }

        for (int i = 0; i < crossWidth; i++) {
            level.setBlock(intersectionPos.north(i), BlockInit.LIFE_TREE_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
            level.setBlock(intersectionPos.south(i), BlockInit.LIFE_TREE_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
        }
    }

}
