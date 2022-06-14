package com.emiv.tempfly;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallListener implements Listener {

    TempFlyMain plugin;
    public FallListener(TempFlyMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerFall(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL){
                if (plugin.fallingPlayers.contains(p)){
                    e.setCancelled(true);
                    plugin.fallingPlayers.remove(p);
                }
            }
        }
    }
}
