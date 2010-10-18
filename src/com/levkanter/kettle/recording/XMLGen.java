package com.levkanter.kettle.recording;

import java.io.PrintWriter;
import java.util.ArrayList;
import processing.core.PApplet;

public class XMLGen 
{
	
	String tabsString(int n) {
		if (n < 1) {
			return "";
		}
		StringBuilder s = new StringBuilder("");
		while (n > 0) {
			s.append("\t");
			n -= 1;
		}
		return s.toString();
	}
	
	public static class Attribute 
	{
		String key, value;
		
		Attribute(String key, String value) {
			this.key = key;
			this.value = value;
		}
		
		public String toString() {
			return toString(true);
		}
		
		public String toString(boolean addSpace) {
			String str = key + "='" + value + "'";
			if (addSpace) {
				str += " ";
			}
			return str;
		}
	}
	
	public XMLGen.Attribute createAttribute(String key, String value) {
		return new XMLGen.Attribute(key, value);
	}
	
	abstract class XMLComponent 
	{
		String name;
		ArrayList<XMLGen.Attribute> attributes;
		XMLBlock parent;
		int tabLevel;
		
		XMLComponent(String name) {
			this.name = name;
			attributes = new ArrayList<XMLGen.Attribute>(3);
			parent = null;
			tabLevel = 0;
		}
		
		void addAttribute(String k, String v) {
			addAttribute(new XMLGen.Attribute(k, v));
		}
		
		void addAttribute(XMLGen.Attribute attr) {
			attributes.add(attr);
		}
		
		String attributesString() {
			StringBuilder s = new StringBuilder("");
			for (int i = 0; i < attributes.size(); i += 1) {
				if (i == attributes.size() - 1) {
					s.append(attributes.get(i).toString(false));
				} else {
					s.append(attributes.get(i).toString(true));
				}
			}
			return s.toString();
		}
		
		String openString() {
			StringBuilder s = new StringBuilder(tabsString(tabLevel) + "<" + name);
			if (attributes.size() > 0) {
				s.append(" " + attributesString());
			}
			s.append(">");
			return s.toString();
		}
		
		String closeString() {
			return "</" + name + ">\r\n";
		}
	}
	
	class XMLUnit 
		extends XMLComponent 
	{ 
		Object content;
		
		XMLUnit(String name, Object content) {
			super(name);
			this.content = content;
		}
		
		public String toString() {
			return openString() + content.toString() + closeString();
		}
	}
	
	class XMLBlock
		extends XMLComponent
	{
		ArrayList<XMLComponent> children;
		
		XMLBlock(String name) {
			super(name);
			children = new ArrayList<XMLComponent>();
		}
		
		void addChild(XMLComponent child) {
			child.parent = this;
			child.tabLevel = tabLevel + 1; 
			children.add(child);
		}
		
		String openString() {
			return super.openString() + "\r\n";
		}
		
		String closeString() {
			return tabsString(tabLevel) + super.closeString();
		}
		
		public String toString() {
			StringBuilder s = new StringBuilder(openString());
			for (int i = 0; i < children.size(); i += 1)
				s.append(children.get(i).toString());
			s.append(closeString());
			return s.toString();
		}
	}
	
	PrintWriter scribe;
	
	XMLBlock masterBlock, currentBlock; 
	
	public XMLGen(PApplet app, String title) {
		this(app, title, null);
	}
	
	public XMLGen(PApplet app, String title, String description) {
		scribe = app.createWriter(title + ".xml");
		scribe.println("<?xml version='1.0'?>\r\n");
		if (description != null) {
			scribe.println("<!-- " + description + " -->\r\n");
		}
		masterBlock = new XMLBlock(title);
		currentBlock = masterBlock;
	}
	
	public XMLGen pushTag(String name) {
		XMLBlock tag = new XMLBlock(name);
		currentBlock.addChild(tag);
		currentBlock = tag;
		return this;
	}
		
	public XMLGen popTag() {
		if (currentBlock.parent != null) {
			currentBlock = currentBlock.parent;
		}
		return this;
	}
	
	public XMLGen popToRoot() {
		currentBlock = masterBlock;
		return this;
	}
	
	public XMLGen addUnit(String name, Object content) {
		return addUnit(name, content, null);
	}
	
	public XMLGen addUnit(String name, XMLGen.Attribute[] attributes) {
		return addUnit(name, "", attributes);
	}
	
	public XMLGen addUnit(String name, Object content, 
						XMLGen.Attribute[] attributes) 
	{
		XMLUnit unit = new XMLUnit(name, content);
		if (attributes != null) {
			for (int i = 0; i < attributes.length; i += 1) {
				unit.addAttribute(attributes[i]);
			}
		}
		currentBlock.addChild(unit);
		return this;
	}
	
	public void printout() {
		scribe.println(masterBlock.toString());
		scribe.flush();
		scribe.close();
	}
	
}