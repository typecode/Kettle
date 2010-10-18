package com.levkanter.kettle.scenes;


import com.levkanter.kettle.color.Eraser;

import codeanticode.glgraphics.GLGraphicsOffScreen;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class GLScene 
	extends Scene
{
	GLGraphicsOffScreen gl;
	Eraser eraser;
		
	public GLScene(PApplet app) {
		super(app);
	}
	
	public void setup() {
		gl = new GLGraphicsOffScreen(app, (int)width, (int)height, true, 4);
		eraser = new Eraser(BLACK, 12);
		clearImage();
	}
	
	public void update() {
		
	}
	
	public void draw() {
		gl.beginDraw();
			draw2();
		gl.endDraw();
		renderTo(app.g, 0, 0);
	}
	
	void draw2() {
		
	}
	
	void renderTo(PGraphics destination, int x, int y) {
		destination.image(gl.getTexture(), x, y);
	}
	
	public PImage getImage(boolean opaque) {
		PImage im = app.createImage(gl.width, gl.height, 
				(opaque ? PApplet.RGB : PApplet.ARGB));
		gl.getTexture().getImage(im);
		return im;
	}
	
	public GLScene clearImage() {
		gl.beginDraw();		
			eraser.erase(gl, true);
		gl.endDraw();
		return this;
	}
	
}
