package com.zkryle.itb.events;

import com.zkryle.itb.ITB;
import com.zkryle.itb.bers.furnace.FurnaceBER;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = ITB.MODID)
public class ClientEvents {

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(BlockEntityType.FURNACE, FurnaceBER::new);
    }

}
