/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package natures.debris.common.blockold.plant;

import natures.debris.common.particle.MDParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

import java.util.Random;

public class GooDripsBlock extends SingleDirectionalPlantBlock {
    private static final VoxelShape SHAPE = makeHangPlantShape(15, 15);

    public GooDripsBlock(Properties properties) {
        super(properties, Direction.DOWN);
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        return SHAPE;
    }


    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (rand.nextInt(50) == 0) {
            double x = pos.getX() + rand.nextDouble();
            double y = pos.getY() + rand.nextDouble();
            double z = pos.getZ() + rand.nextDouble();

            world.addParticle(MDParticleTypes.GOO_HANGING, x, y, z, 0, 0, 0);
        }
    }
}