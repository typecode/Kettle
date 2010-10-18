package com.levkanter.kettle.particles;

import java.util.ArrayList;

public class ClassicParticleSystem 
{	
	
	ArrayList<MortalParticle> particles;
	int capacity;
	
	public ClassicParticleSystem() {
		this(-1);
	}
	
	public ClassicParticleSystem(int capacity) {
		this.capacity = capacity;
		particles = new ArrayList<MortalParticle>();
		if (capacity > 0) particles.ensureCapacity(capacity);
	}
	
	public ClassicParticleSystem update() {
		for (int i = particles.size() - 1; i > -1; i -= 1) {
			MortalParticle p = particles.get(i);
			if (p.isDead()) {
				particles.remove(i);
			} else {
				p.update();
			}
		}
		return this;
	}
		
	public int size() {
		return particles.size();
	}
	
	public ClassicParticleSystem clear() {
		particles.clear();
		return this;
	}
	
	public ClassicParticleSystem add(MortalParticle p) {
		 if (capacity > 0 && particles.size() >  capacity) {
			 overflow(p);
		 } else {
		 	particles.add(p);
		 }
		 return this;
	}
	
	void overflow(MortalParticle p) {
		
	}
	
}
