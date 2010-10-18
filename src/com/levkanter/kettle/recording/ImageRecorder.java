package com.levkanter.kettle.recording;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageRecorder
	extends Recorder
{
	static String FORMAT = "png";
	
	String dir, title, format;
	
	public ImageRecorder(PApplet app, String title) {
		this(app, "", title, FORMAT);
	}
	
	public ImageRecorder(PApplet app, String dir, 
						String title, String format) 
	{
		super(app);
		this.dir = dir;
		this.title = title;
		this.format = validateFormat(format);
	}
	
	static String validateFormat(String format) {
		if (format.equalsIgnoreCase("png") || format.equalsIgnoreCase("jpg") 
			|| format.equalsIgnoreCase("tif") || format.equalsIgnoreCase("tga")) 
		{
			return format.toLowerCase();
		}
		return FORMAT;
	}
	
	public void record(PImage im) {
		String prefix = "";
		if (dir.length() > 0) {
			prefix = dir + SEPERATOR;
		}
		im.save(prefix + title + "--" + saveCount + "." + format);
		saveCount += 1;
	}
		
}
