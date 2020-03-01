/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   03 - 01 - 2020
 * Author: rgsw
 */

package modernity.common.block.plant.growing;

import modernity.api.util.MovingBlockPos;
import modernity.common.block.MDBlocks;
import modernity.common.block.farmland.IFarmland;
import modernity.common.block.plant.MurkReedBlock;
import modernity.common.fluid.MDFluids;
import modernity.common.item.MDItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class ReedGrowLogic implements IGrowLogic {
    @Override
    public void grow( World world, BlockPos pos, BlockState state, Random rand, @Nullable IFarmland farmland ) {
        if( state.get( MurkReedBlock.AGE ) < 15 ) {
            world.setBlockState( pos, state.with( MurkReedBlock.AGE, state.get( MurkReedBlock.AGE ) + 1 ) );
        } else if( canGrow( world, pos, state ) ) {
            world.setBlockState( pos, state.with( MurkReedBlock.AGE, 0 ) );
            world.setBlockState( pos.up(), MDBlocks.MURK_REED.computeStateForPos( world, pos ).with( MurkReedBlock.WATERLOGGED, world.getFluidState( pos.up() ).getFluid() == MDFluids.MURKY_WATER ) );
        }
    }

    @Override
    public boolean grow( World world, BlockPos pos, BlockState state, Random rand, ItemStack item ) {
        if( ! item.getItem().isIn( MDItemTags.FERTILIZER ) ) return false;
        if( world.isRemote ) return true;
        MovingBlockPos mpos = new MovingBlockPos( pos );
        int i = 0;
        while( world.getBlockState( mpos ).getBlock() == MDBlocks.MURK_REED && i < 12 ) {
            mpos.moveUp();
            i++;
        }
        mpos.moveDown();
        if( canGrow( world, mpos, state ) ) {
            world.setBlockState( mpos, state.with( MurkReedBlock.AGE, 0 ) );
            mpos.moveUp();
            world.setBlockState( mpos, MDBlocks.MURK_REED.computeStateForPos( world, pos ).with( MurkReedBlock.WATERLOGGED, world.getFluidState( mpos ).getFluid() == MDFluids.MURKY_WATER ) );
            return true;
        }
        return false;
    }

    private boolean canGrow( World world, BlockPos pos, BlockState state ) {
        BlockPos upPos = pos.up();
        BlockState upState = world.getBlockState( upPos );
        if( ! upState.isAir( world, upPos ) && upState.getBlock() != MDBlocks.MURKY_WATER ) {
            return false;
        }
        int owHeight = 0, totHeight = 0;
        MovingBlockPos mpos = new MovingBlockPos( pos );
        boolean uw = false;
        while( mpos.getY() >= 0 && state.getBlock() == MDBlocks.MURK_REED && totHeight < 10 ) {
            if( state.get( MurkReedBlock.WATERLOGGED ) ) {
                uw = true;
            }
            if( ! uw ) {
                owHeight++;
            }
            totHeight++;
            mpos.moveDown();
            state = world.getBlockState( mpos );
        }
        return totHeight < 10 && owHeight < 3;
    }
}
