/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   02 - 03 - 2020
 * Author: rgsw
 */

package modernity.client.colors.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import modernity.client.colors.ColorDeserializeContext;
import modernity.client.colors.ColorDeserializeException;
import modernity.client.colors.IColorProvider;
import modernity.client.colors.IColorProviderDeserializer;
import modernity.client.colors.provider.BiomeSpecificColorProvider;
import modernity.client.colors.provider.ErrorColorProvider;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BiomeColorProviderDeserializer implements IColorProviderDeserializer {
    @Override
    public IColorProvider deserialize( JsonElement element, ColorDeserializeContext ctx ) throws ColorDeserializeException {
        if( element.isJsonObject() ) {
            JsonObject object = element.getAsJsonObject();

            HashMap<Predicate<String>, IColorProvider> providers = new HashMap<>();
            for( Map.Entry<String, JsonElement> entry : object.entrySet() ) {
                if( entry.getKey().equals( "radius" ) ) continue;
                providers.put( biomeSelector( entry.getKey(), ctx ), ctx.silentDeserialize( entry.getValue(), entry.getKey() ) );
            }

            IColorProvider fallback = ErrorColorProvider.INSTANCE;
            IColorProvider item = ErrorColorProvider.INSTANCE;

            HashMap<Biome, IColorProvider> flattened = new HashMap<>();
            for( Biome biome : ForgeRegistries.BIOMES ) {
                for( Map.Entry<Predicate<String>, IColorProvider> entry : providers.entrySet() ) {
                    String id = biome.getRegistryName() + "";
                    if( entry.getKey().test( id ) ) {
                        flattened.put( biome, entry.getValue() );
                        break;
                    }
                }
            }

            for( Map.Entry<Predicate<String>, IColorProvider> entry : providers.entrySet() ) {
                if( entry.getKey().test( "item" ) ) {
                    item = entry.getValue();
                    break;
                }
            }

            for( Map.Entry<Predicate<String>, IColorProvider> entry : providers.entrySet() ) {
                if( entry.getKey().test( "default" ) ) {
                    fallback = entry.getValue();
                    break;
                }
            }

            int radius = - 1;
            if( object.has( "radius" ) && object.get( "radius" ).isJsonPrimitive() ) {
                radius = object.getAsJsonPrimitive( "radius" ).getAsInt();
            }

            BiomeSpecificColorProvider provider = new BiomeSpecificColorProvider( fallback, item, radius );

            for( Map.Entry<Biome, IColorProvider> e : flattened.entrySet() ) {
                provider.addColor( e.getKey(), e.getValue() );
            }

            return provider;
        } else {
            throw ctx.exception( "Expected an object" );
        }
    }

    private static Predicate<String> biomeSelector( String sel, ColorDeserializeContext ctx ) throws ColorDeserializeException {
        String selector = sel.trim().toLowerCase();
        if( selector.isEmpty() ) throw ctx.exception( "Empty selector" );

        if( selector.equals( "*" ) ) {
            return str -> true;
        }

        if( selector.startsWith( "!" ) ) {
            Predicate<String> a = biomeSelector( selector.substring( 1 ), ctx );
            return str -> ! a.test( str );
        }

        int or = selector.indexOf( '|' );
        if( or >= 0 ) {
            Predicate<String> a = biomeSelector( selector.substring( 0, or ), ctx );
            Predicate<String> b = biomeSelector( selector.substring( or + 1 ), ctx );
            return str -> a.test( str ) || b.test( str );
        }

        return str -> str.equals( selector );
    }

    private static IColorProvider[] parseArray( JsonArray array, ColorDeserializeContext ctx ) throws ColorDeserializeException {
        if( array.size() == 0 ) {
            throw ctx.exception( "Required at least one entry to pick from" );
        }

        IColorProvider[] providers = new IColorProvider[ array.size() ];
        int i = 0;
        for( JsonElement element : array ) {
            providers[ i ] = ctx.silentDeserialize( element, "" + i );
        }
        return providers;
    }
}