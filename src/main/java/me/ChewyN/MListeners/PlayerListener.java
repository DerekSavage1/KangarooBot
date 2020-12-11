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

import static me.ChewyN.Main.getGuild;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		modifyJoinMessage(e);
		String playerName = e.getPlayer().getPlayerListName();

		Main.getInstance().getServer().broadcastMessage("1");

		if(isPlayerDiscordMember(playerName)) {
			Main.getInstance().getServer().broadcastMessage("3");
			setOnlineRole(playerName, true);
		}

		PermissionsManager.getPermissionsManager().reload(player);
		PermissionsManager.getPermissionsManager().refresh(player);
	}

	private boolean isPlayerDiscordMember(String playerName) {

		Main.getGuild().retrieveMembers();
		return !Main.getGuild().getMembersByNickname(playerName, false).isEmpty();

	}

	private void setOnlineRole(String nickname, boolean isOnlineInGame) {
		Member		member = getGuild().getMembersByNickname(nickname,true).get(0);
		Role		onlineRole = getGuild().getRolesByName("online in-game", true).get(0);

		Main.getInstance().getServer().broadcastMessage("method called");

		if(isOnlineInGame && !member.getOnlineStatus().equals(OnlineStatus.INVISIBLE)) {
			getGuild().addRoleToMember(member, onlineRole).complete();
		} else {
			getGuild().removeRoleFromMember(member, onlineRole).complete();
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
		setOnlineRole(e.getPlayer().getPlayerListName(), false);
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
