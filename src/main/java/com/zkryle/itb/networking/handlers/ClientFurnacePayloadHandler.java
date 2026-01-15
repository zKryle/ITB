package com.zkryle.itb.networking.handlers;

import com.zkryle.itb.networking.payloads.FurnaceItemSlotsUploadPayload;
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

import static com.zkryle.itb.ITB.LOGGER;

public class ClientFurnacePayloadHandler {

    public static void handleDataOnMain(final FurnaceItemSlotsUploadPayload data, final IPayloadContext context){
        Level level = Minecraft.getInstance().level;
        AbstractFurnaceBlockEntity furnaceBlockEntity = (AbstractFurnaceBlockEntity) level.getBlockEntity(BlockPos.of(data.blockPos()));

        if(furnaceBlockEntity == null)
            return;

        NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

        ValueInput itemsTag = TagValueInput.create(new ProblemReporter.ScopedCollector(LOGGER), level.registryAccess(), data.items());

        ContainerHelper.loadAllItems(itemsTag, items);

        for (int i = 0; i < data.size(); i++)
            furnaceBlockEntity.setItem(i, items.get(i));
    }
}
