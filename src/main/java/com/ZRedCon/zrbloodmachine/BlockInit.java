package com.ZRedCon.zrbloodmachine;

import java.util.List;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ZRBloodMachine.MOD_ID);
    public static final BlockSetType LIFE_TREE_SET_TYPE = BlockSetType.register(new BlockSetType(ZRBloodMachine.MOD_ID + ":life_tree"));
    public static final WoodType LIFE_TREE_WOOD_TYPE = WoodType.register(new WoodType(ZRBloodMachine.MOD_ID + ":life_tree", LIFE_TREE_SET_TYPE));

        // -- Wood-variant helpers --
    private static BlockBehaviour.Properties woodProps(float hardness, float resistance) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(hardness, resistance).sound(SoundType.WOOD);
    }

    private static RegistryObject<Block> pillar(String name) {
        return BLOCKS.register(name, () -> new RotatedPillarBlock(woodProps(2.0f, 2.0f)));
    }
    private static RegistryObject<Block> cube(String name) {
        return BLOCKS.register(name, () -> new Block(woodProps(2.0f, 3.0f)));
    }
    private static RegistryObject<StairBlock> stairs(String name, RegistryObject<Block> base) {
        return BLOCKS.register(name, () -> new StairBlock(() -> base.get().defaultBlockState(), woodProps(2.0f, 3.0f)));
    }
    private static RegistryObject<SlabBlock> slab(String name) {
        return BLOCKS.register(name, () -> new SlabBlock(woodProps(2.0f, 3.0f)));
    }
    private static RegistryObject<FenceBlock> fence(String name) {
        return BLOCKS.register(name, () -> new FenceBlock(woodProps(2.0f, 3.0f)));
    }
    private static RegistryObject<FenceGateBlock> fenceGate(String name) {
        return BLOCKS.register(name, () -> new FenceGateBlock(woodProps(2.0f, 3.0f), LIFE_TREE_WOOD_TYPE));
    }
    private static RegistryObject<DoorBlock> door(String name) {
        return BLOCKS.register(name, () -> new DoorBlock(woodProps(3.0f, 3.0f).noOcclusion(), LIFE_TREE_SET_TYPE));
    }
    private static RegistryObject<TrapDoorBlock> trapdoor(String name) {
        return BLOCKS.register(name, () -> new TrapDoorBlock(woodProps(3.0f, 3.0f).noOcclusion(), LIFE_TREE_SET_TYPE));
    }
    private static RegistryObject<PressurePlateBlock> pressurePlate(String name) {
        return BLOCKS.register(name, () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
            woodProps(0.5f, 0.5f).noCollission(), LIFE_TREE_SET_TYPE));
    }
    private static RegistryObject<ButtonBlock> button(String name) {
        return BLOCKS.register(name, () -> new ButtonBlock(
            BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD),
            LIFE_TREE_SET_TYPE, 30, true));
    }

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

    //#region Life tree assets

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

    public static final RegistryObject<Block> LIFE_TREE_WOOD = pillar("life_tree_wood");
    public static final RegistryObject<Block> LIFE_TREE_STRIPPED_LOG = pillar("life_tree_stripped_log");
    public static final RegistryObject<Block> LIFE_TREE_STRIPPED_WOOD = pillar("life_tree_stripped_wood");
    public static final RegistryObject<Block> LIFE_TREE_PLANKS = cube("life_tree_planks");
    public static final RegistryObject<StairBlock> LIFE_TREE_STAIRS = stairs("life_tree_stairs", LIFE_TREE_PLANKS);
    public static final RegistryObject<SlabBlock> LIFE_TREE_SLAB = slab("life_tree_slab");
    public static final RegistryObject<FenceBlock> LIFE_TREE_FENCE = fence("life_tree_fence");
    public static final RegistryObject<FenceGateBlock> LIFE_TREE_FENCE_GATE = fenceGate("life_tree_fence_gate");
    public static final RegistryObject<DoorBlock> LIFE_TREE_DOOR = door("life_tree_door");
    public static final RegistryObject<TrapDoorBlock> LIFE_TREE_TRAPDOOR = trapdoor("life_tree_trapdoor");
    public static final RegistryObject<PressurePlateBlock> LIFE_TREE_PRESSURE_PLATE = pressurePlate("life_tree_pressure_plate");
    public static final RegistryObject<ButtonBlock> LIFE_TREE_BUTTON = button("life_tree_button");
    public static final RegistryObject<Block> LIFE_TREE_FULL_WOOD = pillar("life_tree_full_wood");
    public static final RegistryObject<Block> LIFE_TREE_FULL_STRIPPED_LOG = pillar("life_tree_full_stripped_log");
    public static final RegistryObject<Block> LIFE_TREE_FULL_STRIPPED_WOOD = pillar("life_tree_full_stripped_wood");
    public static final RegistryObject<Block> LIFE_TREE_FULL_PLANKS = cube("life_tree_full_planks");
    public static final RegistryObject<StairBlock> LIFE_TREE_FULL_STAIRS = stairs("life_tree_full_stairs", LIFE_TREE_FULL_PLANKS);
    public static final RegistryObject<SlabBlock> LIFE_TREE_FULL_SLAB = slab("life_tree_full_slab");
    public static final RegistryObject<FenceBlock> LIFE_TREE_FULL_FENCE = fence("life_tree_full_fence");
    public static final RegistryObject<FenceGateBlock> LIFE_TREE_FULL_FENCE_GATE = fenceGate("life_tree_full_fence_gate");
    public static final RegistryObject<DoorBlock> LIFE_TREE_FULL_DOOR = door("life_tree_full_door");
    public static final RegistryObject<TrapDoorBlock> LIFE_TREE_FULL_TRAPDOOR = trapdoor("life_tree_full_trapdoor");
    public static final RegistryObject<PressurePlateBlock> LIFE_TREE_FULL_PRESSURE_PLATE = pressurePlate("life_tree_full_pressure_plate");
    public static final RegistryObject<ButtonBlock> LIFE_TREE_FULL_BUTTON = button("life_tree_full_button");

    public static final List<RegistryObject<? extends Block>> WOOD_VARIANTS = List.of(
        LIFE_TREE_WOOD, LIFE_TREE_STRIPPED_LOG, LIFE_TREE_STRIPPED_WOOD, LIFE_TREE_PLANKS,
        LIFE_TREE_STAIRS, LIFE_TREE_SLAB, LIFE_TREE_FENCE, LIFE_TREE_FENCE_GATE,
        LIFE_TREE_DOOR, LIFE_TREE_TRAPDOOR, LIFE_TREE_PRESSURE_PLATE, LIFE_TREE_BUTTON,
        LIFE_TREE_FULL_WOOD, LIFE_TREE_FULL_STRIPPED_LOG, LIFE_TREE_FULL_STRIPPED_WOOD, LIFE_TREE_FULL_PLANKS,
        LIFE_TREE_FULL_STAIRS, LIFE_TREE_FULL_SLAB, LIFE_TREE_FULL_FENCE, LIFE_TREE_FULL_FENCE_GATE,
        LIFE_TREE_FULL_DOOR, LIFE_TREE_FULL_TRAPDOOR, LIFE_TREE_FULL_PRESSURE_PLATE, LIFE_TREE_FULL_BUTTON
    );


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

    //#endregion
}
