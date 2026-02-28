package org.cat.cat_run_run.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.cat.cat_run_run.Cat_run_run;
import org.cat.cat_run_run.variable.variable;
import org.jetbrains.annotations.NotNull;

import static org.cat.cat_run_run.variable.variable.gameStarted;

public class start implements CommandExecutor {

    private final Cat_run_run plugin;

    public start(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("start")) {
            if(!sender.isOp()){
                sender.sendMessage("§c你不是管理員，無法啓動");
                return false;
            }
            if (gameStarted) {
                sender.sendMessage("§c比賽已開始");
                return true;
            }
            gameStarted = true;
            variable.secondsElapsed = 0;
            Bukkit.broadcast(net.kyori.adventure.text.Component.text("§a比賽已開始"));
            return true;
        }
        return false;
    }
}
