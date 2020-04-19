/*
 * Copyright (c) 2019 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   11 - 14 - 2019
 * Author: rgsw
 */

package modernity.common.area.core;

import modernity.common.Modernity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public interface IWorldAreaManager {
    World getWorld();

    void tick();

    Area getLoadedArea( long reference );
    boolean isAreaLoaded( long reference );
    Stream<Area> streamAreas();

    IAreaReferenceChunk getLoadedChunk( int x, int z );
    IAreaReferenceChunk getChunk( int x, int z );
    boolean isChunkLoadedAt( int x, int z );

    default Stream<Area> getAreasAt( int x, int y, int z ) {
        int cx = x >> 4;
        int cz = z >> 4;

        IAreaReferenceChunk chunk = getLoadedChunk( cx, cz );
        if( chunk != null ) {
            return chunk.referenceStream()
                        .mapToObj( this::getLoadedArea )
                        .filter( Objects::nonNull )
                        .filter( area -> area.getBox().contains( x, y, z ) );
        }
        return Stream.empty();
    }

    default Stream<Area> getAreasAt( Vec3i pos ) {
        return getAreasAt( pos.getX(), pos.getY(), pos.getZ() );
    }

    default boolean isInsideArea( int x, int y, int z, AreaType<?> type ) {
        return getAreasAt( x, y, z ).anyMatch( area -> area.getType() == type );
    }

    default boolean isInsideArea( Vec3i pos, AreaType<?> type ) {
        return isInsideArea( pos.getX(), pos.getY(), pos.getZ(), type );
    }

    default boolean isInsideArea( Vec3d pos, AreaType<?> type ) {
        return isInsideArea( pos.x, pos.y, pos.z, type );
    }

    default boolean isInsideArea( double x, double y, double z, AreaType<?> type ) {
        return isInsideArea( MathHelper.floor( x ), MathHelper.floor( y ), MathHelper.floor( z ), type );
    }

    default boolean isInsideArea( Entity entity, AreaType<?> type ) {
        return isInsideArea( entity.getPosX(), entity.getPosY(), entity.getPosZ(), type );
    }

    static Optional<IWorldAreaManager> get( World world ) {
        return Optional.ofNullable( Modernity.get().getWorldAreaManager( world ) );
    }
}
