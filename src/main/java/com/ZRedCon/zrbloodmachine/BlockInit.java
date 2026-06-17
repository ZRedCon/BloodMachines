package com.ZRedCon.zrbloodmachine;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ZRBloodMachine.MOD_ID);

    // -- Blocks --
    // Common start: public static final RegistryObject<Block>

    public static final RegistryObject<Block> HARDEND_BLOOD_ORE = BLOCKS.register("hardend_blood_ore", 
        () -> new Block(BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_RED)
        .strength(5.0f, 17f)
        .requiresCorrectToolForDrops()
    ));

    public static final RegistryObject<Block> HELL_ORE = BLOCKS.register("hell_ore", 
        () -> new Block(BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_ORANGE)
        .strength(1.0f, 0.5f)
        .requiresCorrectToolForDrops()
        .lightLevel(state -> 15)
        .sound(SoundType.GLASS)
    ));

    public static final RegistryObject<Block> STONE_TORCH = BLOCKS.register("stone_torch",
        () -> new WaterloggableTorchBlock(BlockBehaviour.Properties.of()
            .noOcclusion()
            .noCollission()
            .instabreak()
            .lightLevel(state -> 12)
            .sound(SoundType.STONE),
            ParticleTypes.FLAME)
    );

    public static final RegistryObject<Block> STONE_TORCH_WALL = BLOCKS.register("stone_torch_wall",
        () -> new WaterloggableWallTorchBlock(BlockBehaviour.Properties.of()
            .noOcclusion()
            .noCollission()
            .instabreak()
            .lightLevel(state -> 12)
            .sound(SoundType.STONE)
            .lootFrom(() -> STONE_TORCH.get()),
            ParticleTypes.FLAME)
    );

    public static final RegistryObject<Block> LIFE_TREE_SAPLING = BLOCKS.register("life_tree_sapling",
        () -> new LifeTreeSaplingBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.SOUL_SAND)
            .randomTicks()
    ));

    public static final RegistryObject<Block> LIFE_TREE_LOG = BLOCKS.register("life_tree_log", 
        () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.0f)
            .sound(SoundType.WOOD)
    ));

    public static final RegistryObject<Block> LIFE_TREE_FULL_LOG = BLOCKS.register("life_tree_full_log", 
        () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(4.0f)
            .sound(SoundType.WOOD)
    ));

    public static final RegistryObject<Block> LIFE_TREE_FULL_LEAVES = BLOCKS.register("life_tree_full_leaves", 
        () -> new LifeTreeLeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2f)
            .sound(SoundType.GRASS)
            .noOcclusion()
            .lightLevel(state -> 10)
    ));

    public static final RegistryObject<Block> LIFE_TREE_BASE = BLOCKS.register("life_tree_base", 
        () -> new LifeTreeBaseBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(4.0f)
            .sound(SoundType.WOOD)
    ));
}
