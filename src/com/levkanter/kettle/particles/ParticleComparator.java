package com.levkanter.kettle.particles;

import java.util.Comparator;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 * Used for sorting a group of particles by their
 * respective proximity to an arbitrary position
 * 
 * */
public class ParticleComparator 
	implements Comparator<Particle>
{
	PVector arbitraryPos;
	
	public ParticleComparator() {
		arbitraryPos = new PVector();
	}
	
	public void setArbitraryPos(PVector pos) {
		arbitraryPos.set(pos);
	}
	
	public void setArbitraryPos(PApplet app) {
		setArbitraryPos(app, app.g);
	}
	
	public void setArbitraryPos(PApplet app, PGraphics context) {
		arbitraryPos.set(app.random(0, context.width), app.random(0, context.height), 0);
	}
	
	public int compare(Particle p1, Particle p2) {
		float val1 = PVector.dist(p1.pos, arbitraryPos);
		float val2 = PVector.dist(p2.pos, arbitraryPos);
		
		if (val1 < val2) {
			return -1;
		} 
		if (val1 > val2) {
			return 1;
		}
		return 0;
	}

}
