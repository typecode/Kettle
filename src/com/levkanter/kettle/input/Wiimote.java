package com.levkanter.kettle.input;

import java.util.HashMap;

import com.levkanter.kettle.app.AppException;

import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;
import processing.xml.XMLElement;

public class Wiimote 
	implements WiimoteResponder
{
	static final int WAIT_TIME = 300;
	
	PApplet app;
	
	int inPort, outPort;
	
	OscP5 dataHandler;
	NetAddress outAddr;
	HashMap<String, String> addrSpace;
	
	boolean isConnected;
	float batteryLevel;
	int elapseCounter;
	
	boolean isPressedHome;
	boolean isPressedA;
	boolean isPressedB;
	boolean isPressed1;
	boolean isPressed2;
	boolean isPressedMinus;
	boolean isPressedPlus;
	boolean isPressedUp;
	boolean isPressedDown;
	boolean isPressedLeft;
	boolean isPressedRight;
	
	float pitch;
	float roll;
	float yaw;
	float accel;
	
	public Wiimote(PApplet app, XMLElement config) {
		this.app = app;
		addrSpace = new HashMap<String, String>();
		try {
			inPort = config.getChild("port").getIntAttribute("in");
			outPort = config.getChild("port").getIntAttribute("out");
			
			XMLElement addrData = config.getChild("addresses");
			for (int i = 0; i < addrData.getChildCount(); i += 1) {
				XMLElement addr = addrData.getChild(i); 
				addrSpace.put(addr.getName(), addr.getContent().trim());
			}
			
			dataHandler = new OscP5(this, inPort);
			outAddr = new NetAddress("localhost", outPort);
			
			plugResponder(this);
			plug(this, "irx", "x"); 
			plug(this, "iry", "y");
			
			plug(this, "touchPitch", "pitch");
			plug(this, "touchRoll", "roll"); 
			plug(this, "touchYaw", "yaw"); 
			plug(this, "touchAccel", "accel");
			
			elapseCounter = 0;
			
		} catch (Exception ex) {
			throw new AppException("Unable to initialize Wiimote", ex);
		}
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public void query(String addr, String command) {
		OscMessage query = new OscMessage(addr);
		query.add(command);
		dataHandler.send(query, outAddr);
	}
	
	public void plug(Object responder, String methodName, String eventName) { 
		dataHandler.plug(responder, methodName, addrSpace.get(eventName));
	}
	
	public void plugResponder(WiimoteResponder responder) {
		plug(responder, "touchHome", "home");
		plug(responder, "touchA", "A");
		plug(responder, "touchB", "B");
		plug(responder, "touch1", "1");
		plug(responder, "touch2", "2");
		plug(responder, "touchMinus", "minus");
		plug(responder, "touchPlus", "plus");
		plug(responder, "touchUp", "up");
		plug(responder, "touchDown", "down");
		plug(responder, "touchLeft", "left");
		plug(responder, "touchRight", "right");
	}
	
	public void oscEvent(OscMessage inMessage) {
		if (inMessage.isPlugged()) {
			elapseCounter = 0;
			if (!isConnected) {
				isConnected = true;
			}
		} else {
			
		}
	}
	
	public void update() {
		if (isConnected) {
			elapseCounter += 1;
			
		}
	}
	
	public void irx(float inVal) {
		
	}
	
	public void iry(float inVal) {
		
	}
	
	public void touchPitch(float inVal) {
		pitch = inVal;
	}
	
	public void touchRoll(float inVal) {
		roll = inVal;
	}
	
	public void touchYaw(float inVal) {
		yaw = inVal;
	}
	
	public void touchAccel(float inVal) {
		accel = inVal;
	}
	
	public void touchHome(int pressFlag) { 
		isPressedHome = button(pressFlag); 
	}
	
	public void touchA(int pressFlag) { 
		isPressedA = button(pressFlag); 
	}
	
	public void touchB(int pressFlag) { 
		isPressedB = button(pressFlag); 
	}
	
	public void touch1(int pressFlag) { 
		isPressed1 = button(pressFlag); 
	}
	
	public void touch2(int pressFlag) { 
		isPressed2 = button(pressFlag); 
	}
	
	public void touchMinus(int pressFlag) { 
		isPressedMinus = button(pressFlag); 
	}
	
	public void touchPlus(int pressFlag) { 
		isPressedPlus = button(pressFlag);
	}
	
	public void touchUp(int pressFlag) { 
		isPressedUp = button(pressFlag); 
	}
	
	public void touchDown(int pressFlag) { 
		isPressedDown = button(pressFlag); 
	}
	
	public void touchLeft(int pressFlag) { 
		isPressedLeft = button(pressFlag);
	}
	
	public void touchRight(int pressFlag) { 
		isPressedRight = button(pressFlag); 
	}
	
	boolean button(int pressFlag) {
		return (pressFlag > 0) ? true : false;
	}
	
}
