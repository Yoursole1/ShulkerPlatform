package me.yoursole.shulkerplatform;

import me.yoursole.shulkerplatform.blackhole.GameLoop;
import me.yoursole.shulkerplatform.blackhole.commands.Platform;
import me.yoursole.shulkerplatform.blackhole.commands.Region;
import me.yoursole.shulkerplatform.events.BlockBreak;
import me.yoursole.shulkerplatform.events.BlockBreakB;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ShulkerPlatform extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("ForceRegion")).setExecutor(new Region());
        Objects.requireNonNull(getCommand("Platform")).setExecutor(new Platform());

        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakB(), this);

        new GameLoop();
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelTask(GameLoop.taskID);
    }
}
