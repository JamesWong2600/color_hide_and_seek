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
    public static void assignHunterLoadout(Player player) {
        // 1. Create the Sword
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.displayName(Component.text("§4§l死神之劍").decoration(TextDecoration.ITALIC, false));
        swordMeta.addEnchant(Enchantment.SHARPNESS, 255, true);
        swordMeta.setUnbreakable(true);
        swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sword.setItemMeta(swordMeta);

        // 2. Create the Shears (assuming you want these for the game)
        ItemStack shears = new ItemStack(Material.SHEARS);
        ItemMeta shearMeta = shears.getItemMeta();
        shearMeta.displayName(Component.text("§6§l剪羊毛工具").decoration(TextDecoration.ITALIC, false));
        shearMeta.setUnbreakable(true);
        shears.setItemMeta(shearMeta);

        // 3. Assign to specific slots
        // setItem(0, ...) is the FIRST slot
        player.getInventory().setItem(0, sword);

        // setItem(1, ...) is the SECOND slot
        player.getInventory().setItem(1, shears);

        // Optional: Force the player to hold the sword immediately
        player.getInventory().setHeldItemSlot(0);
    }

}
