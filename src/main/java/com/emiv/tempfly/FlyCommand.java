package com.emiv.tempfly;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FlyCommand implements CommandExecutor {

    TempFlyMain plugin;
    public FlyCommand(TempFlyMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (!plugin.flyingPlayers.contains(p)){
                if (plugin.econ.getBalance(p) >= plugin.getConfig().getInt("FlyPrice")){
                    p.setAllowFlight(true);
                    plugin.flyingPlayers.add(p);
                    plugin.econ.withdrawPlayer(p, plugin.getConfig().getInt("FlyPrice"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("FlySuccessMsg").replace("%prefix%", plugin.getConfig().getString("PluginPrefix")).replace("%duration%", String.valueOf(plugin.getConfig().getInt("FlyDuration"))).replace("%price%", String.valueOf(plugin.getConfig().getInt("FlyPrice")))));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setAllowFlight(false);
                            plugin.flyingPlayers.remove(p);
                            plugin.fallingPlayers.add(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("FlyTimeEnded").replace("%prefix%", plugin.getConfig().getString("PluginPrefix"))));
                        }
                    }.runTaskLater(plugin, (20L*plugin.getConfig().getInt("FlyDuration")));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("FlyNoMoneyMsg").replace("%prefix%", plugin.getConfig().getString("PluginPrefix"))));
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("FlyAlreadyEnabled").replace("%prefix%", plugin.getConfig().getString("PluginPrefix"))));
            }
        }
        return false;
    }
}
