package com.zkryle.itb.networking.handlers;

import com.zkryle.itb.Constants;
import com.zkryle.itb.networking.payloads.FurnaceItemSlotsUploadPayload;
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
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientFurnacePayloadHandler {

    public static void handleDataOnMain(final FurnaceItemSlotsUploadPayload data, final IPayloadContext context){
        CommonPacketHandlers.handleFurnaceSync(data.size(), data.items(), data.blockPos());
    }
}
