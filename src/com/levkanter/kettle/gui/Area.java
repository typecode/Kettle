package com.levkanter.kettle.gui;

import processing.core.PApplet;
import processing.core.PGraphics;

abstract public class Area 
{
	
	public static enum AState {
		DEFAULT, 
		HOVER, 
		FOCUS, 
		ACTIVE,
		DISABLED
	}
	
	PApplet app;
	AState state;
	boolean isVisible;
	float x1, y1, x2, y2, w, h;
	
	public Area(PApplet app, float x, float y, float w, float h) {
		this.app = app;
		setZone(x, y, w, h);
		resetState();
	}
	
	public AState getState() {
		return state;
	}
	
	public float[] getZone() {
		// Get the upper left corner and width and height
		// all together in an array
		
		float[] zone = { x1, y1, w, h };
		return zone;
	}
	
	public void setZone(float x, float y, float w, float h) {
		// Set the coordinates by specifying the
		// upper left corner and its width and height
		
		x1 = x;
		y1 = y;
		x2 = x + w;
		y2 = y + h;
		this.w = w;
		this.h = h;
	}
	
	public void setZone(float x, float y) {
		setZone(x, y, w, h);
	}
	
	public void translate(float tX, float tY) {
		setZone(x1 + tX, y1 + tY);
	}
	
	public void show() {
		setVisible(true);
	}
	
	public void hide() {
		setVisible(false);
	}
	
	public void toggle() {
		if (isVisible) {
			hide();
		} else {
			show();
		}
	}
	
	void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	void resetState() {
		state = AState.DEFAULT;
	}
	
	public void disable() {
		state = AState.DISABLED;
	}
	
	public void enable() {
		if (state == AState.DISABLED) {
			resetState();
		}
	}
	
	public void update() {
		update(app.mouseX, app.mouseY, app.mousePressed);
	}
	
	public void update(float x, float y, boolean shouldActivate) {
		if (isVisible) { 
			if (contains(x, y)) {
				switch (state) {
					case DEFAULT:
						state = AState.HOVER;
						break;
					case FOCUS:
						if (shouldActivate) {
							state = AState.ACTIVE;
						}
						break;
					case HOVER:
						if (shouldActivate) {
							state = AState.FOCUS;
						}
						break;
					default:
						break;
				}
			} else {
				switch (state) {
					case HOVER:
						state = AState.DEFAULT;
						break;
					case ACTIVE:
						state = AState.FOCUS;
						break;
					case FOCUS:
						if (shouldActivate) {
							state = AState.DEFAULT;
						}
						break;
					default:
						break;
				}
			}
		}
	}
	
	boolean contains(float x, float y) {
		return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
	}
	
	abstract public void draw(PGraphics g);
	
	public void draw() {
		draw(app.g);
	}
	
	public void drawPlane(PGraphics g) {
		if (isVisible) {
			g.pushMatrix();
				g.translate(x1, y1);
				g.pushStyle();
					style(g);
					g.rect(0, 0, w, h);
				g.popStyle();
			g.popMatrix();
		}
	}
	
	public void drawPlane() {
		drawPlane(app.g);
	}
	
	void style(PGraphics g) {
		switch (state) {
			case HOVER:
				break;
			case FOCUS:
				break;
			case ACTIVE:
				break;
			case DISABLED:
				break;
			default:
				break;
		}
	}
	
}
