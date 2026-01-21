package com.zkryle.itb.events;

import com.zkryle.itb.Constants;
import com.zkryle.itb.bers.NeoForgeFurnaceBER;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(BlockEntityType.FURNACE, (BlockEntityRendererProvider.Context context) -> new NeoForgeFurnaceBER(context, Constants.FURNACE_COOKED, Constants.FURNACE_TOCOOK, Constants.FURNACE_FUEL, Constants.FURNACE_FLUID, true));
        event.registerBlockEntityRenderer(BlockEntityType.BLAST_FURNACE, (BlockEntityRendererProvider.Context context) -> new NeoForgeFurnaceBER(context, Constants.BFURNACE_COOKED, Constants.BFURNACE_TOCOOK, Constants.BFURNACE_FUEL, Constants.BFURNACE_FLUID, false));
    }
}
