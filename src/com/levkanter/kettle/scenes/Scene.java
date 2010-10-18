package com.levkanter.kettle.scenes;


import com.levkanter.kettle.color.ColorPresets;

import processing.core.PApplet;

abstract public class Scene
	implements ColorPresets
{
	PApplet app;
	float width, height;
	private boolean isActive;
	
	public Scene(PApplet app) {
		this.app = app;
		width = app.width;
		height = app.height;
		activate();
		setup();
	}
	
	public void activate() {
		isActive = true;
	}
	
	public void deactivate() {
		isActive = false;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	abstract public void setup();
	abstract public void update();
	abstract public void draw();
	
	// These are intentionally not abstract
	// Scenes can choose to override them:
	
	public void keyPressed(int key, int keyCode) {}
	public void keyReleased(int key, int keyCode) {}
	public void mousePressed() {}
	public void mouseReleased() {}
	public void mouseDragged() {}
	
	public void stop() {}

}
