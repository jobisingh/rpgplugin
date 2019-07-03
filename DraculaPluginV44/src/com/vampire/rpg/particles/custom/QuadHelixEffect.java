package com.vampire.rpg.particles.custom;

import org.bukkit.Location;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.ParticleEffect;

public class QuadHelixEffect extends Effect {
	private static final ParticleEffect particle = ParticleEffect.END_ROD;
	public static int strands = 4;
    public static int particles = 120;
    public static float radius = 1.3F;
    public static float curve = 2.0F;
    public static double rotation = Math.PI / 4;
    
    private float stepX = 0;
	private float stepY = 0;
	private boolean reverse = false;
    private static final double START_Y = 1.5;

    private int step = 0;
    double currY = START_Y; // start height
    private static int perTick = 4;
	public QuadHelixEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
        period = 1;
        iterations = -1;
	}

	@Override
	public void onRun() {
		  Location location = getLocation();
		  location.subtract(0, 0.5, 0);
		  for (int j = step; j <= step + perTick; j++) {
			  for (int i = 1; i <= strands; i++) {
				double dx = -(Math.cos((stepX / 90) * (Math.PI * 2) + ((Math.PI / 2) * i))) * ((60 - Math.abs(stepY)) / 60);
				double dy = ((stepY) / 60) * 1.5;
				double dz = -(Math.sin((stepX / 90) * (Math.PI * 2) + ((Math.PI / 2) * i))) * ((60 - Math.abs(stepY)) / 60);
				location.add(dx, dy, dz);
                display(particle, location);
                location.subtract(dx, dy, dz);
			  }
		  }
		  stepX++;
			if (stepX > 90) {
				stepX = 0;
			}
			if (reverse) {
				stepY++;
				if (stepY > 60) reverse = false;
			} else {
				stepY--;
				if (stepY < -60) reverse = true;
			}
		/*  for (int j = step; j <= step + perTick; j++) {
	            currY -= 0.025d;
	            for (int i = 1; i <= strands; i++) {
	                float ratio = ((float) j) / particles;
	                double angle = curve * ratio * 2.0f * Math.PI / strands + 2.0f * Math.PI * i / strands + rotation;
	                double x = Math.cos(angle) * ratio * radius;
	                double z = Math.sin(angle) * ratio * radius;
	                double y = currY;
	                location.add(x, y, z);
	                display(particle, location);
	                location.subtract(x, y, z);
	            }
	        }
	        if (step > particles - perTick) {
	            step = 0;
	            currY = START_Y;
	        } else {
	            step += perTick;
	        }*/
	}

}
