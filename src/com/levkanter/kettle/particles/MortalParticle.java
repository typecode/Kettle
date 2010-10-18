package com.levkanter.kettle.particles;

public class MortalParticle
	extends Particle
{
	int lifeSpan;
	boolean isDead;
	
	public MortalParticle(int lifeSpan) {
		super();
		if (lifeSpan > 0) {
			this.lifeSpan = lifeSpan;
		} else {
			lifeSpan = 1;
		}
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public float lifePercent() {
		if (isDead()) {
			return 1f;
		}
		return age/(float)lifeSpan;
	}
	
	public float deathPercent() {
		return 1 - lifePercent();
	}
	
	public Particle update() {
		if (age >= lifeSpan) {
			isDead = true;
			return this;
		}
		return super.update();
	}
	
}