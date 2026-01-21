package com.zkryle.itb.events;

import com.zkryle.itb.Constants;
import com.zkryle.itb.networking.handlers.ClientFurnacePayloadHandler;
import com.zkryle.itb.networking.payloads.FurnaceItemSlotsUploadPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(FurnaceItemSlotsUploadPayload.TYPE, FurnaceItemSlotsUploadPayload.STREAM_CODEC, ClientFurnacePayloadHandler::handleDataOnMain);
    }
}
