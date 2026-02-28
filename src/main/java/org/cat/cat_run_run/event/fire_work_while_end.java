package org.cat.cat_run_run.event;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class fire_work_while_end {
    public static void spawn_firework(Player player){
        Location loc = player.getLocation();

        // Spawn the firework entity
        Firework firework = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        // Create the effect
        FireworkEffect effect = FireworkEffect.builder()
                .flicker(true)
                .withColor(Color.GREEN)
                .withFade(Color.WHITE)
                .with(FireworkEffect.Type.BALL_LARGE)
                .trail(true)
                .build();

        meta.addEffect(effect);
        meta.setPower(1); // How high it flies (1 is approx 12-20 blocks)

        firework.setFireworkMeta(meta);
    }
}
