package com.ZRedCon.zrbloodmachine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.HitResult;

public class LifeTreeBaseBlock extends Block implements EntityBlock {
    public static final BooleanProperty FED = BooleanProperty.create("fed");

    public LifeTreeBaseBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState().setValue(FED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FED);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LifeTreeBaseBlockEntity(pos, state);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(state.getValue(FED) ? BlockInit.LIFE_TREE_FULL_LOG.get() : BlockInit.LIFE_TREE_LOG.get());
    }
}
