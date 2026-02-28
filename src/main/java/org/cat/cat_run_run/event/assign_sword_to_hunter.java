package org.cat.cat_run_run.event;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class assign_sword_to_hunter {
    public static void assignsword(Player player){
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = sword.getItemMeta();

        meta.displayName(Component.text("§4§l死神之劍")
                .decoration(TextDecoration.ITALIC, false));

        // Sharpness 255 is the maximum possible level in Minecraft
        meta.addEnchant(Enchantment.SHARPNESS, 255, true);

        // Make it look clean
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        sword.setItemMeta(meta);
        player.getInventory().addItem(sword);
    }
}
