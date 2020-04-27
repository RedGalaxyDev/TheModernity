/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   03 - 04 - 2020
 * Author: rgsw
 */

package modernity.common.generator.region.layer;

import modernity.common.generator.region.IRegion;
import modernity.common.generator.region.IRegionRNG;

@FunctionalInterface
public interface IFilterMergerLayer extends IMergerLayer {
    @Override
    default int generate( IRegionRNG rng, IRegion regionA, IRegion regionB, int x, int z ) {
        return generate( rng, regionA.getValue( x, z ), regionB.getValue( x, z ) );
    }

    int generate( IRegionRNG rng, int a, int b );
}