package com.levkanter.kettle.color;

import com.levkanter.kettle.app.AppException;

import processing.core.PApplet;

public class ColorOp 
{
	private ColorOp() {}
	
	public static int red(int c) { 
		return (c >> 16) & 0xFF;
	}
	
	public static int green(int c) { 
		return (c >> 8) & 0xFF;
	}
	
	public static int blue(int c) { 
		return c & 0xFF;
	}
	
	public static int alpha(int c) {
		return (c >> 24) & 0xFF;
	}
		
	public static int unhexAndFormat(String hex) {	
		String hexStr;
		
		if (hex.startsWith("0x") || hex.startsWith("0X")) {
			hexStr = "FF" + hex.substring(2);
		} else if (hex.startsWith("#")) {
			hexStr = "FF" + hex.substring(1);
		} else {
			throw new AppException("Hex string " +
					"must be of the format 0x000000 or #000000");
		}
		return PApplet.unhex(hexStr);
	}
		
}
