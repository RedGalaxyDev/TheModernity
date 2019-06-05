package modernity.common.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import modernity.api.util.BlockUpdates;
import modernity.common.item.MDItemTags;
import modernity.common.world.gen.decorate.feature.TreeFeature;

import java.util.Random;

public class BlockSapling extends BlockBase {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;

    private final TreeFeature feature;

    public BlockSapling( String id, TreeFeature feature, Properties properties, Item.Properties itemProps ) {
        super( id, properties, itemProps );
        initDefaultState();
        this.feature = feature;
    }

    public BlockSapling( String id, TreeFeature feature, Properties properties ) {
        super( id, properties );
        initDefaultState();
        this.feature = feature;
    }

    private void initDefaultState() {
        setDefaultState( stateContainer.getBaseState().with( AGE, 0 ) );
    }

    @Override
    protected void fillStateContainer( StateContainer.Builder<Block, IBlockState> builder ) {
        builder.add( AGE );
    }

    @Override
    public void randomTick( IBlockState state, World world, BlockPos pos, Random rand ) {
        growOlder( state, world, pos, rand );
        super.randomTick( state, world, pos, rand );
    }

    public void growOlder( IBlockState state, IWorld world, BlockPos pos, Random rand ) {
        if( state.get( AGE ) == 5 ) {
            world.removeBlock( pos );
            feature.generate( world, rand, pos );
        } else {
            world.setBlockState( pos, state.with( AGE, state.get( AGE ) + 1 ), BlockUpdates.CAUSE_UPDATE | BlockUpdates.NOTIFY_CLIENTS | BlockUpdates.NO_RENDER | BlockUpdates.NO_NEIGHBOR_REACTIONS );
        }
    }

    @Override
    public boolean onBlockActivated( IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z ) {
        if( player.getHeldItem( hand ).getItem().isIn( MDItemTags.FERTILIZER ) ) {
            growOlder( state, world, pos, world.rand );
            return true;
        }
        return false;
    }
}