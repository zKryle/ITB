package com.zkryle.itb.networking.packet.clienthandlers;

import com.zkryle.itb.Constants;
import com.zkryle.itb.networking.packet.FurnaceItemSlotsUploadPacket;
import com.zkryle.itb.syncing.CommonPacketHandlers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class FurnaceItemSlotsUploadPacketClientHandler {
    public static void handlePacket(FurnaceItemSlotsUploadPacket data, CustomPayloadEvent.Context ctx){
        CommonPacketHandlers.handleFurnaceSync(data.size, data.items, data.blockPos);
    }
}
