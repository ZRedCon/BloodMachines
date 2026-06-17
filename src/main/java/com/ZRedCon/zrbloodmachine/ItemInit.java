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

    // -- Other Items --

    public static final RegistryObject<Item> STONE_TORCH_ITEM = RegisterItem(TabType.BLOCK, "stone_torch", 
        () -> new StandingAndWallBlockItem(BlockInit.STONE_TORCH.get(), BlockInit.STONE_TORCH_WALL.get(), new Item.Properties(), Direction.DOWN)
    );

    // --

    public static RegistryObject<BlockItem> CreateItemForBlock(String name, RegistryObject<Block> reference) {
        return RegisterItem(TabType.BLOCK, name, 
            () -> new BlockItem(reference.get(), BLOCK_PROPERTIES)
        );
    }

    public static <T extends Item> RegistryObject<T> RegisterItem(TabType type, String name, Supplier<T> item) {
        return CreativeTabInit.addToTab(type, ITEMS.register(name, item));
    }
}
