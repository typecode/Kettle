package com.levkanter.kettle.particles;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Particle 
{
	public PVector pos, vel, acc, drag;
	public float velLimit, accLimit;
	int age;
	
	public Particle() {
		pos = new PVector();
		vel = new PVector();
		acc = new PVector();
		drag = new PVector();
		velLimit = -1;
		accLimit = -1;
		age = 0;
	}
	
	public int age() {
		return age;
	}
	
	public Particle update() {
		updatePhysics();
		updateBorders();
		updateAge(1);
		return this;
	}
	
	void updatePhysics() {
		acc.sub(PVector.mult(vel, drag));
		
		if (accLimit > 0) acc.limit(accLimit);
		vel.add(acc);
		
		if (velLimit > 0) vel.limit(velLimit);
		pos.add(vel);
		
		acc.mult(0);
	}
	
	void updateBorders() {
		// intentionally blank for
		// subclasses to implement
	}
	
	void bounceOffWalls(
			float minX, float minY, 
			float maxX, float maxY, 
			float minZ, float maxZ) 
	{
		if (pos.x < minX || pos.x > maxX) {
			pos.x = (pos.x < minX) ? minX : maxX;
			vel.x *= -1*(1 - drag.x);
		}
		if (pos.y < minY || pos.y > maxY) {
			pos.y = (pos.y < minY) ? minY : maxY;
			vel.y *= -1*(1 - drag.y);
		}
		if (pos.z < minZ || pos.z > maxZ) {
			pos.z = (pos.z < minZ) ? minZ : maxZ;
			vel.z *= -1*(1 - drag.z);
		}
	}
	
	void bounceOffWalls(PGraphics g) {
		float gw = (float)g.width;
		float gh = (float)g.height;
		bounceOffWalls(0f, 0f, gw, gh, 0f, 0f);
	}
	
	void bounceOffWalls(PApplet app) {
		bounceOffWalls(app.g);
	}
	
	void wrapAroundWalls(float minX, float minY, 
						float maxX, float maxY, 
						float minZ, float maxZ) 
	{
		if (pos.x < minX) pos.x = maxX;
		if (pos.x > maxX) pos.x = minX;
		
		if (pos.y < minY) pos.y = maxY;
		if (pos.y > maxY) pos.y = minY;
		
		if (pos.z < minZ) pos.z = maxZ;
		if (pos.z > maxZ) pos.z = minZ;
	}
	
	void wrapAroundWalls(PGraphics g) {
		wrapAroundWalls(0f, 0f, (float)g.width, (float)g.height, 0f, 0f);
	}
	
	void updateAge(int amount) {
		age += amount;
	}
	
	public Particle repel(PVector srcPos, 
			float srcRadius, float multiplier) 
	{
		PVector v = PVector.sub(pos, srcPos);
		float d = v.mag();
		
		if (srcRadius > 0 && d < srcRadius) {
			float pct = 1 - (d/srcRadius);
			v.normalize();
			acc.add(PVector.mult(v, multiplier*pct));
		}
		return this;
	}
	
	public Particle attract(PVector srcPos, 
			float srcRadius, float multiplier) 
	{
		return repel(srcPos, srcRadius, -1*multiplier);
	}
	
}
