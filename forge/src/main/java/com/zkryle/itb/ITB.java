package com.zkryle.itb;

import com.zkryle.itb.networking.ITBPacketHandler;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class ITB {

    public ITB(FMLJavaModLoadingContext context) {
        BusGroup busGroup = context.getModBusGroup();
        FMLCommonSetupEvent.getBus(busGroup).addListener(this::commonSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        ITBPacketHandler.init();
    }
}