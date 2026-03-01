package org.cat.cat_run_run.event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cat.cat_run_run.Cat_run_run;

import static org.cat.cat_run_run.data_processing.hunter_manager.isHunter;
import static org.cat.cat_run_run.variable.variable.games_session;

public class freeze_hunter_on_countdown implements Listener {

    private final Cat_run_run plugin;

    public freeze_hunter_on_countdown(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (isHunter(event.getPlayer().getUniqueId().toString())
                && games_session == 1) {
            event.setCancelled(true);
        }
    }

}
