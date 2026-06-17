package com.ZRedCon.zrbloodmachine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ZRBloodMachine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabInit{
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ZRBloodMachine.MOD_ID);

    public static final List<Supplier<? extends  ItemLike>> BLOCK_TAB_ITEMS = new ArrayList<>();
    public static final List<Supplier<? extends  ItemLike>> ITEM_TAB_ITEMS = new ArrayList<>();

    // --- Tabs ---
    
    public static final RegistryObject<CreativeModeTab> BLOCK_TAB = CreateTab("block_tab", () -> new ItemStack(ItemInit.HARDEND_BLOOD_ORE_ITEM.get()), BLOCK_TAB_ITEMS);
    //public static final RegistryObject<CreativeModeTab> ITEM_TAB = CreateTab("item_tab", Items.SKELETON_SKULL, Items.SKELETON_SKULL);

    // ---

    public static RegistryObject<CreativeModeTab> CreateTab(String name, Supplier<ItemStack> icon, List<Supplier<? extends  ItemLike>> itemList) {
        return TABS.register(name,
            () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + name))
                .icon(icon)
                .displayItems((displayParams, output) -> {
                    itemList.forEach(item -> output.accept(item.get()));
                })
                .build()
        );
    }

    public static <T extends Item> RegistryObject<T> addToTab(TabType type, RegistryObject<T> itemLike)
    {
        var list = switch (type)
        {
            case BLOCK -> BLOCK_TAB_ITEMS;
            case ITEM -> ITEM_TAB_ITEMS;
        };

        list.add(itemLike);
        return itemLike;
    }
}
