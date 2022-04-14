package io.github.sufledev.ffa.listener;

import io.github.suddlefrv.jarvis.CorePlugin;
import io.github.suddlefrv.jarvis.mineman.Mineman;
import io.github.suddlefrv.jarvis.util.ItemBuilder;
import io.github.suddlefrv.jarvis.util.finalutil.Color;
import io.github.suddlefrv.jarvis.util.finalutil.PlayerUtil;
import io.github.sufledev.ffa.FFA;
import io.github.sufledev.ffa.data.PlayerData;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListeners implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Mineman menemen = CorePlugin.getInstance().getPlayerManager().getPlayer(p.getUniqueId());
        int x = -16;
        int y = 189;
        int z = -11;
        int pi = 1;
        int yaw = 0;
        String mundo = "ffa";
        Location n = new Location(Bukkit.getWorld(mundo), x, y, z, pi, yaw);
        e.setJoinMessage(Color.translate("&8[&a+&8] " + menemen.getRank().getColor() + p.getName() + " &fhas joined"));
        FFA.getMedusa().setupPlayer(p);
        PlayerData.getByName(e.getPlayer().getName()).load();
        p.teleport(n);


    }

    @EventHandler
    public void DMG(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)){
            return;
        }
        if(!(e.getDamager() instanceof Player)){
            return;
        }
        Player p = (Player) e.getEntity();
        Player dmg = (Player) e.getDamager();
        Mineman datacik = CorePlugin.getInstance().getPlayerManager().getPlayer(p.getUniqueId());

        if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
            dmg.sendMessage(Color.translate(datacik.getRank().getColor() + datacik.getPlayer().getName() + " &fis now at &c" + p.getHealth()));

        }

    }

    @EventHandler
    public void dth(PlayerDeathEvent e){
        Player p = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        killer.setHealth(killer.getMaxHealth());

        Mineman data1 = CorePlugin.getInstance().getPlayerManager().getPlayer(p.getUniqueId());
        Mineman data2 = CorePlugin.getInstance().getPlayerManager().getPlayer(killer.getUniqueId());

        PlayerData pdata1 = PlayerData.getByName(data1.getName());
        PlayerData pdata2 = PlayerData.getByName(data2.getName());
        p.spigot().respawn();
        FFA.getMedusa().clearPlayer(p);
        FFA.getMedusa().setupPlayer(p);

        pdata2.setKills(pdata2.getKills() + 1);
        pdata2.setGameKills(pdata2.getGameKills() + 1);
        pdata1.setDeaths(pdata1.getDeaths() + 1);

        pdata2.setElo(pdata2.getElo() + 100);
        pdata2.setGameElo(pdata2.getGameElo() + 100);
        pdata2.setHighestKillStreak(pdata2.getHighestKillStreak() + 1);
        if(pdata1.getElo() >= 0){
            pdata1.setElo(pdata1.getElo() - 100);
        }
        if(pdata1.getGameElo() >=0){
            pdata1.setGameElo(pdata1.getGameElo() - 100);
        }
        if(pdata2.getHighestKillStreak() == 5){
            Bukkit.broadcastMessage(Color.translate("&8[&bFFA&8] " + data2.getRank().getColor() + data2.getPlayer().getName() + " &fis on &b5 &fKillStreak!"));
        }
        if(pdata2.getHighestKillStreak() == 10){
            Bukkit.broadcastMessage(Color.translate("&8[&bFFA&8] " + data2.getRank().getColor() + data2.getPlayer().getName() + " &fis on &b10 &fKillStreak!"));
        }
        if(pdata2.getHighestKillStreak() == 15){
            Bukkit.broadcastMessage(Color.translate("&8[&bFFA&8] " + data2.getRank().getColor() + data2.getPlayer().getName() + " &fis on &b15 &fKillStreak!"));
        }
        if(pdata2.getHighestKillStreak() == 20){
            Bukkit.broadcastMessage(Color.translate("&8[&bFFA&8] " + data2.getRank().getColor() + data2.getPlayer().getName() + " &fis on &b20 &fKillStreak!"));
        }
        e.setDeathMessage(Color.translate(data1.getRank().getColor() + data1.getPlayer().getName() + " &fwas killed by " + data2.getRank().getColor() + data2.getPlayer().getName() + "&f."));

        if(pdata1.getHighestKillStreak() != 0){
            pdata1.setHighestKillStreak(0);
            data1.getPlayer().sendMessage(Color.translate("&8[&bFFA&8] &fYou lost your &bkillstreak&f!"));
        }
        killer.sendMessage(Color.translate("&fYou gained &b100 &fELO for killing."));
        killer.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE).name("&6Mysterious Apple").build());
        if(pdata1.getElo() >= 0) {
            p.sendMessage(Color.translate("&fYou lost &b100 &fELO for dying"));
        } else {
            p.sendMessage(Color.translate("&fYou couldn't lost &b100 &fELO ( Not Enough ELO :( )"));
        }

        pdata1.saveWeb();
        pdata2.saveWeb();

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerData.getByName(player.getName());
        Mineman menemen = CorePlugin.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
        event.setQuitMessage(Color.translate("&8[&c-&8] " + menemen.getRank().getColor() + player.getName() + " &fhas left"));
        data.setHighestKillStreak(0);
        data.save();

    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().name().startsWith("RIGHT_")) {
            ItemStack item = event.getItem();
            if (item == null)
                return;
            Player player = event.getPlayer();
            PlayerData playerData = PlayerData.getByName(player.getName());
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled())
            return;
        Player player = event.getPlayer();
        PlayerData data = PlayerData.getByName(player.getName());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().isOp())
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onCraftItemEvent(CraftItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().isOp())
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
        ((Player)event.getEntity()).setSaturation(1000.0F);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        event.setCancelled(false);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState())
            event.setCancelled(true);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }
    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (e.getEntity().getLocation().getWorld().getName().equals("ffa") &&
                e.getEntity() instanceof Player &&
                e.getCause() == EntityDamageEvent.DamageCause.FALL)
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL))
            event.getPlayer().getItemInHand().setDurability((short)(event.getPlayer().getItemInHand().getDurability() + 16));
    }


}
