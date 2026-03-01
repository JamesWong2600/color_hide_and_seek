package org.cat.cat_run_run.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.cat.cat_run_run.Cat_run_run;
import org.cat.cat_run_run.variable.variable;

import static org.cat.cat_run_run.data_processing.player_status_manager.getPlayerColor;
import static org.cat.cat_run_run.data_processing.player_status_manager.setPlayerColor;
import static org.cat.cat_run_run.event.Skin_changer.setPlayerSkinPaper;
import static org.cat.cat_run_run.event.cooldown_manager.isOnCooldown;
import static org.cat.cat_run_run.event.cooldown_manager.setCooldown;

import static org.cat.cat_run_run.variable.variable.games_session;


public class click_block_change_skin implements Listener {

    private final Cat_run_run plugin;

    public click_block_change_skin(Cat_run_run plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        // 1. Basic Checks
        if (variable.games_session <= 2) return;
        if (!event.getAction().toString().contains("RIGHT_CLICK")) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;

        // 2. Identify the target color based on the wool type
        String newColor = getWoolColorName(item.getType());
        System.out.println(newColor);
        if (newColor == null) return; // Not a color-changing item

        // 3. Prevent switching if already that color (Case-Insensitive)
        String currentColor = getPlayerColor(player);
        if (currentColor != null && currentColor.equalsIgnoreCase(newColor)) {
            player.sendMessage("§c你已經是 " + newColor + " 隊了！");
            return;
        }

        // 4. Check Cooldown
        if (cooldown_manager.isOnCooldown(player)) {
            player.sendActionBar(net.kyori.adventure.text.Component.text("§c更換顏色冷卻中...", net.kyori.adventure.text.format.NamedTextColor.RED));
            return;
        }

        // 5. Apply Changes
        setPlayerColor(player, newColor, plugin.getDataFolder());
        applySkin(player, newColor);
        cooldown_manager.setCooldown(player);


        player.sendMessage(net.kyori.adventure.text.Component.text("§a成功切換至 " + newColor + " 隊！"));
    }

    // --- Helper Methods to keep code clean ---

    private String getWoolColorName(Material material) {
        return switch (material) {
            case GOLD_BLOCK -> "yellow";
            case REDSTONE_BLOCK -> "red";
            case LAPIS_BLOCK -> "blue";
            case EMERALD_BLOCK -> "green";
            default -> null;
        };
    }

