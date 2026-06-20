package com.ZRedCon.zrbloodmachine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;

@SuppressWarnings("removal")
public class LifeTreeBaseBlockEntity extends BlockEntity {
    private static final Direction[] FEED_DIRECTIONS = { Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH };

    // The logs that make up this tree (NOT including the base itself).
    private final List<BlockPos> logPositions = new ArrayList<>();
    private int heightToIntersection = 0;
    private int logsFed;

    public LifeTreeBaseBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.LIFE_TREE_BASE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        long[] packed = new long[this.logPositions.size() + 1];
        for (int i = 0; i < packed.length - 1; i++) {
            packed[i] = this.logPositions.get(i).asLong();
        }
        packed[ packed.length - 1 ] = (((long)heightToIntersection << 32) | (logsFed & 0xFFFFFFFFL));
        tag.putLongArray("LogPositions", packed);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.logPositions.clear();
        long[] packets = tag.getLongArray("LogPositions");
        for (long packed : packets) {
            this.logPositions.add(BlockPos.of(packed));
        }
        this.logPositions.remove(this.logPositions.size() - 1);
        long intsStored = packets[ packets.length - 1 ];
        heightToIntersection = (int) (intsStored >> 32);
        logsFed = (int) intsStored;
    }

    // - Helper functions -
    private static boolean IsBase(BlockState state) { return state.is(BlockInit.LIFE_TREE_BASE.get()); }
    private static boolean IsFull(BlockState state) { return state.is(BlockInit.LIFE_TREE_FULL_LOG.get()); }
    private static boolean IsBaseAndFull(BlockState state) { return IsBase(state) && state.getValue(LifeTreeBaseBlock.FED); }
    private static boolean IsLog(BlockState state) { return state.is(BlockInit.LIFE_TREE_LOG.get()); }
    private boolean isBaseFed(ServerLevel level) { return IsBaseAndFull(level.getBlockState(this.getBlockPos())); }
    private boolean isComplete(ServerLevel level) { return logsFed == logPositions.size(); }
    private boolean hasPosition(BlockPos pos) { return logPositions.contains(pos); }
    private float randOffset(RandomSource rand) { return rand.nextFloat() - 0.5f; }

    // F E E D  I T .
    public void feed(ServerLevel level, RandomSource random, ServerPlayer killer) {
        // Base case
        if (!isBaseFed(level)) {
            var baseState = level.getBlockState(this.getBlockPos());
            if (IsBase(baseState)) {
                level.setBlock(this.getBlockPos(), baseState.setValue(LifeTreeBaseBlock.FED, true), 3);
                spawnParticlesAroundBlock(level, 30, this.getBlockPos());
            }
            return;
        }

        // Check for holes
        for (int i = 0; i < (logsFed <= heightToIntersection ? logsFed : heightToIntersection); i++) {
            if (!IsFull(level.getBlockState(this.logPositions.get(i)))) {
                //ChatOutput.debugOut(level, "Possible hole found, escaping procedure.");
                return;
            }
        }
        
        // Do shaft feeding
        if (logsFed <= heightToIntersection) {
            BlockPos target = this.logPositions.get(logsFed);
            convertToFullLog(level, target);
            return;
        }

        // Do crossroads feeding
        int numTargetsFound = 0;
        BlockPos[] possibleTargets = { null, null, null };  // Something has gone terribly wrong if we are in a situation where more than 3 possible logs can be fed
        for (int i = heightToIntersection; i < this.logPositions.size(); i++) {
            var p = this.logPositions.get(i);
            if (!hasAFedNeighbor(level, p)) continue;       // Skip blocks without neighboring filled
            if (!IsLog(level.getBlockState(p))) continue;   // Skip holes
            if (numTargetsFound >= 3) {
                ChatOutput.debugOut(level, "   !WARNING! MANY TARGETS: " + numTargetsFound + "|" + p + "|" + possibleTargets[0] + "|" + possibleTargets[1] + "|" + possibleTargets[2]);
                level.setBlock(p, Blocks.GOLD_BLOCK.defaultBlockState(), 3);
                level.setBlock(possibleTargets[0], Blocks.GOLD_BLOCK.defaultBlockState(), 3);
                level.setBlock(possibleTargets[1], Blocks.GOLD_BLOCK.defaultBlockState(), 3);
                level.setBlock(possibleTargets[2], Blocks.GOLD_BLOCK.defaultBlockState(), 3);
                return;
            }
            possibleTargets[ numTargetsFound++ ] = p;       // Any block with a fed neighbor can be added
        }

        // Return a random valid target
        if (numTargetsFound == 0) {
            return;
        }
        convertToFullLog( level, possibleTargets[ random.nextInt(numTargetsFound) ] );

        if (isComplete(level)) {
            var endPoints = GetEndPoints(level, logPositions.get(heightToIntersection));
            BuildCanopy(level, endPoints);
            level.playSound(null, endPoints[1], SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.BLOCKS, 1.0F, 1.0F);
            grantCompletionAdvancement(level, killer);
        }
    }    

    // Check if the log has a fed neighbor in the tree
    private boolean hasAFedNeighbor(ServerLevel level, BlockPos pos) {
        for (Direction dir : FEED_DIRECTIONS) {
            BlockPos neighbor = pos.relative(dir);
            var s = level.getBlockState(neighbor);
            if ((IsFull(s) || IsBaseAndFull(s)) && hasPosition(neighbor)) return true;
        }
        return false;
    }

    // Convert an empty log into a fed one
    private void convertToFullLog(ServerLevel level, BlockPos pos) {
        var axis = level.getBlockState(pos).getValue(RotatedPillarBlock.AXIS);
        level.setBlock(pos, BlockInit.LIFE_TREE_FULL_LOG.get().defaultBlockState()
            .setValue(RotatedPillarBlock.AXIS, axis), 3);
        
        spawnParticlesAroundBlock(level, 30, pos);
        level.playSound(null, pos, SoundEvents.SLIME_SQUISH_SMALL, SoundSource.BLOCKS, 1.0f, 1.0f);
        logsFed++;
    }

    // Spawn particles around a block
    private void spawnParticlesAroundBlock(ServerLevel level, int countPerFace, BlockPos pos) {
        double cx = pos.getX() + 0.5, cy = pos.getY() + 0.5, cz = pos.getZ() + 0.5;

        for (Direction dir : Direction.values()) {
            if (!level.getBlockState(pos.relative(dir)).canBeReplaced()) continue;
            spewFromFace(level, countPerFace, cx, cy, cz, dir.getStepX(), dir.getStepY(), dir.getStepZ());
        }
    }

    // Shoots particles from a face
    private void spewFromFace(ServerLevel level, int count, double cx, double cy, double cz, int nx, int ny, int nz) {
        RandomSource rand = level.getRandom();
        double px, py, pz, vx, vy, vz, outward = 0.1; // how hard the blood flies out

        for (int i = 0; i < count; i++) {
            px = cx + nx * 0.5 + (nx == 0 ? randOffset(rand) * 2f : 0);
            py = cy + ny * 0.5 + (ny == 0 ? randOffset(rand) * 2f : 0);
            pz = cz + nz * 0.5 + (nz == 0 ? randOffset(rand) * 2f : 0);
            vx = nx * outward + randOffset(rand) * 0.1;
            vy = ny * outward + randOffset(rand) * 0.1;
            vz = nz * outward + randOffset(rand) * 0.1;

            level.sendParticles(ParticleInit.BLOOD_DRIP.get(), px, py, pz, 0, vx, vy, vz, 1.0);
        }
    }

    // Setup the base of the tree with the data it needs
    public void setStructure(List<BlockPos> positions, int height) {
        this.logPositions.clear();
        this.logPositions.addAll(positions);
        this.heightToIntersection = height;
        this.setChanged();
    }

    // Gets the end points of the tree; where leaves start showing up
    private BlockPos[] GetEndPoints(ServerLevel level, BlockPos start) {
        BlockPos[] endPoints = { start, start, start };
        while (hasPosition(endPoints[0])) endPoints[0] = endPoints[0].relative(Direction.SOUTH);
        while (hasPosition(endPoints[1])) endPoints[1] = endPoints[1].relative(Direction.UP);
        while (hasPosition(endPoints[2])) endPoints[2] = endPoints[2].relative(Direction.NORTH);
        return endPoints;
    }

    // Builds the canopy of leaves
    private void BuildCanopy(ServerLevel level, BlockPos[] endPoints) {
        // Build arms
        BlockPos south = endPoints[0], north = endPoints[2];
        replaceWithLeaves(level, south.relative(Direction.UP));
        replaceWithLeaves(level, north.relative(Direction.UP));
        
        // Build hat
        int centerX = endPoints[1].getX(), centerY = endPoints[1].getY(), centerZ = endPoints[1].getZ();
        int r = south.relative(Direction.SOUTH).getZ() - centerZ;
        int rSq = r * r, rHf = r/3;
        for (int dy = -rHf; dy <= rHf; dy++) {
            for (int dz = -r; dz <= r; dz++) {
                for (int dx = -r; dx <= r; dx++) {
                    if (dy * dy + dz * dz + dx * dx > rSq) continue;
                    BlockPos leafPos = new BlockPos(centerX + dx, centerY + dy, centerZ + dz);
                    replaceWithLeaves(level, leafPos);
                }
            }
        }

    }

    // Places leaves at the given point if possible
    private void replaceWithLeaves(ServerLevel level, BlockPos pos) {
        if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, BlockInit.LIFE_TREE_FULL_LEAVES.get().defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 3);
        }
    }

    private void grantCompletionAdvancement(ServerLevel level, ServerPlayer killer) {
        ServerPlayer recipient = killer;

        // No player killer (environmental/mob death) -> credit the nearest player.
        if (recipient == null) {
            BlockPos p = this.getBlockPos();
            Player nearest = level.getNearestPlayer(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5, 16.0, false);
            if (nearest instanceof ServerPlayer sp) {
                recipient = sp;
            }
        }

        if (recipient == null) return;

        Advancement advancement = level.getServer().getAdvancements()
            .getAdvancement(new ResourceLocation(ZRBloodMachine.MOD_ID, "complete_life_tree"));
        if (advancement != null) {
            recipient.getAdvancements().award(advancement, "code_triggered");
        }
    }


}