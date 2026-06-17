package com.ZRedCon.zrbloodmachine;


import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZRBloodMachine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockInit.STONE_TORCH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.STONE_TORCH_WALL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockInit.LIFE_TREE_SAPLING.get(), RenderType.cutout());
        });
    }
}