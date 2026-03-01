package org.cat.cat_run_run;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.cat.cat_run_run.command.HunterCommand;
import org.cat.cat_run_run.command.start;
import org.cat.cat_run_run.event.*;
import org.cat.cat_run_run.scoreboard.scoreboard;
import org.cat.cat_run_run.variable.variable;

import java.io.File;

import static org.cat.cat_run_run.data_processing.flush.flusher;
import static org.cat.cat_run_run.data_processing.hunter_manager.isHunter;
import static org.cat.cat_run_run.data_processing.hunter_manager.loadHunters;
import static org.cat.cat_run_run.data_processing.player_status_manager.loadColors;
import static org.cat.cat_run_run.event.assign_skin_on_start.assignskin;
import static org.cat.cat_run_run.event.assign_sword_to_hunter.assignHunterLoadout;
import static org.cat.cat_run_run.event.assign_wool_on_join.assignwool;
import static org.cat.cat_run_run.event.cooldown_manager.getRemainingSeconds;
import static org.cat.cat_run_run.event.fire_work_while_end.spawn_firework;
import static org.cat.cat_run_run.init.init_world_wool.generateSmoothly;
import static org.cat.cat_run_run.variable.variable.*;

public final class Cat_run_run extends JavaPlugin {
    private cooldown_manager cooldown_manager;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        File hunter_file = new File(getDataFolder(), "hunters.json");
        if (!hunter_file.exists()) {
            saveResource("hunters.json", false);
        }

        File player_color_file = new File(getDataFolder(), "player_colors.json");
        if (!player_color_file.exists()) {
            saveResource("player_colors.json", false);
        }
        loadColors(getDataFolder());

        loadHunters(getDataFolder(), getLogger());
        flusher(this, getDataFolder());
        getServer().getPluginManager().registerEvents(new player_join_event(this), this);
        getServer().getPluginManager().registerEvents(new drop_item(this), this);
        getServer().getPluginManager().registerEvents(new click_block_change_skin(this), this);
        getServer().getPluginManager().registerEvents(new dead_event(this), this);
        getServer().getPluginManager().registerEvents(new prevent_place_block(this), this);
        getServer().getPluginManager().registerEvents(new prevent_pvp(this), this);
        getServer().getPluginManager().registerEvents(new disable_fall_damage(this), this);
        getServer().getPluginManager().registerEvents(new freeze_hunter_on_countdown(this), this);

        getCommand("start").setExecutor(new start(this));
        getCommand("hunter").setExecutor(new HunterCommand(this));
        generateSmoothly(this);
        World world = Bukkit.getWorld("world"); // Replace with your world name
        if (world != null) {
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (games_session == 1) {
                    if (delay_cooldown >= 0) {
                        Component titleText = Component.text(delay_cooldown)
                                .color(NamedTextColor.WHITE);
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.showTitle(Title.title(titleText, Component.text("")));
                        }
                        delay_cooldown--;
                        if (delay_cooldown == 0) {
                            games_session = 3;
                            Bukkit.broadcast(net.kyori.adventure.text.Component.text("§a比賽已開始"));
                            Component Ready_text = Component.text("游戲開始")
                                    .color(NamedTextColor.WHITE);
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.showTitle(Title.title(Ready_text, Component.text("")));


                            }
                            for (Player assign_skin_player : Bukkit.getOnlinePlayers()) {
                                if (!isHunter(assign_skin_player.getUniqueId().toString())){
                                  assignwool(assign_skin_player);
                                  assignskin(assign_skin_player, Cat_run_run.this);
                                }else{
                                    assignHunterLoadout(assign_skin_player);
                                }
                            }
                        }
                    }
                } else if (games_session == 3) {
                    variable.secondsElapsed++;
                }
                String time = String.valueOf(variable.secondsElapsed);
                int remaining = 0;
                if(games_session == 3){
                    remaining = (int) Bukkit.getOnlinePlayers().stream()
                        .filter(players -> players.getGameMode() == GameMode.SURVIVAL && !isHunter(players.getUniqueId().toString()))
                        .count();
                }else{
                    remaining = (int) Bukkit.getOnlinePlayers().stream()
                            .filter(players -> players.getGameMode() == GameMode.ADVENTURE && !isHunter(players.getUniqueId().toString()))
                            .count();
                }
                if (remaining <= 1 && games_session == 3) {

                    String winnerName = Bukkit.getOnlinePlayers().stream()
                            .filter(p -> p.getGameMode() == GameMode.SURVIVAL && !isHunter(p.getUniqueId().toString()))
                            .map(Player::getName) // Convert Player object to String (Name)
                            .findFirst()          // Get the first one found
                            .orElse("None");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        Component Winner_text = Component.text("最終贏家: " + winnerName)
                                .color(NamedTextColor.GREEN);
                        player.showTitle(Title.title(Winner_text, Component.text("")));
                        spawn_firework(player);
                    }
                    games_session = 4;
                    this.cancel();

                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    // Call the static update method
                    int cd = (int) getRemainingSeconds(player);
                    scoreboard.update(player, time, cd, remaining);
                }
            }}.

            runTaskTimer(this,0L,20L);
        cooldown_manager = new cooldown_manager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
