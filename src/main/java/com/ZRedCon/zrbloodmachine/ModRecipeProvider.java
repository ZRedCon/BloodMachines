package com.ZRedCon.zrbloodmachine;

import java.util.function.Consumer;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        woodRecipes(writer, "life_tree", BlockInit.LIFE_TREE_LOG.get());
        woodRecipes(writer, "life_tree_full", BlockInit.LIFE_TREE_FULL_LOG.get());
    }

    private void woodRecipes(Consumer<FinishedRecipe> writer, String p, Block log) {
        Block wood         = reg(p + "_wood");
        Block strippedLog  = reg(p + "_stripped_log");
        Block strippedWood = reg(p + "_stripped_wood");
        Block planks       = reg(p + "_planks");

        // 4 planks from any of the log-likes (no tag needed: Ingredient.of accepts several)
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
            .requires(Ingredient.of(log, wood, strippedLog, strippedWood))
            .group("life_tree_planks")
            .unlockedBy("has_log", has(log))
            .save(writer, new ResourceLocation(ZRBloodMachine.MOD_ID, p + "_planks"));

        // 3 wood / stripped wood from 4 of the matching log (2x2)
        woodFromLogs(writer, wood, log);
        woodFromLogs(writer, strippedWood, strippedLog);

        // Everything else from planks
        stairBuilder(reg(p + "_stairs"), Ingredient.of(planks)).unlockedBy("has_planks", has(planks)).save(writer);
        slab(writer, RecipeCategory.BUILDING_BLOCKS, reg(p + "_slab"), planks);
        fenceBuilder(reg(p + "_fence"), Ingredient.of(planks)).unlockedBy("has_planks", has(planks)).save(writer);
        fenceGateBuilder(reg(p + "_fence_gate"), Ingredient.of(planks)).unlockedBy("has_planks", has(planks)).save(writer);
        doorBuilder(reg(p + "_door"), Ingredient.of(planks)).unlockedBy("has_planks", has(planks)).save(writer);
        trapdoorBuilder(reg(p + "_trapdoor"), Ingredient.of(planks)).unlockedBy("has_planks", has(planks)).save(writer);
        pressurePlate(writer, reg(p + "_pressure_plate"), planks);
        buttonBuilder(reg(p + "_button"), Ingredient.of(planks)).unlockedBy("has_planks", has(planks)).save(writer);
    }

    private static Block reg(String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ZRBloodMachine.MOD_ID, name));
    }
}
