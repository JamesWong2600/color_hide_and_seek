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
        if(item.equals(Material.GOLD_BLOCK) ||
            item.equals(Material.REDSTONE_BLOCK) ||
                item.equals(Material.LAPIS_BLOCK)||
                item.equals(Material.EMERALD_BLOCK) ){
            event.setCancelled(true);
        }
    }

}
