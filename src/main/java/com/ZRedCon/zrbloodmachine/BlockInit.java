package com.ZRedCon.zrbloodmachine;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("null")
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
        .strength(3.0f, 2.0f)
        .requiresCorrectToolForDrops()
        .lightLevel(state -> 15)
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
}
