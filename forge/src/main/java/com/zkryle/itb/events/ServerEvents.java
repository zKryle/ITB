package com.zkryle.itb.events;

import com.zkryle.itb.Constants;
import com.zkryle.itb.networking.ITBPacketHandler;
import com.zkryle.itb.networking.packet.FurnaceItemSlotsUploadPacket;
import net.minecraft.core.NonNullList;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ServerEvents {

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent.Post event){

        Level level = event.level();

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

                TagValueOutput itemsTag = TagValueOutput.createWithContext(new ProblemReporter.ScopedCollector(Constants.LOG), level.registryAccess());

                ContainerHelper.saveAllItems(itemsTag, items);

                ITBPacketHandler.CHANNEL.send(new FurnaceItemSlotsUploadPacket(size, itemsTag.buildResult(), blockEntity.getBlockPos().asLong()), PacketDistributor.TRACKING_CHUNK.with(level.getChunkAt(blockEntityTicker.getPos())));
            }

        }));
    }
}
