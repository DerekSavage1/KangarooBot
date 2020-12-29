package me.ChewyN.Minecraft.Listeners.Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static me.ChewyN.Discord.Util.TextChannels.sendToDiscord;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        Player      p = e.getPlayer();
        String      message = e.getMessage();
        String      username = getUserName(p);

        Bukkit.broadcastMessage(username + ChatColor.GRAY.toString() + " » " + ChatColor.WHITE.toString() + message);
        sendToDiscord(username, message);

    }

    private String getUserName(Player p) {
        if(p.getCustomName() == null) {
            return p.getName();
        } else {
            return p.getCustomName();
        }

    }

}
