package org.cat.cat_run_run.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.cat.cat_run_run.Cat_run_run;
import org.cat.cat_run_run.scoreboard.scoreboard;

import static org.cat.cat_run_run.event.assign_wool_on_join.assignwool;

public class player_join_event implements Listener {

    private final Cat_run_run plugin;

    public player_join_event(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        scoreboard.setup(event.getPlayer());
    }
}
