package com.ZRedCon.zrbloodmachine;

import java.util.function.Supplier;

import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("all")
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ZRBloodMachine.MOD_ID);

    public static final RegistryObject<BlockItem> HARDEND_BLOOD_ORE_ITEM = RegisterItem(TabType.BLOCK, "hardend_blood_ore",
        () -> new BlockItem(BlockInit.HARDEND_BLOOD_ORE.get(), new Item.Properties().rarity(Rarity.UNCOMMON))
    );

    public static final RegistryObject<BlockItem> HELL_ORE_ITEM = RegisterItem(TabType.BLOCK, "hell_ore",
        () -> new BlockItem(BlockInit.HELL_ORE.get(), new Item.Properties().rarity(Rarity.UNCOMMON))
    );

    public static final RegistryObject<Item> STONE_TORCH_ITEM = RegisterItem(TabType.BLOCK, "stone_torch", 
        () -> new StandingAndWallBlockItem(BlockInit.STONE_TORCH.get(), BlockInit.STONE_TORCH_WALL.get(), new Item.Properties(), Direction.DOWN)
    );

    public static <T extends Item> RegistryObject<T> RegisterItem(TabType type, String name, Supplier<T> item) {
        return CreativeTabInit.addToTab(type, ITEMS.register(name, item));
    }
}
