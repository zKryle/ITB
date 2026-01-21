package com.zkryle.itb.syncing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CommonEvents {
    public static void renderShape(PoseStack poseStack, VertexConsumer consumer, VoxelShape shape, double x, double y, double z, float red, float green, float blue, float alpha) {
        PoseStack.Pose posestack$pose = poseStack.last();
        shape.forAllEdges((p_323073_, p_323074_, p_323075_, p_323076_, p_323077_, p_323078_) -> {
            float f = (float)(p_323076_ - p_323073_);
            float f1 = (float)(p_323077_ - p_323074_);
            float f2 = (float)(p_323078_ - p_323075_);
            float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
            f /= f3;
            f1 /= f3;
            f2 /= f3;
            consumer.addVertex(posestack$pose, (float)(p_323073_ + x), (float)(p_323074_ + y), (float)(p_323075_ + z)).setColor(red, green, blue, alpha).setNormal(posestack$pose, f, f1, f2);
            consumer.addVertex(posestack$pose, (float)(p_323076_ + x), (float)(p_323077_ + y), (float)(p_323078_ + z)).setColor(red, green, blue, alpha).setNormal(posestack$pose, f, f1, f2);
        });
    }
}
