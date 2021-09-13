package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import me.ChewyN.Main;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    private static final JDA discordbot = Main.getDiscordbot();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        Player      p = e.getPlayer();
        String      message = e.getMessage();
        String      username = getUserName(p);

        Bukkit.broadcastMessage(username + ChatColor.GRAY + " » " + ChatColor.WHITE + message);
        DiscordMessageHandler.sendToBothDiscordChannels(username, message);

    }

    private String getUserName(Player p) {
        if(p.getCustomName() == null) {
            return p.getName();
        } else {
            return p.getCustomName();
        }
    }

}
