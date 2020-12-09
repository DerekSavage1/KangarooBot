package me.ChewyN.MListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.ChewyN.managers.PermissionsManager;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		PermissionsManager.getPermissionsManager().reload(player);
		PermissionsManager.getPermissionsManager().refresh(player);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		PermissionsManager.getPermissionsManager().clear(player);
	}

}
