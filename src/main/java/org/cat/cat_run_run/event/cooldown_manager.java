package org.cat.cat_run_run.event;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class cooldown_manager {

        private static JavaPlugin plugin;
        private static final Map<UUID, Long> cooldowns = new HashMap<>();
        private static final long COOLDOWN_TIME = 30000; // 30 seconds

        public cooldown_manager(JavaPlugin plugin) {
            this.plugin = plugin;
            startCleanupTask();
        }

        /**
         * Check if player is on cooldown
         * @return true if on cooldown, false if not
         */
        public static boolean isOnCooldown(Player player) {
            UUID playerId = player.getUniqueId();

            if (cooldowns.containsKey(playerId)) {
                long timeLeft = cooldowns.get(playerId) - System.currentTimeMillis();

                if (timeLeft > 0) {
                    return true;
                } else {
                    // Remove expired cooldown
                    cooldowns.remove(playerId);
                }
            }

            return false;
        }

        /**
         * Get remaining cooldown time in seconds
         * @return seconds left, 0 if no cooldown
         */
        public static long getRemainingSeconds(Player player) {
            UUID playerId = player.getUniqueId();

            if (cooldowns.containsKey(playerId)) {
                long timeLeft = cooldowns.get(playerId) - System.currentTimeMillis();
                return timeLeft > 0 ? (timeLeft / 1000) : 0;
            }

            return 0;
        }

        /**
         * Get remaining cooldown time in milliseconds
         * @return milliseconds left, 0 if no cooldown
         */
        public static long getRemainingMillis(Player player) {
            UUID playerId = player.getUniqueId();

            if (cooldowns.containsKey(playerId)) {
                long timeLeft = cooldowns.get(playerId) - System.currentTimeMillis();
                return Math.max(timeLeft, 0);
            }

            return 0;
        }

        /**
         * Set player on cooldown
         */
        public static void setCooldown(Player player) {
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + COOLDOWN_TIME);
        }

        /**
         * Set custom cooldown time
         * @param milliseconds custom cooldown time
         */
        public static void setCooldown(Player player, long milliseconds) {
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + milliseconds);
        }

        /**
         * Remove player from cooldown
         */
        public static void removeCooldown(Player player) {
            cooldowns.remove(player.getUniqueId());
        }

        /**
         * Check if player has cooldown and send message
         * @return true if on cooldown, false if not
         */
        public static boolean checkAndSendMessage(Player player) {
            if (isOnCooldown(player)) {
                long seconds = getRemainingSeconds(player);
                player.sendMessage("§cPlease wait §e" + seconds + " §cmore seconds!");
                return true;
            }
            return false;
        }

        /**
         * Start automatic cleanup task to remove expired cooldowns
         */
        private static void startCleanupTask() {
            new BukkitRunnable() {
                @Override
                public void run() {
                    long now = System.currentTimeMillis();
                    cooldowns.entrySet().removeIf(entry -> entry.getValue() <= now);
                }
            }.runTaskTimerAsynchronously(plugin, 6000L, 6000L); // Clean every 5 minutes (6000 ticks = 5 min)
        }

        /**
         * Get cooldown map size
         */
        public static int getCooldownCount() {
            return cooldowns.size();
        }

        /**
         * Clear all cooldowns
         */
        public static void clearAllCooldowns() {
            cooldowns.clear();
        }
}
