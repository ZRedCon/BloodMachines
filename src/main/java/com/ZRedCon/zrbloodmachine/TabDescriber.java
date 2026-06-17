package com.ZRedCon.zrbloodmachine;

import java.util.function.Supplier;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/*
 * Despite not being used, I have decided to preserve it since it's 
 * the first thing I wrote in Java for this mod that wasn't googled 
 * in any way
 */

public class TabDescriber {
    public String name;
    public MutableComponent title;
    public Supplier<ItemStack> icon;

    public TabDescriber(String name, Item displayIcon) {
        String titleString = "itemGroup." + name;

        this.name = name;
        this.title = Component.translatable(titleString);
        this.icon = () -> new ItemStack(displayIcon);
    }

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
