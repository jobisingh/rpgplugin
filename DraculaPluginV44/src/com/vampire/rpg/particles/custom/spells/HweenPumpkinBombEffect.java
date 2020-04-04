package com.vampire.rpg.particles.custom.spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

import com.vampire.rpg.utils.VamMath;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.ParticleEffect;

public class HweenPumpkinBombEffect extends Effect {

    private Location loc;

    public HweenPumpkinBombEffect(EffectManager effectManager, Location loc) {
        super(effectManager);
        type = EffectType.INSTANT;
        this.loc = loc;
        setLocation(loc);
    }

    @Override
    public void onRun() {
        double x, y, z;
        for (int i = 0; i < 50; i++) {
            int count = 20;
            do {
                count--;
                x = VamMath.randDouble(0, 2);
                y = VamMath.randDouble(0, 2);
                z = VamMath.randDouble(0, 2);
            } while (count >= 0 && x * x + y * y + z * z > 4);
            x -= 1;
            z -= 1;
            loc.add(x, y, z);
            display(Particle.REDSTONE, loc, Color.ORANGE);
            loc.subtract(x, y, z);
        }
    }

}
