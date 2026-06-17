package com.ZRedCon.zrbloodmachine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LifeTreeBaseBlockEntity extends BlockEntity {

    // The logs that make up this tree (NOT including the base itself).
    private final List<BlockPos> logPositions = new ArrayList<>();

    public LifeTreeBaseBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.LIFE_TREE_BASE.get(), pos, state);
    }

    // Called once by the growth code to record the tree's structure.
    public void setStructure(List<BlockPos> positions) {
        this.logPositions.clear();
        this.logPositions.addAll(positions);
        this.setChanged();
    }

    public List<BlockPos> getLogPositions() {
        return this.logPositions;
    }

    // --- Persistence (saving to / loading from disk) ---

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        long[] packed = new long[this.logPositions.size()];
        for (int i = 0; i < packed.length; i++) {
            packed[i] = this.logPositions.get(i).asLong();
        }
        tag.putLongArray("LogPositions", packed);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.logPositions.clear();
        for (long packed : tag.getLongArray("LogPositions")) {
            this.logPositions.add(BlockPos.of(packed));
        }
    }
}