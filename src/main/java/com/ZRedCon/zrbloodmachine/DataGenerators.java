package com.ZRedCon.zrbloodmachine;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZRBloodMachine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // --- Client assets (models, blockstates) ---
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));

        // --- Server data (loot tables, recipes, tags) ---
        generator.addProvider(event.includeServer(), new LootTableProvider(output, java.util.Set.of(),
            java.util.List.of(new LootTableProvider.SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK)))
        );

        generator.addProvider(event.includeServer(), new ModRecipeProvider(output));

        var lookup = event.getLookupProvider();
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(output, lookup, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(),
            new ModItemTagsProvider(output, lookup, blockTags.contentsGetter(), existingFileHelper)
        );

    }
}
