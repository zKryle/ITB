package com.zkryle.itb.networking.packet.clienthandlers;

import com.zkryle.itb.networking.packet.FurnaceItemSlotsUploadPacket;
import com.zkryle.itb.syncing.CommonPacketHandlers;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FurnaceItemSlotsUploadPacketClientHandler {
    public static void handlePacket(FurnaceItemSlotsUploadPacket data, Supplier<NetworkEvent.Context> ctx){
        CommonPacketHandlers.handleFurnaceSync(data.size, data.items, data.blockPos);
    }
}
