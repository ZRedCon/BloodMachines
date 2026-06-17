package com.ZRedCon.zrbloodmachine;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod("zrbloodmachine")
public class ZRBloodMachine {

    public static final String MOD_ID = "zrbloodmachine";

    public ZRBloodMachine(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        CreativeTabInit.TABS.register(bus);
        
        ParticleInit.PARTICLE_TYPES.register(bus);
    }
}
