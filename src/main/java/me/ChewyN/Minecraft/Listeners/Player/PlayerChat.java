package me.ChewyN.Minecraft.Listeners.Player;
import me.ChewyN.Discord.Listeners.DiscordMessageHandler;
import me.ChewyN.Minecraft.Util.MinecraftMessageHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);//TODO

        String message = ChatColor.AQUA + getUserName(e.getPlayer()) + ChatColor.GRAY + " » " + ChatColor.WHITE + e.getMessage();

        MinecraftMessageHandler.broadcastMessage(message);
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
