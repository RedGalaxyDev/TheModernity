/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   02 - 01 - 2020
 * Author: rgsw
 */

package modernity.common.generator.blocks;

import modernity.common.block.plant.SingleDirectionalPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Random;

public class SinglePlantBlockGenerator implements IBlockGenerator {
    private final SingleDirectionalPlantBlock block;

    public SinglePlantBlockGenerator( SingleDirectionalPlantBlock block ) {
        this.block = block;
    }

    @Override
    public boolean generateBlock( IWorld world, BlockPos pos, Random rand ) {
        if( block.canGenerateAt( world, pos, world.getBlockState( pos ) ) ) {
            world.setBlockState( pos, block.computeStateForGeneration( world, pos, rand ), 2 );
            return true;
        }
        return false;
    }
}