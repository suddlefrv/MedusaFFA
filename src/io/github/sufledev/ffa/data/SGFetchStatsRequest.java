package io.github.sufledev.ffa.data;

import io.github.suddlefrv.jarvis.api.request.Request;

import java.util.Map;
import java.util.UUID;

public class SGFetchStatsRequest implements Request
{
    private final UUID uuid;

    public SGFetchStatsRequest(final UUID uuid) {
        this.uuid = uuid;
    }

    public String getPath() {
        return "/ffa/" + this.uuid.toString();
    }

    public Map<String, Object> toMap() {
        return null;
    }
}