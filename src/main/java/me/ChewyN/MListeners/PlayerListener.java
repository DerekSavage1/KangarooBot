package me.ChewyN.MListeners;

import me.ChewyN.Main;
import me.ChewyN.Util.Message;
import me.ChewyN.managers.PermissionsManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
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
		setOnlineRole(e.getPlayer().getPlayerListName(), true);
		
		PermissionsManager.getPermissionsManager().reload(player);
		PermissionsManager.getPermissionsManager().refresh(player);
	}

	private void setOnlineRole(String nickname, boolean isOnline) {
		Member member = Main.getGuild().getMembersByNickname(nickname,true).get(0);
		Role onlineRole = Main.getGuild().getRolesByName("online in-game", true).get(0);
		if(isOnline && !member.getOnlineStatus().equals(OnlineStatus.INVISIBLE)) {
			Main.getGuild().addRoleToMember(member, onlineRole).queue();
		} else {
			Main.getGuild().removeRoleFromMember(member, onlineRole).queue();
		}


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
