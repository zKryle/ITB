package com.zkryle.itb.networking.packet.clienthandlers;

import com.zkryle.itb.networking.packet.FurnaceItemSlotsUploadPacket;
import com.zkryle.itb.syncing.CommonPacketHandlers;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class FurnaceItemSlotsUploadPacketClientHandler {
    public static void handlePacket(FurnaceItemSlotsUploadPacket data, CustomPayloadEvent.Context ctx){
        CommonPacketHandlers.handleFurnaceSync(data.size, data.items, data.blockPos);
    }
}
