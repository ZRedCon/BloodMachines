package com.ZRedCon.zrbloodmachine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class HellOreBlock extends Block {

    public HellOreBlock(Properties props) {
        super(props);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        ItemStack tool = player.getMainHandItem();
        boolean hasSilkTouch = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0;

        if (!level.isClientSide && !hasSilkTouch && !player.isCreative()) {
            explode(level, pos);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        if (!level.isClientSide) {
            explode(level, pos);
        }
    }

    private void explode(Level level, BlockPos pos) {
        level.explode(
            null,
            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
            2.0f,
            Level.ExplosionInteraction.BLOCK
        );
    }
}
