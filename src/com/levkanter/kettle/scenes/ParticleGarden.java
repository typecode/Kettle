package com.levkanter.kettle.scenes;

import com.levkanter.kettle.color.ColorGenerator;
import com.levkanter.kettle.particles.Particle;

import processing.core.PApplet;
import processing.core.PVector;

public class ParticleGarden 
	extends GLScene
{
	ColorGenerator cgen;
	int pcolor, ccolor;
	
	Particle[] particles;
	PVector[] anchors;
	int gw, gh, gspacing;
	boolean repelMode;
	
	float controlX, controlY;
	
	public ParticleGarden(PApplet app) {
		super(app);
	}
	
	public void setup() {
		super.setup();
		
		cgen = new ColorGenerator(app);
		pcolor = cgen.getRandom();
		ccolor = cgen.lighten(pcolor, 50);
		
		gw = 40;
		gh = 30;
		gspacing = 26;
		
		particles = new Particle[gw*gh];
		anchors = new PVector[gw*gh];
		
		for (int i = 0; i < gw; i += 1) {
			for (int j = 0; j < gh; j+= 1) {
				Particle p = new Particle();
				
				p.drag.set(.08f, .08f, 0);
				p.pos.set(i*gspacing, j*gspacing, 0f);
				
				particles[j*gw + i] = p;
				anchors[j*gw + i] = new PVector(p.pos.x, p.pos.y);
			}
		}
		
		repelMode = true;
	}
	
	public void update() {
		controlX = app.mouseX;
		controlY = app.mouseY;
		
		for (int i = 0; i < gw; i += 1) {
			for (int j = 0; j < gh; j+= 1) {
				Particle p = particles[j*gw + i];
				p.update();
				
				if (repelMode) { 
					p.repel(new PVector(controlX, controlY), 300, 8.5f);
				} else {
					p.attract(new PVector(controlX, controlY), 300, 18.5f);
				}
				
				// restore
				p.acc.add(PVector.div(PVector.sub(anchors[j*gw + i], p.pos), 25f));
			}
		}
	}
	
	void draw2() {
		eraser.erase(gl, true);

		for (int i = 0; i < particles.length; i += 1) {
			Particle p = particles[i];
			gl.fill(pcolor, 200);
			gl.noStroke();
			gl.ellipse(p.pos.x, p.pos.y, 30, 30);
		}
		
		gl.fill(ccolor);
		gl.ellipse(controlX, controlY, 100, 100);
	}
	
	public void keyPressed(int key, int keyCode) {
		if (key == ' ') {
			repelMode = !repelMode;
		}
	}
	
}
