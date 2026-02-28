package org.cat.cat_run_run.data_processing;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static org.apache.logging.log4j.LogManager.getLogger;

public class flush {

    public static void flusher(JavaPlugin plugin, File dataFolder){
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            hunter_manager.save(dataFolder);
            getLogger().info("Hunters data flushed to JSON.");
        }, 200L, 200L);
}}

