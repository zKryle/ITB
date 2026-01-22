package com.zkryle.itb.events;

import com.zkryle.itb.Constants;
import com.zkryle.itb.bers.ForgeFurnaceBER;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID)
public class ClientEvents {

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(BlockEntityType.FURNACE, (BlockEntityRendererProvider.Context context) -> new ForgeFurnaceBER(context, Constants.FURNACE_COOKED, Constants.FURNACE_TOCOOK, Constants.FURNACE_FUEL, Constants.FURNACE_FLUID, true));
        event.registerBlockEntityRenderer(BlockEntityType.BLAST_FURNACE, (BlockEntityRendererProvider.Context context) -> new ForgeFurnaceBER(context, Constants.BFURNACE_COOKED, Constants.BFURNACE_TOCOOK, Constants.BFURNACE_FUEL, Constants.BFURNACE_FLUID, false));
        event.registerBlockEntityRenderer(BlockEntityType.SMOKER, (BlockEntityRendererProvider.Context context) -> new ForgeFurnaceBER(context, Constants.SMOKER_COOKED, Constants.SMOKER_TOCOOK, Constants.SMOKER_FUEL, Constants.SMOKER_FLUID, false));
    }

}
