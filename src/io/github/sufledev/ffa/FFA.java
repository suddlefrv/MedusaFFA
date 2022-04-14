package io.github.sufledev.ffa;

import club.minemen.spigot.ClubSpigot;
import club.minemen.spigot.handler.MovementHandler;
import io.github.suddlefrv.jarvis.CorePlugin;
import io.github.suddlefrv.jarvis.board.BoardAdapter;
import io.github.suddlefrv.jarvis.manager.BoardManager;
import io.github.sufledev.ffa.board.ScoreboardProvider;
import io.github.sufledev.ffa.commands.StatsCommand;
import io.github.sufledev.ffa.data.FFARedis;
import io.github.sufledev.ffa.data.PlayerData;
import io.github.sufledev.ffa.listener.PlayerListeners;
import io.github.sufledev.ffa.manager.Medusa;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FFA extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("Enabled");
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);
        Bukkit.getPluginManager().registerEvents(new StatsCommand(), this);
        this.registerProviders();
        new FFARedis();

    }
    private void registerProviders() {
        CorePlugin.getInstance().setBoardManager(new BoardManager((BoardAdapter)new ScoreboardProvider()));
        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(Entity::remove));
        getCommand("stats").setExecutor(new StatsCommand());
    }
    @Override
    public void onDisable() {


    }

    public static Medusa getMedusa(){
        return new Medusa();
    }

    public static FFA getInstance() {return new FFA();}

}
