package com.ZRedCon.zrbloodmachine;

import java.util.function.Supplier;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class TabDescriber {
    public String name;
    public MutableComponent title;
    public Supplier<ItemStack> icon;

    @SuppressWarnings("null")
    public TabDescriber(String name, Item displayIcon) {
        String titleString = "itemGroup." + name;

        this.name = name;
        this.title = Component.translatable(titleString);
        this.icon = () -> new ItemStack(displayIcon);
    }

    @SuppressWarnings("null")
    public TabDescriber(String name, Supplier<? extends ItemLike> displayIcon) {
        String titleString = "itemGroup." + name;

        this.name = name;
        this.title = Component.translatable(titleString);
        this.icon = () -> {
            if (displayIcon == null || displayIcon.get() == null) {
                return ItemStack.EMPTY;
            }
            return new ItemStack(displayIcon.get());
        };
    }
}
