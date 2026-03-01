package org.cat.cat_run_run.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cat.cat_run_run.Cat_run_run;
import org.cat.cat_run_run.variable.variable;
import org.jetbrains.annotations.NotNull;

import static org.cat.cat_run_run.data_processing.hunter_manager.isHunter;
import static org.cat.cat_run_run.event.random_teleport.teleportall;
import static org.cat.cat_run_run.variable.variable.games_session;

public class continue_game implements CommandExecutor {
    private final Cat_run_run plugin;

    public continue_game(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("continue")) {
            if(!sender.isOp()){
                sender.sendMessage("§c你不是管理員，無法啓動");
                return false;
            }
            if (games_session != 0) {
                sender.sendMessage("§c比賽已開始");
                return true;
            }
            teleportall();
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if(isHunter(player.getUniqueId().toString())) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 10));
                }
                player.getPlayer().setGameMode(GameMode.SURVIVAL);
            }
            games_session = 1;
            variable.secondsElapsed = 0;
            return true;
        }
        return false;
    }
}
