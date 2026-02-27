package org.cat.cat_run_run;

import org.bukkit.plugin.java.JavaPlugin;
import org.cat.cat_run_run.event.Skin_changer;
import org.cat.cat_run_run.event.click_block_change_skin;
import org.cat.cat_run_run.event.drop_item;

public final class Cat_run_run extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Skin_changer(this), this);
        getServer().getPluginManager().registerEvents(new drop_item(this), this);
        getServer().getPluginManager().registerEvents(new click_block_change_skin(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
