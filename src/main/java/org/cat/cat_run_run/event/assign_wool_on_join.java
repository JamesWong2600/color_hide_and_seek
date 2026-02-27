package org.cat.cat_run_run.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class assign_wool_on_join {
    public static void assignwool(Player player){
        PlayerInventory inv = player.getInventory();

        // Set different colored wool in slots 0-3
        inv.setItem(0, new ItemStack(Material.YELLOW_WOOL));
        inv.setItem(1, new ItemStack(Material.RED_WOOL));
        inv.setItem(2, new ItemStack(Material.BLUE_WOOL));
        inv.setItem(3, new ItemStack(Material.GREEN_WOOL));
    }
}
