package com.ZRedCon.zrbloodmachine;

import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootProvider extends BlockLootSubProvider {

    protected ModBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for (RegistryObject<? extends Block> ro : BlockInit.WOOD_VARIANTS) {
            Block b = ro.get();
            if (b instanceof SlabBlock) {
                add(b, this::createSlabItemTable);   // drops 2 when it's a double slab
            } else if (b instanceof DoorBlock) {
                add(b, this::createDoorTable);        // drops ONE door (from the lower half only)
            } else {
                dropSelf(b);
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockInit.WOOD_VARIANTS.stream().map(ro -> (Block) ro.get()).collect(Collectors.toList());
    }
}
