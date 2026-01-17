package com.zkryle.itb.bers;

import com.zkryle.itb.bers.furnace.AbstractFurnaceBER;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class ForgeFurnaceBER extends AbstractFurnaceBER {
    public ForgeFurnaceBER(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Identifier getFluidTextureCommon(Fluid fluid) {
        return IClientFluidTypeExtensions.of(fluid).getStillTexture();
    }
}
