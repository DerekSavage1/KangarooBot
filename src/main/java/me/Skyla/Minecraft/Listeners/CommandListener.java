package me.Skyla.Minecraft.Listeners;

import me.ChewyN.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equals("/stop") || e.getMessage().equals("/restart")) {
            e.setCancelled(true);
            Bukkit.broadcastMessage("Server Stopping in 10s!");
            Main.sendStartStopMessageToDiscord(false);
            BukkitScheduler s = Main.getInstance().getServer().getScheduler();
            s.scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.shutdown();
                }
            }, 200l);
        }
    }
 }
