package org.cat.cat_run_run.event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.cat.cat_run_run.Cat_run_run;

import static org.cat.cat_run_run.variable.variable.games_session;


public class dead_event implements Listener {

    private final Cat_run_run plugin;

    public dead_event(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerDeathEvent event) {
        if (event.getPlayer().getKiller() != null && event.getPlayer().getKiller().isOp()
                && games_session == 3) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                event.getPlayer().spigot().respawn();
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }, 1L);
        }
    }
}
