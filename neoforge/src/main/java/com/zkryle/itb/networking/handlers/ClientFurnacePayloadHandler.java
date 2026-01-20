package com.zkryle.itb.networking.handlers;

import com.zkryle.itb.networking.payloads.FurnaceItemSlotsUploadPayload;
import com.zkryle.itb.syncing.CommonPacketHandlers;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientFurnacePayloadHandler {

    public static void handleDataOnMain(final FurnaceItemSlotsUploadPayload data, final IPayloadContext context){
        CommonPacketHandlers.handleFurnaceSync(data.size(), data.items(), data.blockPos());
    }
}
