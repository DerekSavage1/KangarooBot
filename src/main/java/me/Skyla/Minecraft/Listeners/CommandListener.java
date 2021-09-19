package me.Skyla.Minecraft.Listeners;

import me.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class CommandListener implements Listener {

    private Main instance;

    public CommandListener(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equals("/stop") || e.getMessage().equals("/restart")) {
            e.setCancelled(true);
            Bukkit.broadcastMessage("Server Stopping in 10s!");
            Main.sendStartStopMessageToDiscord(false);
            BukkitScheduler s = instance.getServer().getScheduler();
            s.scheduleSyncDelayedTask(instance, Bukkit::shutdown, 200L);
        }
    }
 }
