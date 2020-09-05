/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package natures.debris.common.generator.decorate.decoration;

import natures.debris.common.generator.blocks.IBlockGenerator;
import natures.debris.generic.util.MovingBlockPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

import java.util.Random;

public class ClusterBushDecoration implements IDecoration {
    private final int iterations;
    private final int radius;
    private final IBlockGenerator gen;

    public ClusterBushDecoration(int iterations, int radius, IBlockGenerator gen) {
        this.iterations = iterations;
        this.radius = radius;
        this.gen = gen;
    }

    public int getIterations() {
        return iterations;
    }

    public int getRadius() {
        return radius;
    }

    public IBlockGenerator getGen() {
        return gen;
    }

    @Override
    public void generate(IWorld world, BlockPos pos, Random rand, ChunkGenerator<?> chunkGenerator) {
        int r2 = radius * 2 + 1;
        MovingBlockPos rpos = new MovingBlockPos();
        for (int i = 0; i < iterations; i++) {
            int rx = rand.nextInt(r2) + pos.getX() - radius;
            int ry = rand.nextInt(r2) + pos.getY() - radius;
            int rz = rand.nextInt(r2) + pos.getZ() - radius;

            rpos.setPos(rx, ry, rz);

            gen.generateBlock(world, rpos, rand);
        }
    }
}