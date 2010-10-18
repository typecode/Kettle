package com.levkanter.kettle.color;

import processing.core.PApplet;

public class ColorGenerator 
{
	PApplet app;
	
	public ColorGenerator(PApplet app) {
		this.app = app;
	}
	
	public int getRandom() {
		int c;
		app.pushStyle();
			app.colorMode(PApplet.RGB, 1, 1, 1);
			c = app.color(app.random(0,1), app.random(0,1), app.random(0,1));
		app.popStyle();
		return c;
	}
	
	public int darken(int c, float amount) {
		int C;
		app.pushStyle();
			app.colorMode(PApplet.HSB, 360, 100, 100);
			C = app.color(app.hue(c), app.saturation(c), 
					PApplet.constrain(app.brightness(c) - amount, 0, 100));
		app.popStyle();
		return C;
	}
	
	public int lighten(int c, float amount) {
		return darken(c, -1*amount);
	}
	
}
