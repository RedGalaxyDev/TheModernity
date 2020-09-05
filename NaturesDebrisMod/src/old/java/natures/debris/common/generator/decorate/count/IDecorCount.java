/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package natures.debris.common.generator.decorate.count;

import net.minecraft.world.IWorld;

import java.util.Random;

@FunctionalInterface
public interface IDecorCount {
    int count(IWorld world, int cx, int cz, Random rand);
}