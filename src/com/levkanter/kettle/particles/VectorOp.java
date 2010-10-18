package com.levkanter.kettle.particles;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class VectorOp 
{
	private VectorOp() {}
	
	// rotation ----------------------------------------------------
	
	public static void rotateVec2D(PVector vec, float degrees) {
		rotateVec2DR(vec, PApplet.radians(degrees));
	}
	
	public static void rotateVec2DR(PVector vec, float theta) {
		vec.x = vec.x*PApplet.cos(theta) - vec.y*PApplet.sin(theta);
		vec.y = vec.x*PApplet.sin(theta) - vec.y*PApplet.cos(theta);
	}
	
	// particle steering -------------------------------------------
	
	public static PVector steer(Particle p1, Particle p2) {
		return steer(p1, p2.pos);
	}
	
	public static PVector steer(Particle p, PVector to) {
		PVector v = PVector.sub(to, p.pos);
		float d = v.mag();
		if (d <= 0) {
			v.mult(0);
			return v;
		}
		v.normalize();
		v = PVector.sub(v, p.vel);
		return v;
	}
	
	/**
	 *  Flocking
	 *
	 */
	public static PVector steer(Particle p, 
			ArrayList<Particle> neighbors, 
			FlockControl controls) 
	{
		PVector v = new PVector();
		v.add(steer(p, neighbors, controls, FlockControl.FlockingRule.SEPARATION));
		v.add(steer(p, neighbors, controls, FlockControl.FlockingRule.ALIGNMENT));
		v.add(steer(p, neighbors, controls, FlockControl.FlockingRule.COHESION));
		return v;
	}
	
	public static PVector steer(Particle p, 
			ArrayList<Particle> neighbors, 
			FlockControl controls, 
			FlockControl.FlockingRule rule) 
	{
		PVector v = new PVector();
		float range;
		switch (rule) {
			case SEPARATION:
				range = controls.sepRange;
				break;
			case ALIGNMENT:
				range = controls.aliRange;
				break;
			case COHESION:
				range = controls.cohRange;
				break;
			default:
				return v;
		}
		int ncount = 0;
		for (int i = 0; i < neighbors.size(); i += 1) {
			Particle neighbor = neighbors.get(i);
			float dist = p.pos.dist(neighbor.pos);
			if (dist > 0 && dist < range) {
				switch (rule) {
					case SEPARATION:
						PVector diff = PVector.sub(p.pos, neighbor.pos);
						diff.normalize();
						diff.div(dist);
						v.add(diff);
						break;
					case ALIGNMENT:
						v.add(neighbor.vel);
						break;
					case COHESION:
						v.add(neighbor.pos);
						break;
				}
				ncount += 1;
			}
		}
		if (ncount > 0) {
			v.div((float)ncount);
			switch (rule) {
				case COHESION:
					v = steer(p, v);
					break;
			}
		}
		float multiplier = 1;
		switch (rule) {
			case SEPARATION:
				multiplier = controls.sepMultiplier;
				break;
			case ALIGNMENT:
				multiplier = controls.aliMultiplier;
				break;
			case COHESION:
				multiplier = controls.cohMultiplier;
				break;
		}
		v.mult(multiplier);
		return v;
	}
	
}
