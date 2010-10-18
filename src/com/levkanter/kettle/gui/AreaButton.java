package com.levkanter.kettle.gui;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;

public class AreaButton 
	extends Area
	implements Eventual<AreaButton>
{
	
	int triggerEnergy, maxEnergy;
	boolean toFire;
	
	static final String DEFAULT_EVENT_NAME = "AREA_BUTTON_FIRED";
	String eventName;
	ArrayList< EHandler<AreaButton> > handlers;
	
	public AreaButton(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		triggerEnergy = 0;
		setFireOnRelease();
		handlers = new ArrayList< EHandler<AreaButton> >(5);
		eventName = DEFAULT_EVENT_NAME;
	}
	
	public boolean toFire() {
		return toFire;
	}
	
	public AreaButton setFireOn(int maxEnergy) {
		this.maxEnergy = maxEnergy;
		return this;
	}
	
	public AreaButton setFireOnRelease() {
		return setFireOn(-1);
	}
	
	public void update(float x, float y, boolean shouldActivate) {
		super.update(x, y, shouldActivate);
		if (state == AState.ACTIVE) {
			if (shouldActivate) {
				triggerEnergy += 1;
				if (maxEnergy > 0 && triggerEnergy > maxEnergy) {
					toFire = true;
				}
			} else {
				toFire = true;
			}
		} else {
			triggerEnergy = 0;
			toFire = false;
		}
		if (toFire) {
			Eve<AreaButton> event = new Eve<AreaButton>(this, eventName);
			for (int i = 0; i < handlers.size(); i += 1) {
				handlers.get(i).handle(event);
			}
			resetState();
		}
	}
	
	public void draw(PGraphics g) {
		
	}
	
	public AreaButton addHandler(EHandler<AreaButton> handler) {
		handlers.add(handler);
		return this;
	}
	
	public AreaButton clearHandlers() {
		handlers.clear();
		return this;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
}
