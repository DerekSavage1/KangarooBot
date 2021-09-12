package me.Skyla.Minecraft.Listeners;

import me.ChewyN.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class ServerCommandListener implements Listener {

    @EventHandler
    public void onCommand(ServerCommandEvent e) {
     if (e.getCommand().equals("stop") || e.getCommand().equals("restart")) {
            e.setCancelled(true);
            Bukkit.broadcastMessage("Server Stopping in 10s!");
            Main.sendStartStopMessageToDiscord(false);
            BukkitScheduler s = Main.getInstance().getServer().getScheduler();
            s.scheduleSyncDelayedTask(Main.getInstance(), Bukkit::shutdown, 200L);
        }
    }
}
