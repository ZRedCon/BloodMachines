package com.ZRedCon.zrbloodmachine;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class BloodDripParticle extends TextureSheetParticle {

    private int hangTime;

    protected BloodDripParticle(ClientLevel level, double x, double y, double z,
                                double dx, double dy, double dz, SpriteSet sprites) {
        super(level, x, y, z);
        this.setSize(0.01F, 0.01F);
        this.setColor(0.65F, 0.02F, 0.02F);
        this.lifetime = 60;
        this.pickSprite(sprites);

        boolean launched = (dx != 0.0 || dy != 0.0 || dz != 0.0);
        if (launched) {
            // Burst out of the wood: keep the launch velocity, fall under gravity, no hang.
            this.xd = dx;
            this.yd = dy;
            this.zd = dz;
            this.gravity = 0.06F;
            this.hangTime = 0;
        } else {
            // Drip: hang under the leaf, then fall (unchanged behavior).
            this.xd = 0.0D;
            this.yd = 0.0D;
            this.zd = 0.0D;
            this.gravity = 0.0F;
            this.hangTime = 20 + this.random.nextInt(20);
        }
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        if (this.hangTime-- > 0) {
            this.gravity = 0.0F;
        } else {
            this.gravity += 0.06F;
        }
        super.tick();
        if (this.onGround) {
            // this.remove();
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                double x, double y, double z, double dx, double dy, double dz) {
            return new BloodDripParticle(level, x, y, z, dx, dy, dz, this.sprites);
        }
    }
}
