package org.cat.cat_run_run.event;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
        if(!event.getPlayer().isOp()){
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        }
        scoreboard.setup(event.getPlayer());
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 10));
    }
}
