package me.ChewyN.MListeners;

import me.ChewyN.Util.Message;
import me.ChewyN.managers.PermissionsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		modifyJoinMessage(e);
		
		PermissionsManager.getPermissionsManager().reload(player);
		PermissionsManager.getPermissionsManager().refresh(player);
	}

	private void modifyJoinMessage(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPlayedBefore()) {
			e.setJoinMessage("");
			Message.sendCenteredMessage(p.getServer(), ChatColor.AQUA + "Please welcome " + p.getName() + " to our Chewy's Hub!");
			Message.sendCenteredMessage(p.getServer(), ChatColor.AQUA + "This is their first time playing!");
		} else {
			e.setJoinMessage(Message.getCenteredMessage(ChatColor.YELLOW + p.getName() + ChatColor.AQUA + " has joined the game. Welcome back!"));
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		PermissionsManager.getPermissionsManager().clear(player);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		Player	p = e.getPlayer();
		String	name = p.getDisplayName();
//		TODO add prefix
		String	message = e.getMessage();
		Bukkit.broadcastMessage(name + ChatColor.GRAY.toString() + " » " + ChatColor.WHITE.toString() + message);
	}




}
