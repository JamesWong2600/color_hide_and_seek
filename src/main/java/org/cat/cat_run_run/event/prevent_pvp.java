package org.cat.cat_run_run.event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.cat.cat_run_run.Cat_run_run;

import static org.cat.cat_run_run.variable.variable.games_session;

public class prevent_pvp implements Listener {
    private final Cat_run_run plugin;

    public prevent_pvp(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPVP(EntityDamageByEntityEvent event){
        int remaining = (int) Bukkit.getOnlinePlayers().stream()
                .filter(players -> players.getGameMode() == GameMode.ADVENTURE)
                .count();
        if(remaining == 1){
            event.setCancelled(true);
        }
        if(games_session < 3){
            event.setCancelled(true);
        }
        if(event.getDamager() instanceof Player && !event.getDamager().isOp()){
            event.setCancelled(true);
        }
    }
}
