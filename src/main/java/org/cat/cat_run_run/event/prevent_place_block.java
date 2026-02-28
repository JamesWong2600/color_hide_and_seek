package org.cat.cat_run_run.event;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.cat.cat_run_run.Cat_run_run;

import java.net.http.WebSocket;

public class prevent_place_block implements Listener {
    private final Cat_run_run plugin;

    public prevent_place_block(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropWool(BlockPlaceEvent event){
        if(!event.getPlayer().isOp()){
            event.setCancelled(true);

        }
    }
}
