package com.zkryle.itb.events;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.zkryle.itb.Constants;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.zkryle.itb.syncing.CommonEvents.renderShape;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameClientEvents {
    @SubscribeEvent
    public static void registerBlockEntityRenderers(RenderHighlightEvent.Block event){
        BlockPos pos = event.getTarget().getBlockPos();
        BlockState state = Minecraft.getInstance().level.getBlockState(pos);
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if(state.is(Blocks.BLAST_FURNACE)){
            VertexConsumer vertexConsumer = event.getMultiBufferSource().getBuffer(RenderType.lines());
            renderShape(event.getPoseStack(), vertexConsumer, state.getShape(Minecraft.getInstance().level, pos, CollisionContext.of(camera.getEntity())), (double)pos.getX() - camera.getPosition().x, (double)pos.getY() - camera.getPosition().y, (double)pos.getZ() - camera.getPosition().z, 0.2F, 0.2F, 0.2F, 1.0F);
            event.setCanceled(true);
        }

        // Make outline solid just for the blast furnace to fix a very annoying bug minecraft has with the border of block models set to translucent RenderType;
    }
}
