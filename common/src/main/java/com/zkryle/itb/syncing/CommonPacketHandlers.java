package com.zkryle.itb.syncing;

import com.zkryle.itb.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;

public class CommonPacketHandlers {
    public static void handleFurnaceSync(int size, CompoundTag items, long blockPos){
        Level level = Minecraft.getInstance().level;
        AbstractFurnaceBlockEntity furnaceBlockEntity = (AbstractFurnaceBlockEntity) level.getBlockEntity(BlockPos.of(blockPos));

        if(furnaceBlockEntity == null)
            return;

        NonNullList<ItemStack> itemsList = NonNullList.withSize(3, ItemStack.EMPTY);

        ValueInput itemsTag = TagValueInput.create(new ProblemReporter.ScopedCollector(Constants.LOG), level.registryAccess(), items);

        ContainerHelper.loadAllItems(itemsTag, itemsList);

        for (int i = 0; i < size; i++)
            furnaceBlockEntity.setItem(i, itemsList.get(i));
    }
}
