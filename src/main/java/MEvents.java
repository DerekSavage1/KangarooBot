import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MEvents implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        String username;
        if(p.getCustomName() == null) {
            username = p.getName();
        } else {
            username = p.getCustomName();
        }
        Main.sendToDiscord(username, message);
    }

    @EventHandler
    public void commandSpy(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();

        String username;
        if(p.getCustomName() == null) {
            username = p.getName();
        } else {
            username = p.getCustomName();
        }

        Main.sendToAdminDiscord(username, message);
    }
}
