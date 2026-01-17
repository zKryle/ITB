package com.zkryle.itb.bers;

import com.zkryle.itb.bers.furnace.AbstractFurnaceBER;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class NeoForgeFurnaceBER extends AbstractFurnaceBER {
    public NeoForgeFurnaceBER(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Identifier getFluidTextureCommon(Fluid fluid) {
        return IClientFluidTypeExtensions.of(fluid).getStillTexture();
    }
}
