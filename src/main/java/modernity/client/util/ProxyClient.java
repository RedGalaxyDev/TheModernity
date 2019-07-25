/*
 * Copyright (c) 2019 RedGalaxy & contributors
 * Licensed under the Apache Licence v2.0.
 * Do not redistribute.
 *
 * By  : RGSW
 * Date: 7 - 25 - 2019
 */

package modernity.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import modernity.client.colormap.ColorMap;
import modernity.client.handler.OptionsButtonHandler;
import modernity.client.handler.TextureStitchHandler;
import modernity.client.handler.WorldListenerInjectionHandler;
import modernity.client.particle.DepthParticleList;
import modernity.client.particle.NoDepthParticleList;
import modernity.client.render.block.CustomFluidRenderer;
import modernity.client.texture.ParticleSprite;
import modernity.common.block.MDBlocks;
import modernity.common.entity.MDEntityTypes;
import modernity.common.item.MDItems;
import modernity.common.util.ContainerManager;
import modernity.common.util.ProxyCommon;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;


@OnlyIn( Dist.CLIENT )
public class ProxyClient extends ProxyCommon implements ISelectiveResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation PARTICLE_MAP_LOCATION = new ResourceLocation( "modernity:particles" );

    public final Minecraft mc = Minecraft.getInstance();

    private TextureMap particleTextureMap;
    private CustomFluidRenderer fluidRenderer;

    private final ColorMap humusColorMap = new ColorMap( new ResourceLocation( "modernity:textures/block/humus_top.png" ) );

    private final DepthParticleList depthParticleList = new DepthParticleList( PARTICLE_MAP_LOCATION );
    private final NoDepthParticleList noDepthParticleList = new NoDepthParticleList( PARTICLE_MAP_LOCATION );

    @Override
    public void init() {
        super.init();
        addResourceManagerReloadListener( this );

        MDEntityTypes.registerClient();
    }

    @Override
    public void loadComplete() {
        super.loadComplete();

        fluidRenderer = new CustomFluidRenderer();

        addResourceManagerReloadListener( fluidRenderer );
        addResourceManagerReloadListener( humusColorMap );

        MDBlocks.registerClient( mc.getBlockColors(), mc.getItemColors() );
        MDItems.registerClient( mc.getItemColors() );

        mc.addScheduledTask( this::finishOnMainThread );
    }

    @Override
    public void onResourceManagerReload( IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate ) {
        if( resourcePredicate.test( VanillaResourceType.TEXTURES ) ) {
            if( particleTextureMap != null ) {
                reloadTextureMap();
            }
        }
    }

    public void finishOnMainThread() {
        // Texture map must be initialized on main thread (it uses OpenGL calls)...
        particleTextureMap = new TextureMap( "textures/particle" );
        reloadTextureMap();
    }

    private void reloadTextureMap() {
        particleTextureMap.clear();
        mc.textureManager.loadTickableTexture( PARTICLE_MAP_LOCATION, particleTextureMap );
        mc.textureManager.bindTexture( PARTICLE_MAP_LOCATION );
        particleTextureMap.setMipmapLevels( 0 );
        particleTextureMap.setBlurMipmapDirect( false, false );
        mc.textureManager.bindTexture( TextureMap.LOCATION_BLOCKS_TEXTURE );
        particleTextureMap.stitch( mc.getResourceManager(), collectParticleTextures() );

        for( ParticleSprite sprite : ParticleSprite.SPRITE_LIST ) {
            sprite.reloadSprite();
        }
    }

    private Set<ResourceLocation> collectParticleTextures() {
        HashSet<ResourceLocation> set = new HashSet<>();
        for( ParticleSprite sprite : ParticleSprite.SPRITE_LIST ) {
            LOGGER.info( "Stitching sprite {} on particle texture", sprite.getID() );
            set.add( sprite.getID() );
        }
        return set;
    }

    @Override
    public boolean fancyGraphics() {
        return mc.gameSettings.fancyGraphics;
    }

    @Override
    public void registerListeners() {
        super.registerListeners();
        MinecraftForge.EVENT_BUS.register( new TextureStitchHandler() );
        MinecraftForge.EVENT_BUS.register( new WorldListenerInjectionHandler() );
        MinecraftForge.EVENT_BUS.register( new OptionsButtonHandler() );
    }

    @SuppressWarnings( "deprecation" )
    public void addResourceManagerReloadListener( IResourceManagerReloadListener listener ) {
        IResourceManager manager = mc.getResourceManager();
        if( manager instanceof IReloadableResourceManager ) {
            ( (IReloadableResourceManager) manager ).addReloadListener( listener );
        }
    }

    @Override
    public void openContainer( EntityPlayer player, IInventory inventory ) {
        if( player instanceof EntityPlayerSP ) {
            ContainerManager.openContainerSP( (EntityPlayerSP) player, inventory );
            return;
        }
        super.openContainer( player, inventory );
    }

    public Minecraft getMinecraft() {
        return mc;
    }

    public CustomFluidRenderer getFluidRenderer() {
        return fluidRenderer;
    }

    public TextureMap getParticleTextureMap() {
        return particleTextureMap;
    }

    public ColorMap getHumusColorMap() {
        return humusColorMap;
    }

    public DepthParticleList getDepthParticleList() {
        return depthParticleList;
    }

    public NoDepthParticleList getNoDepthParticleList() {
        return noDepthParticleList;
    }

    @SubscribeEvent
    public void clientTick( TickEvent.ClientTickEvent event ) {
        if( mc.world != null ) {
            depthParticleList.tick();
            noDepthParticleList.tick();
        }
    }

    public void renderParticles( Entity e, float partialTicks ) {
        if( mc.world != null ) {
            depthParticleList.render( e, partialTicks );
            noDepthParticleList.render( e, partialTicks );
        }
    }

    @SubscribeEvent
    public void onWorldLoad( WorldEvent.Load event ) {
        if( event.getWorld().isRemote() ) {
            depthParticleList.clear();
            noDepthParticleList.clear();
        }
    }

    @Override
    public IThreadListener getThreadListener() {
        return mc;
    }

    @Override
    public LogicalSide getSide() {
        return LogicalSide.CLIENT;
    }

    public static ProxyClient get() {
        return (ProxyClient) ProxyCommon.get();
    }
}
