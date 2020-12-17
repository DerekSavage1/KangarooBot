package me.ChewyN.MListeners;

import me.ChewyN.Main;
import me.ChewyN.Util.Message;
import me.ChewyN.managers.PermissionsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static me.ChewyN.Main.*;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player	player = e.getPlayer();

		modifyJoinMessage(e);
		discordMessage(player,true);
		setOnlineRole(player.getPlayerListName(), true);

		PermissionsManager.getPermissionsManager().reload(player);
		PermissionsManager.getPermissionsManager().refresh(player);

	}//on join

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		PermissionsManager.getPermissionsManager().clear(player);
		setOnlineRole(player.getPlayerListName(), false);
		discordMessage(player,false);
	}

	private void discordMessage(Player player, boolean isJoining) {
		String			playerUUID = player.getUniqueId().toString().replaceAll("-","");
		String			faceURL = "https://minotar.net/avatar/"+playerUUID+"/25"; //discord wants as string
		String			playerName = player.getPlayerListName();
		int				playerCount = Bukkit.getOnlinePlayers().size();

		EmbedBuilder	joinMessage = new EmbedBuilder();
		joinMessage.setThumbnail(faceURL);
		if(isJoining) {
			joinMessage.setTitle(playerName + " has joined the server");
			joinMessage.setColor(0x42f545);
		} else {
			joinMessage.setTitle(playerName + " has left the server");
			joinMessage.setColor(0xeb4034);
			playerCount--;
		}

		if(playerCount <= 0) {
			joinMessage.setDescription("No players online");
			GAME_TEXT_CHANNEL.sendMessage(joinMessage.build()).queue();
			joinMessage.clear();
			return;
		}

		//Building description
		StringBuilder description = new StringBuilder("Now " + playerCount + " players online: \n");
		for( Player p : Bukkit.getOnlinePlayers()) {
			if(!isJoining && p.getPlayerListName().equals(player.getPlayerListName())) continue; //if they are quitting leave out their name
			description.append("`").append(p.getPlayerListName()).append("`, ");
		}

		description = new StringBuilder(description.substring(0, description.length() - 2)); //removing the space and comma at the end
		joinMessage.setDescription(description.toString());

		GAME_TEXT_CHANNEL.sendMessage(joinMessage.build()).queue();

		joinMessage.clear();

	} //discord message

	public void setOnlineRole(String nickname, boolean setOnline) {
		List<Member>	members = getGuild().loadMembers().get();
		Role			onlineRole = getGuild().getRolesByName("online in-game", true).get(0);
		Member			match = null;

		for( Member member : members ) {
			if(member.getNickname() == null) continue;

			if(member.getNickname().equalsIgnoreCase(nickname)) {
				match = member;
				break;
			}
		}

		if(match == null) {
			getInstance().getLogger().info("Debug: No match found!");
			return;
		}

		if(setOnline) {
			getGuild().addRoleToMember(match, onlineRole).complete();
		} else {
			getGuild().removeRoleFromMember(match, onlineRole).complete();
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
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		Player	p = e.getPlayer();
		String	name = p.getDisplayName();
//		TODO add prefix when ranks exist
		String	message = e.getMessage();
		Bukkit.broadcastMessage(name + ChatColor.GRAY.toString() + " » " + ChatColor.WHITE.toString() + message);
	}

	@EventHandler
	public void commandSpySign(SignChangeEvent e) {
		Player player = e.getPlayer();
		String signMessage =  e.getLine(0) + " " + e.getLine(1)  + " " +  e.getLine(2) + " " + e.getLine(3);
		String playerName = player.getName();

		if (player.hasPermission("commandspy.see.signs")) player.sendMessage(ChatColor.AQUA + "(Sign) " + playerName + " » " + signMessage);


		ADMIN_TEXT_CHANNEL.sendMessage("`(Sign) " + playerName + " »` " + signMessage).queue();
		Main.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "(Sign) " + playerName + " » " + signMessage);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		e.setDeathMessage(Message.getCenteredMessage(ChatColor.RED + "☠ " + ChatColor.WHITE + e.getEntity().getPlayerListName() +  " has died"  + ChatColor.RED + " ☠"));

		new BukkitRunnable(){
			@Override
			public void run() {
				e.getEntity().sendMessage(ChatColor.GOLD + "For 100 coins you can teleport to where you died with /back");
			}
		}.runTaskLaterAsynchronously(getInstance(), 30);


	}
}
