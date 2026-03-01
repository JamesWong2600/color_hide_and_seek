package org.cat.cat_run_run.scoreboard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import static org.cat.cat_run_run.variable.variable.games_session;

public class scoreboard {
    public static void setup(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        // Register objective with a Title
        Objective obj = board.registerNewObjective("game_info", Criteria.DUMMY,
                Component.text("測試測試測試", NamedTextColor.GOLD));

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }

        // Add New Lines
        // Scores (the numbers on the right) are used to order the lines (3, 2, 1)
        public static void update(Player player, String gameTime, int cooldown, int playersLeft) {
            Scoreboard board = player.getScoreboard();
            Objective obj = board.getObjective("game_info");

            if (obj == null) return;

            // Clean slate
            for (String entry : board.getEntries()) {
                board.resetScores(entry);
            }

            // Helper to convert Component to the String format getScore() expects
            LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();

            // Line 3: Time
            String session = serializer.serialize(Component.text("當前階段:  ", NamedTextColor.WHITE)
                    .append(Component.text(session_text(games_session), NamedTextColor.GREEN)));
            obj.getScore(session).setScore(4);

            String timeLine = serializer.serialize(Component.text("游戲進行時間:  ", NamedTextColor.WHITE)
                    .append(Component.text(gameTime+ "s", NamedTextColor.GREEN)));
            obj.getScore(timeLine).setScore(3);

            // Line 2: Cooldown
            String cdLine = serializer.serialize(Component.text("距離下次切換時間: ", NamedTextColor.WHITE)
                    .append(Component.text(cooldown + "s", NamedTextColor.GREEN)));
            obj.getScore(cdLine).setScore(2);

            // Line 1: Players
            String playerLine = serializer.serialize(Component.text("剩餘存活玩家: ", NamedTextColor.WHITE)
                    .append(Component.text(playersLeft, NamedTextColor.GREEN)));
            obj.getScore(playerLine).setScore(1);
        }


    public static String session_text(int session){
        return switch (session) {
            case 0 -> "正在等待開始比賽";
            case 1 -> "正在倒數";
            case 3 -> "比賽進行中";
            case 4 -> "比賽結束";
            default -> "?";
        };
    }
}
