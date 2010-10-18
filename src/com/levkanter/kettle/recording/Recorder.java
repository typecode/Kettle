package com.levkanter.kettle.recording;

import processing.core.PApplet;
import processing.core.PImage;

abstract public class Recorder 
{
	static String SEPERATOR = "/";
	
	PApplet app;
	int saveCount;
	
	Recorder(PApplet app) {
		this.app = app;
		saveCount = 0;
	}
	
	abstract public void record(PImage im);
	
	public void record() {
		record(app.g);
	}
	
}
