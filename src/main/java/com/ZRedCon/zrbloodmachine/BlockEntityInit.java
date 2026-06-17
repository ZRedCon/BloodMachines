package com.ZRedCon.zrbloodmachine;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ZRBloodMachine.MOD_ID);

    public static final RegistryObject<BlockEntityType<LifeTreeBaseBlockEntity>> LIFE_TREE_BASE =
        BLOCK_ENTITIES.register("life_tree_base",
            () -> BlockEntityType.Builder.of(LifeTreeBaseBlockEntity::new, BlockInit.LIFE_TREE_BASE.get()).build(null)
        );
}
