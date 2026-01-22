package com.zkryle.itb;

import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	public static final String MOD_ID = "itb";
	public static final String MOD_NAME = "Inside The Block (ITB)";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final Vector3f FURNACE_TOCOOK = new Vector3f(-0.125F, 0.0F, 0.075F);
	public static final Vector3f FURNACE_COOKED = new Vector3f(0.125F, 0.0F, 0.325F);
	public static final Vector3f FURNACE_FUEL = new Vector3f(0.0F, 0.0F, 0.19F);
	public static final Vector3f FURNACE_FLUID = new Vector3f(-0.175F, 0.0F, -0.12125F);

	public static final Vector3f BFURNACE_TOCOOK = new Vector3f(-0.125F, -0.19F, 0.075F);
	public static final Vector3f BFURNACE_COOKED = new Vector3f(0.125F, -0.19F, 0.3F);
	public static final Vector3f BFURNACE_FUEL = new Vector3f(0.0F, 0.0625F, 0.19F);
	public static final Vector3f BFURNACE_FLUID = new Vector3f(-0.175F, 0.0625F, -0.12125F);

	public static final Vector3f SMOKER_TOCOOK = new Vector3f(0.0F, -0.05F, 0.0F);
	public static final Vector3f SMOKER_COOKED = new Vector3f(0.0F, 0.25F, 0.0F);
	public static final Vector3f SMOKER_FUEL = new Vector3f(0.0F, 0.0F, 0.19F);
	public static final Vector3f SMOKER_FLUID = new Vector3f(-0.175F, 0.0F, -0.12125F);
}