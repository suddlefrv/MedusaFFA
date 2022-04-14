package io.github.sufledev.ffa.manager;

import io.github.suddlefrv.jarvis.CorePlugin;
import io.github.suddlefrv.jarvis.mineman.Mineman;
import io.github.suddlefrv.jarvis.util.ItemBuilder;
import io.github.suddlefrv.jarvis.util.finalutil.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Medusa implements Manager{

    public void setupPlayer(Player p){
        clearPlayer(p);
        Mineman menemen = CorePlugin.getInstance().getPlayerManager().getPlayer(p.getUniqueId());
        p.sendMessage(Color.translate("&fWelcome to &bFFA " + menemen.getRank().getColor() + menemen.getPlayer().getName() + "&f."));
        p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).name("&bHelmet").build());
        p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).name("&bChestplate").build());
        p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).name("&bLeggings").build());
        p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).name("&bBoots").build());

        p.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).name("&bSword").build());
        p.getInventory().setItem(1, new ItemBuilder(Material.FISHING_ROD).name("&bRod").build());
        p.getInventory().setItem(2, new ItemBuilder(Material.FLINT_AND_STEEL).name("&bFNS").durability(62).build());
        p.getInventory().setItem(3, new ItemBuilder(Material.BOW).name("&bBow").build());
        p.getInventory().setItem(8, new ItemBuilder(Material.ARROW).name("&bArrow").amount(16).build());

    }

    public void clearPlayer(Player p){
        p.getInventory().setHelmet(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setBoots(null);
        p.getInventory().clear();
    }

}
