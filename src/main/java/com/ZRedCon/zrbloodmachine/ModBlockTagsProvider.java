package com.ZRedCon.zrbloodmachine;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper efh) {
        super(output, lookup, ZRBloodMachine.MOD_ID, efh);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Axe-mineable: all wood variants + the logs + the base (consolidated from the old hand-made file)
        var axe = tag(BlockTags.MINEABLE_WITH_AXE);
        for (RegistryObject<? extends Block> ro : BlockInit.WOOD_VARIANTS) {
            axe.add(ro.get());
        }
        axe.add(BlockInit.LIFE_TREE_LOG.get(), BlockInit.LIFE_TREE_FULL_LOG.get(), BlockInit.LIFE_TREE_BASE.get());

        tag(BlockTags.PLANKS).add(BlockInit.LIFE_TREE_PLANKS.get(), BlockInit.LIFE_TREE_FULL_PLANKS.get());

        tag(BlockTags.LOGS_THAT_BURN).add(
            BlockInit.LIFE_TREE_LOG.get(), BlockInit.LIFE_TREE_WOOD.get(),
            BlockInit.LIFE_TREE_STRIPPED_LOG.get(), BlockInit.LIFE_TREE_STRIPPED_WOOD.get(),
            BlockInit.LIFE_TREE_FULL_LOG.get(), BlockInit.LIFE_TREE_FULL_WOOD.get(),
            BlockInit.LIFE_TREE_FULL_STRIPPED_LOG.get(), BlockInit.LIFE_TREE_FULL_STRIPPED_WOOD.get());

        tag(BlockTags.WOODEN_STAIRS).add(BlockInit.LIFE_TREE_STAIRS.get(), BlockInit.LIFE_TREE_FULL_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(BlockInit.LIFE_TREE_SLAB.get(), BlockInit.LIFE_TREE_FULL_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(BlockInit.LIFE_TREE_FENCE.get(), BlockInit.LIFE_TREE_FULL_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(BlockInit.LIFE_TREE_FENCE_GATE.get(), BlockInit.LIFE_TREE_FULL_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(BlockInit.LIFE_TREE_DOOR.get(), BlockInit.LIFE_TREE_FULL_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(BlockInit.LIFE_TREE_TRAPDOOR.get(), BlockInit.LIFE_TREE_FULL_TRAPDOOR.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(BlockInit.LIFE_TREE_PRESSURE_PLATE.get(), BlockInit.LIFE_TREE_FULL_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_BUTTONS).add(BlockInit.LIFE_TREE_BUTTON.get(), BlockInit.LIFE_TREE_FULL_BUTTON.get());
    }
}
