package com.ZRedCon.zrbloodmachine;

import java.util.function.Supplier;

import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("all")
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ZRBloodMachine.MOD_ID);

    public static final Properties BLOCK_PROPERTIES = new Item.Properties().rarity(Rarity.UNCOMMON);

    // -- Block Items --

    public static final RegistryObject<BlockItem> HARDEND_BLOOD_ORE_ITEM = CreateItemForBlock("hardend_blood_ore", BlockInit.HARDEND_BLOOD_ORE);
    public static final RegistryObject<BlockItem> HELL_ORE_ITEM = CreateItemForBlock("hell_ore", BlockInit.HELL_ORE);
    public static final RegistryObject<BlockItem> LIFE_TREE_SAPLING_ITEM = CreateItemForBlock("life_tree_sapling", BlockInit.LIFE_TREE_SAPLING );
    public static final RegistryObject<BlockItem> LIFE_TREE_LOG_ITEM = CreateItemForBlock("life_tree_log", BlockInit.LIFE_TREE_LOG);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_LOG_ITEM = CreateItemForBlock("life_tree_full_log", BlockInit.LIFE_TREE_FULL_LOG);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_LEAVES_ITEM = CreateItemForBlock("life_tree_full_leaves", BlockInit.LIFE_TREE_FULL_LEAVES);
    public static final RegistryObject<BlockItem> LIFE_TREE_WOOD_ITEM = CreateItemForBlock("life_tree_wood", BlockInit.LIFE_TREE_WOOD);
    public static final RegistryObject<BlockItem> LIFE_TREE_STRIPPED_LOG_ITEM = CreateItemForBlock("life_tree_stripped_log", BlockInit.LIFE_TREE_STRIPPED_LOG);
    public static final RegistryObject<BlockItem> LIFE_TREE_STRIPPED_WOOD_ITEM = CreateItemForBlock("life_tree_stripped_wood", BlockInit.LIFE_TREE_STRIPPED_WOOD);
    public static final RegistryObject<BlockItem> LIFE_TREE_PLANKS_ITEM = CreateItemForBlock("life_tree_planks", BlockInit.LIFE_TREE_PLANKS);
    public static final RegistryObject<BlockItem> LIFE_TREE_STAIRS_ITEM = CreateItemForBlock("life_tree_stairs", BlockInit.LIFE_TREE_STAIRS);
    public static final RegistryObject<BlockItem> LIFE_TREE_SLAB_ITEM = CreateItemForBlock("life_tree_slab", BlockInit.LIFE_TREE_SLAB);
    public static final RegistryObject<BlockItem> LIFE_TREE_FENCE_ITEM = CreateItemForBlock("life_tree_fence", BlockInit.LIFE_TREE_FENCE);
    public static final RegistryObject<BlockItem> LIFE_TREE_FENCE_GATE_ITEM = CreateItemForBlock("life_tree_fence_gate", BlockInit.LIFE_TREE_FENCE_GATE);
    public static final RegistryObject<BlockItem> LIFE_TREE_DOOR_ITEM = CreateItemForBlock("life_tree_door", BlockInit.LIFE_TREE_DOOR);
    public static final RegistryObject<BlockItem> LIFE_TREE_TRAPDOOR_ITEM = CreateItemForBlock("life_tree_trapdoor", BlockInit.LIFE_TREE_TRAPDOOR);
    public static final RegistryObject<BlockItem> LIFE_TREE_PRESSURE_PLATE_ITEM = CreateItemForBlock("life_tree_pressure_plate", BlockInit.LIFE_TREE_PRESSURE_PLATE);
    public static final RegistryObject<BlockItem> LIFE_TREE_BUTTON_ITEM = CreateItemForBlock("life_tree_button", BlockInit.LIFE_TREE_BUTTON);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_WOOD_ITEM = CreateItemForBlock("life_tree_full_wood", BlockInit.LIFE_TREE_FULL_WOOD);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_STRIPPED_LOG_ITEM = CreateItemForBlock("life_tree_full_stripped_log", BlockInit.LIFE_TREE_FULL_STRIPPED_LOG);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_STRIPPED_WOOD_ITEM = CreateItemForBlock("life_tree_full_stripped_wood", BlockInit.LIFE_TREE_FULL_STRIPPED_WOOD);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_PLANKS_ITEM = CreateItemForBlock("life_tree_full_planks", BlockInit.LIFE_TREE_FULL_PLANKS);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_STAIRS_ITEM = CreateItemForBlock("life_tree_full_stairs", BlockInit.LIFE_TREE_FULL_STAIRS);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_SLAB_ITEM = CreateItemForBlock("life_tree_full_slab", BlockInit.LIFE_TREE_FULL_SLAB);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_FENCE_ITEM = CreateItemForBlock("life_tree_full_fence", BlockInit.LIFE_TREE_FULL_FENCE);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_FENCE_GATE_ITEM = CreateItemForBlock("life_tree_full_fence_gate", BlockInit.LIFE_TREE_FULL_FENCE_GATE);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_DOOR_ITEM = CreateItemForBlock("life_tree_full_door", BlockInit.LIFE_TREE_FULL_DOOR);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_TRAPDOOR_ITEM = CreateItemForBlock("life_tree_full_trapdoor", BlockInit.LIFE_TREE_FULL_TRAPDOOR);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_PRESSURE_PLATE_ITEM = CreateItemForBlock("life_tree_full_pressure_plate", BlockInit.LIFE_TREE_FULL_PRESSURE_PLATE);
    public static final RegistryObject<BlockItem> LIFE_TREE_FULL_BUTTON_ITEM = CreateItemForBlock("life_tree_full_button", BlockInit.LIFE_TREE_FULL_BUTTON);

    // -- Other Items --

    public static final RegistryObject<Item> STONE_TORCH_ITEM = RegisterItem(TabType.BLOCK, "stone_torch", 
        () -> new StandingAndWallBlockItem(BlockInit.STONE_TORCH.get(), BlockInit.STONE_TORCH_WALL.get(), new Item.Properties(), Direction.DOWN)
    );

    // --

    public static RegistryObject<BlockItem> CreateItemForBlock(String name, RegistryObject<? extends Block> reference) {
        return RegisterItem(TabType.BLOCK, name, 
            () -> new BlockItem(reference.get(), BLOCK_PROPERTIES)
        );
    }

    public static <T extends Item> RegistryObject<T> RegisterItem(TabType type, String name, Supplier<T> item) {
        return CreativeTabInit.addToTab(type, ITEMS.register(name, item));
    }
}
