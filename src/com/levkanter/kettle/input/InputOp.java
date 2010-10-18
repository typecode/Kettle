package com.levkanter.kettle.input;

import processing.core.PApplet;

public class InputOp 
{
	private InputOp() {}
	
	/**
	 * Smooth incoming, relative to current, by smoothAmount.
	 * smoothAmount is constrained between 0.0 and 1.0,
	 * where 0.0 means no smoothing and 1.0 means maximum smoothing.
	 * 
	 */
	public static float smooth(float current, float incoming, float smoothAmount) {
		float sm = PApplet.constrain(smoothAmount, 0f, 1f);
		return sm*current + (1 - sm)*incoming;  
	}
	
}
