package me.ChewyN.Minecraft.Listeners.Player;
import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        DiscordMessageHandler.sendToBothDiscordChannels(getUserName(e.getPlayer()), e.getMessage());
    }

    private String getUserName(Player p) {
        if(p.getCustomName() == null) {
            return p.getName();
        } else {
            return p.getCustomName();
        }
    }

}
