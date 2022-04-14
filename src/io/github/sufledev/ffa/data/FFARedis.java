package io.github.sufledev.ffa.data;

import com.google.gson.JsonObject;
import io.github.suddlefrv.jarvis.CorePlugin;
import io.github.suddlefrv.jarvis.redis.JedisPublisher;
import org.bukkit.Bukkit;

public class FFARedis {
    private JedisPublisher<JsonObject> messagesPublisher;

    public FFARedis() {
        this.messagesPublisher = (JedisPublisher<JsonObject>)new JedisPublisher(CorePlugin.getInstance().getJedisConfig().toJedisSettings(), "game");
    }

    private JsonObject generateBaseMessage() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server-name", CorePlugin.getInstance().getServerManager().getServerName());
        return jsonObject;
    }

    public void updateStatus(final String status) {
        final JsonObject object = this.generateBaseMessage();
        object.addProperty("status", status);
        object.addProperty("online", (Number) Bukkit.getOnlinePlayers().size());
        object.addProperty("initial", (Number)1);
        object.addProperty("gameTime", (Number)1);
        object.addProperty("type", "Solo");
        this.messagesPublisher.write(object);
    }
}
