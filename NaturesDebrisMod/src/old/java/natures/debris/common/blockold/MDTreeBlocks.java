/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package natures.debris.common.blockold;

import modernity.common.blockold.tree.*;
import modernity.common.item.group.MDItemGroup;
import natures.debris.common.blockold.base.AxisBlock;
import natures.debris.common.blockold.loot.LeavesBlockDrops;
import natures.debris.common.blockold.loot.MDBlockDrops;
import natures.debris.common.blockold.tree.*;
import natures.debris.common.generator.tree.MDTrees;
import natures.debris.common.itemold.MDItems;
import net.minecraft.block.material.MaterialColor;

//@ObjectHolder( "modernity" )
public final class MDTreeBlocks {
    // Blackwood
    public static final AxisBlock STRIPPED_BLACKWOOD_LOG
        = MDBlocks.function("stripped_blackwood_log", AxisBlock::new)
                  .wood(MaterialColor.BLACK_TERRACOTTA)
                  .item(MDItemGroup.BLOCKS)
                  .drops(MDBlockDrops.SIMPLE)
                  .create();
    public static final AxisBlock BLACKWOOD_LOG
        = MDBlocks.function("blackwood_log", props -> new StripableLogBlock(() -> STRIPPED_BLACKWOOD_LOG, props))
                  .wood(MaterialColor.BLACK_TERRACOTTA)
                  .item(MDItemGroup.BLOCKS)
                  .drops(MDBlockDrops.SIMPLE)
                  .create();
    public static final AxisBlock STRIPPED_BLACKWOOD
        = MDBlocks.function("stripped_blackwood", AxisBlock::new)
                  .wood(MaterialColor.BLACK_TERRACOTTA)
                  .item(MDItemGroup.BLOCKS)
                  .drops(MDBlockDrops.SIMPLE)
                  .recipeBlock4(() -> STRIPPED_BLACKWOOD_LOG, 3, null)
                  .create();
    public static final AxisBlock BLACKWOOD
        = MDBlocks.function("blackwood", props -> new StripableLogBlock(() -> STRIPPED_BLACKWOOD, props))
                  .wood(MaterialColor.BLACK_TERRACOTTA)
                  .item(MDItemGroup.BLOCKS)
                  .drops(MDBlockDrops.SIMPLE)
                  .recipeBlock4(() -> BLACKWOOD_LOG, 3, null)
                  .create();
    public static final BlackwoodSaplingBlock BLACKWOOD_SAPLING
        = MDBlocks.function("blackwood_sapling", BlackwoodSaplingBlock::new)
                  .strongPlant(MaterialColor.GRASS, 0)
                  .item(MDItemGroup.PLANTS)
                  .drops(MDBlockDrops.SIMPLE)
                  .create();
    public static final HangLeavesBlock BLACKWOOD_LEAVES
        = MDBlocks.function("blackwood_leaves", props -> new BlackwoodLeavesBlock(MDBlockTags.BLACKWOOD_LOG, props))
                  .leaves(MaterialColor.FOLIAGE, 0.2)
                  .item(MDItemGroup.PLANTS)
                  .drops(new LeavesBlockDrops(() -> MDTreeBlocks.BLACKWOOD_SAPLING, () -> MDItems.BLACKWOOD_STICK, 0.05F, 0.0625F, 0.083333336F, 0.1F))
                  .create();
    // Inver
    public static final AxisBlock STRIPPED_INVER_LOG
        = MDBlocks.function("stripped_inver_log", AxisBlock::new)
                  .wood(MaterialColor.WOOD)
                  .item(MDItemGroup.BLOCKS)
                  .dropSelf()
                  .create();
    public static final AxisBlock INVER_LOG
        = MDBlocks.function("inver_log", props -> new StripableLogBlock(() -> STRIPPED_INVER_LOG, props))
                  .wood(MaterialColor.WOOD)
                  .item(MDItemGroup.BLOCKS)
                  .dropSelf()
                  .create();
    public static final AxisBlock STRIPPED_INVER
        = MDBlocks.function("stripped_inver_wood", AxisBlock::new)
                  .wood(MaterialColor.WOOD)
                  .item(MDItemGroup.BLOCKS)
                  .dropSelf()
                  .recipeBlock4(() -> STRIPPED_INVER_LOG, 3, null)
                  .create();
    public static final AxisBlock INVER_WOOD
        = MDBlocks.function("inver_wood", props -> new StripableLogBlock(() -> STRIPPED_INVER, props))
                  .wood(MaterialColor.WOOD)
                  .item(MDItemGroup.BLOCKS)
                  .dropSelf()
                  .recipeBlock4(() -> INVER_LOG, 3, null)
                  .create();
    public static final SimpleSaplingBlock INVER_SAPLING
        = MDBlocks.function("inver_sapling", props -> new SimpleSaplingBlock(() -> MDTrees.INVER, props))
                  .strongPlant(MaterialColor.GRASS, 0)
                  .item(MDItemGroup.PLANTS)
                  .dropSelf()
                  .create();
    public static final SimpleSaplingBlock RED_INVER_SAPLING
        = MDBlocks.function("red_inver_sapling", props -> new SimpleSaplingBlock(() -> MDTrees.RED_INVER, props))
                  .strongPlant(MaterialColor.GRASS, 0)
                  .item(MDItemGroup.PLANTS)
                  .dropSelf()
                  .create();
    public static final DecayLeavesBlock INVER_LEAVES
        = MDBlocks.function("inver_leaves", props -> new InverLeavesBlock(MDBlockTags.INVER_LOG, props, false))
                  .leaves(MaterialColor.FOLIAGE, 0.2)
                  .item(MDItemGroup.PLANTS)
                  .drops(new LeavesBlockDrops(() -> MDTreeBlocks.INVER_SAPLING, () -> MDItems.INVER_STICK, 0.05F, 0.0625F, 0.083333336F, 0.1F))
                  .create();
    public static final DecayLeavesBlock RED_INVER_LEAVES
        = MDBlocks.function("red_inver_leaves", props -> new InverLeavesBlock(MDBlockTags.INVER_LOG, props, true))
                  .leaves(MaterialColor.RED, 0.2)
                  .item(MDItemGroup.PLANTS)
                  .drops(new LeavesBlockDrops(() -> MDTreeBlocks.RED_INVER_SAPLING, () -> MDItems.INVER_STICK, 0.05F, 0.0625F, 0.083333336F, 0.1F))
                  .create();

    public static void init() {
    }
}