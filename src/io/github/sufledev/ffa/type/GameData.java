package io.github.sufledev.ffa.type;

import io.github.suddlefrv.jarvis.util.finalutil.Color;
import io.github.sufledev.ffa.FFA;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Random;

public class GameData {

    public GameTypes gameTypes;

    public static String niceInt(int i) {
        int r = i * 1000;
        int sec = r / 1000 % 60;
        int min = r / '\uea60' % 60;
        int h = r / 3600000 % 24;
        return (h > 0 ? (h < 10 ? "0" : "") + h + ":" : "") + (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
    }

    public static void setgameType(GameTypes gameTypes){
        Bukkit.broadcastMessage(Color.translate("&8[&bFFA&8] &fScenario changed to &b" + gameTypes.toString().toLowerCase(Locale.ROOT)));
        Bukkit.getScheduler().scheduleSyncRepeatingTask(FFA.getInstance(), new Runnable() {
            int time = 2400;
            @Override
            public void run() {
                switch (time){
                    default:
                        time--;
                        break;
                    case 0:
                        resetgameType();
                        break;
                }

            }
        }, 0L, 2400L);
        if(gameTypes == GameTypes.BOWLESS){
            for(Player p : Bukkit.getOnlinePlayers()){
                p.getInventory().remove(Material.BOW);
                p.getInventory().remove(Material.ARROW);
            }
        }

    }

    public static void resetgameType(){
        setgameType(GameTypes.DEFAULT);
    }

    public GameTypes getGameType(){
        return this.gameTypes;
    }

    public static GameData getInstance(){
        return new GameData();
    }

}
