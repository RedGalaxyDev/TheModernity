/*
 * Copyright (c) 2019 RedGalaxy & co.
 * Licensed under the Apache Licence v2.0.
 * Do not redistribute.
 *
 * By  : RGSW
 * Date: 6 - 29 - 2019
 */

package modernity.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.text.ITextComponent;

import modernity.client.gui.GuiNetherAltar;
import modernity.common.net.SPacketOpenContainer;

import java.util.HashMap;

public class ClientContainerManager {
    private static final HashMap<String, IGUIHandler> HANDLERS = new HashMap<>();

    public static void receivePacket( SPacketOpenContainer pkt ) {
        Minecraft mc = Minecraft.getInstance();
        EntityPlayerSP player = mc.player;
        IGUIHandler handler = HANDLERS.get( pkt.getGuiId() );

        if( handler == null ) return;

        GuiContainer container = handler.getGUI( pkt.getGuiId(), pkt.getWindowId(), pkt.getWindowTitle(), pkt.getSlotCount(), mc.player );
        mc.displayGuiScreen( container );
        player.openContainer.windowId = pkt.getWindowId();
    }

    public static void registerHandler( String id, IGUIHandler handler ) {
        HANDLERS.put( id, handler );
    }

    static {
        registerHandler(
                "modernity:nether_altar",
                ( guiID, windowID, title, slotCount, player ) -> new GuiNetherAltar( player.inventory, new InventoryBasic( title, slotCount ) )
        );
    }

    public interface IGUIHandler {
        GuiContainer getGUI( String guiID, int windowID, ITextComponent title, int slotCount, EntityPlayerSP player );
    }
}