package org.cat.cat_run_run.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.cat.cat_run_run.Cat_run_run;

public class disable_fall_damage implements Listener {

    private final Cat_run_run plugin;

    public disable_fall_damage(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        // 1. Check if it's a player
        if (!(event.getEntity() instanceof Player)) return;

        // 2. Check if the cause is falling
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            // 3. Cancel the damage
            event.setCancelled(true);
        }
    }
}
