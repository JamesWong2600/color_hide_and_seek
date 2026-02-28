package org.cat.cat_run_run;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.cat.cat_run_run.command.start;
import org.cat.cat_run_run.event.*;
import org.cat.cat_run_run.scoreboard.scoreboard;
import org.cat.cat_run_run.variable.variable;

import static org.cat.cat_run_run.event.assign_wool_on_join.assignwool;
import static org.cat.cat_run_run.event.cooldown_manager.getRemainingSeconds;
import static org.cat.cat_run_run.variable.variable.delay_cooldown;
import static org.cat.cat_run_run.variable.variable.gameStarted;

public final class Cat_run_run extends JavaPlugin {
    private cooldown_manager cooldown_manager;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new player_join_event(this), this);
        getServer().getPluginManager().registerEvents(new drop_item(this), this);
        getServer().getPluginManager().registerEvents(new click_block_change_skin(this), this);
        getCommand("start").setExecutor(new start(this));


        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (gameStarted) {
                    if(delay_cooldown >= 0){
                        Component titleText = Component.text(delay_cooldown)
                                .color(NamedTextColor.WHITE);
                        player.showTitle(Title.title(titleText, Component.text("")));
                        delay_cooldown--;
                        if(delay_cooldown == 0){
                            Component Ready_text = Component.text("游戲開始")
                                    .color(NamedTextColor.WHITE);
                            player.showTitle(Title.title(Ready_text, Component.text("")));
                            assignwool(player);
                        }
                    }else{
                        variable.secondsElapsed++;
                    }
                }
                String time = String.valueOf(variable.secondsElapsed);
                int cd = (int) getRemainingSeconds(player);
                int remaining = Bukkit.getOnlinePlayers().size();

                // Call the static update method
                scoreboard.update(player, time, cd, remaining);
            }
        }, 0L, 20L); // 20 ticks = 1 second

        cooldown_manager = new cooldown_manager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
