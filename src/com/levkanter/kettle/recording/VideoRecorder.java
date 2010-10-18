package com.levkanter.kettle.recording;

import processing.core.PApplet;
import processing.core.PImage;
import processing.video.MovieMaker;

public class VideoRecorder
	extends Recorder
{
	static int CODEC = MovieMaker.ANIMATION;
	static int QUALITY = MovieMaker.LOSSLESS;
	static int FPS = 30;
	
	public MovieMaker movie;
	
	public VideoRecorder(PApplet app, String dir, String title) {
		super(app);
		movie = new MovieMaker(app, app.width, app.height, 
					dir + "/" + title + ".mov", 
					FPS, CODEC, QUALITY);
	}
	
	public void record(PImage im) {
		im.loadPixels();
		movie.addFrame(im.pixels, im.width, im.height);
		saveCount += 1;
	}
	
	public void finish() {
		movie.finish();
	}

}
