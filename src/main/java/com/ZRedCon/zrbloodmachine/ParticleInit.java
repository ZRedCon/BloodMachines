package com.ZRedCon.zrbloodmachine;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ZRBloodMachine.MOD_ID);

    public static final RegistryObject<SimpleParticleType> BLOOD_DRIP = PARTICLE_TYPES.register("blood_drip", 
        () -> new SimpleParticleType(false)
    );
}
