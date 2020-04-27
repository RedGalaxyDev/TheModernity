/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   03 - 11 - 2020
 * Author: rgsw
 */

package modernity.common.generator.blocks;

import modernity.common.block.plant.BushBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Random;

public class SimpleBushBlockGenerator implements IBlockGenerator {
    private final BushBlock block;

    public SimpleBushBlockGenerator( BushBlock block ) {
        this.block = block;
    }


    @Override
    public boolean generateBlock( IWorld world, BlockPos pos, Random rand ) {
        BlockPos down = pos.down();
        if( world.isAirBlock( pos ) && block.isBlockSideSustainable( world.getBlockState( down ), world, down, Direction.UP ) ) {
            return world.setBlockState( pos, block.getDefaultState(), 2 );
        }
        return false;
    }
}