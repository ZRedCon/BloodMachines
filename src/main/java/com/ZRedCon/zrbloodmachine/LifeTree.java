package com.ZRedCon.zrbloodmachine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class LifeTree {
    private static final int SEARCH_RADIUS = 2;
    private static final int MAX_TREE_SIZE = 32;
    private static final Direction[] POSSIBLE_DIRECTIONS = { Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH };

    // Is this a log?
    public static boolean isTreeLog(BlockState state) {
        return state.is(BlockInit.LIFE_TREE_LOG.get())
            || state.is(BlockInit.LIFE_TREE_FULL_LOG.get());
    }

    // Try to find a log near this location
    public static BlockPos findNearbyLog(ServerLevel level, BlockPos origin) {
        for (BlockPos p : BlockPos.betweenClosed(
                origin.offset(-SEARCH_RADIUS, 0, -SEARCH_RADIUS),
                origin.offset(SEARCH_RADIUS, 1, SEARCH_RADIUS))) {

            if (isTreeLog(level.getBlockState(p))) {
                return p.immutable();
            }
        }
        return null;
    }

    // Get all the logs in a tree form the source
    public static Set<BlockPos> collectTree(ServerLevel level, BlockPos seed) {
        Set<BlockPos> found = new HashSet<>();
        Deque<BlockPos> toVisit = new ArrayDeque<>();

        found.add(seed.immutable());
        toVisit.add(seed.immutable());

        while (!toVisit.isEmpty() && found.size() < MAX_TREE_SIZE) {
            BlockPos current = toVisit.poll();

            for (Direction dir : POSSIBLE_DIRECTIONS) {
                BlockPos neighbor = current.relative(dir);
                if (!found.contains(neighbor) && isTreeLog(level.getBlockState(neighbor))) {
                    BlockPos immutable = neighbor.immutable();
                    found.add(immutable);
                    toVisit.add(immutable);
                }
            }
        }
        return found;
    }
}
