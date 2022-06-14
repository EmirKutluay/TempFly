package com.emiv.tempfly;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class TempFlyMain extends JavaPlugin {

    public Economy econ = null;

    FlyCommand flyCommand;

    public ArrayList<Player> flyingPlayers = new ArrayList<>();
    public ArrayList<Player> fallingPlayers = new ArrayList<>();

    @Override
    public void onEnable(){
        if (!setupEconomy()) {
            Bukkit.getConsoleSender().sendMessage("TempFly needs Vault to function correctly.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.saveDefaultConfig();

        getCommand("fly").setExecutor(new FlyCommand(this));
        flyCommand = new FlyCommand(this);

        Bukkit.getPluginManager().registerEvents(new FallListener(this), this);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp.getProvider();
        return econ != null;
    }
}
