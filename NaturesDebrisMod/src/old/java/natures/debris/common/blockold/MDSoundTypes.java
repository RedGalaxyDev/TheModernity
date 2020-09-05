/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package natures.debris.common.blockold;

import natures.debris.common.sound.MDSoundEvents;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvents;

public final class MDSoundTypes {
    public static final SoundType SHADE_BLUE = new SoundType(
        1, 1,
        MDSoundEvents.BLOCK_SHADE_BLUE_BREAK,
        SoundEvents.BLOCK_GRASS_STEP,
        MDSoundEvents.BLOCK_SHADE_BLUE_PLACE,
        SoundEvents.BLOCK_GRASS_HIT,
        SoundEvents.BLOCK_GRASS_FALL
    );

    public static final SoundType MUD = new SoundType(
        1.2F, 1,
        MDSoundEvents.BLOCK_MUD_BREAK,
        MDSoundEvents.BLOCK_MUD_STEP,
        MDSoundEvents.BLOCK_MUD_PLACE,
        MDSoundEvents.BLOCK_MUD_HIT,
        MDSoundEvents.BLOCK_MUD_FALL
    );

    public static final SoundType ASPHALT = new SoundType(
        1.2F, 1,
        MDSoundEvents.BLOCK_ASPHALT_BREAK,
        MDSoundEvents.BLOCK_ASPHALT_STEP,
        MDSoundEvents.BLOCK_ASPHALT_PLACE,
        MDSoundEvents.BLOCK_ASPHALT_HIT,
        MDSoundEvents.BLOCK_ASPHALT_FALL
    );

    public static final SoundType CRYSTAL = new SoundType(
        1.2F, 1,
        MDSoundEvents.BLOCK_CRYSTAL_BREAK,
        MDSoundEvents.BLOCK_CRYSTAL_STEP,
        MDSoundEvents.BLOCK_CRYSTAL_PLACE,
        MDSoundEvents.BLOCK_CRYSTAL_HIT,
        MDSoundEvents.BLOCK_CRYSTAL_FALL
    );

    private MDSoundTypes() {
    }
}