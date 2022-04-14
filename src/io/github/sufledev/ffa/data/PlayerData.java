package io.github.sufledev.ffa.data;

import java.util.HashMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import io.github.suddlefrv.jarvis.CorePlugin;
import io.github.suddlefrv.jarvis.api.abstr.AbstractBukkitCallback;
import io.github.suddlefrv.jarvis.api.callback.Callback;
import io.github.suddlefrv.jarvis.api.request.Request;
import io.github.suddlefrv.jarvis.mineman.Mineman;
import io.github.sufledev.ffa.FFA;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.UUID;
import java.util.Map;

public class PlayerData
{
    public static Map<UUID, PlayerData> playerDatas;
    private String name;
    private int id;
    private UUID uuid;
    private String realName;
    private String lastVoted;
    private int gameKills;
    private int gameElo;
    private boolean sponsored;
    private int kills;
    private int deaths;
    private int elo;
    private int bounty;
    private int wins;
    private int played;
    private int mvp2;
    private int highestKillStreak;
    private boolean tab;
    private boolean loaded;

    public PlayerData(final String name) {
        this.lastVoted = "";
        this.elo = 1000;
        this.tab = true;
        this.name = name;
        this.uuid = Bukkit.getOfflinePlayer(name).getUniqueId();
        PlayerData.playerDatas.put(this.uuid, this);
    }

    public void load() {
        this.loadWeb();
        this.loaded = true;
    }

    public void save() {
        if (!this.loaded) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) FFA.getInstance(), this::saveWeb);
        this.loaded = true;
    }

    private void loadWeb() {
        final Mineman mineman = CorePlugin.getInstance().getPlayerManager().getPlayer(this.uuid);
        if (mineman == null || !mineman.isDataLoaded() || mineman.isErrorLoadingData()) {
            return;
        }
        this.id = mineman.getId();
        CorePlugin.getInstance().getRequestProcessor().sendRequestAsync((Request)new SGFetchStatsRequest(this.uuid), (Callback)new AbstractBukkitCallback() {
            public void callback(final JsonElement jsonElement) {
                if (!jsonElement.isJsonNull()) {
                    final JsonObject stats = jsonElement.getAsJsonObject();
                    JsonElement element = stats.get("name");
                    if (element != null && !element.isJsonNull()) {
                        PlayerData.this.realName = element.getAsString();
                    }
                    element = stats.get("kills");
                    if (element != null && !element.isJsonNull()) {
                        PlayerData.this.kills = element.getAsInt();
                    }
                    element = stats.get("deaths");
                    if (element != null && !element.isJsonNull()) {
                        PlayerData.this.deaths = element.getAsInt();
                    }
                    element = stats.get("wins");
                    if (element != null && !element.isJsonNull()) {
                        PlayerData.this.wins = element.getAsInt();
                    }
                    element = stats.get("mvp2");
                    if (element != null && !element.isJsonNull()) {
                        PlayerData.this.mvp2 = element.getAsInt();
                    }
                    element = stats.get("played");
                    if (element != null && !element.isJsonNull()) {
                        PlayerData.this.played = element.getAsInt();
                    }
                    element = stats.get("kill_streak");
                    if (element != null && !element.isJsonNull()) {
                        PlayerData.this.highestKillStreak = element.getAsInt();
                    }
                    element = stats.get("elo");
                    if (element != null && !element.isJsonNull()) {
                        PlayerData.this.elo = element.getAsInt();
                    }
                }
            }

            public void onError(final String message) {
                Bukkit.getConsoleSender().sendMessage("Error fetching survivalgames stats for " + PlayerData.this.name);
            }
        });
    }

    public void saveWeb() {
        final UpdateStatsRequest request = UpdateStatsRequest.builder().name(this.name).uuid(this.uuid).id(this.id).kills(this.kills).deaths(this.deaths).elo(this.elo).killStreak(this.highestKillStreak);
        CorePlugin.getInstance().getRequestProcessor().sendRequestAsync((Request)request);
    }

    public static PlayerData getByName(final String name) {
        final UUID uuid = (Bukkit.getPlayer(name) == null) ? Bukkit.getOfflinePlayer(name).getUniqueId() : Bukkit.getPlayer(name).getUniqueId();
        return (PlayerData.playerDatas.get(uuid) == null) ? new PlayerData(name) : PlayerData.playerDatas.get(uuid);
    }

    public double getKD() {
        double kd;
        if (this.kills > 0 && this.deaths == 0) {
            kd = this.kills;
        }
        else if (this.kills == 0 && this.deaths == 0) {
            kd = 0.0;
        }
        else {
            kd = this.kills / this.deaths;
        }
        return kd;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getGameKills() {
        return this.gameKills;
    }

    public void setGameKills(final int gameKills) {
        this.gameKills = gameKills;
    }

    public int getGameElo() {
        return this.gameElo;
    }

    public void setGameElo(final int gameElo) {
        this.gameElo = gameElo;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(final int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(final int deaths) {
        this.deaths = deaths;
    }

    public int getElo() {
        return this.elo;
    }

    public void setElo(final int elo) {
        this.elo = elo;
    }

    public int getBounty() {
        return this.bounty;
    }

    public void setBounty(final int bounty) {
        this.bounty = bounty;
    }

    public int getHighestKillStreak() {
        return this.highestKillStreak;
    }

    public void setHighestKillStreak(final int highestKillStreak) {
        this.highestKillStreak = highestKillStreak;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    static {
        PlayerData.playerDatas = new HashMap<UUID, PlayerData>();
    }}
