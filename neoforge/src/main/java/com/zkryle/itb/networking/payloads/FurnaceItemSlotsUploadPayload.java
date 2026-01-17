package com.zkryle.itb.networking.payloads;

import com.zkryle.itb.Constants;
import com.zkryle.itb.ITB;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record FurnaceItemSlotsUploadPayload(int size, CompoundTag items, long blockPos) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<FurnaceItemSlotsUploadPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "furnace_item_slots"));

    public static final StreamCodec<ByteBuf, FurnaceItemSlotsUploadPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            FurnaceItemSlotsUploadPayload::size,
            ByteBufCodecs.TRUSTED_COMPOUND_TAG,
            FurnaceItemSlotsUploadPayload::items,
            ByteBufCodecs.LONG,
            FurnaceItemSlotsUploadPayload::blockPos,
            FurnaceItemSlotsUploadPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
