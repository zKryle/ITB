package com.zkryle.itb.networking.packet;

import com.zkryle.itb.networking.packet.clienthandlers.FurnaceItemSlotsUploadPacketClientHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class FurnaceItemSlotsUploadPacket {

    public int size;
    public CompoundTag items;
    public long blockPos;

    public FurnaceItemSlotsUploadPacket(int size, CompoundTag items, long blockPos){
        this.size = size;
        this.items = items;
        this.blockPos = blockPos;
    }

    public static void encode(FriendlyByteBuf byteBuf, FurnaceItemSlotsUploadPacket itemSlotsUploadPacket){
        byteBuf.writeInt(itemSlotsUploadPacket.size);
        byteBuf.writeNbt(itemSlotsUploadPacket.items);
        byteBuf.writeLong(itemSlotsUploadPacket.blockPos);
    }

    public static FurnaceItemSlotsUploadPacket decode(FriendlyByteBuf byteBuf){
        return new FurnaceItemSlotsUploadPacket(byteBuf.readInt(), byteBuf.readNbt(), byteBuf.readLong());
    }

    public static void handler(FurnaceItemSlotsUploadPacket itemSlotsUploadPacket, CustomPayloadEvent.Context ctx){
        ctx.enqueueWork(() -> {
                    // Make sure it's only executed on the physical client
                    if (FMLEnvironment.dist.isClient()) {
                        FurnaceItemSlotsUploadPacketClientHandler.handlePacket(itemSlotsUploadPacket, ctx);
                    }
                }
        );
        ctx.setPacketHandled(true);
    }
}
