package org.cat.cat_run_run.init;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class init_world_wool {
    public static void generateSmoothly(JavaPlugin plugin) {
        new BukkitRunnable() {

            int x = -100;
            final World world = Bukkit.getWorld("world");

            @Override
            public void run() {
                setupWorldBorder(world);
                // Process one full X-slice per tick
                for (int z = -100; z <= 100; z++) {

                    Material color = getQuadrantColor(x, z);
                    assert world != null;
                    for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++) {
                        Block b = world.getBlockAt(x, y, z);
                        if (b.getType() != Material.AIR && b.getType() != Material.BEDROCK) {
                            b.setType(color, false);
                        }
                    }
                }

                x++;
                if (x > 100) {
                    this.cancel();
                    Bukkit.broadcast(Component.text("Generation Finished!"));
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private static Material getQuadrantColor(int x, int z) {
        if (x >= 0) return (z >= 0) ? Material.GREEN_WOOL : Material.YELLOW_WOOL;
        return (z >= 0) ? Material.RED_WOOL : Material.BLUE_WOOL;
    }

    public static void setupWorldBorder(World world) {
        WorldBorder border = world.getWorldBorder();

        // 1. Set the Center (where the quadrants meet)
        border.setCenter(0.0, 0.0);

        // 2. Set the Size (diameter)
        // If your radius is 200, your diameter (size) is 400
        border.setSize(200.0);

        // 3. Optional: Set a warning
        border.setWarningDistance(5); // Red tint starts 5 blocks away
        border.setDamageAmount(0.2);  // Damage per block outside the border
    }
}
