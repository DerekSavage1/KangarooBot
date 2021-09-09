package me.ChewyN.Minecraft.Listeners.Player;


//import me.ChewyN.Minecraft.Packets.packet;
import me.ChewyN.Minecraft.Util.Message;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

import static me.ChewyN.Main.*;
import static me.ChewyN.Discord.Util.TextChannels.*;

public class JoinAndQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player  p = e.getPlayer();

        modifyJoinMessage(e);
        sendJoinOrQuitMessageToDiscord(p,true);
        setDiscordOnlineRole(p.getPlayerListName(), true);
//        packet.injectPlayer(p);


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        setDiscordOnlineRole(p.getPlayerListName(), false);
        sendJoinOrQuitMessageToDiscord(p,false);
//        packet.removePlayer(p);
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

    public void setDiscordOnlineRole(String nickname, boolean setOnline) {
        List<Member> members = getGuild().loadMembers().get();
        Role onlineRole = getGuild().getRolesByName("online in-game", true).get(0);
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

    private void sendJoinOrQuitMessageToDiscord(Player player, boolean isJoining) {
        String			playerUUID = player.getUniqueId().toString().replaceAll("-","");
        String			faceURL = "https://minotar.net/avatar/"+playerUUID+"/25"; //discord wants as string
        String			playerName = player.getPlayerListName();
        int				playerCount = Bukkit.getOnlinePlayers().size();

        EmbedBuilder joinMessage = new EmbedBuilder();
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



}
