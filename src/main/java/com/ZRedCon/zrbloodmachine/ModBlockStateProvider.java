package com.ZRedCon.zrbloodmachine;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ZRBloodMachine.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        woodSet("life_tree");
        woodSet("life_tree_full");
    }

    private void woodSet(String p) {
        ResourceLocation logSide = modLoc("block/" + p + "_log_side");
        ResourceLocation strSide = modLoc("block/" + p + "_stripped_log_side");
        ResourceLocation strEnd  = modLoc("block/" + p + "_stripped_log_top");
        ResourceLocation planks  = modLoc("block/" + p + "_planks");
        ResourceLocation doorBot = modLoc("block/" + p + "_door_bottom");
        ResourceLocation doorTop = modLoc("block/" + p + "_door_top");
        ResourceLocation trapTex = modLoc("block/" + p + "_trapdoor");

        // Wood & stripped (pillars)
        pillar(p + "_wood", logSide, logSide);            // bark on all faces
        pillar(p + "_stripped_log", strSide, strEnd);
        pillar(p + "_stripped_wood", strSide, strSide);

        // Planks (full cube; this helper does blockstate + block model + item model)
        Block planksBlock = reg(p + "_planks");
        simpleBlockWithItem(planksBlock, cubeAll(planksBlock));

        // Stairs
        stairsBlock((StairBlock) reg(p + "_stairs"), planks);
        itemModels().withExistingParent(p + "_stairs", modLoc("block/" + p + "_stairs"));

        // Slab (double-slab model = the full planks block)
        slabBlock((SlabBlock) reg(p + "_slab"), modLoc("block/" + p + "_planks"), planks);
        itemModels().withExistingParent(p + "_slab", modLoc("block/" + p + "_slab"));

        // Fence (+ inventory model for the item)
        fenceBlock((FenceBlock) reg(p + "_fence"), planks);
        models().fenceInventory(p + "_fence_inventory", planks);
        itemModels().withExistingParent(p + "_fence", modLoc("block/" + p + "_fence_inventory"));

        // Fence gate
        fenceGateBlock((FenceGateBlock) reg(p + "_fence_gate"), planks);
        itemModels().withExistingParent(p + "_fence_gate", modLoc("block/" + p + "_fence_gate"));

        // Door (cutout); item is a flat sprite
        doorBlockWithRenderType((DoorBlock) reg(p + "_door"), doorBot, doorTop, "cutout");
        itemModels().withExistingParent(p + "_door", mcLoc("item/generated")).texture("layer0", modLoc("item/" + p + "_door"));

        // Trapdoor (cutout, orientable)
        trapdoorBlockWithRenderType((TrapDoorBlock) reg(p + "_trapdoor"), trapTex, true, "cutout");
        itemModels().withExistingParent(p + "_trapdoor", modLoc("block/" + p + "_trapdoor_bottom"));

        // Pressure plate
        pressurePlateBlock((PressurePlateBlock) reg(p + "_pressure_plate"), planks);
        itemModels().withExistingParent(p + "_pressure_plate", modLoc("block/" + p + "_pressure_plate"));

        // Button (+ inventory model for the item)
        buttonBlock((ButtonBlock) reg(p + "_button"), planks);
        models().buttonInventory(p + "_button_inventory", planks);
        itemModels().withExistingParent(p + "_button", modLoc("block/" + p + "_button_inventory"));
    }

    private void pillar(String name, ResourceLocation side, ResourceLocation end) {
        axisBlock((RotatedPillarBlock) reg(name), side, end);
        itemModels().withExistingParent(name, modLoc("block/" + name));
    }

    private static Block reg(String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ZRBloodMachine.MOD_ID, name));
    }
}
