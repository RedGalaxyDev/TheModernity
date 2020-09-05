/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package natures.debris.common.blockold.dispensing;

import natures.debris.common.itemold.MDItems;
import natures.debris.common.itemold.base.BaseBucketItem;
import natures.debris.generic.block.fluid.IAluminiumBucketTakeable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class MDDispenseBehaviors {
    private MDDispenseBehaviors() {
    }

    public static void init() {
        DispenserBlock.registerDispenseBehavior(MDItems.GOO_BALL, new ThrownEntityDispenseBehavior(MDItems.GOO_BALL));
        DispenserBlock.registerDispenseBehavior(MDItems.POISONOUS_GOO_BALL, new ThrownEntityDispenseBehavior(MDItems.POISONOUS_GOO_BALL));
        DispenserBlock.registerDispenseBehavior(MDItems.SHADE_BALL, new ThrownEntityDispenseBehavior(MDItems.SHADE_BALL));

        IDispenseItemBehavior fluidDispenseBehavior = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior def = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                BaseBucketItem bucket = (BaseBucketItem) stack.getItem();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                World world = source.getWorld();
                if (bucket.tryPlaceContainedLiquid(null, world, pos, null, null)) {
                    bucket.onLiquidPlaced(world, stack, pos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return def.dispense(source, stack);
                }
            }
        };

        DispenserBlock.registerDispenseBehavior(MDItems.ALUMINIUM_WATER_BUCKET, fluidDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(MDItems.ALUMINIUM_CLEAN_WATER_BUCKET, fluidDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(MDItems.ALUMINIUM_LAVA_BUCKET, fluidDispenseBehavior);
        DispenserBlock.registerDispenseBehavior(MDItems.ALUMINIUM_BUCKET, new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior def = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World world = source.getWorld();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                BlockState state = world.getBlockState(pos);
                Block block = state.getBlock();

                if (block instanceof IBucketPickupHandler) {
                    Fluid fluid = ((IBucketPickupHandler) block).pickupFluid(world, pos, state);
                    if (!(fluid instanceof IAluminiumBucketTakeable)) {
                        return super.dispenseStack(source, stack);
                    } else {
                        Item item = ((IAluminiumBucketTakeable) fluid).getFilledAluminiumBucket();
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            return new ItemStack(item);
                        } else {
                            if (source.<DispenserTileEntity>getBlockTileEntity().addItemStack(new ItemStack(item)) < 0) {
                                this.def.dispense(source, new ItemStack(item));
                            }

                            return stack;
                        }
                    }
                } else {
                    return super.dispenseStack(source, stack);
                }
            }
        });
    }
}