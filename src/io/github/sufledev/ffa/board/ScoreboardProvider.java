package io.github.sufledev.ffa.board;

import io.github.suddlefrv.jarvis.CorePlugin;
import io.github.suddlefrv.jarvis.board.Board;
import io.github.suddlefrv.jarvis.board.BoardAdapter;
import io.github.suddlefrv.jarvis.mineman.Mineman;
import io.github.suddlefrv.jarvis.rank.Rank;
import io.github.suddlefrv.jarvis.util.finalutil.Color;
import io.github.suddlefrv.jarvis.util.finalutil.PlayerUtil;
import io.github.sufledev.ffa.data.PlayerData;
import io.github.sufledev.ffa.type.GameData;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.material.SimpleAttachableMaterialData;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import sun.java2d.pipe.SpanShapeRenderer;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoreboardProvider implements BoardAdapter {
    @Override
    public List<String> getScoreboard(Player player, Board board) {
        List<String> list = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String nn = null;
        Mineman menemen = CorePlugin.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
        PlayerData data = PlayerData.getByName(menemen.getPlayer().getName());
        list.add("&8&m--------------------");
        list.add("&7" + df.format(date));
        list.add("");
        list.add("&b&lInfo");
        list.add(" &fYou: " + menemen.getRank().getColor() + menemen.getPlayer().getName());
        list.add(" &fPing: &b" + PlayerUtil.getPing(player));
        list.add("");
        list.add("&fScenario: &b" + GameData.getInstance().getGameType());
        list.add("&fOnline: &b" + Bukkit.getOnlinePlayers().size());
        list.add("");
        list.add("&7&omedusapvp.us");
        list.add("&8&m--------------------");

        return Color.translate(list);
    }

    @Override
    public String getTitle(Player player) {
        return Color.translate("&bMedusa &fFFA");
    }

    @Override
    public long getInterval() {
        return 0;
    }

    @Override
    public void onScoreboardCreate(Player player, Scoreboard scoreboard) {
        Bukkit.getOnlinePlayers().forEach((o) -> {
            Mineman mineman = CorePlugin.getInstance().getPlayerManager().getPlayer(o.getUniqueId());
            if (mineman != null) {
                Rank rank = mineman.getDisplayRank();
                if (rank.isAbove(Rank.MASTER)) {
                    Team team = scoreboard.getTeam(rank.getName());
                    if (team == null) {
                        team = scoreboard.registerNewTeam(rank.getName());
                    }

                    team.setPrefix(rank.getColor());
                    if (!team.hasEntry(o.getName())) {
                        team.addEntry(o.getName());
                    }
                }
            }

        });
    }

    @Override
    public void preLoop() {

    }
}
