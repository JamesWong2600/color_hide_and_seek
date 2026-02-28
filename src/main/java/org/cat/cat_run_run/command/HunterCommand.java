package org.cat.cat_run_run.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.cat.cat_run_run.Cat_run_run;
import org.jetbrains.annotations.NotNull;

import static org.cat.cat_run_run.data_processing.hunter_manager.addHunter;
import static org.cat.cat_run_run.data_processing.hunter_manager.removeHunter;

public class HunterCommand implements CommandExecutor {
    private final Cat_run_run plugin;

    public HunterCommand(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.isOp()){
            sender.sendMessage("§c你不是管理員，無法使用");
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /hunter <add|remove> <name>", NamedTextColor.RED));
            return true;
        }

        String action = args[0].toLowerCase();
        String targetName = args[1];

        if (action.equals("add")) {
            addHunter(targetName, plugin.getDataFolder());
            sender.sendMessage(Component.text("Added " + targetName + " to Hunters.", NamedTextColor.GREEN));
        } else if (action.equals("remove")) {
            removeHunter(targetName, plugin.getDataFolder());
            sender.sendMessage(Component.text("Removed " + targetName + " from Hunters.", NamedTextColor.YELLOW));
        }

        return true;
    }
}