package com.zkryle.itb.bers.furnace;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class FurnaceRenderState extends BlockEntityRenderState {
    public ItemStackRenderState fuelItemStackRenderState = null;
    public ItemStackRenderState toCookItemStackRenderState = null;
    public ItemStackRenderState cookedItemStackRenderState = null;
    public Direction facing = Direction.NORTH;
}
