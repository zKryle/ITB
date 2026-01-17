package com.zkryle.itb.networking;

import com.zkryle.itb.networking.packet.FurnaceItemSlotsUploadPacket;
import net.minecraft.network.codec.StreamCodec;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkProtocol;
import net.minecraftforge.network.SimpleChannel;

public class ITBPacketHandler {
    private static final int PROTOCOL_VERSION = 1;
    public static final SimpleChannel CHANNEL = ChannelBuilder.named("main").networkProtocolVersion(PROTOCOL_VERSION).optionalClient().simpleChannel();

    public static void init() {
        CHANNEL.protocol(NetworkProtocol.PLAY).clientbound(registryFriendlyByteBufObjectSimpleFlow -> registryFriendlyByteBufObjectSimpleFlow.add(FurnaceItemSlotsUploadPacket.class, StreamCodec.of(FurnaceItemSlotsUploadPacket::encode, FurnaceItemSlotsUploadPacket::decode), FurnaceItemSlotsUploadPacket::handler));
    }
}
