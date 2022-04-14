package io.github.sufledev.ffa.commands;

import io.github.suddlefrv.jarvis.CorePlugin;
import io.github.suddlefrv.jarvis.clickable.Clickable;
import io.github.suddlefrv.jarvis.mineman.Mineman;
import io.github.suddlefrv.jarvis.util.ItemBuilder;
import io.github.suddlefrv.jarvis.util.finalutil.CC;
import io.github.suddlefrv.jarvis.util.finalutil.Color;
import io.github.sufledev.ffa.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        PlayerData data = PlayerData.getByName(p.getName());
        List<String> list = new ArrayList<>();
        list.add("&8&m------------------------------------");
        list.add("&b&lYour Stats");
        list.add("&f");
        list.add("&fKills: &b" + data.getKills());
        list.add("&fDeaths: &b" + data.getDeaths());
        list.add("&fELO: &b" + data.getElo());
        list.add("&fDivision: &bNone");
        list.add("&fKS: &b" + data.getHighestKillStreak());
        list.add("&f");
        list.add("&8&m------------------------------------");
        list = Color.translate(list);
        if(strings.length == 0){
            for(String listt : list){
                p.sendMessage(listt);

            }
            Clickable clickable = new Clickable();
            clickable.add(Color.translate("&cClick to view your stats in GUI"), "Click Here", "/stats gui");
            clickable.sendToPlayer(p);
        }
        if(strings.length == 1){
            String arg0 = strings[0];
            if(arg0.equalsIgnoreCase("gui")){
                openStatsInv(p);
            } else {
                for(String listt : list){
                    p.sendMessage(listt);
                }
                Clickable clickable = new Clickable();
                clickable.add(Color.translate("&cClick to view your stats in GUI"), "Click Here", "/stats gui");
                clickable.sendToPlayer(p);
            }
        }


        return true;
    }



    public void openStatsInv(Player player){
        Mineman menemen = CorePlugin.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
        PlayerData data = PlayerData.getByName(menemen.getPlayer().getName());
        Inventory inv = Bukkit.createInventory(player, 27, Color.translate(menemen.getRank().getColor() + menemen.getPlayer().getName() + "&f's stats"));
        inv.setItem(11, new ItemBuilder(Material.SKULL_ITEM).name("&b&lKills").lore(Arrays.asList(Color.translate("&8Stats"), Color.translate(""), Color.translate("&fYour Kills: &b" + data.getKills()))).build());
        inv.setItem(12, new ItemBuilder(Material.BONE).name("&b&lDeaths").lore(Arrays.asList(Color.translate("&8Stats"), Color.translate(""), Color.translate("&fYour Deaths: &b" + data.getDeaths()))).build());
        inv.setItem(14, new ItemBuilder(Material.COMPASS).name("&b&lELO").lore(Arrays.asList(Color.translate("&8Stats"), Color.translate(""), Color.translate("&fYour ELO: &b" + data.getElo()))).build());
        inv.setItem(15, new ItemBuilder(Material.FLINT).name("&b&lKillStreak").lore(Arrays.asList(Color.translate("&8Stats"), Color.translate(""), Color.translate("&fYour KillStreak: &b" + data.getHighestKillStreak()))).build());
        inv.setItem(22, new ItemBuilder(Material.BOW).name("&b&lDivision").lore(Arrays.asList(Color.translate("&8Stats"), Color.translate(""), Color.translate("&fYour Division: &bNone"))).build());
        player.openInventory(inv);
    }

    @EventHandler
    public void invclick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        Mineman menemen = CorePlugin.getInstance().getPlayerManager().getPlayer(p.getUniqueId());

        if(e.getClickedInventory().getName().contains(Color.translate(menemen.getRank().getColor() + menemen.getPlayer().getName() + "&f's stats"))){
            e.setCancelled(true);

        }
    }

}
