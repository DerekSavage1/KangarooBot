package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Data.ConfigFile;
import me.ChewyN.Main;
import me.ChewyN.Minecraft.Util.Message;
import me.Skyla.Minecraft.Objects.DeathStatus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Random;

import static me.ChewyN.Data.ConfigFile.getDeathMessages;
import static me.ChewyN.Main.getInstance;

public class PlayerDeath implements Listener {

	private static final HashMap<Player, DeathStatus> deathMap = new HashMap<>();

    Random random = new Random();
    int messageId = 0;
    String message = "";

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

//         Random Death Messages!
        messageId = random.nextInt(5);
        switch (messageId) {
            case 0:
                message = " has died";
                break;
            case 1:
                message = " fucked up";
                break;
            case 2:
                message = " slipped in barbeque sauce";
                break;
            case 3:
                message = " found the off button";
                break;
            case 4:
                message = " failed to live";
                break;
            default:
                message = " something broke here lol";
                break;
        }
		String randomDeathMessage = getDeathMessages().get(new Random().nextInt(getDeathMessages().size()));

		String deathMessageCentered = Message.getCenteredMessage(ChatColor.RED + "☠ " + ChatColor.WHITE + e.getEntity().getPlayerListName() +  randomDeathMessage  + ChatColor.RED + " ☠");
		e.setDeathMessage(deathMessageCentered);

        deathMap.put(e.getEntity(), new DeathStatus(e.getEntity().getLocation()));

        if (ConfigFile.backCommandEnabled()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getEntity().sendMessage(ChatColor.GOLD + "You can teleport to where you died with /back");
                }
            }.runTaskLaterAsynchronously(Main.getInstance(), 10);

        }

    }

    public static DeathStatus getPlayerDeathLocation(Player p) {
        return deathMap.get(p);
    }

}
