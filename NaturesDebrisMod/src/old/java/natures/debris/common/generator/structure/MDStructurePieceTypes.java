/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package natures.debris.common.generator.structure;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.util.HashMap;
import java.util.Map;

/**
 * Holder class for Modernity structure pieces.
 */
public final class MDStructurePieceTypes {
    private static final HashMap<String, IStructurePieceType> PIECES = new HashMap<>();

    public static final IStructurePieceType FOREST_RUNES = register("forest_runes", ForestRunesStructure.Piece::new);

    private MDStructurePieceTypes() {
    }

    private static <T extends IStructurePieceType> T register(String id, T type) {
        PIECES.put("modernity:" + id, type);
        return type;
    }

    public static void registerPieces() {
        for (Map.Entry<String, IStructurePieceType> entry : PIECES.entrySet()) {
            Registry.register(Registry.STRUCTURE_PIECE, entry.getKey(), entry.getValue());
        }
    }
}