    private void applySkin(Player player, String color) {
        switch (color) {
            case "yellow" -> setPlayerSkinPaper(player,
                    "ewogICJ0aW1lc3RhbXAiIDogMTczNjAyNjQ1MTI0NiwKICAicHJvZmlsZUlkIiA6ICJkYjZiYWRlN2NjMzI0MjM4YjU3OTQ4NzMxNTBkNjA1MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJRdWFudHVtQmxvY2tlciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83OTA0YzZiMmI2MDI3MDM3MjFkM2JlYjMzNDAxN2E4ODcyNDI2YmRmNDRmOWY2YThhNjA2OGI5OTUwOWIyN2Q0IgogICAgfQogIH0KfQ==",
                    "b9igVhQmZ54V0XxqxBwYf9aAqbeAJwHFwTtYFNLP6xnWHU4KkvQMmQNSM/qGN7gCva1r/17uSLff7ua9wh7p5H32yG+QrQ2d+t0l7IscVxfrFR7EIZpfX4SdXkv6BUGNr850RCgurzcLDlbQ2YEzAx7bUJv73xpJY9hVHFJF9WNuprotljQRwi2lp8WqLPRuioL6bBEy3zXtVpk7SkyStr1gj7nG1i5lxu+yiyWfmGTGCDpqvBfTb2ZP1HVJlJ0+j4SyT04Ox/syRG+pQOzmbWpK11g9haBI3FbYDGBhmbbQvhdZcUWuEgWWWd+g+CCJ3WxprK9VO2eP8SMh4bhsRsUPnhoSe5O+j3iFZ/jVQ/vLh5vcG7dX7lTtP2QTTpw9wxt7e3PrR78ccZ0UY70+aEF8RxZmflyTa5ABrP12ifjtkb3W7oobAVECg2UyyRydzKAp9T1AmI5Z71zkVKRHSzPdGNZePj1RmkZjOhoJcCF0ag7MxY5Q6uXUk2wAKLuH4cK+3kQ4c3TGKfS6bSPyfpGb1sl42QQ/8IJB0zNwA/ghIjuhlkGRqsDNky9tP8WhJ7gE/fbIeG29lmw16AAlTc7+zxS/KHAH770stDY4CPBANpgaYQVbsGnCWIbJKnlih5LSi4E5meL5qvLQW1tOCTEIXpnZblw/cHBTwarB3RI=");
            case "red" -> setPlayerSkinPaper(player,
                    "ewogICJ0aW1lc3RhbXAiIDogMTYyMzc4MjU3NjY1NSwKICAicHJvZmlsZUlkIiA6ICIyYzEwNjRmY2Q5MTc0MjgyODRlM2JmN2ZhYTdlM2UxYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJOYWVtZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84Y2VhYWViMTJiZGI2ZDg4NWVlNzkzN2YzODAzNDUzM2RjNDFmMDQxZWRjYzRkMGI3YzNmMjk5MWM4ZDJhNDIzIgogICAgfQogIH0KfQ==",
                    "KTAiv2Q8rYN8K/ss7H1VMtzNHPukSToEtY9g0IJVfTrK9olmKm/VmZuSj7/VsojDG36SR1ENoXqqdZJ8Wa7ZX2nHghpm41PwZnfITwTYRpXLqIpH7eXpWuI+Afz9h4m9QQrbL5Fo7pPjJlJK4rii+ZCcerRPnQtnAjmr2Fnv4Sn+2qZKUHH34f2fbJEG9+XT0HQpM3Oq8Dy6BXVedP/8QcmcgUkFoxWGOtv+z+hWjMVr60C/2yzYIyQP2MfFyLmqPaxE1id5JX3h28liBtzSyD8vDWamt4gh8ekX2Al0ZOdjCtCAIBDGet2yk5Jx3yGesrYn3HNCFyxItVCtducc19eLGeDuQhr6vh3sPrRfFLES2lu3zAoH3iMHnXOofZXEuMKUP67LUy7u13kEDO6mDJM3TReSOB4K1u0Lha45EUQxZd9xctw4hi4XVULwjQIhnIgvtETryIi/vWtcAmFhDhhfuxDbM8l6M7lT3KDh6OFFaQE5ekWOibMqnKo0rM3KSoT0aYcAXPTDmjg7csJvhpmzTQiobArnjeCvOLG/gD/MIpHBNAR5n94Bd4ETzkl2hdyy+qQAKHWgcZ24QRr7B6u/HJoAm87zCjK8pyaO2HUxELtivN3jUR/BoVcFp+7h3lIyuUCNevolyFf134q7eFs+KnmyYtJ2gyhxZH4YMxQ=");
            case "blue" -> setPlayerSkinPaper(player,
                    "ewogICJ0aW1lc3RhbXAiIDogMTc0NTg2ODAwMjI1NiwKICAicHJvZmlsZUlkIiA6ICJhZTg3MzEyNjBmMzY0ZWE2YjU3YTRkYjI5Mjk1YTA1OCIsCiAgInByb2ZpbGVOYW1lIiA6ICJGdW50aW1lX0ZveHlfMTkiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQyYTE0OGE3MDRmODZlYmVkOTQ4MjAyMzYxNWMwY2UxODJlNmM0YjlmOWRlOGYzNzk1MWFhMzA5YzUyYjY0ZCIKICAgIH0KICB9Cn0=",
                    "kyDHYyxO0Id/sRR97HOP3WTpNEXIcLwBkuTt3sszNxKsDAFOHOLW/39RIkaqf3JKqm9ueh5CCodR0rUtoHnX4GmvcuYeLxaHVZv6pU/l89V6a11IvjKJ1AiWTER0I2GsOJ07L9vnzvSKptptg2KpoQTNuuyhmQw8KfdGzpL9ugVmpyFQHz7fCh3KE6HC7pYpx3DCZisvPRxg1VFfqaxXKyOE9JLqAhowXmTCVJZqfnrxMbg5BODsYwuZ+CpR2L2lf/VuFCR7IX/v3GMRgPcFM3TNXb6M8pagQJKlOkK9v6IFh/18fPKIFixLNTzZphuTPnIpgUNtjWgu+siOVJp9SkeWaOEiwmOHNedk6r5gvAAUOE6jNfWhRtoYd4oFc4A1AcGlKxNSv7ulgMRydxKRO9mQcS3Fkr3ugsL7k5L3ssyyfbmvuHIMiKLF33/BZVslVt8GLT219cf2Y+Zr0bDqYpozTvpMOVEKM9lJHp1AAsa03aHXWXQkyQP9rBJ+zaCl9/J7NgGLtM1UGnr6iiDysMgRSVvhejfnPpc9uEZLoWWl5U6DodNxB/G1Nx7uBbcxGwMxv3zSlA62UJeVr/XMmgsd5VznKGw4kic42Tew/ikpqUwkcrp19UaIiJbr3VqVMELHvZfIw0yBda987Ro+Xr2sEB8rgPGkfcl0dRBCKXY=");
            case "green" -> setPlayerSkinPaper(player,
                    "ewogICJ0aW1lc3RhbXAiIDogMTcyNjM0MDc3OTcyMiwKICAicHJvZmlsZUlkIiA6ICI4YmM3MjdlYThjZjA0YWUzYTI4MDVhY2YzNjRjMmQyNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJub3RpbnZlbnRpdmUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y2ZGI4MzI2YmU2NDMxODI4ZDgzM2U1MzkyZDY5YWM3MmVkM2QxMWZhNTdjNWM3MTljMTMwZTg2ODczY2NmZiIKICAgIH0KICB9Cn0=",
                    "GmmkFymrmmPzzrtTtI6IO0Vh50UwkYtQ7ArJ6fMKsh3IkFiNcauDlDAhYvuv8/xrnVFCAtv3Gmf+qfkt5oV/0ristdAxuQ2ermmUKVg6elJpUPd2MnQy9OUEqIqnxrNPq3Fw6FEQxt2o0jBTltYeIaFveK10mV84bMaoDHvvhm3XqG1WMeenPU9az+1KC4rrJK85Ab3MGbLhgBqYUZIoajHUIu3PNtW/HzzGhucxdIr/9yUhLXaJbQ0n6yAL5U7zV0uM377Ubgek6sc/SSYvSIpO8jpi572k9tkyF+0y6fiHdzL9hEYO4QsHFteOyDjhWlAVJ29NyYkfYFWwdLV8+6rPpOfEI/5wFEzHB6eFT+SHnr/+GO2fCX9cSh56eob5xoo7ReEi8zxTGGl9AjNEmF0f4BVz0ziwZuR1Ptk7g55XbfhJi/4Ma0O9HguTQOAbQmOiU0/qOS/JGGxwxrE0fWJu5gvo/5JYEWVpXQqCWGxm14/zWY2cDhCbffdpNVIcPrj9mRNSaD/u3D4SFuyJPAW6TYSYHYCqEn7C7QzGP2Vdu6okCuHMLVp9dAoY+dCVpPKBNwq8xyM7LrPWFDAl7atsC9vXyqveIvROoiqQzZoWcIjBe5GGac4DTZ0aJjeimvHfllQXcWd/vsAVC+mTXDxFmxKJiu3+KMQAKF7Yzrc=");
        }
    }
}
//public class click_block_change_skin implements Listener {
//
//    private final Cat_run_run plugin;
//
//    public click_block_change_skin(Cat_run_run plugin) {
//        this.plugin = plugin;
//    }
//
//    @EventHandler
//    public void onPlayerRightClick(PlayerInteractEvent event) {
//        if (games_session <= 2) {
//            return;
//        }
//        Player player = event.getPlayer();
//
//        // Only check main hand (right hand)
//        if (event.getHand() == EquipmentSlot.HAND) {
//            ItemStack mainHand = event.getItem();
//
//            if (mainHand != null) {
//
//                if (event.getAction().toString().contains("RIGHT_CLICK")) {
//
//
//                    if (mainHand.getType() == Material.YELLOW_WOOL) {
//                        if(getPlayerColor(player).equals("yellow")) return;
//                        setPlayerColor(player, "yellow", plugin.getDataFolder());
//                        setPlayerSkinPaper(event.getPlayer(), "ewogICJ0aW1lc3RhbXAiIDogMTczNjAyNjQ1MTI0NiwKICAicHJvZmlsZUlkIiA6ICJkYjZiYWRlN2NjMzI0MjM4YjU3OTQ4NzMxNTBkNjA1MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJRdWFudHVtQmxvY2tlciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83OTA0YzZiMmI2MDI3MDM3MjFkM2JlYjMzNDAxN2E4ODcyNDI2YmRmNDRmOWY2YThhNjA2OGI5OTUwOWIyN2Q0IgogICAgfQogIH0KfQ==",
//                                "b9igVhQmZ54V0XxqxBwYf9aAqbeAJwHFwTtYFNLP6xnWHU4KkvQMmQNSM/qGN7gCva1r/17uSLff7ua9wh7p5H32yG+QrQ2d+t0l7IscVxfrFR7EIZpfX4SdXkv6BUGNr850RCgurzcLDlbQ2YEzAx7bUJv73xpJY9hVHFJF9WNuprotljQRwi2lp8WqLPRuioL6bBEy3zXtVpk7SkyStr1gj7nG1i5lxu+yiyWfmGTGCDpqvBfTb2ZP1HVJlJ0+j4SyT04Ox/syRG+pQOzmbWpK11g9haBI3FbYDGBhmbbQvhdZcUWuEgWWWd+g+CCJ3WxprK9VO2eP8SMh4bhsRsUPnhoSe5O+j3iFZ/jVQ/vLh5vcG7dX7lTtP2QTTpw9wxt7e3PrR78ccZ0UY70+aEF8RxZmflyTa5ABrP12ifjtkb3W7oobAVECg2UyyRydzKAp9T1AmI5Z71zkVKRHSzPdGNZePj1RmkZjOhoJcCF0ag7MxY5Q6uXUk2wAKLuH4cK+3kQ4c3TGKfS6bSPyfpGb1sl42QQ/8IJB0zNwA/ghIjuhlkGRqsDNky9tP8WhJ7gE/fbIeG29lmw16AAlTc7+zxS/KHAH770stDY4CPBANpgaYQVbsGnCWIbJKnlih5LSi4E5meL5qvLQW1tOCTEIXpnZblw/cHBTwarB3RI=");
//                    } else if (mainHand.getType() == Material.RED_WOOL) {
//                        if(getPlayerColor(player).equals("red")) return;
//                        setPlayerColor(player,"red", plugin.getDataFolder());
//                        setPlayerSkinPaper(event.getPlayer(), "ewogICJ0aW1lc3RhbXAiIDogMTYyMzc4MjU3NjY1NSwKICAicHJvZmlsZUlkIiA6ICIyYzEwNjRmY2Q5MTc0MjgyODRlM2JmN2ZhYTdlM2UxYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJOYWVtZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84Y2VhYWViMTJiZGI2ZDg4NWVlNzkzN2YzODAzNDUzM2RjNDFmMDQxZWRjYzRkMGI3YzNmMjk5MWM4ZDJhNDIzIgogICAgfQogIH0KfQ==",
//                                "KTAiv2Q8rYN8K/ss7H1VMtzNHPukSToEtY9g0IJVfTrK9olmKm/VmZuSj7/VsojDG36SR1ENoXqqdZJ8Wa7ZX2nHghpm41PwZnfITwTYRpXLqIpH7eXpWuI+Afz9h4m9QQrbL5Fo7pPjJlJK4rii+ZCcerRPnQtnAjmr2Fnv4Sn+2qZKUHH34f2fbJEG9+XT0HQpM3Oq8Dy6BXVedP/8QcmcgUkFoxWGOtv+z+hWjMVr60C/2yzYIyQP2MfFyLmqPaxE1id5JX3h28liBtzSyD8vDWamt4gh8ekX2Al0ZOdjCtCAIBDGet2yk5Jx3yGesrYn3HNCFyxItVCtducc19eLGeDuQhr6vh3sPrRfFLES2lu3zAoH3iMHnXOofZXEuMKUP67LUy7u13kEDO6mDJM3TReSOB4K1u0Lha45EUQxZd9xctw4hi4XVULwjQIhnIgvtETryIi/vWtcAmFhDhhfuxDbM8l6M7lT3KDh6OFFaQE5ekWOibMqnKo0rM3KSoT0aYcAXPTDmjg7csJvhpmzTQiobArnjeCvOLG/gD/MIpHBNAR5n94Bd4ETzkl2hdyy+qQAKHWgcZ24QRr7B6u/HJoAm87zCjK8pyaO2HUxELtivN3jUR/BoVcFp+7h3lIyuUCNevolyFf134q7eFs+KnmyYtJ2gyhxZH4YMxQ=");
//                    } else if (mainHand.getType() == Material.BLUE_WOOL) {
//                        if(getPlayerColor(player).equals("blue")) return;
//                        setPlayerColor(player,"blue", plugin.getDataFolder());
//                        setPlayerSkinPaper(event.getPlayer(), "ewogICJ0aW1lc3RhbXAiIDogMTc0NTg2ODAwMjI1NiwKICAicHJvZmlsZUlkIiA6ICJhZTg3MzEyNjBmMzY0ZWE2YjU3YTRkYjI5Mjk1YTA1OCIsCiAgInByb2ZpbGVOYW1lIiA6ICJGdW50aW1lX0ZveHlfMTkiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQyYTE0OGE3MDRmODZlYmVkOTQ4MjAyMzYxNWMwY2UxODJlNmM0YjlmOWRlOGYzNzk1MWFhMzA5YzUyYjY0ZCIKICAgIH0KICB9Cn0=",
//                                "kyDHYyxO0Id/sRR97HOP3WTpNEXIcLwBkuTt3sszNxKsDAFOHOLW/39RIkaqf3JKqm9ueh5CCodR0rUtoHnX4GmvcuYeLxaHVZv6pU/l89V6a11IvjKJ1AiWTER0I2GsOJ07L9vnzvSKptptg2KpoQTNuuyhmQw8KfdGzpL9ugVmpyFQHz7fCh3KE6HC7pYpx3DCZisvPRxg1VFfqaxXKyOE9JLqAhowXmTCVJZqfnrxMbg5BODsYwuZ+CpR2L2lf/VuFCR7IX/v3GMRgPcFM3TNXb6M8pagQJKlOkK9v6IFh/18fPKIFixLNTzZphuTPnIpgUNtjWgu+siOVJp9SkeWaOEiwmOHNedk6r5gvAAUOE6jNfWhRtoYd4oFc4A1AcGlKxNSv7ulgMRydxKRO9mQcS3Fkr3ugsL7k5L3ssyyfbmvuHIMiKLF33/BZVslVt8GLT219cf2Y+Zr0bDqYpozTvpMOVEKM9lJHp1AAsa03aHXWXQkyQP9rBJ+zaCl9/J7NgGLtM1UGnr6iiDysMgRSVvhejfnPpc9uEZLoWWl5U6DodNxB/G1Nx7uBbcxGwMxv3zSlA62UJeVr/XMmgsd5VznKGw4kic42Tew/ikpqUwkcrp19UaIiJbr3VqVMELHvZfIw0yBda987Ro+Xr2sEB8rgPGkfcl0dRBCKXY=");
//                    } else if (mainHand.getType() == Material.GREEN_WOOL) {
//                        if(getPlayerColor(player).equals("green")) return;
//                        setPlayerColor(player,"green", plugin.getDataFolder());
//                        setPlayerSkinPaper(event.getPlayer(), "ewogICJ0aW1lc3RhbXAiIDogMTcyNjM0MDc3OTcyMiwKICAicHJvZmlsZUlkIiA6ICI4YmM3MjdlYThjZjA0YWUzYTI4MDVhY2YzNjRjMmQyNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJub3RpbnZlbnRpdmUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y2ZGI4MzI2YmU2NDMxODI4ZDgzM2U1MzkyZDY5YWM3MmVkM2QxMWZhNTdjNWM3MTljMTMwZTg2ODczY2NmZiIKICAgIH0KICB9Cn0=",
//                                "GmmkFymrmmPzzrtTtI6IO0Vh50UwkYtQ7ArJ6fMKsh3IkFiNcauDlDAhYvuv8/xrnVFCAtv3Gmf+qfkt5oV/0ristdAxuQ2ermmUKVg6elJpUPd2MnQy9OUEqIqnxrNPq3Fw6FEQxt2o0jBTltYeIaFveK10mV84bMaoDHvvhm3XqG1WMeenPU9az+1KC4rrJK85Ab3MGbLhgBqYUZIoajHUIu3PNtW/HzzGhucxdIr/9yUhLXaJbQ0n6yAL5U7zV0uM377Ubgek6sc/SSYvSIpO8jpi572k9tkyF+0y6fiHdzL9hEYO4QsHFteOyDjhWlAVJ29NyYkfYFWwdLV8+6rPpOfEI/5wFEzHB6eFT+SHnr/+GO2fCX9cSh56eob5xoo7ReEi8zxTGGl9AjNEmF0f4BVz0ziwZuR1Ptk7g55XbfhJi/4Ma0O9HguTQOAbQmOiU0/qOS/JGGxwxrE0fWJu5gvo/5JYEWVpXQqCWGxm14/zWY2cDhCbffdpNVIcPrj9mRNSaD/u3D4SFuyJPAW6TYSYHYCqEn7C7QzGP2Vdu6okCuHMLVp9dAoY+dCVpPKBNwq8xyM7LrPWFDAl7atsC9vXyqveIvROoiqQzZoWcIjBe5GGac4DTZ0aJjeimvHfllQXcWd/vsAVC+mTXDxFmxKJiu3+KMQAKF7Yzrc=");
//                    }
//                }
//                    if(!isOnCooldown(player)){
//                        setCooldown(player);
//                    }
//
//                }
//            }
//
//        // Check off hand (left hand)
//        if (event.getHand() == EquipmentSlot.OFF_HAND) {
//            ItemStack offHand = player.getInventory().getItemInOffHand();
//
//            if (offHand != null) {
//                if (offHand.getType() == Material.YELLOW_WOOL) {
//                    if(getPlayerColor(player).equals("yellow")) return;
//                    setPlayerColor(player, "yellow", plugin.getDataFolder());
//                    setPlayerSkinPaper(event.getPlayer(), "ewogICJ0aW1lc3RhbXAiIDogMTczNjAyNjQ1MTI0NiwKICAicHJvZmlsZUlkIiA6ICJkYjZiYWRlN2NjMzI0MjM4YjU3OTQ4NzMxNTBkNjA1MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJRdWFudHVtQmxvY2tlciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83OTA0YzZiMmI2MDI3MDM3MjFkM2JlYjMzNDAxN2E4ODcyNDI2YmRmNDRmOWY2YThhNjA2OGI5OTUwOWIyN2Q0IgogICAgfQogIH0KfQ==",
//                            "b9igVhQmZ54V0XxqxBwYf9aAqbeAJwHFwTtYFNLP6xnWHU4KkvQMmQNSM/qGN7gCva1r/17uSLff7ua9wh7p5H32yG+QrQ2d+t0l7IscVxfrFR7EIZpfX4SdXkv6BUGNr850RCgurzcLDlbQ2YEzAx7bUJv73xpJY9hVHFJF9WNuprotljQRwi2lp8WqLPRuioL6bBEy3zXtVpk7SkyStr1gj7nG1i5lxu+yiyWfmGTGCDpqvBfTb2ZP1HVJlJ0+j4SyT04Ox/syRG+pQOzmbWpK11g9haBI3FbYDGBhmbbQvhdZcUWuEgWWWd+g+CCJ3WxprK9VO2eP8SMh4bhsRsUPnhoSe5O+j3iFZ/jVQ/vLh5vcG7dX7lTtP2QTTpw9wxt7e3PrR78ccZ0UY70+aEF8RxZmflyTa5ABrP12ifjtkb3W7oobAVECg2UyyRydzKAp9T1AmI5Z71zkVKRHSzPdGNZePj1RmkZjOhoJcCF0ag7MxY5Q6uXUk2wAKLuH4cK+3kQ4c3TGKfS6bSPyfpGb1sl42QQ/8IJB0zNwA/ghIjuhlkGRqsDNky9tP8WhJ7gE/fbIeG29lmw16AAlTc7+zxS/KHAH770stDY4CPBANpgaYQVbsGnCWIbJKnlih5LSi4E5meL5qvLQW1tOCTEIXpnZblw/cHBTwarB3RI=");
//                } else if (offHand.getType() == Material.RED_WOOL) {
//                    if(getPlayerColor(player).equals("red")) return;
//                    setPlayerColor(player,"red", plugin.getDataFolder());
//                    setPlayerSkinPaper(event.getPlayer(), "ewogICJ0aW1lc3RhbXAiIDogMTYyMzc4MjU3NjY1NSwKICAicHJvZmlsZUlkIiA6ICIyYzEwNjRmY2Q5MTc0MjgyODRlM2JmN2ZhYTdlM2UxYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJOYWVtZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84Y2VhYWViMTJiZGI2ZDg4NWVlNzkzN2YzODAzNDUzM2RjNDFmMDQxZWRjYzRkMGI3YzNmMjk5MWM4ZDJhNDIzIgogICAgfQogIH0KfQ==",
//                            "KTAiv2Q8rYN8K/ss7H1VMtzNHPukSToEtY9g0IJVfTrK9olmKm/VmZuSj7/VsojDG36SR1ENoXqqdZJ8Wa7ZX2nHghpm41PwZnfITwTYRpXLqIpH7eXpWuI+Afz9h4m9QQrbL5Fo7pPjJlJK4rii+ZCcerRPnQtnAjmr2Fnv4Sn+2qZKUHH34f2fbJEG9+XT0HQpM3Oq8Dy6BXVedP/8QcmcgUkFoxWGOtv+z+hWjMVr60C/2yzYIyQP2MfFyLmqPaxE1id5JX3h28liBtzSyD8vDWamt4gh8ekX2Al0ZOdjCtCAIBDGet2yk5Jx3yGesrYn3HNCFyxItVCtducc19eLGeDuQhr6vh3sPrRfFLES2lu3zAoH3iMHnXOofZXEuMKUP67LUy7u13kEDO6mDJM3TReSOB4K1u0Lha45EUQxZd9xctw4hi4XVULwjQIhnIgvtETryIi/vWtcAmFhDhhfuxDbM8l6M7lT3KDh6OFFaQE5ekWOibMqnKo0rM3KSoT0aYcAXPTDmjg7csJvhpmzTQiobArnjeCvOLG/gD/MIpHBNAR5n94Bd4ETzkl2hdyy+qQAKHWgcZ24QRr7B6u/HJoAm87zCjK8pyaO2HUxELtivN3jUR/BoVcFp+7h3lIyuUCNevolyFf134q7eFs+KnmyYtJ2gyhxZH4YMxQ=");
//                } else if (offHand.getType() == Material.BLUE_WOOL) {
//                    if(getPlayerColor(player).equals("blue")) return;
//                    setPlayerColor(player,"blue", plugin.getDataFolder());
//                    setPlayerSkinPaper(event.getPlayer(), "ewogICJ0aW1lc3RhbXAiIDogMTc0NTg2ODAwMjI1NiwKICAicHJvZmlsZUlkIiA6ICJhZTg3MzEyNjBmMzY0ZWE2YjU3YTRkYjI5Mjk1YTA1OCIsCiAgInByb2ZpbGVOYW1lIiA6ICJGdW50aW1lX0ZveHlfMTkiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQyYTE0OGE3MDRmODZlYmVkOTQ4MjAyMzYxNWMwY2UxODJlNmM0YjlmOWRlOGYzNzk1MWFhMzA5YzUyYjY0ZCIKICAgIH0KICB9Cn0=",
//                            "kyDHYyxO0Id/sRR97HOP3WTpNEXIcLwBkuTt3sszNxKsDAFOHOLW/39RIkaqf3JKqm9ueh5CCodR0rUtoHnX4GmvcuYeLxaHVZv6pU/l89V6a11IvjKJ1AiWTER0I2GsOJ07L9vnzvSKptptg2KpoQTNuuyhmQw8KfdGzpL9ugVmpyFQHz7fCh3KE6HC7pYpx3DCZisvPRxg1VFfqaxXKyOE9JLqAhowXmTCVJZqfnrxMbg5BODsYwuZ+CpR2L2lf/VuFCR7IX/v3GMRgPcFM3TNXb6M8pagQJKlOkK9v6IFh/18fPKIFixLNTzZphuTPnIpgUNtjWgu+siOVJp9SkeWaOEiwmOHNedk6r5gvAAUOE6jNfWhRtoYd4oFc4A1AcGlKxNSv7ulgMRydxKRO9mQcS3Fkr3ugsL7k5L3ssyyfbmvuHIMiKLF33/BZVslVt8GLT219cf2Y+Zr0bDqYpozTvpMOVEKM9lJHp1AAsa03aHXWXQkyQP9rBJ+zaCl9/J7NgGLtM1UGnr6iiDysMgRSVvhejfnPpc9uEZLoWWl5U6DodNxB/G1Nx7uBbcxGwMxv3zSlA62UJeVr/XMmgsd5VznKGw4kic42Tew/ikpqUwkcrp19UaIiJbr3VqVMELHvZfIw0yBda987Ro+Xr2sEB8rgPGkfcl0dRBCKXY=");
//                } else if (offHand.getType() == Material.GREEN_WOOL) {
//                    if(getPlayerColor(player).equals("green")) return;
//                    setPlayerColor(player,"green", plugin.getDataFolder());
//                    setPlayerSkinPaper(event.getPlayer(), "ewogICJ0aW1lc3RhbXAiIDogMTcyNjM0MDc3OTcyMiwKICAicHJvZmlsZUlkIiA6ICI4YmM3MjdlYThjZjA0YWUzYTI4MDVhY2YzNjRjMmQyNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJub3RpbnZlbnRpdmUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y2ZGI4MzI2YmU2NDMxODI4ZDgzM2U1MzkyZDY5YWM3MmVkM2QxMWZhNTdjNWM3MTljMTMwZTg2ODczY2NmZiIKICAgIH0KICB9Cn0=",
//                            "GmmkFymrmmPzzrtTtI6IO0Vh50UwkYtQ7ArJ6fMKsh3IkFiNcauDlDAhYvuv8/xrnVFCAtv3Gmf+qfkt5oV/0ristdAxuQ2ermmUKVg6elJpUPd2MnQy9OUEqIqnxrNPq3Fw6FEQxt2o0jBTltYeIaFveK10mV84bMaoDHvvhm3XqG1WMeenPU9az+1KC4rrJK85Ab3MGbLhgBqYUZIoajHUIu3PNtW/HzzGhucxdIr/9yUhLXaJbQ0n6yAL5U7zV0uM377Ubgek6sc/SSYvSIpO8jpi572k9tkyF+0y6fiHdzL9hEYO4QsHFteOyDjhWlAVJ29NyYkfYFWwdLV8+6rPpOfEI/5wFEzHB6eFT+SHnr/+GO2fCX9cSh56eob5xoo7ReEi8zxTGGl9AjNEmF0f4BVz0ziwZuR1Ptk7g55XbfhJi/4Ma0O9HguTQOAbQmOiU0/qOS/JGGxwxrE0fWJu5gvo/5JYEWVpXQqCWGxm14/zWY2cDhCbffdpNVIcPrj9mRNSaD/u3D4SFuyJPAW6TYSYHYCqEn7C7QzGP2Vdu6okCuHMLVp9dAoY+dCVpPKBNwq8xyM7LrPWFDAl7atsC9vXyqveIvROoiqQzZoWcIjBe5GGac4DTZ0aJjeimvHfllQXcWd/vsAVC+mTXDxFmxKJiu3+KMQAKF7Yzrc=");
//                }
//                if(!isOnCooldown(player)){
//                    setCooldown(player);
//                }
//            }
//    }
//
//    }
//
//}