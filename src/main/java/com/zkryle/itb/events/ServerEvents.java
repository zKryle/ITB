package com.zkryle.itb.events;

import com.zkryle.itb.ITB;
import com.zkryle.itb.networking.payloads.FurnaceItemSlotsUploadPayload;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.zkryle.itb.ITB.LOGGER;

@EventBusSubscriber(modid = ITB.MODID)
public class ServerEvents {

    @SubscribeEvent
    public static void levelTick(LevelTickEvent.Post event){

        Level level = event.getLevel();

        if(level.isClientSide())
            return;

        if(((level.getGameTime() & 0x1F) % 12) != 0) // Send the packet only twice a second.
            return;


        level.blockEntityTickers.forEach((blockEntityTicker -> {
            BlockEntity blockEntity = level.getBlockEntity(blockEntityTicker.getPos());
            if(blockEntity instanceof AbstractFurnaceBlockEntity){
                int size = ((AbstractFurnaceBlockEntity) blockEntity).getContainerSize();
                NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

                for (int i = 0; i < size; i++) {
                    items.set(i, ((AbstractFurnaceBlockEntity) blockEntity).getItem(i));
                }

                ValueOutput itemsTag = TagValueOutput.createWithContext(new ProblemReporter.ScopedCollector(LOGGER), level.registryAccess());

                ContainerHelper.saveAllItems(itemsTag, items);

                PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(blockEntity.getBlockPos()), new FurnaceItemSlotsUploadPayload(size, ((TagValueOutput) itemsTag).buildResult(), blockEntity.getBlockPos().asLong()));
            }

        }));
    }

}
