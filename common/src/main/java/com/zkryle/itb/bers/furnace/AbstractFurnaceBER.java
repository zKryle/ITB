package com.zkryle.itb.bers.furnace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public abstract class AbstractFurnaceBER implements BlockEntityRenderer<AbstractFurnaceBlockEntity, FurnaceRenderState> {
    private final ItemModelResolver itemModelResolver;
    private Identifier tempFluid;
    private final Vector3f cookedPos, toCookPos, fuelPos, fluidPos;
    private final boolean isFurnace;

    public AbstractFurnaceBER(BlockEntityRendererProvider.Context context, Vector3f cookedPos, Vector3f toCookPos, Vector3f fuelPos, Vector3f fluidPos, boolean isFurnace){
        itemModelResolver = context.itemModelResolver();
        this.cookedPos = cookedPos;
        this.toCookPos = toCookPos;
        this.fuelPos = fuelPos;
        this.fluidPos = fluidPos;
        this.isFurnace = isFurnace;
    }

    @Override
    public FurnaceRenderState createRenderState() {
        return new FurnaceRenderState();
    }

    @Override
    public void extractRenderState(AbstractFurnaceBlockEntity blockEntity, FurnaceRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.facing = blockEntity.getBlockState().getValue(FurnaceBlock.FACING);

        for (int i = 0; i < blockEntity.getContainerSize(); i++) {
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();

            this.itemModelResolver.updateForTopItem(itemStackRenderState, blockEntity.getItem(i), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, (int) (blockEntity.getBlockPos().asLong() + i));

            switch(i) {
                case 0:
                    renderState.toCookItemStackRenderState = itemStackRenderState;
                    break;
                case 1:
                    renderState.fuelItemStackRenderState = itemStackRenderState;
                    break;
                default:
                case 2:
                    renderState.cookedItemStackRenderState = itemStackRenderState;
                    break;
            }
        }
    }

    @Override
    public void submit(FurnaceRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        int packedLight = LevelRenderer.getLightColor(level, renderState.blockPos.relative(renderState.facing));

        AbstractFurnaceBlockEntity entity = (AbstractFurnaceBlockEntity) level.getBlockEntity(renderState.blockPos);
        ItemStack toCookItem = entity.getItem(0);
        ItemStack cookedItem = entity.getItem(2);
        
        setupAndSubmitFuelItem(renderState.fuelItemStackRenderState, renderState, poseStack, nodeCollector, level, packedLight);
        if(!toCookItem.isEmpty()) {
            Vector3f relParticlePos = new Vector3f(-0.18F, 0.0F, 0.15F);
            renderSmokeParticles(relParticlePos, renderState, level);
            setupAndSubmitToCookItem(renderState.toCookItemStackRenderState, renderState, poseStack, nodeCollector, level, packedLight);
        }
        if(!cookedItem.isEmpty()) {
            Vector3f relParticlePos = new Vector3f( 0.35F, 0.0F, 0.175F);
            renderSmokeParticles(relParticlePos, renderState, level);
            setupAndSubmitCookedItem(renderState.cookedItemStackRenderState, renderState, poseStack, nodeCollector, level, packedLight);
        }
    }

    private void setupAndSubmitCookedItem(ItemStackRenderState toSubmit, FurnaceRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, Level level, int packedLight){
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.575F, 0.5F);
        poseStack.mulPose(Axis.YN.rotationDegrees(90.0F * renderState.facing.get2DDataValue()));
        poseStack.translate(cookedPos.x, cookedPos.y, cookedPos.z);

        poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));

        poseStack.scale(0.35F, 0.35F, 0.35F);
        toSubmit.submit(poseStack, nodeCollector, packedLight, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }

    private void setupAndSubmitToCookItem(ItemStackRenderState toSubmit, FurnaceRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, Level level, int packedLight){
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.575F, 0.5F);
        poseStack.mulPose(Axis.YN.rotationDegrees(90.0F * renderState.facing.get2DDataValue()));
        poseStack.translate(toCookPos.x, toCookPos.y, toCookPos.z);

        poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));

        poseStack.scale(0.35F, 0.35F, 0.35F);
        toSubmit.submit(poseStack, nodeCollector, packedLight, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }

    private void setupAndSubmitFuelItem(ItemStackRenderState toSubmit, FurnaceRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, Level level, int packedLight){
        Identifier lavaTexture = getFluidTextureCommon(Fluids.LAVA);

        if(lavaTexture.getNamespace().equals("minecraft"))
            lavaTexture = Identifier.fromNamespaceAndPath(lavaTexture.getNamespace(), "textures/" + lavaTexture.getPath() + ".png");

        AbstractFurnaceBlockEntity entity = (AbstractFurnaceBlockEntity) level.getBlockEntity(renderState.blockPos);
        ItemStack fuelItem = entity.getItem(1);

        if(fuelItem.getItem() instanceof BucketItem){
            renderFluidQuad(fuelItem, poseStack, nodeCollector, renderState.facing, lavaTexture, packedLight);
            return;
        } else tempFluid = lavaTexture;

        float degCount = 0.0F;
        final float step = 360.0F/10.0F;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.030F, 0.5F);
        poseStack.mulPose(Axis.YN.rotationDegrees(90.0F * renderState.facing.get2DDataValue()));
        poseStack.translate(fuelPos.x, fuelPos.y, fuelPos.z);

        for (int i = 1; i <= 10; i++) {
            poseStack.pushPose();

            poseStack.mulPose(Axis.YP.rotationDegrees(degCount));
            poseStack.mulPose(Axis.XN.rotationDegrees(45.0F));
            poseStack.translate(0.0, 0.0, 0.125F);
            poseStack.scale(0.165F, 0.165F, 0.165F);

            toSubmit.submit(poseStack, nodeCollector, packedLight, OverlayTexture.NO_OVERLAY, 0);

            poseStack.popPose();

            degCount += step;
        }

        poseStack.popPose();
    }

    private void renderFluidQuad(ItemStack bucket, PoseStack poseStack, SubmitNodeCollector nodeCollector, Direction direction, Identifier lavaTexture, int packedLight){
        if(bucket.getItem() instanceof BucketItem bucketItem){
            poseStack.pushPose();

            poseStack.translate(0.5F, 0.0F, 0.5F);
            poseStack.mulPose(Axis.YN.rotationDegrees(90.0F * direction.get2DDataValue()));
            poseStack.translate(0.0F, 0.0F, 0.19F);
            poseStack.translate(fluidPos.x, fluidPos.y, fluidPos.z);

            Identifier fluidTexture = getFluidTextureCommon(bucketItem.getContent());

            if(fluidTexture.getNamespace().equals("minecraft"))
                fluidTexture = Identifier.fromNamespaceAndPath(fluidTexture.getNamespace(), "textures/" + fluidTexture.getPath() + ".png");

            if(tempFluid == null && !fluidTexture.getPath().contains("missingno"))
                tempFluid = fluidTexture;
            else tempFluid = lavaTexture;

            nodeCollector.submitCustomGeometry(poseStack, RenderTypes.text(tempFluid), (pose, consumer) -> {
                    consumer.addVertex(pose, 0.0F, 0.1F, 0.2425F).setColor(-1).setUv(0.0F, 1.0F/20).setLight(packedLight);
            consumer.addVertex(pose, 0.35F, 0.1F, 0.2425F).setColor(-1).setUv(1.0F, 1.0F/20).setLight(packedLight);
            consumer.addVertex(pose, 0.35F, 0.1F, 0.0F).setColor(-1).setUv(1.0F, 0.0F).setLight(packedLight);
            consumer.addVertex(pose, 0.0F, 0.1F, 0.0F).setColor(-1).setUv(0.0F, 0.0F).setLight(packedLight);});

            poseStack.popPose();
        }
    }

    private void renderSmokeParticles(Vector3f relParticlePos, FurnaceRenderState renderState, Level level){
        Vector3f blockPosVec = new Vector3f(renderState.blockPos.getX() + 0.5F, renderState.blockPos.getY() + 0.575F, renderState.blockPos.getZ() + 0.5F);

        relParticlePos = relParticlePos.rotateY((float) Math.toDegrees(90.0F * renderState.facing.get2DDataValue()));

        Vector3f finalVec = blockPosVec.add(relParticlePos);

        if((level.getGameTime() & 0xFF) % ((int)(90 * (Math.random() + 1.0F))) == 0)
            for (int i = 0; i < 10; i++)
                level.addParticle(ParticleTypes.SMOKE, finalVec.x(), finalVec.y(), finalVec.z(), 0.0F, 0.1F, 0.0F);
    }

    protected abstract Identifier getFluidTextureCommon(Fluid fluid);
}
