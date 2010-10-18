package com.levkanter.kettle.gmanip;

import processing.core.PImage;

public class GraphicsOp 
{
	private GraphicsOp() {}
	
	public static void copy(PImage source, PImage destination) {
		int sW = source.width, sH = source.height; 
		int dW = destination.width, dH = destination.height;
		destination.copy(source, 0, 0, sW, sH, 0, 0, dW, dH);
	}
	
	/**
	 * Make all the pixels color c
	 * 
	 */
	public static void fill(PImage im, int c) {
		im.loadPixels();
		for (int i = 0; i < im.pixels.length; i += 1) { 
			im.pixels[i] = c;
		}
		im.updatePixels();
	}
	
	/**
	 * Make all the pixels transparent
	 * 
	 */
	public static void fillT(PImage im) {
		fill(im, 0);
	}
	
}
