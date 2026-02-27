package org.cat.cat_run_run.event;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.cat.cat_run_run.Cat_run_run;

import java.util.Set;

import static org.cat.cat_run_run.event.assign_wool_on_join.assignwool;
import static org.cat.cat_run_run.event.cooldown_manager.getRemainingSeconds;
import static org.cat.cat_run_run.event.cooldown_manager.isOnCooldown;

public class Skin_changer implements Listener {
    private final Cat_run_run plugin;

    public Skin_changer(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        assignwool(event.getPlayer());
    }

    public static void setPlayerSkinPaper(Player player, String skinValue, String skinSignature){

        if(isOnCooldown(player)){
            player.sendMessage(ChatColor.RED+"cooldown still remain " + getRemainingSeconds(player)+ " s");
            return;
        }
        // Get the player's profile
        PlayerProfile profile = player.getPlayerProfile();

        // Remove existing skin textures
        Set<ProfileProperty> properties = profile.getProperties();
        properties.removeIf(prop -> prop.getName().equals("textures"));

        // Add new skin texture
        profile.setProperty(new ProfileProperty("textures", skinValue, skinSignature));

        // Set the updated profile
        player.setPlayerProfile(profile);

        // Refresh player for others
        for (Player online : player.getServer().getOnlinePlayers()) {
            if (online.canSee(player)) {
                online.hidePlayer(JavaPlugin.getProvidingPlugin(Skin_changer.class), player);
                online.showPlayer(JavaPlugin.getProvidingPlugin(Skin_changer.class), player);
            }
        }
    }
}
