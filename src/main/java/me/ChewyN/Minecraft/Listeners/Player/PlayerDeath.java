package me.ChewyN.Minecraft.Listeners.Player;

import me.ChewyN.Minecraft.Util.Message;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static me.ChewyN.Main.getInstance;

public class PlayerDeath implements Listener {

	private HashMap<Player, Location> deathMap = new HashMap<>();

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null); // hmm will this throw an error? FIXME
		Message.sendCenteredMessage(e.getEntity().getServer(), ChatColor.RED + "☠ " + ChatColor.WHITE + e.getEntity().getPlayerListName() +  " has died"  + ChatColor.RED + " ☠");

		deathMap.put(e.getEntity(), e.getEntity().getLocation());

		new BukkitRunnable(){
			@Override
			public void run() {
				e.getEntity().sendMessage(ChatColor.GOLD + "For 100 coins you can teleport to where you died with /back");
			}
		}.runTaskLaterAsynchronously(getInstance(), 50);


	}
}
