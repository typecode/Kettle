package com.levkanter.kettle.gmanip;


import processing.core.PApplet;
import processing.core.PImage;

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 * PImage container to facilitate pixel manipulation
 * 
 * */
public class Mim 
{
	public static final int ON = 1;
	public static final int OFF = 0;
	
	PApplet app;
	PImage source;
	int[] manip;
	int size, width, height;
	
	public Mim(PApplet app, String path) {
		this(app, app.loadImage(path));
	}
	
	public Mim(PApplet app, PImage source) {
		this.app = app;
		this.source = source;
		width = source.width; 
		height = source.height;
		size = width*height;
		manip = new int[size];
		clear();
	}
	
	public Mim clear() {
		for (int n = 0; n < size; n += 1) {
			manip[n] = OFF;
		}
		return this;
	}
	
	public Mim clear(int x, int y, int s) {
		if (s == 1) {
			manip[y*width + x] = OFF;
			return this;
		}
		for (int i = x; i < x + s; i += 1) {
			for (int j = y; j < y + s; j++) {
				try { 
					manip[j*width + i] = OFF;
				} catch (ArrayIndexOutOfBoundsException aiobe) {
					// ignore
				}
			}
		}
		return this;
	}
	
	public Mim clearCircle(int x, int y, int r) {
		if (r == 1) {
			manip[y*width + x] = OFF;
			return this;
		}
		
		int north = y - r;
		int east = x + r;
		int south = y + r;
		int west = x - r;
		
		for (int i = north; i < south; i += 1) {
			for (int j = west; j < east; j += 1) {
				float d = PApplet.sqrt((i - y)*(i - y) + (j - x)*(j - x));
				try { 
					if (d < r && manip[i*width + j] == ON) {
						manip[i*width + j] = OFF;
					}
				} catch (ArrayIndexOutOfBoundsException aiobe) {
					// ignore
				}
			}
		}
		return this;
	}
		
	public Mim copySource() {
		source.loadPixels();
		for (int i = 0; i < size; i += 1) {
			manip[i] = source.pixels[i];
		}
		return this;
	}
	
	public Mim threshold(int threshold) {
		source.loadPixels();
		for (int i = 0; i < size; i += 1) {
			manip[i] = (app.brightness(source.pixels[i]) > threshold) ? OFF : ON;
		}
		return this;
	}
		
	public int getAvgBrightness() {
		int sum = 0;
		float avg = 0;
		source.loadPixels();
		for (int i = 0; i < size; i += 1) {
			sum += app.brightness(source.pixels[i]);
		}
		avg = sum / (size > 0 ? (float)size : 1f);
		return PApplet.round(avg);
	}
	
	public int getCount(int valueOfInterest) {
		return getCount(manip, valueOfInterest);
	}
	
	public static int getCount(int[] array, int valueOfInterest) {
		int count = 0;
		for (int n = 0; n < array.length; n += 1) {
			if (array[n] == valueOfInterest) {
				count += 1;
			}
		}
		return count;
	}
	
	public int[] getNeighbors(int x, int y) { 
		int[] neighbors = new int[8];
		
		int x_m_1 = PApplet.max(0, x - 1); 
		int x_p_1 = PApplet.min(width - 1, x + 1); 
		int y_m_1 = PApplet.max(0, y - 1); 
		int y_p_1 = PApplet.min(height - 1, y + 1);
        
		neighbors[0] = (x_m_1*width + x);
		neighbors[1] = (y_m_1*width + x_m_1);
		neighbors[2] = (y*width + x_m_1);
		neighbors[3] = (y_p_1*width + x_m_1); 
		neighbors[4] = (y_p_1*width + x);
		neighbors[5] = (y_p_1*width + x_p_1);
		neighbors[6] = (y*width + x_p_1);
		neighbors[7] = (y_m_1*width + x_p_1);
        
		return neighbors;
	}
	
	public Square getMaxSquare() {
		Square max = new Square();
		
		for (int i = 0; i < width;i += 1) {
			for (int j = 0; j < height; j += 1) {
				if (manip[j*width + i] == ON) {
					int s = 1, nextS = 2;
					
					while (canMakeSquare(i, j, nextS)) {
						s += 1;
						nextS = s + 1;
					}
					if (s >= max.s) {
						max.s = s;
						max.x = i;
						max.y = j;
					}
				}
			}
		}
		if (max.s > 0) {
			clear(max.x, max.y, max.s);
		}
		return max;
	}
	
	boolean canMakeSquare(int x, int y, int s) {
		for (int i = x; i < x + s; i += 1) {
			for (int j = y; j < y + s; j += 1) {
				if (i + s >= width || j + s >= height || 
						manip[j*width + i] == OFF) 
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public Circle getMaxCircle() {
		Circle max = new Circle();
		
		for (int i = 0; i < width; i += 1) {
			for (int j = 0; j < height; j += 1) {
				if (manip[j*width + i] == ON) {
					int r = 1, nextR = 2;
					
					while (canMakeCircle(i, j, nextR)) {
						r += 1;
						nextR = r + 1;
					}
					if (r >= max.r) {
						max.r = r;
						max.x = i;
						max.y = j;
					}
				}
			}
		}
		if (max.r > 0) {
			clearCircle(max.x, max.y, max.r);
		}
		return max;
	}
	
	boolean canMakeCircle(int x, int y, int r) {
		int north = y - r;
		int east = x + r;
		int south = y + r;
		int west = x - r;
		
		for (int i = north; i < south; i += 1) {
			for (int j = west; j < east; j += 1) {
				float d = PApplet.sqrt((i - y)*(i - y) + (j - x)*(j - x));
				try {
					if (d < r && manip[i*width + j] == OFF) {
						return false;
					}
				} catch (ArrayIndexOutOfBoundsException aiobe) {
					// ignore
				}
			}
		}
		return true;
	}
	
}
