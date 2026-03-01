package org.cat.cat_run_run.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Random;

import static org.bukkit.Bukkit.getWorld;
import static org.cat.cat_run_run.data_processing.hunter_manager.isHunter;

public class random_teleport {
    public static void teleportall(){
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(!isHunter(player.getUniqueId().toString())){
                Random random = new Random();
                int x = random.nextInt(-100,100);
                int z = random.nextInt(-100,100);
                World world = Bukkit.getWorld("world");
                assert world != null;
                int y = world.getHighestBlockYAt(x, z)+1;
                Location loc = new Location(world, x, y , z);
                player.teleport(loc);
            }
        }
    }
}
