package io.github.sufledev.ffa.data;

import io.github.suddlefrv.jarvis.api.request.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateStatsRequest implements Request
{
    private String name;
    private UUID uuid;
    private int id;
    private int kills;
    private int deaths;
    private int wins;
    private int elo;
    private int killStreak;
    private int mvp2;
    private int played;

    public static UpdateStatsRequest builder() {
        return new UpdateStatsRequest();
    }

    public UpdateStatsRequest name(final String name) {
        this.name = name;
        return this;
    }

    public UpdateStatsRequest uuid(final UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UpdateStatsRequest id(final int id) {
        this.id = id;
        return this;
    }

    public UpdateStatsRequest kills(final int id) {
        this.kills = id;
        return this;
    }

    public UpdateStatsRequest mvp2(final int id) {
        this.mvp2 = id;
        return this;
    }

    public UpdateStatsRequest deaths(final int id) {
        this.deaths = id;
        return this;
    }

    public UpdateStatsRequest wins(final int id) {
        this.wins = id;
        return this;
    }

    public UpdateStatsRequest elo(final int id) {
        this.elo = id;
        return this;
    }

    public UpdateStatsRequest killStreak(final int id) {
        this.killStreak = id;
        return this;
    }

    public UpdateStatsRequest played(final int id) {
        this.played = id;
        return this;
    }

    public String getPath() {
        return "/ffa/" + this.id + "/update";
    }

    public Map<String, Object> toMap() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", this.name);
        map.put("uuid", this.uuid.toString());
        map.put("id", this.id);
        map.put("kills", this.kills);
        map.put("mvp2", this.mvp2);
        map.put("deaths", this.deaths);
        map.put("wins", this.wins);
        map.put("elo", this.elo);
        map.put("played", this.played);
        map.put("kill_streak", this.killStreak);
        return map;
    }
}
