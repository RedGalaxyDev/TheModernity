/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   03 - 14 - 2020
 * Author: rgsw
 */

package modernity.client.handler;

// TODO Re-evaluate
//public enum SoundEventHandler {
//    INSTANCE;
//
//    private static final FieldAccessor<SoundHandler, SoundEngine> sndManagerField = new FieldAccessor<>( SoundHandler.class, "field_147694_f" );
//
//    public void init() {
//        SoundHandler handler = Minecraft.getInstance().getSoundHandler();
//        sndManagerField.set( handler, new SoundManager( handler, Minecraft.getInstance().gameSettings, Minecraft.getInstance().getResourceManager() ) );
//    }
//
//    @SubscribeEvent
//    public void onSoundEffect( SoundEffectEvent event ) {
//        ModernityClient.get().getSoundEffectManager().onPlaySound( event );
//    }
//}