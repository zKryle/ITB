package com.zkryle.itb.bers;

import com.zkryle.itb.bers.furnace.AbstractFurnaceBER;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.joml.Vector3f;

public class NeoForgeFurnaceBER extends AbstractFurnaceBER {

    public NeoForgeFurnaceBER(BlockEntityRendererProvider.Context context, Vector3f cookedPos, Vector3f toCookPos, Vector3f fuelPos, Vector3f fluidPos, boolean isFurnace) {
        super(context, cookedPos, toCookPos, fuelPos, fluidPos, isFurnace);
    }

    @Override
    protected ResourceLocation getFluidTextureCommon(BucketItem bucketItem) {
        return bucketItem.content == Fluids.EMPTY ? IClientFluidTypeExtensions.of(Fluids.LAVA).getStillTexture() : IClientFluidTypeExtensions.of(bucketItem.content).getStillTexture();
    }
}
