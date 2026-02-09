package com.zkryle.itb.networking;

import com.zkryle.itb.Constants;
import com.zkryle.itb.networking.packet.FurnaceItemSlotsUploadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ITBPacketHandler {
    public static final String PROTOCOL_VERSION= "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Constants.MOD_ID, "main"), () -> PROTOCOL_VERSION,
            version -> version.equals(PROTOCOL_VERSION), version -> version.equals(PROTOCOL_VERSION));

    public static void init() {
        CHANNEL.registerMessage(0, FurnaceItemSlotsUploadPacket.class, FurnaceItemSlotsUploadPacket::encode, FurnaceItemSlotsUploadPacket::decode, FurnaceItemSlotsUploadPacket::handler);
    }
}
