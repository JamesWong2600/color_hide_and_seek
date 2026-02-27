package org.cat.cat_run_run.event;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.cat.cat_run_run.Cat_run_run;

public class drop_item implements Listener {
    private final Cat_run_run plugin;

    public drop_item(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropWool(PlayerDropItemEvent event){
        Item item = event.getItemDrop();
        if(item.equals(Material.WHITE_WOOL) ||
            item.equals(Material.RED_WOOL) ||
                item.equals(Material.BLUE_WOOL)||
                item.equals(Material.GREEN_WOOL) ){
            event.setCancelled(true);
        }
    }

}
