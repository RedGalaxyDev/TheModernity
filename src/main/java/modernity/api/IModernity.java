/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   03 - 23 - 2020
 * Author: rgsw
 */

package modernity.api;

import modernity.api.data.IDataService;
import net.minecraftforge.eventbus.api.BusBuilder;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IModernity {
    IEventBus EVENT_BUS = BusBuilder.builder().startShutdown().build();

    static IModernity get() {
        return ModernityHolder.instance;
    }

    static void set( IModernity instance ) {
        if( ModernityHolder.instance != null ) {
            throw new IllegalStateException( "Modernity already initialized!" );
        }
        ModernityHolder.instance = instance;
    }

    default void registerListeners() {
    }

    default void setupRegistryHandler() {
    }

    default void preInit() {
    }

    default void init() {
    }

    default void postInit() {
    }

    default IDataService getDataService() {
        return IDataService.NONE;
    }
}
