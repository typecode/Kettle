package com.levkanter.kettle.scenes;

import java.util.ArrayList;

import com.levkanter.kettle.gmanip.Mim;
import com.levkanter.kettle.gmanip.Square;
import com.levkanter.kettle.particles.Particle;

import processing.core.PApplet;
import processing.core.PVector;

public class TypeCodeLogo 
	extends GLScene
{
	static final String PATH = "typecode.jpg";
	
	Mim mimTC;
	int nTotal, nVisited;
	
	ArrayList<Particle> particles;
	ArrayList<Square> anchors;
	
	boolean repelMode;
	
	public TypeCodeLogo(PApplet app) {
		super(app);
	}
	
	public void setup() {
		super.setup();
		
		mimTC = new Mim(app, PATH);
		mimTC.threshold(128);
		
		nTotal = mimTC.getCount(Mim.ON);
		nVisited = 0;
		
		particles = new ArrayList<Particle>();
		anchors = new ArrayList<Square>();
		
		repelMode = true;
	}
	
	public void update() {
		if (nVisited < nTotal) {
			Square sq = mimTC.getMaxSquare();
			Particle p = new Particle();
			
			p.pos.set(sq.x, sq.y, 0);
			p.drag.set(.08f, .08f, 0);
			
			particles.add(p);
			anchors.add(sq);
			nVisited += (sq.s*sq.s);
		}
		
		for (int i = 0; i < particles.size(); i += 1) {
			Particle p = particles.get(i);
			Square sq = anchors.get(i);
			
			p.update();
			
			if (repelMode) {
				p.repel(new PVector(app.mouseX, app.mouseY), 100, 6.5f);
			} else {
				p.attract(new PVector(app.mouseX, app.mouseY), 100, 6.5f);
			}
			
			// restore
			p.acc.add(PVector.div(PVector.sub(new PVector(sq.x, sq.y), p.pos), 25f));
		}
	}
	
	void draw2() {
		eraser.erase(gl, true);
		
		gl.fill(240);
		gl.noStroke();
		
		for (int i = 0; i < particles.size(); i += 1) {
			Particle p = particles.get(i);
			Square sq = anchors.get(i);
			gl.rect(p.pos.x, p.pos.y, sq.s, sq.s);
		}
	}
	
	public void keyPressed(int key, int keyCode) {
		if (key == ' ') {
			repelMode = !repelMode;
		}
	}
	
}
