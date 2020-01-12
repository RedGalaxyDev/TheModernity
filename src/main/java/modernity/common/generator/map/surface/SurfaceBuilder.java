/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   01 - 11 - 2020
 * Author: rgsw
 */

package modernity.common.generator.map.surface;

import modernity.api.util.MDDimension;
import modernity.api.util.MovingBlockPos;
import modernity.common.biome.MDBiomes;
import modernity.common.biome.ModernityBiome;
import modernity.common.generator.map.MapGenerator;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.WorldGenRegion;
import net.rgsw.noise.FractalPerlin3D;
import net.rgsw.noise.INoise3D;

/**
 * Generates the surface of the Modernity's surface dimension.
 */
public class SurfaceBuilder extends MapGenerator<SurfaceGenData> {

    private final INoise3D surfaceNoise;

    public SurfaceBuilder( IWorld world ) {
        super( world );

        this.surfaceNoise = new FractalPerlin3D( rand.nextInt(), 6.348456, 0.52, 6.348456, 6 );

        for( ModernityBiome biome : MDBiomes.getBiomesFor( MDDimension.MURK_SURFACE ) ) {
            biome.getSurfaceGen().init( rand );
        }
    }

    @Override
    public void generate( WorldGenRegion region, SurfaceGenData data ) {
        int[] heightmap = data.getHeightmap();

        MovingBlockPos mpos = new MovingBlockPos();
        int cx = region.getMainChunkX();
        int cz = region.getMainChunkZ();

        IChunk chunk = region.getChunk( cx, cz );

        for( int x = 0; x < 16; x++ ) {
            for( int z = 0; z < 16; z++ ) {
                mpos.setPos( x + cx * 16, 0, z + cz * 16 );
                Biome biome = chunk.getBiome( mpos );
                if( biome instanceof ModernityBiome ) {
                    ModernityBiome mbiome = (ModernityBiome) biome;
                    mbiome.getSurfaceGen().buildSurface( chunk, cx, cz, x, z, rand, mbiome, surfaceNoise, mpos );
                }


                int caveHeight = 0;
                for( int y = 0; y < 256; y++ ) {
                    mpos.setPos( x, y, z );
                    if( ! chunk.getBlockState( mpos ).getMaterial().blocksMovement() ) {
                        caveHeight = y - 1;
                        break;
                    }
                }
                heightmap[ x + z * 16 ] = caveHeight;
            }
        }
    }
}