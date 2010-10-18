package com.levkanter.kettle.color;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Eraser 
{
	public int c, a;
	
	public Eraser(int c, int a) {
		this.c = c;
		this.a = PApplet.constrain(a, 0, 255);
	}
	
	public Eraser(int c) {
		this(c, 255);
	}
	
	public void erase(PGraphics g, boolean opaque) {
		if (opaque) {
			g.background(c);
		} else {
			g.pushStyle();
				g.noStroke();
				g.fill(c, a);
				g.rect(0, 0, g.width, g.height);
			g.popStyle();
		}
	}
	
	public void erase(PGraphics g) {
		erase(g, true);
	}
	
	public void raiseAlpha(int amount) {
		a = PApplet.constrain(a + amount, 0, 255);
	}
	
	public void lowerAlpha(int amount) {
		raiseAlpha(-1*amount);
	}
	
}
