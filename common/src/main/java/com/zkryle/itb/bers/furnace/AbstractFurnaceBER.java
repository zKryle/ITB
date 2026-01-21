package com.zkryle.itb.bers.furnace;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.joml.Vector3f;

public abstract class AbstractFurnaceBER implements BlockEntityRenderer<AbstractFurnaceBlockEntity> {
    private final ItemRenderer itemRenderer;
    private ResourceLocation tempFluid;
    private final Vector3f cookedPos, toCookPos, fuelPos, fluidPos;
    private final boolean isFurnace;

    public AbstractFurnaceBER(BlockEntityRendererProvider.Context context, Vector3f cookedPos, Vector3f toCookPos, Vector3f fuelPos, Vector3f fluidPos, boolean isFurnace){
        itemRenderer = context.getItemRenderer();
        this.cookedPos = cookedPos;
        this.toCookPos = toCookPos;
        this.fuelPos = fuelPos;
        this.fluidPos = fluidPos;
        this.isFurnace = isFurnace;
    }

    @Override
    public void render(AbstractFurnaceBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        Direction direction = entity.getBlockState().getValue(AbstractFurnaceBlock.FACING);
        int frontPackedLight = LevelRenderer.getLightColor(level, entity.getBlockPos().relative(direction));

        ItemStack toCookItem = entity.getItem(0);
        ItemStack fuelItem = entity.getItem(1);
        ItemStack cookedItem = entity.getItem(2);

        setupAndSubmitFuelItem(entity, fuelItem, direction, poseStack, multiBufferSource, level, frontPackedLight, packedOverlay);
        if(!toCookItem.isEmpty()) {
            Vector3f relParticlePos = new Vector3f(-0.18F, 0.0F, 0.15F);
            if (isFurnace)
                renderSmokeParticles(relParticlePos, direction, entity.getBlockPos(), level);
            setupAndSubmitToCookItem(entity, toCookItem, direction, poseStack, multiBufferSource, level, frontPackedLight, packedOverlay);
        }
        if(!cookedItem.isEmpty()) {
            Vector3f relParticlePos = new Vector3f( 0.35F, 0.0F, 0.175F);
            if (isFurnace)
                renderSmokeParticles(relParticlePos, direction, entity.getBlockPos(), level);
            setupAndSubmitCookedItem(entity, cookedItem, direction, poseStack, multiBufferSource, level, frontPackedLight, packedOverlay);
        }
    }

    private void setupAndSubmitCookedItem(AbstractFurnaceBlockEntity entity, ItemStack itemStack, Direction facing, PoseStack poseStack, MultiBufferSource bufferSource, Level level, int packedLight, int packedOverlay){
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.575F, 0.5F);
        poseStack.mulPose(Axis.YN.rotationDegrees(90.0F * facing.get2DDataValue()));
        poseStack.translate(cookedPos.x, cookedPos.y, cookedPos.z);

        poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));

        poseStack.scale(0.35F, 0.35F, 0.35F);

        this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, level, (int) entity.getBlockPos().asLong());

        poseStack.popPose();
    }

    private void setupAndSubmitToCookItem(AbstractFurnaceBlockEntity entity, ItemStack itemStack, Direction facing, PoseStack poseStack, MultiBufferSource bufferSource, Level level, int packedLight, int packedOverlay){
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.575F, 0.5F);
        poseStack.mulPose(Axis.YN.rotationDegrees(90.0F * facing.get2DDataValue()));
        poseStack.translate(toCookPos.x, toCookPos.y, toCookPos.z);

        poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));

        poseStack.scale(0.35F, 0.35F, 0.35F);

        this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, level, (int) entity.getBlockPos().asLong() + 1);

        poseStack.popPose();
    }

    private void setupAndSubmitFuelItem(AbstractFurnaceBlockEntity entity, ItemStack itemStack, Direction facing, PoseStack poseStack, MultiBufferSource bufferSource, Level level, int packedLight, int packedOverlay){
        ResourceLocation lavaTexture = getFluidTextureCommon((BucketItem) Items.LAVA_BUCKET);

        if(lavaTexture.getNamespace().equals("minecraft"))
            lavaTexture = ResourceLocation.fromNamespaceAndPath(lavaTexture.getNamespace(), "textures/" + lavaTexture.getPath() + ".png");

        if(itemStack.getItem() instanceof BucketItem){
            renderFluidQuad(itemStack, poseStack, bufferSource, facing, lavaTexture, packedLight);
            return;
        } else tempFluid = lavaTexture;

        float degCount = 0.0F;
        final float step = 360.0F/10.0F;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.030F, 0.5F);
        poseStack.mulPose(Axis.YN.rotationDegrees(90.0F * facing.get2DDataValue()));
        poseStack.translate(fuelPos.x, fuelPos.y, fuelPos.z);

        for (int i = 1; i <= 10; i++) {
            poseStack.pushPose();

            poseStack.mulPose(Axis.YP.rotationDegrees(degCount));
            poseStack.mulPose(Axis.XN.rotationDegrees(45.0F));
            poseStack.translate(0.0F, 0.0F, 0.125F);
            poseStack.scale(0.165F, 0.165F, 0.165F);

            this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, level, (int) entity.getBlockPos().asLong() + 2);

            poseStack.popPose();

            degCount += step;
        }

        poseStack.popPose();
    }

    private void renderFluidQuad(ItemStack bucket, PoseStack poseStack, MultiBufferSource bufferSource, Direction direction, ResourceLocation lavaTexture, int packedLight){
        if(bucket.getItem() instanceof BucketItem bucketItem){
            poseStack.pushPose();

            poseStack.translate(0.5F, 0.0F, 0.5F);
            poseStack.mulPose(Axis.YN.rotationDegrees(90.0F * direction.get2DDataValue()));
            poseStack.translate(0.0F, 0.0F, 0.19F);
            poseStack.translate(fluidPos.x, fluidPos.y, fluidPos.z);

            ResourceLocation fluidTexture = getFluidTextureCommon(bucketItem);

            if(fluidTexture.getNamespace().equals("minecraft"))
                fluidTexture = ResourceLocation.fromNamespaceAndPath(fluidTexture.getNamespace(), "textures/" + fluidTexture.getPath() + ".png");

            if(tempFluid == null && !fluidTexture.getPath().contains("missingno"))
                tempFluid = fluidTexture;
            else tempFluid = lavaTexture;

            VertexConsumer consumer = bufferSource.getBuffer(RenderType.text(fluidTexture));

            consumer.addVertex(poseStack.last(), 0.0F, 0.1F, 0.2425F).setColor(-1).setUv(0.0F, 1.0F/20).setUv1(0, 1).setNormal(0, 1, 0).setLight(packedLight);
            consumer.addVertex(poseStack.last(),0.35F, 0.1F, 0.2425F).setColor(-1).setUv(1.0F, 1.0F/20).setUv1(1, 1).setNormal(0, 1, 0).setLight(packedLight);
            consumer.addVertex(poseStack.last(),0.35F, 0.1F, 0.0F).setColor(-1).setUv(1.0F, 0.0F).setUv1(1, 0).setNormal(0, 1, 0).setLight(packedLight);
            consumer.addVertex(poseStack.last(),0.0F, 0.1F, 0.0F).setColor(-1).setUv(0.0F, 0.0F).setUv1(0, 0).setNormal(0, 1, 0).setLight(packedLight);

            poseStack.popPose();
        }
    }

    private void renderSmokeParticles(Vector3f relParticlePos, Direction direction, BlockPos blockPos, Level level){
        Vector3f blockPosVec = new Vector3f(blockPos.getX() + 0.5F, blockPos.getY() + 0.575F, blockPos.getZ() + 0.5F);

        relParticlePos = relParticlePos.rotateY((float) Math.toDegrees(90.0F * direction.get2DDataValue()));

        Vector3f finalVec = blockPosVec.add(relParticlePos);

        if((level.getGameTime() & 0xFF) % ((int)(90 * (Math.random() + 1.0F))) == 0)
            for (int i = 0; i < 10; i++)
                level.addParticle(ParticleTypes.SMOKE, finalVec.x(), finalVec.y(), finalVec.z(), 0.0F, 0.1F, 0.0F);
    }

    protected abstract ResourceLocation getFluidTextureCommon(BucketItem fluid);
}
