package com.levkanter.kettle.particles;

public class FlockControl
{
	public static enum FlockingRule {
		SEPARATION,
		ALIGNMENT,
		COHESION
	}
	
	public float sepRange, aliRange, cohRange,
		sepMultiplier, aliMultiplier, cohMultiplier;
	public int neighborhoodSize;
	
	public FlockControl() {
		setDefaults();
	}
	
	public FlockControl setDefaults() {
		sepRange = 25;
		aliRange = 25;
		cohRange = 25;
		sepMultiplier = 8;
		aliMultiplier = 1;
		cohMultiplier = 1;
		neighborhoodSize = 10;
		return this;
	}
	
	// TODO
	public FlockControl contract() {
		return this;
	}
	
	// TODO
	public FlockControl spread() {
		return this;
	}
	
}