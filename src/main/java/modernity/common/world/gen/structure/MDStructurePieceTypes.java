package modernity.common.world.gen.structure;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.util.HashMap;
import java.util.Map;

public final class MDStructurePieceTypes {
    private static final HashMap<String, IStructurePieceType> PIECES = new HashMap<>();

    public static final IStructurePieceType CAVE_DATA = register( "cave_data", CaveStructure.Piece::new );

    private static <T extends IStructurePieceType> T register( String id, T type ) {
        PIECES.put( "modernity:" + id, type );
        return type;
    }

    public static void registerPieces() {
        for( Map.Entry<String, IStructurePieceType> entry : PIECES.entrySet() ) {
            Registry.register( Registry.STRUCTURE_PIECE, entry.getKey(), entry.getValue() );
        }
    }

    private MDStructurePieceTypes() {
    }
}