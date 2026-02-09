package com.zkryle.itb.networking.packet;

import com.zkryle.itb.networking.packet.clienthandlers.FurnaceItemSlotsUploadPacketClientHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FurnaceItemSlotsUploadPacket {

    public int size;
    public CompoundTag items;
    public long blockPos;

    public FurnaceItemSlotsUploadPacket(int size, CompoundTag items, long blockPos){
        this.size = size;
        this.items = items;
        this.blockPos = blockPos;
    }

    public static void encode(FurnaceItemSlotsUploadPacket itemSlotsUploadPacket, FriendlyByteBuf byteBuf){
        byteBuf.writeInt(itemSlotsUploadPacket.size);
        byteBuf.writeNbt(itemSlotsUploadPacket.items);
        byteBuf.writeLong(itemSlotsUploadPacket.blockPos);
    }

    public static FurnaceItemSlotsUploadPacket decode(FriendlyByteBuf byteBuf){
        return new FurnaceItemSlotsUploadPacket(byteBuf.readInt(), byteBuf.readNbt(), byteBuf.readLong());
    }

    public static void handler(FurnaceItemSlotsUploadPacket itemSlotsUploadPacket, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
                    // Make sure it's only executed on the physical client
                    if (FMLEnvironment.dist.isClient()) {
                        FurnaceItemSlotsUploadPacketClientHandler.handlePacket(itemSlotsUploadPacket, ctx);
                    }
                }
        );
        ctx.get().setPacketHandled(true);
    }
}
